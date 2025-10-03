package co.com.bancopopular.automation.features.steps.toggles;

import co.com.bancopopular.automation.data.Environment;
import co.com.bancopopular.automation.models.castlemock.*;
import co.com.bancopopular.automation.rest.apis.ServicePaths;
import co.com.bancopopular.automation.utils.HeadersEnum;
import co.com.bancopopular.automation.utils.RequestsUtil;
import co.com.bancopopular.automation.utils.SessionHelper;
import com.amazonaws.services.dynamodbv2.xspec.S;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import org.aeonbits.owner.ConfigFactory;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.stream.Collectors;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import static co.com.bancopopular.automation.constants.Constants.*;
import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static net.serenitybdd.rest.SerenityRest.given;

public class TogglesRequests {

  Environment environment = ConfigFactory.create(Environment.class);
  private final Map<String,String> castlemock= new HashMap<>();
  private static final  String FILE ="file";
  private static final String APPLICATION ="application";
  private static final String  HOST_CM = "src/test/resources/files/castlemock/";
  private static final String COOKIE=System.getProperty("cookie");
  private static final String GENERAL_ENTITIES="http://xml.org/sax/features/external-general-entities";

  private static final String PARAMETER_ENTITIES="http://xml.org/sax/features/external-parameter-entities";

  private static final String DISALLOW_DOCTYPE ="http://apache.org/xml/features/disallow-doctype-decl";

  private static final Logger LOGGER = LogManager.getLogger(TogglesRequests.class);

  public void searchToggles() throws MalformedURLException {

    given().relaxedHTTPSValidation()
            .contentType(ContentType.JSON)
            .and()
            .header(
                    HeadersEnum.HEADER_X_SECURITY_SESSION.toString(), getFromSession(SessionHelper.SessionData.XSECURITYSESSION))
            .and()
            .header(
                    HeadersEnum.HEADER_X_SECURITY_REQUEST_ID.toString(), getFromSession(SessionHelper.SessionData.XSECURITYREQUESTID))
            .when().get(ServicePaths.getToggles());

    RequestsUtil.addResponseInSerenityReport("Response toggles:");
  }
  public void createRestMocks(String url) throws IOException, ParserConfigurationException, SAXException {
    castlemock.put(FILE, HOST_CM +url+".xml");
    castlemock.put(APPLICATION,castlemockProject(url));
    var xmlMapper = new XmlMapper();
    var xmlFile = new File(castlemock.get(FILE));
    var restMethod = xmlMapper.readValue(xmlFile, RestMethod.class);

    List<MockResponse> mockResponses = restMethod.getMockResponses();


    Map<String, Object> bodyString = new HashMap<>();
    List<String> bodyContents = extractBodyContents(xmlFile);
    var x =0;
    for (MockResponse mockResponse : mockResponses) {
      bodyString.put("id",castlemock.get(APPLICATION));
      bodyString.put("name",mockResponse.getName());
      bodyString.put(STATUS,mockResponse.getStatus());
      bodyString.put("httpStatusCode",mockResponse.getHttpStatusCode());
      bodyString.put("usingExpressions",mockResponse.isUsingExpressions());
      bodyString.put("httpHeaders",mockResponse.getHttpHeaders());

      switch (url) {
        case CRM:
          bodyString.put("body", mockResponse.getBody());
          bodyString.put("parameterQueries", mockResponse.getParameterQueries());
          break;
        case SIM:
          bodyString.put("body", mockResponse.getBody());
          bodyString.put("jsonPathExpressions", mockResponse.getJsonPathExpressions());
          break;
        case SIMULATION:
          bodyString.put("body", formatXML(bodyContents.get(x)).trim());
          List<PathExpression> finalXpath = mockResponse.getXpathExpressions().stream()
                  .map(expr -> new PathExpression(expr.getExpression().replaceAll("\\s*and\\s*", " and ")))
                  .collect(Collectors.toList());
          bodyString.put("xpathExpressions", finalXpath);
          break;
        default:
          break;
      }

      given().relaxedHTTPSValidation()
              .contentType("application/json")
              .header(
                      "Cookie", COOKIE)
              .and()
              .body(bodyString)
              .when().post(ServicePaths.getMocks(url));
      bodyString.clear();
      if(SerenityRest.then().extract().statusCode() == HttpStatus.SC_OK) {
        LOGGER.log(Level.INFO, "Cliente REST creado");
      }else {
        LOGGER.log(Level.ERROR,"Error consumo REST");
        break;
      }
      x++;
      RequestsUtil.pause(2);
    }
  }
  private String castlemockProject(String url) {
    var applicationId="";
    switch (url){
      case CRM:
        applicationId="cVZjyO";
        break;

      case SIM:
        applicationId="FSERDy";
        break;

      case ACTIVAS:
        applicationId="G6fF01";
        break;

      case SIMULATION:
        applicationId="MO5uA9";
        break;

      default:
        applicationId=null;
        break;
    }
    return applicationId;
  }

  public void createSoapMocks(String url) throws IOException, ParserConfigurationException, SAXException {
    castlemock.put(FILE, HOST_CM +url+".xml");
    castlemock.put(APPLICATION,castlemockProject(url));
    var xmlMapper = new XmlMapper();
    var xmlFile = new File(castlemock.get(FILE));
    var soapOperation = xmlMapper.readValue(xmlFile, SoapOperation.class);
    List<MockResponseSoap> mockResponses =  soapOperation.getMockResponses();
    Map<String, Object> bodyString = new HashMap<>();
    List<String> bodyContents = extractBodyContents(xmlFile);
    var x =0;
    for (MockResponseSoap mockResponse : mockResponses) {
      bodyString.put("id", castlemock.get(APPLICATION));
      bodyString.put("name", mockResponse.getName());
      bodyString.put("body", formatXML(bodyContents.get(x)).trim());
      bodyString.put(STATUS, mockResponse.getStatus());
      bodyString.put("httpStatusCode", mockResponse.getHttpStatusCode());
      bodyString.put("usingExpressions", mockResponse.isUsingExpressions());
      bodyString.put("xpathExpressions", mockResponse.getXpathExpressions());

      given().relaxedHTTPSValidation()
              .contentType("application/json")
              .header(
                      "Cookie", COOKIE)
              .and()
              .body(bodyString)
              .when().post(ServicePaths.getMocks(url));
      bodyString.clear();
      if (SerenityRest.then().extract().statusCode() == HttpStatus.SC_OK) {
        LOGGER.log(Level.INFO, "Mock SOAP creado");
      } else {
        LOGGER.log(Level.ERROR,"Error en el consumo SOAP");
        break;
      }
      x++;
      RequestsUtil.pause(2);
    }
  }

  public static List<String> extractBodyContents(File xmlFile) throws ParserConfigurationException, IOException, SAXException {
    List<String> bodyList = new ArrayList<>();
    var factory = DocumentBuilderFactory.newInstance();
    factory.setFeature(GENERAL_ENTITIES, false);
    factory.setFeature(PARAMETER_ENTITIES, false);
    factory.setFeature(DISALLOW_DOCTYPE, true);
    factory.setNamespaceAware(true);
    var builder = factory.newDocumentBuilder();
    var doc = builder.parse(xmlFile);
    var bodyNodes = doc.getElementsByTagName("body");
    for (var i = 0; i < bodyNodes.getLength(); i++) {
      var bodyNode = bodyNodes.item(i);
      bodyList.add(nodeToString(bodyNode));
    }

    return bodyList;
  }

  private static String nodeToString(Node node) {
    var sb = new StringBuilder();
    var childNodes = node.getChildNodes();
    for (var i = 0; i < childNodes.getLength(); i++) {
      var child = childNodes.item(i);
      sb.append(nodeToStringRecursive(child));
    }
    return sb.toString().trim();
  }

  private static String nodeToStringRecursive(Node node) {
    var sb = new StringBuilder();
    if (node.getNodeType() == Node.TEXT_NODE) {
      sb.append(node.getTextContent().trim());
    } else if (node.getNodeType() == Node.ELEMENT_NODE) {
      sb.append("<").append(node.getNodeName());
      var attributes = node.getAttributes();
      for (var i = 0; i < attributes.getLength(); i++) {
        var attr = attributes.item(i);
        sb.append(" ").append(attr.getNodeName()).append("=\"").append(attr.getNodeValue()).append("\"");
      }
      sb.append(">");
      var children = node.getChildNodes();
      for (var i = 0; i < children.getLength(); i++) {
        sb.append(nodeToStringRecursive(children.item(i)));
      }
      sb.append("</").append(node.getNodeName()).append(">");
    }
    return sb.toString();
  }

  private String formatXML(String unformattedXml) {
    try {
      var transformerFactory = TransformerFactory.newInstance();
      transformerFactory.setFeature(GENERAL_ENTITIES, false);
      transformerFactory.setFeature(PARAMETER_ENTITIES, false);
      transformerFactory.setFeature(DISALLOW_DOCTYPE, true);
      transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
      transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");

      transformerFactory.setAttribute("indent-number", 4);

      var transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

      var source = new StreamSource(new StringReader(unformattedXml));
      var outputWriter = new StringWriter();
      var result = new StreamResult(outputWriter);

      transformer.transform(source, result);
      return outputWriter.toString();
    } catch (Exception e) {
      return unformattedXml;
    }
  }
}

