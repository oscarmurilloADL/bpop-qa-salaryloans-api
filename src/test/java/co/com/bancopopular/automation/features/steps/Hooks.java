package co.com.bancopopular.automation.features.steps;

import co.com.bancopopular.automation.actors.ApiCast;
import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.data.Environment;
import co.com.bancopopular.automation.features.steps.toggles.TogglesDescription;
import co.com.bancopopular.automation.features.steps.toggles.TogglesRequests;
import co.com.bancopopular.automation.rest.requests.LoginRequests;
import co.com.bancopopular.automation.utils.*;
import com.google.gson.Gson;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.annotations.Steps;
import net.thucydides.model.util.EnvironmentVariables;
import org.aeonbits.owner.ConfigFactory;
import io.restassured.path.json.JsonPath;
import java.net.MalformedURLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static co.com.bancopopular.automation.utils.SessionHelper.setInSession;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Hooks {

    @Steps
    TogglesRequests togglesRequests = new TogglesRequests();
    LoginRequests loginRequests = new LoginRequests();
    Environment environment = ConfigFactory.create(Environment.class);
    private EnvironmentVariables environmentVariables;
    private Boolean connectToParametersStg = false;
    private static final String FALSE="false";
    private static final String ON="ON";

    @Before(order = 1)
    public void settingTheStage() throws MalformedURLException {
        SerenityRest.enableLoggingOfRequestAndResponseIfValidationFails();
        OnStage.setTheStage(new ApiCast(environmentVariables, connectToParametersStg));
        JsonPath advisorData = loginRequests.advisorByOffice("51919050");
        var jsonResponse = WprCipher.decryptRequest(SerenityRest.then().extract().asString());
        updateSessionWithResponseData(jsonResponse,advisorData);
        togglesRequests.searchToggles();
        var decryptedAnswer = WprCipher.decryptRequest(SerenityRest.then().extract().body().asString()).get().toString();
        decryptedAnswer = decryptedAnswer.replaceAll("usuario,", "usuario")
                .replaceAll("automática,", "automática")
                .replaceAll("=", "\":\"")
                .replaceAll(", ", "\",\"")
                .replaceAll("\\{", "{\"")
                .replaceAll("\\}", "\"}")
                .replaceAll(":true,", ":\"true\",")
                .replaceAll(":false,", ":\"false\",")
                .replaceAll("}\",\"","},");
        var gson = new Gson();
        TogglesDescription[] togglesDescriptions = gson.fromJson(decryptedAnswer, TogglesDescription[].class);
        setInitialSessionValues();
        updateSessionBasedOnToggles(togglesDescriptions);
    }

    private void setInitialSessionValues(){
        setInSession(SessionHelper.SessionData.OPTIMIZATION_TOGGLE_II,ON);
        setInSession(SessionHelper.SessionData.PROCESS_DATA,Constants.MDM);
        setInSession(SessionHelper.SessionData.TOGGLE_SINGLE_PROMISSORY,ON);
        setInSession(SessionHelper.SessionData.OWNERSHIP_REFACTOR_TOGGLE,ON);
        setInSession(SessionHelper.SessionData.PAYSTUBS_SYGNUS_REQUIRED,ON);
        setInSession(SessionHelper.SessionData.EXTRACTOR,ON);
        setInSession(SessionHelper.SessionData.RISK_EMGINE,ON);
    }

    private void updateSessionWithResponseData(JsonPath jsonResponse, JsonPath advisorData) {
        setInSession(SessionHelper.SessionData.ACCESS_KEY, jsonResponse.get("accessKey"));
        setInSession(SessionHelper.SessionData.SECRET_KEY, jsonResponse.get("secretKey"));
        setInSession(SessionHelper.SessionData.SESSION_TOKEN, advisorData.getString("sessionToken"));
    }

    private void updateSessionBasedOnToggles(TogglesDescription[] togglesDescriptions) {
        Map<String, Consumer<String>> updateActions = createUpdateActionsMap();

        for (TogglesDescription value : togglesDescriptions) {
            String featureName = value.getFeatureName();
            String featureEnabled = value.getFeatureEnabled();

            if (FALSE.equals(featureEnabled) && updateActions.containsKey(featureName)) {
                updateActions.get(featureName).accept("");
            }
        }
    }

    private Map<String, Consumer<String>> createUpdateActionsMap() {
        Map<String, Consumer<String>> updateActions = new HashMap<>();

        updateActions.put("PAYROLL_CHECK_OPTIMIZATION_II", value -> setInSession(SessionHelper.SessionData.OPTIMIZATION_TOGGLE_II, value));
        updateActions.put("USE_MDM_SERVICE_TOGGLE", value -> setInSession(SessionHelper.SessionData.PROCESS_DATA, Constants.CRM));
        updateActions.put("USE_TOGGLE_SINGLE_PROMISSORY_NOTE", value -> setInSession(SessionHelper.SessionData.TOGGLE_SINGLE_PROMISSORY, value));
        updateActions.put("OWNERSHIP_REFACTOR_TOGGLE", value -> setInSession(SessionHelper.SessionData.OWNERSHIP_REFACTOR_TOGGLE, value));
        updateActions.put("EXTRACTION_AND_INTERPRETER", value -> setInSession(SessionHelper.SessionData.EXTRACTOR, value));
        updateActions.put("RISK_ENGINE_ACCOUNT_IMPOUND", value -> setInSession(SessionHelper.SessionData.RISK_EMGINE, value));

        return updateActions;
    }

    @Before(value="@Parameters", order=0)
    public void validateAdmin(){
        connectToParametersStg = true;
    }

    private Optional<String> testCaseIDOptional;
    int status = 0;
    String testCase;
    private final Pattern pattern = Pattern.compile("C[1-9]+");
    Matcher matcher;

    @Before(order = 1)
    public void beforeScenario(Scenario sc) {
        Collection<String> tags = sc.getSourceTagNames();
        this.testCaseIDOptional = tags.stream().findFirst();
        OnStage.setTheStage(new ApiCast(environmentVariables, connectToParametersStg));
    }

    @After
    public void afterScenario(Scenario sc) {
        if (this.testCaseIDOptional.isPresent()) {
            matcher = pattern.matcher(this.testCaseIDOptional.toString());
            if (matcher.find()) {
                this.status = sc.isFailed() ? 5 : 1;
                this.testCase = this.testCaseIDOptional.toString().replace("\\[", "")
                        .replace("\\]", "");
                this.testCase = this.testCase.split("C")[1];
                TestCasesInstance.getInstance().setData(this.testCase, this.status);
                TestCasesInstance.getInstance().getData().get(this.testCase);
            }
        }
    }
}
