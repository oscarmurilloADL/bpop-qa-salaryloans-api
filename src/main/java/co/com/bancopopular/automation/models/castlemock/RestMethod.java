package co.com.bancopopular.automation.models.castlemock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class RestMethod {

    @JacksonXmlElementWrapper(localName = "mockResponses")
    @JacksonXmlProperty(localName = "mockResponse")
    private List<MockResponse> mockResponses;


}
