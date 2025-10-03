package co.com.bancopopular.automation.models.interpreter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class PayrollLoanInterpreterInfo {
    @XmlElement(name = "EarnedAmt", namespace = "urn://grupoaval.com/xsd/ifx/")
    private String earnedAmt;
    @XmlElement(name = "DeductibleAmt", namespace = "urn://grupoaval.com/xsd/ifx/")
    private String deductibleAmt;
    @XmlElement(name = "NetAmt", namespace = "urn://grupoaval.com/xsd/ifx/")
    private String netAmt;
    @XmlElement(name = "Regimen", namespace = "urn://grupoaval.com/xsd/ifx/")
    private String regimen;
    @XmlElement(name = "PensionType", namespace = "urn://grupoaval.com/xsd/ifx/")
    private String pensionType;
    @XmlElement(name = "Salary", namespace = "urn://grupoaval.com/xsd/ifx/")
    private String salary;
    @XmlElement(name = "Paymaster", namespace = "urn://grupoaval.com/xsd/ifx/")
    private Paymaster paymaster;

    @XmlElementWrapper(name = "FinancialConceptList", namespace = "urn://grupoaval.com/xsd/ifx/")
    @XmlElement(name = "FinancialConceptData", namespace = "urn://grupoaval.com/xsd/ifx/")
    private List<FinancialConceptData> financialConceptList;
}
