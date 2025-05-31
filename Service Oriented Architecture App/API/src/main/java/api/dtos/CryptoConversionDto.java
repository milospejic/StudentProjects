package api.dtos;

import java.math.BigDecimal;

public class CryptoConversionDto {

	private CryptoExchangeDto exchange;
	private BigDecimal quantity;
	private ConversionResult conversionResult;

	public CryptoConversionDto() {

	}

	public CryptoConversionDto(CryptoExchangeDto exchange, BigDecimal quantity, String cryptoTo,
			BigDecimal result) {
		this.exchange = exchange;
		this.quantity = quantity;
		CryptoConversionDto.ConversionResult resultHolder = this.new ConversionResult(cryptoTo, result);
		this.conversionResult = resultHolder;
	}

	public CryptoExchangeDto getExchange() {
		return exchange;
	}

	public void setExchange(CryptoExchangeDto exchange) {
		this.exchange = exchange;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public ConversionResult getConversionResult() {
		return conversionResult;
	}

	public void setConversionResult(ConversionResult conversionResult) {
		this.conversionResult = conversionResult;
	}

	public class ConversionResult {
		private String cryptoTo;
		private BigDecimal result;

		public ConversionResult() {

		}

		public ConversionResult(String cryptoTo, BigDecimal result) {
			this.cryptoTo = cryptoTo;
			this.result = result;
		}

		public String getCryptoTo() {
			return cryptoTo;
		}

		public void setCryptoTo(String cryptoTo) {
			this.cryptoTo = cryptoTo;
		}

		public BigDecimal getResult() {
			return result;
		}

		public void setResult(BigDecimal result) {
			this.result = result;
		}

	}
}