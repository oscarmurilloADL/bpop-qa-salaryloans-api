package co.com.bancopopular.automation.setenvironments;

import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.util.EnvironmentVariables;

public class SetEnvironmentVariables {
    private  final EnvironmentVariables environmentVariables;

    public SetEnvironmentVariables(EnvironmentVariables environmentVariables){
        this.environmentVariables = environmentVariables;
    }

    public String getBaseUrl(){
        return EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("rest.api.base.url");
    }

    public String getBaseUrlDecrim(){
        return EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("api.base.url.decrim");
    }

    public String getUserAdminSalaryLoans(){
        return EnvironmentSpecificConfiguration.from(environmentVariables).getProperty("user.admin.salaryloans");
    }

    public String getPassAdminSalaryLoans(){
        return EnvironmentSpecificConfiguration.from(environmentVariables).getProperty("pss.admin.salaryloans");
    }

    public String getHostConnectToParameters() {
        return EnvironmentSpecificConfiguration.from(environmentVariables).getProperty("mysqlparameters.host");
    }

    public String getPortConnectToParameters() {
        return EnvironmentSpecificConfiguration.from(environmentVariables).getProperty("mysqlparameters.port");
    }

    public String getPassConnectToParameters() {
        return EnvironmentSpecificConfiguration.from(environmentVariables).getProperty("mysqlparameters.password");
    }

    public String getDataBaseParameters() {
        return EnvironmentSpecificConfiguration.from(environmentVariables).getProperty("mysqlparameters.database");
    }

    public String getUserConnectToParameters() {
        return EnvironmentSpecificConfiguration.from(environmentVariables).getProperty("mysqlparameters.user");
    }
}
