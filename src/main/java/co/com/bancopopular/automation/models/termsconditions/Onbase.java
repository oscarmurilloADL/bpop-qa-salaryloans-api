package co.com.bancopopular.automation.models.termsconditions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Onbase{

	@JsonProperty("expired")
	Boolean expired;

	@JsonProperty("onBasePresent")
	Boolean onBasePresent;

	@JsonProperty("expirationDatePresent")
	String expirationDatePresent;

	@JsonProperty("documentAlreadyInCloud")
	Boolean documentAlreadyInCloud;

	@JsonProperty("cucCode")
	String cucCode;
}