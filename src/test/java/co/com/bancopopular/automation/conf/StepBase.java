package co.com.bancopopular.automation.conf;

import co.com.bancopopular.automation.data.Environment;
import co.com.bancopopular.automation.utils.AssertionsUtil;
import net.serenitybdd.annotations.Steps;
import org.aeonbits.owner.ConfigFactory;

public abstract class StepBase {

  private final Environment environment;

  protected StepBase() {
    environment = ConfigFactory.create(Environment.class);
  }

  protected Environment getEnvironment() {
    return environment;
  }

  @Steps
  public AssertionsUtil assertionsUtil;

}
