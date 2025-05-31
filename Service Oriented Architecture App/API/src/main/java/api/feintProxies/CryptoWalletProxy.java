package api.feintProxies;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import api.dtos.CryptoWalletDto;

@org.springframework.cloud.openfeign.FeignClient("crypto-wallet")
public interface CryptoWalletProxy {

	@GetMapping("/crypto-wallet/amount")
	public BigDecimal getAvailableAmount(@RequestParam(value= "email") String email, @RequestParam(value= "from") String from);
	
	@GetMapping("/crypto-wallet/email/{email}")
	public ResponseEntity<?> getCryptoWalletByEmail(@PathVariable String email);
	
	@PostMapping("/crypto-wallet/create/{email}")
	public ResponseEntity<?> createCryptoWallet(@PathVariable String email);
	
	@PutMapping("/crypto-wallet")
    ResponseEntity<CryptoWalletDto> updateCryptoWallet(@RequestParam("from") String from, @RequestParam("to") String to, @RequestParam("quantity") BigDecimal quantity, @RequestParam("email") String email, @RequestParam("result") BigDecimal result); 
	
	@PutMapping("/crypto-wallet/increase")
	public void increaseFunds(@RequestParam("to") String to, @RequestParam("quantity") BigDecimal quantity, @RequestParam("email") String email);
	
	
	@PutMapping("/crypto-wallet/decrease")
	public void decreaseFunds(@RequestParam("from") String from, @RequestParam("quantity") BigDecimal quantity, @RequestParam("email") String email);
	
	@DeleteMapping("/crypto-wallet/delete/{email}")
	public void deleteCryptoWallet(@PathVariable String email);
}
