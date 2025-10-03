package co.com.bancopopular.automation.models.extractor;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestData {

    @JsonProperty("clientDocumentType")
    private String clientDocumentType;

    @JsonProperty("clientDocumentNumber")
    private String clientDocumentNumber;

    @JsonProperty("clientName")
    private String clientName;

    @JsonProperty("flowType")
    private String flowType;

    @JsonProperty("payerNit")
    private String payerNit;

    @JsonProperty("payerName")
    private String payerName;

    @JsonProperty("sectorCode")
    private String sectorCode;

    @JsonProperty("advisorId")
    private String advisorId;

    @JsonProperty("executeId")
    private String executeId;

    @JsonProperty("journeyId")
    private String journeyId;

    @JsonProperty("obligationId")
    private String obligationId;

    @JsonProperty("officeId")
    private String officeId;


}
