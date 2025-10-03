package co.com.bancopopular.automation.models.extractor;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancialConceptData {

    @JsonProperty("conceptName")
    private String conceptName;

    @JsonProperty("conceptValue")
    private String conceptValue;

    @JsonProperty("conceptId")
    private String conceptId;

    @JsonProperty("trnType")
    private String trnType;



}
