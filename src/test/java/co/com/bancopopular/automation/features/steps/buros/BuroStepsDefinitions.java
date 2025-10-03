package co.com.bancopopular.automation.features.steps.buros;

import co.com.bancopopular.automation.conf.StepBase;
import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.data.Environment;
import co.com.bancopopular.automation.features.steps.anyfeesimulation.AnyFeeSimulationRequests;
import co.com.bancopopular.automation.features.steps.crm.CrmRequests;
import co.com.bancopopular.automation.features.steps.payrollhistory.PayrollHistoryRequests;
import co.com.bancopopular.automation.features.steps.sygnus.SygnusRequests;
import co.com.bancopopular.automation.features.steps.tap.LaboralCertificationRequests;
import co.com.bancopopular.automation.features.steps.tap.PayrollCheckRequests;
import co.com.bancopopular.automation.features.steps.transactionaudit.TransactionAuditRequests;
import co.com.bancopopular.automation.features.steps.validations.ValidationsAssertions;
import co.com.bancopopular.automation.models.tap.StatusBody;
import co.com.bancopopular.automation.rest.requests.ValidationsRequests;
import co.com.bancopopular.automation.utils.DataUserInstance;
import co.com.bancopopular.automation.utils.RequestsUtil;
import co.com.bancopopular.automation.utils.SessionHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.annotations.Steps;
import org.aeonbits.owner.ConfigFactory;

import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static co.com.bancopopular.automation.utils.SessionHelper.setInSession;

public class BuroStepsDefinitions extends StepBase {

    @Steps
    BuroAssertions buroAssertions;
    PayrollCheckRequests payrollCheckRequests = new PayrollCheckRequests();
    AnyFeeSimulationRequests anyFeeSimulationRequests = new AnyFeeSimulationRequests();
    BuroRequests buroRequests = new BuroRequests();
    PayrollHistoryRequests payrollHistoryRequests = new PayrollHistoryRequests();
    TransactionAuditRequests transactionAuditRequests = new TransactionAuditRequests();
    LaboralCertificationRequests fileRequest = new LaboralCertificationRequests();
    SygnusRequests sygnusRequests = new SygnusRequests();
    CrmRequests crmRequests = new CrmRequests();

    ValidationsAssertions validationsAssertions = new ValidationsAssertions();

    ValidationsRequests validationsRequests = new ValidationsRequests();
    Environment environment = ConfigFactory.create(Environment.class);

    private static final String PROVIDER_ID = "providerId";
    private static final String CUSTOMER_DOC_TYPE = "customerDocType";
    private static final String CUSTOMER_DOCUMENT = "customerDocument";
    private static final String OBLIGATION_ID = "obligationID";
    private static final String SAME_FEE = "SAME_FEE";
    private static final String ANY_FEE = "ANY_FEE";
    private static final String TOKEN = "token";
    private static final String ORDINARY = "ORDINARY";
    private static final String GENERATED_ID = "generatedId";
    private static final String ADVISOR_DOCUMENT = "advisorDocument";
    private static final String OFFICE_CODE = "officeCode";

    private static final String PAYER_NAME = "payerName";
    RequestsUtil requestsUtil = new RequestsUtil();

    @When("{word} consulta sus datos en preselecta")
    public void consultPreselectaStatus(String actor) throws MalformedURLException {
        var data = DataUserInstance.getInstance().getData();
        var otpJson = requestsUtil.createAuthOTP();

        payrollHistoryRequests.getPayrollStatus(data.get(CUSTOMER_DOC_TYPE).getAsString(),
                data.get(CUSTOMER_DOCUMENT).getAsString(), otpJson.getString(TOKEN));

        buroRequests.getBuroInfo(data, ORDINARY, SAME_FEE, otpJson.getString(TOKEN));

        if(data.get(CUSTOMER_DOCUMENT).getAsString().equals("400013") &&
                getFromSession(SessionHelper.SessionData.RISK_EMGINE).equals("ON")){
            validationsRequests.sendValidateBusisnessRules(data.get(CUSTOMER_DOC_TYPE).getAsString(),
                    data.get(CUSTOMER_DOCUMENT).getAsString(),data.get("sector").getAsString(),otpJson.getString(TOKEN));
        }
    }

    @Then("es viable para realizar su crédito de libranza")
    public void buroStatusValid() throws FileNotFoundException {
        assertionsUtil.shouldSeeSuccessfulStatusCode();
        assertionsUtil.validateEncryptedJSONSchemaObject("src/test/resources/schemas/buro.json");
        buroAssertions.verifyValidStatus();
    }

    @Then("no es viable por {}")
    @Then("el proceso de preselecta presenta un error {}")
    public void buroBadHabits(String invalidReason) throws FileNotFoundException {
        if(getFromSession(SessionHelper.SessionData.RISK_EMGINE).equals("ON") && invalidReason.equals("cuenta embargada")){
            assertionsUtil.shouldSeeSuccessfulStatusCode();
            validationsAssertions.verifySubcausal(invalidReason,"PreselectViable");
        }else{
            assertionsUtil.shouldSeeInternalErrorStatusCode();
            buroAssertions.verifyInvalidStatus(invalidReason);
            assertionsUtil.validateEncryptedJSONSchemaObject("src/test/resources/schemas/non_viable_customer.json");
        }
    }

    @When("{word} consulta sus datos en preselecta para toda cuota")
    public void consultPreselectaStatusAnyFee(String actor) throws MalformedURLException {
        var data = DataUserInstance.getInstance().getData();
        var otpJson = requestsUtil.createAuthOTP();
        setInSession(SessionHelper.SessionData.TOKEN, otpJson.getString(TOKEN));
        payrollCheckRequests.sendPayrollCheck(data.get(OBLIGATION_ID).getAsString(),
                data.get(CUSTOMER_DOC_TYPE).getAsString(),
                data.get(CUSTOMER_DOCUMENT).getAsString(),
                otpJson.getString(TOKEN));
        anyFeeSimulationRequests.checkProcessIsFinished(data.get(Constants.ID_TAP).getAsString(),
                data.get(OBLIGATION_ID).getAsString(),
                data.get(CUSTOMER_DOC_TYPE).getAsString(),
                data.get(CUSTOMER_DOCUMENT).getAsString());
        buroRequests.getBuroInfo(data, ORDINARY, ANY_FEE, otpJson.getString(TOKEN));
    }

    @When("{word} solicita el valor máximo aprobado para toda cuota con fidelización")
    public void consultViabilityStatusAnyFeeLoyalty(String actor) throws MalformedURLException, JsonProcessingException {
        var data = DataUserInstance.getInstance().getData();
        var otpJson = requestsUtil.createAuthOTP();
        setInSession(SessionHelper.SessionData.TOKEN, otpJson.getString(TOKEN));
        if(getFromSession(SessionHelper.SessionData.OPTIMIZATION_TOGGLE_II).equals("")) {
            payrollCheckRequests.sendPayrollCheck(data.get(OBLIGATION_ID).getAsString(),
                    data.get(CUSTOMER_DOC_TYPE).getAsString(),
                    data.get(CUSTOMER_DOCUMENT).getAsString(),
                    otpJson.getString(TOKEN));
            anyFeeSimulationRequests.checkProcessIsFinished(data.get(Constants.ID_TAP).getAsString(),
                    data.get(OBLIGATION_ID).getAsString(),
                    data.get(CUSTOMER_DOC_TYPE).getAsString(),
                    data.get(CUSTOMER_DOCUMENT).getAsString());
        }else{
            setInSession(SessionHelper.SessionData.PAYROLL_CHECK_PROCESS_ID,
                    data.get(Constants.ID_TAP).getAsString());
            var objectMapper = new ObjectMapper();
            var status =objectMapper.readValue(data.get(Constants.STATUS).toString(), new TypeReference<StatusBody>() {});
            fileRequest.getStatus(otpJson.getString(TOKEN),status);
            var customerInfo = new String[]  {data.get(CUSTOMER_DOC_TYPE).getAsString(), data.get(CUSTOMER_DOCUMENT).getAsString(), data.get(OBLIGATION_ID).getAsString()};
            payrollCheckRequests.getSimulationStatus(customerInfo,
                    data.get(Constants.PAYER_NIT).getAsString(),
                    data.get(Constants.SECTOR_NUMBER).getAsString(),
                    data.get(Constants.SUB_SECTOR_NUMBER).getAsString(),
                    data.get(Constants.ID_TAP).getAsString(),
                    otpJson.getString(TOKEN));
        }
        buroRequests.getBuroInfo(data, ORDINARY, ANY_FEE, otpJson.getString(TOKEN));
    }

    @When("{word} solicita el valor máximo aprobado para misma cuota con fidelización")
    public void consultViabilityMaxValueSameFeeLoyalty(String actor) throws MalformedURLException {
        var data = DataUserInstance.getInstance().getData();
        var otpJson = requestsUtil.createAuthOTP();
        buroRequests.getBuroInfo(data, ORDINARY, SAME_FEE, otpJson.getString(TOKEN));
    }

    @When("{word} solicita valor recalculado para misma cuota con fidelización")
    public void consultViabilityRecalculateSameFeeLoyalty(String actor) throws MalformedURLException {
        var data = DataUserInstance.getInstance().getData();
        var otpJson = requestsUtil.createAuthOTP();
        buroRequests.getBuroInfo(data,
                ORDINARY, SAME_FEE, otpJson.getString(TOKEN));
    }

    @When("{word} actualiza sus datos en la aplicación")
    public void searchOfferingPreselecta(String actor) throws MalformedURLException, JsonProcessingException {
        var data = DataUserInstance.getInstance().getData();
        var otpJson = requestsUtil.createAuthOTP();
        setInSession(SessionHelper.SessionData.REQUEST_ID, otpJson.getInt(GENERATED_ID));
        setInSession(SessionHelper.SessionData.TOKEN, otpJson.getString(TOKEN));
        setInSession(SessionHelper.SessionData.OBLIGATION_ID, data.get(OBLIGATION_ID).getAsString());

        payrollHistoryRequests.getPayrollStatus(data.get(CUSTOMER_DOC_TYPE).getAsString(),
                data.get(CUSTOMER_DOCUMENT).getAsString(), otpJson.getString(TOKEN));

        payrollCheckRequests.sendPayrollCheck(data.get(OBLIGATION_ID).getAsString(),
                data.get(CUSTOMER_DOC_TYPE).getAsString(),
                data.get(CUSTOMER_DOCUMENT).getAsString(),
                otpJson.getString(TOKEN));

        if(getFromSession(SessionHelper.SessionData.OPTIMIZATION_TOGGLE_II).equals("")) {
            payrollCheckRequests.checkStatusPayrollCheck(data.get(Constants.ID_TAP).getAsString(),
                    data.get(OBLIGATION_ID).getAsString(),
                    data.get(CUSTOMER_DOC_TYPE).getAsString(),
                    data.get(CUSTOMER_DOCUMENT).getAsString(),
                    otpJson.getString(TOKEN));
        }else{
            var objectMapper = new ObjectMapper();
            var status =objectMapper.readValue(data.get("status").toString(), new TypeReference<StatusBody>() {});
            fileRequest.getStatus(otpJson.getString(TOKEN),status);
            anyFeeSimulationRequests.checkProcessIsFinished(data.get(Constants.ID_TAP).getAsString(),
                    data.get(OBLIGATION_ID).getAsString(),
                    data.get(CUSTOMER_DOC_TYPE).getAsString(),
                    data.get(CUSTOMER_DOCUMENT).getAsString());
        }

        transactionAuditRequests.getTransactionAudit(data.get(ADVISOR_DOCUMENT).getAsString(),
                data.get(CUSTOMER_DOCUMENT).getAsString(), data.get(CUSTOMER_DOC_TYPE).getAsString(),
                data.get(OFFICE_CODE).getAsString(), otpJson.getString(TOKEN));
        crmRequests.updateCustomer(data.get(CUSTOMER_DOC_TYPE).getAsString(),
                data.get(CUSTOMER_DOCUMENT).getAsString(),
                otpJson.getString(TOKEN));

        buroRequests.getBuroInfo(data,
                ORDINARY, ANY_FEE, otpJson.getString(TOKEN));
    }

    @When("{word} solicita una novación y actualiza sus datos")
    public void sygnusAndBuroRequest(String actor) throws MalformedURLException, JsonProcessingException {
        var data = DataUserInstance.getInstance().getData();
        var otpJson = requestsUtil.createAuthOTP();

        OnStage.theActorInTheSpotlight().remember(Constants.RQ_ID_GENERATED, otpJson.getInt(GENERATED_ID));
        setInSession(SessionHelper.SessionData.REQUEST_ID, otpJson.getInt(GENERATED_ID));
        setInSession(SessionHelper.SessionData.TOKEN, otpJson.getString(TOKEN));
        setInSession(SessionHelper.SessionData.OBLIGATION_ID, data.get(OBLIGATION_ID).getAsString());

        payrollHistoryRequests.getPayrollStatusWithRetry(data.get(CUSTOMER_DOC_TYPE).getAsString(),
                data.get(CUSTOMER_DOCUMENT).getAsString(), otpJson.getString(TOKEN));
        sygnusRequests.sendPayerSygnus(data.get(CUSTOMER_DOCUMENT).getAsString(),
                data.get(CUSTOMER_DOC_TYPE).getAsString(),data.get(PAYER_NAME).getAsString(),
                otpJson.getString(TOKEN));

        sygnusRequests.sygnusPayrollCheckProcess(data, otpJson);

        sygnusRequests.sendBindingSygnus(data.get(CUSTOMER_DOCUMENT).getAsString(),
                data.get(CUSTOMER_DOC_TYPE).getAsString(),data.get(OBLIGATION_ID).getAsString(),data.get(PAYER_NAME).getAsString(),
                otpJson.getString(TOKEN), data.get(PROVIDER_ID).getAsString());

        transactionAuditRequests.getTransactionAudit(data.get(ADVISOR_DOCUMENT).getAsString(),
                data.get(CUSTOMER_DOCUMENT).getAsString(), data.get(CUSTOMER_DOC_TYPE).getAsString(),
                data.get(OFFICE_CODE).getAsString(), otpJson.getString(TOKEN));

        crmRequests.updateCustomer(data.get(CUSTOMER_DOC_TYPE).getAsString(),
                data.get(CUSTOMER_DOCUMENT).getAsString(),
                otpJson.getString(TOKEN));

        buroRequests.getBuroInfo(data, ORDINARY, ANY_FEE, otpJson.getString(TOKEN));
    }

}