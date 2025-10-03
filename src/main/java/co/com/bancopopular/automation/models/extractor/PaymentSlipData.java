package co.com.bancopopular.automation.models.extractor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSlipData {

    @JsonProperty("financialConceptList")
    private FinancialConceptList financialConceptList;

    @JsonProperty("totalIncome")
    private String totalIncome;

    @JsonProperty("totalOutcome")
    private String totalOutcome;
}
