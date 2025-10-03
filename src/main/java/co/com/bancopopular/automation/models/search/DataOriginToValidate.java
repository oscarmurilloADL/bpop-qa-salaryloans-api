package co.com.bancopopular.automation.models.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DataOriginToValidate{

	@JsonProperty("officeName")
	private String officeName;

	@JsonProperty("salesModel")
	private String salesModel;

	@JsonProperty("administrativeOffice")
	private String administrativeOffice;

	@JsonProperty("payerUniqueName")
	private String payerUniqueName;

	@JsonProperty("customerDocType")
	private String customerDocType;

	@JsonProperty("advisorDocument")
	private String advisorDocument;

	@JsonProperty("customerDocument")
	private String customerDocument;

	@JsonProperty("officeCode")
	private String officeCode;

	@JsonProperty("dataResponse")
	private DataResponse dataResponse;

	@JsonProperty("obligationID")
	private String obligationID;

	@JsonProperty("administrativeOfficeName")
	private String administrativeOfficeName;
}