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
public class FinancialConceptData {
    @XmlElement(name = "ConceptId", namespace = "urn://grupoaval.com/xsd/ifx/")
    private String conceptId;
    @XmlElement(name = "ConceptName", namespace = "urn://grupoaval.com/xsd/ifx/")
    private String conceptName;
    @XmlElement(name = "TrnType", namespace = "urn://grupoaval.com/xsd/ifx/")
    private String trnType;
    @XmlElement(name = "ConceptValue", namespace = "urn://grupoaval.com/xsd/ifx/")
    private String conceptValue;
}