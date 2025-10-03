package co.com.bancopopular.automation.features.steps.crm;

import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static net.serenitybdd.rest.SerenityRest.given;

import co.com.bancopopular.automation.rest.apis.ServicePaths;
import co.com.bancopopular.automation.utils.HeadersEnum;
import co.com.bancopopular.automation.utils.RequestsUtil;
import co.com.bancopopular.automation.utils.SessionHelper;
import co.com.bancopopular.automation.utils.WprCipher;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class CrmRequests {

  RequestsUtil requestsUtil = new RequestsUtil();
  private static final String TOKEN = "token";

  public void consultCustomer(String customerDoctype, String customerDocument)
      throws MalformedURLException {
    var authResponse = requestsUtil.getAuthOTP();
    given().relaxedHTTPSValidation().header(HeadersEnum.AUTHORIZATION.toString(), authResponse.getString(TOKEN))
        .contentType(ContentType.JSON)
        .when().get(ServicePaths.getInfoClient(customerDoctype, customerDocument));
  }

  public void updateCustomer(String customerDoctype, String customerDocument, String token)
      throws MalformedURLException {
    Map<String, Object> updateCustomerBody = new HashMap<>();
    List<Map<String, Object>> products = new ArrayList<>();
    Map<String, Object> product = new HashMap<>();

    product.put("accountId","230476515678");
    product.put("accountType","SDA");
    product.put("bankId","0002");

    products.add(product);

    updateCustomerBody.put("name","LAURA MILENA");
    updateCustomerBody.put("lastName","MARTINEZ");
    updateCustomerBody.put("cellNumber","3163842527");
    updateCustomerBody.put("email","laurabancopopular1@gmail.com");
    updateCustomerBody.put("cityId","13-13006");
    updateCustomerBody.put("cityName",null);
    updateCustomerBody.put("stateId","13");
    updateCustomerBody.put("stateProvName","");
    updateCustomerBody.put("countryId","COL");
    updateCustomerBody.put("countryName","COL");
    updateCustomerBody.put("fullAddress","LOS ANGELES CARRERA 60 CA #30-88");
    updateCustomerBody.put("streetType","");
    updateCustomerBody.put("streetName","");
    updateCustomerBody.put("streetNumber","");
    updateCustomerBody.put("phoneNumber", 4412551);
    updateCustomerBody.put("monthlyIncome", 1000000);
    updateCustomerBody.put("additionalIncome", 100000);
    updateCustomerBody.put("monthlyExpenses", 10000000);
    updateCustomerBody.put("assets", 38000000);
    updateCustomerBody.put("debts", 0);
    updateCustomerBody.put("economicActivity", "10");
    updateCustomerBody.put("maritalStatus", "4");
    updateCustomerBody.put("maritalStatusDesc", null);
    updateCustomerBody.put("educationLevelCod", 3);
    updateCustomerBody.put("professionCode", "6");
    updateCustomerBody.put("peopleInChargeOf", "1");
    updateCustomerBody.put("occupation", "E");
    updateCustomerBody.put("products", products);
    updateCustomerBody.put("identityType", customerDoctype);
    updateCustomerBody.put("identityNumber", customerDocument);

    given().relaxedHTTPSValidation().header(HeadersEnum.AUTHORIZATION.toString(), token)
        .contentType(ContentType.JSON)
        .body(updateCustomerBody).when().put(ServicePaths.getUpdateClient());
  }

  public void askMaritalStatus()
      throws MalformedURLException {
    var authResponse = requestsUtil.getAuthOTP();
    given().relaxedHTTPSValidation().header(HeadersEnum.AUTHORIZATION.toString(), authResponse.getString(TOKEN))
        .contentType(ContentType.JSON)
        .when().get(ServicePaths.getCombosMaritalStatus());
  }

  public void consultCity()
      throws MalformedURLException {
    var authResponse = requestsUtil.getAuthOTP();
    given().relaxedHTTPSValidation().header(HeadersEnum.AUTHORIZATION.toString(), authResponse.getString(TOKEN))
        .contentType(ContentType.JSON)
        .when().get(ServicePaths.getCombosCity());
  }

  public void consultPayrollCheckCnc(String customerDocType, String customerDocument, String clientInfo, String payerNit,
                                     String sectorNumber, String clientType)
          throws MalformedURLException {
    var authResponse = requestsUtil.getAuthOTP();
    given().relaxedHTTPSValidation().header(HeadersEnum.AUTHORIZATION.toString(), authResponse.getString(TOKEN))
            .contentType(ContentType.JSON)
            .when().get(ServicePaths.getPayrollCheckCnc(customerDocType, customerDocument, clientInfo, payerNit, sectorNumber, clientType));
  }

  public void offering(String clientInfo, String customerDocument, String payerNit, String clientType, String subSector)
          throws MalformedURLException {
    Map<String, Object> body = new HashMap<>();

    body.put("documentType","CC");
    body.put("documentNumber",customerDocument);
    body.put("obligationId",clientInfo);
    body.put("requiredAmount",0);
    body.put("loanTerm",0);
    body.put("payrollCheckProcessId",customerDocument);
    body.put("modalityType","ORDINARY");
    body.put("campaignType",null);
    body.put("processType","ANY_FEE");
    body.put("clientType",clientType);
    body.put("payerNit",payerNit);
    body.put("sectorNumber",2);
    body.put("subSectorNumber",subSector);

    var gson = new Gson();
    var gsonType = new TypeToken<HashMap<String, Object>>(){}.getType();
    var bodyString = gson.toJson(body,gsonType);
    var encryptedBody = WprCipher.generateEncryptedBody(bodyString);

    var authResponse = requestsUtil.getAuthOTP();

    SerenityRest.given()
            .and()
            .contentType(ContentType.JSON)
            .and()
            .header(
                    HeadersEnum.HEADER_X_SECURITY_HMAC.toString(),
                    WprCipher.generateSecurityHmac(body.toString(), "POST"))
            .and()
            .header(
                    HeadersEnum.HEADER_X_SECURITY_SESSION.toString(), getFromSession(SessionHelper.SessionData.XSECURITYSESSION))
            .and()
            .header(
                    HeadersEnum.HEADER_X_SECURITY_REQUEST_ID.toString(), getFromSession(SessionHelper.SessionData.XSECURITYREQUESTID))
            .and()
            .header(
                    HeadersEnum.AUTHORIZATION.toString(), authResponse.getString(TOKEN))
            .and()
            .body(encryptedBody)
            .when()
            .post(ServicePaths.postOffering());
  }
}