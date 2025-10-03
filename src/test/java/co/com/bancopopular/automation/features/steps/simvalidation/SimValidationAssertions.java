package co.com.bancopopular.automation.features.steps.simvalidation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import co.com.bancopopular.automation.utils.WprCipher;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.annotations.Step;

public class SimValidationAssertions {

  private static final String AUTHENTICATION_SIM_SUCCESSFUL = "AUTHENTICATION_SIM_SUCCESSFUL";
  private static final String AUTHENTICATION_SIM_SUCCESSFUL_CODE = "01";
  private static final String AUTHENTICATION_SIM_PENDING = "AUTHENTICATION_SIM_PENDING";
  private static final String AUTHENTICATION_SIM_PENDING_CODE = "02";

  @Step("Verificacion de respuesta con AUTHENTICATION_SIM_SUCCESSFUL_CODE")
  public void verifyPhoneNumber() {
    var response = SerenityRest.then().extract().body().asString();
    assertThat( WprCipher.decryptRequest(response).getString("status"),
        is(AUTHENTICATION_SIM_SUCCESSFUL));
    assertThat(WprCipher.decryptRequest(response).getString("codeStatus"),
        is(AUTHENTICATION_SIM_SUCCESSFUL_CODE));
  }

  @Step("Verificacion de respuesta con AUTHENTICATION_SIM_PENDING")
  public void verifyPhoneNumberPending() {
    var response = SerenityRest.then().extract().body().asString();
    assertThat(WprCipher.decryptRequest(response).getString("status"),
        is(AUTHENTICATION_SIM_PENDING));
    assertThat(WprCipher.decryptRequest(response).getString("codeStatus"),
        is(AUTHENTICATION_SIM_PENDING_CODE));
  }
}