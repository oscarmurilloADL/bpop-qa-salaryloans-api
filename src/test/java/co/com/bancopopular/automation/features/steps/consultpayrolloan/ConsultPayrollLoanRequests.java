package co.com.bancopopular.automation.features.steps.consultpayrolloan;

import static net.serenitybdd.rest.SerenityRest.given;

import co.com.bancopopular.automation.rest.apis.ServicePaths;
import co.com.bancopopular.automation.utils.RequestsUtil;
import io.restassured.path.json.JsonPath;
import java.net.MalformedURLException;

public class ConsultPayrollLoanRequests {

  RequestsUtil requestsUtil = new RequestsUtil();

  public void getPayrollLoan(String advisorDocument, String customerDocType, String customerDocument)
      throws MalformedURLException {
    JsonPath authResponse = requestsUtil.getAuthOTP();
    given().relaxedHTTPSValidation().header("Authorization", authResponse.getString("token"))
        .contentType("application/json")
        .when()
        .get(ServicePaths.getPayrollloanStatus(advisorDocument, customerDocType, customerDocument));
  }
}