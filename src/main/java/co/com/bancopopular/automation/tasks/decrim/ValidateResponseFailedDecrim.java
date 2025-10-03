package co.com.bancopopular.automation.tasks.decrim;

import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.models.decrim.requestresponse.RsValidateDecrim;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.questions.TheValue;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static org.hamcrest.Matchers.equalTo;

public class ValidateResponseFailedDecrim implements Task {

    public static ValidateResponseFailedDecrim forFaceDoesNotMatch() {
        return instrumented(ValidateResponseFailedDecrim.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        RsValidateDecrim rsValidateDecrimOrigin = actor.recall(Constants.DATA_RESPONSE_DECRIM_TO_VALIDATE);
        RsValidateDecrim rsValidateDecrim = actor.recall(Constants.BODY_RESPONSE_VALIDATE_DECRIM);

        actor.should(
                seeThat(TheValue.of("El ID del caso ", rsValidateDecrim.getIdCase()), equalTo(rsValidateDecrimOrigin.getIdCase())),
                seeThat(TheValue.of("El campo footprintConcept ", rsValidateDecrim.getFootprintConcept()), equalTo(rsValidateDecrimOrigin.getFootprintConcept())),
                seeThat(TheValue.of("El campo documentConcept ", rsValidateDecrim.getDocumentConcept()), equalTo(rsValidateDecrimOrigin.getDocumentConcept())),
                seeThat(TheValue.of("El campo state ", rsValidateDecrim.getState()), equalTo(rsValidateDecrimOrigin.getState())),
                seeThat(TheValue.of("El campo messageState ", rsValidateDecrim.getMessageState().trim()), equalTo(rsValidateDecrimOrigin.getMessageState())),
                seeThat(TheValue.of("El campo dateResponse ", rsValidateDecrim.getDateResponse()), equalTo(rsValidateDecrimOrigin.getDateResponse())),
                seeThat(TheValue.of("El campo proof ", rsValidateDecrim.getProof()), equalTo(rsValidateDecrimOrigin.getProof()))
        );
    }
}
