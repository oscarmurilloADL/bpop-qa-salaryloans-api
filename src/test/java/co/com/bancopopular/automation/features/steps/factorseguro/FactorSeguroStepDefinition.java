package co.com.bancopopular.automation.features.steps.factorseguro;

import co.com.bancopopular.automation.conf.StepBase;
import co.com.bancopopular.automation.rest.requests.FactorSeguroRequest;
import co.com.bancopopular.automation.utils.DataUserInstance;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;

import java.net.MalformedURLException;

public class FactorSeguroStepDefinition extends StepBase {

    @Steps
    FactorSeguroAssertions factorSeguroAssertions;

    FactorSeguroRequest factorseguro = new FactorSeguroRequest();

    @When("{word} envia su edad")
    public void sendRequesFactorSeguro(String actor) throws MalformedURLException {
        var data = DataUserInstance.getInstance().getData();
        factorseguro.edad(data.get("edad").getAsString());
    }

    @Then("^se verifica el porcentaje (.*)$")
    public void verificaPorcentaje(String porcentaje) {
        assertionsUtil.shouldSeeSuccessfulStatusCode();
        if (porcentaje.equalsIgnoreCase("0.0073")){
            factorSeguroAssertions.verificaPorcentaje00073();
        }else if (porcentaje.equalsIgnoreCase("0.112")){
            factorSeguroAssertions.verificaPorcentaje0112();
        }else if (porcentaje.equalsIgnoreCase("0.0245")){
            factorSeguroAssertions.verificaPorcentaje00245();
        }else if (porcentaje.equalsIgnoreCase("0.075")){
            factorSeguroAssertions.verificaPorcentaje0075();
        }else if (porcentaje.equalsIgnoreCase("0.125")){
            factorSeguroAssertions.verificaPorcentaje0125();
        }

    }

}
