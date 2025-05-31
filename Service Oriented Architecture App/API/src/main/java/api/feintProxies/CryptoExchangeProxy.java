package api.feintProxies;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import api.dtos.CryptoExchangeDto;

@org.springframework.cloud.openfeign.FeignClient("crypto-exchange")
public interface CryptoExchangeProxy {

	
	@GetMapping("/crypto-exchange")
	ResponseEntity<CryptoExchangeDto> getExchange(@RequestParam(value= "from") String from, @RequestParam(value = "to") String to);
}