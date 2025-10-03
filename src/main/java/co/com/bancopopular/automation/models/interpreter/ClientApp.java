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
public class ClientApp {
    @XmlElement(name = "Org", namespace = "urn://grupoaval.com/xsd/ifx/")
    private String org;
    @XmlElement(name = "Name", namespace = "urn://grupoaval.com/xsd/ifx/")
    private String name;
    @XmlElement(name = "Version", namespace = "urn://grupoaval.com/xsd/ifx/")
    private String version;
}
