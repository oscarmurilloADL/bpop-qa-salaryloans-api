package co.com.bancopopular.automation.features.steps.simvalidation;

import co.com.bancopopular.automation.conf.StepBase;
import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.rest.requests.LoginRequests;
import co.com.bancopopular.automation.rest.requests.SearchRequests;
import co.com.bancopopular.automation.rest.requests.SimValidationRequests;
import co.com.bancopopular.automation.tasks.simvalidation.RequestCrmAndSimSucces;
import co.com.bancopopular.automation.utils.DataUserInstance;
import co.com.bancopopular.automation.utils.SessionHelper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import lombok.SneakyThrows;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.annotations.Steps;

import java.net.MalformedURLException;

import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;

public class SimValidationStepsDefinition extends StepBase {

  SimValidationRequests simValidationRequests = new SimValidationRequests();
  SearchRequests searchRequests = new SearchRequests();
  LoginRequests loginRequests = new LoginRequests();

  @Steps
  SimValidationAssertions simValidationAssertions;

  private static final String ADVISOR_DOCUMENT = "advisorDocument";

  @Cuando("ella envía el OTP de manera correcta")
  @When("envía su número de teléfono para ser verificado")
  public void sendPhoneNumber() throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();
    var advisorData = loginRequests.advisorByOffice(data.get(ADVISOR_DOCUMENT).getAsString());
    searchRequests
        .searchCustomer(data, advisorData.getString("advisorJourneyId"));
    if(getFromSession(SessionHelper.SessionData.OWNERSHIP_REFACTOR_TOGGLE).equals("ON")){
      searchRequests.getAccountsOwnership(data.get("customerDocType").getAsString(),
              data.get("customerDocument").getAsString());
    }
    simValidationRequests
        .validateSim(data, advisorData.getString("advisorJourneyId"));
  }

  @Then("se valida sim correctamente")
  public void validatePhoneNumber() {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    simValidationAssertions.verifyPhoneNumber();
  }

  @Then("se valida sim no exitosa")
  @Entonces("ella puede validar que se solicita autenticación fuerte - método de validación DECRIM o Certihuella")
  public void validatePhoneNumberPending() {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    simValidationAssertions.verifyPhoneNumberPending();
  }

  @SneakyThrows
  @Cuando("ella ingresa el OTP exitoso")
  public void requestCrmAndSimSucces(){
    var data = DataUserInstance.getInstance().getData();
    var advisorLogin = loginRequests
            .advisorByOffice(data.get(ADVISOR_DOCUMENT).getAsString());

    OnStage.theActorInTheSpotlight().attemptsTo(
            RequestCrmAndSimSucces.forCustomerWithSingleAccount(data, advisorLogin.getString(Constants.ADVISOR_JOURNEY_ID))
    );
  }
}