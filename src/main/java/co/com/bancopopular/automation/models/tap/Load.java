package co.com.bancopopular.automation.models.tap;

import co.com.bancopopular.automation.models.phonemodel.DataPension;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Load {

	@JsonProperty("journeyId")
	private String journeyId;

	@JsonProperty("data")
	private DataPension data;

	@JsonProperty("dataToCorrect")
	private String dataToCorrect;

	@JsonProperty("documentName")
	private  String documentName;

	@JsonProperty("documentType")
	private String documentType;

	@JsonProperty("fileData")
	private FileData fileData;

	@JsonProperty("loanData")
	private LoanData loanData;

}