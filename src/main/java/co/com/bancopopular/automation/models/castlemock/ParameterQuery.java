package co.com.bancopopular.automation.models.castlemock;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ParameterQuery {

    @JacksonXmlProperty(localName = "matchAny")
    private boolean matchAny;

    @JacksonXmlProperty(localName = "matchCase")
    private boolean matchCase;

    @JacksonXmlProperty(localName = "matchRegex")
    private boolean matchRegex;

    @JacksonXmlProperty(localName = "parameter")
    private String parameter;

    @JacksonXmlProperty(localName = "query")
    private String query;

}
