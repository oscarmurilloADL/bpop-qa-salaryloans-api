package co.com.bancopopular.automation.features.steps.accept;

import static net.serenitybdd.rest.SerenityRest.given;

import co.com.bancopopular.automation.rest.apis.ServicePaths;
import co.com.bancopopular.automation.utils.RequestsUtil;
import io.restassured.http.ContentType;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AcceptRequests {

  RequestsUtil requestsUtil = new RequestsUtil();
  private static final String MONTHS = "months";
  private static final String VALUE = "value";
  private static final String VALUE_NOMINAL_MONTH = "valueNominalMonth";
  private static final String SEGMENT_MODALITY = "segmentModality";

  public void acceptCreditData(String documentType, String documentNumber,
      String obligationID, String token)
      throws MalformedURLException {
    Map<String, Object> acceptCreditBody = new HashMap<>();
    Map<String, Object> segmentModalityBody = new HashMap<>();
    Map<String, Object> rateBody = new HashMap<>();
    Map<String, Object> rateBody2 = new HashMap<>();
    Map<String, Object> rateBody3 = new HashMap<>();
    Map<String, Object> rateBody4 = new HashMap<>();
    Map<String, Object> rateBody5 = new HashMap<>();

    segmentModalityBody.put("id",80);
    segmentModalityBody.put("segmentNumber",3);
    segmentModalityBody.put("modalityNumber",24);
    segmentModalityBody.put("conditionMinWage",1);
    segmentModalityBody.put("minAge",51);
    segmentModalityBody.put("maxAge",70);

    rateBody.put("id",80);
    rateBody.put(MONTHS,12);
    rateBody.put(VALUE,0.016970081333333335);
    rateBody.put(VALUE_NOMINAL_MONTH,0.0159);
    rateBody.put(SEGMENT_MODALITY,segmentModalityBody);

    rateBody2.put("id",186);
    rateBody2.put(MONTHS,24);
    rateBody2.put(VALUE,0.016970081333333335);
    rateBody2.put(VALUE_NOMINAL_MONTH,0.0159);
    rateBody2.put(SEGMENT_MODALITY,segmentModalityBody);

    rateBody3.put("id",292);
    rateBody3.put(MONTHS,39);
    rateBody3.put(VALUE,0.016970081333333335);
    rateBody3.put(VALUE_NOMINAL_MONTH,0.0159);
    rateBody3.put(SEGMENT_MODALITY,segmentModalityBody);

    rateBody4.put("id",398);
    rateBody4.put(MONTHS,48);
    rateBody4.put(VALUE,0.016970081333333335);
    rateBody4.put(VALUE_NOMINAL_MONTH,0.0159);
    rateBody4.put(SEGMENT_MODALITY,segmentModalityBody);

    rateBody5.put("id",504);
    rateBody5.put(MONTHS,60);
    rateBody5.put(VALUE,0.016970081333333335);
    rateBody5.put(VALUE_NOMINAL_MONTH,0.0159);
    rateBody5.put(SEGMENT_MODALITY,segmentModalityBody);

    List<Map<String, Object>> rates = new ArrayList<>();
    rates.add(rateBody);
    rates.add(rateBody2);
    rates.add(rateBody3);
    rates.add(rateBody4);
    rates.add(rateBody5);

    acceptCreditBody.put("documentType", documentType);
    acceptCreditBody.put("documentNumber", Integer.parseInt(documentNumber));
    acceptCreditBody.put("obligationId", obligationID);
    acceptCreditBody.put("totalDebt", 17410836.776333332);
    acceptCreditBody.put("requesAmount", 11855000);
    acceptCreditBody.put("obligationBalance", 5555836.776333333);
    acceptCreditBody.put("feeAmount", 464815.01863302046);
    acceptCreditBody.put("feeNumber", 60);
    acceptCreditBody.put("interestRate", 0.016970081333333335);
    acceptCreditBody.put("maxLoanAmount", 11855000);
    acceptCreditBody.put("minLoanTerm", 60);
    acceptCreditBody.put("maxLoanTerm", 60);
    acceptCreditBody.put("loanQuota", 464842);
    acceptCreditBody.put("payerId", "2 - Alcaldia - Municipios Docentes");
    acceptCreditBody.put("payerName", "SECRETARIA DE EDUCACION MUNICIPAL DE GIRON - GIRON - 481 -");
    acceptCreditBody.put("payerUniqueName", "SECRETARIA DE EDUCACION MUNICIPAL DE GIRON");
    acceptCreditBody.put("payerWeb", "http://rrhh.gestionsecretariasdeeducacion.gov.co:2383/humanoEL/Ingresar.aspx?Ent=Giron\\");
    acceptCreditBody.put("payerLocationCode", "2 - Alcaldia - Municipios Docentes");
    acceptCreditBody.put("nominalRate", 2);
    acceptCreditBody.put("anualRate", 2);
    acceptCreditBody.put("otherAmountAccepted", 2);
    acceptCreditBody.put("rates", rates);
    acceptCreditBody.put("creditStudy", 0);
    acceptCreditBody.put("adjustmentInterests", 0);
    acceptCreditBody.put("adjustmentInsurancePremium", 0);
    acceptCreditBody.put("advancedInterests", 0);
    acceptCreditBody.put("advancedInsurancePremium", 0);
    acceptCreditBody.put("discounts", 0);
    acceptCreditBody.put("disbursement", 0);
    acceptCreditBody.put("authMethod", "");
    acceptCreditBody.put("responseAuthMethod", "");

    given().relaxedHTTPSValidation().header("Authorization", token)
        .contentType(ContentType.JSON)
        .body(acceptCreditBody).when().post(ServicePaths.getLoanAccept());


  }
}
