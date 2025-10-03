package co.com.bancopopular.automation.features.steps.validations;

import co.com.bancopopular.automation.conf.StepBase;
import co.com.bancopopular.automation.features.steps.payrollhistory.PayrollHistoryRequests;
import co.com.bancopopular.automation.features.steps.transactionaudit.TransactionAuditRequests;
import co.com.bancopopular.automation.rest.requests.ValidationsRequests;
import co.com.bancopopular.automation.utils.DataUserInstance;
import co.com.bancopopular.automation.utils.RequestsUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.net.MalformedURLException;

import net.serenitybdd.annotations.Steps;

public class ValidationsStepsDefinition extends StepBase {

  ValidationsRequests validationsRequests = new ValidationsRequests();
  RequestsUtil requestsUtil = new RequestsUtil();

  private static final String ADVISOR_DOCUMENT = "advisorDocument";
  private static final String CUSTOMER_DOCUMENT = "customerDocument";
  private static final String CUSTOMER_DOC_TYPE = "customerDocType";
  private static final String TOKEN ="token";
  private static final String OFFICE_CODE ="officeCode";

  PayrollHistoryRequests payrollHistoryRequests = new PayrollHistoryRequests();
  TransactionAuditRequests transactionAuditRequests = new TransactionAuditRequests();

  @Steps
  ValidationsAssertions validationsAssertions = new ValidationsAssertions();

  @When("el envía su número de cédula")
  public void sendDocumentNumber() throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();
    var otpJson = requestsUtil.createAuthOTP();

    payrollHistoryRequests.getPayrollStatus(data.get(CUSTOMER_DOC_TYPE).getAsString(),
            data.get(CUSTOMER_DOCUMENT).getAsString(), otpJson.getString(TOKEN));

    validationsRequests.getExternalViabilityStatus(data,otpJson.getString(TOKEN));

    transactionAuditRequests.getTransactionAudit(data.get(ADVISOR_DOCUMENT).getAsString(),
            data.get(CUSTOMER_DOCUMENT).getAsString(), data.get(CUSTOMER_DOC_TYPE).getAsString(),
            data.get(OFFICE_CODE).getAsString(), otpJson.getString(TOKEN));

    validationsRequests.sendValidateBusisnessRules(data.get(CUSTOMER_DOC_TYPE).getAsString(),
            data.get(CUSTOMER_DOCUMENT).getAsString(),data.get("sector").getAsString(),otpJson.getString(TOKEN));
  }

  @Then("se valida {} e {}")
  @Then("se valida {} y {}")
  public void validateSubcausal(String subcausal,String viability){
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    validationsAssertions.verifySubcausal(subcausal,viability);
  }
}