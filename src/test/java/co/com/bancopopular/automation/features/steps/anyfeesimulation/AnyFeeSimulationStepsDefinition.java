package co.com.bancopopular.automation.features.steps.anyfeesimulation;

import co.com.bancopopular.automation.conf.StepBase;
import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.data.Environment;
import co.com.bancopopular.automation.features.steps.payrollhistory.PayrollHistoryRequests;
import co.com.bancopopular.automation.features.steps.tap.PayrollCheckRequests;
import co.com.bancopopular.automation.rest.requests.SearchRequests;
import co.com.bancopopular.automation.utils.DataUserInstance;
import co.com.bancopopular.automation.utils.RequestsUtil;
import co.com.bancopopular.automation.utils.SessionHelper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;
import org.aeonbits.owner.ConfigFactory;

import java.net.MalformedURLException;

import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static co.com.bancopopular.automation.utils.SessionHelper.setInSession;

public class AnyFeeSimulationStepsDefinition extends StepBase {

  PayrollCheckRequests payrollCheckRequests = new PayrollCheckRequests();
  AnyFeeSimulationRequests anyFeeSimulationRequests = new AnyFeeSimulationRequests();
  RequestsUtil requestsUtil = new RequestsUtil();
  PayrollHistoryRequests payrollHistoryRequests = new PayrollHistoryRequests();
  private static final String OBLIGATION_ID = "obligationID";
  private static final String CUSTOMER_DOC_TYPE = "customerDocType";
  private static final String CUSTOMER_DOCUMENT = "customerDocument";
  private static final String TOKEN = "token";
  private static final String ID_TAP="idTap";

  Environment environment = ConfigFactory.create(Environment.class);

  @Steps
  AnyFeeSimulationAssertions anyFeeSimulationAssertions;
  SearchRequests searchRequests = new SearchRequests();

  @Then("se valida los valores máximos del crédito")
  public void validateMaxValues()  {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    assertionsUtil.validateJSONSchema("schemas/anyfeecreditdata.json");
    anyFeeSimulationAssertions.verifyCreditData();
  }

  @Then("{word} no puede continuar debido a que no tiene la estructura correcta")
  public void validateErrorSimulation(String actor)  {
    anyFeeSimulationAssertions.verifyErrorCreditData();
  }

  @When("{word} envia la petición para monto y plazo máximo")
  public void requireMaxValues(String actor) throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();
    var otpJson = requestsUtil.createAuthOTP();
    setInSession(SessionHelper.SessionData.TOKEN, otpJson.getString(TOKEN));
    if(getFromSession(SessionHelper.SessionData.OPTIMIZATION_TOGGLE_II).equals("")) {
      payrollCheckRequests.sendPayrollCheck(data.get(OBLIGATION_ID).getAsString(),
              data.get(CUSTOMER_DOC_TYPE).getAsString(),
              data.get(CUSTOMER_DOCUMENT).getAsString(),
              otpJson.getString(TOKEN));
      anyFeeSimulationRequests.checkProcessIsFinished(data.get(ID_TAP).getAsString(),
              data.get(OBLIGATION_ID).getAsString(),
              data.get(CUSTOMER_DOC_TYPE).getAsString(),
              data.get(CUSTOMER_DOCUMENT).getAsString());
    }else{
      anyFeeSimulationRequests.checkProcessIsFinished(data.get(ID_TAP).getAsString(),
              data.get(OBLIGATION_ID).getAsString(),
              data.get(CUSTOMER_DOC_TYPE).getAsString(),
              data.get(CUSTOMER_DOCUMENT).getAsString());
    }
  }

  @When("{word} envia la petición al simulador")
  public void requireSimulateError(String actor) throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();
    var otpJson = requestsUtil.createAuthOTP();
    setInSession(SessionHelper.SessionData.TOKEN, otpJson.getString(TOKEN));
    if(getFromSession(SessionHelper.SessionData.OPTIMIZATION_TOGGLE_II).equals("")) {
      payrollCheckRequests.sendPayrollCheck(data.get(OBLIGATION_ID).getAsString(),
              data.get(CUSTOMER_DOC_TYPE).getAsString(),
              data.get(CUSTOMER_DOCUMENT).getAsString(),
              otpJson.getString(TOKEN));
      anyFeeSimulationRequests.checkProcessHasError(data.get(ID_TAP).getAsString(),
              data.get(OBLIGATION_ID).getAsString(),
              data.get(CUSTOMER_DOC_TYPE).getAsString(),
              data.get(CUSTOMER_DOCUMENT).getAsString());
    }else{
      var customerInfo = new String[]  {data.get(CUSTOMER_DOC_TYPE).getAsString(), data.get(CUSTOMER_DOCUMENT).getAsString(), data.get(OBLIGATION_ID).getAsString()};
      payrollCheckRequests.getSimulationStatus(customerInfo,
              data.get(Constants.PAYER_NIT).getAsString(),
              data.get(Constants.SECTOR_NUMBER).getAsString(),
              data.get(Constants.SUB_SECTOR_NUMBER).getAsString(),
              data.get(ID_TAP).getAsString(),
              otpJson.getString(TOKEN));
    }
  }

  @When("{word} envia la petición por primera vez al simulador")
  public void requireSimulationAnswer(String actor) throws MalformedURLException {
    var data = DataUserInstance.getInstance().getData();
    var otpJson = requestsUtil.createAuthOTP();
    setInSession(SessionHelper.SessionData.TOKEN, otpJson.getString(TOKEN));
    payrollHistoryRequests.getPayrollStatus(data.get(CUSTOMER_DOC_TYPE).getAsString(),
            data.get(CUSTOMER_DOCUMENT).getAsString(), otpJson.getString(TOKEN));
  }

  @Then("se valida el codigo de error del simulador {word}")
  public void validateErrorFirstSimulation(String code)  {
    anyFeeSimulationAssertions.verifySimulationError(code);
  }

}