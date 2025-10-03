package co.com.bancopopular.automation.features.steps.cnc;

import co.com.bancopopular.automation.conf.StepBase;
import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.models.tap.StatusBody;
import co.com.bancopopular.automation.utils.DataUserInstance;
import co.com.bancopopular.automation.utils.RedisUtil;
import co.com.bancopopular.automation.utils.RequestsUtil;
import co.com.bancopopular.automation.utils.SessionHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;

import java.net.MalformedURLException;

import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;

public class CNCStepsDefinition extends StepBase {

    private static final String TOKEN = "token";

    @Steps
    RequestsUtil requestsUtil = new RequestsUtil();
    CNCRequest cncRequest = new CNCRequest();
    CNCAssertions cncAssertions = new CNCAssertions();
    RedisUtil redisUtil = new RedisUtil();


    @When("se consulta su edad")
    public void consultAge() throws MalformedURLException, JsonProcessingException {

        var data = DataUserInstance.getInstance().getData();
        var otpJson = requestsUtil.createAuthOTP();

        var objectMapper = new ObjectMapper();
        var ageBody =objectMapper.readValue(data.get("ageBody").toString(), new TypeReference<StatusBody>() {});

        cncRequest.getAge(otpJson.getString(TOKEN),ageBody);

    }

    @Then("se valida {} apto para novar una libranza")
    public void verifyAge(String status){
        cncAssertions.verifyAgeLess70(status);
    }

    @Then("se muestra el error con codigo {word}")
    public void verifyErrorNotCorrespondsToThePayment(String error){
        assertionsUtil.shouldSeeSuccessfulStatusCode();
        cncAssertions.verifyErrorPayrollcheck(error);
    }

    @Then("se solicita cargue unicamente del {word}")
    public void requestLoadOf(String document){
        assertionsUtil.shouldSeeSuccessfulStatusCode();
        cncAssertions.verifyRequestLoadOf(document);
    }

    @Then("se muestra la fecha {word} en cache")
    public void viewDateInCache(String date){
        var data = DataUserInstance.getInstance().getData();
        cncAssertions.verifyDateInCache(redisUtil.getRedisPayrollDate(data.get(Constants.CUSTOMER_DOCUMENT).getAsString(),
                "paymentIncomeDt"),date);
    }

    @Then("el declara la fecha {} de la pagaduria")
    public void inputPayrollDate(String date) throws MalformedURLException {
        var data = DataUserInstance.getInstance().getData();
        cncRequest.inputPayrollDate(data,getFromSession(SessionHelper.SessionData.TOKEN),date);
    }


}
