package co.com.bancopopular.automation.features.steps.crm;

import static co.com.bancopopular.automation.constants.Constants.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.data.Environment;
import co.com.bancopopular.automation.exceptions.UserNotFoundException;
import co.com.bancopopular.automation.utils.AwsUtils;
import co.com.bancopopular.automation.utils.WprCipher;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.annotations.Step;
import org.aeonbits.owner.ConfigFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CrmAssertions {

  private static final String KEY = "key";
  private static final String VALUE = "value";
  AwsUtils awsUtils = new AwsUtils();

  @Step("Verificacion datos cliente actualizado")
  public void verifyCustomerIsUpdated() {
    assertThat(SerenityRest.then().extract().body().jsonPath().getString("responseCode"),
            is("OK"));
    assertThat(SerenityRest.then().extract().body().jsonPath().getString("responseDescription"),
            is("Customer has been updated"));
  }

  @Step("Opciones de estado civil")
  public void verifyMaritalStatus() throws UserNotFoundException {

    var environment = ConfigFactory.create(Environment.class);
    var data = environment.maritalStatusValues();

    Optional<String> dataOptional = Optional.ofNullable(data);
    var dataVerified = dataOptional
        .orElseThrow(() -> new UserNotFoundException("Type of user does not exist in the system"));

    var maritalData = JsonParser.parseString(dataVerified).getAsJsonObject();

    for (var keyValue= 0; keyValue < SerenityRest.then().extract().body().jsonPath().getList(KEY).size(); keyValue++) {
      if(SerenityRest.then().extract().body().jsonPath().getList(KEY).get(keyValue).toString().equals("ND")){
        assertThat(
            SerenityRest.then().extract().body().jsonPath().getList(VALUE).get(keyValue).toString(),
            is(maritalData.get("ND").getAsString()));
      }else{
        assertThat(
            SerenityRest.then().extract().body().jsonPath().getList(VALUE).get(keyValue),
            is(maritalData.get(String.valueOf(keyValue-1)).getAsString()));
      }
    }

    }

  @Step("Opciones de ciudad")
  public void verifyCity() {
    assertThat(SerenityRest.then().extract().body().jsonPath().getList(KEY).get(0),
        is("13-13006"));
    assertThat(
        SerenityRest.then().extract().body().jsonPath().getList(VALUE).get(0),
        is("BOGOTA"));
  }

  @Step("Verificacion datos cliente actualizado")
  public void verifyCrmFail() {
    assertThat(WprCipher.decryptRequest(SerenityRest.then().extract().body().asString()).getString("code"),
            is("CustomerManagement006"));
    assertThat(WprCipher.decryptRequest(SerenityRest.then().extract().body().asString()).getString("description"),
            is("The Buro Preselecta Service not Response"));
  }

  public void verifyCrmFailByPreselectaToBadBehavior() {
    assertThat(WprCipher.decryptRequest(SerenityRest.then().extract().body().asString()).getString("code"),
            is("CustomerManagement008"));
    assertThat(WprCipher.decryptRequest(SerenityRest.then().extract().body().asString()).getString("description"),
            is("Negado por preselecta"));
  }

    public void verifyRequest(String method, JsonObject data) {
      Map<String, Object> dataQuery = new HashMap<>();
      dataQuery.put(CUSTOMER_DOCUMENT,data.get(CUSTOMER_DOCUMENT).getAsString());
      dataQuery.put(REQUEST, method.equals(SEARCH_MDM) ? MDM_DWL_CONTROL : GET_CUSTOMER_DATA_UPDATE);
      var mdmRequest = awsUtils.showEventsInAws(
              Constants.SALARYLOANS_LOG,
              awsUtils.dataCommonQuery(dataQuery)
      );
      System.out.println("LOG MDM: "+mdmRequest);
    }
}