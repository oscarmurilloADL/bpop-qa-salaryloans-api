package co.com.bancopopular.automation.rest.requests;

import static net.serenitybdd.rest.SerenityRest.given;
import co.com.bancopopular.automation.rest.apis.ServicePaths;
import co.com.bancopopular.automation.utils.DataUserInstance;
import co.com.bancopopular.automation.utils.HeadersEnum;
import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LegalConditionsRequest {

  ValidationsRequests validationsRequests = new ValidationsRequests();
  private static final String DOCUMENT_TYPE ="documentType";
  private static final String DOCUMENT_NUMBER ="documentNumber";
  private static final String TERM_CONDITIONS_VERSION ="termsAndConditionsVersion";
  private static final String ACCEPTANCE ="acceptance";
  private static final String ADVISOR_DECISION_TYPE ="advisorDecisionType";
  private static final String ADVISOR_DOCUMENT_NUMBER="advisorDocumentNumber";
  private static final String VERSION="V5 16MARZ2021";
  private static final String NEW_PAYROLL_LOAN="NewPayrollLoan";
  private static final String ADVISOR_DOCUMENT="advisorDocument";
  private static final String ADVISOR_JOURNEY_ID="advisorJourneyId";

  public void getDataTreatment(String customerDocType, String customerDocument, String acceptance, String advisorJourneyId)
      throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();
    validationsRequests.sendValidateInternalViability(data.get("customerDocType").getAsString(),
        data.get("customerDocument").getAsString());
    Map<String, Object> dataTreatmentBody = new HashMap<>();
    dataTreatmentBody.put(ADVISOR_JOURNEY_ID, advisorJourneyId);
    dataTreatmentBody.put(DOCUMENT_TYPE, customerDocType);
    dataTreatmentBody.put(DOCUMENT_NUMBER, customerDocument);
    dataTreatmentBody.put(TERM_CONDITIONS_VERSION, VERSION);
    dataTreatmentBody.put(ACCEPTANCE, acceptance);
    dataTreatmentBody.put(ADVISOR_DECISION_TYPE,NEW_PAYROLL_LOAN);
    dataTreatmentBody.put(ADVISOR_DOCUMENT_NUMBER,data.get(ADVISOR_DOCUMENT).getAsString());

    given().relaxedHTTPSValidation().contentType(ContentType.JSON).body(dataTreatmentBody).when()
        .post(ServicePaths.getTermsAndConditions(customerDocType, customerDocument));
  }

  public void getBureauReport(String customerDocType, String customerDocument, String acceptance,String advisorJourneyId)
      throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();
    Map<String, Object> bureauReportBody = new HashMap<>();
    bureauReportBody.put(ADVISOR_JOURNEY_ID, advisorJourneyId);
    bureauReportBody.put(DOCUMENT_TYPE, customerDocType);
    bureauReportBody.put(DOCUMENT_NUMBER, customerDocument);
    bureauReportBody.put(TERM_CONDITIONS_VERSION, VERSION);
    bureauReportBody.put(ACCEPTANCE, acceptance);
    if(customerDocument.equals("400166")){
      bureauReportBody.put(ADVISOR_DECISION_TYPE, "PayrollLoanRepricing");
    }else {
      bureauReportBody.put(ADVISOR_DECISION_TYPE, NEW_PAYROLL_LOAN);
    }
    bureauReportBody.put(ADVISOR_DOCUMENT_NUMBER,data.get(ADVISOR_DOCUMENT).getAsString());
    given().relaxedHTTPSValidation().contentType("application/json").body(bureauReportBody).when()
        .post(ServicePaths.getCreditBureauReport(customerDocType, customerDocument));
  }

  public void sendTermsAndConditions(JsonObject data, JsonPath otpJson, List<Object> onBaseDocuments)
          throws MalformedURLException {

    Map<String, Object> conditionsBody = new HashMap<>();
    conditionsBody.put("identificationType", data.get("customerDocType").getAsString());
    conditionsBody.put("identificationNumber", data.get("customerDocument").getAsString());
    conditionsBody.put("officeCode", data.get("officeCode").getAsString());
    conditionsBody.put("sellerIdNumber", data.get(ADVISOR_DOCUMENT).getAsString());
    conditionsBody.put("accountType", "DDA");
    conditionsBody.put("accountNumber", "110-038-000186-4");
    conditionsBody.put("generatedId", otpJson.getString("generatedId"));
    conditionsBody.put("insuranceCode", "120901");
    conditionsBody.put("validationMethod", "Certihuella");
    conditionsBody.put("onBaseDocuments", onBaseDocuments);
    conditionsBody.put("coverageSpouse", false);
    conditionsBody.put("maxCoverage", true);
    conditionsBody.put("creditStudy", 117110);
    conditionsBody.put("adjustmentInterests", 924650);
    conditionsBody.put("adjustmentInsurancePremium", 298282);
    conditionsBody.put("accidentInsurancePrice", "1010784");
    conditionsBody.put("totalDiscounts", 2350826);

    given().relaxedHTTPSValidation()
        .header(HeadersEnum.AUTHORIZATION.toString(),otpJson.getString("token"))
        .contentType(ContentType.JSON).body(conditionsBody).when()
        .post(ServicePaths.sendTermsAndConditions(data.get("obligationID").getAsString()));
  }
}