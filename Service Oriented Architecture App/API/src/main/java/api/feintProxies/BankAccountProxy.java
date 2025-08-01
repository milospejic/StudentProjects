package api.feintProxies;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import api.dtos.BankAccountDto;

@org.springframework.cloud.openfeign.FeignClient("bank-account")
public interface BankAccountProxy {

	
	
	@GetMapping("/bank-account/amount")
	public BigDecimal getAvailableAmount(@RequestParam(value= "email") String email, @RequestParam(value= "from") String from);
	
	@GetMapping("/bank-account/email/{email}")
	public ResponseEntity<?> getBankAccountByEmail(@PathVariable("email") String email);
	
	@PostMapping("/bank-account/create/{email}")
	public ResponseEntity<?> createBankAccount(@PathVariable("email") String email);
	
	@PutMapping("/bank-account")
    ResponseEntity<BankAccountDto> updateBankAccount(@RequestParam("from") String from, @RequestParam("to") String to, @RequestParam("quantity") BigDecimal quantity, @RequestParam("email") String email, @RequestParam("result") BigDecimal result); 
	
	@PutMapping("/bank-account/increase")
	public void increaseFunds(@RequestParam("to") String to, @RequestParam("quantity") BigDecimal quantity, @RequestParam("email") String email);
	
	
	@PutMapping("/bank-account/decrease")
	public void decreaseFunds(@RequestParam("from") String from, @RequestParam("quantity") BigDecimal quantity, @RequestParam("email") String email);
	
	
	@DeleteMapping("/bank-account/delete/{email}")
	public void deleteBankAccount(@PathVariable("email") String email);
}
