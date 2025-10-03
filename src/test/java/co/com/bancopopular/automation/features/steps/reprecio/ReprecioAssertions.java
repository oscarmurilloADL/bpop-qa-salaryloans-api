package co.com.bancopopular.automation.features.steps.reprecio;

import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.utils.WprCipher;
import net.serenitybdd.rest.SerenityRest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReprecioAssertions {

    private static final String GROWTH_POLICY="rechazada por politica de crecimiento";
    private static final String NOT_ENABLED_CHANNEL="no habilitada por el canal";
    private static final String ENABLED_CHANNEL="habilitada por el canal";
    private static final String ANSWER_ENABLED_CHANNEL="[{obligationId=26003240400190, payerName=FIDUPREVISORA FONDO PENSIONES MAGISTERIO - C. UNIVERSITARIA - 012 -, payerUniqueName=FIDUPREVISORA FONDO PENSIONES MAGISTERIO - C. UNIVERSITARIA - 012 -, payerWeb=, payerNit=800900, currentDeb=7000000, payerId=10 - Fiduprevisora, payerLocationCode=10 - Fiduprevisora, loanRenewValidation=NOT_APPROVE_PAYER_NOT_SOPPORTED_ADL, customerInBureauTrail=false, platformAuthorizationUrl=null, openingObligationDate=2014-03-18}, {obligationId=11111140400190, payerName=FIDUPREVISORA FONDO PENSIONES MAGISTERIO, payerUniqueName=FIDUPREVISORA FONDO PENSIONES MAGISTERIO, payerWeb=, payerNit=8605251485, currentDeb=3557402, payerId=10 - Fopep, payerLocationCode=10 - Fopep, loanRenewValidation=APPROVE, customerInBureauTrail=false, platformAuthorizationUrl=null, openingObligationDate=2000-01-01}]";




    public void validatePayment(String param) {
        if(param.equals(GROWTH_POLICY)){
            assertThat(SerenityRest.then().extract().body().jsonPath().get(Constants.SIMULATION_ERROR_CODE).toString(), is(Constants.GROWTH_AMOUNT_002));
            assertThat(SerenityRest.then().extract().body().jsonPath().get(Constants.SIMULATION_ERROR_MSG).toString(), is(Constants.GROWTH_AMOUNT_002_DESC));
            assertThat(SerenityRest.then().extract().body().jsonPath().get(Constants.VALIDATION_STATUS).toString(), is(Constants.FAIL_SIMULATION));
        }else {
            var encryptedBody = SerenityRest.then().extract().body().asString();
            var decryptedBody = WprCipher.decryptRequest(encryptedBody);
            switch (param) {
                case NOT_ENABLED_CHANNEL:
                    assertThat(decryptedBody.getList("loanRenewValidation").get(0),
                            is("NOT_APPROVE_PAYER_NOT_SOPPORTED_ADL"));
                    break;
                case ENABLED_CHANNEL:
                    assertThat(decryptedBody.get().toString(), is(ANSWER_ENABLED_CHANNEL));
                    break;
                default:
                    break;
            }
        }
    }

    public void validateCustomerNotEligibleForRepricing(String customerDocument){
        var encryptedBody = SerenityRest.then().extract().body().asString();
        var decryptedBody = WprCipher.decryptRequest(encryptedBody);
        assertThat(decryptedBody.get().toString(),is("{salesModel=null, documentType=CC, documentNumber=" + customerDocument + ", repricing=[]}"));
    }

    public void validateReportedCustomer(){
        var encryptedBody = SerenityRest.then().extract().body().asString();
        var decryptedBody = WprCipher.decryptRequest(encryptedBody);
        assertThat(decryptedBody.get().toString(),is("{code=CustomerManagement023, description=Client was found in risk bases}"));
    }

    public void validateCustomerNotInCRMMDM(){
        var encryptedBody = SerenityRest.then().extract().body().asString();
        var decryptedBody = WprCipher.decryptRequest(encryptedBody);
        assertThat(decryptedBody.get().toString(),is("{code=CustomerManagement001, description=Customer not found}"));
    }
}
