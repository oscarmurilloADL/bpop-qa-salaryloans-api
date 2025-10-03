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
public class SignedInfo {
    @XmlElement(name = "CanonicalizationMethod", namespace = "http://www.w3.org/2000/09/xmldsig#")
    private CanonicalizationMethod canonicalizationMethod;
    @XmlElement(name = "SignatureMethod", namespace = "http://www.w3.org/2000/09/xmldsig#")
    private SignatureMethod signatureMethod;
    @XmlElement(name = "Reference", namespace = "http://www.w3.org/2000/09/xmldsig#")
    private Reference reference;
}
