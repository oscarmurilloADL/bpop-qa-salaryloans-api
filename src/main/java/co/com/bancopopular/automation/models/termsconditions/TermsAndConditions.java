package co.com.bancopopular.automation.models.termsconditions;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TermsAndConditions {

	@JsonProperty("onBaseDocuments")
	List<OnBaseDocumentsItem> onBaseDocuments;

	@JsonProperty("maxCoverage")
	Boolean maxCoverage;

	@JsonProperty("accountType")
	String accountType;

	@JsonProperty("totalDiscounts")
	Integer totalDiscounts;

	@JsonProperty("officeCode")
	Integer officeCode;

	@JsonProperty("identificationType")
	String identificationType;

	@JsonProperty("accountNumber")
	String accountNumber;

	@JsonProperty("generatedId")
	Integer generatedId;

	@JsonProperty("insuranceCode")
	String insuranceCode;

	@JsonProperty("creditStudy")
	Integer creditStudy;

	@JsonProperty("adjustmentInterests")
	Integer adjustmentInterests;

	@JsonProperty("sellerIdNumber")
	Integer sellerIdNumber;

	@JsonProperty("accidentInsurancePrice")
	String accidentInsurancePrice;

	@JsonProperty("identificationNumber")
	String identificationNumber;

	@JsonProperty("adjustmentInsurancePremium")
	Integer adjustmentInsurancePremium;

	@JsonProperty("coverageSpouse")
	Boolean coverageSpouse;

	@JsonProperty("idCase")
	String idCase;

	@JsonProperty("validationMethod")
	String validationMethod;


}