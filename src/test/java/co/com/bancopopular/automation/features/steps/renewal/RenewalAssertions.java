package co.com.bancopopular.automation.features.steps.renewal;

import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.utils.SessionHelper;
import co.com.bancopopular.automation.utils.WprCipher;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.annotations.Step;

import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RenewalAssertions {

    private static final String CODE = "code";
    private static final String DESCRIPTION = "description";
    private static final String AMOUNT_GROWTH_CODE = "GrowthAmount001";
    private static final String AMOUNT_GROWTH_DESCRIPTION = "Offer Doesnt Reach Minimum Growth Amount Policy";

    @Step("verificación de que no tiene documentos pendientes por cargar")
    public void verifyEmptyDocuments(){
        var encryptedBody = SerenityRest.then().extract().body().asString();
        var decryptedBody = WprCipher.decryptRequest(encryptedBody);
        assertThat(decryptedBody.getString("userLoans"),
                is("[]"));
    }

    @Step("verificación de desborde por no ser apto para recálculo ordinaria y no tener disponible BRMS")
    public void verifyGrowthAmountPolicyError() {
        var optimization=getFromSession(SessionHelper.SessionData.OPTIMIZATION_TOGGLE_II).toString();
        if(optimization.equals("")) {
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(CODE),
                    is(AMOUNT_GROWTH_CODE));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(DESCRIPTION),
                    is(AMOUNT_GROWTH_DESCRIPTION));
        }else{
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(Constants.SIMULATION_ERROR_CODE),
                    is(AMOUNT_GROWTH_CODE));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(Constants.SIMULATION_ERROR_MSG),
                    is(AMOUNT_GROWTH_DESCRIPTION));
        }
    }

    @Step("verificacion de desborde para no permitirle continuar por el canal digital")
    public void verifyValueIsNegative() {
        assertThat(SerenityRest.then().extract().body().jsonPath().getString("code"),
                is("PayerPlatform12"));
        assertThat(SerenityRest.then().extract().body().jsonPath().getString(DESCRIPTION),
                is("Other Negative Law Discounts"));
    }
}
