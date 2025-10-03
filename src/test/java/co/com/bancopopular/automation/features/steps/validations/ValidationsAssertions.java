package co.com.bancopopular.automation.features.steps.validations;

import static co.com.bancopopular.automation.constants.Constants.CUSTOMER_DOCUMENT;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import co.com.bancopopular.automation.utils.DataUserInstance;
import co.com.bancopopular.automation.utils.logs.LogPrinter;
import net.serenitybdd.rest.SerenityRest;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

public class ValidationsAssertions {

    private enum SubcausalType {
        INTERNAL("interna"),
        CLIENT_VIABILITY("clientViability"),
        DECISION("decision"),
        MESSAGE_VIABLE("messageViable"),
        DOCUMENTS("documents"),
        NULL("null"),
        PEACE_AND_SAFE("peaceAndSafe"),
        TRUE("true"),
        FALSE("false"),
        AUTOMATIC("automatic"),
        MANUAL("manual"),
        OK("Ok"),
        INTERNAL_NOT_VIABLE("InternalNotViable"),
        EXTERNAL_NOT_VIABLE("ExternalNotViable"),
        NOT_PEACE_AND_SAFE("Not Peace And Safe"),
        QUALIFICATION_PREVIOUS("subcausal Calificación Trimestre Anterior"),
        SUCCESSFUL_VIABILITY("viabilidad exitosa"),
        UNSUCCESSFUL_VIABILITY("viabilidad no exitosa"),
        HISTORICA_30("Histórica 30"),
        HISTORICA_60("Histórica 60"),
        HISTORICA_90("Histórica 90"),
        HISTORICA_120("Histórica 120"),
        REPEALING("Derogatorios"),
        FOOTPRINTS("Huellas"),
        CURRENT("Mora Actual"),
        CANCELLED("cancelada"),
        CAUSAL_LIST("lista de subcausales"),

        CAUSAL_LIST_PEACE_AND_SAFE("cliente con varias subcausales y paz y salvo"),

        CAUSAL_LIST_WITHOUT_PEACE_AND_SAFE("cliente con varias subcausales y sin paz y salvo"),
        NO_APLICA("NO APLICA"),
        LEVANTAMIENTO_EMBARGO("OFICIO DE LEVANTAMIENTO DE EMBARGO"),
        PAZ_Y_SALVO("PAZ Y SALVO"),
        SOPORTE_INGRESOS("SOPORTE INGRESOS ADICIONALES");

        private final String value;

        SubcausalType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private static final Set<String> HISTORICA_SUBCAUSALS = new HashSet<>(Arrays.asList(
            SubcausalType.HISTORICA_30.getValue(),
            SubcausalType.HISTORICA_60.getValue()
    ));

    private static final Set<String> SPECIAL_CASES = new HashSet<>(Arrays.asList(
            SubcausalType.HISTORICA_90.getValue(),
            SubcausalType.HISTORICA_120.getValue(),
            SubcausalType.REPEALING.getValue(),
            SubcausalType.FOOTPRINTS.getValue(),
            SubcausalType.CANCELLED.getValue(),
            SubcausalType.CAUSAL_LIST_WITHOUT_PEACE_AND_SAFE.getValue()
    ));

    private static final Set<String> PEACE_SAFE_SUBCAUSALS = new HashSet<>(Arrays.asList(
            SubcausalType.CURRENT.getValue(),
            SubcausalType.CAUSAL_LIST_PEACE_AND_SAFE.getValue()
    ));

    public void verifyViability(String clientViability, String decision, String msg, String peaceSafe, String documents) {
        var jsonPath = SerenityRest.then().extract().body().jsonPath();
        assertThat(jsonPath.getString(SubcausalType.CLIENT_VIABILITY.getValue()), is(clientViability));
        assertThat(jsonPath.getString(SubcausalType.DECISION.getValue()), is(decision));
        assertThat(jsonPath.getString(SubcausalType.MESSAGE_VIABLE.getValue()), is(msg));
        if (!documents.equals(SubcausalType.NULL.getValue())){
            assertThat(jsonPath.getString(SubcausalType.DOCUMENTS.getValue()), is("["+documents+"]"));
        }else{
            assertThat(jsonPath.getString(SubcausalType.DOCUMENTS.getValue()),nullValue());
        }
        assertThat(jsonPath.getString(SubcausalType.PEACE_AND_SAFE.getValue()), is(peaceSafe));
    }

    public void verifySubcausal(String subcausal, String viability) {
        if (SubcausalType.SUCCESSFUL_VIABILITY.getValue().equals(subcausal)) {
            verifyViability(SubcausalType.TRUE.getValue(), SubcausalType.AUTOMATIC.getValue(), SubcausalType.OK.getValue(), SubcausalType.NOT_PEACE_AND_SAFE.getValue(), SubcausalType.NULL.getValue());
        } else if (SubcausalType.UNSUCCESSFUL_VIABILITY.getValue().equals(subcausal)) {
            verifyUnsuccessfulViability(viability);
        } else if (containsAny(subcausal, HISTORICA_SUBCAUSALS)) {
            verifyViability(SubcausalType.TRUE.getValue(), SubcausalType.AUTOMATIC.getValue(), SubcausalType.OK.getValue(), SubcausalType.NOT_PEACE_AND_SAFE.getValue(), SubcausalType.NULL.getValue());
        } else if (containsAny(subcausal, SPECIAL_CASES)) {
            verifyViability(SubcausalType.TRUE.getValue(), SubcausalType.MANUAL.getValue(), viability, SubcausalType.NOT_PEACE_AND_SAFE.getValue(),SubcausalType.NULL.getValue());
        } else if (containsAny(subcausal, PEACE_SAFE_SUBCAUSALS)) {
            verifyViability(SubcausalType.TRUE.getValue(), SubcausalType.MANUAL.getValue(), viability, SubcausalType.NO_APLICA.getValue(),SubcausalType.PAZ_Y_SALVO.getValue());
        } else if (subcausal.contains(SubcausalType.QUALIFICATION_PREVIOUS.getValue())) {
            verifyViability(SubcausalType.TRUE.getValue(), SubcausalType.AUTOMATIC.getValue(), SubcausalType.OK.getValue(), SubcausalType.NOT_PEACE_AND_SAFE.getValue(),SubcausalType.NULL.getValue());
        } else if (subcausal.contains(SubcausalType.CAUSAL_LIST.getValue())) {
            verifyViability(SubcausalType.TRUE.getValue(), SubcausalType.MANUAL.getValue(), viability, SubcausalType.NO_APLICA.getValue(),SubcausalType.PAZ_Y_SALVO.getValue());
        } else if(subcausal.contains("Cuenta embargada")) {
            verifyViability(SubcausalType.TRUE.getValue(), SubcausalType.MANUAL.getValue(), viability, SubcausalType.NO_APLICA.getValue(),SubcausalType.LEVANTAMIENTO_EMBARGO.getValue());
        }else {
            LogPrinter.printLog("Not found");
        }
    }

    private void verifyUnsuccessfulViability(String viability) {
        if (SubcausalType.INTERNAL.getValue().equals(viability)) {
            verifyViability(SubcausalType.FALSE.getValue(), SubcausalType.MANUAL.getValue(), SubcausalType.INTERNAL_NOT_VIABLE.getValue(), SubcausalType.NOT_PEACE_AND_SAFE.getValue(),SubcausalType.NULL.getValue());
        } else {
            verifyViability(SubcausalType.FALSE.getValue(), SubcausalType.MANUAL.getValue(), SubcausalType.EXTERNAL_NOT_VIABLE.getValue(), SubcausalType.NOT_PEACE_AND_SAFE.getValue(),SubcausalType.NULL.getValue());
        }
    }

    private boolean containsAny(String subcausal, Set<String> values) {
        return values.stream().anyMatch(subcausal::contains);
    }
}