package co.com.bancopopular.automation.features.steps.tap;

import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static co.com.bancopopular.automation.utils.SessionHelper.setInSession;
import static co.com.bancopopular.automation.utils.awssignaturev4.AwsSignatureV4.getAWSToken;
import static java.util.concurrent.TimeUnit.SECONDS;
import static net.serenitybdd.rest.SerenityRest.given;
import static org.awaitility.Awaitility.await;

import co.com.bancopopular.automation.models.tap.Load;
import co.com.bancopopular.automation.rest.apis.ServicePaths;
import co.com.bancopopular.automation.utils.HeadersEnum;
import co.com.bancopopular.automation.utils.SessionHelper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.concurrent.Callable;

public class PayrollCheckRequests {

  private Response response;
  private String idTap;
  private String obligationID;
  private String customerDocType;
  private String customerDocument;
  private static final String PAYROLL_CHECK_IN_PROGRESS = "PayrollChecks005";

  public void sendPayrollCheck(String obligationID, String customerDocType, String customerDocument, String token)
      throws MalformedURLException {
    given().relaxedHTTPSValidation()
        .header(HeadersEnum.AUTHORIZATION.toString(), token)
        .contentType(ContentType.JSON)
        .when().get(ServicePaths.getPayrollCheck(obligationID, customerDocType, customerDocument));
    setInSession(SessionHelper.SessionData.PAYROLL_CHECK_PROCESS_ID,
        SerenityRest.lastResponse().jsonPath().getString("id"));
  }

  public void sendPayrollCheck(String obligationID,Load bodyLoad, String journeyID, String token, String documentNumber) throws IOException {
    bodyLoad.setJourneyId(journeyID);
    var canonicalURL="/security-manager/file/url/"+obligationID+"_CC_"+documentNumber+".pdf/file.featurePayrollCheck";
    Map<String, String> header =getAWSToken(canonicalURL,"GET",null);

    given().relaxedHTTPSValidation()
            .header(HeadersEnum.AUTHORIZATION.toString(), header.get("Authorization"))
            .header("X-Amz-Date",header.get("x-amz-date"))
            .header("X-Amz-Security-Token",getFromSession(SessionHelper.SessionData.SESSION_TOKEN))
            .contentType("application/x-www-form-urlencoded")
            .when().get(ServicePaths.getFile(obligationID,bodyLoad.getLoanData().getClientDocumentNumber()));

    given().relaxedHTTPSValidation()
            .header(HeadersEnum.AUTHORIZATION.toString(), token)
            .contentType(ContentType.JSON)
            .body(bodyLoad)
            .when().post(ServicePaths.getLoad());
  }

  public void checkStatusPayrollCheck(String idTap, String obligationID, String customerDocType,
      String customerDocument, String token)
      throws MalformedURLException {
    this.idTap = idTap;
    this.obligationID = obligationID;
    this.customerDocType = customerDocType;
    this.customerDocument = customerDocument;
    await().atMost(200, SECONDS)
        .until(verifyPayrollCheckIsInProgress(token));
    getPayrollStatus(token);
  }

  public void checkStatusPayrollCheckWithoutAwait(String idTap, String obligationID, String customerDocType,
                                      String customerDocument, String token)
          throws MalformedURLException {
    this.idTap = idTap;
    this.obligationID = obligationID;
    this.customerDocType = customerDocType;
    this.customerDocument = customerDocument;
    getPayrollStatus(token);
  }

  private Callable<Boolean> verifyPayrollCheckIsInProgress(String token) {
    return () -> {
      response = getPayrollStatus(token);
      var codeResponse = response.then().extract().body().jsonPath().getString("code");
      if (codeResponse != null) {
        return codeResponse.equals(PAYROLL_CHECK_IN_PROGRESS);
      }
      return false;
    };
  }

  private Response getPayrollStatus(String token) throws MalformedURLException {
    return given().relaxedHTTPSValidation()
        .header(HeadersEnum.AUTHORIZATION.toString(), token)
        .contentType(ContentType.JSON)
        .when()
        .get(ServicePaths
            .getPayrollCheckStatus(this.idTap, this.obligationID, this.customerDocType,
                this.customerDocument));
  }

  public void getSimulationStatus(String[] customerInfo, String payerNit, String sectorNumber,String subSectorNumber ,String providerID, String token) throws MalformedURLException {
    String documentTypeInfo = customerInfo[0];
    String customerDocumentInfo = customerInfo[1];
    String obligationIDInfo = customerInfo[2];

    Map<String, Object> bodySimulation = new HashMap<>();
    var providerTAP = new String[1];
    providerTAP[0]=providerID;
    bodySimulation.put("documentType",documentTypeInfo);
    bodySimulation.put("documentNumber",customerDocumentInfo);
    bodySimulation.put("obligationId",obligationIDInfo);
    bodySimulation.put("payerNit",payerNit);
    bodySimulation.put("providerId",providerTAP);
    bodySimulation.put("sectorNumber",sectorNumber);
    bodySimulation.put("subSectorNumber",subSectorNumber);

    given().relaxedHTTPSValidation()
            .header(HeadersEnum.AUTHORIZATION.toString(), token)
            .contentType(ContentType.JSON)
            .body(bodySimulation)
            .when().post(ServicePaths.getSimulation());

  }


}