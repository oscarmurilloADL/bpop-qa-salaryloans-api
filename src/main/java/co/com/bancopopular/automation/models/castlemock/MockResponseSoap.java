package co.com.bancopopular.automation.models.castlemock;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MockResponseSoap {

    @JacksonXmlProperty(localName = "id")
    private String id;

    @JacksonXmlProperty(localName = "name")
    private String name;

    @JacksonXmlProperty(localName = "body", isAttribute = false)
    @JacksonXmlCData
    @JacksonXmlText
    @JacksonXmlElementWrapper(useWrapping = false)
    private String body;

    @JacksonXmlProperty(localName = "status")
    private String status;

    @JacksonXmlProperty(localName = "httpStatusCode")
    private int httpStatusCode;

    @JacksonXmlProperty(localName = "usingExpressions")
    private boolean usingExpressions;

    @JacksonXmlElementWrapper(localName = "xpathExpressions")
    private List<PathExpression> xpathExpressions;

    @JacksonXmlElementWrapper(localName = "httpHeaders")
    private List<HttpHeader> httpHeaders;

    @JacksonXmlElementWrapper(localName = "contentEncodings")
    private List<String> contentEncodings;

    @JacksonXmlProperty(localName = "operationId")
    private String operationId;

}
