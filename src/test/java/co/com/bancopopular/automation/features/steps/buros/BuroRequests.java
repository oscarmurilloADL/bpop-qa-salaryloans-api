package co.com.bancopopular.automation.features.steps.buros;

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

public class BuroRequests {

  private static final Long REQUIRED_AMOUNT = 0L;
  private static final Integer LOAN_TERM = 0;

  public void getBuroInfo(JsonObject data, String modalityType, String processType, String token)
      throws MalformedURLException {
    Map<String, Object> buroBody = new HashMap<>();
    buroBody.put("documentType", data.get("customerDocType").getAsString());
    buroBody.put("documentNumber", data.get("customerDocument").getAsString());
    buroBody.put("obligationId", data.get("obligationID").getAsString());
    buroBody.put("requiredAmount", REQUIRED_AMOUNT);
    buroBody.put("loanTerm", LOAN_TERM);
    if (processType.equals("ANY_FEE")) {
      buroBody.put("payrollCheckProcessId",
          getFromSession(SessionHelper.SessionData.PAYROLL_CHECK_PROCESS_ID));
      buroBody.put("payerNit",
              data.get("payerNit").getAsString());
      buroBody.put("sectorNumber",
              data.get("sectorNumber").getAsString());
      buroBody.put("subSectorNumber",
              data.get("subSectorNumber").getAsString());
    }
    buroBody.put("modalityType", modalityType);
    buroBody.put("processType", processType);

    var gson = new Gson();
    var gsonType = new TypeToken<HashMap<String, Object>>(){}.getType();
    var bodyString = gson.toJson(buroBody,gsonType);
    var encryptedBody = WprCipher.generateEncryptedBody(bodyString);

      SerenityRest.given()
              .and()
              .contentType(ContentType.JSON)
              .and()
              .header(
                      HeadersEnum.HEADER_X_SECURITY_HMAC.toString(),
                      WprCipher.generateSecurityHmac(buroBody.toString(), "POST"))
              .and()
              .header(
                      HeadersEnum.HEADER_X_SECURITY_SESSION.toString(), getFromSession(SessionHelper.SessionData.XSECURITYSESSION))
              .and()
              .header(
                      HeadersEnum.HEADER_X_SECURITY_REQUEST_ID.toString(), getFromSession(SessionHelper.SessionData.XSECURITYREQUESTID))
              .and()
              .header(
                      HeadersEnum.AUTHORIZATION.toString(), token)
              .and()
              .body(encryptedBody)
              .when()
              .post(ServicePaths.getOfferingBuro())
              .then()
              .extract()
              .body()
              .asString();

    RequestsUtil.addResponseInSerenityReport("Response offering:");
  }
}