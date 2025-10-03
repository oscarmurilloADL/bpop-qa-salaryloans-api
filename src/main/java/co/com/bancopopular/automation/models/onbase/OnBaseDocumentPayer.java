package co.com.bancopopular.automation.models.onbase;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnBaseDocumentPayer {

	@JsonProperty("creditFlowType")
	private String creditFlowType;

	@JsonProperty("documentNumber")
	private String documentNumber;

	@JsonProperty("documentType")
	private String documentType;

	@JsonProperty("idChannel")
	private String idChannel;

	@JsonProperty("journeyId")
	private String journeyId;

	@JsonProperty("obligationId")
	private String obligationId;

	@JsonProperty("openingObligationDate")
	private String openingObligationDate;

	@JsonProperty("payerUniqueName")
	private String payerUniqueName;

	@JsonProperty("salesProcessType")
	private String salesProcessType;

}