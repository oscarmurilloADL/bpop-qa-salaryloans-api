package co.com.bancopopular.automation.features.steps.termsconditions;

import co.com.bancopopular.automation.models.termsconditions.TermsAndConditions;
import co.com.bancopopular.automation.rest.apis.ServicePaths;
import co.com.bancopopular.automation.utils.HeadersEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import java.net.MalformedURLException;


import static net.serenitybdd.rest.SerenityRest.given;


public class TermsAndConditionsRequest {


    public void getTermsAndConditions(JsonObject data, String token) throws MalformedURLException, JsonProcessingException {

        var objectMapper = new ObjectMapper();
        var termsBody = objectMapper.readValue(data.get("bodyRequestTerms").toString(), new TypeReference<TermsAndConditions>() {
        });

        given().relaxedHTTPSValidation().header(HeadersEnum.AUTHORIZATION.toString(), token)
                .contentType(ContentType.JSON)
                .body(termsBody)
                .when().post(ServicePaths.getTermsAndConditions(data.get("obligationID").getAsString()));
    }
}
