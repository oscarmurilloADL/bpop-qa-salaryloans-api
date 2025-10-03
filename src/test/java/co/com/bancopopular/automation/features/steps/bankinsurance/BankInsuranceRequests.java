package co.com.bancopopular.automation.features.steps.bankinsurance;

import static co.com.bancopopular.automation.constants.Constants.CONDITIONS_TOKEN;
import static co.com.bancopopular.automation.utils.SessionHelper.setInSession;
import static net.serenitybdd.rest.SerenityRest.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.rest.apis.ServicePaths;
import co.com.bancopopular.automation.rest.requests.ValidationsRequests;
import co.com.bancopopular.automation.utils.RequestsUtil;
import co.com.bancopopular.automation.utils.SessionHelper;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.rest.SerenityRest;
import org.apache.commons.httpclient.HttpStatus;

import java.net.MalformedURLException;
import java.util.HashMap;

public class BankInsuranceRequests {

  RequestsUtil requestsUtil = new RequestsUtil();
  ValidationsRequests validationsRequests = new ValidationsRequests();
  private static final String ADL="ADL";

  public void getOffering(String customerDocType,
      String customerDocument, String obligationID, String disbursementAmount, String token)
      throws MalformedURLException {
    HashMap<String, String> bodyRequest = new HashMap<>();
    bodyRequest.put("documentType", customerDocType);
    bodyRequest.put("documentNumber", customerDocument);
    bodyRequest.put("disbursementAmount", disbursementAmount);
    bodyRequest.put("obligationId", obligationID);

    given().relaxedHTTPSValidation().header(Constants.AUTHORIZATION, token)
        .contentType(ContentType.JSON)
        .body(bodyRequest)
        .when().post(ServicePaths.getBankInsurance(customerDocType, customerDocument));
  }

  public void getOfferingForCNC(String customerDocType,
                          String customerDocument, String obligationID, String disbursementAmount,
                                String clientType, String feeNumber, String sectorNumber)
          throws MalformedURLException {
    JsonPath authResponse = requestsUtil.getAuthOTP();
    HashMap<String, String> bodyRequest = new HashMap<>();
    bodyRequest.put("documentType", customerDocType);
    bodyRequest.put("documentNumber", customerDocument);
    bodyRequest.put("disbursementAmount", disbursementAmount);
    bodyRequest.put("clientType", clientType);
    bodyRequest.put("feeNumber", feeNumber);
    bodyRequest.put("sectorNumber", sectorNumber);
    bodyRequest.put("obligationId", obligationID);
    given().relaxedHTTPSValidation().header(Constants.AUTHORIZATION, authResponse.getString("token"))
            .contentType("application/json")
            .body(bodyRequest)
            .when().post(ServicePaths.getBankInsurance(customerDocType, customerDocument));
  }

  public void getTermsAndConditions(String plan,String token) throws MalformedURLException {
    given().relaxedHTTPSValidation().header(Constants.AUTHORIZATION, token)
            .contentType("application/json")
            .header("x-adl-app-name",ADL)
            .and()
            .header("x-adl-channel",ADL)
            .and()
            .header("x-adl-transaction-id","9112124")
            .and()
            .when().get(ServicePaths.getTermsConditions(plan));
    setInSession(SessionHelper.SessionData.PLAN, plan);
  }

  public void getTermsConditionsToken() throws MalformedURLException {
    validationsRequests.getTokenExternalViability(CONDITIONS_TOKEN);
    assertThat(SerenityRest.then().extract().statusCode(), equalTo(HttpStatus.SC_OK));
  }
}