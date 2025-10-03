package co.com.bancopopular.automation.features.steps.toggles;

import co.com.bancopopular.automation.conf.StepBase;
import co.com.bancopopular.automation.exceptions.MockCreationException;
import co.com.bancopopular.automation.exceptions.UserNotFoundException;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;
import java.net.MalformedURLException;

public class TogglesStepsDefinition extends StepBase {

  @Steps
  TogglesAssertions togglesAssertions;
  TogglesRequests togglesRequests = new TogglesRequests();

  @When("{word} consulta la lista de los toggles")
  public void seeToggles(String actor) throws MalformedURLException {
    togglesRequests
            .searchToggles();
  }

  @When("se crean los mocks {} de {}")
  public void createMocks(String type,String url){
    try {
      if(type.equals("REST")) {
        togglesRequests.createRestMocks(url);
      }else {
        togglesRequests.createSoapMocks(url);
      }

    } catch (Exception e) {
      throw new MockCreationException("Error al crear mocks: " + e.getMessage(), e);
    }
  }

  @Then("puede ver la información de las funcionalidades de la aplicación")
  public void togglesIsValidated() throws UserNotFoundException {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    togglesAssertions.verifyTogglesInformation();
  }

}