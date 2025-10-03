package co.com.bancopopular.automation.features.steps.login;

import co.com.bancopopular.automation.conf.StepBase;
import co.com.bancopopular.automation.rest.requests.LoginRequests;
import co.com.bancopopular.automation.utils.DataUserInstance;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;

import java.net.MalformedURLException;

public class LoginStepsDefinition extends StepBase {

  @Steps
  LoginAssertions loginAssertions;
  LoginRequests loginRequests = new LoginRequests();

  @When("{word} envía la peticion para entrar a la aplicación")
  public void sendRequestLogIn(String actor) throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();
    loginRequests.advisorByOffice(data.get("advisorDocument").getAsString());

  }

  @Then("^(?:el|ella) debe ser verificad(?:o|a)$")
  public void isVerified() {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    loginAssertions.verifyAdvisorIsFound();
  }

  @Then("{word} no puede ingresar a la aplicación")
  public void cannotLogIn(String actor) {
    assertionsUtil.shouldSeeInternalErrorStatusCode();
    loginAssertions.verifyAdvisorNotFound();
  }
}