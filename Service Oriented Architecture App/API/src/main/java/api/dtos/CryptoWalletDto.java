package api.dtos;

import java.math.BigDecimal;

public class CryptoWalletDto {
	    private String email;

	    private BigDecimal BTC_amount;

	    private BigDecimal ETH_amount;

	    private BigDecimal BNB_amount;



	    public CryptoWalletDto() {

	    }
	    
	    public CryptoWalletDto(String email, BigDecimal bTC_amount, BigDecimal eTH_amount, BigDecimal bNB_amount) {
			super();
			this.email = email;
			BTC_amount = bTC_amount;
			ETH_amount = eTH_amount;
			BNB_amount = bNB_amount;
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
