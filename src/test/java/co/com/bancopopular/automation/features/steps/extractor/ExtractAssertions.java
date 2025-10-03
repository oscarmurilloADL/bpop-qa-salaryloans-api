package co.com.bancopopular.automation.features.steps.extractor;

import co.com.bancopopular.automation.models.extractor.ItemDynamoExtraction;
import co.com.bancopopular.automation.models.interpreter.*;
import co.com.bancopopular.automation.utils.RequestsUtil;
import co.com.bancopopular.automation.utils.SessionHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import net.thucydides.core.steps.StepEventBus;
import javax.xml.bind.JAXBContext;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static co.com.bancopopular.automation.constants.Constants.*;
import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static co.com.bancopopular.automation.utils.SessionHelper.setInSession;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;


public class ExtractAssertions {

    private static final String COLPENSIONES_LOG = "El nit 9003360047 tiene un valor homologado en la tabla payers 9003360047";
    private static final String FOPEP_LOG = "El nit 9016596505 tiene un valor homologado en la tabla payers 9013361167";
    private static final String FIDUPREVISORA_LOG = "El nit 8605251485 tiene un valor homologado en la tabla payers 8605251485";
    private static final String FERROCARRILES_LOG = "El nit 8001128062 tiene un valor homologado en la tabla payers 8001128062";
    private static final String POSITIVA_LOG = "El nit 8600111536 tiene un valor homologado en la tabla payers 8600111536";
    private static final String EXTRACT_SUCCESS = "EXTRACT_SUCCESS";
    private static final String CUSTOMER_WITHOUT_EXTRACT = "CUSTOMER_WITHOUT_EXTRACT";
    private static final String UUID_PATTERN = "^[a-f0-9]{8}-[a-f0-9]{" + FOUR + "}-[a-f0-9]{" + FOUR + "}-[a-f0-9]{" + FOUR + "}-[a-f0-9]{12}$";
    private static final String LOCAL_DATE = "yyyy-MM-dd'T'HH";
    private static final String SUCCESS = "SUCCESS";

    private static final String NOT_SUCCESS = "NOT SUCCESS";
    private static final String DOES_NOT_APPLY = "DOES_NOT_APPLY";
    private static final String PROCESS_STATUS = "processStatus";
    private static final String RQUID = "rqUID";
    private static final String EXTRACT_INTERPRETER = "extractInterpreterSort";
    private static final String REQUEST_STATE = "requestState";
    private static final String BONUSES = "bonuses";
    private static final String EXTRA_PAYMENT = "extraPayment";
    private static final String HEALTH_DISCOUNT = "healthDiscount";
    private static final String HONORARIUM = "honorarium";
    private static final String DISCOUNT1 = "otherDiscount1";
    private static final String DISCOUNT2 = "otherDiscount2";
    private static final String DISCOUNT3 = "otherDiscount3";
    private static final String OTHER_LAW_DISCOUNT = "otherLawDiscount";
    private static final String OVERTIME = "overtime";
    private static final String PAYMASTER = "paymaster";
    private static final String PENSION_DISCOUNT = "pensionDiscount";
    private static final String SALARY = "salary";
    private static final String TOTAL_OTHER_INCOME = "totalOtherIncome";
    private static final String EXTRACTOR_STATUS = "Process Status ";
    private static final String SUCCEDED = "SUCCEEDED";
    private static final String COLPENSIONES_REQUEST = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ifx=\"urn://grupoaval.com/xsd/ifx/\" xmlns:v2=\"urn://grupoaval.com/xsd/ifx/v2/\"><SOAP-ENV:Header><wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" SOAP-ENV:mustUnderstand=\"1\"><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Id=\"SIG-7b760f9d-80a2-4086-9685-7510c0d0ee33\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"><ec:InclusiveNamespaces xmlns:ec=\"http://www.w3.org/2001/10/xml-exc-c14n#\" PrefixList=\"SOAP-ENV\"/></ds:CanonicalizationMethod><ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"/><ds:Reference URI=\"#id-9b9cd0cc-18f6-4b22-ac64-943ef80a7b57\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/><ds:DigestValue>BuV/GQKBpULZGla+WAfwR0tdKehq0I+6SEN0Xe4T/YE=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>axDQ+++rzESn14J6cSJ5OWu5QPVVAuCRe+oqittLxVow2BbNDhSWrwaJvK2/FdL7Kh+NPLBhHxDEUM0q8Qt6SmQq5zb82jc61XYSefh2nJ8pmqsjeL1AI0rHXKTl+eD+7mi+QyNXuCqGdASOEvxULSXAHsnmkUu1/87I7/YCuqZKAlKJ+ig7kxLGF4BVz/tQqPJ6AysNoFFxuA5U3TrPDlsR9mhhJEjz69vOCcE7OSuboQdQNbzQ8os10aeZEDpo0YdUgb7akfzx+IXtWKpzR5QJMW+vsZzPqLxkcTR24+nKx/T1TwtjEGHgrZLZr1zVPftzSjTlRCISs8J8HnhLFg==</ds:SignatureValue><ds:KeyInfo Id=\"KI-b96da7b1-ab43-4bdf-9707-750e77e1261a\"><wsse:SecurityTokenReference wsu:Id=\"STR-45463ae7-911d-4475-a4f0-7f6774b3bb0e\"><ds:X509Data><ds:X509IssuerSerial><ds:X509IssuerName>CN=Intermediate Authority ADL Digital Lab</ds:X509IssuerName><ds:X509SerialNumber>391882954094950385893853264765133326889919201042</ds:X509SerialNumber></ds:X509IssuerSerial></ds:X509Data></wsse:SecurityTokenReference></ds:KeyInfo></ds:Signature></wsse:Security></SOAP-ENV:Header><SOAP-ENV:Body xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" wsu:Id=\"id-9b9cd0cc-18f6-4b22-ac64-943ef80a7b57\"><ns4:getPayrollLoanInterpreterRequest xmlns:ns2=\"urn://grupoaval.com/xsd/ifx/\" xmlns:ns3=\"urn://grupoaval.com/xsd/ifx/v2/\" xmlns:ns4=\"urn://grupoaval.com/prodschnsmngt/v1/\"><ns4:PayrollLoanInterpreterRq><ns2:RqUID>543998802</ns2:RqUID><ns2:MsgRqHdr><ns2:ClientApp><ns2:Org>ADL</ns2:Org><ns2:Name>ADL</ns2:Name><ns2:Version>1.0</ns2:Version></ns2:ClientApp><ns3:Channel>ADL</ns3:Channel><ns2:BankInfo><ns2:BankId>0002</ns2:BankId></ns2:BankInfo><ns3:ClientDt>2024-12-04T00:59:34.146-05:00</ns3:ClientDt><ns3:IPAddr>169.254.122.14</ns3:IPAddr><ns3:SessKey/><ns2:UserId><ns2:GovIssueIdent><ns2:GovIssueIdentType>CC</ns2:GovIssueIdentType><ns2:IdentSerialNum>500189</ns2:IdentSerialNum></ns2:GovIssueIdent></ns2:UserId><ns3:Reverse>false</ns3:Reverse><ns3:Language>es_CO</ns3:Language><ns3:NextDay>2024-12-04T00:59:34.146-05:00</ns3:NextDay></ns2:MsgRqHdr><ns2:CustId><ns2:GovIssueIdent><ns2:GovIssueIdentType>CC</ns2:GovIssueIdentType><ns2:IdentSerialNum>500189</ns2:IdentSerialNum></ns2:GovIssueIdent></ns2:CustId><ns2:PayrollLoanInterpreterInfo><ns2:EarnedAmt>15000000</ns2:EarnedAmt><ns2:DeductibleAmt>12000000</ns2:DeductibleAmt><ns2:NetAmt>0</ns2:NetAmt><ns2:Regimen>1</ns2:Regimen><ns2:PensionType>0</ns2:PensionType><ns2:Salary>0</ns2:Salary><ns2:Paymaster><ns2:NitPaymaster>9003360047</ns2:NitPaymaster><ns2:NamePaymaster>COLPENSIONES</ns2:NamePaymaster></ns2:Paymaster><ns2:FinancialConceptList><ns2:FinancialConceptData><ns2:ConceptId>9501</ns2:ConceptId><ns2:ConceptName>AUXILIO FUNERARIO INVALIDEZ</ns2:ConceptName><ns2:TrnType>1</ns2:TrnType><ns2:ConceptValue>5000000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>9501</ns2:ConceptId><ns2:ConceptName>SOBREV PENSIONADO-RIESGO 600</ns2:ConceptName><ns2:TrnType>1</ns2:TrnType><ns2:ConceptValue>5000000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>9501</ns2:ConceptId><ns2:ConceptName>MEDIMAS EPS SAS</ns2:ConceptName><ns2:TrnType>1</ns2:TrnType><ns2:ConceptValue>5000000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>9501</ns2:ConceptId><ns2:ConceptName>INVALIDEZ DECRETO 3041</ns2:ConceptName><ns2:TrnType>2</ns2:TrnType><ns2:ConceptValue>4000000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>9501</ns2:ConceptId><ns2:ConceptName>NUEVA EPS</ns2:ConceptName><ns2:TrnType>2</ns2:TrnType><ns2:ConceptValue>4000000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>9501</ns2:ConceptId><ns2:ConceptName>CODEMA -COOPERATIVA DE</ns2:ConceptName><ns2:TrnType>2</ns2:TrnType><ns2:ConceptValue>4000000</ns2:ConceptValue></ns2:FinancialConceptData></ns2:FinancialConceptList></ns2:PayrollLoanInterpreterInfo></ns4:PayrollLoanInterpreterRq></ns4:getPayrollLoanInterpreterRequest></SOAP-ENV:Body></SOAP-ENV:Envelope>";
    private static final String COLPENSIONES_REQUEST_EXT = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ifx=\"urn://grupoaval.com/xsd/ifx/\" xmlns:v2=\"urn://grupoaval.com/xsd/ifx/v2/\"><SOAP-ENV:Header><wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" SOAP-ENV:mustUnderstand=\"1\"><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Id=\"SIG-616d6f4d-4d39-44cb-a8d4-bffb2fc336e3\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"><ec:InclusiveNamespaces xmlns:ec=\"http://www.w3.org/2001/10/xml-exc-c14n#\" PrefixList=\"SOAP-ENV\"/></ds:CanonicalizationMethod><ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"/><ds:Reference URI=\"#id-3a67ff22-7aee-4749-b0c4-a8b7d7497934\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/><ds:DigestValue>vMZyupWylj0rxLCoHk4kZqBCmI8jVe1x9vXSP3zql54=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>BrXwPTuDTfEYsBhYGHJbOFiszpTDv5cBxUIyRJGcQ/QGixPFXTcM4Hun2OalKV0fF5gI/zqDLLb9ueNc/F2kSfP544Bj5MGC5veP5jr0R29sQTroJ/29CgUoezIdn0fnJuSTGWY5ct58tKOFZy3c7Bo5wTkrdFLSLpcXbh/c/q2b2vqNU/c+yrIqbl3VFqFI0+7JU1hXq9rUjpqos9CJPL2gvcAARrhlOKm/tC0ChkuLL7gE8N5ZFIgGX4Lpfbral27XxuvMLpHpWJ5J9uW1ciFSS1J4Sx4C6qR2t3bJj9elX0FbHGRSKZXTeY8xHihxvNVYsaAavgzEsZqTH7I1Yg==</ds:SignatureValue><ds:KeyInfo Id=\"KI-3010697b-07a8-45f1-b777-c39ecd1b84dd\"><wsse:SecurityTokenReference wsu:Id=\"STR-46f69163-46d6-41c5-8bbf-b2a7c1fd4d81\"><ds:X509Data><ds:X509IssuerSerial><ds:X509IssuerName>CN=Intermediate Authority ADL Digital Lab</ds:X509IssuerName><ds:X509SerialNumber>391882954094950385893853264765133326889919201042</ds:X509SerialNumber></ds:X509IssuerSerial></ds:X509Data></wsse:SecurityTokenReference></ds:KeyInfo></ds:Signature></wsse:Security></SOAP-ENV:Header><SOAP-ENV:Body xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" wsu:Id=\"id-3a67ff22-7aee-4749-b0c4-a8b7d7497934\"><ns4:getPayrollLoanInterpreterRequest xmlns:ns2=\"urn://grupoaval.com/xsd/ifx/\" xmlns:ns3=\"urn://grupoaval.com/xsd/ifx/v2/\" xmlns:ns4=\"urn://grupoaval.com/prodschnsmngt/v1/\"><ns4:PayrollLoanInterpreterRq><ns2:RqUID>834916289</ns2:RqUID><ns2:MsgRqHdr><ns2:ClientApp><ns2:Org>ADL</ns2:Org><ns2:Name>ADL</ns2:Name><ns2:Version>1.0</ns2:Version></ns2:ClientApp><ns3:Channel>ADL</ns3:Channel><ns2:BankInfo><ns2:BankId>0002</ns2:BankId></ns2:BankInfo><ns3:ClientDt>2025-01-17T11:19:59.277-05:00</ns3:ClientDt><ns3:IPAddr>169.254.48.35</ns3:IPAddr><ns3:SessKey/><ns2:UserId><ns2:GovIssueIdent><ns2:GovIssueIdentType>CC</ns2:GovIssueIdentType><ns2:IdentSerialNum>500035</ns2:IdentSerialNum></ns2:GovIssueIdent></ns2:UserId><ns3:Reverse>false</ns3:Reverse><ns3:Language>es_CO</ns3:Language><ns3:NextDay>2025-01-17T11:19:59.277-05:00</ns3:NextDay></ns2:MsgRqHdr><ns2:CustId><ns2:GovIssueIdent><ns2:GovIssueIdentType>CC</ns2:GovIssueIdentType><ns2:IdentSerialNum>500035</ns2:IdentSerialNum></ns2:GovIssueIdent></ns2:CustId><ns2:PayrollLoanInterpreterInfo><ns2:EarnedAmt>18000000</ns2:EarnedAmt><ns2:DeductibleAmt>9000000</ns2:DeductibleAmt><ns2:NetAmt>0</ns2:NetAmt><ns2:Regimen>1</ns2:Regimen><ns2:PensionType>0</ns2:PensionType><ns2:Salary>0</ns2:Salary><ns2:Paymaster><ns2:NitPaymaster>9003360047</ns2:NitPaymaster><ns2:NamePaymaster>COLPENSIONES</ns2:NamePaymaster></ns2:Paymaster><ns2:FinancialConceptList><ns2:FinancialConceptData><ns2:ConceptId>8501</ns2:ConceptId><ns2:ConceptName>AUXILIO FUNERARIO INVALIDEZ</ns2:ConceptName><ns2:TrnType>1</ns2:TrnType><ns2:ConceptValue>6000000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>8501</ns2:ConceptId><ns2:ConceptName>SOBREV PENSIONADO-RIESGO 600</ns2:ConceptName><ns2:TrnType>1</ns2:TrnType><ns2:ConceptValue>6000000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>8501</ns2:ConceptId><ns2:ConceptName>MEDIMAS EPS SAS</ns2:ConceptName><ns2:TrnType>1</ns2:TrnType><ns2:ConceptValue>6000000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>7501</ns2:ConceptId><ns2:ConceptName>INVALIDEZ DECRETO 3041</ns2:ConceptName><ns2:TrnType>2</ns2:TrnType><ns2:ConceptValue>3000000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>7501</ns2:ConceptId><ns2:ConceptName>NUEVA EPS</ns2:ConceptName><ns2:TrnType>2</ns2:TrnType><ns2:ConceptValue>3000000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>7501</ns2:ConceptId><ns2:ConceptName>CODEMA -COOPERATIVA DE</ns2:ConceptName><ns2:TrnType>2</ns2:TrnType><ns2:ConceptValue>3000000</ns2:ConceptValue></ns2:FinancialConceptData></ns2:FinancialConceptList></ns2:PayrollLoanInterpreterInfo></ns4:PayrollLoanInterpreterRq></ns4:getPayrollLoanInterpreterRequest></SOAP-ENV:Body></SOAP-ENV:Envelope>";
    private static final String FOPEP_REQUEST = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ifx=\"urn://grupoaval.com/xsd/ifx/\" xmlns:v2=\"urn://grupoaval.com/xsd/ifx/v2/\"><SOAP-ENV:Header><wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" SOAP-ENV:mustUnderstand=\"1\"><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Id=\"SIG-bb2f1ea4-b958-4673-8ee6-183f8aa1c8bf\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"><ec:InclusiveNamespaces xmlns:ec=\"http://www.w3.org/2001/10/xml-exc-c14n#\" PrefixList=\"SOAP-ENV\"/></ds:CanonicalizationMethod><ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"/><ds:Reference URI=\"#id-a1c1a528-f411-4d57-b0b0-62996e401d23\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/><ds:DigestValue>RBO8UEf6xHx1vCd3Yv4eViaD3CvhNpP7yVu1sy6W4Cc=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>nsfbU9/IlK5IYEhvdSz5EMjDbKFffyb8y1icIBe57tDGa89cTAxLZsb5WU7JyZpLy+YWt5xn1tWX2KJJjhXxNQaLfYoTH772sCNXXiqyJeX/ZbVLfZ3Okqkfw5xE9kZgGcX6bgmpdtjEFeN28cAeldxk89H1CvU52fVh+8z8hMdjQ1xBdDoZOah9KNE2T42C8uBxid8/G/CSPtsLfsSKqiNAeuCP5xcf+6VufHuOEuyYSKxFsnmh3B3f/VGzstcxPZexeEEYM4T9jfBvW9omigbtr4hcvWgpd3v/8SB5XMI8Y8OmEBEeaxSJb3D12Mrcuex5VTn8PYe032iaFwSvZA==</ds:SignatureValue><ds:KeyInfo Id=\"KI-a36cda85-55b1-4a8d-baa2-4441314718bd\"><wsse:SecurityTokenReference wsu:Id=\"STR-34400fde-d7fe-455f-8f5f-c50cc4be8df9\"><ds:X509Data><ds:X509IssuerSerial><ds:X509IssuerName>CN=Intermediate Authority ADL Digital Lab</ds:X509IssuerName><ds:X509SerialNumber>391882954094950385893853264765133326889919201042</ds:X509SerialNumber></ds:X509IssuerSerial></ds:X509Data></wsse:SecurityTokenReference></ds:KeyInfo></ds:Signature></wsse:Security></SOAP-ENV:Header><SOAP-ENV:Body xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" wsu:Id=\"id-a1c1a528-f411-4d57-b0b0-62996e401d23\"><ns4:getPayrollLoanInterpreterRequest xmlns:ns2=\"urn://grupoaval.com/xsd/ifx/\" xmlns:ns3=\"urn://grupoaval.com/xsd/ifx/v2/\" xmlns:ns4=\"urn://grupoaval.com/prodschnsmngt/v1/\"><ns4:PayrollLoanInterpreterRq><ns2:RqUID>787482623</ns2:RqUID><ns2:MsgRqHdr><ns2:ClientApp><ns2:Org>ADL</ns2:Org><ns2:Name>ADL</ns2:Name><ns2:Version>1.0</ns2:Version></ns2:ClientApp><ns3:Channel>ADL</ns3:Channel><ns2:BankInfo><ns2:BankId>0002</ns2:BankId></ns2:BankInfo><ns3:ClientDt>2024-12-09T11:08:51.198-05:00</ns3:ClientDt><ns3:IPAddr>169.254.54.110</ns3:IPAddr><ns3:SessKey/><ns2:UserId><ns2:GovIssueIdent><ns2:GovIssueIdentType>CC</ns2:GovIssueIdentType><ns2:IdentSerialNum>500190</ns2:IdentSerialNum></ns2:GovIssueIdent></ns2:UserId><ns3:Reverse>false</ns3:Reverse><ns3:Language>es_CO</ns3:Language><ns3:NextDay>2024-12-09T11:08:51.198-05:00</ns3:NextDay></ns2:MsgRqHdr><ns2:CustId><ns2:GovIssueIdent><ns2:GovIssueIdentType>CC</ns2:GovIssueIdentType><ns2:IdentSerialNum>500190</ns2:IdentSerialNum></ns2:GovIssueIdent></ns2:CustId><ns2:PayrollLoanInterpreterInfo><ns2:EarnedAmt>4900000</ns2:EarnedAmt><ns2:DeductibleAmt>200000</ns2:DeductibleAmt><ns2:NetAmt>0</ns2:NetAmt><ns2:Regimen>1</ns2:Regimen><ns2:PensionType>0</ns2:PensionType><ns2:Salary>0</ns2:Salary><ns2:Paymaster><ns2:NitPaymaster>9016596505</ns2:NitPaymaster><ns2:NamePaymaster>CONSORCIO FOPEP 2022</ns2:NamePaymaster></ns2:Paymaster><ns2:FinancialConceptList><ns2:FinancialConceptData><ns2:ConceptId>410</ns2:ConceptId><ns2:ConceptName>JUBILACION NAL</ns2:ConceptName><ns2:TrnType>1</ns2:TrnType><ns2:ConceptValue>4900000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>430</ns2:ConceptId><ns2:ConceptName>EPS SANITAS</ns2:ConceptName><ns2:TrnType>2</ns2:TrnType><ns2:ConceptValue>200000</ns2:ConceptValue></ns2:FinancialConceptData></ns2:FinancialConceptList></ns2:PayrollLoanInterpreterInfo></ns4:PayrollLoanInterpreterRq></ns4:getPayrollLoanInterpreterRequest></SOAP-ENV:Body></SOAP-ENV:Envelope>";
    private static final String FOPEP_REQUEST_EXT ="";
    private static final String FIDUPREVISORA_REQUEST = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ifx=\"urn://grupoaval.com/xsd/ifx/\" xmlns:v2=\"urn://grupoaval.com/xsd/ifx/v2/\"><SOAP-ENV:Header><wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" SOAP-ENV:mustUnderstand=\"1\"><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Id=\"SIG-efe2d4b4-1501-4b68-933d-768cab824d2d\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"><ec:InclusiveNamespaces xmlns:ec=\"http://www.w3.org/2001/10/xml-exc-c14n#\" PrefixList=\"SOAP-ENV\"/></ds:CanonicalizationMethod><ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"/><ds:Reference URI=\"#id-6790fa88-c2a1-4a11-a49e-38bf296f1936\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/><ds:DigestValue>MpKwWalUImnR2DDiln/87lrunT4jBiWacIR6zzZ9R60=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>mxGlmiCOBPhbHyYp189QxkTZsQ8xn2qYLCkHnNp5LKAC1z7lFWLWmoGE8pyF6Ax5RUVPM6b/sU5nwqkFTz3mnJgS9QR1kNXTAq0+NwMFpm+Yy2d9xwW9Q2pcBdfXthFr+ZmWH9M0BOJsdZJ3JE/WUlkw8Exg/mrKF4ck/3EZ/++rETwrfLyFDMSef9KcUcf85+SIN6FnTPlWqSwrKMxGemG/2qEHu4gJ280n5I0zHK1OuzeZgaGeeo1ikTQZkDNVk8JpMB/7C4v6D8r6FXrSx7LWJ1Z6p9bp+ZQAui/qbIi1MU8ztULlLx6/OJOXbxJgmjvIGxisvYYW0Bael43sig==</ds:SignatureValue><ds:KeyInfo Id=\"KI-58c9b7e9-a46b-4415-bfc1-f32633fbd4bc\"><wsse:SecurityTokenReference wsu:Id=\"STR-ba0b17a4-5765-4bb7-90f9-8469a5a6d4f2\"><ds:X509Data><ds:X509IssuerSerial><ds:X509IssuerName>CN=Intermediate Authority ADL Digital Lab</ds:X509IssuerName><ds:X509SerialNumber>391882954094950385893853264765133326889919201042</ds:X509SerialNumber></ds:X509IssuerSerial></ds:X509Data></wsse:SecurityTokenReference></ds:KeyInfo></ds:Signature></wsse:Security></SOAP-ENV:Header><SOAP-ENV:Body xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" wsu:Id=\"id-6790fa88-c2a1-4a11-a49e-38bf296f1936\"><ns4:getPayrollLoanInterpreterRequest xmlns:ns2=\"urn://grupoaval.com/xsd/ifx/\" xmlns:ns3=\"urn://grupoaval.com/xsd/ifx/v2/\" xmlns:ns4=\"urn://grupoaval.com/prodschnsmngt/v1/\"><ns4:PayrollLoanInterpreterRq><ns2:RqUID>513102382</ns2:RqUID><ns2:MsgRqHdr><ns2:ClientApp><ns2:Org>ADL</ns2:Org><ns2:Name>ADL</ns2:Name><ns2:Version>1.0</ns2:Version></ns2:ClientApp><ns3:Channel>ADL</ns3:Channel><ns2:BankInfo><ns2:BankId>0002</ns2:BankId></ns2:BankInfo><ns3:ClientDt>2024-12-10T12:23:29.519-05:00</ns3:ClientDt><ns3:IPAddr>169.254.181.118</ns3:IPAddr><ns3:SessKey/><ns2:UserId><ns2:GovIssueIdent><ns2:GovIssueIdentType>CC</ns2:GovIssueIdentType><ns2:IdentSerialNum>500191</ns2:IdentSerialNum></ns2:GovIssueIdent></ns2:UserId><ns3:Reverse>false</ns3:Reverse><ns3:Language>es_CO</ns3:Language><ns3:NextDay>2024-12-10T12:23:29.519-05:00</ns3:NextDay></ns2:MsgRqHdr><ns2:CustId><ns2:GovIssueIdent><ns2:GovIssueIdentType>CC</ns2:GovIssueIdentType><ns2:IdentSerialNum>500191</ns2:IdentSerialNum></ns2:GovIssueIdent></ns2:CustId><ns2:PayrollLoanInterpreterInfo><ns2:EarnedAmt>5544369</ns2:EarnedAmt><ns2:DeductibleAmt>1729384</ns2:DeductibleAmt><ns2:NetAmt>0</ns2:NetAmt><ns2:Regimen>1</ns2:Regimen><ns2:PensionType>0</ns2:PensionType><ns2:Salary>0</ns2:Salary><ns2:Paymaster><ns2:NitPaymaster>8605251485</ns2:NitPaymaster><ns2:NamePaymaster>FIDUPREVISORA FONDO PENSIONES MAGISTERIO</ns2:NamePaymaster></ns2:Paymaster><ns2:FinancialConceptList><ns2:FinancialConceptData><ns2:ConceptId>AS006</ns2:ConceptId><ns2:ConceptName>CODEMA -2000 2000</ns2:ConceptName><ns2:TrnType>2</ns2:TrnType><ns2:ConceptValue>83000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>AS007</ns2:ConceptId><ns2:ConceptName>CODEMA -COOPERATIVA DE</ns2:ConceptName><ns2:TrnType>2</ns2:TrnType><ns2:ConceptValue>781060</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>AS082</ns2:ConceptId><ns2:ConceptName>CANAPRO - COOP CASA NA</ns2:ConceptName><ns2:TrnType>2</ns2:TrnType><ns2:ConceptValue>200000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>CR021</ns2:ConceptId><ns2:ConceptName>APORTE DE LEY</ns2:ConceptName><ns2:TrnType>2</ns2:TrnType><ns2:ConceptValue>665324</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>PER</ns2:ConceptId><ns2:ConceptName>RELIQUIDACION PENSIONA</ns2:ConceptName><ns2:TrnType>1</ns2:TrnType><ns2:ConceptValue>5544369</ns2:ConceptValue></ns2:FinancialConceptData></ns2:FinancialConceptList></ns2:PayrollLoanInterpreterInfo></ns4:PayrollLoanInterpreterRq></ns4:getPayrollLoanInterpreterRequest></SOAP-ENV:Body></SOAP-ENV:Envelope>";
    private static final String FIDUPREVISORA_REQUEST_EXT="<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ifx=\"urn://grupoaval.com/xsd/ifx/\" xmlns:v2=\"urn://grupoaval.com/xsd/ifx/v2/\"><SOAP-ENV:Header><wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" SOAP-ENV:mustUnderstand=\"1\"><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Id=\"SIG-f02b3aff-737a-425d-8df1-7b5764453777\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"><ec:InclusiveNamespaces xmlns:ec=\"http://www.w3.org/2001/10/xml-exc-c14n#\" PrefixList=\"SOAP-ENV\"/></ds:CanonicalizationMethod><ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"/><ds:Reference URI=\"#id-759c2f79-b844-4898-b361-bc980674fb13\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/><ds:DigestValue>KYz2PFpvo3rY9/qnrAOq7H74YCqV5+aA5bclpDg5wuo=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>QlpXmJ+ykwMiVVgo/VzclDAm9npVAnDtZ//EwO8In8Vm2HXThMrnMyJ567FkdqT5oBRRKdm0OLCPg3VKRfg/94ZDgiEnVPMQeH4G3iwGVLQdkrjEwtwiJbF+RcVBzeARYpwjYodtoYFUfZsia+0elECu94blBqcjfpuXpaU61QgJv0+sz04/poptm1eyJ1d6vhCtFzIuIeNOuBOPftg3aiayXSPxfR0JpQtBb+FSWpGWUS/h4Nop3+UmUAbWzbkU1jMmDVcLA1azUiCw09qbrkOXuPFkR4AUQx6GWL5SRstxRxIKux1KhvEzDWA/L8HQVGJ2wqrIeKzOkWHOiB/3eg==</ds:SignatureValue><ds:KeyInfo Id=\"KI-88d54c12-91b9-473d-903f-2c5dcfe0a0cb\"><wsse:SecurityTokenReference wsu:Id=\"STR-3aefef1e-df33-4cd8-84af-7ec8fd4b5ccf\"><ds:X509Data><ds:X509IssuerSerial><ds:X509IssuerName>CN=Intermediate Authority ADL Digital Lab</ds:X509IssuerName><ds:X509SerialNumber>285816463554313791323646107715685151337474829468</ds:X509SerialNumber></ds:X509IssuerSerial></ds:X509Data></wsse:SecurityTokenReference></ds:KeyInfo></ds:Signature></wsse:Security></SOAP-ENV:Header><SOAP-ENV:Body xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" wsu:Id=\"id-759c2f79-b844-4898-b361-bc980674fb13\"><ns4:getPayrollLoanInterpreterRequest xmlns:ns2=\"urn://grupoaval.com/xsd/ifx/\" xmlns:ns3=\"urn://grupoaval.com/xsd/ifx/v2/\" xmlns:ns4=\"urn://grupoaval.com/prodschnsmngt/v1/\"><ns4:PayrollLoanInterpreterRq><ns2:RqUID>148816924</ns2:RqUID><ns2:MsgRqHdr><ns2:ClientApp><ns2:Org>ADL</ns2:Org><ns2:Name>ADL</ns2:Name><ns2:Version>1.0</ns2:Version></ns2:ClientApp><ns3:Channel>ADL</ns3:Channel><ns2:BankInfo><ns2:BankId>0002</ns2:BankId></ns2:BankInfo><ns3:ClientDt>2025-09-19T19:18:33.825-05:00</ns3:ClientDt><ns3:IPAddr>169.254.59.73</ns3:IPAddr><ns3:SessKey/><ns2:UserId><ns2:GovIssueIdent><ns2:GovIssueIdentType>CC</ns2:GovIssueIdentType><ns2:IdentSerialNum>500166</ns2:IdentSerialNum></ns2:GovIssueIdent></ns2:UserId><ns3:Reverse>false</ns3:Reverse><ns3:Language>es_CO</ns3:Language><ns3:NextDay>2025-09-19T19:18:33.825-05:00</ns3:NextDay></ns2:MsgRqHdr><ns2:CustId><ns2:GovIssueIdent><ns2:GovIssueIdentType>CC</ns2:GovIssueIdentType><ns2:IdentSerialNum>500166</ns2:IdentSerialNum></ns2:GovIssueIdent></ns2:CustId><ns2:PayrollLoanInterpreterInfo><ns2:EarnedAmt>5544369</ns2:EarnedAmt><ns2:DeductibleAmt>1729384</ns2:DeductibleAmt><ns2:NetAmt>0</ns2:NetAmt><ns2:Regimen>1</ns2:Regimen><ns2:PensionType>0</ns2:PensionType><ns2:Salary>0</ns2:Salary><ns2:Paymaster><ns2:NitPaymaster>8605251485</ns2:NitPaymaster><ns2:NamePaymaster>FIDUPREVISORA FONDO PENSIONES MAGISTERIO</ns2:NamePaymaster></ns2:Paymaster><ns2:FinancialConceptList><ns2:FinancialConceptData><ns2:ConceptId>AS007</ns2:ConceptId><ns2:ConceptName>CODEMA -COOPERATIVA DE</ns2:ConceptName><ns2:TrnType>2</ns2:TrnType><ns2:ConceptValue>83000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>AS007</ns2:ConceptId><ns2:ConceptName>CODEMA -COOPERATIVA DE</ns2:ConceptName><ns2:TrnType>2</ns2:TrnType><ns2:ConceptValue>781060</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>AS082</ns2:ConceptId><ns2:ConceptName>CANAPRO - COOP CASA NA</ns2:ConceptName><ns2:TrnType>2</ns2:TrnType><ns2:ConceptValue>200000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>CR021</ns2:ConceptId><ns2:ConceptName>APORTE DE LEY</ns2:ConceptName><ns2:TrnType>2</ns2:TrnType><ns2:ConceptValue>665324</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>PER</ns2:ConceptId><ns2:ConceptName>RELIQUIDACION PENSIONA</ns2:ConceptName><ns2:TrnType>1</ns2:TrnType><ns2:ConceptValue>5544369</ns2:ConceptValue></ns2:FinancialConceptData></ns2:FinancialConceptList></ns2:PayrollLoanInterpreterInfo></ns4:PayrollLoanInterpreterRq></ns4:getPayrollLoanInterpreterRequest></SOAP-ENV:Body></SOAP-ENV:Envelope>";
    private static final String POSITIVA_REQUEST= "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ifx=\"urn://grupoaval.com/xsd/ifx/\" xmlns:v2=\"urn://grupoaval.com/xsd/ifx/v2/\"><SOAP-ENV:Header><wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" SOAP-ENV:mustUnderstand=\"1\"><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Id=\"SIG-fc4b0ddd-6fdd-450f-88fc-5cb21e707aeb\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"><ec:InclusiveNamespaces xmlns:ec=\"http://www.w3.org/2001/10/xml-exc-c14n#\" PrefixList=\"SOAP-ENV\"/></ds:CanonicalizationMethod><ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"/><ds:Reference URI=\"#id-6dde7094-1e79-4abc-844c-3226ecffd7db\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/><ds:DigestValue>4RHzN4UrCLqACg//CX+Ud3f7JSp7GR1T0nO9IhAdIOM=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>PJQTzzfcGYwHLsNtggy4goWh4wSO7FmiQqtGDuWkcF+cZ8Gpo5knVoV42vQhQYh1SO2c6XqIRfRJJuI8kTGc6RWdswE9R99gDEMlV6Fc28+Xku8adaClm6BFhpAZ1IX4VZr6IgPXjHKS1giQW80mfn6oIl3Z/JZ4ASHKOoTw50qpQ6fiRPEHFHrEG3QPyrFzUT1EDAypXhYwbgAgPsKSGApOECj5Jzq7xwJdF2n8t3i/bMtVd3OBAbw3EEAXiRIiHnexMS194j9otoAWeRNHBK55e7vnOSMW+R7yNDH0sU3+vSuXSvB/MfDHrFhz6pPtM7IGRcne4azcgZpQJD9iCw==</ds:SignatureValue><ds:KeyInfo Id=\"KI-b3441228-6d98-4f03-ad2f-682fc8f2787d\"><wsse:SecurityTokenReference wsu:Id=\"STR-fe95ddca-b464-4645-ba5c-ba8c85328627\"><ds:X509Data><ds:X509IssuerSerial><ds:X509IssuerName>CN=Intermediate Authority ADL Digital Lab</ds:X509IssuerName><ds:X509SerialNumber>391882954094950385893853264765133326889919201042</ds:X509SerialNumber></ds:X509IssuerSerial></ds:X509Data></wsse:SecurityTokenReference></ds:KeyInfo></ds:Signature></wsse:Security></SOAP-ENV:Header><SOAP-ENV:Body xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" wsu:Id=\"id-6dde7094-1e79-4abc-844c-3226ecffd7db\"><ns4:getPayrollLoanInterpreterRequest xmlns:ns2=\"urn://grupoaval.com/xsd/ifx/\" xmlns:ns3=\"urn://grupoaval.com/xsd/ifx/v2/\" xmlns:ns4=\"urn://grupoaval.com/prodschnsmngt/v1/\"><ns4:PayrollLoanInterpreterRq><ns2:RqUID>297213684</ns2:RqUID><ns2:MsgRqHdr><ns2:ClientApp><ns2:Org>ADL</ns2:Org><ns2:Name>ADL</ns2:Name><ns2:Version>1.0</ns2:Version></ns2:ClientApp><ns3:Channel>ADL</ns3:Channel><ns2:BankInfo><ns2:BankId>0002</ns2:BankId></ns2:BankInfo><ns3:ClientDt>2024-12-10T16:19:30.484-05:00</ns3:ClientDt><ns3:IPAddr>169.254.64.250</ns3:IPAddr><ns3:SessKey/><ns2:UserId><ns2:GovIssueIdent><ns2:GovIssueIdentType>CC</ns2:GovIssueIdentType><ns2:IdentSerialNum>500193</ns2:IdentSerialNum></ns2:GovIssueIdent></ns2:UserId><ns3:Reverse>false</ns3:Reverse><ns3:Language>es_CO</ns3:Language><ns3:NextDay>2024-12-10T16:19:30.484-05:00</ns3:NextDay></ns2:MsgRqHdr><ns2:CustId><ns2:GovIssueIdent><ns2:GovIssueIdentType>CC</ns2:GovIssueIdentType><ns2:IdentSerialNum>500193</ns2:IdentSerialNum></ns2:GovIssueIdent></ns2:CustId><ns2:PayrollLoanInterpreterInfo><ns2:EarnedAmt>15000000</ns2:EarnedAmt><ns2:DeductibleAmt>12000000</ns2:DeductibleAmt><ns2:NetAmt>0</ns2:NetAmt><ns2:Regimen>1</ns2:Regimen><ns2:PensionType>0</ns2:PensionType><ns2:Salary>0</ns2:Salary><ns2:Paymaster><ns2:NitPaymaster>8600111536</ns2:NitPaymaster><ns2:NamePaymaster>POSITIVA COMPAÑÍA DE SEGUROS S.A. PENSIONADOS</ns2:NamePaymaster></ns2:Paymaster><ns2:FinancialConceptList><ns2:FinancialConceptData><ns2:ConceptId>CV099</ns2:ConceptId><ns2:ConceptName>AUXILIO FUNERARIO INVALIDEZ</ns2:ConceptName><ns2:TrnType>1</ns2:TrnType><ns2:ConceptValue>5000000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>CV100</ns2:ConceptId><ns2:ConceptName>SOBREV PENSIONADO-RIESGO 600</ns2:ConceptName><ns2:TrnType>1</ns2:TrnType><ns2:ConceptValue>5000000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>CV101</ns2:ConceptId><ns2:ConceptName>MEDIMAS EPS SAS</ns2:ConceptName><ns2:TrnType>1</ns2:TrnType><ns2:ConceptValue>5000000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>CV102</ns2:ConceptId><ns2:ConceptName>INVALIDEZ DECRETO 3041</ns2:ConceptName><ns2:TrnType>2</ns2:TrnType><ns2:ConceptValue>4000000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>CV103</ns2:ConceptId><ns2:ConceptName>NUEVA EPS</ns2:ConceptName><ns2:TrnType>2</ns2:TrnType><ns2:ConceptValue>4000000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>CV104</ns2:ConceptId><ns2:ConceptName>CODEMA -COOPERATIVA DE</ns2:ConceptName><ns2:TrnType>2</ns2:TrnType><ns2:ConceptValue>4000000</ns2:ConceptValue></ns2:FinancialConceptData></ns2:FinancialConceptList></ns2:PayrollLoanInterpreterInfo></ns4:PayrollLoanInterpreterRq></ns4:getPayrollLoanInterpreterRequest></SOAP-ENV:Body></SOAP-ENV:Envelope>";
    private static final String POSITIVA_REQUEST_EXT="<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ifx=\"urn://grupoaval.com/xsd/ifx/\" xmlns:v2=\"urn://grupoaval.com/xsd/ifx/v2/\"><SOAP-ENV:Header><wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" SOAP-ENV:mustUnderstand=\"1\"><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Id=\"SIG-9d4e4653-c9a6-4d7f-8259-16ff80a52d94\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"><ec:InclusiveNamespaces xmlns:ec=\"http://www.w3.org/2001/10/xml-exc-c14n#\" PrefixList=\"SOAP-ENV\"/></ds:CanonicalizationMethod><ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"/><ds:Reference URI=\"#id-1f19cefc-cb12-4777-91a4-04b46eab64d4\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/><ds:DigestValue>wBAIRDhj3NC7KuSdeYz0Pu6WFCR0KVown9WcCqveopY=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>uPQnYcQkFvoJ/JRjkhqt52xGDJEnNe7TYZRJSaf68CViQv0WmNe5xGUoWqScoGVguJNFE3Zf5COvqaTdzmf8lKs7DsuIC3Y/ZmGjQxylnLCb2Vtp6LEUtZYBXlIUMkK+9hClgiHdFwGSXEYlGXaa+n3Mvlta2pxg/06AcrTHFUKg4ZCXbPWLAa+nh8t512xrhhRiV7RegSNQTqVkq42dw+/PK+JCOTEep5/ymbtSxoEI1mP4uftAbzbC4aC+UboZ9Kk6WKPTaMgYWvHzwU63wuEw71EL22PvbERYuR36WEX0jaDunYN+3j4qPzGfPDU9iGPyDNJ/YkvdOCKkfen1+w==</ds:SignatureValue><ds:KeyInfo Id=\"KI-a735b4c5-44c3-42e5-b770-8d2e049f6c37\"><wsse:SecurityTokenReference wsu:Id=\"STR-398d503e-a867-439b-b220-387275e8c8e2\"><ds:X509Data><ds:X509IssuerSerial><ds:X509IssuerName>CN=Intermediate Authority ADL Digital Lab</ds:X509IssuerName><ds:X509SerialNumber>285816463554313791323646107715685151337474829468</ds:X509SerialNumber></ds:X509IssuerSerial></ds:X509Data></wsse:SecurityTokenReference></ds:KeyInfo></ds:Signature></wsse:Security></SOAP-ENV:Header><SOAP-ENV:Body xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" wsu:Id=\"id-1f19cefc-cb12-4777-91a4-04b46eab64d4\"><ns4:getPayrollLoanInterpreterRequest xmlns:ns2=\"urn://grupoaval.com/xsd/ifx/\" xmlns:ns3=\"urn://grupoaval.com/xsd/ifx/v2/\" xmlns:ns4=\"urn://grupoaval.com/prodschnsmngt/v1/\"><ns4:PayrollLoanInterpreterRq><ns2:RqUID>854819045</ns2:RqUID><ns2:MsgRqHdr><ns2:ClientApp><ns2:Org>ADL</ns2:Org><ns2:Name>ADL</ns2:Name><ns2:Version>1.0</ns2:Version></ns2:ClientApp><ns3:Channel>ADL</ns3:Channel><ns2:BankInfo><ns2:BankId>0002</ns2:BankId></ns2:BankInfo><ns3:ClientDt>2025-09-19T21:03:29.710-05:00</ns3:ClientDt><ns3:IPAddr>169.254.82.101</ns3:IPAddr><ns3:SessKey/><ns2:UserId><ns2:GovIssueIdent><ns2:GovIssueIdentType>CC</ns2:GovIssueIdentType><ns2:IdentSerialNum>500171</ns2:IdentSerialNum></ns2:GovIssueIdent></ns2:UserId><ns3:Reverse>false</ns3:Reverse><ns3:Language>es_CO</ns3:Language><ns3:NextDay>2025-09-19T21:03:29.710-05:00</ns3:NextDay></ns2:MsgRqHdr><ns2:CustId><ns2:GovIssueIdent><ns2:GovIssueIdentType>CC</ns2:GovIssueIdentType><ns2:IdentSerialNum>500171</ns2:IdentSerialNum></ns2:GovIssueIdent></ns2:CustId><ns2:PayrollLoanInterpreterInfo><ns2:EarnedAmt>15000000</ns2:EarnedAmt><ns2:DeductibleAmt>12000000</ns2:DeductibleAmt><ns2:NetAmt>0</ns2:NetAmt><ns2:Regimen>1</ns2:Regimen><ns2:PensionType>0</ns2:PensionType><ns2:Salary>0</ns2:Salary><ns2:Paymaster><ns2:NitPaymaster>8600111536</ns2:NitPaymaster><ns2:NamePaymaster>POSITIVA COMPAÑÍA DE SEGUROS S.A. PENSIONADOS</ns2:NamePaymaster></ns2:Paymaster><ns2:FinancialConceptList><ns2:FinancialConceptData><ns2:ConceptId>9501</ns2:ConceptId><ns2:ConceptName>AUXILIO FUNERARIO INVALIDEZ</ns2:ConceptName><ns2:TrnType>1</ns2:TrnType><ns2:ConceptValue>5000000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>9501</ns2:ConceptId><ns2:ConceptName>SOBREV PENSIONADO-RIESGO 600</ns2:ConceptName><ns2:TrnType>1</ns2:TrnType><ns2:ConceptValue>5000000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>9501</ns2:ConceptId><ns2:ConceptName>MEDIMAS EPS SAS</ns2:ConceptName><ns2:TrnType>1</ns2:TrnType><ns2:ConceptValue>5000000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>9501</ns2:ConceptId><ns2:ConceptName>INVALIDEZ DECRETO 3041</ns2:ConceptName><ns2:TrnType>2</ns2:TrnType><ns2:ConceptValue>4000000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>9501</ns2:ConceptId><ns2:ConceptName>NUEVA EPS</ns2:ConceptName><ns2:TrnType>2</ns2:TrnType><ns2:ConceptValue>4000000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>9501</ns2:ConceptId><ns2:ConceptName>CODEMA -COOPERATIVA DE</ns2:ConceptName><ns2:TrnType>2</ns2:TrnType><ns2:ConceptValue>4000000</ns2:ConceptValue></ns2:FinancialConceptData></ns2:FinancialConceptList></ns2:PayrollLoanInterpreterInfo></ns4:PayrollLoanInterpreterRq></ns4:getPayrollLoanInterpreterRequest></SOAP-ENV:Body></SOAP-ENV:Envelope>";
    private static final String FERROCARRILES_REQUEST = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ifx=\"urn://grupoaval.com/xsd/ifx/\" xmlns:v2=\"urn://grupoaval.com/xsd/ifx/v2/\"><SOAP-ENV:Header><wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" SOAP-ENV:mustUnderstand=\"1\"><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Id=\"SIG-2482f9ba-894a-4856-9fb9-d2c54201824c\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"><ec:InclusiveNamespaces xmlns:ec=\"http://www.w3.org/2001/10/xml-exc-c14n#\" PrefixList=\"SOAP-ENV\"/></ds:CanonicalizationMethod><ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"/><ds:Reference URI=\"#id-59d0408f-1813-4a5b-ae68-045e9672c340\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/><ds:DigestValue>qclwuyW26R4S8bOFAf2Ft2H9dY6JpiLkHgAiiLEXz00=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>X8+eu3q1US+9AzTtr5Zf2dORZ5391ki02anLM4ZoANnw/oOE4o/ypzgPLtV9jQJV5x4z7vrv3p4t9y4uwtCvXSLPlAvt3YmIKh4TXnTe2TG7l9mRr+/gvZhFYscRlXAS8XDAwijPmNkgEH2kHstqP7ysuULmrW6dBXHa3XGfjGM273XoMWXT8AQ46YiwoQvcoOqBvzxDYz0K77E3s9EI6pGYXukLTVWlttywt6XA1HWq/qeHS2YeY5RJmrvJzOEAJPOcoLwPYNxgyId6FJX6zY6p5cZmLuoCTokf3xbD+pfhK8VZoXzvnagMc7lg7KS6jBZy9xn3r88SPXk7oyvXAA==</ds:SignatureValue><ds:KeyInfo Id=\"KI-842de8a1-01c0-488f-9b06-fab84f87f865\"><wsse:SecurityTokenReference wsu:Id=\"STR-76f7365f-5eb1-42da-87fe-e08a6989dc77\"><ds:X509Data><ds:X509IssuerSerial><ds:X509IssuerName>CN=Intermediate Authority ADL Digital Lab</ds:X509IssuerName><ds:X509SerialNumber>391882954094950385893853264765133326889919201042</ds:X509SerialNumber></ds:X509IssuerSerial></ds:X509Data></wsse:SecurityTokenReference></ds:KeyInfo></ds:Signature></wsse:Security></SOAP-ENV:Header><SOAP-ENV:Body xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" wsu:Id=\"id-59d0408f-1813-4a5b-ae68-045e9672c340\"><ns4:getPayrollLoanInterpreterRequest xmlns:ns2=\"urn://grupoaval.com/xsd/ifx/\" xmlns:ns3=\"urn://grupoaval.com/xsd/ifx/v2/\" xmlns:ns4=\"urn://grupoaval.com/prodschnsmngt/v1/\"><ns4:PayrollLoanInterpreterRq><ns2:RqUID>480308882</ns2:RqUID><ns2:MsgRqHdr><ns2:ClientApp><ns2:Org>ADL</ns2:Org><ns2:Name>ADL</ns2:Name><ns2:Version>1.0</ns2:Version></ns2:ClientApp><ns3:Channel>ADL</ns3:Channel><ns2:BankInfo><ns2:BankId>0002</ns2:BankId></ns2:BankInfo><ns3:ClientDt>2024-12-10T14:30:52.177-05:00</ns3:ClientDt><ns3:IPAddr>169.254.117.58</ns3:IPAddr><ns3:SessKey/><ns2:UserId><ns2:GovIssueIdent><ns2:GovIssueIdentType>CC</ns2:GovIssueIdentType><ns2:IdentSerialNum>500192</ns2:IdentSerialNum></ns2:GovIssueIdent></ns2:UserId><ns3:Reverse>false</ns3:Reverse><ns3:Language>es_CO</ns3:Language><ns3:NextDay>2024-12-10T14:30:52.177-05:00</ns3:NextDay></ns2:MsgRqHdr><ns2:CustId><ns2:GovIssueIdent><ns2:GovIssueIdentType>CC</ns2:GovIssueIdentType><ns2:IdentSerialNum>500192</ns2:IdentSerialNum></ns2:GovIssueIdent></ns2:CustId><ns2:PayrollLoanInterpreterInfo><ns2:EarnedAmt>2250000</ns2:EarnedAmt><ns2:DeductibleAmt>1000000</ns2:DeductibleAmt><ns2:NetAmt>0</ns2:NetAmt><ns2:Regimen>1</ns2:Regimen><ns2:PensionType>0</ns2:PensionType><ns2:Salary>0</ns2:Salary><ns2:Paymaster><ns2:NitPaymaster>8001128062</ns2:NitPaymaster><ns2:NamePaymaster>FONDO PASIVO FERROCARRILES NACIONALES</ns2:NamePaymaster></ns2:Paymaster><ns2:FinancialConceptList><ns2:FinancialConceptData><ns2:ConceptId>AS007</ns2:ConceptId><ns2:ConceptName>CODEMA -COOPERATIVA DE</ns2:ConceptName><ns2:TrnType>2</ns2:TrnType><ns2:ConceptValue>100000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>AS099</ns2:ConceptId><ns2:ConceptName>2000 4000 6000 COOP</ns2:ConceptName><ns2:TrnType>2</ns2:TrnType><ns2:ConceptValue>200000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>AS082</ns2:ConceptId><ns2:ConceptName>CANAPRO - COOP CASA NA 0020</ns2:ConceptName><ns2:TrnType>2</ns2:TrnType><ns2:ConceptValue>300000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>CR021</ns2:ConceptId><ns2:ConceptName>APORTE DE LEY</ns2:ConceptName><ns2:TrnType>2</ns2:TrnType><ns2:ConceptValue>400000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>PER</ns2:ConceptId><ns2:ConceptName>RELIQUIDACION PENSIONA</ns2:ConceptName><ns2:TrnType>1</ns2:TrnType><ns2:ConceptValue>250000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>ORG</ns2:ConceptId><ns2:ConceptName>RELIQUIDACION 20 COOPERATIVA 50000</ns2:ConceptName><ns2:TrnType>1</ns2:TrnType><ns2:ConceptValue>2000000</ns2:ConceptValue></ns2:FinancialConceptData></ns2:FinancialConceptList></ns2:PayrollLoanInterpreterInfo></ns4:PayrollLoanInterpreterRq></ns4:getPayrollLoanInterpreterRequest></SOAP-ENV:Body></SOAP-ENV:Envelope>";
    private static final String FERROCARRILES_REQUEST_EXT ="<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ifx=\"urn://grupoaval.com/xsd/ifx/\" xmlns:v2=\"urn://grupoaval.com/xsd/ifx/v2/\"><SOAP-ENV:Header><wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" SOAP-ENV:mustUnderstand=\"1\"><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Id=\"SIG-c2a8d70a-114b-4ad4-a2e3-7f31ceb39c8d\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"><ec:InclusiveNamespaces xmlns:ec=\"http://www.w3.org/2001/10/xml-exc-c14n#\" PrefixList=\"SOAP-ENV\"/></ds:CanonicalizationMethod><ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"/><ds:Reference URI=\"#id-2eee4961-bdb3-489f-a889-23fa7194f1af\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/><ds:DigestValue>1yn5ZHcuqhr0ZPBUV822dl/kgV14Rmey38cUhjuh7lQ=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>vOAGmOBJoKSnxvWZ7+uQQSorDdkcu+YdcElC9K/rG9Q9EyuKSKWcVuxBCVUOd05z+eSiqD6TqgD02one1+WVLwECGu94my1udePsDfx0PXo1oomg3CKSEBvOxLHwDSPTgvuotHkesPp273Pwh+qI5PGA/foDOx9SMVf/1ghugBVRqSyqZICBgaXd+5e7xYOkNKksbADPsY/8425kxQDqZJqpwL0DZZcJmSkgj/UypJr8xcyvocdqrMv9fMhw6ysQ2mUhmW7vJWMO9qsgC57EqwnxmI6A2rnIaY93bPxTAmccsR9MNti0rgYkrb53C2yYt/VXqQylWOj/QAxgsZp08Q==</ds:SignatureValue><ds:KeyInfo Id=\"KI-4a6cec46-d7df-4261-a5a9-7d56c46af1c1\"><wsse:SecurityTokenReference wsu:Id=\"STR-19e833a4-c1c1-43d9-9b5f-b12a1b3fcdad\"><ds:X509Data><ds:X509IssuerSerial><ds:X509IssuerName>CN=Intermediate Authority ADL Digital Lab</ds:X509IssuerName><ds:X509SerialNumber>285816463554313791323646107715685151337474829468</ds:X509SerialNumber></ds:X509IssuerSerial></ds:X509Data></wsse:SecurityTokenReference></ds:KeyInfo></ds:Signature></wsse:Security></SOAP-ENV:Header><SOAP-ENV:Body xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" wsu:Id=\"id-2eee4961-bdb3-489f-a889-23fa7194f1af\"><ns4:getPayrollLoanInterpreterRequest xmlns:ns2=\"urn://grupoaval.com/xsd/ifx/\" xmlns:ns3=\"urn://grupoaval.com/xsd/ifx/v2/\" xmlns:ns4=\"urn://grupoaval.com/prodschnsmngt/v1/\"><ns4:PayrollLoanInterpreterRq><ns2:RqUID>141712677</ns2:RqUID><ns2:MsgRqHdr><ns2:ClientApp><ns2:Org>ADL</ns2:Org><ns2:Name>ADL</ns2:Name><ns2:Version>1.0</ns2:Version></ns2:ClientApp><ns3:Channel>ADL</ns3:Channel><ns2:BankInfo><ns2:BankId>0002</ns2:BankId></ns2:BankInfo><ns3:ClientDt>2025-09-19T20:28:21.951-05:00</ns3:ClientDt><ns3:IPAddr>169.254.70.73</ns3:IPAddr><ns3:SessKey/><ns2:UserId><ns2:GovIssueIdent><ns2:GovIssueIdentType>CC</ns2:GovIssueIdentType><ns2:IdentSerialNum>500168</ns2:IdentSerialNum></ns2:GovIssueIdent></ns2:UserId><ns3:Reverse>false</ns3:Reverse><ns3:Language>es_CO</ns3:Language><ns3:NextDay>2025-09-19T20:28:21.951-05:00</ns3:NextDay></ns2:MsgRqHdr><ns2:CustId><ns2:GovIssueIdent><ns2:GovIssueIdentType>CC</ns2:GovIssueIdentType><ns2:IdentSerialNum>500168</ns2:IdentSerialNum></ns2:GovIssueIdent></ns2:CustId><ns2:PayrollLoanInterpreterInfo><ns2:EarnedAmt>9544369</ns2:EarnedAmt><ns2:DeductibleAmt>3780000</ns2:DeductibleAmt><ns2:NetAmt>0</ns2:NetAmt><ns2:Regimen>1</ns2:Regimen><ns2:PensionType>0</ns2:PensionType><ns2:Salary>0</ns2:Salary><ns2:Paymaster><ns2:NitPaymaster>8001128062</ns2:NitPaymaster><ns2:NamePaymaster>FONDO PASIVO FERROCARRILES NACIONALES</ns2:NamePaymaster></ns2:Paymaster><ns2:FinancialConceptList><ns2:FinancialConceptData><ns2:ConceptId>AS007</ns2:ConceptId><ns2:ConceptName>CODEMA -COOPERATIVA DE</ns2:ConceptName><ns2:TrnType>2</ns2:TrnType><ns2:ConceptValue>930000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>AS007</ns2:ConceptId><ns2:ConceptName>CODEMA -COOPERATIVA DE</ns2:ConceptName><ns2:TrnType>2</ns2:TrnType><ns2:ConceptValue>940000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>AS082</ns2:ConceptId><ns2:ConceptName>CANAPRO - COOP CASA NA</ns2:ConceptName><ns2:TrnType>2</ns2:TrnType><ns2:ConceptValue>950000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>CR021</ns2:ConceptId><ns2:ConceptName>APORTE DE LEY</ns2:ConceptName><ns2:TrnType>2</ns2:TrnType><ns2:ConceptValue>960000</ns2:ConceptValue></ns2:FinancialConceptData><ns2:FinancialConceptData><ns2:ConceptId>PER</ns2:ConceptId><ns2:ConceptName>RELIQUIDACION PENSIONA</ns2:ConceptName><ns2:TrnType>1</ns2:TrnType><ns2:ConceptValue>9544369</ns2:ConceptValue></ns2:FinancialConceptData></ns2:FinancialConceptList></ns2:PayrollLoanInterpreterInfo></ns4:PayrollLoanInterpreterRq></ns4:getPayrollLoanInterpreterRequest></SOAP-ENV:Body></SOAP-ENV:Envelope>";
    private static final Logger LOGGER = Logger.getLogger(ExtractAssertions.class.getName());

    public void validateExtractServiceStatus() {
        assertThat(getFromSession(SessionHelper.SessionData.EXTRACTOR_CODE),
                is(SUCCEDED));
        assertThat(getFromSession(SessionHelper.SessionData.EXTRACTOR_STATUS),
                is("INTERPRETER_SUCCESS"));
    }

    public void showHomologatedNIT(String payment,String log) {
        RequestsUtil.addLogInSerenityReport("NIT Homologado Para "+payment,log);
        switch (payment){
            case COLPENSIONES:
                assertThat(log,
                        containsString(COLPENSIONES_LOG));
            break;
            case FOPEP:
                assertThat(log,
                        containsString(FOPEP_LOG));
                break;
            case FIDUPREVISORA:
                assertThat(log,
                        containsString(FIDUPREVISORA_LOG));
                break;
            case FERROCARRILES:
                assertThat(log,
                        containsString(FERROCARRILES_LOG));
                break;
            case POSITIVA:
                assertThat(log,
                        containsString(POSITIVA_LOG));
                break;
            default:
                throw new IllegalArgumentException("Unsupported event");
        }
    }

    public void showEvents(String eventLog, String value, JsonObject data, Map<String, Object> extractionData){
        try {
            var dateString = JsonPath.read(eventLog, "$.date");
            var formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
            var jsonDate = LocalDate.parse((CharSequence) dateString, formatter).toString();
            setInSession(SessionHelper.SessionData.JSON_DATE_DAY,dateString);
            validateField("ID Google Analytics: ",JsonPath.read(eventLog, "$.idGoogleAnalytics"),
                    DOES_NOT_APPLY);
            validateField("Advisor Identification: ",JsonPath.read(eventLog, "$.advisorIdentification"),
                    data.get(ADVISOR_DOCUMENT)
                            .getAsString());
            validateField("User Identification: ",JsonPath.read(eventLog, "$.userIdentification"),
                    data.get(CUSTOMER_DOCUMENT)
                            .getAsString());
            validateField("User Identification Type: ",JsonPath.read(eventLog, "$.userIdentificationType"),
                    data.get(CUSTOMER_DOC_TYPE)
                            .getAsString());
            validateField("EventName: ",JsonPath.read(eventLog, "$.userAgent"),
                    DOES_NOT_APPLY);
            validateField("EventName: ",JsonPath.read(eventLog, "$.eventName"),
                    extractionData.get(EVENT_NAME));
            validateField("EventMnemonic: ",JsonPath.read(eventLog, "$.eventMnemonic"),
                    extractionData.get(EVENT_MNEMONIC));
            validateField("EventCode: ",JsonPath.read(eventLog, "$.eventCode"),
                    extractionData.get(EVENT_CODE));

            var  isInvalidDocument = data.get(CUSTOMER_DOCUMENT).getAsString().equals("500058") ||
                    data.get(CUSTOMER_DOCUMENT).getAsString().equals("500053");

            validateField("Result: ", JsonPath.read(eventLog, "$.result"),
                    isInvalidDocument ? NOT_SUCCESS : SUCCESS);

            validatePattern("UUID: ",JsonPath.read(eventLog, "$.transactionId"),
                    UUID_PATTERN);
            validateField("RequestID / transactionId ",JsonPath.read(eventLog, "$.transactionId"),
                    JsonPath.read(eventLog, "$.requestId"));
            validateField("IP Address: ",JsonPath.read(eventLog, "$.ipAddress"),
                    DOES_NOT_APPLY);
            validateDateField(EXTRACTOR_STATUS,jsonDate,
                    localDate("yyyy-MM-dd"));


            var detailJson = JsonPath.read(eventLog, "$.detail").toString();
            var mapper = new ObjectMapper();

            var detailNode = mapper.readTree(detailJson);

            if (value.equals(EXTRACT_SUCCESS) || value.equals(CUSTOMER_WITHOUT_EXTRACT)) {
                validateExtractDetailJson( value, extractionData,detailNode);
            } else {
                validateInterpreterDetailJson(value, extractionData, data,detailNode);
            }
            StepEventBus.getEventBus().stepFinished();
        }catch (JsonProcessingException e){
            e.getMessage();
        }
    }

    private void validateExtractDetailJson(String value, Map<String, Object> extractionData, JsonNode detailNode) {
        validateField(EXTRACTOR_STATUS,detailNode.get(PROCESS_STATUS).asText(),
                value);
        validateField("Extrarction ID: ",detailNode.get(EXTRACTION_ID).asText(),
                extractionData.get(EXTRACTION_ID));
        validatePattern(RQUID,detailNode.get(RQUID).asText(),
                NUMBER_PATTERN);
        validateField(STATUS,detailNode.get(REQUEST_STATE).asText(),
                "Extracción realizada");
    }

    private void validateInterpreterDetailJson(String value, Map<String, Object> extractionData, JsonObject data, JsonNode detailNode) {
        validateField(STATUS,detailNode.get(PROCESS_STATUS).asText(),
                value);
        validateField("Extraction ID: ",detailNode.get(EXTRACTION_ID).asText(),
                extractionData.get(EXTRACTION_ID));
        validateField("Extract Interpreter Sort: ",detailNode.get(EXTRACT_INTERPRETER).asText(),
                data.get(EXTRACT_INTERPRETER).getAsString());
        validateField("Bonuses: ",detailNode.get(BONUSES).asText(),
                data.get(BONUSES).getAsString());
        validateField("Extra Payment: ",detailNode.get(EXTRA_PAYMENT).asText(),
                data.get(EXTRA_PAYMENT).getAsString());
        validateField("Heal Discount: ",detailNode.get(HEALTH_DISCOUNT).asText(),
                data.get(HEALTH_DISCOUNT).getAsString());
        validateField("Honorarium: ",detailNode.get(HONORARIUM).asText(),
                data.get(HONORARIUM).getAsString());
        validateField("Discount1: ",detailNode.get(DISCOUNT1).asText(),
                data.get(DISCOUNT1).getAsString());
        validateField("Discount2: ",detailNode.get(DISCOUNT2).asText(),
                data.get(DISCOUNT2).getAsString());
        validateField("Discount3: ",detailNode.get(DISCOUNT3).asText(),
                data.get(DISCOUNT3).getAsString());
        validateField("Other Law Discount: ",detailNode.get(OTHER_LAW_DISCOUNT).asText(),
                data.get(OTHER_LAW_DISCOUNT).getAsString());
        validateField("Overtime: ",detailNode.get(OVERTIME).asText(),
                data.get(OVERTIME).getAsString());
        validateField("Paymaster: ",detailNode.get(PAYMASTER).asText(),
                data.get(PAYMASTER).getAsString());
        validateField("Pension Discount: ",detailNode.get(PENSION_DISCOUNT).asText(),
                data.get(PENSION_DISCOUNT).getAsString());
        validateField(SALARY,detailNode.get(SALARY).asText(),
                data.get(SALARY).getAsString());
        validateField("Total Other Income: ",detailNode.get(TOTAL_OTHER_INCOME).asText(),
                data.get(TOTAL_OTHER_INCOME).getAsString());
        validateField("Request State: ",detailNode.get(REQUEST_STATE).asText(),
                "successful interpreter");
    }
    private String localDate(String pattern) {
        var currentDate = LocalDateTime.now();
        var formatter = DateTimeFormatter.ofPattern(pattern);
        return currentDate.format(formatter);
    }

    public void validateDataInExtractorTable(ItemDynamoExtraction dataTableExtract, ItemDynamoExtraction data) {
        validateField("Client Document Type: ",dataTableExtract.getClientDocumentType(),
                data.getClientDocumentType());
        validateField("Advisor ID: ",dataTableExtract.getAdvisorId(),
                data.getAdvisorId());
        validateField(EXTRACTOR_STATUS,dataTableExtract.getProcessStatus(),
                data.getProcessStatus());
        validateField("Client Name: ",dataTableExtract.getClientName(),
                data.getClientName());
        validateField("Sector Code: ",dataTableExtract.getSectoreCode(),
                data.getSectoreCode());
        validateField("Extractor Interpreter Sort: ",dataTableExtract.getExtract_interpreter_sort(),
                data.getExtract_interpreter_sort());
        validateField("Office ID: ",dataTableExtract.getOfficeId(),
                data.getOfficeId());
        validateDateField("Process Date: ",dataTableExtract.getProcessDate(),
                data.getProcessDate());
        validateField("Payern Nit: ",dataTableExtract.getPayerNit(),
                data.getPayerNit());
        validateField("Payern Name: ",dataTableExtract.getPayerName(),
                data.getPayerName());
        validateField("Obligaton ID: ",dataTableExtract.getObligationId(),
                data.getObligationId());
        validateField("Client Document Number: ",dataTableExtract.getClientDocumentNumber(),
                data.getClientDocumentNumber());
        validateField("Allowance Date: ",dataTableExtract.getAllowanceDate(),
                data.getAllowanceDate());
        validateField("Flow Type: ",dataTableExtract.getFlowType(),
                data.getFlowType());
        validatePattern("Interpreter ID: ",dataTableExtract.getExtract_interpreter_id(),
                "^[a-fA-F0-9]{8}-[a-fA-F0-9]{"+FOUR+"}-[a-fA-F0-9]{"+FOUR+"}-[a-fA-F0-9]{"+FOUR+"}-[a-fA-F0-9]{12}$");
        validatePattern("Expiration Time: ",dataTableExtract.getExpiration_time(),
                "^"+REGEX+"{13}$");
    }

    public void validateExtractResult(ItemDynamoExtraction dataTableExtract, ItemDynamoExtraction data) {
        List<co.com.bancopopular.automation.models.extractor.FinancialConceptData> conceptDataList
                = dataTableExtract.getExtractResult().
                getPayrollLoanExtractionInfo().getPaymentSlipList().getPaymentSlipData().get(0).getFinancialConceptList().getFinancialConceptData();
        List<co.com.bancopopular.automation.models.extractor.FinancialConceptData> conceptDataListValidate
                = data.getExtractResult().
                getPayrollLoanExtractionInfo().getPaymentSlipList().getPaymentSlipData().get(0).getFinancialConceptList().getFinancialConceptData();

        validatePattern(RQUID,dataTableExtract.getExtractResult().getRqUID(),
                REGEX+"+");
        validateField("Allowance Date: ",dataTableExtract.getExtractResult()
                        .getPayrollLoanExtractionInfo().getAllowanceDate(),
                data.getExtractResult().getPayrollLoanExtractionInfo().getAllowanceDate());
        validateField("Name Paymaster: ",dataTableExtract.getExtractResult()
                        .getPayrollLoanExtractionInfo().getPaymaster().getNamePaymaster(),
                data.getExtractResult().getPayrollLoanExtractionInfo().getPaymaster().getNamePaymaster());
        validateField("Nit Paymaster: ",dataTableExtract.getExtractResult()
                        .getPayrollLoanExtractionInfo().getPaymaster().getNitPaymaster(),
                data.getExtractResult().getPayrollLoanExtractionInfo().getPaymaster().getNitPaymaster());
        validateField("Member Ship ID: ",dataTableExtract.getExtractResult()
                        .getPayrollLoanExtractionInfo().getMembershipId(),
                data.getExtractResult().getPayrollLoanExtractionInfo().getMembershipId());

        validateField("Total Income: ",dataTableExtract.getExtractResult()
                        .getPayrollLoanExtractionInfo().getPaymentSlipList().getPaymentSlipData().get(0).getTotalIncome(),
                data.getExtractResult().getPayrollLoanExtractionInfo().getPaymentSlipList().getPaymentSlipData().get(0).getTotalIncome());

        validateField("Total Outcome: ",dataTableExtract.getExtractResult()
                        .getPayrollLoanExtractionInfo().getPaymentSlipList().getPaymentSlipData().get(0).getTotalOutcome(),
                data.getExtractResult().getPayrollLoanExtractionInfo().getPaymentSlipList().getPaymentSlipData().get(0).getTotalOutcome());

        validateField(STATUS,dataTableExtract.getExtractResult().getStatus(),
                data.getExtractResult().getStatus());

        validateField("Financial Concept Data Size: ",conceptDataList.size(),
                conceptDataListValidate.size());

        validateField("Financial Concept Data Content: ",conceptDataList,
                conceptDataListValidate);
    }

    public void validateInterpreterResult(ItemDynamoExtraction dataTableExtract, ItemDynamoExtraction data) {
        validateField("Bonusses: ",dataTableExtract.getInterpreterResult()
                        .getPayrollLoanInterpreterInfo().getBonuses(),
                data.getInterpreterResult().getPayrollLoanInterpreterInfo().getBonuses());
        validateField("Health Discount: ",dataTableExtract.getInterpreterResult()
                        .getPayrollLoanInterpreterInfo().getHealthDiscount(),
                data.getInterpreterResult().getPayrollLoanInterpreterInfo().getHealthDiscount());
        validateField("Other Law Discount: ",dataTableExtract.getInterpreterResult()
                        .getPayrollLoanInterpreterInfo().getOtherLawDiscount(),
                data.getInterpreterResult().getPayrollLoanInterpreterInfo().getOtherLawDiscount());
        validateField("Extra Payment: ",dataTableExtract.getInterpreterResult()
                        .getPayrollLoanInterpreterInfo().getExtraPayment(),
                data.getInterpreterResult().getPayrollLoanInterpreterInfo().getExtraPayment());
        validateField("Honorarium: ",dataTableExtract.getInterpreterResult()
                        .getPayrollLoanInterpreterInfo().getHonorarium(),
                data.getInterpreterResult().getPayrollLoanInterpreterInfo().getHonorarium());
        validateField("Overtime: ",dataTableExtract.getInterpreterResult()
                        .getPayrollLoanInterpreterInfo().getOvertime(),
                data.getInterpreterResult().getPayrollLoanInterpreterInfo().getOvertime());
        validateField("Pension Discount: ",dataTableExtract.getInterpreterResult()
                        .getPayrollLoanInterpreterInfo().getPensionDiscount(),
                data.getInterpreterResult().getPayrollLoanInterpreterInfo().getPensionDiscount());
        validateField("Total Other Income: ",dataTableExtract.getInterpreterResult()
                        .getPayrollLoanInterpreterInfo().getTotalOtherIncome(),
                data.getInterpreterResult().getPayrollLoanInterpreterInfo().getTotalOtherIncome());
        validateField(SALARY,dataTableExtract.getInterpreterResult()
                        .getPayrollLoanInterpreterInfo().getSalary(),
                data.getInterpreterResult().getPayrollLoanInterpreterInfo().getSalary());
        validateField("Discount1: ",dataTableExtract.getInterpreterResult()
                        .getPayrollLoanInterpreterInfo().getOtherDiscount1(),
                data.getInterpreterResult().getPayrollLoanInterpreterInfo().getOtherDiscount1());
        validateField("Discount2: ",dataTableExtract.getInterpreterResult()
                        .getPayrollLoanInterpreterInfo().getOtherDiscount2(),
                data.getInterpreterResult().getPayrollLoanInterpreterInfo().getOtherDiscount2());
        validateField("Discount3: ",dataTableExtract.getInterpreterResult()
                        .getPayrollLoanInterpreterInfo().getOtherDiscount3(),
                data.getInterpreterResult().getPayrollLoanInterpreterInfo().getOtherDiscount3());
        validateField("Paymaster: ",dataTableExtract.getInterpreterResult()
                        .getPayrollLoanInterpreterInfo().getPaymaster(),
                data.getInterpreterResult().getPayrollLoanInterpreterInfo().getPaymaster());
        validateField(STATUS,dataTableExtract.getInterpreterResult().getStatus(),
                data.getInterpreterResult().getStatus());
        validateField("Remain Rec: ",dataTableExtract
                .getInterpreterResult()
                .getMessageResponseHeader()
                .getRemainRec(),
                data.getInterpreterResult()
                        .getMessageResponseHeader()
                        .getRemainRec());
        validateField("EffDt: ",dataTableExtract.getInterpreterResult()
                .getMessageResponseHeader().getEffDt(),
                data.getInterpreterResult()
                .getMessageResponseHeader().getEffDt());
        validateField("Tax Cost Amount: ",dataTableExtract.getInterpreterResult()
                        .getMessageResponseHeader()
                        .getTaxCostAmount(),
                data.getInterpreterResult()
                        .getMessageResponseHeader()
                        .getTaxCostAmount());
    }


    public void showInterpreterRequest(String payment,String homologatedLog) {
        homologatedLog = extractSoapBody(homologatedLog.trim());
        var interpreterRequest = (InterpreterRequest) interpreterRequestToObject(homologatedLog);
        RequestsUtil.addLogInSerenityReport("Request enviado para interpretador",
                new GsonBuilder().setPrettyPrinting().create().toJson(interpreterRequest));
        assert interpreterRequest != null;
        validateInterpreterRequest(payment,interpreterRequest);
    }

    private void validateInterpreterRequest(String payment,InterpreterRequest interpreterRequest) {
        InterpreterRequest requestForValidate =
                interpreterRequestToObject(dataForRequestValidate(payment,
                        getFromSession(SessionHelper.SessionData.CREDIT_TYPE)));
        assert requestForValidate != null;
        validateFinancialConceptList(interpreterRequest,requestForValidate);
        validatePaymaster(interpreterRequest,requestForValidate);
        validatePayrollLoanInterpreterRq(interpreterRequest,requestForValidate);
        validateAlternateFields(interpreterRequest,requestForValidate);
    }

    private void validateAlternateFields(InterpreterRequest interpreterRequest, InterpreterRequest requestForValidate) {
        var request = interpreterRequest.getBody().getRequest().getPayrollLoanInterpreterRq();
        var requestValidate = requestForValidate.getBody().getRequest().getPayrollLoanInterpreterRq();
        validateGovIssueIdent(request.getCustId().getGovIssueIdent(), requestValidate.getCustId().getGovIssueIdent());
        validateClientApp(request.getMsgRqHdr().getClientApp(), requestValidate.getMsgRqHdr().getClientApp());
        validateField("Channel: ",request.getMsgRqHdr().getChannel(), requestValidate.getMsgRqHdr().getChannel());
        validateField("Bank Info: ",request.getMsgRqHdr().getBankInfo(),requestValidate.getMsgRqHdr().getBankInfo());
        validateGovIssueIdent(request.getMsgRqHdr().getUserId().getGovIssueIdent(), requestValidate.getMsgRqHdr().getUserId().getGovIssueIdent());
        validateField("Reverse: ",request.getMsgRqHdr().getReverse(), requestValidate.getMsgRqHdr().getReverse());
        validateField("Lenguaje: ",request.getMsgRqHdr().getLanguage(), requestValidate.getMsgRqHdr().getLanguage());
        validateDateField("Next Day: ",request.getMsgRqHdr().getNextDay(), localDate(LOCAL_DATE));
        validateDateField("Client Dt: ",request.getMsgRqHdr().getClientDt(), localDate(LOCAL_DATE));
        validateField("Next Day / Client Dt: ",request.getMsgRqHdr().getNextDay(), request.getMsgRqHdr().getClientDt());
        validatePattern(RQUID,request.getRqUID().trim(), NUMBER_PATTERN);
    }
    private void validateGovIssueIdent(GovIssueIdent ident, GovIssueIdent identValidate) {
        validateField("Ident Type: ",ident.getGovIssueIdentType(), identValidate.getGovIssueIdentType());
        validateField("Ident Serial Num: ",ident.getIdentSerialNum(), identValidate.getIdentSerialNum());
    }
    private void validateClientApp(ClientApp clientApp, ClientApp clientAppValidate) {
        validateField("ClientApp Name: ",clientApp.getName(), clientAppValidate.getName());
        validateField("ORG: ",clientApp.getOrg(), clientAppValidate.getOrg());
        validateField("Version: ",clientApp.getVersion(), clientAppValidate.getVersion());
    }
    private void validateFinancialConceptList(InterpreterRequest interpreterRequest, InterpreterRequest requestForValidate) {
        List<FinancialConceptData> conceptDataList = interpreterRequest.getBody().getRequest().getPayrollLoanInterpreterRq().getPayrollLoanInterpreterInfo().getFinancialConceptList();
        List<FinancialConceptData> conceptDataListValidate = requestForValidate.getBody().getRequest().getPayrollLoanInterpreterRq().getPayrollLoanInterpreterInfo().getFinancialConceptList();

        assertThat(conceptDataList.size(), is(conceptDataListValidate.size()));

        var earnedAmt = 0;
        var deductibleAmt = 0;
        for (var i = 0; i < conceptDataList.size(); i++) {
            var concept = conceptDataList.get(i);
            var conceptValidate = conceptDataListValidate.get(i);
            validateConceptData(concept, conceptValidate);
            var value = Integer.parseInt(concept.getConceptValue().trim());
            if ("1".equals(concept.getTrnType())) {
                earnedAmt += value;
            } else {
                deductibleAmt += value;
            }
        }
        validateAmounts(earnedAmt, deductibleAmt, interpreterRequest);
    }
    private void validateConceptData(FinancialConceptData concept, FinancialConceptData conceptValidate) {
        validateField("Concept ID: ",concept.getConceptId(), conceptValidate.getConceptId());
        validateField("Concept Name: ",concept.getConceptName(), conceptValidate.getConceptName());
        validateField("Trn Type: ",concept.getTrnType(), conceptValidate.getTrnType());
        validateField("Concept Value: ",concept.getConceptValue(), conceptValidate.getConceptValue());

    }
    private void validateAmounts(int earnedAmt, int deductibleAmt, InterpreterRequest interpreterRequest) {
        var info = interpreterRequest.getBody().getRequest().getPayrollLoanInterpreterRq().getPayrollLoanInterpreterInfo();
        validateField("Earned Amt: ","<" + earnedAmt + ">",
                "<" + info.getEarnedAmt() + ">");

        validateField("Deductible Amt: ","<" + deductibleAmt + ">",
                "<" + info.getDeductibleAmt() + ">");

    }
    private void validatePaymaster(InterpreterRequest interpreterRequest, InterpreterRequest requestForValidate) {
        var paymaster = interpreterRequest.getBody().getRequest().getPayrollLoanInterpreterRq().getPayrollLoanInterpreterInfo().getPaymaster();
        var paymasterValidate = requestForValidate.getBody().getRequest().getPayrollLoanInterpreterRq().getPayrollLoanInterpreterInfo().getPaymaster();
        validateField("Name Paymaster: ",paymaster.getNamePaymaster(), paymasterValidate.getNamePaymaster());
        validateField("Nit Paymaster: ",paymaster.getNitPaymaster(), paymasterValidate.getNitPaymaster());
    }
    private void validatePayrollLoanInterpreterRq(InterpreterRequest interpreterRequest, InterpreterRequest requestForValidate) {
        var info = interpreterRequest.getBody().getRequest().getPayrollLoanInterpreterRq().getPayrollLoanInterpreterInfo();
        var infoValidate = requestForValidate.getBody().getRequest().getPayrollLoanInterpreterRq().getPayrollLoanInterpreterInfo();
        validateField("Deductible Amt: ",info.getDeductibleAmt(), infoValidate.getDeductibleAmt());
        validateField("Earned Amt: ",info.getEarnedAmt(), infoValidate.getEarnedAmt());
        validateField("Net Amt: ",info.getNetAmt(), infoValidate.getNetAmt());
        validateField("Regimen: ",info.getRegimen(), infoValidate.getRegimen());
        validateField("Pension Type: ",info.getPensionType(),infoValidate.getPensionType());
        validateField(SALARY,info.getSalary(),infoValidate.getSalary());
    }

    private String dataForRequestValidate(String payment, String creditType) {
        var request="";
        switch (payment){
            case COLPENSIONES:
                if(creditType.equals("NEW_PAYROLL_LOAN")) {
                    request = COLPENSIONES_REQUEST;
                }else{
                    request = COLPENSIONES_REQUEST_EXT;
                }
                break;
            case FOPEP:
                if(creditType.equals("NEW_PAYROLL_LOAN")) {
                    request = FOPEP_REQUEST;
                }else{
                    request = FOPEP_REQUEST_EXT;
                }
                break;
            case FIDUPREVISORA:
                if(creditType.equals("NEW_PAYROLL_LOAN")) {
                    request = FIDUPREVISORA_REQUEST;
                }else{
                    request = FIDUPREVISORA_REQUEST_EXT;
                }
                break;
            case POSITIVA:
                if(creditType.equals("NEW_PAYROLL_LOAN")) {
                    request = POSITIVA_REQUEST;
                }else{
                    request = POSITIVA_REQUEST_EXT;
                }
                break;
            case FERROCARRILES:
                if(creditType.equals("NEW_PAYROLL_LOAN")) {
                    request = FERROCARRILES_REQUEST;
                }else{
                    request = FERROCARRILES_REQUEST_EXT;
                }
                break;
            default:
                break;
        }
        return request;
    }

    private InterpreterRequest interpreterRequestToObject(String homologatedLog) {
        try {
            var context = JAXBContext.newInstance(InterpreterRequest.class);
            var unmarshaller = context.createUnmarshaller();
            var reader = new StringReader(homologatedLog);
            return (InterpreterRequest) unmarshaller.unmarshal(reader);
        }catch (Exception e){
            LOGGER.log(Level.SEVERE, "error in SOAP: ", e);
        }
        return null;
    }
    private String extractSoapBody(String homologatedLog) {
        int startIndex = homologatedLog.indexOf("<SOAP-ENV:Envelope");
        int endIndex = homologatedLog.lastIndexOf("</SOAP-ENV:Envelope>");
        if (startIndex == -1 || endIndex == -1) {
            throw new IllegalArgumentException("No se encontró contenido XML válido en el log.");
        }
        endIndex += "</SOAP-ENV:Envelope>".length();
        return homologatedLog.substring(startIndex, endIndex).trim();
    }

    private void validateField(String fieldName, Object actual, Object expected) {
        assertThat(actual, is(expected));
        RequestsUtil.logSuccessfulAssertion(String.format("%s " + RESULT_LOG, fieldName, actual, expected));

    }
    private void validateDateField(String fieldName, String actualDate, String expectedDate) {
        assertThat(actualDate, containsString(expectedDate));
        RequestsUtil.logSuccessfulAssertion(String.format("%s " + DATE_LOG, fieldName, actualDate, expectedDate));
    }
    private void validatePattern(String fieldName, String actual, String pattern) {
        assertThat(actual, matchesPattern(pattern));
        RequestsUtil.logSuccessfulAssertion(String.format("%s " + PATTERN_LOG, fieldName, actual, pattern));
    }

    public void validateServiceStatusWithoutExtraction() {
        assertThat(getFromSession(SessionHelper.SessionData.EXTRACTOR_CODE),
                is(SUCCEDED));
        assertThat(getFromSession(SessionHelper.SessionData.EXTRACTOR_STATUS),
                is(CUSTOMER_WITHOUT_EXTRACT));
    }
}





