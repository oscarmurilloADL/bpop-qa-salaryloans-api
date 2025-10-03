package co.com.bancopopular.automation.features.steps.reprecio;

import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.rest.apis.ServicePaths;
import co.com.bancopopular.automation.utils.HeadersEnum;
import co.com.bancopopular.automation.utils.RequestsUtil;
import co.com.bancopopular.automation.utils.SessionHelper;
import co.com.bancopopular.automation.utils.WprCipher;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import io.restassured.http.ContentType;


import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import static co.com.bancopopular.automation.constants.Constants.*;
import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import java.lang.reflect.Type;
import io.restassured.RestAssured;
import net.serenitybdd.rest.SerenityRest;

public class ReprecioRequest {

    private Gson gson = new Gson();
    private Type gsonType = new TypeToken<HashMap<String, Object>>(){}.getType();

    private String getEncryptedBody(Map<String, ?> body) {
        var bodyString = gson.toJson(body, gsonType);
        return WprCipher.generateEncryptedBody(bodyString);
    }

    private void makePostRequest(String endpoint, Map<String, ?> body) {
        var encryptedBody = getEncryptedBody(body);
        SerenityRest.given()
                .and()
                .contentType(ContentType.JSON)
                .and()
                .header(HeadersEnum.HEADER_X_SECURITY_HMAC.toString(), WprCipher.generateSecurityHmac(body.toString(), "POST"))
                .and()
                .header(HeadersEnum.HEADER_X_SECURITY_SESSION.toString(), getFromSession(SessionHelper.SessionData.XSECURITYSESSION))
                .and()
                .header(HeadersEnum.HEADER_X_SECURITY_REQUEST_ID.toString(), getFromSession(SessionHelper.SessionData.XSECURITYREQUESTID))
                .and()
                .body(encryptedBody)
                .when()
                .post(endpoint);
    }

    public void search(JsonObject data, String token, String advisorJourneyId) throws MalformedURLException {
        Map<String, String> bodyAdvisorByOffice = new HashMap<>();
        bodyAdvisorByOffice
                .put("administrativeOfficeCode", data.get("administrativeOffice").getAsString());
        bodyAdvisorByOffice
                .put("businessAdvisorDocument", data.get(ADVISOR_DOCUMENT).getAsString());
        bodyAdvisorByOffice.put(DOCUMENT_NUMBER, data.get(CUSTOMER_DOCUMENT).getAsString());
        bodyAdvisorByOffice.put(DOCUMENT_TYPE, data.get(CUSTOMER_DOC_TYPE).getAsString());
        bodyAdvisorByOffice.put(OFFICE_CODE, data.get(OFFICE_CODE).getAsString());
        bodyAdvisorByOffice.put(OFFICE_NAME, data.get(OFFICE_NAME).getAsString());
        bodyAdvisorByOffice.put(ADVISOR_JOURNEY_ID, advisorJourneyId);
        bodyAdvisorByOffice.put("salesModel", data.get("salesModel").getAsString());
        bodyAdvisorByOffice
                .put("administrativeOfficeName", data.get("administrativeOfficeName").getAsString());
        gson = new Gson();
        gsonType = new TypeToken<HashMap<String, Object>>() {
        }.getType();
        var bodyString = gson.toJson(bodyAdvisorByOffice, gsonType);
        var encryptedBody = WprCipher.generateEncryptedBody(bodyString);
        SerenityRest.given()
                .and()
                .contentType(ContentType.JSON)
                .and()
                .header(HeadersEnum.AUTHORIZATION.toString(), token)
                .and()
                .header(
                        HeadersEnum.HEADER_X_SECURITY_HMAC.toString(),
                        WprCipher.generateSecurityHmac(bodyAdvisorByOffice.toString(), "POST"))
                .and()
                .header(
                        HeadersEnum.HEADER_X_SECURITY_SESSION.toString(), getFromSession(SessionHelper.SessionData.XSECURITYSESSION))
                .and()
                .header(
                        HeadersEnum.HEADER_X_SECURITY_REQUEST_ID.toString(), getFromSession(SessionHelper.SessionData.XSECURITYREQUESTID))
                .and()
                .body(encryptedBody)
                .when()
                .post(ServicePaths.getSearchRepricing())
                .then()
                .extract()
                .body()
                .asString();

        RequestsUtil.addResponseInSerenityReport("Response repricing/search:");
    }

    public void callCenter(String journeyId, JsonObject data) throws MalformedURLException {
        Map<String, String> body = new HashMap<>();
        body.put(DOCUMENT_NUMBER, data.get(CUSTOMER_DOCUMENT).getAsString());
        body.put(ADVISOR_DECISION_TYPE, "PayrollLoanRepricing");
        body.put(ADVISOR_DOCUMENT_NUMBER, data.get(ADVISOR_DOCUMENT).getAsString());
        body.put(ADVISOR_JOURNEY_ID, journeyId);
        body.put(DOCUMENT_TYPE, data.get(CUSTOMER_DOC_TYPE).getAsString());
        body.put(OFFICE_ID, data.get(OFFICE_CODE).getAsString());
        body.put(OFFICE_NAME, data.get(Constants.OFFICE_NAME).getAsString());
        body.put(OTP_VALUE, null);
        body.put(RECAPTCHA_RESPONSE, DUMMY_TEST);
        makePostRequest(ServicePaths.getCallCenter().toString(), body);
    }

    public String getAuthTokenSalary(String journeyID, JsonObject dataScenario) throws MalformedURLException {
        Map<String, Object> validateBody = new HashMap<>();
        validateBody.put(ADVISOR_DECISION_TYPE, "NewPayrollLoan");
        validateBody.put(ADVISOR_DOCUMENT_NUMBER, dataScenario.get(ADVISOR_DOCUMENT).getAsString());
        validateBody.put(ADVISOR_JOURNEY_ID, journeyID);
        validateBody.put(DOCUMENT_NUMBER, dataScenario.get(CUSTOMER_DOCUMENT).getAsString());
        validateBody.put(DOCUMENT_TYPE, dataScenario.get(CUSTOMER_DOC_TYPE).getAsString());
        validateBody.put(OFFICE_ID, dataScenario.get(OFFICE_CODE).getAsString());
        validateBody.put(OFFICE_NAME, dataScenario.get(Constants.OFFICE_NAME).getAsString());
        validateBody.put(OTP_VALUE, DIGITVALUE);
        validateBody.put(RECAPTCHA_RESPONSE, DUMMY_TEST);

        await().atMost(40, SECONDS).pollInSameThread().until(WprCipher.getKeysCipher());

        var encryptedValidateBody = getEncryptedBody(validateBody);
        var response = RestAssured.given()
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .header(HeadersEnum.HEADER_X_SECURITY_HMAC.toString(), WprCipher.generateSecurityHmac(validateBody.toString(), "POST"))
                .header(HeadersEnum.HEADER_X_SECURITY_SESSION.toString(), getFromSession(SessionHelper.SessionData.XSECURITYSESSION))
                .header(HeadersEnum.HEADER_X_SECURITY_REQUEST_ID.toString(), getFromSession(SessionHelper.SessionData.XSECURITYREQUESTID))
                .body(encryptedValidateBody)
                .when()
                .post(ServicePaths.getAuthValidateOTP())
                .asString();

        return WprCipher.decryptRequest(response).get("token").toString();
    }
}
