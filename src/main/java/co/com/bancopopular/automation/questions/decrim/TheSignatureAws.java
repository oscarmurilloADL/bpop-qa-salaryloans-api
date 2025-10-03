package co.com.bancopopular.automation.questions.decrim;

import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.models.decrim.data.DataForSignature;
import co.com.bancopopular.automation.utils.awssignaturev4.AwsSignatureV4;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class TheSignatureAws implements Question<Map<String, String>> {
    String bodyRqCreateCase;
    String accesKey;
    String secretKey;
    String dataToSign;

    public TheSignatureAws(String bodyRqCreateCase, String accesKey, String secretKey, String dataToSign) {
        this.bodyRqCreateCase = bodyRqCreateCase;
        this.accesKey = accesKey;
        this.secretKey = secretKey;
        this.dataToSign = dataToSign;
    }

    public TheSignatureAws(String accesKey, String secretKey) {
        this.accesKey = accesKey;
        this.secretKey = secretKey;
        this.bodyRqCreateCase=null;
        this.dataToSign=null;
    }

    public static TheSignatureAws ofCredentials(String bodyRqCreateCase, String accesKey, String secretKey, String dataToSign){
        return new TheSignatureAws(bodyRqCreateCase, accesKey, secretKey, dataToSign);
    }

    @SneakyThrows
    @Override
    public Map<String, String> answeredBy(Actor actor) {

        var objectMapper = new ObjectMapper();
        var dataForSignature = objectMapper.readValue(dataToSign, new TypeReference<DataForSignature>() {});
        AwsSignatureV4 awsSignatureV4 = new AwsSignatureV4.Builder(accesKey ,secretKey)
                .regionName(dataForSignature.getRegionName())
                .serviceName(dataForSignature.getServiceName())
                .httpMethodName(dataForSignature.getHttpMethodName())
                .canonicalURI(dataForSignature.getCanonicalURI())
                .queryParametes(null)
                .awsHeaders(getAwsHeaders())
                .payload(bodyRqCreateCase)
                .build();

        return awsSignatureV4.getHeaders();
    }

    private static TreeMap<String, String> getAwsHeaders(){
        TreeMap<String, String> awsHeaders = new TreeMap<>();
        awsHeaders.put(Constants.HOST_HEADER, Constants.HOST_DECRIM_SIGNATURE);
        awsHeaders.put(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_DECRIM_VALUE);
        awsHeaders.put(Constants.X_AMZ_DATE_MAP_HEADERS, getTimeStamp());


        return awsHeaders;
    }

    private static String getTimeStamp() {
        DateFormat dateFormat = new SimpleDateFormat(Constants.FORMAT_DATE_TO_SIGNATURE_DECRIM);
        dateFormat.setTimeZone(TimeZone.getTimeZone(Constants.TIME_ZONE));//server timezone
        return dateFormat.format(new Date());
    }
}
