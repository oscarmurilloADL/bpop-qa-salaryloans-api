package co.com.bancopopular.automation.features.steps.adminvalidations;

import co.com.bancopopular.automation.conf.StepBase;
import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.features.steps.renewal.RenewalStepsDefinition;
import co.com.bancopopular.automation.tasks.adminsalariloans.ConsultSale;
import co.com.bancopopular.automation.tasks.adminsalariloans.ValidateLastSale;
import co.com.bancopopular.automation.utils.dates.DateAndTimeUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import lombok.NoArgsConstructor;
import net.serenitybdd.screenplay.actors.OnStage;

import java.net.MalformedURLException;

@NoArgsConstructor
public class AdminValidationsStepsDefinitions extends StepBase {

    private static final String SAME_FEE_PROCESS_TYPE = "SAME_FEE";

    @Cuando("ella consulta las ultimas ventas")
    public void consultSale() throws MalformedURLException, JsonProcessingException {
        var renewalStepsDefinition = new RenewalStepsDefinition();
        OnStage.theActorInTheSpotlight().remember(Constants.FECHA_Y_HORA_VENTA, DateAndTimeUtils.getDateFormatyyyyMMddHHmm(""));
        renewalStepsDefinition.renewalProcess(SAME_FEE_PROCESS_TYPE, true, false);

        OnStage.theActorInTheSpotlight().attemptsTo(
                ConsultSale.toCheckTheSave()
        );
    }

    @Entonces("ella ve la información correspondiente a la última venta")
    public void validateConsultLastSale()  {

        OnStage.theActorInTheSpotlight().attemptsTo(
                ValidateLastSale.realizedByAutomation()
        );
        assertionsUtil.validateJSONSchema("schemas/consultlastsale.json");
    }
}
