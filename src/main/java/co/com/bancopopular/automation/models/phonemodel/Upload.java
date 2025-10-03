package co.com.bancopopular.automation.models.phonemodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Upload {

	@JsonProperty("clientType")
	private String clientType;

	@JsonProperty("data")
	private DataPension data;

	@JsonProperty("documentType")
	private String documentType;

	@JsonProperty("documentNumber")
	private String documentNumber;

	@JsonProperty("sectorNumber")
	private String sectorNumber;

	@JsonProperty("subSectorNumber")
	private String subSectorNumber;

	@JsonProperty("obligationId")
	private String obligationId;

	@JsonProperty("payerNit")
	private String payerNit;
}