package co.com.bancopopular.automation.utils;

import static com.github.fge.jsonschema.SchemaVersion.DRAFTV4;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import org.everit.json.schema.loader.SchemaLoader;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.annotations.Step;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AssertionsUtil {


  @Step("Verificacion de status code 200")
  public void shouldSeeSuccessfulStatusCode() {
    assertThat(SerenityRest.then().extract().statusCode(), equalTo(HttpStatus.SC_OK));
  }

  @Step("Verificacion de status code 404")
  public void shouldSeeNotFoundStatusCode() {
    assertThat(SerenityRest.then().extract().statusCode(), equalTo(HttpStatus.SC_NOT_FOUND));
  }

  @Step("Verificacion de status code 500")
  public void shouldSeeInternalErrorStatusCode() {
    assertThat(SerenityRest.then().extract().statusCode(),
        equalTo(HttpStatus.SC_INTERNAL_SERVER_ERROR));
  }

  @Step("Validacion Schema encriptado Correcto para JSONArray")
  public void validateEncryptedJSONSchemaArray(String pathToSchema) throws FileNotFoundException {
    var decryptedBodyString = WprCipher.decryptRequest(SerenityRest.then().extract().body().asString()).prettify();
    var schemaFile = new File(pathToSchema);
    var schemaData = new JSONTokener(new FileInputStream(schemaFile));
    var jsonSchema = new JSONObject(schemaData);

    var jsonBody = new JSONArray(decryptedBodyString);

    var schemaValidator = SchemaLoader.load(jsonSchema);
    schemaValidator.validate(jsonBody);
  }

  @Step("Validacion Schema encriptado Correcto para JSONObject")
  public void validateEncryptedJSONSchemaObject(String pathToSchema) throws FileNotFoundException {
    var decryptedBodyString = WprCipher.decryptRequest(SerenityRest.then().extract().body().asString()).prettify();
    var schemaFile = new File(pathToSchema);
    var schemaData = new JSONTokener(new FileInputStream(schemaFile));
    var jsonSchema = new JSONObject(schemaData);

    var jsonBody = new JSONObject(decryptedBodyString);

    var schemaValidator = SchemaLoader.load(jsonSchema);
    schemaValidator.validate(jsonBody);
  }

  @Step("Validacion Schema Correcto")
  public void validateJSONSchema(String pathToSchema) {
    var jsonSchemaFactory = JsonSchemaFactory.newBuilder()
        .setValidationConfiguration(ValidationConfiguration
            .newBuilder().setDefaultVersion(DRAFTV4).freeze()).freeze();
    SerenityRest.then().assertThat()
        .body(matchesJsonSchemaInClasspath(pathToSchema).using(jsonSchemaFactory));
  }

}