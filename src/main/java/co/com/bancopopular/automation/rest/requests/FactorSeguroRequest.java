package co.com.bancopopular.automation.rest.requests;

import co.com.bancopopular.automation.rest.apis.ServicePaths;
import co.com.bancopopular.automation.utils.HeadersEnum;
import co.com.bancopopular.automation.utils.SessionHelper;
import co.com.bancopopular.automation.utils.WprCipher;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import java.net.MalformedURLException;
import java.security.SecureRandom;
import static co.com.bancopopular.automation.constants.Constants.CONDITIONS_TOKEN;
import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public class FactorSeguroRequest {

    ValidationsRequests validationsRequests = new ValidationsRequests();

    public String edad(String edad) throws MalformedURLException {
        var random = new SecureRandom();
        var bound = 100;
        await().atMost(40, SECONDS).pollInSameThread().until(WprCipher.getKeysCipher());
        var responseToken = validationsRequests.getTokenExternalViability(CONDITIONS_TOKEN);

        return SerenityRest.given()
                .and()
                .contentType(ContentType.JSON)
                .and()
                .header(
                        HeadersEnum.HEADER_X_SECURITY_HMAC.toString(), random.nextInt(bound)
                        )
                .and()
                .header(
                        HeadersEnum.HEADER_X_SECURITY_SESSION.toString(), getFromSession(SessionHelper.SessionData.XSECURITYSESSION))
                .and()
                .header(
                        HeadersEnum.HEADER_X_SECURITY_REQUEST_ID.toString(), getFromSession(SessionHelper.SessionData.XSECURITYREQUESTID))
                .and()
                .header(HeadersEnum.AUTHORIZATION.toString(), "Bearer "+ responseToken)
                .body(
                        /*Este tramo de codigo queda quemado para mas adelante ser mejorado por temas de tiempo*/
                        bodyFactorSeguro(Integer.parseInt(edad))
                )
                .when()
                .post(ServicePaths.getFactorSeguro())
                .then()
                .extract()
                .body().toString();
    }

    private String bodyFactorSeguro(int edad){
        return  "{ \n" +
                "    \"edadCliente\": "+edad+", \n" +
                "    \"messageid\": 86544568,\n" +
                "\t\"channel\": \"ADL\",\n" +
                "\t\"clientdt\": \"2021-04-29T15:21:24\",\n" +
                "    \"ipaddr\": \"127.0.0.1\"\n" +
                "}";
    }
}
