package cryptoConversion;

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

import api.dtos.CryptoWalletDto;
import api.dtos.CryptoConversionDto;
import api.dtos.CryptoExchangeDto;
import api.feintProxies.CryptoWalletProxy;
import api.feintProxies.CryptoExchangeProxy;
import api.feintProxies.UsersProxy;
import api.services.CryptoConversionService;
import util.exceptions.InvalidRequestException;
import util.exceptions.NoDataFoundException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import feign.FeignException;
import util.exceptions.ServiceUnavailableException;

@RestController
public class CryptoConversionServiceImpl implements CryptoConversionService {

	@Autowired
	private CryptoExchangeProxy cryptoExchangeProxy;
	
	
	@Autowired
	private UsersProxy userProxy;
	
	@Autowired
	private CryptoWalletProxy cryptoWalletProxy;
	
	
	
	private RestTemplate template = new RestTemplate();
	
	Retry retry;
	CryptoExchangeDto response;

	public CryptoConversionServiceImpl(RetryRegistry registry) {
		this.retry = registry.retry("default");
	}

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
				availableAmount = cryptoWalletProxy.getAvailableAmount(email, from);
			} catch (FeignException e) {
				if(e.status() != 404) {
					throw new ServiceUnavailableException("Crypto wallet service is unavailable");
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
						throw new ServiceUnavailableException("Crypto exchange service is unavailable");
				}
					throw new NoDataFoundException(e.getMessage());
				}
				CryptoConversionDto cryptoConversionDto = exchangeToConversion(response,quantity);		
				ResponseEntity<CryptoWalletDto> cryptoWalletDto;
				try {
					cryptoWalletDto = cryptoWalletProxy.updateCryptoWallet(from, to, quantity, email, cryptoConversionDto.getConversionResult().getResult());
				} catch (FeignException e) {
					if(e.status() != 404) {
						throw new ServiceUnavailableException("Crypto wallet service is unavailable");
				}
				throw new NoDataFoundException(e.getMessage());
				}
			
			
				Map<String, Object> responseBody = new HashMap<>();
			    responseBody.put("cryptoWallet", cryptoWalletDto.getBody());
			    responseBody.put("message", "Successfully converted " + from + ": " + quantity + " for " + to + ": " + cryptoConversionDto.getConversionResult().getResult());
			    return ResponseEntity.ok().body(responseBody);
			}else {
				throw new InvalidRequestException("You don't have enough money to exchange!");
			}
		
		
		

	}

	public CryptoExchangeDto acquireExchange(String from, String to) {
		return cryptoExchangeProxy.getExchange(from, to).getBody();
	}

	public ResponseEntity<?> fallback(CallNotPermittedException ex){
		throw new ServiceUnavailableException("Crypto conversion service is unavailable");
	}
	
	
	
	public CryptoConversionDto exchangeToConversion(CryptoExchangeDto exchange,
			BigDecimal quantity) {
		return new CryptoConversionDto(exchange, quantity, exchange.getTo(),
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