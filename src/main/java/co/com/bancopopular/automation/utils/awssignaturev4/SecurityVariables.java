package co.com.bancopopular.automation.utils.awssignaturev4;

import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.util.EnvironmentVariables;

public class SecurityVariables {
    private final EnvironmentVariables environmentVariables;

    public SecurityVariables(EnvironmentVariables environmentVariables) {
        this.environmentVariables = environmentVariables;
    }
    public String getSha256(){
        return EnvironmentSpecificConfiguration.from(environmentVariables).getProperty("sha256");
    }
}
