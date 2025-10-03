package co.com.bancopopular.automation.features.steps.event.feature;

import co.com.bancopopular.automation.conf.StepBase;
import co.com.bancopopular.automation.utils.DataUserInstance;
import co.com.bancopopular.automation.utils.EventsUtil;
import co.com.bancopopular.automation.utils.RequestsUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.net.MalformedURLException;

public class EventStepsDefinition extends StepBase {


  EventRequests eventRequests = new EventRequests();
  RequestsUtil requestsUtil = new RequestsUtil();

  @When("él {}")
  public void sendEvent(String eventType) throws MalformedURLException {
    var event="";
    var eventText="";
    var otpJson = requestsUtil.createAuthOTP();

    switch (eventType) {
      case "acepta la oferta del crédito":
        event = EventsUtil.EVENT_CONDITIONS.toString();
        eventText = EventsUtil.EVENT_TEXT_CONDITIONS.toString();
        break;
      case "confirma los términos y condiciones del crédito":
        event = EventsUtil.EVENT_TERMS.toString();
        eventText = EventsUtil.EVENT_TEXT_TERMS.toString();
        break;
      case "finaliza la selección de la cuenta y los documentos":
        event = EventsUtil.EVENT_ACCOUNT.toString();
        eventText = EventsUtil.EVENT_TEXT_ACCOUNT.toString();
        break;
      default:
        event = null;
        eventText = null;
    }

    var data = DataUserInstance.getInstance().getData();
    eventRequests.getDataEvent(data.get("customerDocument").getAsString(),
        data.get("customerDocType").getAsString(),
        event, eventText, otpJson.getString("token"),otpJson.getString("name"));
  }

  @Then("se valida el evento")
  public void validateStatusEvent() {
    assertionsUtil.shouldSeeSuccessfulStatusCode();
  }
}