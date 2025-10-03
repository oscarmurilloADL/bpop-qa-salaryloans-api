package co.com.bancopopular.automation.features.steps.search;

import co.com.bancopopular.automation.conf.StepBase;
import co.com.bancopopular.automation.rest.requests.LoginRequests;
import co.com.bancopopular.automation.rest.requests.SearchRequests;
import co.com.bancopopular.automation.utils.DataUserInstance;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.net.MalformedURLException;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.annotations.Steps;
import org.apache.http.HttpStatus;

public class SearchStepsDefinition extends StepBase {

  @Steps
  SearchAssertions searchAssertions;
  LoginRequests loginRequests = new LoginRequests();
  SearchRequests searchRequests = new SearchRequests();

  @When("{word} envía sus datos para ser verificados")
  public void sendUserData(String actor) throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();
    var advisorData = loginRequests.advisorByOffice(data.get("advisorDocument").getAsString());
    for(var x = 0 ; x < 10 ; x++) {
      searchRequests
              .searchCustomer(data, advisorData.getString("advisorJourneyId"));
      if(SerenityRest.then().extract().statusCode() == HttpStatus.SC_OK){
        break;
      }
    }
  }

  @Then("se validan correctamente")
  public void isValidated() {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
  }

  @Then("no debe aparecer en el sistema")
  public void notInSystem() {
    assertionsUtil.shouldSeeInternalErrorStatusCode();
    searchAssertions.verifyCustomerNotFound();
  }

  @Then("se debe indicar que no tiene celular")
  public void notCellphone() {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    searchAssertions.verifyCustomerDoesNotHaveCellphone();
  }

  @Then("se indica que la libranza no es activa")
  public void canceledPayroll() {
    assertionsUtil.shouldSeeInternalErrorStatusCode();
    searchAssertions.verifyCustomerCanceledPayroll();
  }

  @Then("se retorna la marca {}")
  public void flagIsReturned(String flag) {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    searchAssertions.validateCustomerTypeFlag(flag);
  }

  @Then("se indica que está reportado en bases de riesgo")
  public void reportedClient() {
    assertionsUtil.shouldSeeInternalErrorStatusCode();
    searchAssertions.validateCustomerReportedODM();
  }

  @Then("se indica falla técnica de la búsqueda de cliente")
  public void technicalFailure() {
    assertionsUtil.shouldSeeInternalErrorStatusCode();
    searchAssertions.validateTechnicalFailure();
  }

}