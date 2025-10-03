package co.com.bancopopular.automation.models.extractor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusExtract {

    @JsonProperty("severity")
    private String severity;

    @JsonProperty("statusDesc")
    private String statusDesc;

    @JsonProperty("additionalStatus")
    private List<AdditionalStatus> additionalStatus;

    @JsonProperty("statusCode")
    private String statusCode;

}
