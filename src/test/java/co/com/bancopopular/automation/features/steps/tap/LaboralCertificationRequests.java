package co.com.bancopopular.automation.features.steps.tap;

import co.com.bancopopular.automation.models.tap.StatusBody;
import co.com.bancopopular.automation.rest.apis.ServicePaths;
import co.com.bancopopular.automation.utils.HeadersEnum;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import org.apache.commons.httpclient.HttpStatus;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import static net.serenitybdd.rest.SerenityRest.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class LaboralCertificationRequests {

    public void getValityStatus(String token, StatusBody valityStatus) throws MalformedURLException {
        given().relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .headers(HeadersEnum.AUTHORIZATION.toString(), token)
                .body(valityStatus).when().post(ServicePaths.getValityStatus());
        assertThat(SerenityRest.then().extract().statusCode(), equalTo(HttpStatus.SC_OK));

    }

    public void getStatus(String token, StatusBody status) throws MalformedURLException {
        given().relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .headers(HeadersEnum.AUTHORIZATION.toString(), token)
                .body(status).when().post(ServicePaths.getStatus());
        assertThat(SerenityRest.then().extract().statusCode(), equalTo(HttpStatus.SC_OK));

    }

    public void getCreditTypeDecision(String customerDocument, String token, String creditType) throws MalformedURLException {

        Map<String,String> body = new HashMap<>();
        body.put("creditTypeEnum",creditType);
        body.put("documentNumber",customerDocument);
        body.put("documentType","CC");

        given().relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .headers(HeadersEnum.AUTHORIZATION.toString(), token)
                .body(body).when().post(ServicePaths.getCreditTypeDecision());

    }

    public void getSector(String token) throws MalformedURLException {
        given().relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .headers(HeadersEnum.AUTHORIZATION.toString(), token)
                .get(ServicePaths.getCashierOfficeSectors());
    }

    public void getChoosePayrollcheck(String customerDocument, String token, String sector) throws MalformedURLException {
        given().relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .headers(HeadersEnum.AUTHORIZATION.toString(), token)
                .get(ServicePaths.getChoosePayrollcheck(customerDocument,sector));
    }
}
