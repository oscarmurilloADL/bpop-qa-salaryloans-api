package co.com.bancopopular.automation.tasks.adminsalariloans;

import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.matchers.DateIsAfter;
import co.com.bancopopular.automation.models.adminsales.DataSales;
import co.com.bancopopular.automation.models.adminsales.ResponseRequestLastSales;
import co.com.bancopopular.automation.utils.dates.DateAndTimeUtils;
import lombok.SneakyThrows;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.questions.TheValue;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

import java.util.Date;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static org.hamcrest.Matchers.equalTo;

public class ValidateLastSale implements Task {
    private static final String CAPTUR_TYPE = "En Proceso";
    private static final String CREDIT_TYPE = "Ampliación";
    private static final String LOAN_ID = "26003240000601";

    public static ValidateLastSale realizedByAutomation() {
        return instrumented(ValidateLastSale.class);
    }

    @SneakyThrows
    @Override
    public <T extends Actor> void performAs(T actor) {
        ResponseRequestLastSales responseRequestLastSales = actor.asksFor(LastResponse.received()).as(ResponseRequestLastSales.class);
        Date executedSaleDate = actor.recall(Constants.FECHA_Y_HORA_VENTA);

        int rqid = actor.recall(Constants.RQ_ID_GENERATED);

        DataSales dataSales= responseRequestLastSales.getData().stream().filter(data -> data.getRequestId()==rqid ).findFirst()
                .orElseThrow(() -> new AssertionError(String.format("RQID %s no encontrado", rqid)));

        actor.should(
                seeThat(TheValue.of("El tipo de Captura-captureType ", dataSales.getCaptureType()), equalTo(CAPTUR_TYPE)),
                seeThat(TheValue.of("El tipo de crédito-creditType ", dataSales.getCreditType()), equalTo(CREDIT_TYPE)),
                seeThat(TheValue.of("El código del prestamo-loanCode ", dataSales.getLoanCode()), equalTo(LOAN_ID)),
                seeThat(TheValue.of("La fecha de creación", DateAndTimeUtils.getDateFormatyyyyMMddHHmm(dataSales.getFechaHoraGeneracion())), DateIsAfter.than(executedSaleDate))
        );
    }
}