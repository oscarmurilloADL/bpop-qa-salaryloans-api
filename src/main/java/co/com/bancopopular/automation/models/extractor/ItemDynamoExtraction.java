package co.com.bancopopular.automation.models.extractor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDynamoExtraction {

    @JsonProperty("extract_interpreter_id")
    private String extract_interpreter_id;

    @JsonProperty("extract_interpreter_sort")
    private String extract_interpreter_sort;

    @JsonProperty("advisorId")
    private String advisorId;

    @JsonProperty("allowanceDate")
    private String allowanceDate;

    @JsonProperty("clientDocumentNumber")
    private String clientDocumentNumber;

    @JsonProperty("clientDocumentType")
    private String clientDocumentType;

    @JsonProperty("clientName")
    private String clientName;

    @JsonProperty("expiration_time")
    private String expiration_time;

    @JsonProperty("extractResult")
    private ExtractResult extractResult;

    @JsonProperty("flowType")
    private String flowType;

    @JsonProperty("interpreterResult")
    private InterpreterResult interpreterResult;

    @JsonProperty("journeyId")
    private String journeyId;

    @JsonProperty("obligationId")
    private String obligationId;

    @JsonProperty("officeId")
    private String officeId;

    @JsonProperty("payerName")
    private String payerName;

    @JsonProperty("payerNit")
    private String payerNit;

    @JsonProperty("processDate")
    private String processDate;

    @JsonProperty("processStatus")
    private String processStatus;

    @JsonProperty("sectoreCode")
    private String sectoreCode;
}
