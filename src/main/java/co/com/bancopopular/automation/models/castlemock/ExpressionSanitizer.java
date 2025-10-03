package co.com.bancopopular.automation.models.castlemock;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;


public class ExpressionSanitizer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser p, DeserializationContext ct) throws IOException {
        var value = p.getValueAsString();
        return value != null ? value.replaceAll("\\s+", "") : null;
    }
}
