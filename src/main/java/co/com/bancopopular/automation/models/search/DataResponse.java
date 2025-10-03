package co.com.bancopopular.automation.models.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataResponse{

	@JsonProperty("code")
	private String code;

	@JsonProperty("description")
	private String description;

	@JsonProperty("cellphone")
	private String cellphone;
}