package co.com.bancopopular.automation.actors;

import co.com.bancopopular.automation.abilities.ConnectToParameters;
import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.setenvironments.SetEnvironmentVariables;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.Cast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.model.util.EnvironmentVariables;


import java.util.Arrays;

public class ApiCast extends Cast {

    private final SetEnvironmentVariables setEnvironmentVariables;
    private final Boolean connectToParametersStg;

    private static final String DESCRIPTION_ACTOR_DECRIM = "Actor temporal para consultar Decrim ";
    private static final String DESCRIPTION_ACTOR_SALARY_LOANS = "Actor en SalaryLoans ";

    public ApiCast(EnvironmentVariables environmentVariables, Boolean connectToParametersStg) {
        this.setEnvironmentVariables = new SetEnvironmentVariables(environmentVariables);
        this.connectToParametersStg = connectToParametersStg;
    }

    @Override
    public Actor actorNamed(String actorName, Ability... abilities) {
        Actor theActor;
        SerenityRest.filters(Arrays.asList(new RequestLoggingFilter(), new ResponseLoggingFilter()));

        if (actorName.equals(Constants.ACTOR_DECRIM)) {
            theActor = super.actorNamed(actorName, CallAnApi.at(setEnvironmentVariables.getBaseUrlDecrim()))
                    .describedAs(DESCRIPTION_ACTOR_DECRIM);
        } else {
            theActor = super.actorNamed(
                    actorName,
                    CallAnApi.at(setEnvironmentVariables.getBaseUrl())).describedAs(DESCRIPTION_ACTOR_SALARY_LOANS);
        }

        theActor.remember(Constants.USER_ADMIN_SALARYLOANS, setEnvironmentVariables.getUserAdminSalaryLoans());
        theActor.remember(Constants.PSS_ADMIN_SALARYLOANS, setEnvironmentVariables.getPassAdminSalaryLoans());

        if (Boolean.TRUE.equals(connectToParametersStg)) {
            theActor.can(
                    ConnectToParameters.at(
                            setEnvironmentVariables.getHostConnectToParameters(),
                            setEnvironmentVariables.getPortConnectToParameters(),
                            setEnvironmentVariables.getPassConnectToParameters(),
                            setEnvironmentVariables.getDataBaseParameters(),
                            setEnvironmentVariables.getUserConnectToParameters()
                    )
            );
        }
        return theActor;
    }
}
