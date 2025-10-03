package co.com.bancopopular.automation.features.steps.anyfeesimulation;

import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.utils.AssertionsUtil;
import co.com.bancopopular.automation.utils.SessionHelper;
import co.com.bancopopular.automation.utils.WprCipher;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.annotations.Steps;

public class AnyFeeSimulationAssertions {

  private static final String SIMULATION_ERROR_CODE = "PayrollChecks9999";
  private static final String SIMULATION_ERROR_MESSAGE = "Unknown Error";
  private static final String SIMULATION_ERROR="simulationError";
  private static final String ADDITIONAL_STATUS="additionalStatus";
  private static final String STATUS_CODE="statusCode";
  private static final String STATUS_DESC="statusDesc";
  private static final String SERVER_STATUS_CODE="serverStatusCode";
  private static final String DESC_GENERIC_ERROR ="No es posible procesar la transacción. Comuníquese con la Entidad.";
  private static final String DESC_ESPECIFIC_ERROR ="No Es Posible Procesar La Transaccion - Problemas Tecnicos. Por Favor Intente Mas Tarde";
  private static final String DESC_ERROR_CODE_2="Segun [Pagaduria = FONDO EDUCATIVO REGIONAL - RIONEGRO - 185 - ; Modalidad 80 ; Edad= 56 ; Disponible= 1582243.0 ; Regimen= ACTIVO ; Linea= 03 ; Sucursal= 2] Error Logicas: No se encontro grupo de condiciones financieras en la pagaduria o el cliente presenta ingresos inferiores a un salario minimo.";
  private static final String DESC_ERROR_CODE_105="La obligacion  23103240009494  afectaron desprendible. Deben recibir por lo menos una cuota pagada completa para poder ser recogidas.";
  private static final String DESC_ERROR_CODE_111="Error Logicas: Con la información ingresada (nit, código de oficina, sector) no se encontró una pagaduría para realizar simulación.";
  private static final String DESC_ERROR_CODE_114="Error Logicas: El deudor seleccionado ya tiene una obligacion directa con esta pagaduria, debe ingresar la solicitud por la opción de Ampliacion de Credito (no aplica crédito nuevo)";
  private static final String DESC_ERROR_CODE_117="Error Logicas: El valor de la cuota no debe ser mayor al valor disponible.";
  private static final String DESC_ERROR_CODE_122="Error Logicas: Con los criterios especificados, no se han encontrado una modalidad (edad =93, regimen =1, modalidades =79,80,81,82,83, consulta=SELECT DISTINCT p.c42idmodalidad    FROM T42CONFINANCIERA P,  t32modalidad md,    t32tipomodalidad tm    WHERE p.c42idmodalidad = md.c32idmodalidad    and md.c32idtipomodalidad = tm.c32idtipomodalidad    and P.C42IDCRITERIO =115   AND P.C42GRUPO ='-1'   AND 93 BETWEEN CAST(P.c42campo01 AS NUMERIC) AND CAST(P.c42campo02 AS NUMERIC)   and p.c42idlinea = '03'   AND p.c42idregimen = 1   AND p.c42proceso = 2   and tm.c32nemotecnico ='ORD'   AND p.c42idmodalidad in    (SELECT     DISTINCT txf.C41IDMODALIDAD    FROM t41tasaxfecha      txf,    t42confinanciera   cf,    T26GRUPOXPAGADURIA pxg    WHERE txf.c41grupo = cf.c42grupo    and cf.c42grupo = pxg.c26grupo    and txf.c41idmodalidad = cf.c42idmodalidad    and txf.c41idregimen = cf.c42idregimen    and txf.c41fecinivigencia  to_date ('2022/05/17','yyyy/mm/dd')   AND cf.c42idcriterio = 132   and cf.c42idlinea = '03'   AND cf.c42idregimen =1   AND cf.c42proceso = 2   AND txf.C41IDMODALIDAD IN (79,80,81,82,83)     AND CASE WHEN CAST(cf.c42campo01 AS NUMERIC) = 0     AND CAST(cf.c42campo02 AS NUMERIC) = 999 THEN 1   ELSE CAST(cf.c42campo01 AS NUMERIC)     END =1   AND CASE WHEN CAST(cf.c42campo01 AS NUMERIC) = 0   AND CAST(cf.c42campo02 AS NUMERIC) = 999 THEN 999   ELSE CAST(cf.c42campo02 AS NUMERIC)   END =999)) existe más de una modalidad con las mismas condiciones.";
  private static final String DESC_ERROR_CODE_125="Error Logicas: Con los criterios especificados, no se han encontrado modalidad por sector y subSector (sector =2, subSector=20, nemoTecnico =ORD).";
  private static final String DESC_ERROR_CODE_141="Error Logicas: El deudor tiene una solicitud en trámite con esta pagaduría, imposible continuar.";
  private static final String DESC_ERROR_CODE_145="Error Logicas: El cliente presenta las siguientes causales: ( CLIENTE CON INCAPACIDAD PERMANENTE), imposible continuar";
  private static final String DESC_ERROR_CODE_147="Error Logicas: No se puede generar oferta para el cliente debido a que se supera la edad máxima por politica";

  @Steps
  AssertionsUtil assertionsUtil;

  @Step("Verificacion valores maximo del credito")
  public void verifyCreditData() {
    assertThat(SerenityRest.then().extract().body().jsonPath().getInt("id"),
        is(500017));
    assertThat(SerenityRest.then().extract().body().jsonPath().getInt("maxLoanAmount"),
        is(63529000));
    assertThat(SerenityRest.then().extract().body().jsonPath().getDouble("minLoanAmount"),
        is(3.00E+5));
    assertThat(SerenityRest.then().extract().body().jsonPath().getInt("maxLoanTerm"),
        is(120));
    assertThat(SerenityRest.then().extract().body().jsonPath().getInt("minLoanTerm"),
        is(73));
    assertThat(SerenityRest.then().extract().body().jsonPath().getDouble("interestRate"),
        is(0.017406));
    assertThat(SerenityRest.then().extract().body().jsonPath().getDouble("nominalRate"),
        is(0.0164));
  }

  @Step("Verificacion de mensaje de error por falta del campo DisbursementAmt")
  public void verifyErrorCreditData() {
    String optimization=getFromSession(SessionHelper.SessionData.OPTIMIZATION_TOGGLE_II).toString();
    if(optimization.equals("")) {
      assertionsUtil.shouldSeeInternalErrorStatusCode();
      assertThat(SerenityRest.then().extract().body().jsonPath().getString("code"),
              is(SIMULATION_ERROR_CODE));
      assertThat(SerenityRest.then().extract().body().jsonPath().getString("description"),
              is(SIMULATION_ERROR_MESSAGE));
    }else{
      assertionsUtil.shouldSeeSuccessfulStatusCode();
      assertThat(SerenityRest.then().extract().body().jsonPath().getString("validationStatus"),
              is(Constants.FAIL_SIMULATION));
    }
  }

  public void verifySimulationError(String code) {
    var encryptedBody = SerenityRest.then().extract().body().asString();
    var decryptedBody = WprCipher.decryptRequest(encryptedBody);
    assertThat(decryptedBody.getString(SIMULATION_ERROR+"."+STATUS_DESC),
            is(DESC_GENERIC_ERROR));
    assertThat(decryptedBody.getString(SIMULATION_ERROR+"."+ADDITIONAL_STATUS+"[0]."+SERVER_STATUS_CODE),
            is(DESC_ESPECIFIC_ERROR));
    assertThat(decryptedBody.getString(SIMULATION_ERROR+"."+ADDITIONAL_STATUS+"[0]."+STATUS_CODE),
            is(code));
    switch (code){
      case "2":
        assertThat(decryptedBody.getString(SIMULATION_ERROR+"."+ADDITIONAL_STATUS+"[0]."+STATUS_DESC),
                is(DESC_ERROR_CODE_2));
        break;

      case "105":
        assertThat(decryptedBody.getString(SIMULATION_ERROR+"."+ADDITIONAL_STATUS+"[0]."+STATUS_DESC),
                is(DESC_ERROR_CODE_105));
        break;

      case "111":
        assertThat(decryptedBody.getString(SIMULATION_ERROR+"."+ADDITIONAL_STATUS+"[0]."+STATUS_DESC),
                is(DESC_ERROR_CODE_111));
        break;

      case "114":
        assertThat(decryptedBody.getString(SIMULATION_ERROR+"."+ADDITIONAL_STATUS+"[0]."+STATUS_DESC),
                is(DESC_ERROR_CODE_114));
        break;

      case "117":
        assertThat(decryptedBody.getString(SIMULATION_ERROR+"."+ADDITIONAL_STATUS+"[0]."+STATUS_DESC),
                is(DESC_ERROR_CODE_117));
        break;

      case "122":
        assertThat(decryptedBody.getString(SIMULATION_ERROR+"."+ADDITIONAL_STATUS+"[0]."+STATUS_DESC),
                is(DESC_ERROR_CODE_122));
        break;

      case "125":
        assertThat(decryptedBody.getString(SIMULATION_ERROR+"."+ADDITIONAL_STATUS+"[0]."+STATUS_DESC),
                is(DESC_ERROR_CODE_125));
        break;

      case "141":
        assertThat(decryptedBody.getString(SIMULATION_ERROR+"."+ADDITIONAL_STATUS+"[0]."+STATUS_DESC),
                is(DESC_ERROR_CODE_141));
        break;

      case "145":
        assertThat(decryptedBody.getString(SIMULATION_ERROR+"."+ADDITIONAL_STATUS+"[0]."+STATUS_DESC),
                is(DESC_ERROR_CODE_145));
        break;

      case "147":
        assertThat(decryptedBody.getString(SIMULATION_ERROR+"."+ADDITIONAL_STATUS+"[0]."+STATUS_DESC),
                is(DESC_ERROR_CODE_147));
        break;
      default:
        break;
    }

  }
}
