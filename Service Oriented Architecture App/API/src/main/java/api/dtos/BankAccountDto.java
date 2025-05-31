package api.dtos;

import java.math.BigDecimal;

public class BankAccountDto {

	
    
    private String email;

    private BigDecimal RSD_amount;

    private BigDecimal EUR_amount;

    private BigDecimal CHF_amount;

    private BigDecimal GBP_amount;

    private BigDecimal USD_amount;

    public BankAccountDto() {

    }
    
    public BankAccountDto(String email, BigDecimal rSD_amount, BigDecimal eUR_amount, BigDecimal cHF_amount,
			BigDecimal gBP_amount, BigDecimal uSD_amount) {
		super();
		this.email = email;
		RSD_amount = rSD_amount;
		EUR_amount = eUR_amount;
		CHF_amount = cHF_amount;
		GBP_amount = gBP_amount;
		USD_amount = uSD_amount;
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

    public void setRSD_amount(BigDecimal RSD_amount) {
        this.RSD_amount = RSD_amount;
    }

    public BigDecimal getEUR_amount() {
        return EUR_amount;
    }

    public void setEUR_amount(BigDecimal EUR_amount) {
        this.EUR_amount = EUR_amount;
    }

    public BigDecimal getCHF_amount() {
        return CHF_amount;
    }

    public void setCHF_amount(BigDecimal CHF_amount) {
        this.CHF_amount = CHF_amount;
    }

    public BigDecimal getGBP_amount() {
        return GBP_amount;
    }

    public void setGBP_amount(BigDecimal GBP_amount) {
        this.GBP_amount = GBP_amount;
    }

    public BigDecimal getUSD_amount() {
        return USD_amount;
    }

    public void setUSD_amount(BigDecimal USD_amount) {
        this.USD_amount = USD_amount;
    }
}