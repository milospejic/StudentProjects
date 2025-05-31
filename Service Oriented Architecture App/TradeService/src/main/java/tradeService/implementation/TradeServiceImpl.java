package tradeService.implementation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import api.dtos.UserDto;
import api.feintProxies.BankAccountProxy;
import api.feintProxies.CryptoWalletProxy;
import api.feintProxies.CurrencyExchangeProxy;
import api.feintProxies.UsersProxy;
import api.services.TradeService;
import feign.FeignException;
import tradeService.model.TradeModel;
import tradeService.repository.TradeRepository;
import util.exceptions.InvalidRequestException;
import util.exceptions.NoDataFoundException;
import util.exceptions.ServiceUnavailableException;

@RestController
public class TradeServiceImpl implements TradeService {

    @Autowired
    private TradeRepository repo;

    @Autowired
    private BankAccountProxy bankAccountProxy;

    @Autowired
    private CryptoWalletProxy cryptoWalletProxy;

    @Autowired
    private UsersProxy usersProxy;
    
    @Autowired
    private CurrencyExchangeProxy currencyExchangeProxy;

    @Override
    public ResponseEntity<?> getConversion(String from, String to, BigDecimal quantity,  @RequestHeader("Authorization") String authorization) {


    	if(quantity.compareTo(BigDecimal.valueOf(0)) < 0) {
			throw new InvalidRequestException("Quantity must be positive!");
		}
    	
    	String email = getEmail(authorization);
		email = email.toLowerCase();
   
        if (isFiatCurrency(from)) {
        	String originalFrom = from;
        	BigDecimal originalQuantity = quantity;
        	if(isFiatCurrency(to)) {
        		throw new InvalidRequestException("For such an action you have to use CurrencyConversion service");
        	}
        	
        	if(!isCryptoCurrency(to)) {
                throw new InvalidRequestException("Bad TO currency");
        	}
        	
        	 BigDecimal availableAmount;
             
             try {
            	 availableAmount = bankAccountProxy.getAvailableAmount(email, from);
            	 if (availableAmount.compareTo(quantity) < 0) {
                     throw new InvalidRequestException("Insufficient funds in bank account");
                 }
	        	 if(!("USD".equals(from) || "EUR".equals(from))){
	        		
	        		bankAccountProxy.decreaseFunds(from, quantity, email);
	        		try {
	        			quantity = quantity.multiply(currencyExchangeProxy.getExchange(from, "USD").getBody().getExchangeValue());
		        	 }catch (FeignException e) {
		 				if(e.status() != 404) {
		 					throw new ServiceUnavailableException("Currency exchange service is unavailable");
		 				}
		 				throw new NoDataFoundException(e.getMessage());
		             }
	        		from="USD";
	        	 }else {
	        		bankAccountProxy.decreaseFunds(from, quantity, email);
	        	 }
             }catch (FeignException e) {
					if(e.status() != 404) {
						throw new ServiceUnavailableException("Bank account service is unavailable");
					}
					throw new NoDataFoundException(e.getMessage());
			}
        	
            //BigDecimal result = quantity.multiply(repo.findByFromAndTo(from, to).getExchangeValue());
             BigDecimal result = quantity.divide(repo.findByFromAndTo(to, from).getExchangeValue(), 8, RoundingMode.HALF_UP);
             try {
            	
            	cryptoWalletProxy.increaseFunds(to, result, email);

	        } catch (FeignException e) {
				if(e.status() != 404) {
					throw new ServiceUnavailableException("Crypto wallet service is unavailable");
				}
				throw new NoDataFoundException(e.getMessage());
			}
            Map<String, Object> responseBody = new HashMap<>();
		    responseBody.put("cryptoWallet", cryptoWalletProxy.getCryptoWalletByEmail(email).getBody());
		    responseBody.put("message"," Successfully converted " + originalFrom + ": " + originalQuantity + " for " + to + ": " + result);
		    return ResponseEntity.ok().body(responseBody);
        } else if (isCryptoCurrency(from)){
        	
        	String originalTo = to;
        	BigDecimal originalQuantity = quantity;
        	if(isCryptoCurrency(to)) {
                throw new InvalidRequestException("For such an action you have to use CryptoConversion service");
        	}
        	
        	if(!isFiatCurrency(to)) {
                throw new InvalidRequestException("Bad TO currency");
        	}
        	BigDecimal availableAmount;
        	try {
        		availableAmount = cryptoWalletProxy.getAvailableAmount(email, from);
        		if (availableAmount.compareTo(quantity) < 0) {
                    throw new InvalidRequestException("Insufficient funds in crypto wallet");
                }
                
                cryptoWalletProxy.decreaseFunds(from, quantity, email);
	        } catch (FeignException e) {
				if(e.status() != 404) {
					throw new ServiceUnavailableException("Crypto wallet service is unavailable");
				}
				throw new NoDataFoundException(e.getMessage());
			}
        	
            
            
            
            if(!("USD".equals(to) || "EUR".equals(to))){
        		
        		to="USD";
        	}
        		
            BigDecimal result = quantity.multiply(repo.findByFromAndTo(from, to).getExchangeValue());
            if(!("USD".equals(originalTo) || "EUR".equals(originalTo))){
	            try {
	            	result= result.multiply(currencyExchangeProxy.getExchange(to, originalTo).getBody().getExchangeValue());
	            }catch (FeignException e) {
					if(e.status() != 404) {
						throw new ServiceUnavailableException("Currency exchange service is unavailable");
					}
					throw new NoDataFoundException(e.getMessage());
	            }
            }
            try {
            	bankAccountProxy.increaseFunds(originalTo, result, email);
            }catch (FeignException e) {
				if(e.status() != 404) {
					throw new ServiceUnavailableException("Bank account service is unavailable");
				}
				throw new NoDataFoundException(e.getMessage());
            }
            		
            		
            Map<String, Object> responseBody = new HashMap<>();
		    responseBody.put("bankAccount", bankAccountProxy.getBankAccountByEmail(email).getBody());
		    responseBody.put("message", "Successfully converted " + from + ": " + originalQuantity + " for " + originalTo + ": " + result);
		    return ResponseEntity.ok().body(responseBody);
        } else {
            throw new InvalidRequestException("Bad FROM currency");
    	}
    }

    private boolean isFiatCurrency(String currency) {
        return "USD".equals(currency) || "EUR".equals(currency) || "RSD".equals(currency) || "GBP".equals(currency) || "CHF".equals(currency) ;
    }
    
    private boolean isCryptoCurrency(String currency) {
        return "BTC".equals(currency) || "ETH".equals(currency) || "BNB".equals(currency );
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
