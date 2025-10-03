package co.com.bancopopular.automation.rest.requests;

import static co.com.bancopopular.automation.constants.Constants.*;
import static net.serenitybdd.rest.SerenityRest.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import co.com.bancopopular.automation.rest.apis.ServicePaths;
import co.com.bancopopular.automation.utils.HeadersEnum;
import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import org.apache.http.HttpStatus;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class ValidationsRequests {

  private static final String TYPE = "type";
  private static final String IDENTIFICATION = "identification";
  private static final String JSON = "application/json";
  private static final String TYPE_DOCUMENT="typeDocument";
  private static final String DOCUMEMT_NUMBER="documentNumber";
  private static final String PRODUCT_TYPE="productType";
  private static final String RISK_AREA="riskArea";
  private static final String LAST_NAME="lastName";
  private static final String CUSTOMER_STATUS="customerStatus";
  private static final String DOCUMENT_TYPE="documentType";
  private static final String DOCUMENT_NUMBER="documentNumber";
  private static final String ACCESS_TOKEN="access_token";
  private static final String CLIENT_SECRET="client_secret";
  private static final String CLIENT_ID="client_id";
  private static final String GRANT_TYPE="grant_type";
  private static final String CLIENT_CREDENTIALS = "client_credentials";
  private static final String APPLICATION_URLENCODED="application/x-www-form-urlencoded";
  private static final String CODE ="code";
  private static final String RISK_MANAGEMENT005="RiskManagement005";
  private static final String TECHNICAL_FAIL="{\"statusCode\":100,\"severity\":\"INFO\",\"statusDesc\":\"Falla Tecnica\"}";
  private static final String MESSAGE="message";
  private static final String CLIENT_ID_VALUE=System.getProperty("ClientId");
  private static final String CLIENT_SECRET_VALUE=System.getProperty("ClientSecret");
  private static final String CONDITIONS_ID_VALUE=System.getProperty("BankingClient");
  private static final String CONDITIONS_SECRET_VALUE=System.getProperty("BankingSecret");

  public void sendValidateInternalViability(String customerDocType, String customerDocument)
      throws MalformedURLException {
    Map<String, Object> internalViabilityBody = new HashMap<>();
    internalViabilityBody.put(TYPE, customerDocType);
    internalViabilityBody.put(IDENTIFICATION, customerDocument);
    for(var x = 0 ; x < 10 ; x++) {
      given().relaxedHTTPSValidation().contentType(JSON).body(internalViabilityBody)
              .when()
              .post(ServicePaths.getValidationInternalViability());
      if (SerenityRest.then().extract().statusCode() == HttpStatus.SC_OK) {
        break;
      }
    }
  }

  public void sendValidateBusisnessRules(String customerDocType, String customerDocument,String sector,String token)
          throws MalformedURLException {
    Map<String, Object> viabilityBody = new HashMap<>();
    viabilityBody.put(TYPE_DOCUMENT, customerDocType);
    viabilityBody.put(DOCUMEMT_NUMBER, customerDocument);
    viabilityBody.put("sector", sector);
    given().relaxedHTTPSValidation()
            .header(HeadersEnum.AUTHORIZATION.toString(), token)
            .contentType(JSON).body(viabilityBody)
            .when()
            .post(ServicePaths.getValidationPeaceAndSafe());
  }

    public void getExternalViability(String productType, String riskArea, String lastName, String customerStatus,
                                     String documentType,String documentNumber) throws MalformedURLException {

      Map<String, Object> externalViabilityBody = new HashMap<>();
      var token =getTokenExternalViability(CONDITIONS_TOKEN);

      externalViabilityBody.put(PRODUCT_TYPE,productType);
      externalViabilityBody.put(RISK_AREA,riskArea);
      externalViabilityBody.put(LAST_NAME,lastName);
      externalViabilityBody.put(CUSTOMER_STATUS,customerStatus);
      externalViabilityBody.put(DOCUMENT_TYPE,documentType);
      externalViabilityBody.put(DOCUMENT_NUMBER,documentNumber);

      given().relaxedHTTPSValidation()
              .header(HeadersEnum.AUTHORIZATION.toString(), token)
              .contentType(JSON).body(externalViabilityBody)
              .when()
              .post(ServicePaths.getExternalViability());

     assertThat(SerenityRest.then().extract().body().jsonPath().getString(CODE),
              is(RISK_MANAGEMENT005));
      assertThat(SerenityRest.then().extract().body().jsonPath().getString(MESSAGE),
              is(TECHNICAL_FAIL));
    }

    public String getTokenExternalViability(String typeToken) throws MalformedURLException {
      Map<String,String> body = new HashMap<>();
      var urlToken=ServicePaths.getOauth2Token();
        body.put(GRANT_TYPE,CLIENT_CREDENTIALS);
        body.put(CLIENT_ID,CLIENT_ID_VALUE);
        body.put(CLIENT_SECRET,CLIENT_SECRET_VALUE);
        if(typeToken.equals(CONDITIONS_TOKEN)){
          body.put(GRANT_TYPE,CLIENT_CREDENTIALS);
          body.put(CLIENT_ID,CONDITIONS_ID_VALUE);
          body.put(CLIENT_SECRET,CONDITIONS_SECRET_VALUE);
          urlToken=ServicePaths.getTokenTermCondition();
        }
        given().relaxedHTTPSValidation()
                .contentType(APPLICATION_URLENCODED)
                .formParam(GRANT_TYPE,body.get(GRANT_TYPE))
                .formParam(CLIENT_ID,body.get(CLIENT_ID))
                .formParam(CLIENT_SECRET,body.get(CLIENT_SECRET))
                .when()
                .post(urlToken);

        return SerenityRest.then().extract().body().jsonPath().getString(ACCESS_TOKEN);
    }

  public void getExternalViabilityStatus(JsonObject data, String token) throws MalformedURLException {

    Map<String, Object> body = new HashMap<>();
    body.put("type",data.get(CUSTOMER_DOC_TYPE).getAsString());
    body.put(IDENTIFICATION,data.get(CUSTOMER_DOCUMENT).getAsString());

    given().relaxedHTTPSValidation()
            .contentType(ContentType.JSON)
            .header(HeadersEnum.AUTHORIZATION.toString(), token)
            .body(body)
            .when()
            .post(ServicePaths.getExternalViabilityStatus());
  }
}