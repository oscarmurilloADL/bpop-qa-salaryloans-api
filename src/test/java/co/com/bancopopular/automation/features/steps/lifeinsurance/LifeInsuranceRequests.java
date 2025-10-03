package co.com.bancopopular.automation.features.steps.lifeinsurance;


import static net.serenitybdd.rest.SerenityRest.given;

import co.com.bancopopular.automation.rest.apis.ServicePaths;
import co.com.bancopopular.automation.utils.HeadersEnum;
import io.restassured.http.ContentType;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LifeInsuranceRequests {

  private static final String ADL="ADL";

  public void getDataLifeInsurance(String customerDocument, String customerDocType, String token)
      throws MalformedURLException {
    ArrayList<String> diseases = new ArrayList<>();
    diseases.add("Ninguna");
    Map<String, Object> data = new HashMap<>();
    data.put("diseases", diseases);
    data.put("documentNumber", customerDocument);
    data.put("documentType", customerDocType);
    data.put("medicalHistoryAuthorization",true);
    data.put("advisorLegalStatement",true);
    given().relaxedHTTPSValidation()
        .header(HeadersEnum.AUTHORIZATION.toString(), token)
        .and()
        .header("X-Adl-App-Name",ADL)
        .and()
        .header("X-Adl-Channel","LB-ASSISTED")
        .and()
        .header("X-Adl-Bank-Id","110")
        .and()
        .header("X-Adl-Transaction-Id","9112124")
        .and()
        .contentType(ContentType.JSON)
        .body(data)
        .when()
        .post(ServicePaths.getLifeInsurance());
  }
}