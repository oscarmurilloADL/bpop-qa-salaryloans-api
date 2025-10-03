package co.com.bancopopular.automation.models.extractor;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayrollLoanExtractionInfo {

    @JsonProperty("allowanceDate")
    private String allowanceDate;

    @JsonProperty("membershipId")
    private String membershipId;

    @JsonProperty("paymaster")
    private PaymasterExtract paymaster;

    private PaymentSlipList paymentSlipList;
}
