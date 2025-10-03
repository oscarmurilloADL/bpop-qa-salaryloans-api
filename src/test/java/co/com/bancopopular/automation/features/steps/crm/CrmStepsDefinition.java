package co.com.bancopopular.automation.features.steps.crm;

import co.com.bancopopular.automation.conf.StepBase;
import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.exceptions.UserNotFoundException;
import co.com.bancopopular.automation.features.steps.anyfeesimulation.AnyFeeSimulationRequests;
import co.com.bancopopular.automation.features.steps.mdm.MdmRequest;
import co.com.bancopopular.automation.features.steps.tap.LaboralCertificationRequests;
import co.com.bancopopular.automation.models.mdm.MDM;
import co.com.bancopopular.automation.models.tap.StatusBody;
import co.com.bancopopular.automation.utils.DataUserInstance;
import co.com.bancopopular.automation.utils.RequestsUtil;
import co.com.bancopopular.automation.utils.SessionHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.annotations.Steps;

import java.net.MalformedURLException;

import static co.com.bancopopular.automation.constants.Constants.*;
import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;

public class CrmStepsDefinition extends StepBase {

  @Steps
  CrmAssertions crmAssertions;
  private static final String CUSTOMERDOCTYPE = "customerDocType";
  private static final String TOKEN = "token";
  MdmRequest mdmRequest = new MdmRequest();

  CrmRequests crmRequests = new CrmRequests();
  RequestsUtil requestsUtil = new RequestsUtil();
  LaboralCertificationRequests tapRequest = new LaboralCertificationRequests();
  AnyFeeSimulationRequests anyFeeSimulationRequests = new AnyFeeSimulationRequests();

  @When("{word} consulta sus datos personales")
  public void sendRequestInfo(String actor) throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();
    crmRequests.consultCustomer(data.get(CUSTOMERDOCTYPE).getAsString(),
            data.get(CUSTOMERDOCUMENT).getAsString());
  }

  @Then("{word} puede visualizarlos")
  public void seeInfo(String actor) {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    if(getFromSession(SessionHelper.SessionData.PROCESS_DATA).equals(Constants.CRM)) {
      assertionsUtil.validateJSONSchema("schemas/infoclient.json");
    }else{
      assertionsUtil.validateJSONSchema("schemas/infoclientMDM.json");
    }
  }

  @When("{word} envía sus nuevos datos personales")
  public void sendPersonalInfo(String actor) throws MalformedURLException, JsonProcessingException {
    var data = DataUserInstance.getInstance().getData();
    var otpJson = requestsUtil.createAuthOTP();
    if (getFromSession(SessionHelper.SessionData.PROCESS_DATA).equals(Constants.CRM)) {
      crmRequests.updateCustomer(data.get(CUSTOMERDOCTYPE).getAsString(),
              data.get(CUSTOMERDOCUMENT).getAsString(),
              otpJson.getString(TOKEN));
    } else {
      var objectMapper = new ObjectMapper();
      var bodyRequestUpdateMDM =objectMapper.readValue(data.get("bodyRequestUpdate").toString(), new TypeReference<MDM>() {});
      mdmRequest.updateCustomerMDM(bodyRequestUpdateMDM,mdmRequest.headersMDM(otpJson.getString(TOKEN)));
    }
  }

  @Then("su información es actualizada")
  public void infoIsUpdated() {
    crmAssertions.verifyCustomerIsUpdated();
  }

  @Then("el valida los datos enviados en el request de {word}")
  public void validateMDMSearchRequest(String method){
    var data = DataUserInstance.getInstance().getData();
    crmAssertions.verifyRequest(method,data);
  }

  @When("{word} consulta el estado civil")
  public void sendMaritalStatus(String actor) throws MalformedURLException {
    crmRequests.askMaritalStatus();
  }

  @Then("puede ver las opciones de estado civil")
  public void seeMaritalStatusOptions() throws UserNotFoundException {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    crmAssertions.verifyMaritalStatus();
  }

  @When("{word} consulta la ciudad")
  public void sendCity(String actor) throws MalformedURLException {
    crmRequests.consultCity();
  }

  @When("ella envía los valores para actualizar sus datos")
  public void sendOffering() throws MalformedURLException, JsonProcessingException {
    var data = DataUserInstance.getInstance().getData();
    var otpJson = requestsUtil.createAuthOTP();
    var objectMapper = new ObjectMapper();
    var valityStatus =objectMapper.readValue(data.get(Constants.VALITY_STATUS).toString(), new TypeReference<StatusBody>() {});

    tapRequest.getCreditTypeDecision(data.get(CUSTOMER_DOCUMENT).getAsString(),otpJson.getString(TOKEN),NEW_PAYROLL_LOAN);
    tapRequest.getSector(otpJson.getString(TOKEN));
    tapRequest.getChoosePayrollcheck(data.get(CUSTOMER_DOCUMENT).getAsString(),otpJson.getString(TOKEN),data.get(SECTOR_NUMBER).getAsString());
    tapRequest.getValityStatus(otpJson.getString(TOKEN),valityStatus);

    anyFeeSimulationRequests.callCNCSimulation(data,otpJson.getString(TOKEN));

    crmRequests.offering(data.get(CLIENT_INFO).getAsString(),data.get(CUSTOMERDOCUMENT).getAsString(),
            data.get(PAYER_NIT).getAsString(),data.get(CLIENT_TYPE).getAsString(), data.get(SUB_SECTOR_NUMBER).getAsString());
  }

  @Then("puede seleccionar las opciones de ciudad")
  public void seeCity() {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    crmAssertions.verifyCity();
  }

  @Then("se genera un error por preselecta, debe volver a procesar la operación")
  public void clientCrmCnc() {
    assertionsUtil.shouldSeeInternalErrorStatusCode();
    crmAssertions.verifyCrmFail();
  }
  @Entonces("ella visualiza un mensaje de error indicando que fue negado por Preselecta")
  public void validateMessageErrorByPreselecta(){
    assertionsUtil.shouldSeeInternalErrorStatusCode();
    crmAssertions.verifyCrmFailByPreselectaToBadBehavior();
  }
}