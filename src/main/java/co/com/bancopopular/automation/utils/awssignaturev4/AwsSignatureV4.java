package co.com.bancopopular.automation.utils.awssignaturev4;

import co.com.bancopopular.automation.models.decrim.data.DataForSignature;
import co.com.bancopopular.automation.utils.SessionHelper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;

public class AwsSignatureV4 {

    private static final Logger LOGGER = Logger.getLogger(AwsSignatureV4.class.getName());

    public static class Builder {

        private String accessKeyID;
        private String secretAccessKey;
        private String regionName;
        private String serviceName;
        private String httpMethodName;
        private String canonicalURI;
        private SortedMap<String, String> queryParametes;
        private SortedMap<String, String> awsHeaders;
        private String payload;
        DataForSignature objDataToSign;

        public Builder(String accessKeyID, String secretAccessKey) {
            this.accessKeyID = accessKeyID;
            this.secretAccessKey = secretAccessKey;
        }

        public Builder(DataForSignature objDataToSign) {
            this.objDataToSign = objDataToSign;
        }

        public Builder regionName(String regionName) {
            this.regionName = regionName;
            return this;
        }

        public Builder serviceName(String serviceName) {
            this.serviceName = serviceName;
            return this;
        }

        public Builder httpMethodName(String httpMethodName) {
            this.httpMethodName = httpMethodName;
            return this;
        }

        public Builder canonicalURI(String canonicalURI) {
            this.canonicalURI = canonicalURI;
            return this;
        }

        public Builder queryParametes(SortedMap<String, String> queryParametes) {
            this.queryParametes = queryParametes;
            return this;
        }

        public Builder awsHeaders(SortedMap<String, String> awsHeaders) {
            this.awsHeaders = awsHeaders;
            return this;
        }

        public Builder payload(String payload) {
            this.payload = payload;
            return this;
        }

        public AwsSignatureV4 build() {
            return new AwsSignatureV4(this);
        }

    }

    private String accessKeyID;
    private String secretAccessKey;
    private String regionName;
    private String serviceName;
    private String httpMethodName;
    private String canonicalURI;
    private SortedMap<String, String> queryParametes;
    private SortedMap<String, String> awsHeaders;
    private String payload;

    private static final String HMAC = "AWS4-HMAC-SHA256";
    private static final String REQUEST = "aws4_request";
    private String strSignedHeader;
    private String xAmzDate;
    private String currentDate;

    public AwsSignatureV4(Builder builder) {
        accessKeyID = builder.accessKeyID;
        secretAccessKey = builder.secretAccessKey;
        regionName = builder.regionName;
        serviceName = builder.serviceName;
        httpMethodName = builder.httpMethodName;
        canonicalURI = builder.canonicalURI;
        queryParametes = builder.queryParametes;
        awsHeaders = builder.awsHeaders;
        payload = builder.payload;
        xAmzDate = getTimeStamp();
        currentDate = getDate();
    }

    private String prepareCanonicalRequest() {
        var canonicalURL = new StringBuilder("");
        canonicalURL.append(httpMethodName).append("\n");
        canonicalURI = canonicalURI == null || canonicalURI.trim().isEmpty() ? "/" : canonicalURI;
        canonicalURL.append(canonicalURI).append("\n");
        var queryString = new StringBuilder("");
        if (queryParametes != null && !queryParametes.isEmpty()) {
            for (Map.Entry<String, String> entrySet : queryParametes.entrySet()) {
                String key = entrySet.getKey();
                String value = entrySet.getValue();
                queryString.append(key).append("=").append(encodeParameter(value)).append("&");
            }
            queryString.deleteCharAt(queryString.lastIndexOf("&"));
            queryString.append("\n");
        } else {
            queryString.append("\n");
        }
        canonicalURL.append(queryString);

        var signedHeaders = new StringBuilder("");
        if (awsHeaders != null && !awsHeaders.isEmpty()) {
            for (Map.Entry<String, String> entrySet : awsHeaders.entrySet()) {
                String key = entrySet.getKey();
                String value = entrySet.getValue();
                signedHeaders.append(key).append(";");
                canonicalURL.append(key).append(":").append(value).append("\n");
            }
            canonicalURL.append("\n");
        } else {
            canonicalURL.append("\n");
        }

        strSignedHeader = signedHeaders.substring(0, signedHeaders.length() - 1);
        canonicalURL.append(strSignedHeader).append("\n");
        payload = payload == null ? "" : payload;
        canonicalURL.append(generateHex(payload));
        return canonicalURL.toString();
    }

    private String prepareStringToSign(String canonicalURL) {
        var stringToSign = "";
        stringToSign = HMAC + "\n";
        stringToSign += xAmzDate + "\n";
        stringToSign += currentDate + "/" + regionName + "/" + serviceName + "/" + REQUEST + "\n";
        stringToSign += generateHex(canonicalURL);
        return stringToSign;
    }

    private String calculateSignature(String stringToSign) {
        try {
            byte[] signatureKey = getSignatureKey(secretAccessKey, currentDate, regionName, serviceName);
            byte[] signature = hmacSHA256(signatureKey, stringToSign);
            return bytesToHex(signature);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "error encrypting info: ", ex);
        }
        return null;
    }

    public Map<String, String> getHeaders() {
        awsHeaders.put("x-amz-date", xAmzDate);
        String canonicalURL = prepareCanonicalRequest();
        var stringToSign = prepareStringToSign(canonicalURL);
        String signature = calculateSignature(stringToSign);

        if (signature != null) {
            Map<String, String> header = new HashMap<>(0);
            header.put("x-amz-date", xAmzDate);
            header.put("Authorization", buildAuthorizationString(signature));
            return header;
        } else {
            return new HashMap<>();
        }
    }

    private String buildAuthorizationString(String strSignature) {
        return HMAC + " "
                + "Credential=" + accessKeyID + "/" + getDate() + "/" + regionName + "/" + serviceName + "/" + REQUEST + ","
                + "SignedHeaders=" + strSignedHeader + ","
                + "Signature=" + strSignature;
    }

    private String generateHex(String data) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(data.getBytes(StandardCharsets.UTF_8));
            byte[] digest = messageDigest.digest();
            return String.format("%064x", new java.math.BigInteger(1, digest));
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.SEVERE, "error encrypting info: ", e);
        }
        return null;
    }

    private byte[] hmacSHA256(byte[] key, String data) throws NoSuchAlgorithmException, InvalidKeyException {
        var algorithm = "HmacSHA256";
        var mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key, algorithm));
        return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    private byte[] getSignatureKey(String key, String date, String regionName, String serviceName) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] kSecret = ("AWS4" + key).getBytes(StandardCharsets.UTF_8);
        byte[] kDate = hmacSHA256(kSecret, date);
        byte[] kRegion = hmacSHA256(kDate, regionName);
        byte[] kService = hmacSHA256(kRegion, serviceName);
        return hmacSHA256(kService, REQUEST);
    }

    static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    private String bytesToHex(byte[] bytes) {
        var hexChars = new char[bytes.length * 2];
        for (var j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars).toLowerCase();
    }

    private String getTimeStamp() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(new Date());
    }

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(new Date());
    }

    private String encodeParameter(String param){
        try {
            return URLEncoder.encode(param, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "";
        }
    }

    public static Map<String, String> getAWSToken(String canonicalURI, String type,String payload){

        TreeMap<String, String> awsHeaders = new TreeMap<>();
        awsHeaders.put("host", "lvx0wk9q6c.execute-api.us-east-2.amazonaws.com");

        AwsSignatureV4 aWSV4Auth = new AwsSignatureV4.Builder(getFromSession(SessionHelper.SessionData.ACCESS_KEY) ,getFromSession(SessionHelper.SessionData.SECRET_KEY))
                .regionName("us-east-2")
                .serviceName("execute-api")
                .httpMethodName(type)
                .canonicalURI(canonicalURI)
                .queryParametes(null)
                .awsHeaders(awsHeaders)
                .payload(payload)
                .build();

        return aWSV4Auth.getHeaders();
    }


}
