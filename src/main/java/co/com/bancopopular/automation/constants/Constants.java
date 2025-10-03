package co.com.bancopopular.automation.constants;

import co.com.bancopopular.automation.utils.RequestsUtil;


public class Constants {

    private Constants() {
    }

    public static final String VALUE_PAYER = "valuePayer" ;
    public static final String DATA_NOT_FOUND = "Data no encontrada";
    public static final String TRUE ="true" ;
    public static final String FAIL_SIMULATION = "FAIL_SIMULATION";
    public static final String SIMULATION_ERROR_CODE ="simulationErrorCode" ;
    public static final String SIMULATION_ERROR_MSG="simulationErrorMessage";
    public static final String VALIDATION_STATUS="validationStatus";
    public static final String USER_ADMIN_SALARYLOANS = "user_admin_salaryloans";
    public static final String PSS_ADMIN_SALARYLOANS = "pss_admin_salaryloans";
    public static final String END_POINT_ADMIN_CONSULT_SALE = "v1/salaryloans-core/api/back-office/renewals?pageNumber=%d";
    public static final String END_POINT_REQUEST_SALARY_LOANS = "v1/salaryloans-core/api/back-office/payrollloans/";
    public static final String END_POINT_GET_FILE_FEATURE_DECRIM = "/security-manager/file/url/CC_400059_%s.jpg/file.featureDecrim";
    public static final String END_POINT_POST_AUTH_DECRIM = "/security-manager/auth/decrim";
    public static final String SALARYLOANS_LOG ="ecs-gb-salaryloans-stg-salaryloans-core";
    public static  final String SEARCH_MDM="consulta";
    public static final String CUSTOMERDOCUMENT = "customerDocument";
    public static final String AUTHORIZATION = "Authorization";
    public static final String ADVISOR_JOURNEY_ID = "advisor_journey_id";
    public static final String END_POINT_SIM_VALIDATION_SUCCES = "/v1/salaryloans-core/auth/phoneNumber";
    public static final String NUM_OBLIGATION_ID = "num_obligation_id";
    public static final String FIELD_OBLIGATION_ID = "obligationId";
    public static final String FECHA_Y_HORA_VENTA = "fecha_y_hora_venta";
    public static final String RQ_ID_GENERATED = "rq_id_generated";
    public static final String PORT="3306";
    public static final String HOST="gb-stg-prestaya-digital.cziqxrvqp4eq.us-east-2.rds.amazonaws.com";
    public static final String PASS="bpopdigital01.";
    public static final String DATABASE="parameters";
    public static final String USER="master";
    public static final String ACTOR_DECRIM = "actor_decrim";
    public static final String HOST_DECRIM_SIGNATURE = "lvx0wk9q6c.execute-api.us-east-2.amazonaws.com";
    public static final String HOST_HEADER = "host";
    public static final String CONTENT_TYPE_DECRIM_VALUE = "application/json; charset=UTF-8";
    public static final String CONTENT_TYPE = "content-type";
    public static final String X_AMZ_DATE_MAP_HEADERS = "x-amz-date";
    public static final String FORMAT_DATE_TO_SIGNATURE_DECRIM = "yyyyMMdd'T'HHmmss'Z'";
    public static final String TIME_ZONE = "UTC";
    public static final String BODY_RESPONSE_CREATE_CASE = "body_response_create_case";
    public static final String BODY_RESPONSE_VALIDATE_DECRIM = "body_response_validate_decrim";
    public static final String SECURITY_TOKEN= "X-Amz-Security-Token";
    public static final String DATE = "X-Amz-Date";
    public static final RequestsUtil REQUESTS_UTIL = new RequestsUtil();
    public static final String DATA_RESPONSE_CREATE_CASE_DECRIM_TO_VALIDATE = "response_create_case_decrim";
    public static final String DATA_RESPONSE_DECRIM_TO_VALIDATE = "response_validate_decrim";
    public static final String CRM = "CRM";

    public static final String ACTIVAS="ACTIVAS";
    public static final String SIM = "SIM";
    public static final String MDM = "MDM";
    public static final String SIMULATION="SIMULATION";
    public static final String NAME="name";
    public static final String ACCEPT_TERMS_AND_CONDITIONS="ACCEPT_TERMS_AND_CONDITIONS";
    public static final String VALITY_STATUS="valityStatus";
    public static final String STATUS="status";
    public static final String PAYER_NIT="payerNit";
    public static final String SECTOR_NUMBER="sectorNumber";

    public static final String SUB_SECTOR_NUMBER="subSectorNumber";
    public static final String ID_TAP = "idTap";
    public static final String CERO_POSITION="[0].";
    public static final String DOCUMENT_TYPE="documentType";
    public static final String OFFICE_ID="officeId";
    public static final String CUSTOMER_DOCUMENT="customerDocument";
    public static final String INTERPRETER_REQUEST="AWSSoapEnvelopeLoggingInterceptor - Security lib Request";
    public static final String REQUEST = "REQUEST";
    public static final String MDM_DWL_CONTROL="MdmDwlControl";
    public static final String GET_CUSTOMER_DATA_UPDATE="getCustomerDataUpdate";
    public static final String OFFICE_NAME="officeName";
    public static final String OTP_VALUE="otpValue";
    public static final String RECAPTCHA_RESPONSE="recaptchaResponse";
    public static final String DOCUMENT_NUMBER="documentNumber";
    public static final String ADVISOR_DECISION_TYPE="advisorDecisionType";
    public static final String ADVISOR_DOCUMENT_NUMBER="advisorDocumentNumber";
    public static final String ADVISOR_DOCUMENT="advisorDocument";
    public static final String CUSTOMER_DOC_TYPE="customerDocType";
    public static final String OFFICE_CODE="officeCode";
    public static final String DUMMY_TEST="dummy_test";
    public static final String DIGITVALUE="123456";
    public static final String TOKEN="token";
    public static final String SECTOR="sector";
    public static final String COLPENSIONES="COLPENSIONES";
    public static final String FOPEP="FOPEP";
    public static final String FIDUPREVISORA="FIDUPREVISORA";
    public static final String FERROCARRILES="FERROCARRILES";
    public static final String POSITIVA="POSITIVA";
    public static final String EVENT_NAME ="eventName";
    public static final String EVENT_CODE ="eventCode";
    public static final String EVENT_MNEMONIC="eventMnemonic";
    public static final String EXTRACTION_ID="extractionId";
    public static final String ONE="1";
    public static final String TWO="2";
    public static final String FOUR="4";
    public static final int FIVE = 5;
    public static final String CERO = "0";
    public static final String REGEX="\\d";
    public static final String NUMBER_PATTERN="^\\d+$";
    public static final String PAYER_NAME="payerName";

    public static final String OBLIGATION_ID="obligationId";
    public static final String RESULT_LOG=": Actual %s | Esperado %s ";
    public static final String DATE_LOG=": Valor actual %s Contiene  %s ";
    public static final String PATTERN_LOG=": %s tiene el patron %s ";
    public static final String EXTRACTOR_LAMBDA = "gb-salaryloans-stg-payroll-extraction";
    public static final String DATE_TEST = "DATE_TEST";
    public static final int MAX_ATTEMPTS=15;
    public static final String NEW_PAYROLL_LOAN ="NEW_PAYROLL_LOAN";

    public static final String CLIENT_INFO="clientInfo";

    public static final String CLIENT_TYPE="clientType";

    public static final String CONDITIONS_TOKEN ="conditionsToken";
    public static final String GROWTH_AMOUNT_002="GrowthAmount002";
    public static final String GROWTH_AMOUNT_002_DESC="Offer Doesnt Reach Minimum Growth Amount Policy";


}
