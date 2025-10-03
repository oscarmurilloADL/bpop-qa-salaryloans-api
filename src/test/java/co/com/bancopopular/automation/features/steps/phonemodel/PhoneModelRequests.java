package co.com.bancopopular.automation.features.steps.phonemodel;

import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static java.util.concurrent.TimeUnit.SECONDS;
import static net.serenitybdd.rest.SerenityRest.given;
import static org.awaitility.Awaitility.await;

import co.com.bancopopular.automation.models.phonemodel.Upload;
import co.com.bancopopular.automation.rest.apis.ServicePaths;
import co.com.bancopopular.automation.utils.*;
import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import java.net.MalformedURLException;

public class PhoneModelRequests {

  RequestsUtil requestsUtil = new RequestsUtil();

  private static final String DOCUMENT_NUMBER = "documentNumber";
  private static final String DOCUMENT_TYPE = "documentType";
  private static final String RECAPTCHA_RESPONSE = "recaptchaResponse";
  private static final String TOKEN = "token";

  public void getPayrollCheckUploads(Upload body)
      throws MalformedURLException {

    var authResponse = requestsUtil.getAuthOTP();

    given().relaxedHTTPSValidation()
        .header(HeadersEnum.AUTHORIZATION.toString(), authResponse.getString(TOKEN))
        .contentType(ContentType.JSON)
        .body(body)
        .when().post(ServicePaths.getUploadLink());
  }

  public void getPayrollCheckUploadsClient(String customerDocType, String customerDocument)
      throws MalformedURLException {

    var jsO = new JsonObject();
    jsO.addProperty(DOCUMENT_NUMBER, customerDocument);
    jsO.addProperty(DOCUMENT_TYPE, customerDocType);
    jsO.addProperty(RECAPTCHA_RESPONSE, "dummy.test");
    var jsonB = jsO.toString();
    await().atMost(40, SECONDS).pollInSameThread().until(WprCipher.getKeysCipher());
    var encryptedBody = WprCipher.generateEncryptedBody(jsonB);

    given().relaxedHTTPSValidation()
        .contentType(ContentType.JSON)
        .header(
            HeadersEnum.HEADER_X_SECURITY_HMAC.toString(),
            WprCipher.generateSecurityHmac(jsonB, "POST"))
        .and()
        .header(
            HeadersEnum.HEADER_X_SECURITY_SESSION.toString(), getFromSession(SessionHelper.SessionData.XSECURITYSESSION))
        .and()
        .header(
            HeadersEnum.HEADER_X_SECURITY_REQUEST_ID.toString(), getFromSession(SessionHelper.SessionData.XSECURITYREQUESTID))
        .body(encryptedBody)
        .when().post(ServicePaths.getClientUploadLink());
  }

  public void getPayrollCheckCredentialsClient(String customerDocType, String customerDocument)
      throws MalformedURLException {
    var authResponse = requestsUtil.getAuthOTP();
    given().relaxedHTTPSValidation()
        .header(HeadersEnum.AUTHORIZATION.toString(), authResponse.getString(TOKEN))
        .contentType(ContentType.JSON)
        .when().get(ServicePaths.getPayrollCheckLink(customerDocType, customerDocument));
  }

}