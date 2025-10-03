package co.com.bancopopular.automation.features.steps.tap;

import co.com.bancopopular.automation.conf.StepBase;
import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.features.steps.cnc.CNCRequest;
import co.com.bancopopular.automation.models.tap.StatusBody;
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

import static co.com.bancopopular.automation.constants.Constants.NEW_PAYROLL_LOAN;
import static co.com.bancopopular.automation.constants.Constants.SECTOR;
import static co.com.bancopopular.automation.utils.SessionHelper.setInSession;

public class LaboralCertificationStepsDefinition extends StepBase {

    private static final String TOKEN = "token";
    private static final String SCHEMA_STATUS="schemas/status_CNC_Edu.json";
    private static final String CUSTOMER_DOCUMENT="customerDocument";
    private static final String FINAL="final";

    @Steps
    RequestsUtil requestsUtil = new RequestsUtil();
    LaboralCertificationRequests laboralCertificationRequests = new LaboralCertificationRequests();
    LaboralCertificationAssertions laboralCertificationAssertions  = new LaboralCertificationAssertions();
    CNCRequest cncRequest = new CNCRequest();

    @When("el consulta el estado {word} de sus documentos")
    public void loadAndVerifyDocumentsStatus(String documentStatus) throws MalformedURLException, JsonProcessingException {
        var data = DataUserInstance.getInstance().getData();
        var otpJson = requestsUtil.createAuthOTP();

        laboralCertificationRequests.getCreditTypeDecision(data.get(CUSTOMER_DOCUMENT).getAsString(),otpJson.getString(TOKEN),NEW_PAYROLL_LOAN);
        laboralCertificationRequests.getSector(otpJson.getString(TOKEN));
        laboralCertificationRequests.getChoosePayrollcheck(data.get(CUSTOMER_DOCUMENT).getAsString(),otpJson.getString(TOKEN),data.get(SECTOR).getAsString());

        var objectMapper = new ObjectMapper();
        var valityStatus =objectMapper.readValue(data.get(Constants.VALITY_STATUS).toString(), new TypeReference<StatusBody>() {});
        laboralCertificationRequests.getValityStatus(otpJson.getString(TOKEN),valityStatus);
        assertionsUtil.validateJSONSchema("schemas/valityStatus_CNC_Edu.json");
        if(documentStatus.equals(FINAL)) {
            var status = objectMapper.readValue(data.get("status").toString(), new TypeReference<StatusBody>() {
            });
            laboralCertificationRequests.getStatus(otpJson.getString(TOKEN), status);
        }
        setInSession(SessionHelper.SessionData.TOKEN, otpJson.getString(TOKEN));
    }

    @When("el valida sus documentos")
    public void valityDocuments() throws MalformedURLException, JsonProcessingException {
        var data = DataUserInstance.getInstance().getData();
        var otpJson = requestsUtil.createAuthOTP();
        var objectMapper = new ObjectMapper();
        var valityStatus =objectMapper.readValue(data.get(Constants.VALITY_STATUS).toString(), new TypeReference<StatusBody>() {});
        laboralCertificationRequests.getValityStatus(otpJson.getString(TOKEN),valityStatus);
    }


    @Then("se visualiza codigo de error {int} en el certificado")
    public void verify101CodeCertificationError(int code){
        assertionsUtil.shouldSeeSuccessfulStatusCode();
        assertionsUtil.validateJSONSchema(SCHEMA_STATUS);
        laboralCertificationAssertions.verifyErrorCode(code);
    }

    @Then("se visualiza varios codigo de error en el certificado")
    public void verifyAllCodesCertificationErrors(){
        assertionsUtil.shouldSeeSuccessfulStatusCode();
        assertionsUtil.validateJSONSchema(SCHEMA_STATUS);
        laboralCertificationAssertions.verifyErrorCode(0);
    }

    @Then("se informa que su certificado esta vencido")
    public void verifyExpiredCertificated(){
        assertionsUtil.shouldSeeSuccessfulStatusCode();
        assertionsUtil.validateJSONSchema(SCHEMA_STATUS);
        laboralCertificationAssertions.verifyExpiredCertificated();
    }

    @Then("se valida estado exitoso de ambos documentos")
    public void verifySuccessfulAllDocuments(){
        assertionsUtil.shouldSeeSuccessfulStatusCode();
        assertionsUtil.validateJSONSchema(SCHEMA_STATUS);
        laboralCertificationAssertions.verifySuccessfulDocuments();
    }

    @Then("se valida que no tiene documentos precargados")
    public void verifyEmptyDocuments(){
        assertionsUtil.shouldSeeSuccessfulStatusCode();
        laboralCertificationAssertions.verifyEmptyDocuments();
    }
}
