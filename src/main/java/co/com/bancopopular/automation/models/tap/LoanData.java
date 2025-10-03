package co.com.bancopopular.automation.models.tap;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoanData {

	@JsonProperty("clientDocumentNumber")
	private String clientDocumentNumber;

	@JsonProperty("clientDocumentType")
	private String clientDocumentType;

	@JsonProperty("payerNit")
	private String payerNit;

	@JsonProperty("payrollPayer")
	private String payrollPayer;

	@JsonProperty("sectorNumber")
	private String sectorNumber;

}