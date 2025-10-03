package co.com.bancopopular.automation.utils;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.Environment;
import com.amazonaws.services.lambda.model.GetFunctionConfigurationRequest;
import com.amazonaws.services.lambda.model.GetFunctionConfigurationResult;
import com.amazonaws.services.lambda.model.UpdateFunctionConfigurationRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatchlogs.CloudWatchLogsClient;
import software.amazon.awssdk.services.cloudwatchlogs.model.*;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static co.com.bancopopular.automation.constants.Constants.*;
import static co.com.bancopopular.automation.constants.Constants.INTERPRETER_REQUEST;
import static co.com.bancopopular.automation.utils.RequestsUtil.pause;


public class AwsUtils {

    private static final Logger LOGGER = LogManager.getLogger(AwsUtils.class);
    private final CloudWatchLogsClient logsClient = initCredentialsAwsLogs();

    private static final String AWS_ACCESS_KEY=System.getProperty("AwsAccessKey");
    private static final String AWS_SECRET_ACCESS_KEY=System.getProperty("AwsSecretAccessKey");
    private static final String AWS_SESSION_TOKEN=System.getProperty("AwsSessionToken");

    private static final  BasicSessionCredentials basicSessionCredentials = new BasicSessionCredentials(
            AWS_ACCESS_KEY,
            AWS_SECRET_ACCESS_KEY,
            AWS_SESSION_TOKEN
    );
    private static final AwsSessionCredentials awsSessionCredentials = AwsSessionCredentials.create(
            AWS_ACCESS_KEY,
            AWS_SECRET_ACCESS_KEY,
            AWS_SESSION_TOKEN
    );

    public String showLogsInAws(String logGroupName, String filterPattern) {
        var currentTimeMillis = Instant.now().toEpochMilli();
        var fiveMinutesAgoMillis = currentTimeMillis - (5 * 60 * 1000);
        try {
            var request = FilterLogEventsRequest.builder()
                    .logGroupName(logGroupName)
                    .filterPattern(filterPattern)
                    .startTime(fiveMinutesAgoMillis)
                    .endTime(currentTimeMillis)
                    .build();

            var response = logsClient.filterLogEvents(request);

            if (response.events().isEmpty()) {
                return null;
            } else {
                return response.events().get(0).message();
            }
        } catch (CloudWatchLogsException e) {
            LOGGER.log(Level.ERROR, e.awsErrorDetails().errorMessage());
            return e.awsErrorDetails().errorMessage();
        }
    }

    public String showEventsInAws(String logGroupName, String query) {
        GetQueryResultsResponse getQueryResultsResponse;

        do {
            var startQueryRequest = StartQueryRequest.builder()
                    .logGroupName(logGroupName)
                    .startTime(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(5))
                    .endTime(System.currentTimeMillis())
                    .queryString(query)
                    .build();
            var startQueryResponse = logsClient.startQuery(startQueryRequest);
            var queryId = startQueryResponse.queryId();
            pause(10);
            var getQueryResultsRequest = GetQueryResultsRequest.builder()
                    .queryId(queryId)
                    .build();
            getQueryResultsResponse = logsClient.getQueryResults(getQueryResultsRequest);
        } while (getQueryResultsResponse.results().isEmpty());

        return getQueryResultsResponse.results().get(0).toString();
    }

    public void updateDateExtractorLambda(String newDate){
        var lambdaClient = initLambdaClient();
        try {
            GetFunctionConfigurationRequest getRequest = new GetFunctionConfigurationRequest()
                    .withFunctionName(EXTRACTOR_LAMBDA);
            GetFunctionConfigurationResult currentConfig = lambdaClient.getFunctionConfiguration(getRequest);
            Map<String, String> updatedEnvironmentVariables = currentConfig.getEnvironment().getVariables();
            updatedEnvironmentVariables.put(DATE_TEST, newDate);
            UpdateFunctionConfigurationRequest updateRequest = new UpdateFunctionConfigurationRequest()
                    .withFunctionName(EXTRACTOR_LAMBDA)
                    .withEnvironment(new Environment().withVariables(updatedEnvironmentVariables));
            lambdaClient.updateFunctionConfiguration(updateRequest);
            LOGGER.log(Level.INFO, "Fecha actualizada en la lambda");
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error actualizando la lambda");
        }
    }

    public  CloudWatchLogsClient initCredentialsAwsLogs(){
        return CloudWatchLogsClient.builder()
                .region(Region.US_EAST_2)
                .credentialsProvider(StaticCredentialsProvider.create(awsSessionCredentials))
                .build();
    }

    public AWSLambda initLambdaClient() {
        return AWSLambdaClientBuilder.standard()
                .withRegion(Region.US_EAST_2.toString())
                .withCredentials(new AWSStaticCredentialsProvider(basicSessionCredentials))
                .build();
    }
    public AmazonDynamoDB initClientDynamo() {
        var credentialsProvider = new AWSStaticCredentialsProvider(basicSessionCredentials);
        return  AmazonDynamoDBClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withRegion(Regions.US_EAST_2)
                .build();
    }

    public String dataCommonQuery(Map<String, Object> dataQuery) {
        return "fields @timestamp, @message "
                + "| filter @message like \"" + escapeQuotes((String) dataQuery.get(CUSTOMER_DOCUMENT)) + "\" "
                + "and @message like \"" + escapeQuotes((String) dataQuery.get(REQUEST)) + "\" "
                + "| sort @timestamp desc "
                + "| limit 10000";
    }

    public String dataQuery(Map<String, Object> dataQuery) {
        return "fields @timestamp, @message "
                + "| filter eventName = \"" + escapeQuotes((String) dataQuery.get(EVENT_NAME)) + "\" "
                + "and eventCode = \"" + escapeQuotes((String) dataQuery.get(EVENT_CODE)) + "\" "
                + "and userIdentification = \"" + escapeQuotes((String) dataQuery.get(CUSTOMER_DOCUMENT)) + "\" "
                + "and eventMnemonic = \"" + escapeQuotes((String) dataQuery.get(EVENT_MNEMONIC)) + "\" "
                + "| parse detail '\"extractionId\":\"*\"' as extractionId "
                + " | filter extractionId = \"" + escapeQuotes((String) dataQuery.get(EXTRACTION_ID)) + "\" "
                + "| sort @timestamp desc "
                + "| limit 20";
    }

    private static String escapeQuotes(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\"", "\\\"");
    }
}