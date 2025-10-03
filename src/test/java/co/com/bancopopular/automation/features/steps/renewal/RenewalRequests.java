package co.com.bancopopular.automation.features.steps.renewal;

import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static java.util.concurrent.TimeUnit.SECONDS;
import static net.serenitybdd.rest.SerenityRest.given;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import co.com.bancopopular.automation.abilities.ConnectToParameters;
import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.models.factory.Loan;
import co.com.bancopopular.automation.models.onbase.OnBaseDocumentPayer;
import co.com.bancopopular.automation.rest.apis.ServicePaths;
import co.com.bancopopular.automation.utils.*;
import co.com.bancopopular.automation.utils.logs.LogPrinter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.questions.TheResponseStatusCode;
import org.apache.http.HttpStatus;
import java.net.MalformedURLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;


public class RenewalRequests {

    Response responseRecalculate;
    int generatedId;
    private Response response;
    private static final int WAIT_CHANGE_STATUS = 20;
    private static final String GENERATED_ID = "generatedId";
    private static final String TOKEN = "token";
    private static final String ACCOUNT_NUMBER = "accountNumber";
    private static final String VENDOR_DOCUMENT = "vendorDocument";
    private static final String CUSTOMER_DOC_TYPE = "customerDocType";
    private static final String CUSTOMER_DOCUMENT = "customerDocument";
    private static final String OFFICE_CODE = "officeCode";

    RequestsUtil requestsUtil = new RequestsUtil();

    public void getRenewal(JsonObject data, String accountType, String insuranceCode, String token, int generatedId,
                           String obligationId, List<Object> onBaseDocuments)
            throws MalformedURLException {

        var sellerId = Integer.parseInt(data.get(VENDOR_DOCUMENT).getAsString());
        var office = Integer.parseInt(data.get(OFFICE_CODE).getAsString());
        Map<String, Object> renewalBody = new HashMap<>();
        renewalBody.put("identificationType", data.get(CUSTOMER_DOC_TYPE).getAsString());
        renewalBody.put("identificationNumber", data.get(CUSTOMER_DOCUMENT).getAsString());
        renewalBody.put(OFFICE_CODE, office);
        renewalBody.put("sellerIdNumber", sellerId);
        renewalBody.put("accountType", accountType);
        renewalBody.put(ACCOUNT_NUMBER, data.get(ACCOUNT_NUMBER).getAsString());
        renewalBody.put(GENERATED_ID, generatedId);
        renewalBody.put("insuranceCode", insuranceCode);
        renewalBody.put("renewalConfirmationCode", "213123rewrwe");
        renewalBody.put("validationMethod", "Certihuella");
        renewalBody.put("onBaseDocuments", onBaseDocuments);
        renewalBody.put("coverageSpouse", false);
        renewalBody.put("maxCoverage", true);
        renewalBody.put("creditStudy", 117110);
        renewalBody.put("adjustmentInterests", 924650);
        renewalBody.put("adjustmentInsurancePremium", 298282);
        renewalBody.put("accidentInsurancePrice", "1010784");
        renewalBody.put("totalDiscounts", 2350826);

        given().relaxedHTTPSValidation().header(HeadersEnum.AUTHORIZATION.toString(), token)
                .contentType(ContentType.JSON)
                .body(renewalBody)
                .when().post(ServicePaths.getRenewal(obligationId));
    }

    public void startNewSimulation(String customerDocType, String customerDocument,
                                   String obligationID, Long requiredAmount, Integer loanTerm,
                                   String modalityType, String processType)
            throws MalformedURLException {
        Map<String, Object> offerBody = new HashMap<>();
        offerBody.put("documentType", customerDocType);
        offerBody.put("documentNumber", customerDocument);
        offerBody.put("obligationId", obligationID);
        offerBody.put("requiredAmount", requiredAmount);
        offerBody.put("loanTerm", loanTerm);
        if (processType.equals("ANY_FEE")) {
            offerBody.put("payrollCheckProcessId",
                    getFromSession(SessionHelper.SessionData.PAYROLL_CHECK_PROCESS_ID));
        }
        offerBody.put("modalityType", modalityType);
        offerBody.put("processType", processType);

        var gson = new Gson();
        var gsonType = new TypeToken<HashMap<String, Object>>() {
        }.getType();
        var bodyString = gson.toJson(offerBody, gsonType);
        var encryptedBody = WprCipher.generateEncryptedBody(bodyString);

        responseRecalculate = SerenityRest.given()
                .and()
                .contentType(ContentType.JSON)
                .and()
                .header(
                        HeadersEnum.HEADER_X_SECURITY_HMAC.toString(),
                        WprCipher.generateSecurityHmac(offerBody.toString(), "POST"))
                .and()
                .header(
                        HeadersEnum.HEADER_X_SECURITY_SESSION.toString(), getFromSession(SessionHelper.SessionData.XSECURITYSESSION))
                .and()
                .header(
                        HeadersEnum.HEADER_X_SECURITY_REQUEST_ID.toString(), getFromSession(SessionHelper.SessionData.XSECURITYREQUESTID))
                .and()
                .header(HeadersEnum.AUTHORIZATION.toString(), getFromSession(SessionHelper.SessionData.TOKEN))
                .and()
                .body(encryptedBody)
                .when()
                .put(ServicePaths.getBuroInfo());

    }

    public void getOfferInfo(String token) {
        verifySimulationIsFinished(token);
    }

    public void getRenewalStatus(String renewalId, String obligationID, String token) {
        verifyRenewalIsFinished(renewalId, obligationID, token);
    }

    public void getLoadDocuments() throws MalformedURLException {
        Map<String, Object> vendorBody = new HashMap<>();
        vendorBody.put("documentNumber", getFromSession(SessionHelper.SessionData.CUSTOMER_ID));
        var gson = new Gson();
        var gsonType = new TypeToken<HashMap<String, Object>>() {
        }.getType();
        var bodyString = gson.toJson(vendorBody, gsonType);
        var encryptedBody = WprCipher.generateEncryptedBody(bodyString);

        SerenityRest.given()
                .and()
                .contentType(ContentType.JSON)
                .and()
                .header(
                        HeadersEnum.HEADER_X_SECURITY_HMAC.toString(),
                        WprCipher.generateSecurityHmac(vendorBody.toString(), "POST"))
                .and()
                .header(
                        HeadersEnum.HEADER_X_SECURITY_SESSION.toString(), getFromSession(SessionHelper.SessionData.XSECURITYSESSION))
                .and()
                .header(
                        HeadersEnum.HEADER_X_SECURITY_REQUEST_ID.toString(), getFromSession(SessionHelper.SessionData.XSECURITYREQUESTID))
                .and()
                .body(encryptedBody)
                .when()
                .post(ServicePaths.getVendors());

        OnStage.theActorInTheSpotlight().should(
                seeThat(new TheResponseStatusCode(), equalTo(HttpStatus.SC_OK)));
    }

    public void getRenewalStatusError(String renewalId, String obligationID, String token) {
         await().pollInSameThread().atMost(20, SECONDS)
        .until(verifyRenewalStatus(renewalId, obligationID, token));
    }

    private Callable<Boolean> verifySimulationIsFinished(String token) {
        return () -> {
            response = getOfferSimulated(token);
            var code = response.then().extract().body().jsonPath().getString("code");
            return code == null;
        };
    }

    private Callable<Boolean> verifyRenewalIsFinished(String renewalId, String obligationID, String token) {
        return () -> {
            response = getRenewalCode(renewalId, obligationID, token);
            var statusResponse = response.then().extract().body().jsonPath().getString("status");
            return statusResponse != null;
        };
    }

    private Callable<Boolean> verifyRenewalStatus(String renewalId, String obligationID, String token) {
        return () -> {
            response = getRenewalCode(renewalId, obligationID, token);
            var codeError = response.then().extract().body().jsonPath().getString("description");
            return !codeError.equals("InProgress");
        };
    }

    public Response getOfferSimulated(String token) throws MalformedURLException {
        var decryptedBody = WprCipher.decryptRequest(responseRecalculate.then().extract().body().asString());
        return given().relaxedHTTPSValidation().header(HeadersEnum.AUTHORIZATION.toString(), token)
                .contentType(ContentType.JSON)
                .when().get(ServicePaths.getOfferInfo(decryptedBody.getString("idSimulation")));
    }

    public Response getRenewalCode(String renewalId, String obligationID, String token) throws MalformedURLException {
        return given().relaxedHTTPSValidation().header(HeadersEnum.AUTHORIZATION.toString(), token)
                .contentType(ContentType.JSON)
                .when().get(ServicePaths.getRenewalStatus(obligationID, renewalId));
    }

    public void setFactoryStatus(String customerDocument, String obligationID, String adminDocument, String password)
            throws MalformedURLException, InterruptedException, JsonProcessingException {
        var data = DataUserInstance.getInstance().getData();
        TimeUnit.SECONDS.sleep(WAIT_CHANGE_STATUS);
        var date = new Date();
        var formatter = new SimpleDateFormat("yyyy-MM-dd");
        var currentDate = formatter.format(date);
        generatedId = getFromSession(SessionHelper.SessionData.REQUEST_ID);
        var objectMapper = new ObjectMapper();
        var loanBody =objectMapper.readValue(data.get("bodyLoans").toString(), new TypeReference<Loan>() {});
        loanBody.setDate(currentDate);
        loanBody.setRequestId(generatedId);
        loanBody.setLoanCode(obligationID);
        loanBody.setIdentificationNumber(customerDocument);
        loanBody.setFechaHoraGeneracion(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date));
        loanBody.setFilename(currentDate+"/"+generatedId+"/"+loanBody.getPayerName()+"/"+customerDocument+".pdf");
        var authResponseAdmin = requestsUtil.createAdminLoginAuth(adminDocument, password);
        given().relaxedHTTPSValidation()
                .header(HeadersEnum.AUTHORIZATION.toString(), authResponseAdmin.getString(TOKEN))
                .contentType(ContentType.JSON)
                .body(loanBody)
                .when()
                .put(ServicePaths
                        .getLoanReview());
    }

    public void queryOnbaseDocumentLoan(String customerID) throws SQLException {
        var factoryID = "";
        ConnectToParameters connectToParameters = null;
        ResultSet resultSet = null;
        connectToParameters = new ConnectToParameters(Constants.HOST,
                Constants.PORT,
                Constants.PASS,
                Constants.DATABASE,
                Constants.USER);
        var query = "SELECT * FROM parameters.factory_file where filename like '%=?%' and captura_resultado is null;";
        try (PreparedStatement preparedStatement = connectToParameters.connect().prepareStatement(query);){
            preparedStatement.setString(1,customerID);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                factoryID = resultSet.getString(1);
            }
        }catch (SQLException se) {
            LogPrinter.printError(se.toString(),"");
        }finally {
            if (Objects.nonNull(resultSet))
                resultSet.close();
            connectToParameters.tearDown();
        }

        var onbaseDocument = "SELECT * FROM parameters.on_base_document_loan where factory_file_id=?;";
        try (PreparedStatement preparedStatement = connectToParameters.connect().prepareStatement(onbaseDocument);){
            preparedStatement.setString(1,factoryID);
            resultSet = preparedStatement.executeQuery();
        }catch (SQLException se) {
            LogPrinter.printError(se.toString(),"");
        }finally {
            if (Objects.nonNull(resultSet))
                resultSet.close();
            connectToParameters.tearDown();
        }
    }

    public void getDocumentsPayer(JsonObject data, String journeyID,String token) throws JsonProcessingException, MalformedURLException {
        var objectMapper = new ObjectMapper();
        var documentPayer = objectMapper.readValue(data.get("documentPayer").toString(), new TypeReference<OnBaseDocumentPayer>() {
        });
        documentPayer.setJourneyId(journeyID);
        for(var x = 0; x<8 ; x++) {
            given().relaxedHTTPSValidation()
                    .header(HeadersEnum.AUTHORIZATION.toString(), token)
                    .contentType(ContentType.JSON)
                    .body(documentPayer)
                    .when().post(ServicePaths.getDocumentPayer());
            if (SerenityRest.then().extract().statusCode() == HttpStatus.SC_OK) {
                break;
            }
        }
    }
}