package co.com.bancopopular.automation.models.decrim.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.TreeMap;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DataForSignature {
    @JsonProperty("accessKeyID")
    private String accessKeyID;
    @JsonProperty("secretAccessKey")
    private String secretAccessKey;
    @JsonProperty("regionName")
    private String regionName;
    @JsonProperty("serviceName")
    private String serviceName;
    @JsonProperty("httpMethodName")
    private String httpMethodName;
    @JsonProperty("canonicalURI")
    private String canonicalURI;
    @JsonProperty("queryParametes")
    private TreeMap<String, String> queryParametes;
    @JsonProperty("awsHeaders")
    private TreeMap<String, String> awsHeaders;
    @JsonProperty("payload")
    private String payload;
    @JsonProperty("debug")
    @Builder.Default
    private boolean debug = false;
}
