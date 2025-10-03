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
public class SecurityTokenReference {
    @XmlAttribute(name = "Id", namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd")
    private String id;
    @XmlElement(name = "X509Data", namespace = "http://www.w3.org/2000/09/xmldsig#")
    private X509Data x509Data;
}