package co.com.bancopopular.automation.features.steps.tap;

import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.utils.AssertionsUtil;
import co.com.bancopopular.automation.utils.SessionHelper;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.annotations.Steps;

public class PayrollCheckAssertions {

    @Steps
    AssertionsUtil assertionsUtil;

    private static final String CODE = "code";
    private static final String ID = "id";
    private static final String DESCRIPTION = "description";
    private static final String AMOUNT_GROWTH_CODE = "GrowthAmount001";
    private static final String AMOUNT_GROWTH_DESCRIPTION = "Offer Doesnt Reach Minimum Growth Amount Policy";
    private static final String CONTINUE_SALE_DESCRIPTION = "The information was properly processed - At this moment we are calculating the offer to be made according to its discount capacity";
    private static final String CONTINUE_SALE_CODE = "PayrollChecks012";
    private static final String UNREADABLE_DESCRIPTION="Unreadable PayrollCheck";
    private static final String INCOMPLETRE_DESCIPTION="Incomplete PayrollCheck";
    private static final String UNREADABLE_CODE="PayrollChecks004";
    private static final String NON_PAYABLE_CUSTOMER_CODE="PayrollChecks009";
    private static final String DOCUMENT_CODE_NOT_READABLE="PayrollChecks020";
    private static final String NOT_SERVICE_SYGNUS="PayrollChecks012";
    private static final String ERROR_DESCRIPTION="Not payroll check";
    private static final String ERROR_CODE="PayrollChecks001";
    private static final String LOAN_RENEW_VALIDATION="loanRenewValidation";
    private static final String RENEW_VALIDATION_DESCRIPTION="[NOT_APPROVE_EXTERNAL_VIABILITY]";
    private static final String STATUS = "status";
    private static final String OK="OK";
    private static final String ONBASE_CODE = "onbaseCode";
    private static final String ONBASE_CODE_33="0033";
    private static final String STATUS_OCR_BY_DOCUMENT = "statusOCRByDocument";
    private static final String SUCCESS="SUCCESS";
    private static final String POSITION="[0].";
    private static final String FAIL="FAIL";
    private static final String FAIL_OCR="FAIL_OCR";
    private static final String ERRORS="errors";
    private static final String PAYROLL_017="PayrollChecks017";
    private static final String PAYROLL_007="PayrollChecks007";

    @Step("Verificación datos subida desprendible")
    public void verifyUploadData(int customerClient) {
        var optimization=getFromSession(SessionHelper.SessionData.OPTIMIZATION_TOGGLE_II).toString();
        if(optimization.equals("")) {
            assertionsUtil.validateJSONSchema("schemas/payrollcheckupload.json");
            assertThat(SerenityRest.then().extract().body().jsonPath().getInt(ID),
                    is(customerClient));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(CODE),
                    is("PayrollChecks005"));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(DESCRIPTION),
                    is("PayrollCheck in Progress"));
        }else{
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT+POSITION+STATUS),
                    is(OK));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT+POSITION+ONBASE_CODE),
                    is(ONBASE_CODE_33));
        }
    }

    @Step("Verificación datos termina proceso de lectura")
    public void verifyProcessIsFinished() {
        var optimization=getFromSession(SessionHelper.SessionData.OPTIMIZATION_TOGGLE_II).toString();
        if(optimization.equals("")) {
            assertThat(SerenityRest.then().extract().body().jsonPath().getInt(ID),
                    is(500017));
            var codeResponse = SerenityRest.then().extract().body().jsonPath().getString("code");
            if (codeResponse != null) {
                assertThat(SerenityRest.then().extract().body().jsonPath().getString(CODE),
                        is(CONTINUE_SALE_CODE));
                assertThat(SerenityRest.then().extract().body().jsonPath().getString(DESCRIPTION),
                        is(CONTINUE_SALE_DESCRIPTION));
            } else {
                assertionsUtil.validateJSONSchema("schemas/anyfeecreditdata.json");
            }
        }else{
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + ONBASE_CODE),
                    is(ONBASE_CODE_33));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + STATUS),
                    is(OK));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(Constants.VALIDATION_STATUS),
                    is(SUCCESS));
        }
    }

    @Step("Verificación datos desprendible con titular incorrecto")
    public void verifyInvalidPayrollCheckHolder() {
        var optimization=getFromSession(SessionHelper.SessionData.OPTIMIZATION_TOGGLE_II).toString();
        if(optimization.equals("")) {
            assertionsUtil.shouldSeeInternalErrorStatusCode();
            assertThat(SerenityRest.then().extract().body().jsonPath().getInt(ID),
                    is(500026));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(CODE),
                    is(PAYROLL_017));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(DESCRIPTION),
                    is("Invalid PayrollCheck Horlder"));
        }else{
            assertionsUtil.shouldSeeSuccessfulStatusCode();
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + ONBASE_CODE),
                    is(ONBASE_CODE_33));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + STATUS),
                    is(FAIL));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(Constants.VALIDATION_STATUS),
                    is(FAIL_OCR));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + ERRORS + POSITION + CODE),
                    is(PAYROLL_017));
        }
    }

    @Step("Verificación datos desprendible vencido")
    public void verifyExpiredPayrollCheck() {
        var optimization=getFromSession(SessionHelper.SessionData.OPTIMIZATION_TOGGLE_II).toString();
        if(optimization.equals("")) {
            assertionsUtil.shouldSeeInternalErrorStatusCode();
            assertThat(SerenityRest.then().extract().body().jsonPath().getInt(ID),
                    is(500021));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(CODE),
                    is(PAYROLL_007));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(DESCRIPTION),
                    is("Invalid validity PayrollCheck"));
        }else{
            assertionsUtil.shouldSeeSuccessfulStatusCode();
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + ONBASE_CODE),
                    is(ONBASE_CODE_33));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + STATUS),
                    is(FAIL));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(Constants.VALIDATION_STATUS),
                    is(FAIL_OCR));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + ERRORS + POSITION + CODE),
                    is(PAYROLL_007));
        }
    }

    @Step("Verificación datos desprendible no sujeto de libranza")
    public void     verifyNotSubjectToPayroll() {
        var optimization=getFromSession(SessionHelper.SessionData.OPTIMIZATION_TOGGLE_II).toString();
        if(optimization.equals("")) {
            assertionsUtil.shouldSeeInternalErrorStatusCode();
            assertThat(SerenityRest.then().extract().body().jsonPath().getInt(ID),
                    is(8));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(CODE),
                    is(NON_PAYABLE_CUSTOMER_CODE));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(DESCRIPTION),
                    is(UNREADABLE_DESCRIPTION));
        }else{
            assertionsUtil.shouldSeeSuccessfulStatusCode();
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + ONBASE_CODE),
                    is(ONBASE_CODE_33));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + STATUS),
                    is(FAIL));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(Constants.VALIDATION_STATUS),
                    is(FAIL_OCR));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + ERRORS + POSITION + CODE),
                    is(NON_PAYABLE_CUSTOMER_CODE));
        }
    }

    @Step("Verificación datos desprendible no cumple política de crecimiento")
    public void verifyNotGrowthAmount() {
        var optimization=getFromSession(SessionHelper.SessionData.OPTIMIZATION_TOGGLE_II).toString();
        if(optimization.equals("")) {
            assertionsUtil.shouldSeeInternalErrorStatusCode();
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(CODE),
                    is(AMOUNT_GROWTH_CODE));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(DESCRIPTION),
                    is(AMOUNT_GROWTH_DESCRIPTION));
        }else{
            assertionsUtil.shouldSeeSuccessfulStatusCode();
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(Constants.SIMULATION_ERROR_CODE),
                    is(AMOUNT_GROWTH_CODE));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(Constants.SIMULATION_ERROR_MSG),
                    is(AMOUNT_GROWTH_DESCRIPTION));
        }
    }

    @Step("Verificación carga de desprendible con valor descuento3 mayor o igual a la cuota actual")
    public void verifyDocumentWasProcessed() {
        var optimization=getFromSession(SessionHelper.SessionData.OPTIMIZATION_TOGGLE_II).toString();
        if(optimization.equals("")) {
            assertThat(SerenityRest.then().extract().body().jsonPath().getInt(ID),
                    is(500040));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(DESCRIPTION),
                    is(CONTINUE_SALE_DESCRIPTION));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(CODE),
                    is(CONTINUE_SALE_CODE));
        }else{
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + ONBASE_CODE),
                    is(ONBASE_CODE_33));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + STATUS),
                    is(OK));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(Constants.VALIDATION_STATUS),
                    is(SUCCESS));
        }
    }

    @Step("Verificación carga no exitosa del desprendible")
    public void verifyDocumentWasNotProcessed()  {
        var optimization=getFromSession(SessionHelper.SessionData.OPTIMIZATION_TOGGLE_II).toString();
        if(optimization.equals("")) {
            assertionsUtil.shouldSeeInternalErrorStatusCode();
            assertThat(SerenityRest.then().extract().body().jsonPath().getInt(ID),
                    is(31));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(DESCRIPTION),
                    is(UNREADABLE_DESCRIPTION));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(CODE),
                    is(UNREADABLE_CODE));
        }else{
            assertionsUtil.shouldSeeSuccessfulStatusCode();
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + ONBASE_CODE),
                    is(ONBASE_CODE_33));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + STATUS),
                    is(FAIL));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(Constants.VALIDATION_STATUS),
                    is(FAIL_OCR));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + ERRORS + POSITION + CODE),
                    is(UNREADABLE_CODE));
        }
    }

    @Step("Verificación cliente no sujeto de libranza")
    public void verifyNonPayableCustomer()  {
        var optimization=getFromSession(SessionHelper.SessionData.OPTIMIZATION_TOGGLE_II).toString();
        if(optimization.equals("")) {
            assertionsUtil.shouldSeeInternalErrorStatusCode();
            assertThat(SerenityRest.then().extract().body().jsonPath().getInt(ID),
                    is(500999));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(DESCRIPTION),
                    is(UNREADABLE_DESCRIPTION));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(CODE),
                    is(DOCUMENT_CODE_NOT_READABLE));
        }else{
            assertionsUtil.shouldSeeSuccessfulStatusCode();
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + ONBASE_CODE),
                    is(ONBASE_CODE_33));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + STATUS),
                    is(FAIL));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(Constants.VALIDATION_STATUS),
                    is(FAIL_OCR));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + ERRORS + POSITION + CODE),
                    is(DOCUMENT_CODE_NOT_READABLE));
        }
    }

    @Step("Verificación cliente no sujeto de libranza")
    public void checkTearOffNotReadable()  {
        var optimization=getFromSession(SessionHelper.SessionData.OPTIMIZATION_TOGGLE_II).toString();
        if(optimization.equals("")) {
            assertionsUtil.shouldSeeInternalErrorStatusCode();
            assertThat(SerenityRest.then().extract().body().jsonPath().getInt(ID),
                    is(500112));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(DESCRIPTION),
                    is(INCOMPLETRE_DESCIPTION));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(CODE),
                    is(DOCUMENT_CODE_NOT_READABLE));
        }else{
            assertionsUtil.shouldSeeSuccessfulStatusCode();
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + ONBASE_CODE),
                    is(ONBASE_CODE_33));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + STATUS),
                    is(FAIL));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(Constants.VALIDATION_STATUS),
                    is(FAIL_OCR));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + ERRORS + POSITION + CODE),
                    is(DOCUMENT_CODE_NOT_READABLE));
        }
    }

    @Step("Verificación carga exitosa del nuevo desprendible")
    public void verifyNewDocumentWasProcessed()  {
        var optimization=getFromSession(SessionHelper.SessionData.OPTIMIZATION_TOGGLE_II).toString();
        if(optimization.equals("")) {
            assertThat(SerenityRest.then().extract().body().jsonPath().getInt(ID),
                    is(500004));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(CODE),
                    is(CONTINUE_SALE_CODE));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(DESCRIPTION),
                    is(CONTINUE_SALE_DESCRIPTION));
        }else{
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + ONBASE_CODE),
                    is(ONBASE_CODE_33));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + STATUS),
                    is(OK));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(Constants.VALIDATION_STATUS),
                    is(SUCCESS));
        }
    }

    @Step("Verificación no se consuman los servicios de Sygnus")
    public void checkNoConsumptionServices()  {
        var optimization=getFromSession(SessionHelper.SessionData.OPTIMIZATION_TOGGLE_II).toString();
        if(optimization.equals("")) {
            assertThat(SerenityRest.then().extract().body().jsonPath().getInt(ID),
                    is(500078));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(DESCRIPTION),
                    is(CONTINUE_SALE_DESCRIPTION));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(CODE),
                    is(NOT_SERVICE_SYGNUS));
        }else{
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + ONBASE_CODE),
                    is(ONBASE_CODE_33));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(STATUS_OCR_BY_DOCUMENT + POSITION + STATUS),
                    is(OK));
            assertThat(SerenityRest.then().extract().body().jsonPath().getString(Constants.VALIDATION_STATUS),
                    is(SUCCESS));
        }
    }

    @Step("Verificación activacion del boton para volver a subir desprendible")
    public void verifyUploadButtonIsActivated(){
        assertThat(SerenityRest.then().extract().body().jsonPath().getString(ID),
                is("unknown"));
        assertThat(SerenityRest.then().extract().body().jsonPath().getString(CODE),
                is(ERROR_CODE));
        assertThat(SerenityRest.then().extract().body().jsonPath().getString(DESCRIPTION),
                is(ERROR_DESCRIPTION));
    }
    @Step("Verificación reporte de hábito de pago con otra entidad")
    public void verifyPaymentHabitReport(){
        assertThat(SerenityRest.then().extract().body().jsonPath().getString(LOAN_RENEW_VALIDATION),
                is(RENEW_VALIDATION_DESCRIPTION));
        assertionsUtil.validateJSONSchema("schemas/payrollstatus.json");
    }

}