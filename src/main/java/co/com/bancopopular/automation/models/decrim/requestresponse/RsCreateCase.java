package co.com.bancopopular.automation.models.decrim.requestresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RsCreateCase{

	@JsonProperty("idCase")
	private String idCase;

	@JsonProperty("responseMessage")
	private String responseMessage;
}