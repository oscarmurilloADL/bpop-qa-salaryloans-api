package co.com.bancopopular.automation.tasks.decrim;

import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.data.Environment;
import co.com.bancopopular.automation.models.decrim.requestresponse.RsCreateCase;
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
import net.serenitybdd.screenplay.rest.interactions.Post;
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

public class CreateCase implements Task {

    private static final Environment environment = ConfigFactory.create(Environment.class);
    public static CreateCase toUploadDocumentsDecrim() {
        return instrumented(CreateCase.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        Optional<String> obJson = Optional.ofNullable(environment.toSignDecrimCreateCase());
        var data = obJson.orElseThrow(() -> new UserNotFoundException(Constants.DATA_NOT_FOUND));
        var gson = new Gson();
        var gsonType = new TypeToken<HashMap<String, Object>>(){}.getType();
        var bodyString = gson.toJson(getBodyAuthDecrim(),gsonType);
        var encryptedBody = WprCipher.generateEncryptedBody(bodyString);
        Map<String, String> headers = actor.asksFor(TheSignatureAws.ofCredentials(encryptedBody, getFromSession(SessionHelper.SessionData.ACCESS_KEY), getFromSession(SessionHelper.SessionData.SECRET_KEY), data));
        var actorDecrim = OnStage.theActor(Constants.ACTOR_DECRIM);
        actorDecrim.attemptsTo(
                Post.to(Constants.END_POINT_POST_AUTH_DECRIM).with(
                        requestSpecification -> requestSpecification
                                .contentType(ContentType.JSON)
                                .header(Constants.SECURITY_TOKEN, getFromSession(SessionHelper.SessionData.SESSION_TOKEN))
                                .header(Constants.DATE, headers.get(Constants.X_AMZ_DATE_MAP_HEADERS))
                                .header(Constants.AUTHORIZATION, headers.get(Constants.AUTHORIZATION))
                                .header(HeadersEnum.HEADER_X_SECURITY_HMAC.toString(),WprCipher.generateSecurityHmac(getBodyAuthDecrim().toString(), "POST") )
                                .header(HeadersEnum.HEADER_X_SECURITY_SESSION.toString(), getFromSession(SessionHelper.SessionData.XSECURITYSESSION))
                                .header(HeadersEnum.HEADER_X_SECURITY_REQUEST_ID.toString(), getFromSession(SessionHelper.SessionData.XSECURITYREQUESTID))
                                .body(encryptedBody)
                )
        );
        actorDecrim.should(seeThat(new TheResponseStatusCode(), equalTo(HttpStatus.SC_OK)));
        RsCreateCase rsCreateCase = actorDecrim.asksFor(LastResponse.received()).as(RsCreateCase.class);
        actor.remember(Constants.BODY_RESPONSE_CREATE_CASE, rsCreateCase);
        OnStage.theActor(actor.getName()).entersTheScene();
    }

    private Map<String, Object> getBodyAuthDecrim(){
        Map<String, Object> validateBodyDecrim = new HashMap<>();
        validateBodyDecrim.put("fullName", "LAURA MILENA");
        validateBodyDecrim.put("documentNumber", "400059");
        validateBodyDecrim.put("documentType", "1");
        validateBodyDecrim.put("advisorId", "51919050");
        validateBodyDecrim.put("fileName1", "CC_500068_1.pdf");
        validateBodyDecrim.put("fileType1", "pdf");
        validateBodyDecrim.put("fileName2", "CC_500068_2.pdf");
        validateBodyDecrim.put("fileType2", "pdf");
        validateBodyDecrim.put("fileName3", "CC_500068_3.pdf");
        validateBodyDecrim.put("fileType3", "pdf");
        return validateBodyDecrim;
    }
}
