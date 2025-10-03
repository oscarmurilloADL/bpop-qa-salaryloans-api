package co.com.bancopopular.automation.features.steps.legalconditions;

import co.com.bancopopular.automation.conf.StepBase;
import co.com.bancopopular.automation.rest.requests.LegalConditionsRequest;
import co.com.bancopopular.automation.rest.requests.LoginRequests;
import co.com.bancopopular.automation.rest.requests.SearchRequests;
import co.com.bancopopular.automation.rest.requests.SimValidationRequests;
import co.com.bancopopular.automation.utils.DataUserInstance;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.net.MalformedURLException;

public class LegalConditionsStepsDefinition extends StepBase {


  LegalConditionsRequest legalConditionsRequest = new LegalConditionsRequest();
  LoginRequests loginRequests = new LoginRequests();
  SearchRequests searchRequests = new SearchRequests();
  SimValidationRequests simValidationRequests = new SimValidationRequests();
  LegalConditionsAssertions legalConditionsAssertions = new LegalConditionsAssertions();

  private static final String ADVISOR_DOCUMENT = "advisorDocument";
  private static final String CUSTOMER_DOC_TYPE = "customerDocType";
  private static final String CUSTOMER_DOCUMENT = "customerDocument";
  private static final String ADVISOR_JOURNEY_ID = "advisorJourneyId";

  @When("acepta tratamiento de datos personales")
  public void acceptDataTreatment() throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();
    var advisorData = loginRequests.advisorByOffice(data.get(ADVISOR_DOCUMENT).getAsString());
    searchRequests
        .searchCustomer(data, advisorData.getString(ADVISOR_JOURNEY_ID));
    simValidationRequests
        .validateSim(data, advisorData.getString(ADVISOR_JOURNEY_ID));
    legalConditionsRequest.getDataTreatment(data.get(CUSTOMER_DOC_TYPE).getAsString(),
        data.get(CUSTOMER_DOCUMENT).getAsString(), "true",advisorData.getString(ADVISOR_JOURNEY_ID));
  }

  @When("rechaza el tratamiento de datos personales")
  public void notAcceptDataTreatment() throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();
    var advisorData = loginRequests.advisorByOffice(data.get(ADVISOR_DOCUMENT).getAsString());
    searchRequests
        .searchCustomer(data, advisorData.getString(ADVISOR_JOURNEY_ID));
    legalConditionsRequest.getDataTreatment(data.get(CUSTOMER_DOC_TYPE).getAsString(),
        data.get(CUSTOMER_DOCUMENT).getAsString(), "false",advisorData.getString(ADVISOR_JOURNEY_ID));
  }

  @Then("se valida su respuesta correctamente")
  public void dataTreatment() {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    assertionsUtil.validateJSONSchema("schemas/legalconditions.json");
    legalConditionsAssertions.validateDataTreatment();
  }

  @When("acepta la consulta en centrales de riesgo")
  public void acceptBureauReport() throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();
    var advisorData = loginRequests.advisorByOffice(data.get(ADVISOR_DOCUMENT).getAsString());
    searchRequests
        .searchCustomer(data, advisorData.getString(ADVISOR_JOURNEY_ID));
    simValidationRequests
        .validateSim(data, advisorData.getString(ADVISOR_JOURNEY_ID));
    legalConditionsRequest.getDataTreatment(data.get(CUSTOMER_DOC_TYPE).getAsString(),
        data.get(CUSTOMER_DOCUMENT).getAsString(), "true",advisorData.getString(ADVISOR_JOURNEY_ID));
    legalConditionsRequest.getBureauReport(data.get(CUSTOMER_DOC_TYPE).getAsString(),
        data.get(CUSTOMER_DOCUMENT).getAsString(), "true",advisorData.getString(ADVISOR_JOURNEY_ID));
  }

  @When("rechaza la consulta en centrales de riesgo")
  public void notAcceptBureauReport() throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();
    var advisorData = loginRequests.advisorByOffice(data.get(ADVISOR_DOCUMENT).getAsString());
    searchRequests
        .searchCustomer(data , advisorData.getString(ADVISOR_JOURNEY_ID));
    legalConditionsRequest.getDataTreatment(data.get(CUSTOMER_DOC_TYPE).getAsString(),
        data.get(CUSTOMER_DOCUMENT).getAsString(), "true",advisorData.getString(ADVISOR_JOURNEY_ID));
    legalConditionsRequest.getBureauReport(data.get(CUSTOMER_DOC_TYPE).getAsString(),
        data.get(CUSTOMER_DOCUMENT).getAsString(), "false",advisorData.getString(ADVISOR_JOURNEY_ID));
  }
}