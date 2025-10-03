package co.com.bancopopular.automation.models.castlemock;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "soapOperation")
public class SoapOperation {

    @JacksonXmlElementWrapper(localName = "mockResponses")
    @JacksonXmlProperty(localName = "mockResponse")
    private List<MockResponseSoap> mockResponses;


}
