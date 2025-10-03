package co.com.bancopopular.automation.models.extractor;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayrollLoanInterpreterInfo {

    @JsonProperty("pensionDiscount")
    private String pensionDiscount;

    @JsonProperty("extraPayment")
    private String extraPayment;

    @JsonProperty("paymaster")
    private PaymasterInterpreter paymaster;

    @JsonProperty("salary")
    private String salary;

    @JsonProperty("honorarium")
    private String honorarium;

    @JsonProperty("otherLawDiscount")
    private String otherLawDiscount;

    @JsonProperty("healthDiscount")
    private String healthDiscount;

    @JsonProperty("totalOtherIncome")
    private String totalOtherIncome;

    @JsonProperty("otherDiscount3")
    private String otherDiscount3;

    @JsonProperty("overtime")
    private String overtime;

    @JsonProperty("otherDiscount2")
    private String otherDiscount2;

    @JsonProperty("otherDiscount1")
    private String otherDiscount1;

    @JsonProperty("bonuses")
    private String bonuses;

}
