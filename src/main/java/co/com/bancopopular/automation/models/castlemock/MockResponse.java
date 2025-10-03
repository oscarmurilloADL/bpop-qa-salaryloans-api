package co.com.bancopopular.automation.models.castlemock;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class MockResponse {

    @JacksonXmlProperty(localName = "id")
    private String id;

    @JacksonXmlProperty(localName = "name")
    private String name;

    @JacksonXmlProperty(localName = "body")
    private String body;

    @JacksonXmlProperty(localName = "status")
    private String status;

    @JacksonXmlProperty(localName = "httpStatusCode")
    private int httpStatusCode;

    @JacksonXmlProperty(localName = "usingExpressions")
    private boolean usingExpressions;

    @JacksonXmlElementWrapper(localName = "xpathExpressions")
    private List<PathExpression> xpathExpressions;

    @JacksonXmlElementWrapper(localName = "jsonPathExpressions")
    private List<PathExpression> jsonPathExpressions;

    @JacksonXmlElementWrapper(localName = "httpHeaders")
    private List<HttpHeader> httpHeaders;

    @JacksonXmlElementWrapper(localName = "contentEncodings")
    private List<String> contentEncodings;

    @JacksonXmlElementWrapper(localName = "parameterQueries")
    private List<ParameterQuery> parameterQueries;

}
