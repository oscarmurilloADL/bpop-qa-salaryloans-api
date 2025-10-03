package co.com.bancopopular.automation.features.steps.anyfeesimulation;

import static co.com.bancopopular.automation.constants.Constants.*;
import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static co.com.bancopopular.automation.utils.SessionHelper.setInSession;
import static java.util.concurrent.TimeUnit.SECONDS;
import static net.serenitybdd.rest.SerenityRest.given;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import co.com.bancopopular.automation.models.extractor.ItemDynamoExtraction;
import co.com.bancopopular.automation.rest.apis.ServicePaths;
import co.com.bancopopular.automation.utils.HeadersEnum;
import co.com.bancopopular.automation.utils.RequestsUtil;
import co.com.bancopopular.automation.utils.SessionHelper;
import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.annotations.Step;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;


public class AnyFeeSimulationRequests {

  private String idTap;
  private String obligationID;
  private String customerDocType;
  private String customerDocument;
  private Response response;
  RequestsUtil requestsUtil = new RequestsUtil();

  public void checkProcessIsFinished(String idTap, String obligationID, String customerDocType,
      String customerDocument) {
    this.idTap = idTap;
    this.obligationID = obligationID;
    this.customerDocType = customerDocType;
    this.customerDocument = customerDocument;

    await().atMost(40, SECONDS).pollInSameThread().until(verifyPayrollCheckIsFinished());
  }

  public void checkProcessHasError(String idTap, String obligationID, String customerDocType,
                                     String customerDocument) {
    this.idTap = idTap;
    this.obligationID = obligationID;
    this.customerDocType = customerDocType;
    this.customerDocument = customerDocument;

    await().atMost(40, SECONDS).pollInSameThread().until(verifyPayrollCheckHasError());
  }

  public void recalculateAmountsAnyFee(String obligationID, String customerDocType,
      String customerDocument)
          throws MalformedURLException, InterruptedException {
    JsonPath authResponse = requestsUtil.getAuthOTP();
    given().relaxedHTTPSValidation()
        .header("Authorization", authResponse.getString("token"))
        .contentType("application/json")
        .body("{" +
            "  \"documentType\": \"" + customerDocType + "\"," +
            "  \"documentNumber\": " + customerDocument + " ," +
            "  \"obligationId\": \"" + obligationID + "\"," +
            "  \"requiredAmount\":65268000," +
            "  \"loanTerm\":\"120\"," +
            "  \"transactionId\":\"44\"}")
        .when()
        .post(ServicePaths
            .getSimulationAnyFee(customerDocType, customerDocument));
  }


  private Callable<Boolean> verifyPayrollCheckIsFinished() {
    return () -> {
      response = getPayrollStatus();
      var maxLoanAmountResponse = response.then().extract().body().jsonPath()
          .getString("maxLoanAmount");
      var payrollCheckProcessId = response.then().extract().body().jsonPath()
          .getString("id");
      setInSession(SessionHelper.SessionData.PAYROLL_CHECK_PROCESS_ID, payrollCheckProcessId);
      if (maxLoanAmountResponse != null) {
        return maxLoanAmountResponse.equals("63529000");
      }
      return false;
    };
  }

  private Callable<Boolean> verifyPayrollCheckHasError() {
    return () -> {
      response = getPayrollStatus();
      var codeResponse = response.then().extract().body().jsonPath()
          .getString("code");
      var payrollCheckProcessId = response.then().extract().body().jsonPath()
          .getString("id");
      setInSession(SessionHelper.SessionData.PAYROLL_CHECK_PROCESS_ID, payrollCheckProcessId);
      if (codeResponse != null ) {
        return codeResponse.equals("PayrollChecks9999");
      }
      return false;
    };
  }

  private Response getPayrollStatus() throws MalformedURLException {
    return given().relaxedHTTPSValidation()
        .header(HeadersEnum.AUTHORIZATION.toString(), getFromSession(SessionHelper.SessionData.TOKEN))
        .contentType(ContentType.JSON)
        .when()
        .get(ServicePaths
            .getPayrollCheckStatus(this.idTap, this.obligationID, this.customerDocType, this.customerDocument));

  }

    public void callCNCSimulation(JsonObject data, String token) throws MalformedURLException {
      do {
        SerenityRest.given()
                .contentType(ContentType.JSON)
                .header(HeadersEnum.AUTHORIZATION.toString(), token)
                .body(bodyCNCSimulation(data))
                .when()
                .post(ServicePaths.getSimulationCNC());
      } while (SerenityRest.then().extract().statusCode() == 504);
    }

    private String bodyCNCSimulation(JsonObject data){
      return "{" +
              "    \"documentNumber\": \""+data.get(CUSTOMER_DOCUMENT).getAsString()+"\"," +
              "    \"documentType\": \""+data.get(CUSTOMER_DOC_TYPE).getAsString()+"\"," +
              "    \"obligationId\": \""+data.get("clientInfo").getAsString()+"\"," +
              "    \"payerNit\": \""+data.get(PAYER_NIT).getAsString()+"\"," +
              "    \"providerId\": [" +
              "        \""+data.get(CUSTOMER_DOCUMENT).getAsString()+"\""+
              "    ]," +
              "    \"sectorNumber\": \""+data.get(SECTOR_NUMBER).getAsString()+"\"," +
              "    \"subSectorNumber\": \""+data.get(SUB_SECTOR_NUMBER).getAsString()+"\"" +
              "}";
    }
  @Step("Validacion request del simuador")
  public void validateSimulationRequest(String simulationRequestLog, ItemDynamoExtraction dataForValidate) {
    RequestsUtil.addLogInSerenityReport("Datos encontrados en el request",simulationRequestLog);
    Map<String, Object> validationMap = new HashMap<>();
    validationMap.put("<ifx:Salary>("+REGEX+"+)</ifx:Salary>", dataForValidate.getInterpreterResult().getPayrollLoanInterpreterInfo().getSalary());
    validationMap.put("<ifx:Bonuses>("+REGEX+"+)</ifx:Bonuses>", dataForValidate.getInterpreterResult().getPayrollLoanInterpreterInfo().getBonuses());
    validationMap.put("<ifx:Overtime>("+REGEX+"+)</ifx:Overtime>", CERO);
    validationMap.put("<ifx:ExtraPayment>("+REGEX+"+)</ifx:ExtraPayment>", CERO);
    validationMap.put("<ifx:Honorarium>("+REGEX+"+)</ifx:Honorarium>", CERO);
    validationMap.put("<ifx:TotalOtherIncome>("+REGEX+"+)</ifx:TotalOtherIncome>", CERO);
    validationMap.put("<ifx:HealthDiscount>("+REGEX+"+)</ifx:HealthDiscount>", dataForValidate.getInterpreterResult().getPayrollLoanInterpreterInfo().getHealthDiscount());
    validationMap.put("<ifx:PensionDiscount>("+REGEX+"+)</ifx:PensionDiscount>", dataForValidate.getInterpreterResult().getPayrollLoanInterpreterInfo().getPensionDiscount());
    validationMap.put("<ifx:OtherLawDiscount>("+REGEX+"+)</ifx:OtherLawDiscount>", dataForValidate.getInterpreterResult().getPayrollLoanInterpreterInfo().getOtherLawDiscount());
    validationMap.put("<ifx:OtherDiscount1>("+REGEX+"+)</ifx:OtherDiscount1>", dataForValidate.getInterpreterResult().getPayrollLoanInterpreterInfo().getOtherDiscount1());
    validationMap.put("<ifx:OtherDiscount2>("+REGEX+"+)</ifx:OtherDiscount2>", dataForValidate.getInterpreterResult().getPayrollLoanInterpreterInfo().getOtherDiscount2());
    validationMap.put("<ifx:OtherDiscount3>("+REGEX+"+)</ifx:OtherDiscount3>", CERO);
    validationMap.put("<ifx:Payment>("+REGEX+"+)</ifx:Payment>", dataForValidate.getPayerNit());
    validationMap.put("<ifx:Sector>("+REGEX+"+)</ifx:Sector>", dataForValidate.getSectoreCode());
    validationMap.put("<ifx:ModalityType>(ORD)</ifx:ModalityType>", "ORD");
    validationMap.put("<ifx:ProcessType>(STC)</ifx:ProcessType>", "STC");
    validationMap.put("<ifx:RequiredAmount>("+REGEX+"+)</ifx:RequiredAmount>", CERO);
    validationMap.put("<ifx:RequestDeadLine>("+REGEX+"+)</ifx:RequestDeadLine>", CERO);
    validationMap.forEach((regex, expectedValue) -> {
      var actualValue = searchSimulationData(simulationRequestLog, regex);
      assertThat(actualValue, is(expectedValue));
    });
  }

  private String searchSimulationData(String log, String regex) {
    var pattern = Pattern.compile(regex);
    var matcher = pattern.matcher(log);
    return matcher.find() ? matcher.group(1) : null;
  }

}