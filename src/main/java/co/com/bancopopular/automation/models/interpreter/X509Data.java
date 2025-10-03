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
public class X509Data {
    @XmlElement(name = "X509IssuerSerial", namespace = "http://www.w3.org/2000/09/xmldsig#")
    private X509IssuerSerial x509IssuerSerial;
}
