package bankAccount.model;

import jakarta.persistence.Column;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;




@Entity
public class BankAccountModel {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(unique=true, nullable = false)
	private String email;
	
	

    @Column
	private BigDecimal RSD_amount;
	
    @Column
	private BigDecimal EUR_amount;
	
    @Column
	private BigDecimal CHF_amount;
	
    @Column
	private BigDecimal GBP_amount;
	
    @Column
	private BigDecimal USD_amount;
	
	
	public BankAccountModel() {
	
	}

	public BankAccountModel(String email) {
		super();
		this.email = email;
		RSD_amount = BigDecimal.valueOf(0);
		EUR_amount = BigDecimal.valueOf(0);
		CHF_amount = BigDecimal.valueOf(0);
		GBP_amount = BigDecimal.valueOf(0);
		USD_amount = BigDecimal.valueOf(0);
	}
	
	
	public BankAccountModel(String email,BigDecimal rSD_amount, BigDecimal eUR_amount, BigDecimal cHF_amount,
			BigDecimal gBP_amount, BigDecimal uSD_amount) {
		super();
		this.email = email;
		RSD_amount = rSD_amount;
		EUR_amount = eUR_amount;
		CHF_amount = cHF_amount;
		GBP_amount = gBP_amount;
		USD_amount = uSD_amount;
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

	public BigDecimal getRSD_amount() {
		return RSD_amount;
	}

	public void setRSD_amount(BigDecimal rSD_amount) {
		RSD_amount = rSD_amount;
	}

	public BigDecimal getEUR_amount() {
		return EUR_amount;
	}

	public void setEUR_amount(BigDecimal eUR_amount) {
		EUR_amount = eUR_amount;
	}

	public BigDecimal getCHF_amount() {
		return CHF_amount;
	}

	public void setCHF_amount(BigDecimal cHF_amount) {
		CHF_amount = cHF_amount;
	}

	public BigDecimal getGBP_amount() {
		return GBP_amount;
	}

	public void setGBP_amount(BigDecimal gBP_amount) {
		GBP_amount = gBP_amount;
	}

	public BigDecimal getUSD_amount() {
		return USD_amount;
	}

	public void setUSD_amount(BigDecimal uSD_amount) {
		USD_amount = uSD_amount;
	}
	
}
