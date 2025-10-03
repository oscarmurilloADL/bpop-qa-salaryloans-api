package co.com.bancopopular.automation.models.tap;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StatusBody{

	@JsonProperty("documentType")
	private String documentType;

	@JsonProperty("documentNumber")
	private String documentNumber;

	@JsonProperty("providerId")
	private List<String> providerId;

	@JsonProperty("sectorNumber")
	private String sectorNumber;

	@JsonProperty("subSectorNumber")
	private String subSectorNumber;

	@JsonProperty("obligationId")
	private String obligationId;

	@JsonProperty("payerNit")
	private String payerNit;

	@JsonProperty("onbaseCodes")
	private List<String> onbaseCodes;

	@JsonProperty("payerName")
	private String payerName;

	@JsonProperty("sectorName")
	private String sectorName;
	
}