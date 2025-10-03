package co.com.bancopopular.automation.features.steps.cnc;

import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.models.tap.StatusBody;
import co.com.bancopopular.automation.rest.apis.ServicePaths;
import co.com.bancopopular.automation.utils.HeadersEnum;
import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import static net.serenitybdd.rest.SerenityRest.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class CNCRequest {

    public void getAge(String token, StatusBody ageBody) throws MalformedURLException {

        given().relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .headers(HeadersEnum.AUTHORIZATION.toString(), token)
                .body(ageBody).when().post(ServicePaths.getAge());
    }


    public void inputPayrollDate(JsonObject data,String token, String date) throws MalformedURLException {

        Map<String, Object> seniorityBody = new HashMap<>();
        seniorityBody.put(Constants.DOCUMENT_NUMBER,data.get(Constants.CUSTOMER_DOCUMENT).getAsString());
        seniorityBody.put(Constants.DOCUMENT_TYPE,data.get(Constants.CUSTOMER_DOC_TYPE).getAsString());
        seniorityBody.put("paymentOfficeDate",date);
        seniorityBody.put(Constants.SECTOR,data.get(Constants.SECTOR).getAsString());
        seniorityBody.put("valuePayer",data.get(Constants.VALUE_PAYER).getAsString());

        given().relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .headers(HeadersEnum.AUTHORIZATION.toString(), token)
                .body(seniorityBody).when().post(ServicePaths.getSeniorityCheck());

        assertThat(SerenityRest.then().extract().body().jsonPath().getString("status"),
                is("validation OK"));
    }
}
