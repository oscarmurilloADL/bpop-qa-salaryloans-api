package co.com.bancopopular.automation.features.steps.consultpayrolloan;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.annotations.Step;

public class ConsultPayrollLoanAssertions {

  @Step("Verificar estado de la libranza")
  public void verifyPayrollLoanStatus() {
    assertThat(SerenityRest.then().extract().body().jsonPath().getString("causal"),
        is("6"));
    assertThat(SerenityRest.then().extract().body().jsonPath().getString("cellPhoneNumber"),
        is(""));
    assertThat(SerenityRest.then().extract().body().jsonPath().getString("payerName"),
        is("SECRETARIA DE EDUCACION DE GIRON - GIRON - 481 -"));
    assertThat(SerenityRest.then().extract().body().jsonPath().getString("state"),
        is("4"));
    assertThat(SerenityRest.then().extract().body().jsonPath().getString("toDo"),
        is("No tiene documentos"));
  }

  @Step("Verificar que la venta no fue insertada con la causal de caida")
  public void clientSygnusWithFailedRenewall() {
    assertThat(SerenityRest.then().extract().body().jsonPath().getString("causal"),
            is("29"));
    assertThat(SerenityRest.then().extract().body().jsonPath().getString("toDo"),
            is("No insertado en pruebas"));
  }
}
