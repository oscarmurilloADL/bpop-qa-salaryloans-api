package co.com.bancopopular.automation.tasks.simvalidation;

import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.helpers.HelperSimValidation;
import co.com.bancopopular.automation.utils.RequestsUtil;
import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import lombok.SneakyThrows;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.serenitybdd.screenplay.rest.questions.TheResponseStatusCode;
import org.apache.http.HttpStatus;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static org.hamcrest.Matchers.equalTo;

public class RequestCrmAndSimSucces implements Task {
    private final JsonObject data;
    private final String journeyId;

    public RequestCrmAndSimSucces(JsonObject data, String journeyId) {
       this.data = data;
        this.journeyId = journeyId;
    }

    public static RequestCrmAndSimSucces forCustomerWithSingleAccount(JsonObject data, String journeyId) {
        return instrumented(RequestCrmAndSimSucces.class, data, journeyId);
    }

    @SneakyThrows
    @Override
    public <T extends Actor> void performAs(T actor) {

        var requestsUtil = new RequestsUtil();
        var authResponseAdmin = requestsUtil.createAdminLoginAuth(data.get("adminDocument").getAsString(),
                data.get("adminPassword").getAsString());

        actor.attemptsTo(
                Post.to(Constants.END_POINT_SIM_VALIDATION_SUCCES).with(
                        requestSpecification -> requestSpecification
                        .contentType(ContentType.JSON)
                        .header(Constants.AUTHORIZATION, authResponseAdmin.getString("token"))
                        .body(HelperSimValidation.getBodySimValidationCrm(data, journeyId))
                )
        );
        actor.should(
                seeThat(new TheResponseStatusCode(), equalTo(HttpStatus.SC_OK))
        );
    }
}
