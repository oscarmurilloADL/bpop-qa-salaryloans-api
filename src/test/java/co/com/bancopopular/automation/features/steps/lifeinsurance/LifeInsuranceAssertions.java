package co.com.bancopopular.automation.features.steps.lifeinsurance;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.annotations.Step;

public class LifeInsuranceAssertions {

  @Step("Información exitosa")
  public void verifyLifeInsurance() {
    assertThat(SerenityRest.then().extract().body().jsonPath().getString("responseDescription"),
        is("Information saved successfully."));
  }
}