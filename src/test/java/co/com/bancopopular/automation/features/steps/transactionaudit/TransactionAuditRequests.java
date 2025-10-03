package co.com.bancopopular.automation.features.steps.transactionaudit;

import static net.serenitybdd.rest.SerenityRest.given;

import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.rest.apis.ServicePaths;
import co.com.bancopopular.automation.utils.HeadersEnum;
import co.com.bancopopular.automation.utils.QuestionEventEnum;
import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class TransactionAuditRequests {

  private static final String BUSINESS_ADVISOR_NUMBER = "businessAdvisorNumber";
  private static final String DESCRIPTION = "description";
  private static final String DOCUMENT_NUMBER = "documentNumber";
  private static final String DOCUMENT_TYPE = "documentType";
  private static final String EVENT_CODE = "eventCode";

  public void getQuestion(String advisorDocument, String customerDocument, String customerDocType,
                          String officeCode, String eventCodeRequest, String eventCodeResponse, String token)
      throws MalformedURLException {
    Map<String, Object> dataQuestion = new HashMap<>();
    dataQuestion.put(BUSINESS_ADVISOR_NUMBER, advisorDocument);
    dataQuestion.put(DESCRIPTION, "SalaryLoansOverview,Pep,CarouselSteps");
    dataQuestion.put(DOCUMENT_NUMBER, customerDocument);
    dataQuestion.put(DOCUMENT_TYPE, customerDocType);
    dataQuestion.put(EVENT_CODE, eventCodeRequest);
    dataQuestion.put(Constants.OFFICE_ID, officeCode);
    given().relaxedHTTPSValidation().header(HeadersEnum.AUTHORIZATION.toString(), token)
        .contentType(ContentType.JSON)
        .body(dataQuestion)
        .when()
        .post(ServicePaths.getTransactionAudit());
    Map<String, Object> dataResponse = new HashMap<>();
    dataResponse.put(BUSINESS_ADVISOR_NUMBER, advisorDocument);
    dataResponse.put(DESCRIPTION, "No");
    dataResponse.put(DOCUMENT_NUMBER, customerDocument);
    dataResponse.put(DOCUMENT_TYPE, customerDocType);
    dataResponse.put(EVENT_CODE, eventCodeResponse);
    dataResponse.put(Constants.OFFICE_ID, officeCode);
    given().relaxedHTTPSValidation().header(HeadersEnum.AUTHORIZATION.toString(), token)
        .contentType(ContentType.JSON)
        .body(dataResponse)
        .when()
        .post(ServicePaths.getTransactionAuditAnalytics());
  }

  public void sendTransactionAuditAnalytics(JsonObject data, String token, String description,
    String eventCode) throws MalformedURLException {
    Map<String, Object> dataResponse = new HashMap<>();
    dataResponse.put(BUSINESS_ADVISOR_NUMBER, data.get("advisorDocument").getAsString());
    dataResponse.put(DESCRIPTION, description);
    dataResponse.put(DOCUMENT_NUMBER, data.get("customerDocument").getAsString());
    dataResponse.put(DOCUMENT_TYPE, data.get("customerDocType").getAsString());
    dataResponse.put(EVENT_CODE, eventCode);
    dataResponse.put(Constants.OFFICE_ID, data.get("officeCode").getAsString());
    given().relaxedHTTPSValidation().header(HeadersEnum.AUTHORIZATION.toString(), token)
        .contentType(ContentType.JSON)
        .body(dataResponse)
        .when()
        .post(ServicePaths.getTransactionAuditAnalytics());
  }

  public void getTransactionAudit(String advisorDocument, String customerDocument,
      String customerDocType, String officeCode, String token)
      throws MalformedURLException {

    getQuestion(advisorDocument, customerDocument, customerDocType, officeCode,
        QuestionEventEnum.QUESTION_PEP_REQUEST.toString(),
        QuestionEventEnum.QUESTION_PEP_RESPONSE.toString(),
        token);
    getQuestion(advisorDocument, customerDocument, customerDocType, officeCode,
        QuestionEventEnum.QUESTION_USGAP_REQUEST.toString(),
        QuestionEventEnum.QUESTION_USGAP_RESPONSE.toString(),
        token);
    getQuestion(advisorDocument, customerDocument, customerDocType, officeCode,
        QuestionEventEnum.QUESTION_BOARD_MEMBER_REQUEST.toString(),
        QuestionEventEnum.QUESTION_BOARD_MEMBER_RESPONSE.toString(),
        token);

  }
}