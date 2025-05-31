package usersService.implementation;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import api.dtos.UserDto;
import api.feintProxies.BankAccountProxy;
import api.feintProxies.CryptoWalletProxy;
import api.services.UsersService;
import feign.FeignException;
import usersService.model.UserModel;
import usersService.repository.UsersServiceRepository;
import util.exceptions.EntityAlreadyExistsException;
import util.exceptions.ForbidenActionException;
import util.exceptions.InvalidRequestException;
import util.exceptions.NoDataFoundException;
import util.exceptions.ServiceUnavailableException;

@RestController
public class UsersServiceImplemetation implements UsersService{

	
	@Autowired
	private UsersServiceRepository repo;
	
	@Autowired
	private BankAccountProxy bankAccountProxy;
	
	@Autowired
	private CryptoWalletProxy cryptoWalletProxy;
	
	@Override
	public List<UserDto> getUsers() {
		List<UserModel> listOfModels = repo.findAll();
		ArrayList<UserDto> listOfDtos = new ArrayList<UserDto>();
		for(UserModel model: listOfModels) {
			listOfDtos.add(convertModelToDto(model));
		}
		return listOfDtos;
	}
	
	
	@Override
	public UserDto getUser(@PathVariable String email){
		if(repo.existsByEmail(email)) {
		UserModel userModel = repo.findByEmail(email);
		return convertModelToDto(userModel);
		}else {
			return null;
		}
	}


	@Override
	public ResponseEntity<?> createUser(UserDto dto,@RequestHeader("Authorization") String authorization) {
		
		String email = getEmail(authorization);
		email = email.toLowerCase();
		if(dto.getRole().equals("USER") || dto.getRole().equals("ADMIN")) {
			if(repo.findByEmail(dto.getEmail()) == null) {
			if(dto.getRole().equals("ADMIN") && repo.existsByEmailAndRole(email,"ADMIN")) {
				
				throw new ForbidenActionException("As an ADMIN you can only add USERs");
			}
			UserModel model = convertDtoToModel(dto);
			
			if(dto.getRole().equals("USER")) {
				try {
					bankAccountProxy.createBankAccount(dto.getEmail().toLowerCase());
				} catch (FeignException e) {
						if(e.status() != 404) {
							throw new ServiceUnavailableException("Bank account service is unavailable");
						}
					throw new NoDataFoundException(e.getMessage());
				}				
				try {
					cryptoWalletProxy.createCryptoWallet(dto.getEmail().toLowerCase());
				}catch (FeignException e) {
					throw new ServiceUnavailableException("Crypto wallet  service is unavailable");
				}
			}
			return ResponseEntity.status(201).body(repo.save(model));
			}
			throw new EntityAlreadyExistsException("User with forwarded email already exists");
		}
		throw new InvalidRequestException("Role must be either USER or ADMIN");

	}

	@Override
	public ResponseEntity<?> updateUser(UserDto dto, @RequestHeader("Authorization") String authorization) {
		String email = getEmail(authorization);
		email = email.toLowerCase();
		if(dto.getRole().equals("USER") || dto.getRole().equals("ADMIN")) {
		UserModel existingUser =repo.findByEmail(dto.getEmail());
		if( existingUser != null) {
			if(existingUser.getRole().equals("ADMIN") && repo.existsByEmailAndRole(email,"ADMIN")) {
				throw new ForbidenActionException("As an ADMIN you can only update USERs");
			}
			
			if( dto.getRole().equals("ADMIN") && repo.existsByEmailAndRole(email,"ADMIN")) {
				throw new ForbidenActionException("As an ADMIN you can not award other USERs the status of ADMIN");
			}
			if(existingUser.getRole().equals("ADMIN") &&  dto.getRole().equals("USER")) {
				try {
					bankAccountProxy.createBankAccount(dto.getEmail().toLowerCase());
				} catch (FeignException e) {
						if(e.status() != 404) {
							throw new ServiceUnavailableException("Bank account service is unavailable");
						}
					throw new NoDataFoundException(e.getMessage());
				}				
				try {
					cryptoWalletProxy.createCryptoWallet(dto.getEmail().toLowerCase());
				}catch (FeignException e) {
					throw new ServiceUnavailableException("Crypto wallet  service is unavailable");
				}
			}
			if(existingUser.getRole().equals("USER") &&  dto.getRole().equals("ADMIN")) {
				try {
					bankAccountProxy.deleteBankAccount(existingUser.getEmail());
				}catch (FeignException e) {
					throw new ServiceUnavailableException("Bank account service is unavailable");
				}
				try {
					cryptoWalletProxy.deleteCryptoWallet(existingUser.getEmail());
				}catch (FeignException e) {
					throw new ServiceUnavailableException("Crypto wallet  service is unavailable");
				}
			}
			
			repo.updateUser(dto.getEmail(), dto.getPassword(), dto.getRole());
			
			return ResponseEntity.status(200).body(dto);
		}
			throw new NoDataFoundException("User with forwarded email does not exist");	
		}
		throw new InvalidRequestException("Role must be either USER or ADMIN");
	}
	
	public UserModel convertDtoToModel(UserDto dto) {
		return new UserModel(dto.getEmail(), dto.getPassword(), dto.getRole());
	}

	public UserDto convertModelToDto(UserModel model) {
		return new UserDto(model.getEmail(), model.getPassword(), model.getRole());
	}

	@Override
	public ResponseEntity<?> deleteUser(int id) {
		UserModel existingUser =repo.findById(id);
		if(existingUser.getRole().equals("USER")) {
			try {
				bankAccountProxy.deleteBankAccount(existingUser.getEmail());
			}catch (FeignException e) {
				throw new ServiceUnavailableException("Bank account service is unavailable");
			}
			try {
				cryptoWalletProxy.deleteCryptoWallet(existingUser.getEmail());
			}catch (FeignException e) {
				throw new ServiceUnavailableException("Crypto wallet  service is unavailable");
			}
		}
		
		if (existingUser != null) {
			if(existingUser.getRole().equals("OWNER")) {
				throw new InvalidRequestException("You cant delete the OWNER, he stays!");
			}
            repo.deleteById(id);
            return ResponseEntity.status(200).body("User deleted successfully");
        } else {
        	throw new NoDataFoundException("User not found with id " + id);
        }
	}
	
	
	private String getEmail(String authorization) {
		String base64Credentials = authorization.substring("Basic".length()).trim();
		byte[] decoded = Base64.getDecoder().decode(base64Credentials);
		String credentials = new String(decoded, StandardCharsets.UTF_8);
		String[] emailPassword = credentials.split(":", 2);
		String email = emailPassword[0];
		return email;
	}
}