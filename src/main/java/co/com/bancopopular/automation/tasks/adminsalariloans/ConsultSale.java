package co.com.bancopopular.automation.tasks.adminsalariloans;

import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.questions.admin.ReadNumberOfPages;
import co.com.bancopopular.automation.utils.DataUserInstance;
import co.com.bancopopular.automation.utils.RequestsUtil;
import io.restassured.http.ContentType;
import lombok.SneakyThrows;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.serenitybdd.screenplay.rest.questions.TheResponseStatusCode;
import org.apache.http.HttpStatus;

import java.util.concurrent.TimeUnit;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static org.hamcrest.Matchers.equalTo;

public class ConsultSale implements Task {

    public static ConsultSale toCheckTheSave() {
        return instrumented(ConsultSale.class);
    }

    @SneakyThrows
    @Override
    public <T extends Actor> void performAs(T actor) {

        var data = DataUserInstance.getInstance().getData();

        var requestsUtil = new RequestsUtil();
        var authResponseAdmin = requestsUtil.createAdminLoginAuth(data.get("adminDocument").getAsString(),
                data.get("adminPassword").getAsString());
        TimeUnit.SECONDS.sleep(15);
        actor.attemptsTo(
                Get.resource(String.format(Constants.END_POINT_ADMIN_CONSULT_SALE, actor.asksFor(ReadNumberOfPages.forGetLastPage()))).with(
                        requestSpecification -> requestSpecification
                                .contentType(ContentType.JSON)
                                .header(Constants.AUTHORIZATION, authResponseAdmin.getString("token"))
                )
        );

        actor.should(
                seeThat(new TheResponseStatusCode(), equalTo(HttpStatus.SC_OK))
        );
    }
}