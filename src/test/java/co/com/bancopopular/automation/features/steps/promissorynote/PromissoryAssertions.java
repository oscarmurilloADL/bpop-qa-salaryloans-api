package co.com.bancopopular.automation.features.steps.promissorynote;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.annotations.Step;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PromissoryAssertions {

    private static final String DOCUMENT_PAYERS="[{code=510, name=SECRETARIA DE EDUCACION DE MEDELLIN DOCENTES, documents=[{codeDocument=0, nameDocument=DOCUMENTO DE IDENTIDAD, required=true, special=false, bankingInsurance=false, onbase={onBasePresent=false, expired=false, cucCode=0001, expirationDatePresent=null, documentAlreadyInCloud=false}, applyMandatoryFront=false}, {codeDocument=0, nameDocument=ORDEN DE DESCUENTO, required=true, special=false, bankingInsurance=false, onbase={onBasePresent=false, expired=false, cucCode=0202, expirationDatePresent=null, documentAlreadyInCloud=false}, applyMandatoryFront=false}, {codeDocument=0, nameDocument=PAGARE Y CARTA DE INSTRUCCIONES, required=true, special=false, bankingInsurance=false, onbase={onBasePresent=false, expired=false, cucCode=0207, expirationDatePresent=null, documentAlreadyInCloud=false}, applyMandatoryFront=false}, {codeDocument=0, nameDocument=COMPROBANTE DE PAGO, required=true, special=false, bankingInsurance=false, onbase={onBasePresent=false, expired=false, cucCode=0033, expirationDatePresent=null, documentAlreadyInCloud=true}, applyMandatoryFront=false}], platForm=null, insertionConcept=null, withdrawalConcepts=null, authorizationUrl=null, requiredTelephoneSale=true, onBaseServiceActive=true, showRnecReminderMessage=false}]";
    @Step("Validar solicitud del pagare unico")
    public void validateLoadPromissoryDocument(){
        assertThat(SerenityRest.then().extract().body().jsonPath().get().toString(),
                is(DOCUMENT_PAYERS));
    }
}
