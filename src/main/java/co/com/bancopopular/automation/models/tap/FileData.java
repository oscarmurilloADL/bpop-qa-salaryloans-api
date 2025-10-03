package co.com.bancopopular.automation.models.tap;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FileData {

	@JsonProperty("fileBucket")
	private String fileBucket;

	@JsonProperty("fileExtension")
	private String fileExtension;

	@JsonProperty("fileUrl")
	private String fileUrl;

}