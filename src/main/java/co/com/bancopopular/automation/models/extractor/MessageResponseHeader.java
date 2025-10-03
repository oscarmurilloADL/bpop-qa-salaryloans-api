package co.com.bancopopular.automation.models.extractor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponseHeader {

    @JsonProperty("taxCostAmount")
    private TaxCostAmount taxCostAmount;

    @JsonProperty("effDt")
    private String effDt;

    @JsonProperty("remainRec")
    private String remainRec;


}
