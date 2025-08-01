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

import api.dtos.BankAccountDto;

public interface BankAccountService {
	
	@GetMapping("/bank-account")
	List<BankAccountDto> getBankAccounts();

	@GetMapping("/bank-account/email/{email}")
	public ResponseEntity<?> getBankAccountByEmail(@PathVariable String email);
	
	@GetMapping("/bank-account/myAccount")
	public ResponseEntity<?> getMyBankAccount(@RequestHeader("Authorization") String authorization);
	
	@GetMapping("/bank-account/amount")
	public BigDecimal getAvailableAmount(@RequestParam(value= "email") String email, @RequestParam(value= "from") String from);
	
	
	@PostMapping("/bank-account/create")
	public ResponseEntity<?> createBankAccount(@RequestBody BankAccountDto bankAccountDto); 
	
	@PostMapping("/bank-account/create/{email}")
	public ResponseEntity<?> createBankAccount(@PathVariable String email);
	
	@PutMapping("/bank-account/update")
	public ResponseEntity<?> updateBankAccount(@RequestBody BankAccountDto updatedBankAccount);
	
	@PutMapping("/bank-account")
    ResponseEntity<BankAccountDto> updateBankAccount(@RequestParam("from") String from, @RequestParam("to") String to, @RequestParam("quantity") BigDecimal quantity, @RequestParam("email") String email, @RequestParam("result") BigDecimal result); 
    
	@PutMapping("/bank-account/increase")
	public void increaseFunds(@RequestParam("to") String to, @RequestParam("quantity") BigDecimal quantity, @RequestParam("email") String email);
	
	
	@PutMapping("/bank-account/decrease")
	public void decreaseFunds(@RequestParam("from") String from, @RequestParam("quantity") BigDecimal quantity, @RequestParam("email") String email);
	
	
	@DeleteMapping("/bank-account/id/{id}")
	public ResponseEntity<?> deleteBankAccount(@PathVariable int id);
	
	@DeleteMapping("/bank-account/delete/{email}")
	public void deleteBankAccount(@PathVariable String email);
}
