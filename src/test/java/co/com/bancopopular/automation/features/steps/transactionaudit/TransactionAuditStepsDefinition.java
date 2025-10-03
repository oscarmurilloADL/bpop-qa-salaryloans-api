package co.com.bancopopular.automation.features.steps.transactionaudit;

import co.com.bancopopular.automation.conf.StepBase;
import co.com.bancopopular.automation.utils.DataUserInstance;
import co.com.bancopopular.automation.utils.QuestionEventEnum;
import co.com.bancopopular.automation.utils.RequestsUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.net.MalformedURLException;

public class TransactionAuditStepsDefinition extends StepBase {

  TransactionAuditRequests transactionAuditRequests = new TransactionAuditRequests();
  RequestsUtil requestsUtil = new RequestsUtil();

  @When("valida que {}")
  public void sendQuestion(String questionType) throws MalformedURLException {
    var eventCodeRequest="";
    var eventCodeResponse="";
    var otpJson = requestsUtil.createAuthOTP();
    switch (questionType){
      case "No tiene impuestos y nacionalidad extranjera":
        eventCodeRequest = QuestionEventEnum.QUESTION_USGAP_REQUEST.toString();
        eventCodeResponse = QuestionEventEnum.QUESTION_USGAP_RESPONSE.toString();
      break;
      case "No es miembro de Junta Directiva":
        eventCodeRequest = QuestionEventEnum.QUESTION_BOARD_MEMBER_REQUEST.toString();
        eventCodeResponse = QuestionEventEnum.QUESTION_BOARD_MEMBER_RESPONSE.toString();
      break;
      default:
        eventCodeRequest = QuestionEventEnum.QUESTION_PEP_REQUEST.toString();
        eventCodeResponse = QuestionEventEnum.QUESTION_PEP_RESPONSE.toString();
       break;
    }

    var data = DataUserInstance.getInstance().getData();
    transactionAuditRequests.getQuestion(data.get("advisorDocument").getAsString(),
        data.get("customerDocument").getAsString(),
        data.get("customerDocType").getAsString(),
        data.get("officeCode").getAsString(),
        eventCodeRequest, eventCodeResponse,
        otpJson.getString("token"));
  }

  @Then("se permite continuar el proceso")
  public void validateData() {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
  }
}