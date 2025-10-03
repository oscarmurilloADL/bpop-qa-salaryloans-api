package co.com.bancopopular.automation.features.steps.toggles;

import co.com.bancopopular.automation.data.Environment;
import co.com.bancopopular.automation.exceptions.UserNotFoundException;
import co.com.bancopopular.automation.utils.WprCipher;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.annotations.Step;
import org.aeonbits.owner.ConfigFactory;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TogglesAssertions {

  private final Environment environment = ConfigFactory.create(Environment.class);


  @Step("Verificacion información de los toggles")
  public void verifyTogglesInformation() throws UserNotFoundException {
    var data = environment.togglesInformation();
    Optional<String> dataOptional = Optional.ofNullable(data);

    var dataVerified = dataOptional
        .orElseThrow(() -> new UserNotFoundException("Type of user does not exist in the system"));
    var dataToggles = JsonParser.parseString(dataVerified).getAsJsonArray();

    var decryptedAnswer = WprCipher.decryptRequest(SerenityRest.then().extract().body().asString()).get().toString();
    var regexGeneralFix = "(?<=:\")[^\"]*?(?=[},])";
    decryptedAnswer = decryptedAnswer.replace("usuario,", "usuario")
            .replace("automática,", "automática")
            .replace("=", "\":\"")
            .replace(", ", "\",\"")
            .replace("\\{", "{\"")
            .replace("\\}", "\"}")
            .replace(":true,", ":\"true\",")
            .replace(":false,", ":\"false\",")
            .replace("}\",\"","},")
            .replace("featureName","\"featureName")
            .replaceAll(regexGeneralFix, "$0\"");
    var gson = new Gson();
    TogglesDescription[] togglesDescriptions = gson.fromJson(decryptedAnswer, TogglesDescription[].class);
    for (var i = 0 ; i < togglesDescriptions.length-1; i++) {
      assertThat(togglesDescriptions[i].getFeatureName(),
          is(dataToggles.get(i).getAsJsonObject().get("featureName").getAsString()));
    }
  }
}
