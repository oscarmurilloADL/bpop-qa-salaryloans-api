package co.com.bancopopular.automation.features.steps.payrollhistory;

import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import co.com.bancopopular.automation.utils.SessionHelper;
import co.com.bancopopular.automation.utils.WprCipher;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.annotations.Step;
import static org.hamcrest.Matchers.anyOf;

public class PayrollHistoryAssertions {

  private static final String APPROVE = "APPROVE";
  private static final String EXTRACTOR = "PAYER_WITH_EXTRACTOR";
  private static final String OBLIGATION_ID = "obligationId";
  private static final String LOAN_RENEW_VALIDATION = "loanRenewValidation";
  private static final String CUSTOMER_IN_BUREAU = "customerInBureauTrail";
  private static final String MAX_AGE_EXCEEDED_MESSAGE = "NOT_APPROVE_MAX_AGE_EXCEEDED";

  private static final String OBLIGATION_NUMBER="26003240500070";

  private static final String SECOND_OBLIGATION_NUMBER="48103010500060";
  private static final String FALSE="false";

  @Step("Verificacion datos libranza con estado APPROVE")
  public void verifyApproveStatus() {
    JsonPath payrollStatus = getFromSession(SessionHelper.SessionData.PAYROLL_STATUS);
    assertThat(
            payrollStatus.getList(LOAN_RENEW_VALIDATION).get(0),
        is(APPROVE));
  }

  @Step("Verificacion datos libranza con estado LOAN_NOT_GENERATE_TEN_PERCENTAGE_GROWTH")
  public void verifyLoanNotConditionSameFee(String payerUniqueName) {
    JsonPath payrollStatus = getFromSession(SessionHelper.SessionData.PAYROLL_STATUS);
    assertThat(
            payrollStatus.getList(LOAN_RENEW_VALIDATION).get(0),
        is("LOAN_NOT_GENERATE_TEN_PERCENTAGE_GROWTH"));
    assertThat(payrollStatus.getList("payerUniqueName").get(0),
            is(payerUniqueName));
  }
  
  @Step("Cliente esta en base de huellas")
  public void verifyClientInBaseBureauTrail() {
    JsonPath payrollStatus = getFromSession(SessionHelper.SessionData.PAYROLL_STATUS);
    assertThat(payrollStatus.getList(OBLIGATION_ID).get(0),
            anyOf(is(OBLIGATION_NUMBER), is(SECOND_OBLIGATION_NUMBER)));
    assertThat(
            payrollStatus.getList(LOAN_RENEW_VALIDATION).get(0),
        is(APPROVE));
    assertThat(payrollStatus.getList(CUSTOMER_IN_BUREAU).get(0),
        is(true));
  }

  @Step("Cliente esta en base de huellas con extractor")
  public void verifyClientInBaseBureauTrailExtractor() {
    JsonPath payrollStatus = getFromSession(SessionHelper.SessionData.PAYROLL_STATUS);
    if(getFromSession(SessionHelper.SessionData.EXTRACTOR).equals("")){
      assertThat(payrollStatus.getList(OBLIGATION_ID).get(0),
              anyOf(is(OBLIGATION_NUMBER), is(SECOND_OBLIGATION_NUMBER)));
      assertThat(
              payrollStatus.getList(LOAN_RENEW_VALIDATION).get(0),
              is(APPROVE));
      assertThat(payrollStatus.getList(CUSTOMER_IN_BUREAU).get(0),
              is(true));
    }else {
      assertThat(payrollStatus.getList(OBLIGATION_ID).get(0),
              is(OBLIGATION_NUMBER));
      assertThat(
              payrollStatus.getList(LOAN_RENEW_VALIDATION).get(0),
              is(EXTRACTOR));
      assertThat(payrollStatus.getList(CUSTOMER_IN_BUREAU).get(0),
              is(true));
    }
  }
  
  @Step("Cliente no esta en base de huellas")
  public void verifyClientIsNotBaseBureauTrail() {
    JsonPath payrollStatus = getFromSession(SessionHelper.SessionData.PAYROLL_STATUS);
    assertThat(payrollStatus.getList(CUSTOMER_IN_BUREAU).get(0),
        is(false));
  }
  
  @Step("Cliente con una libranza activa, desembolsada y sin pago por nomina")
  public void verifyClientWithActivePayrollLoanNotPaymentByPayrollAndDisbursements() {
    JsonPath payrollStatus = getFromSession(SessionHelper.SessionData.PAYROLL_STATUS);
    assertThat(payrollStatus.getList(OBLIGATION_ID).get(0),
            anyOf(is("26003240000669"), is(SECOND_OBLIGATION_NUMBER)));
    assertThat(
        payrollStatus.getList(LOAN_RENEW_VALIDATION).get(0),
        is("NOT_APPROVE_BUSINESS_RULES"));
    assertThat(payrollStatus.getList(CUSTOMER_IN_BUREAU).get(0),
        is(true));
    assertThat(payrollStatus.getList(OBLIGATION_ID).get(1),
        is("26003240001669"));
    assertThat(
        payrollStatus.getList(LOAN_RENEW_VALIDATION).get(1),
        is("INVALID_PAYMENT_TYPE"));
    assertThat(payrollStatus.getList(CUSTOMER_IN_BUREAU).get(1),
        is(true));
  }

  @Step("Cliente con pagaduria no incluida en el modelo digital y una libranza en estado Para desembolso")
  public void verifyClientPaymentNotIncluded() {
    JsonPath payrollStatus = getFromSession(SessionHelper.SessionData.PAYROLL_STATUS);
    assertThat(payrollStatus.getList(OBLIGATION_ID).get(0),
            anyOf(is("26003240000609"), is(SECOND_OBLIGATION_NUMBER)));
    assertThat(
        payrollStatus.getList(LOAN_RENEW_VALIDATION).get(0),
        is("NOT_APPROVE_PAYER_NOT_SOPPORTED_ADL"));
    assertThat(payrollStatus.getList(CUSTOMER_IN_BUREAU).get(0),
        is(false));
    assertThat(payrollStatus.getList(OBLIGATION_ID).get(1),
        is("26003240001609"));
    assertThat(
        payrollStatus.getList(LOAN_RENEW_VALIDATION).get(1),
        is("NOT_APPROVE_OTHER_LOAN_IN_PROCESS"));
    assertThat(payrollStatus.getList(CUSTOMER_IN_BUREAU).get(1),
        is(false));
  }

  @Step("Cliente con libranzas en estado de desembolso, cancelado y activo")
  public void verifyClientWithDisbursedPayrollCanceledPayrollAndActivePayroll() {
    JsonPath payrollStatus = getFromSession(SessionHelper.SessionData.PAYROLL_STATUS);
    assertThat(payrollStatus.getList(OBLIGATION_ID).get(0),
            anyOf(is("26003240000665"), is(SECOND_OBLIGATION_NUMBER)));
    assertThat(
        payrollStatus.getList(LOAN_RENEW_VALIDATION).get(0),
        is("NOT_APPROVE_BUSINESS_RULES"));
    assertThat(payrollStatus.getList(CUSTOMER_IN_BUREAU).get(0),
        is(true));
    assertThat(payrollStatus.getList(OBLIGATION_ID).get(1),
        is("26003240000667"));
    assertThat(
        payrollStatus.getList(LOAN_RENEW_VALIDATION).get(1),
        is(APPROVE));
    assertThat(payrollStatus.getList(CUSTOMER_IN_BUREAU).get(1),
        is(true));
  }

  @Step("Cliente con cuenta del banco Popular en estado embargado")
  public void verifyClientSeizureAccountBP() {
    JsonPath payrollStatus = getFromSession(SessionHelper.SessionData.PAYROLL_STATUS);
    if(getFromSession(SessionHelper.SessionData.OWNERSHIP_REFACTOR_TOGGLE).equals("ON")){
      var encryptedBody = SerenityRest.then().extract().body().asString();
      var decryptedBody = WprCipher.decryptRequest(encryptedBody);
      assertThat(decryptedBody.getString("result"),
              is("success"));
      assertThat(decryptedBody.getString("isThereAccounts"),
              is(FALSE));
      assertThat(decryptedBody.getString("isThereTechnicalError"),
              is(FALSE));
      assertThat(decryptedBody.getString("context"),
              is("[numberOfSeizedAccounts:1, numberOfBlockedAccounts:0, numberOfDualOwnershipAccounts:0]"));
    }else{
      assertThat(payrollStatus.getList(OBLIGATION_ID).get(0),
          is("48103010052488"));
      assertThat(
          payrollStatus.getList(LOAN_RENEW_VALIDATION).get(0),
          is("NOT_APPROVE_ACCOUNT_ISSUE"));
      assertThat(payrollStatus.getList(CUSTOMER_IN_BUREAU).get(0),
          is(false));
    }
  }

  @Step("Cliente que excede el máximo de edad permitido")
  public void verifyClientMaxAgeExceeded() {
    JsonPath payrollStatus = getFromSession(SessionHelper.SessionData.PAYROLL_STATUS);
    assertThat(
        payrollStatus.getList(LOAN_RENEW_VALIDATION).get(0),
        is(MAX_AGE_EXCEEDED_MESSAGE));
    assertThat(payrollStatus.getList(CUSTOMER_IN_BUREAU).get(0),
        is(false));
  }
}