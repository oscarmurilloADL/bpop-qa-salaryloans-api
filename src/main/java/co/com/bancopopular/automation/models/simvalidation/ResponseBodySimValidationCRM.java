package co.com.bancopopular.automation.models.simvalidation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBodySimValidationCRM{

	@JsonProperty("status")
	private String status;

	@JsonProperty("codeStatus")
	private String codeStatus;
}