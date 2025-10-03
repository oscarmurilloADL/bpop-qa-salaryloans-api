package co.com.bancopopular.automation.features.steps.cashieroffice;

import co.com.bancopopular.automation.abilities.ConnectToParameters;
import co.com.bancopopular.automation.conf.StepBase;
import co.com.bancopopular.automation.constants.Constants;
import co.com.bancopopular.automation.exceptions.UserNotFoundException;
import co.com.bancopopular.automation.models.payrolls.Payroll;
import co.com.bancopopular.automation.utils.DataUserInstance;
import co.com.bancopopular.automation.utils.RequestsUtil;
import co.com.bancopopular.automation.utils.SessionHelper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;

import java.net.MalformedURLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static co.com.bancopopular.automation.utils.SessionHelper.setInSession;

public class CashierOfficeStepDefinitions extends StepBase {

    @Steps
    CashierOfficeRequests cashierOfficeRequests = new CashierOfficeRequests();
    CashierOfficeAssertions cashierOfficeAssertions = new CashierOfficeAssertions();

    private ConnectToParameters connectToParameters;
    private PreparedStatement preparedStatement;
    private static final String CUSTOMER_DOC_TYPE = "customerDocType";
    private static final String CUSTOMER_DOCUMENT = "customerDocument";

    RequestsUtil requestsUtil = new RequestsUtil();

    @When("{word} despliega la lista de pagadurias disponibles para el sector {word}")
    public void displaysTheListOfAvailablePaymentsForSector(String actor, String sector) throws MalformedURLException {
        var sectorNumber="";
        if (sector.equals("pensionados")){
            sectorNumber = "2";
        }
        var data = DataUserInstance.getInstance().getData();
        cashierOfficeRequests.getList(data.get(CUSTOMER_DOC_TYPE).getAsString(),
                data.get(CUSTOMER_DOCUMENT).getAsString(),
                sectorNumber);
    }

    @When("se comparan los datos contra libranzas")
    public void verifyParamerizedData() throws SQLException {
        List<Payroll> payrolls = new ArrayList<>();
        try{
            ResultSet resultSet = null;
            connectToParameters = new ConnectToParameters(Constants.HOST,
                    Constants.PORT,
                    Constants.PASS,
                    Constants.DATABASE,
                    Constants.USER);
            var query = "SELECT nit, sector_number, subsector_number, unique_name, status FROM parameters.cashiers_office ORDER BY NIT ASC;";
            preparedStatement = connectToParameters.connect().prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                payrolls.add(new Payroll(resultSet.getString(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        resultSet.getInt(5)));
            }
            resultSet.close();
            setInSession(SessionHelper.SessionData.MYSQL_PAYROLLS, payrolls);
        }finally {
            preparedStatement.close();
            connectToParameters.tearDown();
        }
    }

    @Then("se muestra la lista completa")
    public void buroStatusValid() throws UserNotFoundException {
        assertionsUtil.shouldSeeSuccessfulStatusCode();
        cashierOfficeAssertions.verifyFullAvailableListOfPayrolls();
    }

    @Then("se muestra la lista completa excepto {}")
    public void buroStatusValid(String missingPayroll) throws UserNotFoundException {
        assertionsUtil.shouldSeeSuccessfulStatusCode();
        cashierOfficeAssertions.verifyFullAvailableListExceptMissingPayroll(missingPayroll);
    }

    @Then("se valida la consistencia de los datos en mariaDB")
    public void validateMariaDB(){
        cashierOfficeAssertions.validateDataConsistencyWithMariaDB();
    }
}
