package co.com.bancopopular.automation.tasks.consultpayrollloan;

import co.com.bancopopular.automation.questions.TheCustomerInquiry;
import co.com.bancopopular.automation.utils.WprCipher;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.questions.TheValue;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static org.hamcrest.Matchers.equalTo;

public class ClientSearchResponse implements Task {
    private final String clientWithDisabledAccount;

    public ClientSearchResponse(String clientWithDisabledAccount) {
        this.clientWithDisabledAccount = clientWithDisabledAccount;
    }

    public static ClientSearchResponse toValidate(String clientWithDisabledAccount) {
        return instrumented(ClientSearchResponse.class, clientWithDisabledAccount);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        var dataToValidate = OnStage.theActorInTheSpotlight().asksFor(TheCustomerInquiry.ofTheLasResponse(clientWithDisabledAccount));
        var encryptedBody = SerenityRest.then().extract().body().asString();
        var decryptedBody = WprCipher.decryptRequest(encryptedBody);
        if(!dataToValidate.getCode().equals("")) {
            OnStage.theActorInTheSpotlight().should(
                    seeThat(TheValue.of("El código de respuesta ", decryptedBody.getString("code")), equalTo(dataToValidate.getCode())),
                    seeThat(TheValue.of("La descripción del código ", decryptedBody.getString("description")), equalTo(dataToValidate.getDescription()))
            );
        }else{
            OnStage.theActorInTheSpotlight().should(
                    seeThat(TheValue.of("valor del celular", decryptedBody.getString("cellphone")), equalTo(""))
            );
        }
    }
}
