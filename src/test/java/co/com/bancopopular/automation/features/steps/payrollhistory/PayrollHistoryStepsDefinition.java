package co.com.bancopopular.automation.features.steps.payrollhistory;

import co.com.bancopopular.automation.conf.StepBase;
import co.com.bancopopular.automation.rest.requests.SearchRequests;
import co.com.bancopopular.automation.utils.DataUserInstance;
import co.com.bancopopular.automation.utils.RequestsUtil;
import co.com.bancopopular.automation.utils.SessionHelper;
import co.com.bancopopular.automation.utils.WprCipher;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.net.MalformedURLException;

import io.restassured.path.json.JsonPath;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.annotations.Steps;

import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static co.com.bancopopular.automation.utils.SessionHelper.setInSession;

public class PayrollHistoryStepsDefinition extends StepBase {

  @Steps
  PayrollHistoryAssertions payrollHistoryAssertions;

  PayrollHistoryRequests payrollHistoryRequests = new PayrollHistoryRequests();
  RequestsUtil requestsUtil = new RequestsUtil();

  SearchRequests searchRequests = new SearchRequests();

  private static final String CUSTOMER_DOCUMENT ="customerDocument";

  private static final String CUSTOMER_DOC_TYPE="customerDocType";

  @When("{word} consulta el estado de su libranza")
  public void consultPayrollStatus(String actor) throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();
    var otpJson = requestsUtil.createAuthOTP();

    if(data.get(CUSTOMER_DOCUMENT).getAsString().equals("400030") &&
            getFromSession(SessionHelper.SessionData.OWNERSHIP_REFACTOR_TOGGLE).equals("ON")){
      searchRequests.getAccountsOwnership(data.get(CUSTOMER_DOC_TYPE).getAsString(),
              data.get(CUSTOMER_DOCUMENT).getAsString());
    }else {
      payrollHistoryRequests.getPayrollStatus(data.get(CUSTOMER_DOC_TYPE).getAsString(),
              data.get(CUSTOMER_DOCUMENT).getAsString(), otpJson.getString("token"));
      var encryptedBody = SerenityRest.then().extract().body().asString();
      JsonPath payrollStatus = WprCipher.decryptRequest(encryptedBody);
      setInSession(SessionHelper.SessionData.PAYROLL_STATUS,payrollStatus);

    }
  }

  @Then("su libranza debe ser válida")
  public void payrollStatusValid() {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    payrollHistoryAssertions.verifyApproveStatus();
  }

  @Then("está en base de huellas")
  public void bureauTrailValid() {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    payrollHistoryAssertions.verifyClientInBaseBureauTrail();
  }

  @Then("está en base de huellas con extractor")
  public void bureauTrailValidExtractor() {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    payrollHistoryAssertions.verifyClientInBaseBureauTrailExtractor();
  }

  @Then("no está en base de huellas")
  public void bureauTrailInvalid() {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    payrollHistoryAssertions.verifyClientIsNotBaseBureauTrail();
  }

  @When("{word} consulta la información de su libranza")
  public void consultPayrollInfo(String actor) throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();
    payrollHistoryRequests.getPayrollInfo(data.get(CUSTOMER_DOC_TYPE).getAsString(),
        data.get(CUSTOMER_DOCUMENT).getAsString());
  }

  @Then("{word} puede ver su información")
  public void payrollInfoValid(String actor) {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    assertionsUtil.validateJSONSchema("schemas/payrollinfo.json");
  }

  @Then("su libranza no cumple condiciones misma cuota")
  public void loanGrowthsIsLessThanTenPercent() {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    var data = DataUserInstance.getInstance().getData();
    payrollHistoryAssertions
        .verifyLoanNotConditionSameFee(data.get("payerUniqueName").getAsString());
  }

  @Then("el tipo de pago no es por nómina para su libranza activa y desembolsada")
  public void typeOfPaymentIsNotPayroll() {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    payrollHistoryAssertions.verifyClientWithActivePayrollLoanNotPaymentByPayrollAndDisbursements();
  }

  @Then("su libranza no puede ser tramitada por ADL")
  public void typeOfPaymentIsNotADL() {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    payrollHistoryAssertions.verifyClientPaymentNotIncluded();
  }

  @Then("no se activa oferta para libranzas en estado desembolsado y cancelado")
  public void noOffersDisbursedPayrollAndCanceledPayroll() {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    payrollHistoryAssertions.verifyClientWithDisbursedPayrollCanceledPayrollAndActivePayroll();
  }

  @Then("no se activa oferta para cliente con cuenta BP en estado embargada")
  public void noOffersClientSeizureAccountBP() {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    payrollHistoryAssertions.verifyClientSeizureAccountBP();
  }

  @Then("no se activa oferta para cliente con máximo de edad excedida")
  public void noOffersClientNoGrowthPolicy() {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    payrollHistoryAssertions.verifyClientMaxAgeExceeded();
  }
}