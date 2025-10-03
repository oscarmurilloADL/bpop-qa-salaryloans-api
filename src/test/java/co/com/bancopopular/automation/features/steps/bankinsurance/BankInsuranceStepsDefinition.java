package co.com.bancopopular.automation.features.steps.bankinsurance;

import co.com.bancopopular.automation.conf.StepBase;
import co.com.bancopopular.automation.exceptions.UserNotFoundException;
import co.com.bancopopular.automation.utils.DataUserInstance;
import co.com.bancopopular.automation.utils.RequestsUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.actors.OnStage;

import java.io.IOException;
import java.net.MalformedURLException;

public class BankInsuranceStepsDefinition extends StepBase {

  BankInsuranceRequests bankInsuranceRequests = new BankInsuranceRequests();
  BankInsuranceAssertions bankInsuranceAssertions = new BankInsuranceAssertions();
  RequestsUtil requestsUtil = new RequestsUtil();
  private static final String TOKEN = "token";

  @Given("los {} para los planes bancaseguros")
  public void termsConditionsBankInsurance(String actor) throws MalformedURLException {
    OnStage.theActorCalled(actor);
    bankInsuranceRequests.getTermsConditionsToken();
  }

  @When("{word} selecciona las ofertas de banca seguros")
  public void getOferrings(String actor) throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();
    var otpJson = requestsUtil.createAuthOTP();
    bankInsuranceRequests.getOffering(data.get("customerDocType").getAsString(),
        data.get("customerDocument").getAsString(), data.get("obligationID").getAsString(),
        data.get("disbursementAmount").getAsString(), otpJson.getString(TOKEN));
  }

  @When("{word} selecciona las ofertas de banca seguros en CNC")
  public void getOferringsForCNC(String actor) throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();
    bankInsuranceRequests.getOfferingForCNC(data.get("customerDocType").getAsString(),
            data.get("customerDocument").getAsString(), data.get("obligationID").getAsString(),
            data.get("disbursementAmount").getAsString(), data.get("clientType").getAsString(),
            data.get("feeNumber").getAsString(), data.get("sectorNumber").getAsString());
  }

  @When("se consulta el plan {word}")
  public void showTermsAndConditions(String plan) throws MalformedURLException {
    bankInsuranceRequests.getTermsAndConditions(plan,
            SerenityRest.then().extract().body().jsonPath().get("access_token"));
  }

  @Then("se muestran correctamente")
  public void showCorrectly() throws UserNotFoundException {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    assertionsUtil.validateJSONSchema("schemas/bankinsurance.json");
    bankInsuranceAssertions.verifyBankInsuranceEducation();
  }

  @Then("se muestran los valores correctamente sector Pensionado hasta 69 años con prima {word} meses")
  public void showCorrectlyBankInsurancePensioner69(String months) throws UserNotFoundException {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    bankInsuranceAssertions.verifyBankInsurancePensioner69(months);
  }

  @Then("se muestran los valores correctamente sector Pensionado mayor 70 años con prima {word} meses")
  public void showCorrectlyBankInsurance12Pensioner70(String months) throws UserNotFoundException {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    bankInsuranceAssertions.verifyBankInsurancePensioner70(months);
  }

  @Then("no se muestra oferta en CNC")
  public void showCorrectlyBankInsurancePensionerOver81CNC() throws UserNotFoundException {
    assertionsUtil.shouldSeeInternalErrorStatusCode();
    bankInsuranceAssertions.verifyBankInsurancePensionerover81CNC();
  }

  @Then("se muestran los valores correctamente sector Pensionado 70 a 81 años CNC")
  public void showCorrectlyBankInsurancePensioner70to81CNC() throws UserNotFoundException {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    bankInsuranceAssertions.verifyBankInsurancePensioner70To81CNC();
  }

  @Then("se muestran los valores correctamente sector Pensionado menor a 69 años CNC")
  public void showCorrectlyBankInsurancePensionerBelow69CNC() throws UserNotFoundException {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    bankInsuranceAssertions.verifyBankInsurancePensioner69("48");
  }

  @Then("se informa que no hay oferta para bancaseguros")
  public void withoutBankinsuranceOffer(){
    assertionsUtil.shouldSeeInternalErrorStatusCode();
    bankInsuranceAssertions.verifyWithoutBankininsuranceOffer();
    assertionsUtil.validateJSONSchema("schemas/non_viable_customer.json");
  }

  @Then("se muestran los valores correctamente sector educativo 18-40 años con prima {word} meses")
  public void showCorrectlyBankInsuranceEducational40(String months) throws UserNotFoundException {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    bankInsuranceAssertions.verifyBankInsuranceEducational40(months);
  }

  @Then("se muestran los valores correctamente sector educativo 56-65 años con prima {word} meses")
  public void showCorrectlyBankInsuranceEducational56(String months) throws UserNotFoundException {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    bankInsuranceAssertions.verifyBankInsuranceEducational56(months);
  }

  @Then("se muestran los valores correspondientes")
  public void showCorrespondingValues() throws IOException {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    bankInsuranceAssertions.verifyTermsConditions();
  }

}