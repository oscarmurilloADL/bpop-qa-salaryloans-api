package co.com.bancopopular.automation.features.steps.renewal;

import co.com.bancopopular.automation.conf.StepBase;
import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.features.steps.accept.AcceptRequests;
import co.com.bancopopular.automation.features.steps.auth.AuthRequests;
import co.com.bancopopular.automation.features.steps.bankinsurance.BankInsuranceRequests;
import co.com.bancopopular.automation.features.steps.buros.BuroRequests;
import co.com.bancopopular.automation.features.steps.crm.CrmRequests;
import co.com.bancopopular.automation.features.steps.tap.LaboralCertificationRequests;
import co.com.bancopopular.automation.features.steps.event.feature.EventRequests;
import co.com.bancopopular.automation.features.steps.lifeinsurance.LifeInsuranceRequests;
import co.com.bancopopular.automation.features.steps.mdm.MdmRequest;
import co.com.bancopopular.automation.features.steps.payrollhistory.PayrollHistoryRequests;
import co.com.bancopopular.automation.features.steps.sygnus.SygnusRequests;
import co.com.bancopopular.automation.features.steps.tap.PayrollCheckRequests;
import co.com.bancopopular.automation.features.steps.transactionaudit.TransactionAuditRequests;
import co.com.bancopopular.automation.models.mdm.MDM;
import co.com.bancopopular.automation.models.tap.StatusBody;
import co.com.bancopopular.automation.rest.requests.LegalConditionsRequest;
import co.com.bancopopular.automation.rest.requests.ValidationsRequests;
import co.com.bancopopular.automation.utils.DataUserInstance;
import co.com.bancopopular.automation.utils.RequestsUtil;
import co.com.bancopopular.automation.utils.SessionHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import net.serenitybdd.screenplay.rest.questions.TheResponseStatusCode;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static co.com.bancopopular.automation.constants.Constants.CONDITIONS_TOKEN;
import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static co.com.bancopopular.automation.utils.SessionHelper.setInSession;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.equalTo;
import com.fasterxml.jackson.core.type.TypeReference;

public class RenewalStepsDefinition extends StepBase {

  RenewalRequests renewalRequests = new RenewalRequests();
  PayrollCheckRequests payrollCheckRequests = new PayrollCheckRequests();
  CrmRequests crmRequests = new CrmRequests();
  MdmRequest mdmRequest = new MdmRequest();
  BuroRequests buroRequests = new BuroRequests();
  BankInsuranceRequests bankInsuranceRequests = new BankInsuranceRequests();
  AcceptRequests acceptRequests = new AcceptRequests();
  RequestsUtil requestsUtil = new RequestsUtil();
  AuthRequests authRequests = new AuthRequests();
  PayrollHistoryRequests payrollHistoryRequests = new PayrollHistoryRequests();
  TransactionAuditRequests transactionAuditRequests = new TransactionAuditRequests();
  EventRequests eventRequests = new EventRequests();
  LifeInsuranceRequests lifeInsuranceRequests = new LifeInsuranceRequests();
  SygnusRequests sygnusRequests = new SygnusRequests();
  RenewalAssertions renewalAssertions = new RenewalAssertions();
  LaboralCertificationRequests fileRequest = new LaboralCertificationRequests();
  LegalConditionsRequest legalConditionsRequest = new LegalConditionsRequest();
  ValidationsRequests validationsRequests = new ValidationsRequests();
  private String renewalId;
  private static final String ADVISOR_DOCUMENT = "advisorDocument";
  private static final String OFFICE_CODE = "officeCode";
  private static final String CUSTOMER_DOC_TYPE = "customerDocType";
  private static final String CUSTOMER_DOCUMENT = "customerDocument";
  private static final String OBLIGATION_ID = "obligationID";
  private static final String ORDINARY_MOD = "ORDINARY";
  private static final String SAME_FEE_PROCESS_TYPE = "SAME_FEE";
  private static final String ANY_FEE_PROCESS_TYPE = "ANY_FEE";
  private static final String SYGNUS_PROCESS_TYPE = "SYGNUS";
  private static final String MODALITY_TYPE_LOYALTY = "LOYALTY";
  private static final String DISBURSEMENT_AMOUNT = "disbursementAmount";
  private static final String INSURANCE_CODE = "120901";
  private static final String EVENT_CONDITIONS = "SELECT_LOAN_CONDITIONS";
  private static final String TOKEN = "token";
  private static final String GENERATED_ID = "generatedId";
  private static final String PROCESS_ID = "processId";
  private static final String PROVIDER_ID = "providerId";

  @When("{word} solicita una novación")
  public void getRenewalSameFee(String actor) throws MalformedURLException, JsonProcessingException {
    renewalProcess(SAME_FEE_PROCESS_TYPE, true, false);
  }


  @When("{word} solicita una novación tipo Sygnus")
  public void renewalSygnus(String actor) throws MalformedURLException, JsonProcessingException {
    renewalProcess(SYGNUS_PROCESS_TYPE, false, false);
  }

  @When("{word} solicita una novación por toda cuota")
  public void getRenewalAnyFee(String actor) throws MalformedURLException, JsonProcessingException {
    renewalProcess(ANY_FEE_PROCESS_TYPE, false, false);
  }

  @When("{word} solicita una novación por toda cuota con validacion de modalidad loyalty")
  public void getRenewalAnyFeeWithLoyaltyValidation(String actor) throws MalformedURLException, JsonProcessingException {
    renewalProcess(ANY_FEE_PROCESS_TYPE, false, true);
  }

  @When("{word} solicita una novación misma cuota con fidelización")
  public void getRenewalSameFeeLoyalty(String actor) throws MalformedURLException, JsonProcessingException {
    renewalProcess(SAME_FEE_PROCESS_TYPE, true, false);
  }

  @When("{word} solicita una novación toda cuota con fidelización")
  public void getRenewalAnyFeeLoyalty(String actor) throws MalformedURLException, JsonProcessingException {
    renewalProcess(ANY_FEE_PROCESS_TYPE, true, false);
  }

  @When("{word} intenta solicitar una novación toda cuota")
  public void tryToGetRenewalAnyFeeLoyalty(String actor) throws MalformedURLException, InterruptedException, JsonProcessingException {
    tryRenewalProcess();
  }

  @Then("la pre aprobación es exitosa")
  public void successfulApprove() throws IOException, SQLException {
    renewalRequests.getRenewalStatus(renewalId, getFromSession(SessionHelper.SessionData.OBLIGATION_ID), getFromSession(SessionHelper.SessionData.TOKEN));
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    renewalRequests.getLoadDocuments();
    renewalAssertions.verifyEmptyDocuments();
  }

  @Then("la pre aprobación es fallida por política de crecimiento")
  public void failedApproveForGrowthPolicy() {
    renewalAssertions.verifyGrowthAmountPolicyError();
  }

  @Then("se muestra un pop-up de desborde para no permitirle continuar por el canal digital")
  public void failedApproveByDigitalChannel() {
    OnStage.theActorInTheSpotlight().should(
            seeThat(new TheResponseStatusCode(), equalTo(HttpStatus.SC_INTERNAL_SERVER_ERROR)));
    renewalAssertions.verifyValueIsNegative();

  }

  public void renewalProcess(String renewalProcess, boolean isloyalty, boolean loyaltyValidation) throws MalformedURLException, JsonProcessingException {
    var tryNumber = 1;
    var data = DataUserInstance.getInstance().getData();
    var otpJson = requestsUtil.createAuthOTP();
    var tokenLifeInsurance = validationsRequests.getTokenExternalViability(CONDITIONS_TOKEN);
    OnStage.theActorInTheSpotlight().remember(Constants.RQ_ID_GENERATED, otpJson.getInt(GENERATED_ID));
    setInSession(SessionHelper.SessionData.REQUEST_ID, otpJson.getInt(GENERATED_ID));
    setInSession(SessionHelper.SessionData.TOKEN, otpJson.getString(TOKEN));
    setInSession(SessionHelper.SessionData.OBLIGATION_ID, data.get(OBLIGATION_ID).getAsString());

    payrollHistoryRequests.getPayrollStatus(data.get(CUSTOMER_DOC_TYPE).getAsString(),
            data.get(CUSTOMER_DOCUMENT).getAsString(), otpJson.getString(TOKEN));
    if (renewalProcess.equals(ANY_FEE_PROCESS_TYPE))
      renewalForAnyFee(data, loyaltyValidation, otpJson, tryNumber);
    else if (renewalProcess.equals(SYGNUS_PROCESS_TYPE)) {
      renewalForSygnus(data, otpJson);
      renewalProcess = ANY_FEE_PROCESS_TYPE;
    }

    transactionAuditRequests.getTransactionAudit(data.get(ADVISOR_DOCUMENT).getAsString(),
            data.get(CUSTOMER_DOCUMENT).getAsString(), data.get(CUSTOMER_DOC_TYPE).getAsString(),
            data.get(OFFICE_CODE).getAsString(), otpJson.getString(TOKEN));
    if (getFromSession(SessionHelper.SessionData.PROCESS_DATA).equals(Constants.CRM)) {
      crmRequests.updateCustomer(data.get(CUSTOMER_DOC_TYPE).getAsString(),
              data.get(CUSTOMER_DOCUMENT).getAsString(),
              otpJson.getString(TOKEN));
    } else {
      var objectMapper = new ObjectMapper();
      var bodyRequestUpdateMDM = objectMapper.readValue(data.get("bodyRequestUpdate").toString(), new TypeReference<MDM>() {
      });
      mdmRequest.updateCustomerMDM(bodyRequestUpdateMDM, mdmRequest.headersMDM(otpJson.getString(TOKEN)));
    }

    buroRequests.getBuroInfo(data, ORDINARY_MOD, renewalProcess, otpJson.getString(TOKEN));
    if (renewalProcess.equals(SAME_FEE_PROCESS_TYPE)) {
      renewalRequests.startNewSimulation(data.get(CUSTOMER_DOC_TYPE).getAsString(),
              data.get(CUSTOMER_DOCUMENT).getAsString(), data.get(OBLIGATION_ID).getAsString(),
              (long) data.get("requiredAmount").getAsInt(), data.get("loanTerm").getAsInt(),
              ORDINARY_MOD, renewalProcess);
      renewalRequests.getOfferInfo(otpJson.getString(TOKEN));
    }
    if (isloyalty) {
      renewalRequests.startNewSimulation(data.get(CUSTOMER_DOC_TYPE).getAsString(),
              data.get(CUSTOMER_DOCUMENT).getAsString(), data.get(OBLIGATION_ID).getAsString(),
              0L, 0,
              MODALITY_TYPE_LOYALTY, renewalProcess);
      renewalRequests.getOfferInfo(otpJson.getString(TOKEN));
      renewalRequests.startNewSimulation(data.get(CUSTOMER_DOC_TYPE).getAsString(),
              data.get(CUSTOMER_DOCUMENT).getAsString(), data.get(OBLIGATION_ID).getAsString(),
              (long) data.get("requiredAmountFid").getAsInt(), data.get("loanTermFid").getAsInt(),
              MODALITY_TYPE_LOYALTY, renewalProcess);
      renewalRequests.getOfferInfo(otpJson.getString(TOKEN));

    }
    eventRequests.getDataEvent(data.get(CUSTOMER_DOCUMENT).getAsString(),
            data.get(CUSTOMER_DOC_TYPE).getAsString(), EVENT_CONDITIONS, "Valor: $ 1.000.000, Plazo: 110, Cuota: 120989, Sí, es mi crédito",
            otpJson.getString(TOKEN), data.get(Constants.NAME).getAsString());
    bankInsuranceRequests.getOffering(data.get(CUSTOMER_DOC_TYPE).getAsString(),
            data.get(CUSTOMER_DOCUMENT).getAsString(), data.get(OBLIGATION_ID).getAsString(),
            data.get(DISBURSEMENT_AMOUNT).getAsString(), otpJson.getString(TOKEN));
    lifeInsuranceRequests.getDataLifeInsurance(data.get(CUSTOMER_DOCUMENT).getAsString(),
            data.get(CUSTOMER_DOC_TYPE).getAsString(), tokenLifeInsurance);
    transactionAuditRequests.sendTransactionAuditAnalytics(data, otpJson.getString(TOKEN),
            "Aceptó términos y condiciones", Constants.ACCEPT_TERMS_AND_CONDITIONS);
    acceptRequests.acceptCreditData(data.get(CUSTOMER_DOC_TYPE).getAsString(),
            data.get(CUSTOMER_DOCUMENT).getAsString(), data.get(OBLIGATION_ID).getAsString(), otpJson.getString(TOKEN));
    List<Object> onBaseDocuments = new ArrayList<>();
    renewalRequests.getDocumentsPayer(data, getFromSession(SessionHelper.SessionData.JOURNEY_ID),
            otpJson.getString(TOKEN));
    legalConditionsRequest.sendTermsAndConditions(data, otpJson,onBaseDocuments);
    authRequests.generateConfirmationOTP(data.get(OBLIGATION_ID).getAsString(), "sms",
            data.get(CUSTOMER_DOC_TYPE).getAsString(),
            data.get(CUSTOMER_DOCUMENT).getAsString(), otpJson.getString(TOKEN));
    authRequests.validateConfirmationOTP(data.get(CUSTOMER_DOC_TYPE).getAsString(),
            data.get(CUSTOMER_DOCUMENT).getAsString(), otpJson.getString(TOKEN), otpJson.getInt(GENERATED_ID));
    renewalRequests.getRenewal(data, "DDA", INSURANCE_CODE, otpJson.getString(TOKEN), otpJson.getInt(GENERATED_ID),
            data.get(OBLIGATION_ID).getAsString(), onBaseDocuments);
    renewalId = SerenityRest.lastResponse().jsonPath().getString(PROCESS_ID);
    setInSession(SessionHelper.SessionData.RENEWAL_ID, renewalId);
    setInSession(SessionHelper.SessionData.CUSTOMER_ID, data.get(CUSTOMER_DOCUMENT).getAsString());
  }

  public void renewalForAnyFee(JsonObject data, boolean loyaltyValidation, JsonPath otpJson, int tryNumber) throws MalformedURLException, JsonProcessingException {
    if (getFromSession(SessionHelper.SessionData.OPTIMIZATION_TOGGLE_II).equals("")) {
      payrollCheckRequests.sendPayrollCheck(data.get(OBLIGATION_ID).getAsString(),
              data.get(CUSTOMER_DOC_TYPE).getAsString(),
              data.get(CUSTOMER_DOCUMENT).getAsString(),
              otpJson.getString(TOKEN));
      if (loyaltyValidation)
        tryNumber = 10;
      for (var i = 0; i < tryNumber; i++) {
        payrollCheckRequests.checkStatusPayrollCheck(data.get(Constants.ID_TAP).getAsString(),
                data.get(OBLIGATION_ID).getAsString(),
                data.get(CUSTOMER_DOC_TYPE).getAsString(),
                data.get(CUSTOMER_DOCUMENT).getAsString(),
                otpJson.getString(TOKEN));
        if (SerenityRest.then().extract().body().jsonPath().getString("maxLoanTerm") != null)
          break;
      }
    } else {
      payrollHistoryRequests.getPayrollStatus(data.get(CUSTOMER_DOC_TYPE).getAsString(),
              data.get(CUSTOMER_DOCUMENT).getAsString(), otpJson.getString(TOKEN));
      var documents = new ObjectMapper();
      var documentStatus = documents.readValue(data.get(Constants.VALITY_STATUS).toString(), new TypeReference<StatusBody>() {
      });
      fileRequest.getValityStatus(otpJson.getString(TOKEN), documentStatus);
      var finalStatus = documents.readValue(data.get(Constants.STATUS).toString(), new TypeReference<StatusBody>() {
      });
      fileRequest.getStatus(otpJson.getString(TOKEN), finalStatus);
    }
  }

  public void renewalForSygnus(JsonObject data, JsonPath otpJson) throws MalformedURLException, JsonProcessingException {

    OnStage.theActorInTheSpotlight().remember(Constants.RQ_ID_GENERATED, otpJson.getInt(GENERATED_ID));
    setInSession(SessionHelper.SessionData.REQUEST_ID, otpJson.getInt(GENERATED_ID));
    setInSession(SessionHelper.SessionData.TOKEN, otpJson.getString(TOKEN));
    setInSession(SessionHelper.SessionData.OBLIGATION_ID, data.get(OBLIGATION_ID).getAsString());

    sygnusRequests.sygnusPayrollCheckProcess(data, otpJson);

    sygnusRequests.sendPayerSygnus(data.get(CUSTOMER_DOCUMENT).getAsString(),
            data.get(CUSTOMER_DOC_TYPE).getAsString(), data.get("payerName").getAsString(),
            otpJson.getString(TOKEN));
    for (var x = 0; x < 3; x++) {
      sygnusRequests.sendBindingSygnus(data.get(CUSTOMER_DOCUMENT).getAsString(),
              data.get(CUSTOMER_DOC_TYPE).getAsString(), data.get(OBLIGATION_ID).getAsString(), data.get("payerName").getAsString(),
              otpJson.getString(TOKEN), data.get(PROVIDER_ID).getAsString());
      if (SerenityRest.then().extract().statusCode() == HttpStatus.SC_OK) {
        break;
      }
    }
  }

  public void tryRenewalProcess() throws MalformedURLException, InterruptedException, JsonProcessingException {
    var data = DataUserInstance.getInstance().getData();
    var otpJson = requestsUtil.createAuthOTP();
    OnStage.theActorInTheSpotlight().remember(Constants.RQ_ID_GENERATED, otpJson.getInt(GENERATED_ID));
    setInSession(SessionHelper.SessionData.REQUEST_ID, otpJson.getInt(GENERATED_ID));
    setInSession(SessionHelper.SessionData.TOKEN, otpJson.getString(TOKEN));
    setInSession(SessionHelper.SessionData.OBLIGATION_ID, data.get(OBLIGATION_ID).getAsString());
    if(getFromSession(SessionHelper.SessionData.OPTIMIZATION_TOGGLE_II).equals("")) {

      payrollHistoryRequests.getPayrollStatusWithRetry(data.get(CUSTOMER_DOC_TYPE).getAsString(),
              data.get(CUSTOMER_DOCUMENT).getAsString(), otpJson.getString(TOKEN));

      payrollCheckRequests.sendPayrollCheck(data.get(OBLIGATION_ID).getAsString(),
              data.get(CUSTOMER_DOC_TYPE).getAsString(),
              data.get(CUSTOMER_DOCUMENT).getAsString(),
              otpJson.getString(TOKEN));

      Response response = null;

      for (var i = 0; i <= 2; i++) {
        response = OnStage.theActorInTheSpotlight().asksFor(LastResponse.received());
        if (response.getBody().jsonPath().getString("code").equals("GrowthAmount001"))
          break;
        Thread.sleep(20000);
        payrollCheckRequests.checkStatusPayrollCheckWithoutAwait(data.get(Constants.ID_TAP).getAsString(),
                data.get(OBLIGATION_ID).getAsString(),
                data.get(CUSTOMER_DOC_TYPE).getAsString(),
                data.get(CUSTOMER_DOCUMENT).getAsString(),
                otpJson.getString(TOKEN));
      }
    }else{
      var objectMapper = new ObjectMapper();
      payrollHistoryRequests.getPayrollStatus(data.get(CUSTOMER_DOC_TYPE).getAsString(),
              data.get(CUSTOMER_DOCUMENT).getAsString(), otpJson.getString(TOKEN));
      fileRequest.getValityStatus(otpJson.getString(TOKEN),objectMapper.readValue(data.get(Constants.VALITY_STATUS).toString(),
              new TypeReference<StatusBody>() {}));
      fileRequest.getStatus(otpJson.getString(TOKEN),objectMapper.readValue(data.get(Constants.STATUS).toString(),
              new TypeReference<StatusBody>() {}));
      var customerInfo = new String[] {data.get(CUSTOMER_DOC_TYPE).getAsString(), data.get(CUSTOMER_DOCUMENT).getAsString(), data.get(OBLIGATION_ID).getAsString()};
      payrollCheckRequests.getSimulationStatus(customerInfo,
              data.get(Constants.PAYER_NIT).getAsString(),
              data.get(Constants.SECTOR_NUMBER).getAsString(),
              data.get(Constants.SUB_SECTOR_NUMBER).getAsString(),
              data.get(Constants.ID_TAP).getAsString(),
              otpJson.getString(TOKEN));
    }
  }
}