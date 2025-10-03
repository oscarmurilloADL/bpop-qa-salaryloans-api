package co.com.bancopopular.automation.features.steps.tap;

import co.com.bancopopular.automation.constants.Constants;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.annotations.Step;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LaboralCertificationAssertions {

    private static final String STATUS_OCR_BY_DOCUMENT = "statusOCRByDocument";
    private static final String PROVIDER_ID = "providerId";
    private static final String ONBASE_CODE = "onbaseCode";
    private static final String SUCCESS="SUCCESS";
    private static final String FAIL ="FAIL";
    private static final String FAIL_OCR="FAIL_OCR";
    private static final String STATUS = "status";
    private static final String ERRORS = "errors";
    private static final String CODE = "code";
    private static final String CERO="0";
    private static final String POSITION="[0].";
    private static final String ONE_POSITION="[1].";
    private static final String CODE_101="laborCertification101";
    private static final String CODE_102="laborCertification102";
    private static final String CODE_103="laborCertification103";
    private static final String CODE_104="laborCertification104";
    private static final String CODE_105="laborCertification105";
    private static final String CODE_106="laborCertification106";
    private static final String ONBASE_CODE_30="0030";
    private static final String ONBASE_CODE_33="0033";
    private static final String []LABORAL_ERRORS={CODE_101,CODE_102,CODE_103,CODE_104,CODE_106};
    private String providerId = "";
    private String codeError = "";
    private static final String OK="OK";
    private static final String NOT_EXIST="NOT_EXIST";
    private static final String DOCUMENTS="documents";

    @Step("Verificación codigo de error en el certificado")
    public void verifyErrorCode(int code) {

        getProviderAndCode(code);

        assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + PROVIDER_ID),
                is(providerId));
        assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + ONBASE_CODE),
                is(ONBASE_CODE_30));
        assertThat(SerenityRest.then().extract().body().jsonPath().getString(Constants.VALIDATION_STATUS),
                is(FAIL_OCR));
        assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + STATUS),
                is(FAIL));

        if (codeError.equals(CERO)) {
            for(var x = 0 ; x < LABORAL_ERRORS.length ; x++) {
                assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + ERRORS + "["+x+"]." + CODE),
                        is(LABORAL_ERRORS[x]));
            }
        } else {
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + ERRORS + POSITION + CODE),
                    is(codeError));
        }
    }

    @Step("Verificación certificado expirado")
    public void verifyExpiredCertificated() {
        assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + PROVIDER_ID),
                is("4001352"));
        assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + ONBASE_CODE),
                is(ONBASE_CODE_30));
        assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + STATUS),
                is("EXPIRED"));
    }

    @Step("Verificación documentos exitosos")
    public void verifySuccessfulDocuments(){
        assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + PROVIDER_ID),
                is("5002002"));
        assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + ONBASE_CODE),
                is(ONBASE_CODE_30));
        assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + STATUS),
                is(OK));
        assertThat(SerenityRest.then().extract().body().jsonPath().getString(Constants.VALIDATION_STATUS),
                is(SUCCESS));
        assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + ONE_POSITION + PROVIDER_ID),
                is("5002001"));
        assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + ONE_POSITION + ONBASE_CODE),
                is(ONBASE_CODE_33));
        assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + ONE_POSITION + STATUS),
                is(OK));
        assertThat(SerenityRest.then().extract().body().jsonPath().getString(Constants.VALIDATION_STATUS),
                is(SUCCESS));
    }

    private void getProviderAndCode(int code){
        switch (code) {
            case 101:
                codeError = CODE_101;
                providerId = "4001302";
                break;
            case 102:
                codeError = CODE_102;
                providerId = "4001312";
                break;
            case 103:
                codeError = CODE_103;
                providerId = "4001322";
                break;
            case 104:
                codeError = CODE_104;
                providerId = "4001332";
                break;
            case 105:
                codeError = CODE_105;
                providerId = "4001162";
                break;
            case 106:
                codeError = CODE_106;
                providerId = "4001152";
                break;
            default:
                codeError = CERO;
                providerId = "4001342";
                break;
        }
    }

    @Step("Verificación no existencia de documentos en dynamoDB")
    public void verifyEmptyDocuments() {
        assertThat(SerenityRest.then().extract().body().jsonPath().getString(DOCUMENTS + POSITION + STATUS),
                is(NOT_EXIST));
        assertThat(SerenityRest.then().extract().body().jsonPath().getString(DOCUMENTS + POSITION + ONBASE_CODE),
                is(ONBASE_CODE_33));
        assertThat(SerenityRest.then().extract().body().jsonPath().getString(DOCUMENTS + POSITION + PROVIDER_ID),
                is(""));
        assertThat(SerenityRest.then().extract().body().jsonPath().getString(DOCUMENTS + ONE_POSITION + STATUS),
                is(NOT_EXIST));
        assertThat(SerenityRest.then().extract().body().jsonPath().getString(DOCUMENTS + ONE_POSITION + ONBASE_CODE),
                is(ONBASE_CODE_30));
        assertThat(SerenityRest.then().extract().body().jsonPath().getString(DOCUMENTS + ONE_POSITION + PROVIDER_ID),
                is(""));
    }
}
