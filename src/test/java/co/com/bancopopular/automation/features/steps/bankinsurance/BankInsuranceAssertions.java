package co.com.bancopopular.automation.features.steps.bankinsurance;

import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.data.Environment;
import co.com.bancopopular.automation.exceptions.UserNotFoundException;
import co.com.bancopopular.automation.utils.SessionHelper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.annotations.Step;
import org.aeonbits.owner.ConfigFactory;
import java.util.HashMap;
import java.util.Map;

public class BankInsuranceAssertions {

  private static final String DESCRIPTION = "description";
  private static final String MONTHS="12";
  private static final String CODE="code";
  private static final String NAME ="name";
  private static final String MAX_COVERAGE = "maxCoverage";
  private static final String SPOUSE_COVERAGED ="spouseCoveraged";
  private static final String PRICE = "price";
  private static final String USER_NOT_FOUND_EXCEPTION= "Type of user does not exist in the system";
  private static final String COVERAGES = "coverages";
  private static final String SERVICES="services";
  private static final String WITHOUT_OFFER_CODE ="CustomerManagement020";
  private static final String WITHOUT_OFFER_DESCRIPTION="There are no offerings for the customer";
  private static final String TERMS="terms";
  private static final String TERM_CONDITIONS="termsConditions";
  private static final String FILES_DIR="src/test/resources/files/";
  private final Environment environment = ConfigFactory.create(Environment.class);

  private static final Map<String, String> codeToFileMap = new HashMap<>();

  static {
    codeToFileMap.put("121005", "121005.txt");
    codeToFileMap.put("121006", "121006.txt");
    codeToFileMap.put("121007", "121007.txt");
    codeToFileMap.put("121008", "121008.txt");
    codeToFileMap.put("121009", "121009.txt");
    codeToFileMap.put("121010", "121010.txt");
    codeToFileMap.put("121011", "121011.txt");
    codeToFileMap.put("121012", "121012.txt");
    codeToFileMap.put("120905", "120905.txt");
    codeToFileMap.put("120906", "120906.txt");
    codeToFileMap.put("121505", "121505.txt");
    codeToFileMap.put("121506", "121506.txt");
  }

  @Step("Verificar valores bancaseguros para sector educativo")
  public void verifyBankInsuranceEducation() throws UserNotFoundException {

    var data = environment.bankInsuranceCoverageEducation();

    verifyInsurance(data);
  }

  @Step("Verificar valores bancaseguros en Pensionado hasta 69 años")
  public void verifyBankInsurancePensioner69(String months) throws UserNotFoundException {
    var data="";
    if(months.equals(MONTHS)){
      data = environment.bankInsuranceCoverage69Old12();
    }else {
      data = environment.bankInsuranceCoverage69Old48();
    }

    verifyInsurance(data);
  }

  @Step("Verificar valores bancaseguros en Pensionado mayor a 70 años con prima n meses")
  public void verifyBankInsurancePensioner70(String months) throws UserNotFoundException {

    var data="";
    if(months.equals(MONTHS)){
      data = environment.bankInsuranceCoverage70Old12();
    }else {
      data = environment.bankInsuranceCoverage70Old48();
    }
    verifyInsurance(data);
  }

  @Step("Verificar valores bancaseguros en educativo hasta 18-40 años")
  public void verifyBankInsuranceEducational40(String months) throws UserNotFoundException {
    var data = environment.bankInsuranceCoverage40Old12();
    verifyInsurance(data);
  }

  @Step("Verificar valores bancaseguros en educativo hasta 56-65 años")
  public void verifyBankInsuranceEducational56(String months) throws UserNotFoundException {
    var data = environment.bankInsuranceCoverage56Old48();
    verifyInsurance(data);
  }

  @Step("Verificar valores bancaseguros en Pensionado 70 a 81 años en flujo CNC")
  public void verifyBankInsurancePensioner70To81CNC() throws UserNotFoundException {
    var data = environment.bankInsuranceCoverage70to81CNC();
    verifyInsurance(data);
  }

  @Step("Verificar valores bancaseguros en Pensionado mayor o igual a 81 años en CNC")
  public void verifyBankInsurancePensionerover81CNC() {
    SerenityRest.then().assertThat().body(CODE, equalTo(WITHOUT_OFFER_CODE));
    SerenityRest.then().assertThat().body(DESCRIPTION, equalTo(WITHOUT_OFFER_DESCRIPTION));
  }

  public void verifyInsurance(String data) throws UserNotFoundException {
    Optional<String> dataOptional = Optional.ofNullable(data);
    var dataVerified = dataOptional
            .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_EXCEPTION));

    var dataInsurance = JsonParser.parseString(dataVerified).getAsJsonObject();

    BankInsuranceCoverages[] bankInsuranceCoverages = SerenityRest.then().extract().body().as(BankInsuranceCoverages[].class);
    for (BankInsuranceCoverages bankInsuranceCoverage : bankInsuranceCoverages) {

      String bankInsuranceCode = bankInsuranceCoverage.getCode();
      JsonObject jsonCoverage = dataInsurance.get(bankInsuranceCode).getAsJsonObject();

      assertThat(bankInsuranceCoverage.getName(),
              is(jsonCoverage.get(NAME).getAsString()));
      var jsonCoverageDescription = jsonCoverage.get(COVERAGES).getAsJsonArray();
      var jsonServiceDescription = jsonCoverage.get(SERVICES).getAsJsonArray();
      assertThat(bankInsuranceCoverage.getMaxCoverage(),
              is(jsonCoverage.get(MAX_COVERAGE).getAsBoolean()));
      assertThat(bankInsuranceCoverage.getPrice(),
              is(jsonCoverage.get(PRICE).getAsString()));
      assertThat(bankInsuranceCoverage.getSpouseCoveraged(),
              is(jsonCoverage.get(SPOUSE_COVERAGED).getAsBoolean()));

      for (var descIndex = 0; descIndex < bankInsuranceCoverage.getCoverages().size(); descIndex++) {
        assertThat(bankInsuranceCoverage.getCoverages().get(descIndex).getDescription(),
                is(jsonCoverageDescription.get(descIndex).getAsJsonObject().get(DESCRIPTION).getAsString()));
      }
      for (var descIndex = 0; descIndex < bankInsuranceCoverage.getServices().size(); descIndex++) {
        assertThat(bankInsuranceCoverage.getServices().get(descIndex).getDescription(),
                is(jsonServiceDescription.get(descIndex).getAsJsonObject().get(DESCRIPTION).getAsString()));
      }
    }
  }
    @Step("Verificar que no exista oferta para bancaseguros")
    public void verifyWithoutBankininsuranceOffer() {
      assertThat(SerenityRest.then().extract().body().jsonPath().getString(CODE),
              is(WITHOUT_OFFER_CODE));
      assertThat(SerenityRest.then().extract().body().jsonPath().getString(DESCRIPTION),
              is(WITHOUT_OFFER_DESCRIPTION));
    }

  public void verifyTermsConditions() throws IOException {
    var code = getFromSession(SessionHelper.SessionData.PLAN).toString();
    var fileName = codeToFileMap.get(code);
    if (fileName == null) {
      throw new IllegalArgumentException("Unknow: ");
    }
    var jsonPath = SerenityRest.then().extract().body().jsonPath();
    var nameFromJson = jsonPath.getString(TERMS + Constants.CERO_POSITION + NAME);
    var termConditionsFromJson = jsonPath.getString(TERMS + Constants.CERO_POSITION + TERM_CONDITIONS);
    var expectedContent = cleanText(readFile(FILES_DIR + fileName));
    assertThat(nameFromJson, is(code));
    assertThat(cleanText(termConditionsFromJson), is(cleanText(expectedContent)));
  }
  private static String readFile(String path) throws IOException {
    byte[] encoded = Files.readAllBytes(Paths.get(path));
    return new String(encoded, StandardCharsets.UTF_8).trim();
  }
  private static String cleanText(String text) {
    return text.replace("\\", "").replaceAll("\\s+", "").trim();
  }
}