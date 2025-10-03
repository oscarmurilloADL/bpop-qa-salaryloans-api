package co.com.bancopopular.automation.models.mdm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MDM {

	@JsonProperty("lastName")
	private String lastName;

	@JsonProperty("secondLastName")
	private String secondLastName;

	@JsonProperty("occupation")
	private String occupation;

	@JsonProperty("peopleInChargeOf")
	private String peopleInChargeOf;

	@JsonProperty("validAccount")
	private boolean validAccount;

	@JsonProperty("cityId")
	private String cityId;

	@JsonProperty("countryId")
	private String countryId;

	@JsonProperty("cellNumber")
	private String cellNumber;

	@JsonProperty("products")
	private Object products;

	@JsonProperty("streetName")
	private Object streetName;

	@JsonProperty("customerType")
	private String customerType;

	@JsonProperty("assets")
	private int assets;

	@JsonProperty("cityName")
	private String cityName;

	@JsonProperty("monthlyExpenses")
	private int monthlyExpenses;

	@JsonProperty("economicActivityType")
	private Object economicActivityType;

	@JsonProperty("educationLevelCod")
	private Object educationLevelCod;

	@JsonProperty("identityType")
	private String identityType;

	@JsonProperty("identityNumber")
	private String identityNumber;

	@JsonProperty("additionalIncome")
	private int additionalIncome;

	@JsonProperty("cellNumberUsageType")
	private String cellNumberUsageType;

	@JsonProperty("email")
	private String email;

	@JsonProperty("monthlyIncome")
	private int monthlyIncome;

	@JsonProperty("datePhoneUpdate")
	private String datePhoneUpdate;

	@JsonProperty("streetType")
	private String streetType;

	@JsonProperty("streetNumber")
	private Object streetNumber;

	@JsonProperty("economicActivity")
	private Object economicActivity;

	@JsonProperty("professionCode")
	private Object professionCode;

	@JsonProperty("stateId")
	private String stateId;

	@JsonProperty("fullName")
	private String fullName;

	@JsonProperty("birthDate")
	private String birthDate;

	@JsonProperty("debts")
	private int debts;

	@JsonProperty("phoneNumber")
	private String phoneNumber;

	@JsonProperty("complementAddress")
	private String complementAddress;

	@JsonProperty("maritalStatusDesc")
	private Object maritalStatusDesc;

	@JsonProperty("name")
	private String name;

	@JsonProperty("fullAddress")
	private String fullAddress;

	@JsonProperty("validPhoneNumberCRM")
	private boolean validPhoneNumberCRM;

	@JsonProperty("middleName")
	private String middleName;

	@JsonProperty("countryName")
	private Object countryName;

	@JsonProperty("stateProvName")
	private Object stateProvName;

	@JsonProperty("maritalStatus")
	private String maritalStatus;
}