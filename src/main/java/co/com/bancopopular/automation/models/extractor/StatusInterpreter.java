package co.com.bancopopular.automation.models.extractor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusInterpreter {

    @JsonProperty("severity")
    private String severity;

    @JsonProperty("statusDescription")
    private String statusDescription;

    @JsonProperty("additionalStatusList")
    private List<AdditionalStatusList> additionalStatusList;

    @JsonProperty("statusCode")
    private String statusCode;

}
