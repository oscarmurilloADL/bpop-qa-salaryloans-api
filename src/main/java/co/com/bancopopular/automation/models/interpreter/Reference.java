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
public class Reference {
    @XmlAttribute(name = "URI")
    private String uri;
    @XmlElement(name = "Transforms", namespace = "http://www.w3.org/2000/09/xmldsig#")
    private Transforms transforms;
    @XmlElement(name = "DigestMethod", namespace = "http://www.w3.org/2000/09/xmldsig#")
    private DigestMethod digestMethod;
    @XmlElement(name = "DigestValue", namespace = "http://www.w3.org/2000/09/xmldsig#")
    private String digestValue;
}
