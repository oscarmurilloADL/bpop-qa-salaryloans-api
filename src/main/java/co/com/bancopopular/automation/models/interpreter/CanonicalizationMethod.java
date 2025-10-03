package co.com.bancopopular.automation.models.interpreter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class CanonicalizationMethod {
    @XmlAttribute(name = "Algorithm")
    private String algorithm;
    @XmlElement(name = "InclusiveNamespaces", namespace = "http://www.w3.org/2001/10/xml-exc-c14n#")
    private InclusiveNamespaces inclusiveNamespaces;
}