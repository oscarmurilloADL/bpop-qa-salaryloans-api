package co.com.bancopopular.automation.features.steps.sygnus;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import co.com.bancopopular.automation.utils.RedisUtil;
import co.com.bancopopular.automation.utils.WprCipher;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.annotations.Step;

public class SygnusAssertions {

  private static final String ACTIVE = "active";
  private static final String BINDING_NUMBER = "bindingNumber";
  private static final String POSITION = "position";
  private static final String SYGNUS_ERROR_DESCRIPTION_MINUS2 = "Datos de trama incorrectos.  - -2";
  private static final String SYGNUS_ERROR_CODE_PAYERPLATFORM01 = "PayerPlatform01";
  private static final String SYGNUS_ERROR_DESCRIPTION_MINUS9 = "El beneficiario no ha autorizado procesos en línea.  - -9";
  private static final String SYGNUS_ERROR_CODE_PAYERPLATFORM05 = "PayerPlatform05";
  private static final String DESCRIPTION = "description";
  private static final String PURCHASING_ERROR = "El beneficiario está en proceso de compra de cartera.  - -9";
  private static final String PURCHASING_CODE_ERROR = "PayerPlatform04";
  private static final String GROWTH_POLICY_CODE_ERROR = "Simulate001";
  private static final String GROWTH_POLICY_CODE_DESCRIPTION = "Exception in simulation by: Payroll Simulation Invalid, Payroll Simulation Negative, Financial Services Transaction, Salary Loan Does Not Reach Minimum Growth Amount, Invalid Loan Conditions, Renew Loan Growth Policy Transaction, Modality Information Not Found or Cashier Office Not Found";
  private static final String ON = "ON";
  private static final String OFF = "OFF";
  private static final String CREMIL = "CAJA DE RETIRO DE LAS FFMM";


  @Step("Verificación de vinculaciones")
  public void verifyBindigs() {
    var decryptedBody = WprCipher.decryptRequest(SerenityRest.then().extract().body().asString());
    assertThat(decryptedBody.getList(ACTIVE).get(0),
            is(1));
    assertThat(decryptedBody.getList(BINDING_NUMBER).get(0),
            is("11354244X"));
    assertThat(decryptedBody.getList(POSITION).get(0),
            is("SV"));
    assertThat(decryptedBody.getList(ACTIVE).get(1),
            is(1));
    assertThat(decryptedBody.getList(BINDING_NUMBER).get(1),
            is("5559999X"));
    assertThat(decryptedBody.getList(POSITION).get(1),
            is("TC"));
    assertThat(decryptedBody.getList(ACTIVE).get(2),
            is(1));
    assertThat(decryptedBody.getList(BINDING_NUMBER).get(2),
            is("23354244X"));
    assertThat(decryptedBody.getList(POSITION).get(2),
            is("SV"));
  }

  @Step("Verificación de autorización en Sygnus")
  public void verifyAuthorizationSygnus() {
    var decryptedBody = WprCipher.decryptRequest(SerenityRest.then().extract().body().asString());
    assertThat(decryptedBody.getString("code"),
            is(SYGNUS_ERROR_CODE_PAYERPLATFORM05));
    assertThat(decryptedBody.getString(DESCRIPTION),
            is("El beneficiario no ha autorizado procesos en línea.  - -6"));
  }
  @Step("Verificación de cupo en Sygnus exitosa")
  public void verifyOfferBinding() {
    assertThat(SerenityRest.then().extract().body().jsonPath().getJsonObject("creditReview").toString(),
            is("117110"));
    assertThat(SerenityRest.then().extract().body().jsonPath().getJsonObject("disbursementAmount").toString(),
            is("49814856"));
    assertThat(SerenityRest.then().extract().body().jsonPath().getJsonObject("feeAmount").toString(),
            is("1319977"));
    assertThat(SerenityRest.then().extract().body().jsonPath().getJsonObject("id").toString(),
            not(""));
    assertThat(SerenityRest.then().extract().body().jsonPath().getJsonObject("interestAdjustment").toString(),
            is("50110"));
    assertThat(SerenityRest.then().extract().body().jsonPath().getJsonObject("interestAdvanced").toString(),
            is("924650"));
    assertThat(SerenityRest.then().extract().body().jsonPath().getJsonObject("interestRate").toString(),
            is("0.022444"));
    assertThat(SerenityRest.then().extract().body().jsonPath().getJsonObject("lifeInsuranceAdjustment").toString(),
            is("7292"));
    assertThat(SerenityRest.then().extract().body().jsonPath().getJsonObject("lifeInsuranceAdvanced").toString(),
            is("298282"));
    assertThat(SerenityRest.then().extract().body().jsonPath().getJsonObject("maxFeeNumberRule"),
            is(nullValue()));
    assertThat(SerenityRest.then().extract().body().jsonPath().getJsonObject("maxLoanAmount").toString(),
            is("51155000"));
    assertThat(SerenityRest.then().extract().body().jsonPath().getJsonObject("maxLoanTerm").toString(),
            is("120"));
    assertThat(SerenityRest.then().extract().body().jsonPath().getJsonObject("minLoanAmount").toString(),
            is("300000.0"));
    assertThat(SerenityRest.then().extract().body().jsonPath().getJsonObject("minLoanTerm").toString(),
            is("73"));
    assertThat(SerenityRest.then().extract().body().jsonPath().getJsonObject("nominalRate").toString(),
            is("0.0169"));
    assertThat(SerenityRest.then().extract().body().jsonPath().getJsonObject("obligationBalance").toString(),
            is("3557402"));
    assertThat(SerenityRest.then().extract().body().jsonPath().getJsonObject("responseCode"),
            is(nullValue()));
  }

  @Step("Consulta de cupo en Sygnus no exitosa")
  public void verifyOfferError() {
    assertThat(SerenityRest.then().extract().body().jsonPath().getJsonObject("code").toString(),
            is(PURCHASING_CODE_ERROR));
    assertThat(
            SerenityRest.then().extract().body().jsonPath().getJsonObject(DESCRIPTION).toString(),
            is(PURCHASING_ERROR));
  }

  @Step("Consulta de cupo en Sygnus no exitosa")
  public void verifyGrowthPolicyError() {
    assertThat(SerenityRest.then().extract().body().jsonPath().getJsonObject("code").toString(),
            is(GROWTH_POLICY_CODE_ERROR));
    assertThat(
            SerenityRest.then().extract().body().jsonPath().getJsonObject(DESCRIPTION).toString(),
            is(GROWTH_POLICY_CODE_DESCRIPTION));
  }

  public void verifySygnusError(String errorCode) {
    if (errorCode.equals("-2"))
    {
      assertThat(SerenityRest.then().extract().body().jsonPath().getJsonObject("code").toString(),
              is(SYGNUS_ERROR_CODE_PAYERPLATFORM01));
      assertThat(
              SerenityRest.then().extract().body().jsonPath().getJsonObject(DESCRIPTION).toString(),
              is(SYGNUS_ERROR_DESCRIPTION_MINUS2));
    } else if (errorCode.equals("-9"))
    {
      assertThat(SerenityRest.then().extract().body().jsonPath().getJsonObject("code").toString(),
              is(SYGNUS_ERROR_CODE_PAYERPLATFORM05));
      assertThat(
              SerenityRest.then().extract().body().jsonPath().getJsonObject(DESCRIPTION).toString(),
              is(SYGNUS_ERROR_DESCRIPTION_MINUS9));
    }
  }
  @Step("Verificacion estado plataforma en Sygnus")
  public void verifySygnusStatus(String status) {
    RedisUtil redisUtil = new RedisUtil();
    if (status.equals("abierta")){
      assertThat(redisUtil.getRedis(CREMIL), is(ON));
    }else{
      assertThat(redisUtil.getRedis(CREMIL), is(OFF));
    }
  }
}