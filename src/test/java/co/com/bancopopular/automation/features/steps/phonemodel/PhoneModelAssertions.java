package co.com.bancopopular.automation.features.steps.phonemodel;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import co.com.bancopopular.automation.utils.WprCipher;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.annotations.Step;

public class PhoneModelAssertions {

  @Step("Verificación de envío de enlace")
  public void verifyLinkSent() {
    assertThat(SerenityRest.then().extract().body().jsonPath().getString("mailSent"),
        is("true"));
    assertThat(SerenityRest.then().extract().body().jsonPath().getString("result"),
        is("OK"));
    assertThat(SerenityRest.then().extract().body().jsonPath().getString("smsSent"),
        is("true"));
  }

  @Step("Verificación de cliente habilitado en el enlace")
  public void verifyClientFound() {
    var response = SerenityRest.then().extract().asString();
    var jsonResponse = WprCipher.decryptRequest(response);
    assertThat(jsonResponse.getString("documentUpload"),
        is("true"));
  }

  @Step("Verificación de cliente no habilitado en el enlace")
  public void verifyClientNotFound() {
    var response = SerenityRest.then().extract().asString();
    var jsonResponse = WprCipher.decryptRequest(response);
    assertThat(jsonResponse.getString("code"),
        is("PayrollCheckUpload001"));
    assertThat(jsonResponse.getString("description"),
        is("Error: Client is not enabled to upload documents"));
  }

  @Step("Validación de cargue de desprendible")
  public void verifyPayrollCheckSuccess() {
    assertThat(SerenityRest.then().extract().body().jsonPath().getString("idObligation"),
        is(""));
  }
}