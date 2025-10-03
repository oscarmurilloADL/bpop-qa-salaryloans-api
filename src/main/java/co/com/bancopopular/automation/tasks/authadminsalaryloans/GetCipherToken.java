package co.com.bancopopular.automation.tasks.authadminsalaryloans;

import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.utils.RequestsUtil;

import io.restassured.http.ContentType;
import lombok.SneakyThrows;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.serenitybdd.screenplay.rest.questions.TheResponseStatusCode;
import org.apache.http.HttpStatus;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static org.hamcrest.Matchers.equalTo;

public class GetCipherToken implements Task {
    RequestsUtil requestsUtil = new RequestsUtil();

    public static GetCipherToken forAdminSalaryLoans() {
        return instrumented(GetCipherToken.class);
    }

    @SneakyThrows
    @Override
    public <T extends Actor> void performAs(T actor) {
        String userAdmin = actor.recall(Constants.USER_ADMIN_SALARYLOANS);
        String pass = actor.recall(Constants.PSS_ADMIN_SALARYLOANS);

        var authResponseAdmin = requestsUtil.createAdminLoginAuth(userAdmin, pass);

        actor.attemptsTo(
                Get.resource(Constants.END_POINT_ADMIN_CONSULT_SALE).with(
                        requestSpecification -> requestSpecification
                        .contentType(ContentType.JSON)
                        .header(Constants.AUTHORIZATION, authResponseAdmin)
                )
        );
        actor.should(
                seeThat(new TheResponseStatusCode(), equalTo(HttpStatus.SC_OK))
        );
    }
}
