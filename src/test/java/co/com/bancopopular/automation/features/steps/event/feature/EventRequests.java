package co.com.bancopopular.automation.features.steps.event.feature;

import static net.serenitybdd.rest.SerenityRest.given;

import co.com.bancopopular.automation.rest.apis.ServicePaths;
import co.com.bancopopular.automation.utils.HeadersEnum;
import io.restassured.http.ContentType;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class EventRequests {

  public void getDataEvent(String customerDocument, String customerDocType, String event,
      String eventText, String token,String name)
      throws MalformedURLException {
    Map<String, Object> data = new HashMap<>();
    data.put("documentNumber", customerDocument);
    data.put("documentType", customerDocType);
    data.put("event", event);
    data.put("eventText", eventText);
    data.put("name", name);
    given().relaxedHTTPSValidation()
        .header(HeadersEnum.AUTHORIZATION.toString(), token)
        .contentType(ContentType.JSON)
        .body(data)
        .when()
        .post(ServicePaths.getEvent());
  }
}