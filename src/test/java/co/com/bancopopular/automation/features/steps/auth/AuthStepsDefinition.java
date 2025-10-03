package co.com.bancopopular.automation.features.steps.auth;

import co.com.bancopopular.automation.conf.StepBase;
import co.com.bancopopular.automation.features.steps.accept.AcceptRequests;
import co.com.bancopopular.automation.rest.requests.LoginRequests;
import co.com.bancopopular.automation.rest.requests.SearchRequests;
import co.com.bancopopular.automation.rest.requests.SimValidationRequests;
import co.com.bancopopular.automation.utils.DataUserInstance;
import co.com.bancopopular.automation.utils.RequestsUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.net.MalformedURLException;
import net.serenitybdd.annotations.Steps;

public class AuthStepsDefinition extends StepBase {

  AuthRequests authRequests = new AuthRequests();
  SearchRequests searchRequests = new SearchRequests();
  LoginRequests loginRequests = new LoginRequests();
  SimValidationRequests simValidationRequests = new SimValidationRequests();
  RequestsUtil requestsUtil = new RequestsUtil();
  AcceptRequests acceptRequests = new AcceptRequests();

  private static final String OBLIGATION_ID = "obligationID";
  private static final String CUSTOMER_DOC_TYPE = "customerDocType";
  private static final String CUSTOMER_DOCUMENT = "customerDocument";
  private static final String ADVISOR_DOCUMENT = "advisorDocument";
  private static final String ADVISOR_JOURNEY_ID = "advisorJourneyId";
  private static final String DIGIT_VALUE = "123456";
  private static final String DIGIT_VALUE_INCORRECT = "121212";
  private static final String TOKEN = "token";
  private static final String OFFICE_CODE="officeCode";
  private static final String OFFICE_NAME="officeName";

  @Steps
  AuthAssertions authAssertions;

  @When("{word} solicita su OTP")
  public void generateOTP(String actor) throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();
    var advisorLogin = loginRequests
        .advisorByOffice(data.get(ADVISOR_DOCUMENT).getAsString());
    searchRequests.searchCustomer(data, advisorLogin.getString(ADVISOR_JOURNEY_ID)
    );
    authRequests.getAuthGenerateOTP(data.get(CUSTOMER_DOC_TYPE).getAsString(),
        data.get(CUSTOMER_DOCUMENT).getAsString(),
            advisorLogin.getString(ADVISOR_JOURNEY_ID),
            data.get(OFFICE_CODE).getAsString(),
            data.get(OFFICE_NAME).getAsString());
  }

  @When("{word} solicita su OTP por llamada")
  public void generateOTPCall(String actor) throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();
    var advisorLogin = loginRequests
            .advisorByOffice(data.get(ADVISOR_DOCUMENT).getAsString());
    authRequests.getAuthGenerateOTPCall(data.get(CUSTOMER_DOC_TYPE).getAsString(),
            data.get(CUSTOMER_DOCUMENT).getAsString(),
            advisorLogin.getString(ADVISOR_JOURNEY_ID),
            data.get(OFFICE_CODE).getAsString(),
            data.get(OFFICE_NAME).getAsString());
  }

  @Then("se genera su OTP correctamente")
  public void validateGenerateOTP() {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    authAssertions.verifyGenerateAuthOTP();
  }
  @Then("se genera su OTP telefonicamente correctamente")
  public void validateGeneratePhoneOTP() {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    assertionsUtil.validateJSONSchema("schemas/generate_auth_otp.json");
    authAssertions.verifyGeneratePhoneAuthOTP();
  }

  @When("{word} ingresa su OTP")
  public void validateOTP(String actor) throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();
    var advisorData = loginRequests.advisorByOffice(data.get(ADVISOR_DOCUMENT).getAsString());
    var advisorLogin = loginRequests
        .advisorByOffice(data.get(ADVISOR_DOCUMENT).getAsString());
    searchRequests.searchCustomer(data, advisorLogin.getString(ADVISOR_JOURNEY_ID));
    simValidationRequests
        .validateSim(data, advisorData.getString(ADVISOR_JOURNEY_ID));
    authRequests.getAuthGenerateOTP(data.get(CUSTOMER_DOC_TYPE).getAsString(),
            data.get(CUSTOMER_DOCUMENT).getAsString(),
            advisorLogin.getString(ADVISOR_JOURNEY_ID),
            data.get(OFFICE_CODE).getAsString(),
            data.get(OFFICE_NAME).getAsString());
    authRequests.getAuthValidateOTP(data.get(CUSTOMER_DOC_TYPE).getAsString(),
            data.get(CUSTOMER_DOCUMENT).getAsString(),
            advisorLogin.getString(ADVISOR_JOURNEY_ID),
            data.get(OFFICE_NAME).getAsString(),
            data.get(OFFICE_CODE).getAsString(), DIGIT_VALUE);
  }

  @Then("se valida su OTP correctamente")
  public void validateValidationOTP() {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    authAssertions.verifyOTPAuth();
  }

  @When("{word} ingresa su OTP incorrecto")
  public void validateIncorrectOTP(String actor) throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();
    var advisorData = loginRequests.advisorByOffice(data.get(ADVISOR_DOCUMENT).getAsString());
    var advisorLogin = loginRequests
        .advisorByOffice(data.get(ADVISOR_DOCUMENT).getAsString());
    searchRequests.searchCustomer(data, advisorLogin.getString(ADVISOR_JOURNEY_ID));
    simValidationRequests
        .validateSim(data, advisorData.getString(ADVISOR_JOURNEY_ID));
    authRequests.getAuthGenerateOTP(data.get(CUSTOMER_DOC_TYPE).getAsString(),
            data.get(CUSTOMER_DOCUMENT).getAsString(),
            advisorLogin.getString(ADVISOR_JOURNEY_ID),
            data.get(OFFICE_CODE).getAsString(),
            data.get(OFFICE_NAME).getAsString());
    authRequests.getAuthValidateOTP(data.get(CUSTOMER_DOC_TYPE).getAsString(),
            data.get(CUSTOMER_DOCUMENT).getAsString(),
            advisorLogin.getString(ADVISOR_JOURNEY_ID),
            data.get(OFFICE_NAME).getAsString(),
            data.get(OFFICE_CODE).getAsString(), DIGIT_VALUE_INCORRECT);

  }

  @Then("se valida su OTP incorrecto")
  public void validateIncorrectOTPValidation() {
    assertionsUtil.shouldSeeInternalErrorStatusCode();
    authAssertions.verifyFailedOTPAuth();
  }

  @When("{word} solicita su OTP de confirmación de crédito")
  public void generateConfirmationOTP(String actor) throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();
    var otpJson = requestsUtil.createAuthOTP();
    authRequests.generateConfirmationOTP(data.get(OBLIGATION_ID).getAsString(), "sms",
        data.get(CUSTOMER_DOC_TYPE).getAsString(),
        data.get(CUSTOMER_DOCUMENT).getAsString(), otpJson.getString(TOKEN));
  }

  @When("{word} solicita su OTP de confirmación por llamada")
  public void generateConfirmationCallOTP(String actor) throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();
    var otpJson = requestsUtil.createAuthOTP();
    authRequests.generateConfirmationOTP(data.get(OBLIGATION_ID).getAsString(), "call",
        data.get(CUSTOMER_DOC_TYPE).getAsString(),
        data.get(CUSTOMER_DOCUMENT).getAsString(), otpJson.getString(TOKEN));
  }

  @Then("se genera su OTP de confirmación correctamente")
  public void validateConfirmationOTPGeneration() {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    authAssertions.verifyGenerateConfirmationOTP();
  }

  @When("{word} ingresa su OTP de confirmación de crédito")
  public void sendConfirmationOTP(String actor) throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();
    var otpJson = requestsUtil.createAuthOTP();
    acceptRequests.acceptCreditData(data.get(CUSTOMER_DOC_TYPE).getAsString(),
        data.get(CUSTOMER_DOCUMENT).getAsString(), data.get(OBLIGATION_ID).getAsString(),
        otpJson.getString(TOKEN));
    authRequests.generateConfirmationOTP(data.get(OBLIGATION_ID).getAsString(), "sms",
        data.get(CUSTOMER_DOC_TYPE).getAsString(),
        data.get(CUSTOMER_DOCUMENT).getAsString(),
        otpJson.getString(TOKEN));
    authRequests.validateConfirmationOTP(data.get(CUSTOMER_DOC_TYPE).getAsString(),
        data.get(CUSTOMER_DOCUMENT).getAsString(), otpJson.getString(TOKEN), otpJson.getInt("generatedId"));
  }

  @Then("se valida su OTP de confirmación correctamente")
  public void validateConfirmationOTPCorrectly() {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    authAssertions.verifyValidationConfirmationOTP();
  }

  @When("{word} ingresa su OTP de confirmación incorrecto")
  public void sendConfirmationIncorrectOTP(String actor) throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();
    var otpJson = requestsUtil.createAuthOTP();
    acceptRequests.acceptCreditData(data.get(CUSTOMER_DOC_TYPE).getAsString(),
        data.get(CUSTOMER_DOCUMENT).getAsString(), data.get(OBLIGATION_ID).getAsString(),
        otpJson.getString(TOKEN));
    authRequests.generateConfirmationOTP(data.get(OBLIGATION_ID).getAsString(), "sms",
        data.get(CUSTOMER_DOC_TYPE).getAsString(),
        data.get(CUSTOMER_DOCUMENT).getAsString(),
        otpJson.getString(TOKEN));
    authRequests.validateConfirmationIncorrectOTP(data.get(CUSTOMER_DOC_TYPE).getAsString(),
        data.get(CUSTOMER_DOCUMENT).getAsString(), otpJson.getString(TOKEN), otpJson.getString("generatedId"));

  }

  @Then("se valida su OTP de confirmación incorrecto")
  public void validateConfirmationIncorrectOTP() {
    assertionsUtil.shouldSeeInternalErrorStatusCode();
    authAssertions.verifyValidationConfirmationOTPIncorrect();
  }

  @When("{word} ingresa al administrador")
  public void loginRequest(String actor) throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();
    authRequests.getAdminLogin(data.get(CUSTOMER_DOCUMENT).getAsString(),
        data.get("password").getAsString());
  }

  @Then("puede ingresar correctamente al administrador")
  public void loginValidate() {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    authAssertions.verifyLoginFactoryAdmin();
  }
}