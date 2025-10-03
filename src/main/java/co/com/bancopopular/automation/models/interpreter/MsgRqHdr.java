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
public class MsgRqHdr {
    @XmlElement(name = "ClientApp", namespace = "urn://grupoaval.com/xsd/ifx/")
    private ClientApp clientApp;
    @XmlElement(name = "Channel", namespace = "urn://grupoaval.com/xsd/ifx/v2/")
    private String channel;
    @XmlElement(name = "BankInfo", namespace = "urn://grupoaval.com/xsd/ifx/")
    private BankInfo bankInfo;
    @XmlElement(name = "ClientDt", namespace = "urn://grupoaval.com/xsd/ifx/v2/")
    private String clientDt;
    @XmlElement(name = "IPAddr", namespace = "urn://grupoaval.com/xsd/ifx/v2/")
    private String ipAddr;
    @XmlElement(name = "SessKey", namespace = "urn://grupoaval.com/xsd/ifx/v2/")
    private String sessKey;
    @XmlElement(name = "UserId", namespace = "urn://grupoaval.com/xsd/ifx/")
    private UserId userId;
    @XmlElement(name = "Reverse", namespace = "urn://grupoaval.com/xsd/ifx/v2/")
    private String reverse;
    @XmlElement(name = "Language", namespace = "urn://grupoaval.com/xsd/ifx/v2/")
    private String language;
    @XmlElement(name = "NextDay", namespace = "urn://grupoaval.com/xsd/ifx/v2/")
    private String nextDay;
}
