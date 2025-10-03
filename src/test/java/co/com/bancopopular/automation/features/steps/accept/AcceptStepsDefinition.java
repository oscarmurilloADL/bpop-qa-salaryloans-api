package co.com.bancopopular.automation.features.steps.accept;

import co.com.bancopopular.automation.conf.StepBase;
import co.com.bancopopular.automation.utils.DataUserInstance;
import co.com.bancopopular.automation.utils.RequestsUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.net.MalformedURLException;
import net.serenitybdd.annotations.Steps;

public class AcceptStepsDefinition extends StepBase {

  AcceptRequests acceptRequests = new AcceptRequests();
  RequestsUtil requestsUtil = new RequestsUtil();

  @Steps
  AcceptAssertions acceptAssertions;

  @When("{word} acepta los valores de su crédito")
  public void acceptCreditData(String actor) throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();
    var otpJson = requestsUtil.createAuthOTP();
    acceptRequests.acceptCreditData(data.get("customerDocType").getAsString(),
        data.get("customerDocument").getAsString(), data.get("obligationID").getAsString(),
        otpJson.getString("token"));
  }

  @Then("los valores son procesados")
  public void processedValues() {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    acceptAssertions.verifyCreditData();
  }

}
