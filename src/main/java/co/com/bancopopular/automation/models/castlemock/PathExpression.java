package co.com.bancopopular.automation.models.castlemock;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PathExpression {
    @JacksonXmlProperty(localName = "expression")
    @JsonDeserialize(using = ExpressionSanitizer.class)
    private String expression;
}
