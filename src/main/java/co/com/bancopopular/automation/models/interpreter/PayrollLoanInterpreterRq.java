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
public class PayrollLoanInterpreterRq {
    @XmlElement(name = "RqUID", namespace = "urn://grupoaval.com/xsd/ifx/")
    private String rqUID;
    @XmlElement(name = "MsgRqHdr", namespace = "urn://grupoaval.com/xsd/ifx/")
    private MsgRqHdr msgRqHdr;
    @XmlElement(name = "CustId", namespace = "urn://grupoaval.com/xsd/ifx/")
    private CustId custId;
    @XmlElement(name = "PayrollLoanInterpreterInfo", namespace = "urn://grupoaval.com/xsd/ifx/")
    private PayrollLoanInterpreterInfo payrollLoanInterpreterInfo;
}
