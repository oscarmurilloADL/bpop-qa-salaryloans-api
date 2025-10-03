package co.com.bancopopular.automation.features.steps.extractor;

import co.com.bancopopular.automation.conf.StepBase;
import co.com.bancopopular.automation.dynamodb.DynamoQueries;
import co.com.bancopopular.automation.features.steps.anyfeesimulation.AnyFeeSimulationRequests;
import co.com.bancopopular.automation.features.steps.payrollhistory.PayrollHistoryRequests;
import co.com.bancopopular.automation.features.steps.tap.LaboralCertificationRequests;
import co.com.bancopopular.automation.models.extractor.Extractor;
import co.com.bancopopular.automation.models.extractor.ItemDynamoExtraction;
import co.com.bancopopular.automation.utils.AwsUtils;
import co.com.bancopopular.automation.utils.DataUserInstance;
import co.com.bancopopular.automation.utils.RequestsUtil;
import co.com.bancopopular.automation.utils.SessionHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.SneakyThrows;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

import static co.com.bancopopular.automation.constants.Constants.*;
import static co.com.bancopopular.automation.utils.RequestsUtil.pause;
import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static co.com.bancopopular.automation.utils.SessionHelper.setInSession;

import com.google.gson.GsonBuilder;


public class ExtractorStepDefinition extends StepBase {
    private static final String EXTRACT_LOG ="/aws/lambda/gb-salaryloans-stg-payroll-extraction";
    private static final String INTERPRETER_LOG ="/aws/lambda/gb-salaryloans-stg-payroll-interpreter";
    private static final String SIMULATION="/aws/lambda/gb-salaryloans-stg-simulator-v5";
    private static final String EXTRACTOR_PROCESS="EXTRACTOR_PROCESS";
    private static final String INTERPRETER_PROCESS="INTERPRETER_PROCESS";
    private static final String EXTRACTORRES="EXTRACTORRES";
    private static final String INTERPRETERRES="INTERPRETERRES";
    private static  final String NEW_PAYROLL_LOAN="NEW_PAYROLL_LOAN";
    private static final String CONSORCIO_FOPEP="CONSORCIO FOPEP 2022";
    private static final String MAGISTERIO="FIDUPREVISORA FONDO PENSIONES MAGISTERIO";
    private static final String NACIONALES="FONDO PASIVO FERROCARRILES NACIONALES";
    private static final String POSITIVA_SEGUROS="POSITIVA COMPAÑÍA DE SEGUROS S.A. PENSIONADOS";

    ExtractAssertions extractAssertions = new ExtractAssertions();
    RequestsUtil requestsUtil = new RequestsUtil();
    AwsUtils awsUtils = new AwsUtils();
    ExtractorRequest extractorRequest = new ExtractorRequest();
    ObjectMapper objectMapper = new ObjectMapper();
    LaboralCertificationRequests laboralCertificationRequests = new LaboralCertificationRequests();
    AnyFeeSimulationRequests anyFeeSimulationRequests = new AnyFeeSimulationRequests();
    PayrollHistoryRequests payrollHistoryRequests = new PayrollHistoryRequests();
    Gson gson= new GsonBuilder().setPrettyPrinting().create();
    DynamoQueries dynamoQueries = new DynamoQueries();


    @SneakyThrows
    @When("el realiza el proceso de extractor e interpretador de desprendibles para {}")
    public void consumeExtractorInterpreterServices(String typeCredit) {
        setInSession(SessionHelper.SessionData.CREDIT_TYPE, typeCredit);
        var data = DataUserInstance.getInstance().getData();
        RequestsUtil.pause(10);
        var otpJson = requestsUtil.createAuthOTP();
        laboralCertificationRequests.getCreditTypeDecision(data.get(CUSTOMER_DOCUMENT)
                .getAsString(), otpJson.getString(TOKEN), typeCredit);
        if(typeCredit.equals(NEW_PAYROLL_LOAN)) {
            laboralCertificationRequests.getSector(otpJson.getString(TOKEN));
            laboralCertificationRequests.getChoosePayrollcheck(data.get(CUSTOMER_DOCUMENT)
                    .getAsString(), otpJson.getString(TOKEN), data.get(SECTOR).getAsString());
        }else{
            payrollHistoryRequests.getPayrollStatus(data.get(CUSTOMER_DOC_TYPE).getAsString(),
                    data.get(CUSTOMER_DOCUMENT).getAsString(), otpJson.getString(TOKEN));
        }
        var bodyRequestExtractor =objectMapper.readValue(data.get("bodyRequestExtractor").toString(), new TypeReference<Extractor>() {});
        extractorRequest.consumeExtractorInterpreterServices(bodyRequestExtractor,otpJson.getString(TOKEN));
    }


    @When("el consulta el nit homologado para {}")
    public void validateHomologatedNIT(String payment){
        var homologatedLog="";
        homologatedLog= searchHomologatedLog(payment,homologatedLog);
        extractAssertions.showHomologatedNIT(payment,homologatedLog);
    }

    @When("el consulta los datos enviados a interpretador para {}")
    public void validateRequestForInterpreter(String payment){
        var requestInterpreterLog="";
        requestInterpreterLog = searchRequestInterpretadorLog(INTERPRETER_LOG);
        extractAssertions.showInterpreterRequest(payment,requestInterpreterLog);
    }

    @When("el consulta el evento {} y se espera en estado {}")
    public void validateExtractorAndInterpreterEvents(String event, String value) {
        var eventLog="";
        var data = DataUserInstance.getInstance().getData();
        Map<String, Object> query = buildBaseQuery(event,
                data.get(CUSTOMER_DOCUMENT).getAsString(),
                getFromSession(SessionHelper.SessionData.EXECUTE_ID).toString());
        eventLog = searchEventExtractLog(event,query);
        var json = gson.fromJson(requestsUtil.extractJson(eventLog), Object.class);
        RequestsUtil.addLogInSerenityReport("Evento obtenido: "+event,
                new GsonBuilder().setPrettyPrinting().create().toJson(json));
        extractAssertions.showEvents(requestsUtil.extractJson(eventLog), value, data,query);
    }

    @When("el consulta en base de datos la informacion de interpretador")
    public void validateDateConsistency() throws IOException {
        var itemExtract = queryExtractBase();
        var data = DataUserInstance.getInstance().getData();
        setInSession(SessionHelper.SessionData.DYNAMO_SIMULATION, itemExtract);
        var dataForValidate = jsonExtractForValidate(data);
        extractAssertions.validateDataInExtractorTable(itemExtract,dataForValidate);
        extractAssertions.validateExtractResult(itemExtract,dataForValidate);
        extractAssertions.validateInterpreterResult(itemExtract,dataForValidate);
    }
    @When("el consulta los datos en la primer simulacion")
    public void validateSimulationRequest() throws JsonProcessingException {
        var data = DataUserInstance.getInstance().getData();
        var dataForValidate =objectMapper.readValue(data.get("dynamoJsonExtract").toString(),
                new TypeReference<ItemDynamoExtraction>() {});
        var simulationRequestLog= awsUtils
                .showLogsInAws(SIMULATION,patterSimulation(dataForValidate));
        anyFeeSimulationRequests.validateSimulationRequest(simulationRequestLog,dataForValidate);
    }

    @When("se ajusta la fecha de la base de desprendibles banco por {}")
    public void updateDateDataBaseExtractor(String date){
        awsUtils.updateDateExtractorLambda(date);
    }

    @When("se ajusta la fecha de la lambda de extractor en {}")
    public void updateDateInExtractorLambda(String date){
        awsUtils.updateDateExtractorLambda(date);
    }

    @Then("el valida la respuesta para extractor e interpretador de desprendibles")
    public void validateExtractorResponse(){
        assertionsUtil.shouldSeeSuccessfulStatusCode();
        extractAssertions.validateExtractServiceStatus();
    }

    @Then("el valida la respuesta para extractor cuando su desprendible no esta vigente")
    public void validateResponseWithoutProcessExtraction(){
        assertionsUtil.shouldSeeSuccessfulStatusCode();
        extractAssertions.validateServiceStatusWithoutExtraction();
    }

    private String patterSimulation(ItemDynamoExtraction data) {
        return "\"ifx:IdentSerialNum>"+data.getClientDocumentNumber()+"\"";
    }

    private Map<String, Object> buildBaseQuery(String event, String document, String extractionId) {
        Map<String, Object> query = new HashMap<>();
        query.put(CUSTOMER_DOCUMENT, document);
        query.put(EVENT_NAME, event);
        query.put(EXTRACTION_ID, extractionId);
        return query;
    }
    private String fetchEventLog(String logType, Map<String, Object> query, boolean flag) {
        return awsUtils.showEventsInAws(
                logType,
                flag ? awsUtils.dataQuery(query) : awsUtils.dataCommonQuery(query)
        );
    }

    private String searchHomologatedLog(String payment, String homologatedLog) {
        pause(20);
        switch (payment){
            case COLPENSIONES:
                homologatedLog = awsUtils
                        .showLogsInAws(EXTRACT_LOG,"El nit 9003360047 tiene un valor homologado en la tabla payers 9003360047");
                break;

            case FOPEP:
                homologatedLog = awsUtils
                        .showLogsInAws(EXTRACT_LOG,"El nit 9016596505 tiene un valor homologado");
                break;

            case POSITIVA:
                homologatedLog = awsUtils
                        .showLogsInAws(EXTRACT_LOG,"El nit 8600111536 tiene un valor homologado");
                break;

            case FERROCARRILES:
                homologatedLog = awsUtils
                        .showLogsInAws(EXTRACT_LOG,"El nit 8001128062 tiene un valor homologado");
                break;

            case FIDUPREVISORA:
                homologatedLog = awsUtils
                        .showLogsInAws(EXTRACT_LOG,"El nit 8605251485 tiene un valor homologado");
                break;
            default:
                break;
        }
        return  homologatedLog;
    }

    private String searchEventExtractLog(String event, Map<String, Object> query) {
        var eventLog="";
        switch (event) {
            case EXTRACTOR_PROCESS:
                query.put(EVENT_CODE, "14001");
                query.put(EVENT_MNEMONIC, EXTRACTORRES);
                eventLog = fetchEventLog(EXTRACT_LOG, query,true);
                break;
            case INTERPRETER_PROCESS:
                query.put(EVENT_CODE, "14002");
                query.put(EVENT_MNEMONIC, INTERPRETERRES);
                eventLog = fetchEventLog(INTERPRETER_LOG, query,true);
                break;
            default:
                throw new IllegalArgumentException("Unsupported event: " + event);
        }
        return eventLog;
    }
    private ItemDynamoExtraction jsonExtractForValidate(JsonObject data) throws JsonProcessingException {
        var dataForValidate =objectMapper.readValue(data.get("dynamoJsonExtract").toString(),
                new TypeReference<ItemDynamoExtraction>() {});
        dataForValidate.setProcessDate(getFromSession(SessionHelper.SessionData.NOW));
        return dataForValidate;
    }
    private ItemDynamoExtraction queryExtractBase() throws JsonProcessingException {
        var data = DataUserInstance.getInstance().getData();
        var dataTableExtract = dynamoQueries.queryExtractTable(data,false);
        return objectMapper.readValue(jsonExtractValidation(dataTableExtract,data), new TypeReference<>() {
        });
    }
    private String jsonExtractValidation(String json, JsonObject data) {
        json = json.replaceAll("([,{\\[\\s])(\\w+)(?=\\s*=)", "$"+ONE+"\"$"+TWO+"\"");
        json = json.replace("=", ":");
        json = json.replaceAll("(?<=:\\s*)([A-Za-z0-9_\\-]+)(?=[,}\\]])", "\"$"+ONE+"\"");
        json = json.replaceAll("("+REGEX+"{"+FOUR+"}-"+REGEX+"{"+TWO+"}-"+REGEX+"{"+TWO+"}T"+REGEX+"{"+TWO+"}:"+REGEX+"{"+TWO+"}:"+REGEX+"{"+TWO+"}\\."+REGEX+"{3}Z)", "\"$"+ONE+"\"");
        json = json.replaceAll("("+REGEX+"{"+FOUR+"}-"+REGEX+"{"+TWO+"}-"+REGEX+"{"+TWO+"}T"+REGEX+"{"+TWO+"}:"+REGEX+"{"+TWO+"}:"+REGEX+"{"+TWO+"}Z)", "\"$"+ONE+"\"");
        json = json.replaceAll("([A-Za-z]{"+TWO+",}(?: [A-Za-z]{"+TWO+",})+ "+REGEX+"{"+ONE+","+TWO+"} "+REGEX+"{"+TWO+"}:"+REGEX+"{"+TWO+"}:"+REGEX+"{"+TWO+"} COT "+REGEX+"{"+FOUR+"})", "\"$"+ONE+"\"");
        json = replaceFieldValues(json, "statusDescription");
        json = replaceFieldValues(json, "allowanceDate");
        json = replaceFieldValues(json, "membershipId");
        json = replaceFieldValues(json, "conceptName");
        json = replaceFieldValues(json, "clientName");
        json = replaceFieldValues(json, "statusDesc");
        json = replaceFieldValues(json, "serverStatusCode");
        json= json.substring(json.indexOf("{\"Item\":") + 8, json.lastIndexOf("}") );
        json= adjustJsonPaymentdata(data.get(CUSTOMER_DOCUMENT).getAsString(),json);
        RequestsUtil.addLogInSerenityReport("Dynamo Extract Data", json);
        return json;
    }

    private String adjustJsonPaymentdata(String document, String json) {
        var jsonObject = new JSONObject(json);
        switch (document) {
            case "500190":
                jsonObject.put(PAYER_NAME,CONSORCIO_FOPEP);
                break;
            case "500191":
                jsonObject.put(PAYER_NAME,MAGISTERIO);
                break;
            case "500192":
                jsonObject.put(PAYER_NAME,NACIONALES);
                break;
            case "500193":
                jsonObject.put(PAYER_NAME,POSITIVA_SEGUROS);
                break;
            default:
                break;
        }
        return jsonObject.toString();
    }

    private String replaceFieldValues(String json, String fieldName) {
        var regex = "(?<=\"" + fieldName + "\":)([^,}]+)(?=,|})";
        return json.replaceAll(regex, "\"$"+ONE+"\"");
    }

    private String  searchRequestInterpretadorLog(String groupLog) {
        var data = DataUserInstance.getInstance().getData();
        Map<String, Object> query = new HashMap<>();
        query.put(REQUEST,INTERPRETER_REQUEST);
        query.put(CUSTOMER_DOCUMENT,data.get(CUSTOMER_DOCUMENT).getAsString());
        return fetchEventLog(groupLog,query,false);
    }

}
