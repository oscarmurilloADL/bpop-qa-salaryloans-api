package co.com.bancopopular.automation.features.steps.accounts;

import static net.serenitybdd.rest.SerenityRest.given;

import co.com.bancopopular.automation.rest.apis.ServicePaths;
import co.com.bancopopular.automation.utils.RequestsUtil;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.rest.SerenityRest;
import org.apache.http.HttpStatus;

import java.net.MalformedURLException;

public class AccountsRequests {

    RequestsUtil requestsUtil = new RequestsUtil();

    public void getAccounts(String customerDoctype, String customerDocument)
            throws MalformedURLException {
        JsonPath authResponse = requestsUtil.getAuthOTP();
        for (var x = 0; x < 5; x++) {
            given().relaxedHTTPSValidation().header("Authorization", authResponse.getString("token"))
                    .contentType("application/json")
                    .when().get(ServicePaths.getAccounts(customerDoctype, customerDocument));
            if (SerenityRest.then().extract().statusCode() == HttpStatus.SC_OK) {
                break;
            }
        }
    }
}