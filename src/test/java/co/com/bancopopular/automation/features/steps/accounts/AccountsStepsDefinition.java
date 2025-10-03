package co.com.bancopopular.automation.features.steps.accounts;

import co.com.bancopopular.automation.conf.StepBase;
import co.com.bancopopular.automation.rest.requests.SearchRequests;
import co.com.bancopopular.automation.utils.DataUserInstance;
import co.com.bancopopular.automation.utils.RequestsUtil;
import co.com.bancopopular.automation.utils.SessionHelper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;

import java.net.MalformedURLException;

import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;

public class AccountsStepsDefinition extends StepBase {

  AccountsRequests accountsRequests = new AccountsRequests();
  SearchRequests searchRequests = new SearchRequests();
  RequestsUtil requestsUtil = new RequestsUtil();
  private static final String CUSTOMER_DOC_TYPE = "customerDocType";
  private static final String CUSTOMER_DOCUMENT = "customerDocument";

  @Steps
  AccountsAssertions accountsAssertions;

  @When("{word} consulta sus cuentas")
  public void sendRequestAccounts(String actor) throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();
    accountsRequests.getAccounts(data.get(CUSTOMER_DOC_TYPE).getAsString(),
        data.get(CUSTOMER_DOCUMENT).getAsString());
  }

  @When("{word} consulta la titularidad de sus cuentas")
  public void sendRequestAccountList(String actor) throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();
    requestsUtil.getAuthOTP();
    if(getFromSession(SessionHelper.SessionData.OWNERSHIP_REFACTOR_TOGGLE).equals("ON")){
      searchRequests.getAccountsOwnership(data.get(CUSTOMER_DOC_TYPE).getAsString(),
              data.get(CUSTOMER_DOCUMENT).getAsString());
    }
  }

  @Then("{word} puede conocer la información de estas y se le muestra solo {}")
  public void seeAccountsInfo(String actor, String accountType) {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    assertionsUtil.validateJSONSchema("schemas/accounts.json");
    accountsAssertions.verifyAccounts(accountType);
  }

  @Then("{word} {word} tiene cuentas Flexcube que inician con 500")
  public void looksForNativeAccounts(String actor, String nativeFlexcubeAccounts) {
    var hasNativeFlexcubeAccounts = true;
    if (nativeFlexcubeAccounts.equals("no"))
      hasNativeFlexcubeAccounts=false;
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    assertionsUtil.validateJSONSchema("schemas/accounts.json");
    accountsAssertions.verifyNativeAccountsInList(hasNativeFlexcubeAccounts);
  }

  @Then("{word} cliente tiene cuentas derrogadas, bloqueadas y con doble titularidad")
  public void customerWithSizedAccountAndDualOwnershipAccount(String actor){
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    accountsAssertions.verifySeizedBloquedAndDualOwnershipAccounts();
  }

  @Then("{word} cliente tiene cuentas aptas")
  public void customerWithValidAccounts(String actor){
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    accountsAssertions.verifyValidAccounts();
  }

  @Then("se valida que el cliente tiene cuenta en estado doble titularidad")
  public void customerWithDualOwnnershipAccount(){
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    accountsAssertions.verifyDualOwnnershipAccount();
  }

  @Then("se valida que el cliente tiene cuenta en estado bloqueado")
  public void customerWithBloquedAccount(){
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    accountsAssertions.verifyBloquedAccount();
  }

  @Then("{word} cliente tiene cuentas derrogadas, bloqueadas y con doble titularidad y el flujo es no exitoso")
  public void customerWithSizedAccountAndDualOwnershipAccountFailed(String actor){
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    accountsAssertions.verifySeizedBloquedAndDualOwnershipAccountsFailed();
  }

  @Then("{word} cliente no tiene cuentas Flexcube y el flujo es no exitoso")
  public void customerWithoutFlexcubeAccounts(String actor){
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    accountsAssertions.verifyWithoutFlexcubeAccounts();
  }

  @Then("se obtiene error técnico y el flujo es no exitoso")
  public void customerWithTechnicalError(){
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    accountsAssertions.verifyTechnicalErrorOwnership();
  }

  @Then("{word} cliente tiene cuentas sin la estructura correcta y el flujo es no exitoso")
  public void customerWithUnstructuredAccountFailed(String actor){
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    accountsAssertions.verifyUnstructuredAccountFailed();
  }
}
