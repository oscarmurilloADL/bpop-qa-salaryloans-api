package co.com.bancopopular.automation.utils;

import java.util.ArrayList;
import java.util.List;
import net.serenitybdd.core.Serenity;

public class SessionHelper {
  public static <T> T getFromSession(SessionData key) {
    return Serenity.sessionVariableCalled(key.value);
  }

  public static <T> void setInSession(SessionData key, T value) {
    Serenity.setSessionVariable(key.value).to(value);
  }

  public static <T> void addValueToListInTheSessionData(SessionData key, T value) {
    List<T> currentValues = getFromSession(key);
    if (currentValues == null) {
      currentValues = new ArrayList<>();
    }
    currentValues.add(value);

    Serenity.setSessionVariable(key.value).to(currentValues);
  }

  public static Boolean thereIsASessionVariableCalled(SessionData key) {
    return Serenity.hasASessionVariableCalled(key.value);
  }

  public static void updateSessionRequestData(String url, String method) {
    Serenity.setSessionVariable("lastURL").to(url);
    Serenity.setSessionVariable("lastMethod").to(method);
  }

  public enum SessionData {
    XSYMMETRICKEY("xSymmetricKey"),
    XSYMMETRICIV("xSymmetricIv"),
    XSECURITYSESSION("xSecuritySession"),
    XSECURITYREQUESTID("xSecurityRequestId"),
    PAYROLL_CHECK_PROCESS_ID("payrollCheckProcessId"),
    REQUEST_ID("requestId"),
    TOKEN("token"),
    RENEWAL_ID("renewalId"),
    OBLIGATION_ID("obligationId"),
    CUSTOMER_ID("customerId"),
    BINDING_NUMBER("bindingNumber"),
    OPTIMIZATION_TOGGLE_II("optimizationToggleII"),
    JOURNEY_ID("journeyId"),
    ACCESS_KEY("accessKey"),
    SECRET_KEY("secretKey"),
    SESSION_TOKEN("sessionToken"),
    PROCESS_DATA("process"),
    PLAN("plan"),
    TOGGLE_SINGLE_PROMISSORY("singlePromissory"),
    OWNERSHIP_REFACTOR_TOGGLE("ownershipRefactor"),
    PAYROLL_STATUS("payrollStatus"),
    PAYSTUBS_SYGNUS_REQUIRED("paystubsSygnusRequired"),

    EXTRACTOR("extractor"),

    EXCEL_PAYROLLS("excelPayrolls"),

    MYSQL_PAYROLLS("mysqlPayrolls"),

    RISK_EMGINE("riskEngine"),

    EXECUTE_ID("executeId"),
    JSON_DATE_DAY("jsonDateDay"),
    NOW("now"),
    EXTRACTOR_CODE("extractorCode"),
    EXTRACTOR_STATUS("extractorStatus"),
    DYNAMO_SIMULATION("dynamoSimulation"),
    CREDIT_TYPE("creditType");


    private String value;

    SessionData(String value) {
      this.value = value;
    }
  }
}
