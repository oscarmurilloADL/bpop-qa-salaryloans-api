package co.com.bancopopular.automation.tasks.decrim;

import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.data.Environment;
import co.com.bancopopular.automation.models.decrim.requestresponse.RsValidateDecrim;
import co.com.bancopopular.automation.questions.decrim.TheSignatureAws;
import co.com.bancopopular.automation.utils.HeadersEnum;
import co.com.bancopopular.automation.utils.SessionHelper;
import co.com.bancopopular.automation.utils.WprCipher;
import com.amazonaws.services.connect.model.UserNotFoundException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.interactions.Put;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import net.serenitybdd.screenplay.rest.questions.TheResponseStatusCode;
import org.aeonbits.owner.ConfigFactory;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static org.hamcrest.Matchers.equalTo;

public class AskDecrimForApproval implements Task {
    private static final Environment environment = ConfigFactory.create(Environment.class);
    public static AskDecrimForApproval ofTheUploadDocuments() {
        return instrumented(AskDecrimForApproval.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        Optional<String> obJson = Optional.ofNullable(environment.toSignDecrimValidation());
        var dataToSign = obJson.orElseThrow(() -> new UserNotFoundException(Constants.DATA_NOT_FOUND));
        var gson = new Gson();
        var gsonType = new TypeToken<HashMap<String, Object>>(){}.getType();
        var bodyString = gson.toJson(getBodyRequestValidationDecrim(),gsonType);
        var encryptedBody = WprCipher.generateEncryptedBody(bodyString);
        Map<String, String> headers = actor.asksFor(TheSignatureAws.ofCredentials(encryptedBody, getFromSession(SessionHelper.SessionData.ACCESS_KEY), getFromSession(SessionHelper.SessionData.SECRET_KEY), dataToSign));
        var actorDecrim = OnStage.theActor(Constants.ACTOR_DECRIM);

        actorDecrim.attemptsTo(
                Put.to(Constants.END_POINT_POST_AUTH_DECRIM).with(
                        requestSpecification -> requestSpecification
                                .contentType(ContentType.JSON)
                                .header(Constants.SECURITY_TOKEN, getFromSession(SessionHelper.SessionData.SESSION_TOKEN))
                                .header(Constants.DATE, headers.get(Constants.X_AMZ_DATE_MAP_HEADERS))
                                .header(Constants.AUTHORIZATION, headers.get(Constants.AUTHORIZATION))
                                .header(HeadersEnum.HEADER_X_SECURITY_HMAC.toString(),WprCipher.generateSecurityHmac(getBodyRequestValidationDecrim().toString(), "PUT") )
                                .header(HeadersEnum.HEADER_X_SECURITY_SESSION.toString(), getFromSession(SessionHelper.SessionData.XSECURITYSESSION))
                                .header(HeadersEnum.HEADER_X_SECURITY_REQUEST_ID.toString(), getFromSession(SessionHelper.SessionData.XSECURITYREQUESTID))
                                .body(encryptedBody)
                )
        );
        actorDecrim.should(seeThat(new TheResponseStatusCode(), equalTo(HttpStatus.SC_OK)));
        RsValidateDecrim rsValidateDecrim = actorDecrim.asksFor(LastResponse.received()).as(RsValidateDecrim.class);
        actor.remember(Constants.BODY_RESPONSE_VALIDATE_DECRIM, rsValidateDecrim);
        OnStage.theActor(actor.getName()).entersTheScene();
    }

    private Map<String, Object> getBodyRequestValidationDecrim(){
        Map<String, Object> validateBodyDecrim = new HashMap<>();
        validateBodyDecrim.put("idCase", "1058040");
        validateBodyDecrim.put("footprintConcept", "2");
        validateBodyDecrim.put("documentConcept", "4");
        validateBodyDecrim.put("state", "4");
        validateBodyDecrim.put("messageState", "R10. El rostro capturado y el rostro de referencia NO son semejantes");
        validateBodyDecrim.put("dateResponse", "2021-09-16 09:41:53");
        validateBodyDecrim.put("proof", "https://dev.consultorid.com/gen/reportws.php?id_caso=1058040");
        return validateBodyDecrim;
    }
}
