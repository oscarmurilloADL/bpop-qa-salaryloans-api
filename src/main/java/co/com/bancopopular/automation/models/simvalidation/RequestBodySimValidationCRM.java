package co.com.bancopopular.automation.models.simvalidation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestBodySimValidationCRM{

	@JsonProperty("businessAdvisorDocument")
	private String businessAdvisorDocument;

	@JsonProperty("administrativeOfficeCode")
	private String administrativeOfficeCode;

	@JsonProperty("officeName")
	private String officeName;

	@JsonProperty("advisorJourneyId")
	private String advisorJourneyId;

	@JsonProperty("salesModel")
	private String salesModel;

	@JsonProperty("documentType")
	private String documentType;

	@JsonProperty("documentNumber")
	private String documentNumber;

	@JsonProperty("officeCode")
	private String officeCode;

	@JsonProperty("administrativeOfficeName")
	private String administrativeOfficeName;
}