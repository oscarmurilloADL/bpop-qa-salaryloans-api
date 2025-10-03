package co.com.bancopopular.automation.features.steps.accept;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.annotations.Step;

public class AcceptAssertions {

  @Step("Aceptacion datos del credito")
  public void verifyCreditData() {
    assertThat(SerenityRest.then().extract().body().jsonPath().getString("status"),
        is("OK"));
  }

}
