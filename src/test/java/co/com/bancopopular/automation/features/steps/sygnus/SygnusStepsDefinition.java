package co.com.bancopopular.automation.features.steps.sygnus;

import co.com.bancopopular.automation.conf.StepBase;
import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.features.steps.payrollhistory.PayrollHistoryRequests;
import co.com.bancopopular.automation.features.steps.renewal.RenewalRequests;
import co.com.bancopopular.automation.utils.DataUserInstance;
import co.com.bancopopular.automation.utils.RequestsUtil;
import co.com.bancopopular.automation.utils.SessionHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.annotations.Steps;
import redis.clients.jedis.Jedis;

import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static co.com.bancopopular.automation.utils.SessionHelper.setInSession;

public class SygnusStepsDefinition extends StepBase {

  @Steps
  static SygnusRequests sygnusRequests = new SygnusRequests();
  SygnusAssertions sygnusAssertions = new SygnusAssertions();
  static RequestsUtil requestsUtil = new RequestsUtil();
  RenewalRequests renewalRequests = new RenewalRequests();
  static PayrollHistoryRequests payrollHistoryRequests = new PayrollHistoryRequests();
  private static final String CUSTOMER_DOCUMENT = "customerDocument";
  private static final String CUSTOMER_DOC_TYPE = "customerDocType";
  private static final String PAYER_NAME = "payerName";
  private static final String TOKEN = "token";
  private static final String PROVIDER_ID = "providerId";
  private static final String OBLIGATION_ID = "obligationID";
  private static final String GENERATED_ID = "generatedId";

  @When("{word} verifica su disponible")
  public void verifyAvailable(String actor) throws MalformedURLException {

    Jedis jedis = new Jedis("gb-salaryloans-stg-redis.nu4apa.ng.0001.use2.cache.amazonaws.com", 6379);
    String value = jedis.get("CAJA DE RETIRO DE LAS FFMM");

    if (value != null) {
      System.out.println("Valor de la clave '\"CAJA DE RETIRO DE LAS FFMM\"': " + value);
    } else {
      System.out.println("La clave '\"CAJA DE RETIRO DE LAS FFMM\"' no existe.");
    }

    var data = DataUserInstance.getInstance().getData();
    var otpJson = requestsUtil.createAuthOTP();
    payrollHistoryRequests.getPayrollStatus(data.get(CUSTOMER_DOC_TYPE).getAsString(),
            data.get(CUSTOMER_DOCUMENT).getAsString(), otpJson.getString(TOKEN));
    sygnusRequests.sendPayerSygnus(data.get(CUSTOMER_DOCUMENT).getAsString(),
            data.get(CUSTOMER_DOC_TYPE).getAsString(),data.get(PAYER_NAME).getAsString(),
            otpJson.getString(TOKEN));
  }

  @Then("obtiene las vinculaciones en Sygnus")
  public void seeBindings() throws FileNotFoundException {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    assertionsUtil.validateEncryptedJSONSchemaArray("src/test/resources/schemas/sygnus_bindings.json");
    sygnusAssertions.verifyBindigs();
  }

  @Then("el cliente no tiene autorización en Sygnus")
  public void seeClientUnauthorizedSygnus() {
    assertionsUtil.shouldSeeInternalErrorStatusCode();
    sygnusAssertions.verifyAuthorizationSygnus();
  }

  @When("{word} selecciona el número de vinculación")
  public static void selectBinding(String actor) throws MalformedURLException, JsonProcessingException {
    var data = DataUserInstance.getInstance().getData();
    var otpJson = requestsUtil.createAuthOTP();

    OnStage.theActorInTheSpotlight().remember(Constants.RQ_ID_GENERATED, otpJson.getInt(GENERATED_ID));
    setInSession(SessionHelper.SessionData.REQUEST_ID, otpJson.getInt(GENERATED_ID));
    setInSession(SessionHelper.SessionData.TOKEN, otpJson.getString(TOKEN));
    setInSession(SessionHelper.SessionData.OBLIGATION_ID, data.get(OBLIGATION_ID).getAsString());

    payrollHistoryRequests.getPayrollStatusWithRetry(data.get(CUSTOMER_DOC_TYPE).getAsString(),
            data.get(CUSTOMER_DOCUMENT).getAsString(), otpJson.getString(TOKEN));
    sygnusRequests.sendPayerSygnus(data.get(CUSTOMER_DOCUMENT).getAsString(),
            data.get(CUSTOMER_DOC_TYPE).getAsString(),data.get(PAYER_NAME).getAsString(),
            otpJson.getString(TOKEN));

    sygnusRequests.sygnusPayrollCheckProcess(data, otpJson);

    sygnusRequests.sendBindingSygnus(data.get(CUSTOMER_DOCUMENT).getAsString(),
            data.get(CUSTOMER_DOC_TYPE).getAsString(),data.get(OBLIGATION_ID).getAsString(),data.get(PAYER_NAME).getAsString(),
            otpJson.getString(TOKEN), data.get(PROVIDER_ID).getAsString());
  }

  @Then("obtiene el cupo disponible en Sygnus")
  public void seeOfferBinding() {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
    assertionsUtil.validateJSONSchema("schemas/sygnus_offer.json");
    sygnusAssertions.verifyOfferBinding();
  }

  @Then("no obtiene cupo disponible en Sygnus")
  public void seeOfferError() {
    assertionsUtil.shouldSeeInternalErrorStatusCode();
    sygnusAssertions.verifyOfferError();
  }

  @Then("no puede finalizar su proceso de venta con Sygnus {word}")
  public void seeSygnusError(String errorCode) {
    renewalRequests.getRenewalStatusError(getFromSession(SessionHelper.SessionData.RENEWAL_ID),
            getFromSession(SessionHelper.SessionData.OBLIGATION_ID), getFromSession(SessionHelper.SessionData.TOKEN));
    assertionsUtil.shouldSeeInternalErrorStatusCode();
    sygnusAssertions.verifySygnusError(errorCode);
  }

  @Then("el cliente es desbordado por política de crecimiento")
  public void clientIsRejectedForGrowthPolicy() {
    assertionsUtil.shouldSeeInternalErrorStatusCode();
    sygnusAssertions.verifyGrowthPolicyError();
  }

  @Given("la plataforma esta {word}")
  public void checkSygnusStatus(String status) {
    sygnusAssertions.verifySygnusStatus(status);
  }
}