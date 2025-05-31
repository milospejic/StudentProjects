package bankAccount.implementation;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import api.dtos.BankAccountDto;
import api.dtos.UserDto;
import api.feintProxies.UsersProxy;
import api.services.BankAccountService;
import bankAccount.model.BankAccountModel;
import bankAccount.repository.BankAccountRepository;
import feign.FeignException;
import jakarta.transaction.Transactional;
import util.exceptions.EntityAlreadyExistsException;
import util.exceptions.InvalidRequestException;
import util.exceptions.NoDataFoundException;
import util.exceptions.ServiceUnavailableException;

@RestController
public class BankAccountServiceImpl implements BankAccountService{

	@Autowired
	private BankAccountRepository repo;
	
	@Autowired
	private UsersProxy userProxy;

	
	@Override
	public List<BankAccountDto> getBankAccounts() {
		List<BankAccountModel> listOfModels = repo.findAll();
		ArrayList<BankAccountDto> listOfDtos = new ArrayList<BankAccountDto>();
		for(BankAccountModel model: listOfModels) {
			listOfDtos.add(convertModelToDto(model));
		}
		return listOfDtos;
	}
	
	@Override
	public  ResponseEntity<?> getBankAccountByEmail(String email) {
		if(repo.existsByEmail(email)) {
			BankAccountDto dto = convertModelToDto(repo.findByEmail(email));
			return ResponseEntity.status(200).body(dto);
		}
			throw new NoDataFoundException("Bank account not found with email " + email);
	}

	@Override
	public ResponseEntity<?> createBankAccount(BankAccountDto bankAccountDto) {
		try {
			if(userProxy.getUserByEmail(bankAccountDto.getEmail()).getBody() != null) {
				if(repo.findByEmail(bankAccountDto.getEmail()) == null) {
					if(bankAccountDto.getRSD_amount().compareTo(BigDecimal.valueOf(0)) < 0) {
						throw new InvalidRequestException("RSD amount should be 0 or more!");
					}else if(bankAccountDto.getEUR_amount().compareTo(BigDecimal.valueOf(0)) < 0) {
						throw new InvalidRequestException("EUR amount should be 0 or more!");
					}else if(bankAccountDto.getGBP_amount().compareTo(BigDecimal.valueOf(0)) < 0) {
						throw new InvalidRequestException("GBP amount should be 0 or more!");
					}else if(bankAccountDto.getUSD_amount().compareTo(BigDecimal.valueOf(0)) < 0) {
						throw new InvalidRequestException("USD amount should be 0 or more!");
					}else if(bankAccountDto.getCHF_amount().compareTo(BigDecimal.valueOf(0)) < 0) {
						throw new InvalidRequestException("CHF amount should be 0 or more!");
					}
					BankAccountModel bankAccountModel = new BankAccountModel(bankAccountDto.getEmail(),bankAccountDto.getRSD_amount(),bankAccountDto.getEUR_amount(),bankAccountDto.getCHF_amount(),bankAccountDto.getGBP_amount(),bankAccountDto.getUSD_amount());
					return ResponseEntity.status(201).body(repo.save(bankAccountModel));
				}else {
					throw new EntityAlreadyExistsException("Bank account already exists with email " + bankAccountDto.getEmail());
				}
				
			}else {
				throw new NoDataFoundException("User not found with email " + bankAccountDto.getEmail());
			}
		}catch (FeignException e) {
			if(e.status() == 503) {
				throw new ServiceUnavailableException("User service is unavailable");
			}
			throw new NoDataFoundException(e.getMessage());
		}
	}
	
	@Override
	public ResponseEntity<?> createBankAccount(String email) {
		
				BankAccountModel bankAccountModel = new BankAccountModel(email);
				return ResponseEntity.status(201).body(repo.save(bankAccountModel));
	}

	@Override
	public ResponseEntity<?> updateBankAccount(BankAccountDto updatedBankAccount) {
		if(repo.findByEmail(updatedBankAccount.getEmail()) != null) {
			if(updatedBankAccount.getRSD_amount().compareTo(BigDecimal.valueOf(0)) < 0) {
				throw new InvalidRequestException("RSD amount should be 0 or more!");
			}else if(updatedBankAccount.getEUR_amount().compareTo(BigDecimal.valueOf(0)) < 0) {
				throw new InvalidRequestException("EUR amount should be 0 or more!");
			}else if(updatedBankAccount.getGBP_amount().compareTo(BigDecimal.valueOf(0)) < 0) {
				throw new InvalidRequestException("GBP amount should be 0 or more!");
			}else if(updatedBankAccount.getUSD_amount().compareTo(BigDecimal.valueOf(0)) < 0) {
				throw new InvalidRequestException("USD amount should be 0 or more!");
			}else if(updatedBankAccount.getCHF_amount().compareTo(BigDecimal.valueOf(0)) < 0) {
				throw new InvalidRequestException("CHF amount should be 0 or more!");
			}
			repo.updateBankAccount(updatedBankAccount.getEmail(), updatedBankAccount.getRSD_amount(),updatedBankAccount.getGBP_amount(), updatedBankAccount.getEUR_amount(), updatedBankAccount.getUSD_amount(), updatedBankAccount.getCHF_amount());
			return ResponseEntity.status(200).body(updatedBankAccount);
		}
		throw new NoDataFoundException("Bank account not found with email " + updatedBankAccount.getEmail());
	}

	@Override
	public ResponseEntity<?> deleteBankAccount(int id) {
		if(repo.existsById(id)) {
			repo.deleteById(id);
            return ResponseEntity.status(200).body("Bank account deleted successfully");
		}
		throw new NoDataFoundException("Bank account not found with id: " + id);

	}
	
	@Override
	@Transactional
	public void deleteBankAccount(String email) {
		BankAccountModel account = repo.findByEmail(email);
		if (account != null) {
			repo.deleteByEmail(email);
		}		
	}
	
	public BankAccountModel convertDtoToModel(BankAccountDto dto) {
		return new BankAccountModel(dto.getEmail(), dto.getRSD_amount(), dto.getEUR_amount(),dto.getCHF_amount(), dto.getUSD_amount(), dto.getGBP_amount());
	}

	public BankAccountDto convertModelToDto(BankAccountModel model) {
		return new BankAccountDto(model.getEmail(), model.getRSD_amount(), model.getEUR_amount(), model.getCHF_amount(),model.getGBP_amount(), model.getUSD_amount());
	}

	@Override
	public BigDecimal getAvailableAmount(String email, String from) {
		
		BankAccountModel bankAccount = repo.findByEmail(email);
		String fromCurrency = from.toLowerCase();

		if(fromCurrency.equals("rsd")) {
			return bankAccount.getRSD_amount();
		}else if (fromCurrency.equals("gbp")) {
			return bankAccount.getGBP_amount();
		}else if (fromCurrency.equals("eur")) {
			return bankAccount.getEUR_amount();
		}else if (fromCurrency.equals("usd")) {
			return bankAccount.getUSD_amount();
		}else if (fromCurrency.equals("chf")) {
			return bankAccount.getCHF_amount();
		}else {
			return BigDecimal.valueOf(-1);
		}
	}

	@Override
	public ResponseEntity<BankAccountDto> updateBankAccount(String from, String to, BigDecimal quantity, String email,
			BigDecimal result) {
		BankAccountModel bankAccount = repo.findByEmail(email);
		if (bankAccount != null) {
			String fromCurrency = from.toLowerCase();
		    String toCurrency = to.toLowerCase();

		    if ("rsd".equals(fromCurrency)) {
		        bankAccount.setRSD_amount(bankAccount.getRSD_amount().subtract(quantity));
		    } else if ("usd".equals(fromCurrency)) {
		        bankAccount.setUSD_amount(bankAccount.getUSD_amount().subtract(quantity));
		    } else if ("gbp".equals(fromCurrency)) {
		        bankAccount.setGBP_amount(bankAccount.getGBP_amount().subtract(quantity));
		    } else if ("chf".equals(fromCurrency)) {
		        bankAccount.setCHF_amount(bankAccount.getCHF_amount().subtract(quantity));
		    } else if ("eur".equals(fromCurrency)) {
		        bankAccount.setEUR_amount(bankAccount.getEUR_amount().subtract(quantity));
		    } else {
		    	throw new InvalidRequestException("Not an valid FROM currency");
		    }

		    if ("rsd".equals(toCurrency)) {
		        bankAccount.setRSD_amount(bankAccount.getRSD_amount().add(result));
		    } else if ("usd".equals(toCurrency)) {
		        bankAccount.setUSD_amount(bankAccount.getUSD_amount().add(result));
		    } else if ("gbp".equals(toCurrency)) {
		        bankAccount.setGBP_amount(bankAccount.getGBP_amount().add(result));
		    } else if ("chf".equals(toCurrency)) {
		        bankAccount.setCHF_amount(bankAccount.getCHF_amount().add(result));
		    } else if ("eur".equals(toCurrency)) {
		        bankAccount.setEUR_amount(bankAccount.getEUR_amount().add(result));
		    } else {
		    	throw new InvalidRequestException("Not an valid TO currency");
		    }

		    repo.save(bankAccount);

		    BankAccountDto dto = convertModelToDto(bankAccount);
		    return ResponseEntity.ok(dto);
		}
		throw new NoDataFoundException("Bank account with forwarded email not found");
	}

	@Override
	public void increaseFunds(String to, BigDecimal quantity, String email) {
		BankAccountModel bankAccount = repo.findByEmail(email);
		if (bankAccount != null) {
		    String toCurrency = to.toLowerCase();

		    if ("rsd".equals(toCurrency)) {
		        bankAccount.setRSD_amount(bankAccount.getRSD_amount().add(quantity));
		    } else if ("usd".equals(toCurrency)) {
		        bankAccount.setUSD_amount(bankAccount.getUSD_amount().add(quantity));
		    } else if ("gbp".equals(toCurrency)) {
		        bankAccount.setGBP_amount(bankAccount.getGBP_amount().add(quantity));
		    } else if ("chf".equals(toCurrency)) {
		        bankAccount.setCHF_amount(bankAccount.getCHF_amount().add(quantity));
		    } else if ("eur".equals(toCurrency)) {
		        bankAccount.setEUR_amount(bankAccount.getEUR_amount().add(quantity));
		    } 

		    repo.save(bankAccount);
		}
	}

	@Override
	public void decreaseFunds(String from, BigDecimal quantity, String email) {
		BankAccountModel bankAccount = repo.findByEmail(email);
		if (bankAccount != null) {
			String fromCurrency = from.toLowerCase();

		    if ("rsd".equals(fromCurrency)) {
		        bankAccount.setRSD_amount(bankAccount.getRSD_amount().subtract(quantity));
		    } else if ("usd".equals(fromCurrency)) {
		        bankAccount.setUSD_amount(bankAccount.getUSD_amount().subtract(quantity));
		    } else if ("gbp".equals(fromCurrency)) {
		        bankAccount.setGBP_amount(bankAccount.getGBP_amount().subtract(quantity));
		    } else if ("chf".equals(fromCurrency)) {
		        bankAccount.setCHF_amount(bankAccount.getCHF_amount().subtract(quantity));
		    } else if ("eur".equals(fromCurrency)) {
		        bankAccount.setEUR_amount(bankAccount.getEUR_amount().subtract(quantity));
		    } 
		    
		    repo.save(bankAccount);
		}
	}

	@Override
	public ResponseEntity<?> getMyBankAccount(String authorization) {
		String email = getEmail(authorization);
		if(repo.existsByEmail(email)) {
			return ResponseEntity.status(200).body(convertModelToDto(repo.findByEmail(email)));
		}else {
			throw new NoDataFoundException("No account found for your email address");
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
