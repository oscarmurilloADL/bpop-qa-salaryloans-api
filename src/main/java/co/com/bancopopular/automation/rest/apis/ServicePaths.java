package co.com.bancopopular.automation.rest.apis;

import co.com.bancopopular.automation.data.Environment;
import java.net.MalformedURLException;
import java.net.URL;
import org.aeonbits.owner.ConfigFactory;

import static co.com.bancopopular.automation.constants.Constants.*;

public class ServicePaths {

  private static final Environment environment;
  private static final String ACCOUNTCUSTOMERURL = "accounts/customers/";
  private static final String PAYROLLCHECKURL = "payrollCheck/";
  private static final String OFFERING = "offering";
  private static final String ACCOUNT_LIST="flexcube-ownership/accounts-list";
  private static final String EXTRACTION="extraction";

  static {
    environment = ConfigFactory.create(Environment.class);
  }

  private ServicePaths() {
    throw new IllegalStateException("Utility class");
  }

  public static URL getFactorSeguro() throws MalformedURLException {
    return new URL(environment.baseUrlFactor() + "insurance-factor");
  }

  public static URL getOauth2Token() throws MalformedURLException {
    return new URL(environment.baseUrlToken() + "oauth2/token");
  }

  public static URL getAdvisor() throws MalformedURLException {
    return new URL(environment.baseUrl() + "advisor/search");
  }

  public static URL getSearchCustomer() throws MalformedURLException {
    return new URL(environment.baseUrl() + "customer/search");
  }

  public static URL getPayrollStatus(String customerDocType, String customerDocument)
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + ACCOUNTCUSTOMERURL + customerDocType + "/" + customerDocument
                    + "/loans/status");
  }

  public static URL getAuthDebitCard() throws MalformedURLException {
    return new URL(environment.baseUrl() + "auth/debitCard");
  }

  public static URL getPayrollInfo(String customerDocType, String customerDocument)
          throws MalformedURLException {
    return new URL(environment.baseUrl() + ACCOUNTCUSTOMERURL + "loans/" + customerDocType
            + "/" + customerDocument);
  }

  public static URL getInfoClient(String customerDocType, String customerDocument)
          throws MalformedURLException {
    return new URL(environment.baseUrl() + ACCOUNTCUSTOMERURL + customerDocType
            + "/" + customerDocument + "/info");
  }

  public static URL getUpdateClient()
          throws MalformedURLException {
    return new URL(environment.baseUrl() + ACCOUNTCUSTOMERURL);
  }

  public static URL getBuroInfo()
          throws MalformedURLException {
    return new URL(environment.baseUrl() + OFFERING);
  }

  public static URL getValidationCustomer()
          throws MalformedURLException {
    return new URL(environment.baseUrl() + "validations/customer");
  }

  public static URL getValidationInternalViability()
          throws MalformedURLException {
    return new URL(environment.baseUrl() + "validations/customer/internal-viability");
  }

  public static URL getValidationSim()
          throws MalformedURLException {
    return new URL(environment.baseUrl() + "auth/phoneNumber");
  }

  public static URL getSimulationAnyFee(String customerDocType, String customerDocument)
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + "simulations/payrollloan/" + customerDocType + "/"
                    + customerDocument);
  }

  public static URL getAccounts(String customerDocType, String customerDocument)
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + ACCOUNTCUSTOMERURL + customerDocType + "/" + customerDocument
                    + "/accounts");
  }

  public static URL getRenewal(String obligationID)
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + obligationID + "/renew-process");
  }

  public static URL getRenewalStatus(String obligationID, String renewalId)
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + obligationID + "/renew-process/" + renewalId);
  }

  public static URL getLoanAccept()
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + ACCOUNTCUSTOMERURL + "loans/accept");
  }

  public static URL getBankInsurance(String customerDocType, String customerDocument)
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + "banking-insurances/offerings/" + customerDocType + "/"
                    + customerDocument);
  }

  public static URL getPayrollCheck(String obligationID, String customerDocType,
                                    String customerDocument)
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + PAYROLLCHECKURL + "unknown/" + obligationID + "/" + customerDocType
                    + "/" + customerDocument);

  }

  public static URL getPayrollCheckStatus(String idTap, String obligationID, String customerDocType,
                                          String customerDocument)
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + PAYROLLCHECKURL + idTap + "/" + obligationID + "/" + customerDocType
                    + "/" + customerDocument);
  }

  public static URL getGenerateConfirmationOTP(String obligationID)
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + obligationID + "/renewal-confirmation-code");
  }

  public static URL getValidateConfirmationOTP()
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + "validate-renewal-confirmation-code");
  }

  public static URL getOfferInfo(String idSimulation)
          throws MalformedURLException {
    return new URL(environment.baseUrl() + "offering/" + idSimulation);
  }

  public static URL getAuthAdmin()
          throws MalformedURLException {
    return new URL(environment.baseUrl() + "auth/user");
  }

  public static URL getLoanReview()
          throws MalformedURLException {
    return new URL(environment.baseUrl() + "api/back-office/loan-extension");
  }

  public static URL getAuthGenerateOTPCall()
          throws MalformedURLException {
    return new URL(environment.baseUrl() + "auth/otpCall");
  }

  public static URL getAuthValidateOTP()
          throws MalformedURLException {
    return new URL(environment.baseUrl() + "auth/otp");
  }

  public static URL getTermsAndConditions(String customerDocType, String customerDocument)
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + "terms-and-conditions/" + customerDocType + "/" + customerDocument);
  }

  public static URL getCreditBureauReport(String customerDocType, String customerDocument)
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + "credit-bureau-reporting/" + customerDocType + "/"
                    + customerDocument);
  }

  public static URL sendTermsAndConditions(String obligationId)
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + obligationId + "/terms-and-conditions");
  }

  public static URL getTransactionAudit()
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + "transaction-audit");
  }

  public static URL getTransactionAuditAnalytics()
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + "transaction-audit-analytics/");
  }

  public static URL getEvent()
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + "event");
  }

  public static URL getPayrollloanStatus(String advisorDocument, String customerDocType,
                                         String customerDocument)
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + "api/back-office/payrollloans/" + advisorDocument + "/"
                    + customerDocType + "/" + customerDocument + "/status");
  }

  public static URL getCombosMaritalStatus()
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + ACCOUNTCUSTOMERURL + "combos/MaritalStatusOptions");
  }

  public static URL getCombosCity()
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + ACCOUNTCUSTOMERURL + "combos/CityOptions/?cityFilter=bog");
  }

  public static URL getLifeInsurance()
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + "customers/new-life-insurance");
  }

  public static URL getBindings()
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + "payer/platform/bindings");
  }

  public static URL getOfferSygnus()
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + "payer/platform/offer");
  }

  public static URL getToggles()
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + "features/all");

  }
  public static URL getUploadLink()
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + "payrollCheck/uploads");
  }

  public static URL getClientUploadLink()
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + "payrollCheck/client/status");
  }

  public static URL getPayrollCheckLink(String customerDocType, String customerDocument)
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + "credentials/client/" + customerDocType + "/"
                    + customerDocument + "/pdf");
  }

  public static URL getDocuments(String customerDocType, String customerDocument)
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + "onbase/users/" + customerDocType + "/"
                    + customerDocument + "/documents");
  }

  public static URL getPublicKey()
          throws MalformedURLException {
    return new URL(
            environment.baseCipherUrl() + "public-key");
  }

  public static URL getSimmetricKey()
          throws MalformedURLException {
    return new URL(
            environment.baseCipherUrl() + "symmetric-key");
  }

  public static URL getCashierOfficeList(String customerDocType, String customerDocument, String sector)
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + "cashier-office?documentType=" + customerDocType + "&documentId="
                    + customerDocument + "&sector=" + sector);
  }

  public static URL postOffering()
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + OFFERING);
  }

  public static URL getPayrollCheckCnc(String customerDocType, String customerDocument, String clientInfo, String payerNit,
                                       String sectorNumber, String clientType)
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + PAYROLLCHECKURL + customerDocument + "/" + clientInfo + "/" + customerDocType + "/" + customerDocument +
                    "?payer-nit=" + payerNit + "&sector-number=" + sectorNumber + "&client-type=" + clientType);
  }

  public static URL getOfferingBuro()
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + OFFERING);
  }

  public static URL getValidationPeaceAndSafe()
          throws MalformedURLException {
    return new URL(environment.baseUrl() + "peace-safe/loan/business-rules");
  }

  public static URL getValityStatus() throws  MalformedURLException{
    return new URL(environment.baseUrl() + "ocr/documents/validity-status");
  }

  public static URL getStatus() throws MalformedURLException{
    return new URL(environment.baseUrl() + "ocr/documents/status");
  }

  public static URL getLoad()throws MalformedURLException{
    return new URL ("https://64oj268ooh.execute-api.us-east-2.amazonaws.com/v1/file-provider-service/load");
  }

  public static URL getFile(String obligationID,String document) throws MalformedURLException{
    return new URL (environment.baseSecurityUrl()+"file/url/"
            +obligationID+"_CC_"+document+".pdf/file.featurePayrollCheck");
  }

  public static  URL getAge() throws  MalformedURLException{
    return new URL(environment.baseUrl() + "customer-age");
  }

  public static URL getVendors()
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + "onbase/users/documents/vendors");
  }

  public static URL getTermsAndConditions(String obligationID)
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + obligationID+"/terms-and-conditions");
  }

  public static URL getSimulation()throws MalformedURLException{
    return new URL(environment.baseUrl() + "simulation/CUSTOMER_WITH_LOANS");
  }

  public static URL getLoadDecrim() throws  MalformedURLException{
    return new URL(environment.baseSecurityUrl() +"auth/decrim");
  }

  public static URL getCreditTypeDecision() throws  MalformedURLException{
    return new URL(environment.baseUrl() +"customers/credit-type-decision");
  }

  public static URL getCashierOfficeSectors() throws  MalformedURLException{
    return new URL(environment.baseUrl() +"cashier-office/sectors");
  }

  public static URL getChoosePayrollcheck(String document,String sector) throws  MalformedURLException{
    return new URL(environment.baseUrl() +"cashier-office?documentType=CC&documentId="+document+"&sector="+sector);
  }

  public static URL getTermsConditions(String plan) throws MalformedURLException {
    return new URL("https://1ui86aw8q8.execute-api.us-east-2.amazonaws.com/stg/v1/common/terms/conditions/query?idTerms=" + plan);
  }

  public static URL getDocumentPayer() throws MalformedURLException{
    return new URL ("https://64oj268ooh.execute-api.us-east-2.amazonaws.com/v1/payroll-payer/documentsPayer");
  }

  public static URL getExternalViability() throws MalformedURLException{
    return new URL("https://crfthkvnri.execute-api.us-east-2.amazonaws.com/v1/risk/external-viabilities");
  }

  public static URL getAccountsList()
          throws MalformedURLException {
    return new URL(
            environment.baseUrl() + ACCOUNT_LIST);
  }

  public static URL getSearchRepricing() throws MalformedURLException{
    return new URL (environment.baseUrl()+"customer/campaign/repricing/search");
  }

  public static URL getCallCenter() throws MalformedURLException{
    return new URL (environment.baseUrl()+"auth/callCenter");
  }

  public static URL getExtraction() throws MalformedURLException {
    return new URL (environment.baseExtractUrl()+EXTRACTION);
  }

  public static URL getExtractionResult() throws MalformedURLException {
    return new URL (environment.baseExtractUrl()+EXTRACTION+"/result");
  }

  public static URL getSimulationCNC() throws MalformedURLException {
    return new URL (environment.baseUrl()+"simulation/CLIENTE_NUEVO_CONOCIDO");
  }

  public static URL getExternalViabilityStatus() throws MalformedURLException {
    return new URL (environment.baseUrl()+"validations/customer/external-viability");
  }

  public static URL getTokenTermCondition() throws MalformedURLException {
    return new URL ("https://gb-commons-stg.auth.us-east-2.amazoncognito.com/oauth2/token");
  }

  public static URL getSeniorityCheck() throws MalformedURLException {
    return new URL (environment.baseUrl() +"entry-date/seniority-check");
  }

  public static URL getMocks(String url) throws MalformedURLException {
    switch (url){
      case CRM:
        return new URL (environment.castlemockUrl()+"rest/project/9xvNWz/application/cVZjyO/resource/XhUj0a/method/Sj1UXP/mockresponse");

      case SIM:
        return new URL (environment.castlemockUrl()+"rest/project/rn0p4o/application/FSERDy/resource/0J36ec/method/OgMpvK/mockresponse");

      case ACTIVAS:
        return new URL (environment.castlemockUrl()+"soap/project/G6fF01/port/vOblxz/operation/PtykVu/mockresponse");

      case SIMULATION:
        return new URL (environment.castlemockUrl()+"rest/project/7NaRAR/application/MO5uA9/resource/57imOH/method/8p4afa/mockresponse");

      default:
        return null;

    }
  }

}