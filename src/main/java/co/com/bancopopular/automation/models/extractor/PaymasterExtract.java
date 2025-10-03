package co.com.bancopopular.automation.models.extractor;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymasterExtract {

    @JsonProperty("namePaymaster")
    private String namePaymaster;

    @JsonProperty("nitPaymaster")
    private String nitPaymaster;

}
