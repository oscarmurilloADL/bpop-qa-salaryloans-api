package co.com.bancopopular.automation.rest.requests;

import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;

import co.com.bancopopular.automation.rest.apis.ServicePaths;
import co.com.bancopopular.automation.utils.HeadersEnum;
import co.com.bancopopular.automation.utils.RequestsUtil;
import co.com.bancopopular.automation.utils.SessionHelper;
import co.com.bancopopular.automation.utils.SessionHelper.SessionData;
import co.com.bancopopular.automation.utils.WprCipher;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.rest.SerenityRest;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import static co.com.bancopopular.automation.utils.SessionHelper.setInSession;
import static org.awaitility.Awaitility.await;
import static java.util.concurrent.TimeUnit.SECONDS;

public class LoginRequests {

  public JsonPath advisorByOffice(String advisorID)
      throws MalformedURLException {
    Map<String, String> bodyAdvisorByOffice = new HashMap<>();
    bodyAdvisorByOffice.put("documentNumber", advisorID);
    bodyAdvisorByOffice.put("recaptchaResponse", "dummyText");
    bodyAdvisorByOffice.put("googleAnalyticsId", "0000.0000");
    await().atMost(40, SECONDS).pollInSameThread().until(WprCipher.getKeysCipher());
    var gson = new Gson();
    var gsonType = new TypeToken<HashMap<String, Object>>(){}.getType();
    var bodyString = gson.toJson(bodyAdvisorByOffice,gsonType);
    var encryptedBody = WprCipher.generateEncryptedBody(bodyString);

    var response = SerenityRest.given()
        .and()
        .contentType(ContentType.JSON)
        .and()
        .header(
            HeadersEnum.HEADER_X_SECURITY_HMAC.toString(),
            WprCipher.generateSecurityHmac(bodyAdvisorByOffice.toString(), "POST"))
        .and()
        .header(
            HeadersEnum.HEADER_X_SECURITY_SESSION.toString(), getFromSession(SessionData.XSECURITYSESSION))
        .and()
        .header(
            HeadersEnum.HEADER_X_SECURITY_REQUEST_ID.toString(), getFromSession(SessionData.XSECURITYREQUESTID))
        .and()
        .body(encryptedBody)
        .when()
        .post(ServicePaths.getAdvisor())
        .then()
        .extract()
        .body()
        .asString();

    var jsonResponse = WprCipher.decryptRequest(SerenityRest.then().extract().asString());
    setInSession(SessionHelper.SessionData.ACCESS_KEY, jsonResponse.get("accessKey"));
    setInSession(SessionHelper.SessionData.SECRET_KEY, jsonResponse.get("secretKey"));
    setInSession(SessionHelper.SessionData.SESSION_TOKEN, jsonResponse.getString("sessionToken"));

    RequestsUtil.addResponseInSerenityReport("Response advisor/search:");
    return WprCipher.decryptRequest(response);
  }
}