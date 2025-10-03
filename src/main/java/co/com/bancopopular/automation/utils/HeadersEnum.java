package co.com.bancopopular.automation.utils;


public enum HeadersEnum {
  AUTHORIZATION ("Authorization"),
  HEADER_X_SECURITY_SESSION ("X-SECURITY-SESSION"),
  HEADER_X_SECURITY_HMAC ("X-SECURITY-HMAC"),
  HEADER_X_SECURITY_REQUEST_ID("X-SECURITY-REQUEST-ID"),
  HEADER_X_RQUID("X-RqUID"),
  HEADER_X_CHANNEL("X-Channel"),
  HEADER_X_COMPANY_ID("X-CompanyId"),
  HEADER_X_IDENT_SERIAL_NUM("X-IdentSerialNum"),
  HEADER_X_GOV_ISSUE_IDENT_TYPE("X-GovIssueIdentType"),
  HEADER_X_IPA_DDR("X-IPAddr"),
  HEADER_X_LANGUAGE("X-Language"),
  HEADER_X_NAME("X-Name"),
  HEADER_X_CLIENT_DT("X-ClientDt"),
  HEADER_X_LEGAL_NAME("X-LegalName"),
  HEADER_X_VERSION("X-Version"),
  HEADER_X_ACCEPT("Accept");

  private final String text;

  private HeadersEnum(String value) {
    text = value;
  }

  @Override
  public String toString(){
    return this.text;
  }
}
