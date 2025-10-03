package co.com.bancopopular.automation.features.steps.lifeinsurance;

import co.com.bancopopular.automation.conf.StepBase;
import co.com.bancopopular.automation.rest.requests.ValidationsRequests;
import co.com.bancopopular.automation.utils.DataUserInstance;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.net.MalformedURLException;
import static co.com.bancopopular.automation.constants.Constants.CONDITIONS_TOKEN;

public class LifeInsuranceStepsDefinition extends StepBase {

  LifeInsuranceRequests lifeInsuranceRequests = new LifeInsuranceRequests();
  LifeInsuranceAssertions lifeInsuranceAssertions = new LifeInsuranceAssertions();
  ValidationsRequests validationsRequests = new ValidationsRequests();

  @When("{word} selecciona enfermedad ninguna")
  public void selectDisease(String actor) throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();
    var token =validationsRequests.getTokenExternalViability(CONDITIONS_TOKEN);
    lifeInsuranceRequests.getDataLifeInsurance(data.get("customerDocument").getAsString(),
        data.get("customerDocType").getAsString(), token);
  }

  @Then("se valida la información del seguro exitosamente")
  public void informationSaved() {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    lifeInsuranceAssertions.verifyLifeInsurance();
    assertionsUtil.validateJSONSchema("schemas/lifeInsurance.json");
  }
}