package co.com.bancopopular.automation.features.steps.consultpayrolloan;

import co.com.bancopopular.automation.data.Environment;
import co.com.bancopopular.automation.features.steps.search.SearchAssertions;
import co.com.bancopopular.automation.rest.requests.LoginRequests;
import co.com.bancopopular.automation.rest.requests.SearchRequests;
import co.com.bancopopular.automation.tasks.RequestPayrollLoans;
import co.com.bancopopular.automation.tasks.consultpayrollloan.ClientSearchResponse;
import co.com.bancopopular.automation.utils.AssertionsUtil;
import co.com.bancopopular.automation.utils.DataUserInstance;
import co.com.bancopopular.automation.utils.RequestsUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.questions.TheValue;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import net.serenitybdd.screenplay.rest.questions.TheResponseStatusCode;
import net.serenitybdd.annotations.Steps;
import org.aeonbits.owner.ConfigFactory;
import org.apache.http.HttpStatus;

import java.net.MalformedURLException;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.equalTo;

public class ConsultPayrollLoanStepsDefinition {

    ConsultPayrollLoanRequests consultPayrollLoanRequests = new ConsultPayrollLoanRequests();
    ConsultPayrollLoanAssertions consultPayrollLoanAssertions = new ConsultPayrollLoanAssertions();
    AssertionsUtil assertionsUtil = new AssertionsUtil();
    RequestsUtil requestsUtil = new RequestsUtil();

    Actor theActor = OnStage.theActorInTheSpotlight();
    @Steps
    LoginRequests loginRequests = new LoginRequests();
    SearchRequests searchRequests = new SearchRequests();
    SearchAssertions searchAssertions = new SearchAssertions();
    Environment environment = ConfigFactory.create(Environment.class);

    private static final String MESSAGE_REPORT = "La reserva no fue insertada ";
    private static final String FIELD_WITH_MESSAGE_TO_VALIDATE = "toDo";
    private static final String MESSAGE_TO_VALIDATE = "No insertado en pruebas";


    @When("{word} consulta su número de cédula")
    public void sendDocumentNumber(String actor) throws MalformedURLException {
        var data = DataUserInstance.getInstance().getData();
        consultPayrollLoanRequests.getPayrollLoan(data.get("advisorDocument").getAsString(),
                data.get("customerDocType").getAsString(),
                data.get("customerDocument").getAsString());
    }

    @When("el hace la consulta de sus datos")
    public void searchCustomerInformation() throws MalformedURLException {
        var data = DataUserInstance.getInstance().getData();
        var advisorData = loginRequests.advisorByOffice(data.get("advisorDocument").getAsString());
        searchRequests
                .searchCustomer(data, advisorData.getString("advisorJourneyId"));
    }

    @Then("observa el estado de su crédito en fábrica")
    public void getPayrolloanStatus() {
        assertionsUtil.shouldSeeSuccessfulStatusCode();
        assertionsUtil.validateJSONSchema("schemas/consultPayrollLoan.json");
        consultPayrollLoanAssertions.verifyPayrollLoanStatus();
    }

    @Then("el ve un mensaje indicando que la libranza no es activa")
    public void validateMessageAccountDisabled() {
        OnStage.theActorInTheSpotlight().should(seeThat(new TheResponseStatusCode(), equalTo(HttpStatus.SC_INTERNAL_SERVER_ERROR)));

        OnStage.theActorInTheSpotlight().attemptsTo(
                ClientSearchResponse.toValidate(environment.clientWithPunishedPayroll())
        );
    }

    @Then("el ve un mensaje indicando que el cliente no tiene una cuenta hábil")
    public void validateClientWithDisabledAccount() {
        OnStage.theActorInTheSpotlight().should(seeThat(new TheResponseStatusCode(), equalTo(HttpStatus.SC_INTERNAL_SERVER_ERROR)));

        OnStage.theActorInTheSpotlight().attemptsTo(
                ClientSearchResponse.toValidate(environment.clientWithDisabledAccount())
        );
    }

    @Then("el ve un mensaje indicando que el cliente no tiene método de autenticación")
    public void validateClientWithOutAuthenticationMethod() {
        theActor.attemptsTo(
                ClientSearchResponse.toValidate(environment.clientWithOutAuthenticationMethodMDM())
        );
    }

    @Then("el ve un mensaje indicando que el cliente tiene una condición anormal")
    public void validateClientWithAbnormalCondition() {
        theActor.should(seeThat(new TheResponseStatusCode(), equalTo(HttpStatus.SC_INTERNAL_SERVER_ERROR)));

        theActor.attemptsTo(
                ClientSearchResponse.toValidate(environment.clientWithAbnormalCondition())
        );
    }

    @When("el consulta la libranza con la cc del cliente")
    public void requestTheSalaryLoansWithTheClientsID() {
        theActor.attemptsTo(
                RequestPayrollLoans.withDataOfIdentification()
        );
    }

    @When("el consulta un cliente")
    public void searchClient(){
        requestsUtil.perfomanceSearch();
    }

    @Then("el debe visualizar un mensaje indicando que la reserva no fue ingresada")
    public void validateMessageReserveNotInsert(){
        var response = OnStage.theActorInTheSpotlight().asksFor(LastResponse.received());

        OnStage.theActorInTheSpotlight().should(
                seeThat(TheValue.of(MESSAGE_REPORT, String.valueOf(response.body().jsonPath().getString(FIELD_WITH_MESSAGE_TO_VALIDATE))), equalTo(MESSAGE_TO_VALIDATE) )
        );
    }

    @Then("el ve una mensaje indicando que la venta no fue insertada con la causal de caida")
    public void validateFailedSygnusRenewal() {
        assertionsUtil.shouldSeeSuccessfulStatusCode();
        assertionsUtil.validateJSONSchema("schemas/consultPayrollLoan.json");
        consultPayrollLoanAssertions.clientSygnusWithFailedRenewall();

    }

    @Then("se genera un error por no tener metodo de contacto")
    public void validateErrorByNotFoundContactInfo(){
        OnStage.theActorInTheSpotlight().should(seeThat(new TheResponseStatusCode(), equalTo(HttpStatus.SC_OK)));
        searchAssertions.verifyCustomerStatusCellphoneNumber("");

    }

    @Then("se muestra el numero de celular {}")
    public void viewCellPhoneNumber(String cellPhoneNumber){
        OnStage.theActorInTheSpotlight().should(seeThat(new TheResponseStatusCode(), equalTo(HttpStatus.SC_OK)));
        searchAssertions.verifyCustomerStatusCellphoneNumber(cellPhoneNumber);
    }
}