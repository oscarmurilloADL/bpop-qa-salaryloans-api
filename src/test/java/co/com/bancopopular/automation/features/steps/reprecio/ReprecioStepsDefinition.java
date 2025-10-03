package co.com.bancopopular.automation.features.steps.reprecio;

import co.com.bancopopular.automation.conf.StepBase;
import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.features.steps.accounts.AccountsAssertions;
import co.com.bancopopular.automation.features.steps.anyfeesimulation.AnyFeeSimulationRequests;
import co.com.bancopopular.automation.features.steps.payrollhistory.PayrollHistoryRequests;
import co.com.bancopopular.automation.features.steps.tap.LaboralCertificationRequests;
import co.com.bancopopular.automation.models.tap.StatusBody;
import co.com.bancopopular.automation.rest.requests.LegalConditionsRequest;
import co.com.bancopopular.automation.rest.requests.LoginRequests;
import co.com.bancopopular.automation.rest.requests.SearchRequests;
import co.com.bancopopular.automation.rest.requests.ValidationsRequests;
import co.com.bancopopular.automation.dynamodb.DynamoQueries;
import co.com.bancopopular.automation.utils.DataUserInstance;
import co.com.bancopopular.automation.utils.RequestsUtil;
import co.com.bancopopular.automation.utils.SessionHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;

import java.net.MalformedURLException;
import java.util.List;

import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReprecioStepsDefinition extends StepBase {
    @Steps
    LoginRequests loginRequests = new LoginRequests();
    SearchRequests searchRequests = new SearchRequests();
    ReprecioRequest reprecioRequest = new ReprecioRequest();
    RequestsUtil requestsUtil = new RequestsUtil();
    ReprecioAssertions reprecioAssertions = new ReprecioAssertions();
    AccountsAssertions accountsAssertions = new AccountsAssertions();
    LegalConditionsRequest legalConditionsRequest = new LegalConditionsRequest();
    PayrollHistoryRequests payrollHistoryRequests = new PayrollHistoryRequests();
    ValidationsRequests validationsRequests = new ValidationsRequests();
    AnyFeeSimulationRequests anyFeeSimulationRequests = new AnyFeeSimulationRequests();
    LaboralCertificationRequests fileRequest = new LaboralCertificationRequests();
    private static final String CUSTOMER_DOCUMENT = "customerDocument";
    private static final String RECORD = "record";
    private static final String CUSTOMER_DOC_TYPE = "customerDocType";
    private static final String ADVISOR_DOCUMENT="advisorDocument";
    private static final String ADVISOR_JOURNEY_ID="advisorJourneyId";

    public ReprecioStepsDefinition() throws MalformedURLException {
        //Excepción tokenRepricing
    }

    @When("{word} es consultado en base reprecio revisando sus {int} registros")
    public void queryRepricingDynamoDB(String actor, int recordsQuantity) throws MalformedURLException {
        var data = DataUserInstance.getInstance().getData();
        var advisorData = loginRequests.advisorByOffice(data.get(ADVISOR_DOCUMENT).getAsString());
        var dynamoQueries = new DynamoQueries();
        searchRequests
                .searchCustomer(data, advisorData.getString(ADVISOR_JOURNEY_ID));
        if(getFromSession(SessionHelper.SessionData.OWNERSHIP_REFACTOR_TOGGLE).equals("ON")){
            searchRequests.getAccountsOwnership(data.get(CUSTOMER_DOC_TYPE).getAsString(),
                    data.get(CUSTOMER_DOCUMENT).getAsString());
        }
       List<Object> resultsRepricingDynamoDB = dynamoQueries.queryRepricingTable(data.get(CUSTOMER_DOCUMENT).getAsString());
        checkRecordRepricingTable(resultsRepricingDynamoDB,recordsQuantity);
    }

    @When("{word} hace la consulta de sus datos por reprecio")
    public void searchDataRecipring(String actor) throws MalformedURLException {
        var tokenRepricing = validationsRequests.getTokenExternalViability("");
        var data = DataUserInstance.getInstance().getData();
        reprecioRequest.search(data, tokenRepricing, ADVISOR_JOURNEY_ID);
    }

    @When("{word} hace la consulta del cliente con problemas")
    public void searchCustomerWithConflicts(String actor) throws MalformedURLException {
        var data = DataUserInstance.getInstance().getData();
        var advisorData = loginRequests.advisorByOffice(data.get(ADVISOR_DOCUMENT).getAsString());
        searchRequests
                .searchCustomer(data, advisorData.getString(ADVISOR_JOURNEY_ID));
    }

    @When("{word} sigue flujo corto de reprecio")
    public void processShortFlowRepricing(String actor) throws MalformedURLException, JsonProcessingException {
        var data = DataUserInstance.getInstance().getData();
        var otpJson = requestsUtil.createAuthOTP();
        var tokenRepricing = validationsRequests.getTokenExternalViability("");
        var advisorData = loginRequests.advisorByOffice(data.get(ADVISOR_DOCUMENT).getAsString());
        searchRequests
                .searchCustomer(data, advisorData.getString(ADVISOR_JOURNEY_ID));
        searchRequests.getAccountsOwnership(data.get(CUSTOMER_DOC_TYPE).getAsString(),
                data.get(CUSTOMER_DOCUMENT).getAsString());
        reprecioRequest.search(data, tokenRepricing, ADVISOR_JOURNEY_ID);

        legalConditionsRequest.getDataTreatment(data.get(CUSTOMER_DOC_TYPE).getAsString(),
                data.get(CUSTOMER_DOCUMENT).getAsString(), Constants.TRUE, advisorData.getString(ADVISOR_JOURNEY_ID));
        reprecioRequest.callCenter(advisorData.getString(ADVISOR_JOURNEY_ID),data);
        legalConditionsRequest.getBureauReport(data.get(CUSTOMER_DOC_TYPE).getAsString(),
                data.get(CUSTOMER_DOCUMENT).getAsString(), Constants.TRUE, advisorData.getString(ADVISOR_JOURNEY_ID));
        payrollHistoryRequests.getPayrollStatus(data.get(CUSTOMER_DOC_TYPE).getAsString(),
                data.get(CUSTOMER_DOCUMENT).getAsString(),otpJson.getString(Constants.TOKEN));
       if(data.get(CUSTOMER_DOCUMENT).getAsString().equals("400166")) {
           anyFeeSimulationRequests.callCNCSimulation(data, otpJson.getString(Constants.TOKEN));
        }
    }

    @Then("se valida la pagaduria {}")
    public void paymentNotEnabledDisabled(String param){
        assertionsUtil.shouldSeeSuccessfulStatusCode();
        reprecioAssertions.validatePayment(param);
    }

    @Then("se valida que el cliente no es apto para flujo reprecio")
    public void notEligibleForRepricing(){
        var data = DataUserInstance.getInstance().getData();
        assertionsUtil.shouldSeeSuccessfulStatusCode();
        reprecioAssertions.validateCustomerNotEligibleForRepricing(data.get(CUSTOMER_DOCUMENT).getAsString());
    }

    @Then("se valida que el cliente está reportado")
    public void reportedCustomer(){
        assertionsUtil.shouldSeeInternalErrorStatusCode();
        reprecioAssertions.validateReportedCustomer();
    }

    @Then("se valida que el cliente no existe en CRM o MDM")
    public void customerNotInCRMMDM(){
        assertionsUtil.shouldSeeInternalErrorStatusCode();
        reprecioAssertions.validateCustomerNotInCRMMDM();
    }

    @Then("se valida que el cliente tiene cuenta en estado derogatorio")
    public void customerWithSizedAccount(){
        assertionsUtil.shouldSeeSuccessfulStatusCode();
        accountsAssertions.verifySeizedAccount();
    }

    public void checkRecordRepricingTable(List<Object> resultsRepricingDynamoDB, int numberOfRecords){
        var data = DataUserInstance.getInstance().getData();
        assertThat(resultsRepricingDynamoDB.size(),
                equalTo(numberOfRecords));
        if (numberOfRecords>0)
        {
            for(var i=0; i<resultsRepricingDynamoDB.size();i++)
            {
                assertThat(resultsRepricingDynamoDB.get(i).toString(),
                        equalTo(data.get(RECORD).getAsJsonArray().get(i).getAsJsonObject().get("repriceInfo").getAsString()));
            }
        }
    }
}
