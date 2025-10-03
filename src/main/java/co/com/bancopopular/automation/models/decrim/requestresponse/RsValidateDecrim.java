package co.com.bancopopular.automation.models.decrim.requestresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RsValidateDecrim{

	@JsonProperty("documentConcept")
	private String documentConcept;

	@JsonProperty("messageState")
	private String messageState;

	@JsonProperty("idCase")
	private String idCase;

	@JsonProperty("footprintConcept")
	private String footprintConcept;

	@JsonProperty("state")
	private String state;

	@JsonProperty("dateResponse")
	private String dateResponse;

	@JsonProperty("proof")
	private String proof;
}