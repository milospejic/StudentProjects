package currencyConversion;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import api.dtos.BankAccountDto;
import api.dtos.CurrencyConversionDto;
import api.dtos.CurrencyExchangeDto;
import api.feintProxies.BankAccountProxy;
import api.feintProxies.CurrencyExchangeProxy;
import api.feintProxies.UsersProxy;
import api.services.CurrencyConversionService;
import util.exceptions.InvalidRequestException;
import util.exceptions.NoDataFoundException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import feign.FeignException;
import util.exceptions.ServiceUnavailableException;

@RestController
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

	@Autowired
	private CurrencyExchangeProxy currencyExchangeProxy;
	
	
	@Autowired
	private UsersProxy userProxy;
	
	@Autowired
	private BankAccountProxy bankAccountProxy;
	
	
	
	private RestTemplate template = new RestTemplate();
	
	Retry retry;
	CurrencyExchangeDto response;

	public CurrencyConversionServiceImpl(RetryRegistry registry) {
		this.retry = registry.retry("default");
	}

	/*@Override
	public ResponseEntity<?> getConversion(String from, String to, BigDecimal quantity) {
		HashMap<String,String> uriVariables = new HashMap<String,String>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		
		CurrencyExchangeDto exchange;
		
		try {
			ResponseEntity<CurrencyExchangeDto> response = template.
					getForEntity("http://localhost:8000/currency-exchange?from={from}&to={to}", 
						CurrencyExchangeDto.class, uriVariables);
			
			exchange = response.getBody();
		} catch (HttpClientErrorException e) {
			throw new NoDataFoundException(e.getMessage());
		}
		
		
		return ResponseEntity.ok(exchangeToConversion(exchange, quantity));
	}*/
	@Override
	@CircuitBreaker(name = "cb", fallbackMethod = "fallback")
	public ResponseEntity<?> getConversion(String from, String to, BigDecimal quantity, @RequestHeader("Authorization") String authorization) {
			
			if(quantity.compareTo(BigDecimal.valueOf(0)) < 0) {
				throw new InvalidRequestException("Quantity must be positive!");
			}
				String email = getEmail(authorization);
			email = email.toLowerCase();
			BigDecimal availableAmount;
			try {
			 availableAmount = bankAccountProxy.getAvailableAmount(email, from);
			} catch (FeignException e) {
				if(e.status() != 404) {
					throw new ServiceUnavailableException("Bank account service is unavailable");
				}
				throw new NoDataFoundException(e.getMessage());
			}
			if (availableAmount.compareTo(BigDecimal.valueOf(-1)) == 0) {
				throw new InvalidRequestException("Please enter valid FROM currency!");
			}
			
			if(availableAmount.compareTo(quantity) == 0 || availableAmount.compareTo(quantity) == 1) {
				
				try {
					retry.executeSupplier( () -> response = acquireExchange(from, to));
				} catch (FeignException e) {
					if(e.status() != 404) {
						throw new ServiceUnavailableException("Currency exchange service is unavailable");
					}
					throw new NoDataFoundException(e.getMessage());
				}
				CurrencyConversionDto currencyConversionDto = exchangeToConversion(response,quantity);		
				ResponseEntity<BankAccountDto> bankAccountDto;
				try {
					bankAccountDto = bankAccountProxy.updateBankAccount(from, to, quantity, email, currencyConversionDto.getConversionResult().getResult());
				} catch (FeignException e) {
					if(e.status() != 404) {
						throw new ServiceUnavailableException("Bank account service is unavailable");
					}
					throw new NoDataFoundException(e.getMessage());
				}
				
				Map<String, Object> responseBody = new HashMap<>();
			    responseBody.put("bankAccount", bankAccountDto.getBody());
			    responseBody.put("message", "Successfully converted " + from + ": " + quantity + " for " + to + ": " + currencyConversionDto.getConversionResult().getResult());
			    return ResponseEntity.ok().body(responseBody);
			}else {
				throw new InvalidRequestException("You don't have enough money to exchange!");
			}
		

	}

	public CurrencyExchangeDto acquireExchange(String from, String to) {
		return currencyExchangeProxy.getExchange(from, to).getBody();
	}

	public ResponseEntity<?> fallback(CallNotPermittedException ex){
		throw new ServiceUnavailableException("Currency conversion service is unavailable");
	}
	
	
	
	public CurrencyConversionDto exchangeToConversion(CurrencyExchangeDto exchange,
			BigDecimal quantity) {
		return new CurrencyConversionDto(exchange, quantity, exchange.getTo(),
				quantity.multiply(exchange.getExchangeValue()));

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




