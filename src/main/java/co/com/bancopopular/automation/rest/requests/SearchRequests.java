package co.com.bancopopular.automation.rest.requests;

import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static net.serenitybdd.rest.SerenityRest.given;
import co.com.bancopopular.automation.rest.apis.ServicePaths;
import co.com.bancopopular.automation.utils.HeadersEnum;
import co.com.bancopopular.automation.utils.RequestsUtil;
import co.com.bancopopular.automation.utils.SessionHelper;
import co.com.bancopopular.automation.utils.WprCipher;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import org.apache.commons.httpclient.HttpStatus;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchRequests {

    private static final Logger logger = Logger.getLogger(SearchRequests.class.getName());

    public void searchCustomer(JsonObject data, String advisorJourneyId)
            throws MalformedURLException, NullPointerException {
        Map<String, String> bodyAdvisorByOffice = new HashMap<>();
        bodyAdvisorByOffice.put("administrativeOfficeCode", data.get("administrativeOffice").getAsString());
        bodyAdvisorByOffice.put("businessAdvisorDocument", data.get("advisorDocument").getAsString());
        bodyAdvisorByOffice.put("documentNumber", data.get("customerDocument").getAsString());
        bodyAdvisorByOffice.put("documentType", data.get("customerDocType").getAsString());
        bodyAdvisorByOffice.put("officeCode", data.get("officeCode").getAsString());
        bodyAdvisorByOffice.put("officeName", data.get("officeName").getAsString());
        bodyAdvisorByOffice.put("advisorJourneyId", advisorJourneyId);
        bodyAdvisorByOffice.put("salesModel", data.get("salesModel").getAsString());
        bodyAdvisorByOffice.put("administrativeOfficeName", data.get("administrativeOfficeName").getAsString());

        var gson = new Gson();
        var gsonType = new TypeToken<HashMap<String, Object>>() {}.getType();
        var bodyString = gson.toJson(bodyAdvisorByOffice, gsonType);
        var encryptedBody = WprCipher.generateEncryptedBody(bodyString);
        var maxRetries = 10;
        var attempt = 0;
        var statusCode=0;
        var response = "";
        do {
            var restResponse = SerenityRest.given()
                    .and()
                    .contentType(ContentType.JSON)
                    .and()
                    .header(
                            HeadersEnum.HEADER_X_SECURITY_HMAC.toString(),
                            WprCipher.generateSecurityHmac(bodyAdvisorByOffice.toString(), "POST"))
                    .and()
                    .header(
                            HeadersEnum.HEADER_X_SECURITY_SESSION.toString(),
                            getFromSession(SessionHelper.SessionData.XSECURITYSESSION))
                    .and()
                    .header(
                            HeadersEnum.HEADER_X_SECURITY_REQUEST_ID.toString(),
                            getFromSession(SessionHelper.SessionData.XSECURITYREQUESTID))
                    .and()
                    .body(encryptedBody)
                    .when()
                    .post(ServicePaths.getSearchCustomer());

            statusCode = restResponse.statusCode();
            response = restResponse.body().asString();

            if (statusCode == HttpStatus.SC_GATEWAY_TIMEOUT) {
                logger.log(Level.WARNING, "Received 504 Gateway Timeout. Retrying... Attempt {0}", (attempt + 1));
            } else {
                break;
            }
            attempt++;
        } while (attempt < maxRetries);

        logger.log(Level.INFO, "Cliente con registros en base Reprecio " + WprCipher.decryptRequest(response));
        RequestsUtil.addResponseInSerenityReport("Response repricing/search:");
    }

    public void getAccountsOwnership(String customerDoctype, String customerDocument) throws MalformedURLException {
        Map<String, String> bodyAAccountsOwnership = new HashMap<>();
        bodyAAccountsOwnership.put("identificationType", customerDoctype);
        bodyAAccountsOwnership.put("identificationNumber", customerDocument);
        bodyAAccountsOwnership.put("reverse", "false");

        var gson = new Gson();
        var gsonType = new TypeToken<HashMap<String, Object>>() {
        }.getType();
        var bodyString = gson.toJson(bodyAAccountsOwnership, gsonType);
        var encryptedBody = WprCipher.generateEncryptedBody(bodyString);

        given().relaxedHTTPSValidation()
                .contentType("application/json")
                .header(
                        HeadersEnum.HEADER_X_SECURITY_HMAC.toString(),
                        WprCipher.generateSecurityHmac(bodyAAccountsOwnership.toString(), "POST"))
                .and()
                .header(
                        HeadersEnum.HEADER_X_SECURITY_SESSION.toString(), getFromSession(SessionHelper.SessionData.XSECURITYSESSION))
                .and()
                .header(
                        HeadersEnum.HEADER_X_SECURITY_REQUEST_ID.toString(), getFromSession(SessionHelper.SessionData.XSECURITYREQUESTID))
                .and()
                .body(encryptedBody)
                .when().post(ServicePaths.getAccountsList());

        RequestsUtil.addResponseInSerenityReport("Response accountList:");
    }
}

