package co.com.bancopopular.automation.models.extractor;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtractResult {

    @JsonProperty("payrollLoanExtractionInfo")
    private PayrollLoanExtractionInfo payrollLoanExtractionInfo;

    @JsonProperty("rqUID")
    private String rqUID;

    @JsonProperty("status")
    private StatusExtract status;


}
