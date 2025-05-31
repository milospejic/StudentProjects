package bankAccount.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import bankAccount.model.BankAccountModel;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;

public interface BankAccountRepository extends JpaRepository<BankAccountModel, Long> {
	BankAccountModel findByEmail(String email);

	boolean existsByEmail(String email);

	void deleteByEmail(String email);

	
	@Modifying
	@Transactional
	@Query("update BankAccountModel b set b.RSD_amount = :RSD_amount, b.GBP_amount = :GBP_amount, b.EUR_amount = :EUR_amount, b.USD_amount = :USD_amount, b.CHF_amount = :CHF_amount where b.email = :email")
	void updateBankAccount(@Param("email") String email, @Param("RSD_amount") BigDecimal RSD_amount, @Param("GBP_amount") BigDecimal GBP_amount,
	                       @Param("EUR_amount") BigDecimal EUR_amount, @Param("USD_amount") BigDecimal USD_amount, @Param("CHF_amount") BigDecimal CHF_amount);

	boolean existsById(int id);

	void deleteById(int id);
	
}
