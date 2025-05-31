package cryptoWallet.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CryptoWalletModel {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(unique=true, nullable = false)
	private String email;
	
	

    @Column
	private BigDecimal BTC_amount;

    @Column
    private BigDecimal ETH_amount;

    @Column
    private BigDecimal BNB_amount;
	
	
	
	public CryptoWalletModel() {
	
	}

	public CryptoWalletModel(String email) {
		super();
		this.email = email;
		BTC_amount = BigDecimal.valueOf(0);
		ETH_amount = BigDecimal.valueOf(0);
		BNB_amount = BigDecimal.valueOf(0);
	}
	
	
	public CryptoWalletModel(String email,BigDecimal bTC_amount, BigDecimal eTH_amount, BigDecimal bNB_amount) {
		super();
		this.email = email;
		BTC_amount = bTC_amount;
		ETH_amount = eTH_amount;
		BNB_amount = bNB_amount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getBTC_amount() {
		return BTC_amount;
	}

	public void setBTC_amount(BigDecimal bTC_amount) {
		BTC_amount = bTC_amount;
	}

	public BigDecimal getETH_amount() {
		return ETH_amount;
	}

	public void setETH_amount(BigDecimal eTH_amount) {
		ETH_amount = eTH_amount;
	}

	public BigDecimal getBNB_amount() {
		return BNB_amount;
	}

	public void setBNB_amount(BigDecimal bNB_amount) {
		BNB_amount = bNB_amount;
	}


	
}

