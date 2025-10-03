package co.com.bancopopular.automation.features.steps.tap;

import co.com.bancopopular.automation.abilities.ConnectToParameters;
import co.com.bancopopular.automation.conf.StepBase;
import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.features.steps.crm.CrmRequests;
import co.com.bancopopular.automation.features.steps.payrollhistory.PayrollHistoryRequests;
import co.com.bancopopular.automation.features.steps.sygnus.SygnusRequests;
import co.com.bancopopular.automation.models.tap.StatusBody;
import co.com.bancopopular.automation.utils.DataUserInstance;
import co.com.bancopopular.automation.utils.RequestsUtil;
import co.com.bancopopular.automation.utils.SessionHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import net.serenitybdd.annotations.Steps;

import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;


public class PayrollCheckStepsDefinition extends StepBase {

    private static final String OBLIGATION_ID = "obligationID";
    private static final String CUSTOMER_DOC_TYPE = "customerDocType";
    private static final String CUSTOMER_DOCUMENT = "customerDocument";
    private static final String TOKEN = "token";
    private static final String PAYER_NAME = "payerName";
    private static final String COMPLETED="COMPLETED";
    private PreparedStatement preparedStatement;
    private ConnectToParameters connectToParameters;

    @Steps
    PayrollCheckAssertions payrollCheckAssertions;
    PayrollCheckRequests payrollCheckRequests = new PayrollCheckRequests();
    PayrollHistoryRequests payrollHistoryRequests = new PayrollHistoryRequests();
    LaboralCertificationRequests fileRequest = new LaboralCertificationRequests();
    RequestsUtil requestsUtil = new RequestsUtil();
    CrmRequests crmRequests = new CrmRequests();
    static SygnusRequests sygnusRequests = new SygnusRequests();

    @When("{word} envía su desprendible de pago")
    public void sendUpload(String actor) throws IOException {
        var data = DataUserInstance.getInstance().getData();
        var otpJson = requestsUtil.createAuthOTP();

        if(getFromSession(SessionHelper.SessionData.OPTIMIZATION_TOGGLE_II).equals("")) {
            payrollCheckRequests.sendPayrollCheck(data.get(OBLIGATION_ID).getAsString(),
                    data.get(CUSTOMER_DOC_TYPE).getAsString(),
                    data.get(CUSTOMER_DOCUMENT).getAsString(),
                    otpJson.getString(TOKEN));
        }else{
            payrollHistoryRequests.getPayrollStatus(data.get(CUSTOMER_DOC_TYPE).getAsString(),
                    data.get(CUSTOMER_DOCUMENT).getAsString(), otpJson.getString(TOKEN));

            var valityPayroll = new ObjectMapper();

            fileRequest.getValityStatus(otpJson.getString(TOKEN),valityPayroll.readValue(data.get(Constants.VALITY_STATUS).toString(),
                    new TypeReference<StatusBody>() {}));

            fileRequest.getStatus(otpJson.getString(TOKEN),valityPayroll.readValue(data.get(Constants.STATUS).toString(),
                    new TypeReference<StatusBody>() {}));

        }

    }

    @When("{word} solicita una ampliación en la libranza")
    public void isInThe(String actor) throws MalformedURLException {
        var data = DataUserInstance.getInstance().getData();
        var otpJson = requestsUtil.createAuthOTP();

        payrollHistoryRequests.getPayrollStatus(data.get(CUSTOMER_DOC_TYPE).getAsString(),
                data.get(CUSTOMER_DOCUMENT).getAsString(), otpJson.getString(TOKEN));
    }

    @Then("se valida que subió correctamente su desprendible")
    public void validateUpload() {
        var data = DataUserInstance.getInstance().getData();
        assertionsUtil.shouldSeeSuccessfulStatusCode();
        payrollCheckAssertions.verifyUploadData(data.get(CUSTOMER_DOCUMENT).getAsInt());
    }

    @When("el pregunta por el estado de su desprendible de pago")
    public void askStatus() throws MalformedURLException, JsonProcessingException {
        var data = DataUserInstance.getInstance().getData();
        var otpJson = requestsUtil.createAuthOTP();
        if(getFromSession(SessionHelper.SessionData.OPTIMIZATION_TOGGLE_II).equals("")) {
            payrollCheckRequests.checkStatusPayrollCheck(data.get(Constants.ID_TAP).getAsString(),
                    data.get(OBLIGATION_ID).getAsString(),
                    data.get(CUSTOMER_DOC_TYPE).getAsString(),
                    data.get(CUSTOMER_DOCUMENT).getAsString(),
                    otpJson.getString(TOKEN));
        }else{
            var objectMapper = new ObjectMapper();
            StatusBody checkStatus =objectMapper.readValue(data.get(Constants.STATUS).toString(), new TypeReference<StatusBody>() {});
            fileRequest.getStatus(otpJson.getString(TOKEN),checkStatus);
        }
    }

    @When("el pregunta por el estado de su desprendible de pago con valor menor a 300000")
    public void askStatusError() throws MalformedURLException, JsonProcessingException {
        var data = DataUserInstance.getInstance().getData();
        var otpJson = requestsUtil.createAuthOTP();
        if(getFromSession(SessionHelper.SessionData.OPTIMIZATION_TOGGLE_II).equals("")) {
            payrollHistoryRequests.getPayrollStatus(data.get(CUSTOMER_DOC_TYPE).getAsString(),
                    data.get(CUSTOMER_DOCUMENT).getAsString(), otpJson.getString(TOKEN));
            payrollCheckRequests.sendPayrollCheck(data.get(OBLIGATION_ID).getAsString(),
                    data.get(CUSTOMER_DOC_TYPE).getAsString(),
                    data.get(CUSTOMER_DOCUMENT).getAsString(),
                    otpJson.getString(TOKEN));
        }else{
            payrollHistoryRequests.getPayrollStatus(data.get(CUSTOMER_DOC_TYPE).getAsString(),
                    data.get(CUSTOMER_DOCUMENT).getAsString(), otpJson.getString(TOKEN));
            var payrollcheck = new ObjectMapper();
            var searchStatus =payrollcheck.readValue(data.get(Constants.VALITY_STATUS).toString(), new TypeReference<StatusBody>() {});
            fileRequest.getValityStatus(otpJson.getString(TOKEN),searchStatus);
            var viewFinalstatus =payrollcheck.readValue(data.get(Constants.STATUS).toString(), new TypeReference<StatusBody>() {});
            fileRequest.getStatus(otpJson.getString(TOKEN),viewFinalstatus);
            var customerInfo = new String[]  {data.get(CUSTOMER_DOC_TYPE).getAsString(), data.get(CUSTOMER_DOCUMENT).getAsString(), data.get(OBLIGATION_ID).getAsString()};
            payrollCheckRequests.getSimulationStatus(customerInfo,
                    data.get(Constants.PAYER_NIT).getAsString(),
                    data.get(Constants.SECTOR_NUMBER).getAsString(),
                    data.get(Constants.SUB_SECTOR_NUMBER).getAsString(),
                    data.get(Constants.ID_TAP).getAsString(),
                    otpJson.getString(TOKEN));
        }
    }

    @When("{word} envía su desprendible de pago con valor recibido en descuento 3 igual o mayor a la cuota actual")
    public void uploadDocument(String actor) throws MalformedURLException, JsonProcessingException {
        var data = DataUserInstance.getInstance().getData();
        var otpJson = requestsUtil.createAuthOTP();
        if(getFromSession(SessionHelper.SessionData.OPTIMIZATION_TOGGLE_II).equals("")) {
            payrollHistoryRequests.getPayrollStatus(data.get(CUSTOMER_DOC_TYPE).getAsString(),
                    data.get(CUSTOMER_DOCUMENT).getAsString(), otpJson.getString(TOKEN));

            payrollCheckRequests.checkStatusPayrollCheck(data.get(Constants.ID_TAP).getAsString(),
                    data.get(OBLIGATION_ID).getAsString(),
                    data.get(CUSTOMER_DOC_TYPE).getAsString(),
                    data.get(CUSTOMER_DOCUMENT).getAsString(),
                    otpJson.getString(TOKEN));
        }else{
            var objectMapper = new ObjectMapper();
            var fileDiscountThreeStatus =objectMapper.readValue(data.get(Constants.VALITY_STATUS).toString(), new TypeReference<StatusBody>() {});
            fileRequest.getValityStatus(otpJson.getString(TOKEN),fileDiscountThreeStatus);
            var status =objectMapper.readValue(data.get(Constants.STATUS).toString(), new TypeReference<StatusBody>() {});
            fileRequest.getStatus(otpJson.getString(TOKEN),status);
        }
    }


    @When("{word} sube un desprendible con respuesta {word}")
    public void uploadUnreadableDocument(String actor, String status) throws MalformedURLException, SQLException, JsonProcessingException {
        var data = DataUserInstance.getInstance().getData();
        var otpJson = requestsUtil.createAuthOTP();
        if(getFromSession(SessionHelper.SessionData.OPTIMIZATION_TOGGLE_II).equals("")) {
            try {
                connectToParameters = new ConnectToParameters(Constants.HOST,
                        Constants.PORT,
                        Constants.PASS,
                        Constants.DATABASE,
                        Constants.USER);
                var query = "UPDATE payrollcheck SET status=? WHERE document_number=?;";
                preparedStatement = connectToParameters.connect().prepareStatement(query);
                preparedStatement.setString(1, status);
                preparedStatement.setString(2, data.get(CUSTOMER_DOCUMENT).getAsString());
                preparedStatement.executeUpdate();
                if (status.equals("DELETED")) {
                    payrollCheckRequests.sendPayrollCheck(data.get(OBLIGATION_ID).getAsString(),
                            data.get(CUSTOMER_DOC_TYPE).getAsString(),
                            data.get(CUSTOMER_DOCUMENT).getAsString(),
                            otpJson.getString(TOKEN));
                    assertionsUtil.shouldSeeInternalErrorStatusCode();
                    payrollCheckAssertions.verifyUploadButtonIsActivated();
                    var query1 = "UPDATE payrollcheck SET status=? WHERE document_number=?;";
                    preparedStatement = connectToParameters.connect().prepareStatement(query1);
                    preparedStatement.setString(1, COMPLETED);
                    preparedStatement.setString(2, data.get(CUSTOMER_DOCUMENT).getAsString());
                    preparedStatement.executeUpdate();
                }
            } finally {
                preparedStatement.close();
                connectToParameters.tearDown();
            }
            payrollCheckRequests.sendPayrollCheck(data.get(OBLIGATION_ID).getAsString(),
                    data.get(CUSTOMER_DOC_TYPE).getAsString(),
                    data.get(CUSTOMER_DOCUMENT).getAsString(),
                    otpJson.getString(TOKEN));
        }else{
            var objectMapper = new ObjectMapper();
            var statusOCR =objectMapper.readValue(data.get(Constants.STATUS).toString(), new TypeReference<StatusBody>() {});
            fileRequest.getStatus(otpJson.getString(TOKEN),statusOCR);
        }
    }

    @When("el solicita una nueva oferta")
    public void requestNewOffer() throws MalformedURLException, JsonProcessingException {
        var data = DataUserInstance.getInstance().getData();
        var otpJson = requestsUtil.createAuthOTP();

        crmRequests.updateCustomer(data.get(CUSTOMER_DOC_TYPE).getAsString(),
                data.get(CUSTOMER_DOCUMENT).getAsString(),
                otpJson.getString(TOKEN));

        if(getFromSession(SessionHelper.SessionData.OPTIMIZATION_TOGGLE_II).equals("")) {
            payrollCheckRequests.sendPayrollCheck(data.get(OBLIGATION_ID).getAsString(),
                    data.get(CUSTOMER_DOC_TYPE).getAsString(),
                    data.get(CUSTOMER_DOCUMENT).getAsString(),
                    otpJson.getString(TOKEN));

            payrollCheckRequests.checkStatusPayrollCheck(data.get(Constants.ID_TAP).getAsString(),
                    data.get(OBLIGATION_ID).getAsString(),
                    data.get(CUSTOMER_DOC_TYPE).getAsString(),
                    data.get(CUSTOMER_DOCUMENT).getAsString(),
                    otpJson.getString(TOKEN));
        }else{
            var objectMapper = new ObjectMapper();
            var viewFileStatus =objectMapper.readValue(data.get(Constants.VALITY_STATUS).toString(), new TypeReference<StatusBody>() {});
            fileRequest.getValityStatus(otpJson.getString(TOKEN),viewFileStatus);
            var finalFileStatus =objectMapper.readValue(data.get(Constants.STATUS).toString(), new TypeReference<StatusBody>() {});
            fileRequest.getStatus(otpJson.getString(TOKEN),finalFileStatus);
        }
    }

    @When("carga el desprendible de pago y consulta el estado de su desprendible de pago")
    public void verifySygnus() throws MalformedURLException, JsonProcessingException {
        var data = DataUserInstance.getInstance().getData();
        var otpJson = requestsUtil.createAuthOTP();
        sygnusRequests.sendPayerSygnus(data.get(CUSTOMER_DOCUMENT).getAsString(),
                data.get(CUSTOMER_DOC_TYPE).getAsString(),data.get(PAYER_NAME).getAsString(),
                otpJson.getString(TOKEN));
        assertionsUtil.shouldSeeInternalErrorStatusCode();

        if(getFromSession(SessionHelper.SessionData.OPTIMIZATION_TOGGLE_II).equals("")) {
            payrollCheckRequests.checkStatusPayrollCheck(data.get(Constants.ID_TAP).getAsString(),
                    data.get(OBLIGATION_ID).getAsString(),
                    data.get(CUSTOMER_DOC_TYPE).getAsString(),
                    data.get(CUSTOMER_DOCUMENT).getAsString(),
                    otpJson.getString(TOKEN));
        }else{
            var fileStatus = new ObjectMapper();
            var status =fileStatus.readValue(data.get(Constants.STATUS).toString(), new TypeReference<StatusBody>() {});
            fileRequest.getStatus(otpJson.getString(TOKEN),status);
        }
    }

    @Then("se valida correctamente su desprendible")
    public void validateStatus() {
        assertionsUtil.shouldSeeSuccessfulStatusCode();
        payrollCheckAssertions.verifyProcessIsFinished();
    }

    @Then("se informa que debe revisar la titularidad del desprendible")
    public void validateStatusInvalidPayrollCheckHolder() {
        payrollCheckAssertions.verifyInvalidPayrollCheckHolder();
    }

    @Then("se informa que su desprendible está vencido y debe actualizarlo")
    public void validateStatusExpiredPayrollCheck() {
        payrollCheckAssertions.verifyExpiredPayrollCheck();
    }

    @Then("se informa que su tipo de mesada no es sujeta a oferta de libranza")
    public void validateStatusUnreadablePayrollCheck() {
        payrollCheckAssertions.verifyNotSubjectToPayroll();
    }

    @Then("se informa que no cumple con la política de crecimiento de monto")
    public void validateStatusNoMinimumGrowthAmount() {
        payrollCheckAssertions.verifyNotGrowthAmount();
    }


    @Then("se informa la lectura exitosa del desprendible y continuidad de la venta")
    public void verifyDocumentWasProcessed() {
        assertionsUtil.shouldSeeSuccessfulStatusCode();
        payrollCheckAssertions.verifyDocumentWasProcessed();
    }

    @Then("se informa la lectura no exitosa del desprendible")
    public void verifyDocumentWasNotProcessed() {
        payrollCheckAssertions.verifyDocumentWasNotProcessed();
    }

    @Then("se informa que no es sujeto de libranza")
    public void verifyNonPayableCustomer(){
        payrollCheckAssertions.verifyNonPayableCustomer();
    }

    @Then("se informa que no se pudo leer el desprendible se debe envíar de nuevo")
    public void checkTearOffNotReadable(){
        payrollCheckAssertions.checkTearOffNotReadable();
    }

    @Then("se valida correctamente nuevo desprendible")
    public void verifyNewDocumentWasProcessed(){
        assertionsUtil.shouldSeeSuccessfulStatusCode();
        payrollCheckAssertions.verifyNewDocumentWasProcessed();
    }

    @Then("se valida que no se consuman los servicios de Sygnus")
    public void checkNoConsumptionServices(){
        assertionsUtil.shouldSeeSuccessfulStatusCode();
        payrollCheckAssertions.checkNoConsumptionServices();
    }

    @Then("se valida que se activa la opcion de subir un nuevo desprendible")
    public void verifyUploadButtonIsActivated() {
        assertionsUtil.shouldSeeInternalErrorStatusCode();
        payrollCheckAssertions.verifyUploadButtonIsActivated();
    }

}