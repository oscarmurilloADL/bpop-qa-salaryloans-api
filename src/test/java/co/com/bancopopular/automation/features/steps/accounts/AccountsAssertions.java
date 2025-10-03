package co.com.bancopopular.automation.features.steps.accounts;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import co.com.bancopopular.automation.utils.WprCipher;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.annotations.Step;
import java.util.List;

public class AccountsAssertions {

  private static final String ACCOUNT_ID = "accountId";
  private static final String ACCOUNT_STATUS = "accountStatus";
  private static final String ACCOUNT_TYPE = "accountType";
  private static final String RESULT = "result";
  private static final String IS_THERE_ACCOUNTS = "isThereAccounts";
  private static final String IS_THERE_TECHNICAL_ERROR = "isThereTechnicalError";
  private static final String TECHNICAL_ERROR_DTO = "technicalErrorDto";
  private static final String CONTEXT = "context";
  private static final String SUCCESS = "success";
  private static final String FAIL = "fail";
  private static final String FALSE = "false";
  private static final String CODE_DESCRIPTION = "[code:, description:]";
  private static final String TRUE = "true";
  private static final String CONTEXT_WITHOUT_PROBLEMATIC_ACCOUNTS = "[numberOfSeizedAccounts:0, numberOfBlockedAccounts:0, numberOfDualOwnershipAccounts:0]";


  @Step("Verificar datos de las cuentas del cliente")
  public void verifyAccounts(String accountType) {
    if (accountType.equals("activas e inactivas"))
    {
      assertThat(SerenityRest.then().extract().body().jsonPath().getList(ACCOUNT_ID).get(0),
              is("110-038-000186-4"));
      assertThat(SerenityRest.then().extract().body().jsonPath().getList(ACCOUNT_STATUS).get(0),
              is("INACTIVA"));
      assertThat(SerenityRest.then().extract().body().jsonPath().getList(ACCOUNT_TYPE).get(0),
              is("SDA"));
      assertThat(SerenityRest.then().extract().body().jsonPath().getList(ACCOUNT_ID).get(1),
              is("110-038-000187-4"));
      assertThat(SerenityRest.then().extract().body().jsonPath().getList(ACCOUNT_STATUS).get(1),
              is("ACTIVA"));
      assertThat(SerenityRest.then().extract().body().jsonPath().getList(ACCOUNT_TYPE).get(1),
              is("SDA"));
    }
    else if (accountType.equals("la nueva"))
    {
      assertThat(SerenityRest.then().extract().body().jsonPath().getList(ACCOUNT_ID).get(0),
              is("230-038-000186-5"));
      assertThat(SerenityRest.then().extract().body().jsonPath().getList(ACCOUNT_STATUS).get(0),
              is("NUEVA"));
      assertThat(SerenityRest.then().extract().body().jsonPath().getList(ACCOUNT_TYPE).get(0),
              is("SDA"));
    }
    else if (accountType.equals("la disponible"))
    {
      assertThat(SerenityRest.then().extract().body().jsonPath().getList(ACCOUNT_ID).get(0),
              is("21004000723934"));
      assertThat(SerenityRest.then().extract().body().jsonPath().getList(ACCOUNT_STATUS).get(0),
              is("ACTIVA"));
      assertThat(SerenityRest.then().extract().body().jsonPath().getList(ACCOUNT_TYPE).get(0),
              is("SDA"));
      assertThat(SerenityRest.then().extract().body().jsonPath().getList(ACCOUNT_TYPE).size(),
              is(1));
    } else if (accountType.equals("las de estructura correcta"))
    {
      assertThat(SerenityRest.then().extract().body().jsonPath().getList(ACCOUNT_TYPE).size(),
              is(9));
    }
  }

  @Step("Verifica presencia de cuentas nativas Flexcube")
  public void verifyNativeAccountsInList(boolean hasNativeFlexcubeAccounts) {
    List<String> accountList;
    accountList = SerenityRest.then().extract().body().jsonPath().getList(ACCOUNT_ID);
    var isAnyNativeAccount = false;
    isAnyNativeAccount = accountList.stream().allMatch(t -> t.startsWith("500"));
    if (hasNativeFlexcubeAccounts)
      assertThat(isAnyNativeAccount, is(true));
    else
      assertThat(isAnyNativeAccount, is(false));
  }

  @Step("Verifica que tiene unica cuenta en estado derogatorio")
  public void verifySeizedAccount() {
    JsonPath decryptedBody = WprCipher.extractDecryptResponse();
    assertThat(decryptedBody.getString(RESULT),
            is(SUCCESS));
    assertThat(decryptedBody.getString(IS_THERE_ACCOUNTS),
            is(FALSE));
    assertThat(decryptedBody.getString(IS_THERE_TECHNICAL_ERROR),
            is(FALSE));
    assertThat(decryptedBody.getString(TECHNICAL_ERROR_DTO),
            is(CODE_DESCRIPTION));
    assertThat(decryptedBody.getString(CONTEXT),
            is("[numberOfSeizedAccounts:1, numberOfBlockedAccounts:0, numberOfDualOwnershipAccounts:0]"));
  }

  @Step("Verifica que tiene cuentas derrogadas, bloqueadas y con doble titularidad")
  public void verifySeizedBloquedAndDualOwnershipAccounts() {
    JsonPath decryptedBody = WprCipher.extractDecryptResponse();
    assertThat(decryptedBody.getString(RESULT),
            is(SUCCESS));
    assertThat(decryptedBody.getString(IS_THERE_ACCOUNTS),
            is(TRUE));
    assertThat(decryptedBody.getString(IS_THERE_TECHNICAL_ERROR),
            is(FALSE));
    assertThat(decryptedBody.getString(TECHNICAL_ERROR_DTO),
            is(CODE_DESCRIPTION));
    assertThat(decryptedBody.getString(CONTEXT),
            is("[numberOfSeizedAccounts:1, numberOfBlockedAccounts:1, numberOfDualOwnershipAccounts:1]"));
  }

  @Step("Verifica que tiene cuentas aptas")
  public void verifyValidAccounts() {
    JsonPath decryptedBody = WprCipher.extractDecryptResponse();
    assertThat(decryptedBody.getString(RESULT),
            is(SUCCESS));
    assertThat(decryptedBody.getString(IS_THERE_ACCOUNTS),
            is(TRUE));
    assertThat(decryptedBody.getString(IS_THERE_TECHNICAL_ERROR),
            is(FALSE));
    assertThat(decryptedBody.getString(TECHNICAL_ERROR_DTO),
            is(CODE_DESCRIPTION));
    assertThat(decryptedBody.getString(CONTEXT),
            is(CONTEXT_WITHOUT_PROBLEMATIC_ACCOUNTS));
  }

  @Step("Verifica que tiene unica cuenta en estado doble titularidad")
  public void verifyDualOwnnershipAccount() {
    JsonPath decryptedBody = WprCipher.extractDecryptResponse();
    assertThat(decryptedBody.getString(RESULT),
            is(SUCCESS));
    assertThat(decryptedBody.getString(IS_THERE_ACCOUNTS),
            is(FALSE));
    assertThat(decryptedBody.getString(IS_THERE_TECHNICAL_ERROR),
            is(FALSE));
    assertThat(decryptedBody.getString(TECHNICAL_ERROR_DTO),
            is(CODE_DESCRIPTION));
    assertThat(decryptedBody.getString(CONTEXT),
            is("[numberOfSeizedAccounts:0, numberOfBlockedAccounts:0, numberOfDualOwnershipAccounts:1]"));
  }

  @Step("Verifica que tiene unica cuenta en estado bloqueado")
  public void verifyBloquedAccount() {
    JsonPath decryptedBody = WprCipher.extractDecryptResponse();
    assertThat(decryptedBody.getString(RESULT),
            is(SUCCESS));
    assertThat(decryptedBody.getString(IS_THERE_ACCOUNTS),
            is(FALSE));
    assertThat(decryptedBody.getString(IS_THERE_TECHNICAL_ERROR),
            is(FALSE));
    assertThat(decryptedBody.getString(TECHNICAL_ERROR_DTO),
            is(CODE_DESCRIPTION));
    assertThat(decryptedBody.getString(CONTEXT),
            is("[numberOfSeizedAccounts:0, numberOfBlockedAccounts:1, numberOfDualOwnershipAccounts:0]"));
  }

  @Step("Verifica que tiene cuentas derrogadas, bloqueadas y con doble titularidad flujo no exitoso")
  public void verifySeizedBloquedAndDualOwnershipAccountsFailed() {
    JsonPath decryptedBody = WprCipher.extractDecryptResponse();
    assertThat(decryptedBody.getString(RESULT),
            is(SUCCESS));
    assertThat(decryptedBody.getString(IS_THERE_ACCOUNTS),
            is(FALSE));
    assertThat(decryptedBody.getString(IS_THERE_TECHNICAL_ERROR),
            is(FALSE));
    assertThat(decryptedBody.getString(TECHNICAL_ERROR_DTO),
            is(CODE_DESCRIPTION));
    assertThat(decryptedBody.getString(CONTEXT),
            is("[numberOfSeizedAccounts:1, numberOfBlockedAccounts:1, numberOfDualOwnershipAccounts:1]"));
  }

  @Step("Verifica que no tiene cuentas Flexcube y el flujo es no exitoso")
  public void verifyWithoutFlexcubeAccounts() {
    JsonPath decryptedBody = WprCipher.extractDecryptResponse();
    assertThat(decryptedBody.getString(RESULT),
            is(SUCCESS));
    assertThat(decryptedBody.getString(IS_THERE_ACCOUNTS),
            is(FALSE));
    assertThat(decryptedBody.getString(IS_THERE_TECHNICAL_ERROR),
            is(FALSE));
    assertThat(decryptedBody.getString(TECHNICAL_ERROR_DTO),
            is("[code:failFlexCubeOwnership003, description:Cliente no tiene cuentas FlexCube]"));
    assertThat(decryptedBody.getString(CONTEXT),
            is(CONTEXT_WITHOUT_PROBLEMATIC_ACCOUNTS));
  }

  @Step("Verifica error técnico de titularidad y el flujo es no exitoso")
  public void verifyTechnicalErrorOwnership() {
    JsonPath decryptedBody = WprCipher.extractDecryptResponse();
    assertThat(decryptedBody.getString(RESULT),
            is(FAIL));
    assertThat(decryptedBody.getString(IS_THERE_ACCOUNTS),
            is(FALSE));
    assertThat(decryptedBody.getString(IS_THERE_TECHNICAL_ERROR),
            is(TRUE));
    assertThat(decryptedBody.getString(TECHNICAL_ERROR_DTO),
            is("[code:failFlexcubeOwnership001, description:{\"result\":\"failed\",\"error\":{\"name\":\"FlexcubeAccountsLambdaException\",\"description\":\"class java.lang.Exception: null\",\"flexcubeAccountsResponseDTO\":{\"isThereAcount\":false,\"isThereTechnicalError\":true,\"tecnicalErrorDTO\":{\"codeError\":500,\"descriptionError\":\"LambdaException\"},\"result\":\"failed\",\"content\":{\"accountList\":null}}}}]"));
    assertThat(decryptedBody.getString(CONTEXT),
            is(CONTEXT_WITHOUT_PROBLEMATIC_ACCOUNTS));
  }

  @Step("Verifica Cuentas sin la estructura correcta y el flujo es no exitoso")
  public void verifyUnstructuredAccountFailed() {
    JsonPath decryptedBody = WprCipher.extractDecryptResponse();
    assertThat(decryptedBody.getString(RESULT),
            is(SUCCESS));
    assertThat(decryptedBody.getString(IS_THERE_ACCOUNTS),
            is(FALSE));
    assertThat(decryptedBody.getString(IS_THERE_TECHNICAL_ERROR),
            is(FALSE));
    assertThat(decryptedBody.getString(TECHNICAL_ERROR_DTO),
            is("[code:failFlexCubeOwnership003, description:Cliente no tiene cuentas aptas]"));
    assertThat(decryptedBody.getString(CONTEXT),
            is(CONTEXT_WITHOUT_PROBLEMATIC_ACCOUNTS));
  }
}
