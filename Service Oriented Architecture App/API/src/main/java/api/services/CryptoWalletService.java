package api.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import api.dtos.CryptoWalletDto;

public interface CryptoWalletService {

	@GetMapping("/crypto-wallet")
	List<CryptoWalletDto> getCryptoWallets();

	@GetMapping("/crypto-wallet/email/{email}")
	public ResponseEntity<?> getCryptoWalletByEmail(@PathVariable("email") String email);
	
	@GetMapping("/crypto-wallet/myWallet")
	public ResponseEntity<?> getMyCryptoWallet(@RequestHeader("Authorization") String authorization);
	
	@GetMapping("/crypto-wallet/amount")
	public BigDecimal getAvailableAmount(@RequestParam(value= "email") String email, @RequestParam(value= "from") String from);
	
	
	@PostMapping("/crypto-wallet/create")
	public ResponseEntity<?> createCryptoWallet(@RequestBody CryptoWalletDto bankAccountDto); 
	
	@PostMapping("/crypto-wallet/create/{email}")
	public ResponseEntity<?> createCryptoWallet(@PathVariable("email") String email);
	
	@PutMapping("/crypto-wallet/update")
	public ResponseEntity<?> updateCryptoWallet(@RequestBody CryptoWalletDto updatedCryptoWallet);
	
	@PutMapping("/crypto-wallet")
    ResponseEntity<CryptoWalletDto> updateCryptoWallet(@RequestParam("from") String from, @RequestParam("to") String to, @RequestParam("quantity") BigDecimal quantity, @RequestParam("email") String email, @RequestParam("result") BigDecimal result); 
    
	@PutMapping("/crypto-wallet/increase")
	public void increaseFunds(@RequestParam("to") String to, @RequestParam("quantity") BigDecimal quantity, @RequestParam("email") String email);
	
	
	@PutMapping("/crypto-wallet/decrease")
	public void decreaseFunds(@RequestParam("from") String from, @RequestParam("quantity") BigDecimal quantity, @RequestParam("email") String email);
	
	
	@DeleteMapping("/crypto-wallet/id/{id}")
	public ResponseEntity<?> deleteCryptoWallet(@PathVariable("id") int id);
	
	@DeleteMapping("/crypto-wallet/delete/{email}")
	public void deleteCryptoWallet(@PathVariable("email") String email);
}
