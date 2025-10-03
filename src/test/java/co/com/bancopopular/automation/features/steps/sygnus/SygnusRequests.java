package co.com.bancopopular.automation.features.steps.sygnus;

import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static co.com.bancopopular.automation.utils.SessionHelper.setInSession;

import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.features.steps.payrollhistory.PayrollHistoryRequests;
import co.com.bancopopular.automation.features.steps.tap.LaboralCertificationRequests;
import co.com.bancopopular.automation.features.steps.tap.PayrollCheckRequests;
import co.com.bancopopular.automation.models.tap.StatusBody;
import co.com.bancopopular.automation.rest.apis.ServicePaths;
import co.com.bancopopular.automation.utils.HeadersEnum;
import co.com.bancopopular.automation.utils.SessionHelper;
import co.com.bancopopular.automation.utils.WprCipher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.rest.SerenityRest;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class SygnusRequests {

  private static final String DOCUMENT_NUMBER = "documentNumber";
  private static final String DOCUMENT_TYPE = "documentType";
  private static final String PAYER_NAME = "payerName";
  private static final String BINDING_NUMBER = "bindingNumber";
  private static final String PAYROLLCHECKPROCESSID = "payrollCheckProcessId";
  private static final String CUSTOMER_DOCUMENT = "customerDocument";
  private static final String CUSTOMER_DOC_TYPE = "customerDocType";
  private static final String OBLIGATION_ID = "obligationID";
  private static final String TOKEN = "token";
  static PayrollHistoryRequests payrollHistoryRequests = new PayrollHistoryRequests();
  static LaboralCertificationRequests fileRequest = new LaboralCertificationRequests();
  static PayrollCheckRequests payrollCheckRequests = new PayrollCheckRequests();

  public void sendPayerSygnus(String customerDocument, String customerDocType, String payerName,
                              String token)
      throws MalformedURLException {
    Map<String, Object> generationBody = new HashMap<>();
    generationBody.put(DOCUMENT_NUMBER, customerDocument);
    generationBody.put(DOCUMENT_TYPE, customerDocType);
    generationBody.put(PAYER_NAME, payerName);
    var gson = new Gson();
    var gsonType = new TypeToken<HashMap<String, Object>>(){}.getType();
    var bodyString = gson.toJson(generationBody,gsonType);
    var encryptedBody = WprCipher.generateEncryptedBody(bodyString);

    SerenityRest.given()
            .and()
            .contentType(ContentType.JSON)
            .and()
            .header(
                    HeadersEnum.HEADER_X_SECURITY_HMAC.toString(),
                    WprCipher.generateSecurityHmac(generationBody.toString(), "POST"))
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
            .post(ServicePaths
                    .getBindings());
    var decryptedBody = WprCipher.decryptRequest(SerenityRest.lastResponse().then().extract().body().asString());
    if(decryptedBody.getString(BINDING_NUMBER)!= null)
      setInSession(SessionHelper.SessionData.BINDING_NUMBER,decryptedBody.getList(BINDING_NUMBER).get(0).toString().replaceAll("[\\[\\](){}]",""));
  }

  public void sendBindingSygnus(String customerDocument, String customerDocType, String obligationID, String payerName,
                                String token, String providerID)
      throws MalformedURLException {

    Map<String, Object> offerBody = new HashMap<>();
    offerBody.put(BINDING_NUMBER, getFromSession(SessionHelper.SessionData.BINDING_NUMBER));
    offerBody.put(DOCUMENT_NUMBER, customerDocument);
    offerBody.put(DOCUMENT_TYPE, customerDocType);
    offerBody.put("obligationId", obligationID);
    offerBody.put(PAYER_NAME, payerName);
    offerBody.put(PAYROLLCHECKPROCESSID, providerID);

    SerenityRest.given().relaxedHTTPSValidation()
        .header(HeadersEnum.AUTHORIZATION.toString(), token)
        .contentType(ContentType.JSON)
        .body(offerBody)
        .when().post(ServicePaths.getOfferSygnus());
    setInSession(SessionHelper.SessionData.PAYROLL_CHECK_PROCESS_ID,
        SerenityRest.lastResponse().jsonPath().getString("id"));
  }

  public void sygnusPayrollCheckProcess(JsonObject data, JsonPath otpJson) throws MalformedURLException, JsonProcessingException {
    if(getFromSession(SessionHelper.SessionData.OPTIMIZATION_TOGGLE_II).equals("")) {
      payrollCheckRequests.sendPayrollCheck(data.get(OBLIGATION_ID).getAsString(),
              data.get(CUSTOMER_DOC_TYPE).getAsString(),
              data.get(CUSTOMER_DOCUMENT).getAsString(),
              otpJson.getString(TOKEN));
    }else{
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

}