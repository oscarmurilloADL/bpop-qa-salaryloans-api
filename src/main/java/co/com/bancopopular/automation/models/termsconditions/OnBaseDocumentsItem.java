package co.com.bancopopular.automation.models.termsconditions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnBaseDocumentsItem{

	@JsonProperty("special")
	Boolean special;

	@JsonProperty("bankingInsurance")
	Boolean bankingInsurance;

	@JsonProperty("onbase")
	Onbase onbase;

	@JsonProperty("checked")
	Boolean checked;

	@JsonProperty("codeDocument")
	Integer codeDocument;

	@JsonProperty("nameDocument")
	String nameDocument;

	@JsonProperty("required")
	Boolean required;

	@JsonProperty("applyMandatoryFront")
	Boolean applyMandatoryFront;
}