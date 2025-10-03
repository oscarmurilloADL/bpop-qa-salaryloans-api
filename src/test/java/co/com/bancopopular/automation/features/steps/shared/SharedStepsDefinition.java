package co.com.bancopopular.automation.features.steps.shared;

import co.com.bancopopular.automation.exceptions.UserNotFoundException;
import co.com.bancopopular.automation.data.ScenarioFactory;
import co.com.bancopopular.automation.features.steps.cashieroffice.CashierOfficeRequests;
import co.com.bancopopular.automation.utils.DataUserInstance;
import com.google.gson.JsonObject;
import io.cucumber.java.en.Given;
import io.cucumber.java.es.Dado;
import net.serenitybdd.screenplay.actors.OnStage;

import java.io.IOException;

public class SharedStepsDefinition {

  @Given("{word} es un {}")
  @Dado("que {} es un {}")
  @Given("que {word} tiene rol de administrador y quiere {}")
  @Given("un grupo de clientes con {}")
  public void userRole(String actor, String userRole) {
    var scenarioFactory = new ScenarioFactory();
    OnStage.theActorCalled(actor);
    JsonObject data = null;
    try {
      data = scenarioFactory.getData(userRole);
      DataUserInstance.getInstance().setData(data);
    } catch (UserNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Given("el anexo de documentos requeridos por pagaduria y tipo documental")
  public void attachRequiredDocumentsByPayrollAndDocumentType() throws IOException {
    CashierOfficeRequests.retrievePayrollsExcel();
  }

}
