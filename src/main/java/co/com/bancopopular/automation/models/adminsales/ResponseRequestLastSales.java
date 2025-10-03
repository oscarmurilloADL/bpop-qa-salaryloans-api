package co.com.bancopopular.automation.models.adminsales;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseRequestLastSales{

	@JsonProperty("data")
	private List<DataSales> data;

	@JsonProperty("last")
	private boolean last;

	@JsonProperty("numberOfElements")
	private int numberOfElements;

	@JsonProperty("totalPages")
	private int totalPages;

	@JsonProperty("first")
	private boolean first;

	@JsonProperty("totalElements")
	private int totalElements;
}