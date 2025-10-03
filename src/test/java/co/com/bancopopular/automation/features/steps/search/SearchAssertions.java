package co.com.bancopopular.automation.features.steps.search;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import co.com.bancopopular.automation.utils.WprCipher;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.annotations.Step;

public class SearchAssertions {

    private static final String CUSTOMER_NOT_FOUND_CODE = "CustomerManagement001";
    private static final String CUSTOMER_NOT_FOUND_DESCRIPTION = "Customer not found";
    private static final String DEROGATORY_ERROR = "CustomerManagement014";
    private static final String CELL_PHONE="cellphone";
    private static final String REPORTED_CLIENT_ERROR = "CustomerManagement023";
    private static final String REPORTED_CLIENT = "Client was found in risk bases";
    private static final String TECHNICAL_FAILURE_ERROR = "CustomerManagement9999";
    private static final String TECHNICAL_FAILURE = "Unknown Error";
    private static final String DESCRIPTION = "description";

  @Step("Verificacion datos cliente no encontrado")
  public void verifyCustomerNotFound() {
        JsonPath decryptedBody = WprCipher.extractDecryptResponse();
    assertThat(decryptedBody.getString("code"),
        is(CUSTOMER_NOT_FOUND_CODE));
    assertThat(decryptedBody.getString(DESCRIPTION),
        is(CUSTOMER_NOT_FOUND_DESCRIPTION));
  }

  @Step("Verificacion datos cliente sin celular")
  public void verifyCustomerDoesNotHaveCellphone() {
    JsonPath decryptedBody = WprCipher.extractDecryptResponse();
    assertThat(decryptedBody.getString(CELL_PHONE),
        is(""));
  }

  @Step("Verificacion datos cliente con libranza en estado derogatorio")
  public void verifyCustomerCanceledPayroll() {
    JsonPath decryptedBody = WprCipher.extractDecryptResponse();
    assertThat(decryptedBody.getString("code"),
        is(DEROGATORY_ERROR));
    assertThat(decryptedBody.getString(DESCRIPTION),
        is(""));
  }

    @Step("Verificacion estado de los datos del cliente")
    public void verifyCustomerStatusCellphoneNumber(String cellPhoneNumber) {
        JsonPath decryptedBody = WprCipher.extractDecryptResponse();
        if(cellPhoneNumber.equals("")) {
            assertThat(decryptedBody.getString(CELL_PHONE),
                    is(""));
        }else{
            assertThat(decryptedBody.getString(CELL_PHONE),
                    is(cellPhoneNumber));
        }
    }

    @Step("Validacion flag de tipo de cliente")
    public void validateCustomerTypeFlag(String flag){
        var encryptedBody = SerenityRest.then().extract().body().asString();
        var decryptedBody = WprCipher.decryptRequest(encryptedBody);
        assertThat(decryptedBody.getString("customerType"),equalTo(flag));
    }

    @Step("Validacion reporte del cliente en bases de riesgo")
    public void validateCustomerReportedODM(){
        JsonPath decryptedBody = WprCipher.extractDecryptResponse();
        assertThat(decryptedBody.getString("code"),
                is(REPORTED_CLIENT_ERROR));
        assertThat(decryptedBody.getString(DESCRIPTION),
                is(REPORTED_CLIENT));
    }

    @Step("Validacion falla técnica")
    public void validateTechnicalFailure(){
        JsonPath decryptedBody = WprCipher.extractDecryptResponse();
        assertThat(decryptedBody.getString("code"),
                is(TECHNICAL_FAILURE_ERROR));
        assertThat(decryptedBody.getString(DESCRIPTION),
                is(TECHNICAL_FAILURE));
    }
}
