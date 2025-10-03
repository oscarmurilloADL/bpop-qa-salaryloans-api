package co.com.bancopopular.automation.models.interpreter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class GovIssueIdent {
    @XmlElement(name = "GovIssueIdentType", namespace = "urn://grupoaval.com/xsd/ifx/")
    private String govIssueIdentType;
    @XmlElement(name = "IdentSerialNum", namespace = "urn://grupoaval.com/xsd/ifx/")
    private String identSerialNum;
}
