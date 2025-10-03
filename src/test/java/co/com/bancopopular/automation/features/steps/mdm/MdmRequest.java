package co.com.bancopopular.automation.features.steps.mdm;

import co.com.bancopopular.automation.models.mdm.MDM;
import co.com.bancopopular.automation.rest.apis.ServicePaths;
import co.com.bancopopular.automation.utils.HeadersEnum;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import org.apache.commons.httpclient.HttpStatus;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import static net.serenitybdd.rest.SerenityRest.given;

public class MdmRequest {

    public void updateCustomerMDM(MDM body, Map<String,String> headers)
            throws MalformedURLException {

        for(var x = 0 ; x < 10 ; x++) {
            given().relaxedHTTPSValidation()
                    .contentType(ContentType.JSON)
                    .headers(headers)
                    .body(body).when().put(ServicePaths.getUpdateClient());
            if(SerenityRest.then().extract().statusCode() == HttpStatus.SC_OK){
                break;
            }
        }

    }

    public Map<String,String> headersMDM(String token){
        Map<String,String> requestHeaders = new HashMap<>();
        requestHeaders.put(HeadersEnum.AUTHORIZATION.toString(),token);
        return requestHeaders;
    }
}
