package co.com.bancopopular.automation.tasks.decrim;

import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.models.decrim.requestresponse.RsCreateCase;
import co.com.bancopopular.automation.utils.WprCipher;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.questions.TheValue;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static org.hamcrest.Matchers.equalTo;

public class ValidateCreateCase implements Task {
    public static ValidateCreateCase ofSendFiles() {
        return instrumented(ValidateCreateCase.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        RsCreateCase rsCreateCaseToValidate = actor.recall(WprCipher.decryptRequest(actor.recall(Constants.DATA_RESPONSE_CREATE_CASE_DECRIM_TO_VALIDATE)).get());
        RsCreateCase rsCreateCase = actor.recall(Constants.BODY_RESPONSE_CREATE_CASE);

        actor.should(
                seeThat(TheValue.of("El ID del caso ", rsCreateCase.getIdCase()), equalTo(rsCreateCaseToValidate.getIdCase())),
                seeThat(TheValue.of("El mensaje de caso creado ", rsCreateCase.getResponseMessage()), equalTo(rsCreateCaseToValidate.getResponseMessage()))
        );
    }
}
