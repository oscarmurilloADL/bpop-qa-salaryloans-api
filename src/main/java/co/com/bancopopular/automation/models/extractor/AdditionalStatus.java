package co.com.bancopopular.automation.models.extractor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalStatus {

    @JsonProperty("statusCode")
    private String statusCode;

    @JsonProperty("serverStatusCode")
    private String serverStatusCode;

}
