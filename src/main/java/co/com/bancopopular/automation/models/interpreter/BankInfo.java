package co.com.bancopopular.automation.models.interpreter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Getter
@Setter
@NoArgsConstructor
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class BankInfo {

    @JsonProperty("statusCode")
    @XmlElement(name = "BankId", namespace = "urn://grupoaval.com/xsd/ifx/")
    private String bankId;
}
