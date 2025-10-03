package co.com.bancopopular.automation.features.steps.payrollhistory;

import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static net.serenitybdd.rest.SerenityRest.given;
import static org.assertj.core.api.Assertions.assertThat;

import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.rest.apis.ServicePaths;
import co.com.bancopopular.automation.utils.HeadersEnum;
import co.com.bancopopular.automation.utils.RequestsUtil;
import co.com.bancopopular.automation.utils.SessionHelper;
import co.com.bancopopular.automation.utils.WprCipher;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.actors.OnStage;
import org.apache.http.HttpStatus;
import java.net.MalformedURLException;

public class  PayrollHistoryRequests {

  private static final String AUTHORIZATION = "Authorization";
  private static final String APPLICATIONJSON = "application/json";

  RequestsUtil requestsUtil = new RequestsUtil();

  public void getPayrollStatus(String customerDocType, String customerDocument, String token)
      throws MalformedURLException {
      for(var x = 0 ; x <= 5 ; x++) {

        given().relaxedHTTPSValidation().header(AUTHORIZATION, token)
                .contentType(APPLICATIONJSON)
                .and()
                .header(
                        HeadersEnum.HEADER_X_SECURITY_SESSION.toString(), getFromSession(SessionHelper.SessionData.XSECURITYSESSION))
                .and()
                .header(
                        HeadersEnum.HEADER_X_SECURITY_REQUEST_ID.toString(), getFromSession(SessionHelper.SessionData.XSECURITYREQUESTID))
                .when().get(ServicePaths.getPayrollStatus(customerDocType, customerDocument));
        if(SerenityRest.then().extract().statusCode() == HttpStatus.SC_OK) {
          break;
        }
      }
      RequestsUtil.addResponseInSerenityReport("Response loan/status:");

  }

  public void getPayrollStatusWithRetry(String customerDocType, String customerDocument, String token)
          throws MalformedURLException {

    for (var i=0;i<2;i++)
    {
      given().relaxedHTTPSValidation().header(AUTHORIZATION, token)
              .contentType(APPLICATIONJSON)
              .and()
              .header(
                      HeadersEnum.HEADER_X_SECURITY_SESSION.toString(), getFromSession(SessionHelper.SessionData.XSECURITYSESSION))
              .and()
              .header(
                      HeadersEnum.HEADER_X_SECURITY_REQUEST_ID.toString(), getFromSession(SessionHelper.SessionData.XSECURITYREQUESTID))
              .when().get(ServicePaths.getPayrollStatus(customerDocType, customerDocument));
      var encryptedBody = SerenityRest.then().extract().body().asString();
      var decryptedBody = WprCipher.decryptRequest(encryptedBody);

      OnStage.theActorInTheSpotlight().remember(Constants.NUM_OBLIGATION_ID, decryptedBody.get(Constants.FIELD_OBLIGATION_ID).toString().replace("[", "").replace("]", ""));
      if(SerenityRest.then().extract().statusCode() == HttpStatus.SC_OK)
        break;
    }
  }

  public void getPayrollInfo(String customerDocType, String customerDocument)
      throws MalformedURLException {
    var authResponse = requestsUtil.getAuthOTP();
    given().relaxedHTTPSValidation().header(AUTHORIZATION, authResponse.getString("token"))
        .contentType(APPLICATIONJSON)
        .when().get(ServicePaths.getPayrollInfo(customerDocType, customerDocument));
  }
}