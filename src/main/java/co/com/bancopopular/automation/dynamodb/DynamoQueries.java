package co.com.bancopopular.automation.dynamodb;

import co.com.bancopopular.automation.utils.AwsUtils;
import co.com.bancopopular.automation.utils.RequestsUtil;
import co.com.bancopopular.automation.utils.SessionHelper;
import com.amazonaws.services.dynamodbv2.document .*;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static co.com.bancopopular.automation.utils.SessionHelper.setInSession;

public class DynamoQueries {
    private static final Logger logger = Logger.getLogger(DynamoQueries.class.getName());
    private static final String TABLE_REPRICING = "gb-salaryloans-stg-repricing-campaign";
    private static final String EXTRACT_TABLE="gb-salaryloans-stg-payrollcheck_extract";
    private static final String FIELD_NAME_REPRICING = "client_id_document";
    private static final String FIELD_NAME_EXTRACT = "extract_interpreter_sort";
    private static  final String EXTRACT_INTERPRETER_ID="extract_interpreter_id";
    private static final String FIELD_PROCESS_DATE ="processDate";
    private final AwsUtils awsUtils = new AwsUtils();

    public List<Object> queryRepricingTable(String customerDocument) {

        List<Object> results = new ArrayList<>();
        var dynamoDB = new DynamoDB(awsUtils.initClientDynamo());
        var table = dynamoDB.getTable(TABLE_REPRICING);
        try {
            var filter = new ScanFilter(FIELD_NAME_REPRICING).contains(customerDocument);
            var scanSpec = new ScanSpec().withScanFilters(filter);
            ItemCollection<ScanOutcome> items = table.scan(scanSpec);
            Iterator<Item> iterator = items.iterator();
            if (!iterator.hasNext()) {
                logger.log(Level.INFO, "Cliente sin registros en base Reprecio.");
            }
            while (iterator.hasNext()) {
                var item = iterator.next();
                RequestsUtil.addLogInSerenityReport("Reprecio","Cliente con registros en base Reprecio");
                results.add(item.toString());
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error al consultar base Reprecio X");
        }
        return results;
    }

    public String queryExtractTable(JsonObject data, boolean delete) {
        var dynamoDB = new DynamoDB(awsUtils.initClientDynamo());
        var table = dynamoDB.getTable(EXTRACT_TABLE);
        List<Object> results = new ArrayList<>();
        ScanSpec scanSpec;
        try {
            var filter = new ScanFilter(FIELD_NAME_EXTRACT)
                    .contains(data.get("extractInterpreterSort").getAsString());
            var filterID = new ScanFilter(EXTRACT_INTERPRETER_ID)
                    .contains(getFromSession(SessionHelper.SessionData.EXECUTE_ID));

           if(delete) {
               scanSpec = new ScanSpec().withScanFilters(filter);
           }else{
               var filterDate = new ScanFilter(FIELD_PROCESS_DATE)
                       .contains(processDate());
               scanSpec = new ScanSpec().withScanFilters(filter,filterDate,filterID);
           }

            ItemCollection<ScanOutcome> items = table.scan(scanSpec);
            Iterator<Item> iterator = items.iterator();
            if (!iterator.hasNext()) {
                logger.log(Level.INFO, "Cliente sin registros en base extractor.");
            }
            while (iterator.hasNext()) {
                var item = iterator.next();
                results.add(item.toString());
                if(delete){
                    var primaryKeyValue = item.getString(EXTRACT_INTERPRETER_ID);
                    var sortKeyValue = item.getString(FIELD_NAME_EXTRACT);
                    table.deleteItem(EXTRACT_INTERPRETER_ID,primaryKeyValue,FIELD_NAME_EXTRACT,sortKeyValue);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar base extractor",e);
        }
        return results.isEmpty() ? null : results.get(0).toString();
    }

    public void insertInExtractTable(String document) {
        var filePath="src/test/resources/files/extractor/extractor_"+document+".txt";
        var gson = new Gson();
        var dynamoDB = new DynamoDB(awsUtils.initClientDynamo());
        try(var bufferedReader = new BufferedReader((new FileReader(filePath)))){
            var table = dynamoDB.getTable(EXTRACT_TABLE);
            String line;
            while((line = bufferedReader.readLine())!= null) {
                var mapType = new TypeToken<HashMap<String, Object>>() {}.getType();
                Map<String, Object> itemMap = gson.fromJson(line, mapType);
                var item = new Item();
                for (Map.Entry<String, Object> entry : itemMap.entrySet()) {
                    item.with(entry.getKey(), entry.getValue());
                }
                table.putItem(item);
                logger.log(Level.INFO, "Registro insertado: ");
            }
        }catch (Exception e){
            RequestsUtil.addLogInSerenityReport("Error al insertar datos en extractor",e.getMessage());
        }
        RequestsUtil.pause(30);
    }

    private String processDate() {
        var processDate= getFromSession(SessionHelper.SessionData.JSON_DATE_DAY);
        var originalFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        var dateTime = ZonedDateTime.parse((CharSequence) processDate, originalFormatter);
        var targetFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH",Locale.ENGLISH);
        var date = dateTime.format(targetFormatter).replace(".","");
        setInSession(SessionHelper.SessionData.NOW,date);
        logger.log(Level.INFO, "Fecha capturada para filtrar en dynamo: "+date);
        return date;
    }

}