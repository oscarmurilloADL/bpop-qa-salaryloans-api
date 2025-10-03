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
public class Signature {

    @XmlAttribute(name = "Id")
    private String id;

    @XmlElement(name = "SignedInfo", namespace = "http://www.w3.org/2000/09/xmldsig#")
    private SignedInfo signedInfo;

    @XmlElement(name = "SignatureValue", namespace = "http://www.w3.org/2000/09/xmldsig#")
    private String signatureValue;

    @XmlElement(name = "KeyInfo", namespace = "http://www.w3.org/2000/09/xmldsig#")
    private KeyInfo keyInfo;
}
