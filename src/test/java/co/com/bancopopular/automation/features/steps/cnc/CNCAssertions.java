package co.com.bancopopular.automation.features.steps.cnc;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.annotations.Step;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CNCAssertions {

    private static final String CODE = "code";
    private static final String RESULT = "result";
    private static final String VALID_AGE = "que es";
    private static final String DESCRIPTION="description";
    private static final String STATUS_OCR_BY_DOCUMENT = "statusOCRByDocument";
    private static final String ERROR = "errors";
    private static final String ONBASE_CODE = "onbaseCode";
    private static final String STATUS = "status";
    private static final String ONBASE_CODE_33="0033";
    private static final String ONBASE_CODE_30="0030";
    private static final String ONE_POSITION="[1].";
    private static final String CERO_POSITION="[0].";
    private static final String FAIL = "FAIL";
    private static final String VALIDATION_STATUS="validationStatus";
    private static final String TIMEOUT="TIMEOUT";
    private static final String CERTIFICATE="certificado";
    private static final String OK ="OK";
    private static final String PROVIDER_ID="providerId";
    private static final String NOT_EXIST = "NOT_EXIST";
    private static final String DOCUMENTS="documents";


    @Step("Verificar que el cliente es apto para novar una libranza por cnc educativo")
    public void verifyAgeLess70(String status) {
        if (status.equals(VALID_AGE)) {
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(CODE),
                    is("CustomerAgeService8881"));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(RESULT),
                    is("client is viable"));
        } else {
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(CODE),
                    is("CustomerAgeService8889"));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(DESCRIPTION),
                    is("the client have more 70 years old and belong to the sector Educativo"));
        }
    }


    public void verifyErrorPayrollcheck(String error) {
        if(error.equals(TIMEOUT)){
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + CERO_POSITION + ONBASE_CODE),
                    is(ONBASE_CODE_33));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + CERO_POSITION + STATUS),
                    is(error));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(VALIDATION_STATUS),
                    is(error));

        }else {
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + CERO_POSITION + ONBASE_CODE),
                    is(ONBASE_CODE_33));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + CERO_POSITION + STATUS),
                    is(FAIL));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + CERO_POSITION + ERROR + "." + CODE),
                    is(error));
        }
    }

    public void verifyRequestLoadOf(String document) {
        if(document.equals(CERTIFICATE)){
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(DOCUMENTS+CERO_POSITION + ONBASE_CODE),
                    is(ONBASE_CODE_33));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(DOCUMENTS+CERO_POSITION + STATUS),
                    is(OK));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(DOCUMENTS+CERO_POSITION + PROVIDER_ID),
                    is("5003141"));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(DOCUMENTS+ONE_POSITION + ONBASE_CODE),
                    is(ONBASE_CODE_30));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(DOCUMENTS+ONE_POSITION + STATUS),
                    is(NOT_EXIST));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(DOCUMENTS+ONE_POSITION + PROVIDER_ID),
                    is(""));

        }else{
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(DOCUMENTS+CERO_POSITION + ONBASE_CODE),
                    is(ONBASE_CODE_33));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(DOCUMENTS+CERO_POSITION + STATUS),
                    is(NOT_EXIST));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(DOCUMENTS+CERO_POSITION + PROVIDER_ID),
                    is(""));
        }
    }

    public void verifyDateInCache(String paymentIncomeDt, String date) {
        assertThat(paymentIncomeDt,is(date));
    }
}
