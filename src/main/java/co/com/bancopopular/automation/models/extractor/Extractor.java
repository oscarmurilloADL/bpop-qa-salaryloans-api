package co.com.bancopopular.automation.models.extractor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Extractor {

    @JsonProperty("data")
    private RequestData data;

    @JsonProperty("executeId")
    private String executeId;




}
