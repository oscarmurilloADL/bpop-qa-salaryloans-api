package co.com.bancopopular.automation.features.steps.legalconditions;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.annotations.Step;

public class LegalConditionsAssertions {

  @Step("se valida su respuesta")
  public void validateDataTreatment() {
    assertThat(SerenityRest.then().extract().body().jsonPath().getString("statusCode"),
        is("OK"));
    assertThat(SerenityRest.then().extract().body().jsonPath().getString("description"),
        is(""));
  }
}