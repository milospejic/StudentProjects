package api.feintProxies;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import api.dtos.CurrencyExchangeDto;

@org.springframework.cloud.openfeign.FeignClient("currency-exchange")
public interface CurrencyExchangeProxy {

	
	@GetMapping("/currency-exchange")
	ResponseEntity<CurrencyExchangeDto> getExchange(@RequestParam(value= "from") String from, @RequestParam(value = "to") String to);
}
