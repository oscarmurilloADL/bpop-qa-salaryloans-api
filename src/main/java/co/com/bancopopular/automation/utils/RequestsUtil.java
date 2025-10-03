package co.com.bancopopular.automation.utils;

import static co.com.bancopopular.automation.constants.Constants.MAX_ATTEMPTS;
import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static co.com.bancopopular.automation.utils.SessionHelper.setInSession;
import static java.util.concurrent.TimeUnit.SECONDS;
import static net.serenitybdd.rest.SerenityRest.given;
import static org.awaitility.Awaitility.await;
import net.serenitybdd.core.Serenity;
import net.thucydides.model.steps.ExecutedStepDescription;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import co.com.bancopopular.automation.rest.apis.ServicePaths;
import co.com.bancopopular.automation.rest.requests.*;
import co.com.bancopopular.automation.utils.logs.LogPrinter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import net.serenitybdd.rest.SerenityRest;
import org.apache.http.HttpStatus;
import net.thucydides.core.steps.StepEventBus;


public class RequestsUtil {

    SearchRequests searchRequests = new SearchRequests();
    LoginRequests loginRequests = new LoginRequests();
    SimValidationRequests simValidationRequests = new SimValidationRequests();
    LegalConditionsRequest legalConditionsRequest = new LegalConditionsRequest();
    private static final String DIGITVALUE = "123456";
    private static final String ADVISOR = "51919050";
    private static final String CUSTOMER_DOCUMENT = "customerDocument";
    private static final String CUSTOMER_DOC_TYPE = "customerDocType";
    private static final String ADVISOR_DOCUMENT = "advisorDocument";
    private static final String DOCUMENT_TYPE = "documentType";
    private static final String ADVISOR_JOURNEY_ID = "advisorJourneyId";
    private static final String ADVISOR_DECISION_TYPE = "advisorDecisionType";
    private static final String ADVISOR_DOCUMENT_NUMBER = "advisorDocumentNumber";
    private static final String OFFICE_CODE = "officeCode";
    private static final String OFFICE_NAME = "officeName";
    private static final String RECAPTCHA_RESPONSE = "recaptchaResponse";
    private static final String DUMMY_TEST = "dummy_test";
    private static final String OFFICE_ID = "officeId";
    private static final String OTP_VALUE = "otpValue";
    private static final String DOCUMENT_NUMBER = "documentNumber";
    private static final String NEW_PAYROLL = "NewPayrollLoan";
    private static final String WITHOUT_LOGS="No se encontraron logs";
    private static final Logger LOGGER = LogManager.getLogger(RequestsUtil.class);

    public static JsonPath getAuthDebitCard() throws MalformedURLException {
        var data = TokenInstance.getInstance().getData();
        var dataScenario = DataUserInstance.getInstance().getData();
        if (data != null) {
            if (!TokenInstance.getInstance().getDocumentData()
                    .equals(dataScenario.get(CUSTOMER_DOCUMENT).getAsString())) {
                return setDebitCardInfo(dataScenario);
            }
            return data;
        }
        return setDebitCardInfo(dataScenario);
    }

    public static JsonPath createAuthDebitCard() throws MalformedURLException {
        var dataScenario = DataUserInstance.getInstance().getData();
        return setDebitCardInfo(dataScenario);
    }

    public JsonPath createAdminLoginAuth(String factoryDocument, String password) throws MalformedURLException {
        TokenInstance.getInstance()
                .setUserDocumentData(factoryDocument);
        Map<String, Object> loginBody = new HashMap<>();
        loginBody.put("id", factoryDocument);
        loginBody.put("password", password);
        await().atMost(40, SECONDS).pollInSameThread().until(WprCipher.getKeysCipher());
        WprCipher.getKeysCipher();
        var gson = new Gson();
        var gsonType = new TypeToken<HashMap<String, Object>>() {
        }.getType();
        var bodyString = gson.toJson(loginBody, gsonType);
        var encryptedBody = WprCipher.generateEncryptedBody(bodyString);
        SerenityRest.given().relaxedHTTPSValidation()
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
                .post(ServicePaths.getAuthAdmin()).asString();

        var encryptedAnswer = SerenityRest.then().extract().body().asString();
        return WprCipher.decryptRequest(encryptedAnswer);
    }

    private static JsonPath setDebitCardInfo(JsonObject dataScenario) throws MalformedURLException {
        TokenInstance.getInstance()
                .setUserDocumentData(dataScenario.get(CUSTOMER_DOCUMENT).getAsString());
        Map<String, String> debitCardOffice = new HashMap<>();
        debitCardOffice.put("debitCardNumber", DIGITVALUE);
        debitCardOffice.put("debitCardPassword", CipherUtil.cipherRSAOAEP512("1234"));
        debitCardOffice.put("documentId", dataScenario.get(CUSTOMER_DOCUMENT).getAsString());
        debitCardOffice.put(DOCUMENT_TYPE, dataScenario.get(CUSTOMER_DOC_TYPE).getAsString());
        Response response = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .body(debitCardOffice)
                .when().post(ServicePaths.getAuthDebitCard());
        TokenInstance.getInstance().setData(response.then().extract().body().jsonPath());
        return response.then().extract().body().jsonPath();
    }

    public JsonPath setOTPInfo(JsonObject dataScenario) throws MalformedURLException {
        TokenInstance.getInstance()
                .setUserDocumentData(dataScenario.get(CUSTOMER_DOCUMENT).getAsString());
        var data = DataUserInstance.getInstance().getData();
        JsonPath advisorData = null;
        try {
            for(var x = 0 ; x < MAX_ATTEMPTS ;x++){
                advisorData = loginRequests
                        .advisorByOffice(dataScenario.get(ADVISOR_DOCUMENT).getAsString());
                if (SerenityRest.then().extract().statusCode() == HttpStatus.SC_OK) {
                    break;
                }
            }
            for (var x = 0; x < MAX_ATTEMPTS; x++) {
                searchRequests
                        .searchCustomer(data, advisorData.getString(ADVISOR_JOURNEY_ID));
                if (SerenityRest.then().extract().statusCode() == HttpStatus.SC_OK) {
                    break;
                }
            }
            if(getFromSession(SessionHelper.SessionData.OWNERSHIP_REFACTOR_TOGGLE).equals("ON")){
                searchRequests.getAccountsOwnership(data.get(CUSTOMER_DOC_TYPE).getAsString(),
                        data.get(CUSTOMER_DOCUMENT).getAsString());
            }

            setInSession(SessionHelper.SessionData.JOURNEY_ID, advisorData.getString(ADVISOR_JOURNEY_ID));
            simValidationRequests
                    .validateSim(data, advisorData.getString(ADVISOR_JOURNEY_ID));
            legalConditionsRequest.getDataTreatment(data.get(CUSTOMER_DOC_TYPE).getAsString(),
                    data.get(CUSTOMER_DOCUMENT).getAsString(), "true", advisorData.getString(ADVISOR_JOURNEY_ID));
            legalConditionsRequest.getBureauReport(data.get(CUSTOMER_DOC_TYPE).getAsString(),
                    data.get(CUSTOMER_DOCUMENT).getAsString(), "true", advisorData.getString(ADVISOR_JOURNEY_ID));
        } catch (NullPointerException e) {
            LogPrinter.printError("", e.toString());
        }

        var jsO = new JsonObject();
        jsO.addProperty(ADVISOR_DECISION_TYPE, NEW_PAYROLL);
        jsO.addProperty(ADVISOR_DOCUMENT_NUMBER, ADVISOR);
        assert advisorData != null;
        jsO.addProperty(ADVISOR_JOURNEY_ID, advisorData.getString(ADVISOR_JOURNEY_ID));
        jsO.addProperty(DOCUMENT_NUMBER, dataScenario.get(CUSTOMER_DOCUMENT).getAsString());
        jsO.addProperty(DOCUMENT_TYPE, dataScenario.get(CUSTOMER_DOC_TYPE).getAsString());
        jsO.addProperty(OFFICE_ID, dataScenario.get(OFFICE_CODE).getAsString());
        jsO.addProperty(OFFICE_NAME, dataScenario.get(OFFICE_NAME).getAsString());
        jsO.addProperty(RECAPTCHA_RESPONSE, DUMMY_TEST);
        var jsonB = jsO.toString();
        await().atMost(40, SECONDS).pollInSameThread().until(WprCipher.getKeysCipher());
        var encryptedBody = WprCipher.generateEncryptedBody(jsonB);

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

        Map<String, Object> validateBody = new HashMap<>();
        validateBody.put(ADVISOR_DECISION_TYPE, NEW_PAYROLL);
        validateBody.put(ADVISOR_DOCUMENT_NUMBER, ADVISOR);
        validateBody.put(ADVISOR_JOURNEY_ID, advisorData.getString(ADVISOR_JOURNEY_ID));
        validateBody.put(DOCUMENT_NUMBER, dataScenario.get(CUSTOMER_DOCUMENT).getAsString());
        validateBody.put(DOCUMENT_TYPE, dataScenario.get(CUSTOMER_DOC_TYPE).getAsString());
        validateBody.put(OFFICE_ID, dataScenario.get(OFFICE_CODE).getAsString());
        validateBody.put(OFFICE_NAME, dataScenario.get(OFFICE_NAME).getAsString());
        validateBody.put(OTP_VALUE, DIGITVALUE);
        validateBody.put(RECAPTCHA_RESPONSE, DUMMY_TEST);

        await().atMost(40, SECONDS).pollInSameThread().until(WprCipher.getKeysCipher());
        var gson = new Gson();
        var gsonType = new TypeToken<HashMap<String, Object>>() {
        }.getType();
        var bodyString = gson.toJson(validateBody, gsonType);
        var encryptedValidateBody = WprCipher.generateEncryptedBody(bodyString);

        var response = SerenityRest.given().relaxedHTTPSValidation()
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
                .body(encryptedValidateBody)
                .when()
                .post(ServicePaths
                        .getAuthValidateOTP())
                .asString();

        return WprCipher.decryptRequest(response);
    }

    public JsonPath getAuthOTP() throws MalformedURLException {
        var data = TokenInstance.getInstance().getData();
        var dataScenario = DataUserInstance.getInstance().getData();
        if (data != null) {
            if (!TokenInstance.getInstance().getDocumentData()
                    .equals(dataScenario.get(CUSTOMER_DOCUMENT).getAsString())) {
                return setOTPInfo(dataScenario);
            }
            return data;
        }
        return setOTPInfo(dataScenario);
    }

    public JsonPath createAuthOTP() throws MalformedURLException {
        var dataScenario = DataUserInstance.getInstance().getData();
        return setOTPInfo(dataScenario);
    }

    public String createOauth2Token() throws MalformedURLException {
        return SerenityRest.given()
                .and()
                .contentType(ContentType.JSON)
                .and()
                /* Estos valores se deben encriptar con los metodos existentes encode y decode en la clase WprCiper
                 porque por temas de funcionalidad exitosa se dejan quemados */
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParam("client_id","6jo68uh5qgp7h1oi7dgrnbdgfl")
                .formParam("grant_type","client_credentials")
                .formParam("client_secret","qq8v8vriupmu75uv582laht1lbe37lrn2aao80gb5d44i8gmta3")
                .when()
                .post(ServicePaths.getOauth2Token())
                .then()
                .extract()
                .jsonPath()
                .get("access_token")
                .toString();
    }

    public void perfomanceSearch() {
        ValidationsRequests validationsRequests = new ValidationsRequests();
        var dataScenario = DataUserInstance.getInstance().getData();
        TokenInstance.getInstance().setUserDocumentData(dataScenario.get(CUSTOMER_DOCUMENT).getAsString());
        var data = DataUserInstance.getInstance().getData();
        try {
            JsonPath advisorData = loginRequests.advisorByOffice(dataScenario.get(ADVISOR_DOCUMENT).getAsString());
            var advisorJourneyId = advisorData.getString(ADVISOR_JOURNEY_ID);
            var customerDocType = data.get(CUSTOMER_DOC_TYPE).getAsString();
            var customerDocument = data.get(CUSTOMER_DOCUMENT).getAsString();

            for (var x = 0; x < 120; x++) {
                searchRequests.searchCustomer(data, advisorJourneyId);
                simValidationRequests.validateSim(data, advisorJourneyId);
                validationsRequests.sendValidateInternalViability(customerDocType, customerDocument);
            }
        } catch (MalformedURLException e) {
            LOGGER.log(Level.ERROR, "Error encrypting info", e);
        }
    }

    public static void addResponseInSerenityReport(String tittle){
        var decryptedResponse = WprCipher.decryptRequest(SerenityRest.then().extract().body().asString());
        Serenity.recordReportData().withTitle(tittle)
                .andContents(decryptedResponse.get().toString());
    }

    public static void addLogInSerenityReport(String tittle, String content){
        Serenity.recordReportData().asEvidence()
                .withTitle(tittle)
                .andContents(content);
    }

    public static void pause(int seconds){
        try{
            TimeUnit.SECONDS.sleep(seconds);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    public String extractJson(String eventLog) {
        var startIndex = eventLog.indexOf("{");
        var endIndex = eventLog.lastIndexOf("}");

        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            return eventLog.substring(startIndex, endIndex + 1);
        }else{
            return WITHOUT_LOGS;
        }
    }

    public static void logSuccessfulAssertion(String description) {
        StepEventBus.getEventBus().stepStarted(ExecutedStepDescription.withTitle(description));
        StepEventBus.getEventBus().stepFinished();
    }


}