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
public class KeyInfo {
    @XmlAttribute(name = "Id")
    private String id;
    @XmlElement(name = "SecurityTokenReference", namespace = "http://schemas.xmlsoap.org/ws/2002/12/secext")
    private SecurityTokenReference securityTokenReference;
}
