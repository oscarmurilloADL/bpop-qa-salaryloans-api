package co.com.bancopopular.automation.features.steps.promissorynote;

import co.com.bancopopular.automation.conf.StepBase;
import co.com.bancopopular.automation.features.steps.renewal.RenewalRequests;
import co.com.bancopopular.automation.utils.DataUserInstance;
import co.com.bancopopular.automation.utils.RequestsUtil;
import co.com.bancopopular.automation.utils.SessionHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;

import java.net.MalformedURLException;

import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static junit.framework.TestCase.fail;

public class PromissoryStepDefinition extends StepBase {

    private static final String TOKEN="token";

    RequestsUtil requestsUtil = new RequestsUtil();
    RenewalRequests renewalRequests = new RenewalRequests();
    
    @Steps
    PromissoryAssertions promissoryAssertions = new PromissoryAssertions();


    @When("se realiza el proceso de consulta en la BD de pagare unico")
    public void consultSinglePromissoryNoteDatabase() throws MalformedURLException, JsonProcessingException {
        var data = DataUserInstance.getInstance().getData();
        var otpJson = requestsUtil.createAuthOTP();

        if(getFromSession(SessionHelper.SessionData.TOGGLE_SINGLE_PROMISSORY).equals("ON")){
            renewalRequests.getDocumentsPayer(data, getFromSession(SessionHelper.SessionData.JOURNEY_ID),
                    otpJson.getString(TOKEN));
        }else{
            throw new IllegalStateException("El toggle USE_TOGGLE_SINGLE_PROMISSORY_NOTE está desactivado. Por favor, actívalo.");
        }
    }

    @Then("el aplicativo si debe solicitar al asesor cargar el documento del pagare")
    public void validateLoadPromissoryDocument(){
        promissoryAssertions.validateLoadPromissoryDocument();
    }

}
