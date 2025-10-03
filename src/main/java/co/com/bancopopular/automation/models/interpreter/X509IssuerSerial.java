package co.com.bancopopular.automation.models.interpreter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class X509IssuerSerial {
    @XmlElement(name = "X509IssuerName", namespace = "http://www.w3.org/2000/09/xmldsig#")
    private String x509IssuerName;
    @XmlElement(name = "X509SerialNumber", namespace = "http://www.w3.org/2000/09/xmldsig#")
    private String x509SerialNumber;
}
