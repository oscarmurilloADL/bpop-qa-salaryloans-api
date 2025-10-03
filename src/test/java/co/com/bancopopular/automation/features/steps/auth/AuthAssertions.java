package co.com.bancopopular.automation.features.steps.auth;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import co.com.bancopopular.automation.utils.WprCipher;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.annotations.Step;

public class AuthAssertions {

  private static final String CODE = "code";
  private static final String MESSAGE = "message";

  @Step("Verificacion datos generacion confirmacion OTP")
  public void verifyGenerateConfirmationOTP() {
    assertThat(SerenityRest.then().extract().body().jsonPath().getString("result"),
        is("OTP successfully generated"));
  }

  @Step("Verificación datos validación confirmación OTP")
  public void verifyValidationConfirmationOTP() {
    assertThat(SerenityRest.then().extract().body().jsonPath().getString(CODE),
        is("0000"));
    assertThat(SerenityRest.then().extract().body().jsonPath().getString(MESSAGE),
        is("OTP CONFIRMED"));
  }

  @Step("Verificación datos login de admin fábrica")
  public void verifyLoginFactoryAdmin() {
    var response = SerenityRest.then().extract().asString();
    JsonPath jsonResponse = WprCipher.decryptRequest(response);
    assertThat(jsonResponse.getString("profile"),
            is("FABRICA"));
  }

  @Step("Verificación datos generacion OTP autenticación")
  public void verifyGenerateAuthOTP() {
    var response = SerenityRest.then().extract().asString();
    JsonPath jsonResponse = WprCipher.decryptRequest(response);
    assertThat(jsonResponse.getString("code"),
        is("0"));
    assertThat(jsonResponse.getString(MESSAGE),
        is("Transacción Exitosa"));
  }

  @Step("Verificación datos generacion OTP por llamada autenticación")
  public void verifyGeneratePhoneAuthOTP() {
    assertThat(SerenityRest.then().extract().body().jsonPath().getString("code"),
        is("0"));
    assertThat(SerenityRest.then().extract().body().jsonPath().getString(MESSAGE),
        is("Transacción Exitosa"));
  }

  @Step("Verificación datos validacion OTP autenticación")
  public void verifyOTPAuth() {
    var response = SerenityRest.then().extract().asString();
    JsonPath jsonResponse = WprCipher.decryptRequest(response);
    assertThat(jsonResponse.getString("returnCode"),
        is("0"));
  }

  @Step("Verificación datos validación OTP incorrecto")
  public void verifyFailedOTPAuth() {
    var response = SerenityRest.then().extract().asString();
    JsonPath jsonResponse = WprCipher.decryptRequest(response);
    assertThat(jsonResponse.getString("code"),
        is("AthManagement9999"));
    assertThat(jsonResponse.getString("description"),
        is("Unknown Error"));
  }

  @Step("Verificación datos validación OTP de confirmación incorrecto")
  public void verifyValidationConfirmationOTPIncorrect() {
    assertThat(SerenityRest.then().extract().body().jsonPath().getString("code"),
        is("AuditError0007"));
    assertThat(SerenityRest.then().extract().body().jsonPath().getString("description"),
        is("Invalid otp"));
  }
}