package co.com.bancopopular.automation.features.steps.buros;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import co.com.bancopopular.automation.utils.WprCipher;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.annotations.Step;

public class BuroAssertions {

  private static final String BAD_BEHAVIOR_REASON = "malos hàbitos de pago";
  private static final String BAD_BEHAVIOR_ERROR = "CustomerManagement008";
  private static final String BAD_BEHAVIOR_DESCRIPTION = "Negado por preselecta";
  private static final String LOW_CAPACITY_ERROR = "CustomerManagement006";
  private static final String LOW_CAPACITY_DESCRIPTION = "The Buro Preselecta Service not Response";
  private static final String SEIZURE_ACCOUNT_REASON = "cuenta embargada";
  private static final String SEIZURE_ACCOUNT_ERROR = "CustomerManagement008";
  private static final String SEIZURE_ACCOUNT_DESCRIPTION = "Cuenta embargada";
  private static final String NO_OFFER_ERROR = "CustomerManagement008";
  private static final String NO_OFFER_DESCRIPTION= "La respuesta no trae oferta para el cliente";
  private static final String NO_VIABLE_PROCESS_ERROR ="CustomerManagement028";
  private static final String NO_VIABLE_PROCESS_DESCRIPTION ="Negado en preselecta por capacidad de pago";
  private static final String NO_MINIMUM_DISCOUNT_CAPACITY ="no tener el mìnimo de capacidad de descuento";
  private static final String FINANCIAL_SECTOR="Paz y salvo sector financiero";
  private static final String GROWTH_AMOUNT="GrowthAmount001";
  private static final String GROWTH_AMOUNT_DESCRIPTION="Offer Doesnt Reach Minimum Growth Amount Policy";
  private static final String CURRENT_PAYMENT="Paz y salvo por habito pago actual banco popular";

  @Step("Verificacion datos buro valido")
  public void verifyValidStatus() {
    assertThat(WprCipher.decryptRequest(SerenityRest.then().extract().body().asString()).getString("responseCode"),
        is("valid"));
  }

  @Step("Verificacion datos buro invalido")
  public void verifyInvalidStatus(String invalidReason) {
    var errorCode = LOW_CAPACITY_ERROR;
    var errorDescription = LOW_CAPACITY_DESCRIPTION;

    switch (invalidReason) {
      case BAD_BEHAVIOR_REASON:
        errorCode = BAD_BEHAVIOR_ERROR;
        errorDescription = BAD_BEHAVIOR_DESCRIPTION;
        break;
      case SEIZURE_ACCOUNT_REASON:
        errorCode = SEIZURE_ACCOUNT_ERROR;
        errorDescription = SEIZURE_ACCOUNT_DESCRIPTION;
        break;
      case NO_OFFER_DESCRIPTION:
        errorCode = NO_OFFER_ERROR;
        errorDescription = NO_OFFER_DESCRIPTION;
        break;
      case NO_VIABLE_PROCESS_DESCRIPTION:
        errorCode = NO_VIABLE_PROCESS_ERROR;
        errorDescription = NO_VIABLE_PROCESS_DESCRIPTION;
        break;
      case NO_MINIMUM_DISCOUNT_CAPACITY:
        errorCode = GROWTH_AMOUNT;
        errorDescription = GROWTH_AMOUNT_DESCRIPTION;
        break;
      case FINANCIAL_SECTOR:
        errorCode = BAD_BEHAVIOR_ERROR;
        errorDescription = FINANCIAL_SECTOR;
        break;
      case CURRENT_PAYMENT:
        errorCode = BAD_BEHAVIOR_ERROR;
        errorDescription = CURRENT_PAYMENT;
        break;
      default:
        break;
    }

   assertThat(WprCipher.decryptRequest(SerenityRest.then().extract().body().asString()).getString("code"),
        is(errorCode));
    assertThat(WprCipher.decryptRequest(SerenityRest.then().extract().body().asString()).getString("description"),
        is(errorDescription));
  }
}
