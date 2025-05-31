package cryptoWallet.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cryptoWallet.model.CryptoWalletModel;
import jakarta.transaction.Transactional;

public interface CryptoWalletRepository extends JpaRepository<CryptoWalletModel, Long> {
	CryptoWalletModel findByEmail(String email);

	boolean existsByEmail(String email);

	void deleteByEmail(String email);

	
	@Modifying
	@Transactional
	@Query("update CryptoWalletModel b set b.BTC_amount = :BTC_amount, b.ETH_amount = :ETH_amount, b.BNB_amount = :BNB_amount where b.email = :email")
	void updateCryptoWallet(@Param("email") String email, @Param("BTC_amount") BigDecimal BTC_amount, @Param("ETH_amount") BigDecimal ETH_amount, @Param("BNB_amount") BigDecimal BNB_amount);

	boolean existsById(int id);

	void deleteById(int id);
	
}
