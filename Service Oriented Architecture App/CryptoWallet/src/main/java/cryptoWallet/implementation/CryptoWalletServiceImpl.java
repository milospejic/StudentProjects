package cryptoWallet.implementation;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import api.dtos.CryptoWalletDto;
import api.dtos.UserDto;
import api.feintProxies.UsersProxy;
import api.services.CryptoWalletService;
import cryptoWallet.model.CryptoWalletModel;
import cryptoWallet.repository.CryptoWalletRepository;
import feign.FeignException;
import jakarta.transaction.Transactional;
import util.exceptions.EntityAlreadyExistsException;
import util.exceptions.InvalidRequestException;
import util.exceptions.NoDataFoundException;
import util.exceptions.ServiceUnavailableException;

@RestController
public class CryptoWalletServiceImpl implements CryptoWalletService{

	@Autowired
	private CryptoWalletRepository repo;
	
	@Autowired
	private UsersProxy userProxy;

	
	@Override
	public List<CryptoWalletDto> getCryptoWallets() {
		List<CryptoWalletModel> listOfModels = repo.findAll();
		ArrayList<CryptoWalletDto> listOfDtos = new ArrayList<CryptoWalletDto>();
		for(CryptoWalletModel model: listOfModels) {
			listOfDtos.add(convertModelToDto(model));
		}
		return listOfDtos;
	}
	
	@Override
	public  ResponseEntity<?> getCryptoWalletByEmail(String email) {
		if(repo.existsByEmail(email)) {
			CryptoWalletDto dto = convertModelToDto(repo.findByEmail(email));
			return ResponseEntity.status(200).body(dto);
		}
			throw new NoDataFoundException("Crypto wallet not found with email " + email);
	}

	@Override
	public ResponseEntity<?> createCryptoWallet(CryptoWalletDto cryptoWalletDto) {
		try {
			if(userProxy.getUserByEmail(cryptoWalletDto.getEmail()).getBody() != null) {
				if(repo.findByEmail(cryptoWalletDto.getEmail()) == null) {
					if(cryptoWalletDto.getBTC_amount().compareTo(BigDecimal.valueOf(0)) < 0) {
						throw new InvalidRequestException("BTC amount should be 0 or more!");
					}else if(cryptoWalletDto.getETH_amount().compareTo(BigDecimal.valueOf(0)) < 0) {
						throw new InvalidRequestException("ETH amount should be 0 or more!");
					}else if(cryptoWalletDto.getBNB_amount().compareTo(BigDecimal.valueOf(0)) < 0) {
						throw new InvalidRequestException("BNB amount should be 0 or more!");
					}
					CryptoWalletModel cryptoWalletModel = new CryptoWalletModel(cryptoWalletDto.getEmail(),cryptoWalletDto.getBTC_amount(),cryptoWalletDto.getETH_amount(),cryptoWalletDto.getBNB_amount());
					return ResponseEntity.status(201).body(repo.save(cryptoWalletModel));
				}else {
					throw new EntityAlreadyExistsException("Crypto wallet already exists with email " + cryptoWalletDto.getEmail());
				}
				
			}else {
				throw new NoDataFoundException("User not found with email " + cryptoWalletDto.getEmail());
			}
		}catch (FeignException e) {
			if(e.status() == 503) {
				throw new ServiceUnavailableException("User service is unavailable");
			}
			throw new NoDataFoundException(e.getMessage());
		}
	}
	
	@Override
	public ResponseEntity<?> createCryptoWallet(String email) {
		
				CryptoWalletModel cryptoWalletModel = new CryptoWalletModel(email);
				return ResponseEntity.status(201).body(repo.save(cryptoWalletModel));
	}

	@Override
	public ResponseEntity<?> updateCryptoWallet(CryptoWalletDto updatedCryptoWallet) {
		if(repo.findByEmail(updatedCryptoWallet.getEmail()) != null) {
			if(updatedCryptoWallet.getBTC_amount().compareTo(BigDecimal.valueOf(0)) < 0) {
				throw new InvalidRequestException("BTC amount should be 0 or more!");
			}else if(updatedCryptoWallet.getETH_amount().compareTo(BigDecimal.valueOf(0)) < 0) {
				throw new InvalidRequestException("ETH amount should be 0 or more!");
			}else if(updatedCryptoWallet.getBNB_amount().compareTo(BigDecimal.valueOf(0)) < 0) {
				throw new InvalidRequestException("BNB amount should be 0 or more!");
			}
			repo.updateCryptoWallet(updatedCryptoWallet.getEmail(), updatedCryptoWallet.getBTC_amount(),updatedCryptoWallet.getETH_amount(), updatedCryptoWallet.getBNB_amount());
			return ResponseEntity.status(200).body(updatedCryptoWallet);
		}
		throw new NoDataFoundException("Crypto wallet not found with email " + updatedCryptoWallet.getEmail());
	}

	@Override
	public ResponseEntity<?> deleteCryptoWallet(int id) {
		if(repo.existsById(id)) {
			repo.deleteById(id);
            return ResponseEntity.status(200).body("Crypto wallet deleted successfully");
		}
		throw new NoDataFoundException("Crypto wallet not found with id: " + id);

	}
	
	@Override
	@Transactional
	public void deleteCryptoWallet(String email) {
		CryptoWalletModel account = repo.findByEmail(email);
		if (account != null) {
			repo.deleteByEmail(email);
		}		
	}
	
	public CryptoWalletModel convertDtoToModel(CryptoWalletDto dto) {
		return new CryptoWalletModel(dto.getEmail(), dto.getBTC_amount(), dto.getETH_amount(),dto.getBNB_amount());
	}

	public CryptoWalletDto convertModelToDto(CryptoWalletModel model) {
		return new CryptoWalletDto(model.getEmail(), model.getBTC_amount(), model.getETH_amount(), model.getBNB_amount());
	}

	@Override
	public BigDecimal getAvailableAmount(String email, String from) {
		
		CryptoWalletModel cryptoWallet = repo.findByEmail(email);
		String fromCurrency = from.toLowerCase();

		if(fromCurrency.equals("btc")) {
			return cryptoWallet.getBTC_amount();
		}else if (fromCurrency.equals("eth")) {
			return cryptoWallet.getETH_amount();
		}else if (fromCurrency.equals("bnb")) {
			return cryptoWallet.getBNB_amount();
		}else {
			return BigDecimal.valueOf(-1);
		}
	}

	@Override
	public ResponseEntity<CryptoWalletDto> updateCryptoWallet(String from, String to, BigDecimal quantity, String email,
			BigDecimal result) {
		CryptoWalletModel cryptoWallet = repo.findByEmail(email);
		if (cryptoWallet != null) {
			String fromCurrency = from.toLowerCase();
		    String toCurrency = to.toLowerCase();

		    if ("btc".equals(fromCurrency)) {
		        cryptoWallet.setBTC_amount(cryptoWallet.getBTC_amount().subtract(quantity));
		    } else if ("eth".equals(fromCurrency)) {
		        cryptoWallet.setETH_amount(cryptoWallet.getETH_amount().subtract(quantity));
		    } else if ("bnb".equals(fromCurrency)) {
		        cryptoWallet.setBNB_amount(cryptoWallet.getBNB_amount().subtract(quantity));
		    } else {
		        throw new InvalidRequestException("Not an valid FROM currency");
		    }

		    if ("btc".equals(toCurrency)) {
		        cryptoWallet.setBTC_amount(cryptoWallet.getBTC_amount().add(result));
		    } else if ("eth".equals(toCurrency)) {
		        cryptoWallet.setETH_amount(cryptoWallet.getETH_amount().add(result));
		    } else if ("bnb".equals(toCurrency)) {
		        cryptoWallet.setBNB_amount(cryptoWallet.getBNB_amount().add(result));
		    } else {
		    	throw new InvalidRequestException("Not an valid TO currency");
		    }

		    repo.save(cryptoWallet);

		    CryptoWalletDto dto = convertModelToDto(cryptoWallet);
		    return ResponseEntity.ok(dto);
		}
		throw new NoDataFoundException("Crypto wallet with forwarded email not found");
	}

	@Override
	public void increaseFunds(String to, BigDecimal quantity, String email) {
		CryptoWalletModel cryptoWallet = repo.findByEmail(email);
		if (cryptoWallet != null) {
		    String toCurrency = to.toLowerCase();

		    if ("btc".equals(toCurrency)) {
		        cryptoWallet.setBTC_amount(cryptoWallet.getBTC_amount().add(quantity));
		    } else if ("eth".equals(toCurrency)) {
		        cryptoWallet.setETH_amount(cryptoWallet.getETH_amount().add(quantity));
		    } else if ("bnb".equals(toCurrency)) {
		        cryptoWallet.setBNB_amount(cryptoWallet.getETH_amount().add(quantity));
		    } 

		    repo.save(cryptoWallet);
		}
	}

	@Override
	public void decreaseFunds(String from, BigDecimal quantity, String email) {
		CryptoWalletModel cryptoWallet = repo.findByEmail(email);
		if (cryptoWallet != null) {
			String fromCurrency = from.toLowerCase();

		    if ("btc".equals(fromCurrency)) {
		        cryptoWallet.setBTC_amount(cryptoWallet.getBTC_amount().subtract(quantity));
		    } else if ("eth".equals(fromCurrency)) {
		        cryptoWallet.setETH_amount(cryptoWallet.getETH_amount().subtract(quantity));
		    } else if ("bnb".equals(fromCurrency)) {
		        cryptoWallet.setBNB_amount(cryptoWallet.getETH_amount().subtract(quantity));
		    } 

		    repo.save(cryptoWallet);
		}
	}
	
	@Override
	public ResponseEntity<?> getMyCryptoWallet(String authorization) {
		String email = getEmail(authorization);
		if(repo.existsByEmail(email)) {
			return ResponseEntity.status(200).body(convertModelToDto(repo.findByEmail(email)));
		}else {
			throw new NoDataFoundException("No wallet found for your email address");
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
