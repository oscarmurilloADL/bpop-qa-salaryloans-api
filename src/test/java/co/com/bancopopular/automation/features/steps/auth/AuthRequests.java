package co.com.bancopopular.automation.features.steps.auth;

import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static java.util.concurrent.TimeUnit.SECONDS;
import static net.serenitybdd.rest.SerenityRest.given;
import static org.awaitility.Awaitility.await;

import co.com.bancopopular.automation.rest.apis.ServicePaths;
import co.com.bancopopular.automation.utils.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;


public class AuthRequests {

  RequestsUtil requestsUtil = new RequestsUtil();

  private static final String DIGIT_VALUE = "123456";
  private static final String DIGIT_VALUE_INCORRECT= "121212";
  private static final String DOCUMENT_NUMBER = "documentNumber";
  private static final String DOCUMENT_TYPE = "documentType";
  private static final String TOKEN = "token";
  private static final String ADVISOR_DECISION_TYPE= "advisorDecisionType";
  private static final String RECAPTCHA_RESPONSE="recaptchaResponse";
  private static final String DUMMY_TEST="dummy_test";
  private static final String NEW_PAYROLL="NewPayrollLoan";
  private static final String ADVISOR_DOCUMENT_NUMBER="advisorDocumentNumber";
  private static final String ADVISOR="51919050";
  private static final String ADVISOR_JOURNEY_ID="advisorJourneyId";
  private static final String OFFICE_ID="officeId";
  private static final String OFFICE_NAME="officeName";
  private static final String OTP_VALUE="otpValue";
  private static final String CELL_PHONE="cellphoneNumber";

  public void generateConfirmationOTP(String obligationID, String generationType,
      String customerDocType,
      String customerDocument, String token)
      throws MalformedURLException {
    Map<String, Object> generationBody = new HashMap<>();
    generationBody.put("confirmationCodeType", generationType);
    generationBody.put(DOCUMENT_NUMBER, customerDocument);
    generationBody.put(DOCUMENT_TYPE, customerDocType);
    given().relaxedHTTPSValidation()
        .header(HeadersEnum.AUTHORIZATION.toString(), token)
        .contentType(ContentType.JSON)
        .body(generationBody)
        .when()
        .post(ServicePaths
            .getGenerateConfirmationOTP(obligationID));
  }

  public void validateConfirmationOTP(String customerDocType, String customerDocument, String token,
                                      int generatedId)
      throws MalformedURLException {
    Map<String, Object> generationBody = new HashMap<>();
    generationBody.put("idRequest", generatedId);
    generationBody.put("identificationNumber", customerDocument);
    generationBody.put("identificationType", customerDocType);
    generationBody.put("otpCode", DIGIT_VALUE);
    given().relaxedHTTPSValidation()
        .header(HeadersEnum.AUTHORIZATION.toString(), token)
        .contentType(ContentType.JSON)
        .body(generationBody)
        .when()
        .put(ServicePaths
            .getValidateConfirmationOTP());
  }

  public void validateConfirmationIncorrectOTP(String customerDocType, String customerDocument, String token, String generatedId)
      throws MalformedURLException {
    Map<String, Object> generationBody = new HashMap<>();
    generationBody.put("idRequest", generatedId);
    generationBody.put("identificationNumber", customerDocument);
    generationBody.put("identificationType", customerDocType);
    generationBody.put("otpCode", DIGIT_VALUE_INCORRECT);
    given().relaxedHTTPSValidation()
        .header(HeadersEnum.AUTHORIZATION.toString(), token)
        .contentType(ContentType.JSON)
        .body(generationBody)
        .when()
        .put(ServicePaths
            .getValidateConfirmationOTP());
  }

  public void getAuthGenerateOTP(String customerDocType, String customerDocument, String advisorJourneyID,
                                 String officeName, String officeCode)
      throws MalformedURLException {
    var jsO = new JsonObject();
    jsO.addProperty(DOCUMENT_NUMBER, customerDocument);
    jsO.addProperty(DOCUMENT_TYPE, customerDocType);

    jsO.addProperty(ADVISOR_DECISION_TYPE, NEW_PAYROLL);
    jsO.addProperty(ADVISOR_DOCUMENT_NUMBER, ADVISOR);
    jsO.addProperty(ADVISOR_JOURNEY_ID, advisorJourneyID);
    jsO.addProperty(DOCUMENT_NUMBER, customerDocument);
    jsO.addProperty(DOCUMENT_TYPE, customerDocType);
    jsO.addProperty(OFFICE_ID, officeCode);
    jsO.addProperty(OFFICE_NAME, officeName);
    jsO.addProperty(RECAPTCHA_RESPONSE,DUMMY_TEST);

    var jsonB = jsO.toString();
    await().atMost(40, SECONDS).pollInSameThread().until(WprCipher.getKeysCipher());
    String encryptedBody = WprCipher.generateEncryptedBody(jsonB);

    given().relaxedHTTPSValidation()
        .contentType(ContentType.JSON)
        .and()
        .header(
            HeadersEnum.HEADER_X_SECURITY_HMAC.toString(),
            WprCipher.generateSecurityHmac(jsonB, "POST"))
        .and()
        .header(
            HeadersEnum.HEADER_X_SECURITY_SESSION.toString(), getFromSession(SessionHelper.SessionData.XSECURITYSESSION))
        .and()
        .header(
            HeadersEnum.HEADER_X_SECURITY_REQUEST_ID.toString(), getFromSession(SessionHelper.SessionData.XSECURITYREQUESTID))
        .and()
        .body(encryptedBody)
        .when()
        .put(ServicePaths
            .getAuthValidateOTP());
  }

  public void getAuthValidateOTP(String customerDocType, String customerDocument, String advisorJourneyID,
                                 String officeName, String officeCode, String otp)
      throws MalformedURLException {
    Map<String, Object> validateBody = new HashMap<>();
    validateBody.put(ADVISOR_DECISION_TYPE, NEW_PAYROLL);
    validateBody.put(ADVISOR_DOCUMENT_NUMBER, ADVISOR);
    validateBody.put(ADVISOR_JOURNEY_ID, advisorJourneyID);
    validateBody.put(DOCUMENT_NUMBER, customerDocument);
    validateBody.put(DOCUMENT_TYPE, customerDocType);
    validateBody.put(OFFICE_ID, officeCode);
    validateBody.put(OFFICE_NAME, officeName);
    validateBody.put(OTP_VALUE, otp);
    validateBody.put(RECAPTCHA_RESPONSE,DUMMY_TEST);
    await().atMost(40, SECONDS).pollInSameThread().until(WprCipher.getKeysCipher());
    var gson = new Gson();
    var gsonType = new TypeToken<HashMap<String, Object>>(){}.getType();
    var bodyString = gson.toJson(validateBody,gsonType);
    String encryptedBody = WprCipher.generateEncryptedBody(bodyString);

    given().relaxedHTTPSValidation()
        .contentType(ContentType.JSON)
        .and()
        .header(
            HeadersEnum.HEADER_X_SECURITY_HMAC.toString(),
            WprCipher.generateSecurityHmac(bodyString, "POST"))
        .and()
        .header(
            HeadersEnum.HEADER_X_SECURITY_SESSION.toString(), getFromSession(SessionHelper.SessionData.XSECURITYSESSION))
        .and()
        .header(
            HeadersEnum.HEADER_X_SECURITY_REQUEST_ID.toString(), getFromSession(SessionHelper.SessionData.XSECURITYREQUESTID))
        .and()
        .body(encryptedBody)
        .when()
        .post(ServicePaths
            .getAuthValidateOTP());
  }

  public void getAuthGenerateOTPCall(String customerDocType, String customerDocument, String advisorJourneyID,
                                     String officeName, String officeCode)
      throws MalformedURLException {
    Map<String, Object> body = new HashMap<>();
    body.put(DOCUMENT_TYPE, customerDocType);
    body.put(DOCUMENT_NUMBER, customerDocument);
    body.put(ADVISOR_DECISION_TYPE, NEW_PAYROLL);
    body.put(ADVISOR_DOCUMENT_NUMBER, ADVISOR);
    body.put(ADVISOR_JOURNEY_ID, advisorJourneyID);
    body.put(OFFICE_NAME, officeName);
    body.put(OFFICE_ID, officeCode);
    body.put(RECAPTCHA_RESPONSE, DUMMY_TEST);
    body.put(CELL_PHONE, "**********135");

    JsonPath authResponse = requestsUtil.getAuthOTP();
    given().relaxedHTTPSValidation().header(HeadersEnum.AUTHORIZATION.toString(), authResponse.getString(TOKEN))
        .contentType(ContentType.JSON)
        .body(body)
        .when()
        .post(ServicePaths.getAuthGenerateOTPCall());
  }

  public void getAdminLogin(String customerDocument, String password)
      throws MalformedURLException {
    Map<String, Object> loginBody = new HashMap<>();
    loginBody.put("id", customerDocument);
    loginBody.put("password", password);
    loginBody.put(RECAPTCHA_RESPONSE, DUMMY_TEST);
    await().atMost(40, SECONDS).pollInSameThread().until(WprCipher.getKeysCipher());
    var gson = new Gson();
    var gsonType = new TypeToken<HashMap<String, Object>>(){}.getType();
    var bodyString = gson.toJson(loginBody,gsonType);
    var encryptedBody = WprCipher.generateEncryptedBody(bodyString);
    given().relaxedHTTPSValidation()
            .contentType(ContentType.JSON)
            .header(
                    HeadersEnum.HEADER_X_SECURITY_HMAC.toString(),
                    WprCipher.generateSecurityHmac(bodyString, "POST"))
            .and()
            .header(
                    HeadersEnum.HEADER_X_SECURITY_SESSION.toString(), getFromSession(SessionHelper.SessionData.XSECURITYSESSION))
            .and()
            .header(
                    HeadersEnum.HEADER_X_SECURITY_REQUEST_ID.toString(), getFromSession(SessionHelper.SessionData.XSECURITYREQUESTID))
            .body(encryptedBody)
            .when()
            .post(ServicePaths.getAuthAdmin());
  }
}