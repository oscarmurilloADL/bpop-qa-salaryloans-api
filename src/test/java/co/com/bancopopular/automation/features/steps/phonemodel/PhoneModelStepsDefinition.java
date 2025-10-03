package co.com.bancopopular.automation.features.steps.phonemodel;

import co.com.bancopopular.automation.conf.StepBase;
import co.com.bancopopular.automation.models.phonemodel.Upload;
import co.com.bancopopular.automation.utils.DataUserInstance;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.net.MalformedURLException;
import net.serenitybdd.annotations.Steps;

public class PhoneModelStepsDefinition extends StepBase {

  @Steps
  PhoneModelRequests phoneModelRequests = new PhoneModelRequests();
  PhoneModelAssertions phoneModelAssertions = new PhoneModelAssertions();

  private static final String CUSTOMER_DOCUMENT = "customerDocument";
  private static final String CUSTOMER_DOC_TYPE = "customerDocType";

  @When("{word} solicita el enlace para subir su desprendible")
  public void sendUploadLink(String actor) throws MalformedURLException, JsonProcessingException {
    var data = DataUserInstance.getInstance().getData();
    var objectMapper = new ObjectMapper();
    var bodyRequestUploads =objectMapper.readValue(data.get("uploads").toString(), new TypeReference<Upload>() {});
    phoneModelRequests.getPayrollCheckUploads(bodyRequestUploads);
  }

  @Then("se envia el enlace exitosamente")
  public void validateLinkSend() {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    assertionsUtil.validateJSONSchema("schemas/phone_model.json");
    phoneModelAssertions.verifyLinkSent();
  }

  @When("{word} consulta su número de documento en el enlace")
  public void consultClient(String actor) throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();

    phoneModelRequests.getPayrollCheckUploadsClient(data.get(CUSTOMER_DOC_TYPE).getAsString(),
        data.get(CUSTOMER_DOCUMENT).getAsString());
  }

  @Then("ingresa al cargue del desprendible")
  public void getPayrollCheckLoad() {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    phoneModelAssertions.verifyClientFound();
  }

  @Then("no es posible ingresar al cargue del desprendible")
  public void getNotPayrollCheckLoad() {
    assertionsUtil.shouldSeeInternalErrorStatusCode();
    phoneModelAssertions.verifyClientNotFound();
  }

  @When("{word} selecciona el archivo a cargar")
  public void selectFile(String actor) throws MalformedURLException, JsonProcessingException {
    var data = DataUserInstance.getInstance().getData();
    var objectMapper = new ObjectMapper();
    var bodyRequestUploads =objectMapper.readValue(data.get("uploads").toString(), new TypeReference<Upload>() {});
    phoneModelRequests.getPayrollCheckUploads(bodyRequestUploads);

    phoneModelRequests.getPayrollCheckCredentialsClient(data.get(CUSTOMER_DOC_TYPE).getAsString(),
        data.get(CUSTOMER_DOCUMENT).getAsString());
  }

  @Then("se carga el desprendible exitosamente")
  public void getPayrollCheckSuccess() {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    phoneModelAssertions.verifyPayrollCheckSuccess();
  }
}