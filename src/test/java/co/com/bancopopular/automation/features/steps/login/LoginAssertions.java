package co.com.bancopopular.automation.features.steps.login;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;

import co.com.bancopopular.automation.utils.WprCipher;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.annotations.Step;

public class LoginAssertions {

  @Step("Verificacion datos asesor no encontrado")
  public void verifyAdvisorNotFound() {
    var response = SerenityRest.then().extract().asString();
    var jsonResponse = WprCipher.decryptRequest(response);
    assertThat(jsonResponse.getString("code"),
        is("AdvisorManagement001"));
    assertThat(jsonResponse.getString("description"),
        is("Advisor not found"));
  }

  @Step("Verificacion datos asesor encontrado")
  public void verifyAdvisorIsFound() {
    var response = SerenityRest.then().extract().asString();
    var jsonResponse = WprCipher.decryptRequest(response);
    assertThat(jsonResponse.getInt("identificationNumber"),
        is(51919050));
    assertThat(jsonResponse.getString("name"),
            anyOf(is("STELLA CARDENAS GUZMAN"),
                    is("STELLA IB\u00C1\u00D1EZ GUZM\u00C1N")));
    assertThat(jsonResponse.getInt("idChannel"),
        is(1));
    assertThat(jsonResponse.getString("nameChannel"),
        is("RED DE OFICINAS"));
    assertThat(jsonResponse.getBoolean("administrativeOffice"),
        is(false));
  }

}
