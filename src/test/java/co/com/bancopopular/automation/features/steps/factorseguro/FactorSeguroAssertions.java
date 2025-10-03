package co.com.bancopopular.automation.features.steps.factorseguro;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.annotations.Step;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FactorSeguroAssertions {

    private static final String FACTOR_SEGURO = "data.factorSeguro";

    @Step("se verifica el porcentaje 0.0073")
    public void verificaPorcentaje00073(){
        ExtractableResponse<Response> response = SerenityRest.then().extract();
        assertThat(response.jsonPath().get(FACTOR_SEGURO),
                is(Float.parseFloat("0.0073")));
    }
    @Step("se verifica el porcentaje 0.112")
    public void verificaPorcentaje0112(){
        ExtractableResponse<Response> response = SerenityRest.then().extract();
        assertThat(response.jsonPath().get(FACTOR_SEGURO),
                is(Float.parseFloat("0.112")));
    }

    @Step("se verifica el porcentaje 0.0245")
    public void verificaPorcentaje00245(){
        ExtractableResponse<Response> response = SerenityRest.then().extract();
        assertThat(response.jsonPath().get(FACTOR_SEGURO),
                is(Float.parseFloat("0.0245")));
    }

    @Step("se verifica el porcentaje 0.075")
    public void verificaPorcentaje0075(){
        ExtractableResponse<Response> response = SerenityRest.then().extract();
        assertThat(response.jsonPath().get(FACTOR_SEGURO),
                is(Float.parseFloat("0.075")));
    }

    @Step("se verifica el porcentaje 0.125")
    public void verificaPorcentaje0125(){
        ExtractableResponse<Response> response = SerenityRest.then().extract();
        assertThat(response.jsonPath().get(FACTOR_SEGURO),
                is(Float.parseFloat("0.125")));
    }
}
