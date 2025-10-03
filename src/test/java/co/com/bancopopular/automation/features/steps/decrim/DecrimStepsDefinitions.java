package co.com.bancopopular.automation.features.steps.decrim;

import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.models.decrim.requestresponse.RsCreateCase;
import co.com.bancopopular.automation.models.decrim.requestresponse.RsValidateDecrim;
import co.com.bancopopular.automation.tasks.decrim.AskDecrimForApproval;
import co.com.bancopopular.automation.tasks.decrim.CreateCase;
import co.com.bancopopular.automation.tasks.decrim.ValidateCreateCase;
import co.com.bancopopular.automation.tasks.decrim.ValidateResponseFailedDecrim;
import co.com.bancopopular.automation.utils.DataUserInstance;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.SneakyThrows;
import net.serenitybdd.screenplay.actors.OnStage;

import java.net.MalformedURLException;


public class DecrimStepsDefinitions {

    @SneakyThrows
    @When("el solicita validar los documentos a Decrim")
    public void requestValidatToDecrimDocuments() throws MalformedURLException {
        var objectMapper = new ObjectMapper();
        var theActor = OnStage.theActorInTheSpotlight();
        var data = DataUserInstance.getInstance().getData();
        theActor.remember(Constants.DATA_RESPONSE_CREATE_CASE_DECRIM_TO_VALIDATE, objectMapper.readValue(data.get("rsCreateCase").toString(), new TypeReference<RsCreateCase>() {}));
        theActor.remember(Constants.DATA_RESPONSE_DECRIM_TO_VALIDATE, objectMapper.readValue(data.get("rsValidateDecrim").toString(), new TypeReference<RsValidateDecrim>() {}));

        Constants.REQUESTS_UTIL.createAuthOTP();

        theActor.attemptsTo(
                CreateCase.toUploadDocumentsDecrim(),
                AskDecrimForApproval.ofTheUploadDocuments()
        );
    }

    @Then("el recibe un mensaje indicando que la autenticación fue fallida porque el rostro no coincide con el de referencia")
    public void validateMessageFaceNotValid() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                ValidateCreateCase.ofSendFiles(),
                ValidateResponseFailedDecrim.forFaceDoesNotMatch()
        );
    }
}
