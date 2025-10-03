package co.com.bancopopular.automation.rest.requests;

import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;

import co.com.bancopopular.automation.rest.apis.ServicePaths;
import co.com.bancopopular.automation.utils.HeadersEnum;
import co.com.bancopopular.automation.utils.RequestsUtil;
import co.com.bancopopular.automation.utils.SessionHelper;
import co.com.bancopopular.automation.utils.WprCipher;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class SimValidationRequests {

  public void validateSim(JsonObject data, String advisorJourneyId)
      throws MalformedURLException {
    Map<String, String> bodySimValidation = new HashMap<>();
    bodySimValidation
        .put("administrativeOfficeCode", data.get("administrativeOffice").getAsString());
    bodySimValidation
        .put("businessAdvisorDocument", data.get("advisorDocument").getAsString());
    bodySimValidation.put("documentNumber", data.get("customerDocument").getAsString());
    bodySimValidation.put("documentType", data.get("customerDocType").getAsString());
    bodySimValidation.put("officeCode", data.get("officeCode").getAsString());
    bodySimValidation.put("officeName", data.get("officeName").getAsString());
    bodySimValidation.put("advisorJourneyId", advisorJourneyId);
    bodySimValidation
        .put("administrativeOfficeName", data.get("administrativeOfficeName").getAsString());

    var gson = new Gson();
    var gsonType = new TypeToken<HashMap<String, Object>>(){}.getType();
    var bodyString = gson.toJson(bodySimValidation,gsonType);
    var encryptedBody = WprCipher.generateEncryptedBody(bodyString);

    SerenityRest.given()
        .and()
        .contentType(ContentType.JSON)
        .and()
        .header(
            HeadersEnum.HEADER_X_SECURITY_HMAC.toString(),
            WprCipher.generateSecurityHmac(bodySimValidation.toString(), "POST"))
        .and()
        .header(
            HeadersEnum.HEADER_X_SECURITY_SESSION.toString(), getFromSession(SessionHelper.SessionData.XSECURITYSESSION))
        .and()
        .header(
            HeadersEnum.HEADER_X_SECURITY_REQUEST_ID.toString(), getFromSession(SessionHelper.SessionData.XSECURITYREQUESTID))
        .and()
        .body(encryptedBody)
        .when()
        .post(ServicePaths.getValidationSim())
        .then()
        .extract()
        .body()
        .asString();
    RequestsUtil.addResponseInSerenityReport("Response validacion SIM:");
  }
}