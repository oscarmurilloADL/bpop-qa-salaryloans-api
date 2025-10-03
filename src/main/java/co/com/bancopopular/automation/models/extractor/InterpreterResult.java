package co.com.bancopopular.automation.models.extractor;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterpreterResult {

    @JsonProperty("payrollLoanInterpreterInfo")
    private PayrollLoanInterpreterInfo payrollLoanInterpreterInfo;

    @JsonProperty("messageResponseHeader")
    private MessageResponseHeader messageResponseHeader;

    @JsonProperty("requestUniqueIdentifier")
    private String requestUniqueIdentifier;

    @JsonProperty("status")
    private StatusInterpreter status;


}
