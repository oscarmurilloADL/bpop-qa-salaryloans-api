package co.com.bancopopular.automation.tasks;

import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.utils.DataUserInstance;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.serenitybdd.screenplay.rest.questions.TheResponseStatusCode;
import org.apache.http.HttpStatus;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static org.hamcrest.core.IsEqual.equalTo;

public class RequestPayrollLoans implements Task {
    private static final String STATUS = "/status";
    private static final String ADVISON_DOCUMENT = "advisorDocument";
    private static final String DOCUMENT_DOC_TYPE = "customerDocType";
    private static final String CUSTOMER_DOCUMENT = "customerDocument";

    public static RequestPayrollLoans withDataOfIdentification() {
            return instrumented(RequestPayrollLoans.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        var data = DataUserInstance.getInstance().getData();

        actor.attemptsTo(
                Get.resource(Constants.END_POINT_REQUEST_SALARY_LOANS.concat(data.get(ADVISON_DOCUMENT).getAsString()).concat("/").concat(data.get(DOCUMENT_DOC_TYPE).getAsString()).concat("/").concat(data.get(CUSTOMER_DOCUMENT).getAsString()).concat(STATUS))
        );
        actor.should(
                seeThat(new TheResponseStatusCode(), equalTo(HttpStatus.SC_OK))
        );
    }
}
