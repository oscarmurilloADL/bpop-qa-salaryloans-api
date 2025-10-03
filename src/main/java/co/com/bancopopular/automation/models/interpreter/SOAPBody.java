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
public class SOAPBody {
    @XmlElement(name = "getPayrollLoanInterpreterRequest", namespace = "urn://grupoaval.com/prodschnsmngt/v1/")
    private PayrollLoanInterpreterRequest request;
}
