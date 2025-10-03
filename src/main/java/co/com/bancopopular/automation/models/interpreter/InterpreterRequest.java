package co.com.bancopopular.automation.models.interpreter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "Envelope", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
@XmlAccessorType(XmlAccessType.FIELD)
public class InterpreterRequest {
    @XmlElement(name = "Header", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
    private SOAPHeader header;
    @XmlElement(name = "Body", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
    private SOAPBody body;
}
