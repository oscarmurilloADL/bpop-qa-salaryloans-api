package co.com.bancopopular.automation.features.steps.extractor;

import co.com.bancopopular.automation.models.extractor.Extractor;
import co.com.bancopopular.automation.rest.apis.ServicePaths;
import co.com.bancopopular.automation.utils.AssertionsUtil;
import co.com.bancopopular.automation.utils.HeadersEnum;
import co.com.bancopopular.automation.utils.SessionHelper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.annotations.Steps;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.UUID;
import java.net.URL;
import static co.com.bancopopular.automation.constants.Constants.FIVE;
import static co.com.bancopopular.automation.utils.RequestsUtil.pause;
import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static co.com.bancopopular.automation.utils.SessionHelper.setInSession;

public class ExtractorRequest {

    @Steps
    public AssertionsUtil assertionsUtil;

    private static final String RUNNING_STATUS = "RUNNING";
    private static final String STATUS_CODE_KEY = "statusCode";
    private static final int MAX_ATTEMPTS=20;

    public void consumeExtractorInterpreterServices(Extractor bodyRequestExtractor, String token) throws MalformedURLException {
        bodyRequestExtractor.getData().setJourneyId(getFromSession(SessionHelper.SessionData.JOURNEY_ID));
        Map<String, Object> bodyResult =initialConsumeResultExtraction(bodyRequestExtractor);
        getExtract(bodyRequestExtractor,token,bodyResult);
        pause(10);
        setInSession(SessionHelper.SessionData.EXTRACTOR_CODE,
                SerenityRest.then().extract().jsonPath().get(STATUS_CODE_KEY));
        setInSession(SessionHelper.SessionData.EXTRACTOR_STATUS,
                SerenityRest.then().extract().jsonPath().get("response.processStatus"));
    }

    public Map<String, Object> initialConsumeResultExtraction(Extractor bodyRequestExtractor){
        var executeId = UUID.randomUUID().toString();
        bodyRequestExtractor.setExecuteId(executeId);
        bodyRequestExtractor.getData().setExecuteId(executeId);
        Map<String, Object> bodyResult = Map.of("executeId", executeId);
        setInSession(SessionHelper.SessionData.EXECUTE_ID,executeId);
        return bodyResult;
    }

    private void getExtract(Extractor bodyRequestExtractor, String token,Map<String, Object> bodyResult) throws MalformedURLException {
        sendPostRequest(ServicePaths.getExtraction(), bodyRequestExtractor, token);
        pause(FIVE);
        for (var attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            var statusCode = sendPostRequest(ServicePaths.getExtractionResult(), bodyResult, token)
                    .jsonPath().getString(STATUS_CODE_KEY);
            pause(FIVE);

            if (!RUNNING_STATUS.equals(statusCode)) {
               break;
            }
            if (attempt + 1 >= MAX_ATTEMPTS) {
                pause(FIVE);
                getExtract(bodyRequestExtractor, token,bodyResult);
            }
        }
    }
    private Response sendPostRequest(URL url, Object body, String token) {
        return SerenityRest.given()
                .contentType(ContentType.JSON)
                .header(HeadersEnum.AUTHORIZATION.toString(), token)
                .body(body)
                .post(url);
    }

}
