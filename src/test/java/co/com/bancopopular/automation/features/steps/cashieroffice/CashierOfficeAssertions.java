package co.com.bancopopular.automation.features.steps.cashieroffice;

import co.com.bancopopular.automation.data.Environment;
import co.com.bancopopular.automation.exceptions.UserNotFoundException;
import co.com.bancopopular.automation.models.payrolls.Payroll;
import co.com.bancopopular.automation.utils.SessionHelper;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.annotations.Step;
import org.aeonbits.owner.ConfigFactory;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class CashierOfficeAssertions {
    private final Environment environment = ConfigFactory.create(Environment.class);
    private static final String USER_NOT_FOUND_EXCEPTION= "Type of user does not exist in the system";
    private static final String PAYERCODE = "payerCode";
    private static final String PAYERNAME = "payerName";
    private static final String PAYERNIT = "payerNit";

    @Step("Verificar que la lista de pagadurias disponibles este completa")
    public void verifyFullAvailableListOfPayrolls() throws UserNotFoundException {
        var data = environment.fullListPayrolls();

        Optional<String> dataOptional = Optional.ofNullable(data);
        var dataVerified = dataOptional
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_EXCEPTION));

        var reader = new JsonReader(new StringReader(dataVerified));
        var dataPayroll = JsonParser.parseReader(reader).getAsJsonArray();

        List<AvailablePayrolls> availablePayrolls = Arrays
                .asList(SerenityRest.then().extract().body().as(AvailablePayrolls[].class));

        for (var i = 0; i < availablePayrolls.size(); i++) {
            assertThat(availablePayrolls.get(i).getPayerCode(),
                    is(dataPayroll.get(i).getAsJsonObject().get(PAYERCODE).getAsString()));
            assertThat(availablePayrolls.get(i).getPayerName(),
                    is(dataPayroll.get(i).getAsJsonObject().get(PAYERNAME).getAsString()));
            assertThat(availablePayrolls.get(i).getPayerNit(),
                    is(dataPayroll.get(i).getAsJsonObject().get(PAYERNIT).getAsString()));
        }
    }

    @Step("Verificar que la lista de pagadurias disponibles este completa excepto la que esta activa o en proceso")
    public void verifyFullAvailableListExceptMissingPayroll(String missingPayroll) throws UserNotFoundException {
        var data = environment.fullListPayrolls();

        Optional<String> dataOptional = Optional.ofNullable(data);
        var dataVerified = dataOptional
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_EXCEPTION));

        var reader = new JsonReader(new StringReader(dataVerified));
        var dataPayroll = JsonParser.parseReader(reader).getAsJsonArray();

        for (var i = 0; i < dataPayroll.size(); i++) {
            if(dataPayroll.get(i).getAsJsonObject().get(PAYERNAME).getAsString().equals(missingPayroll)){
                dataPayroll.remove(i);
                break;
            }
        }

        List<AvailablePayrolls> availablePayrolls = Arrays
                .asList(SerenityRest.then().extract().body().as(AvailablePayrolls[].class));

        for (var i = 0; i < availablePayrolls.size(); i++) {
            assertThat(availablePayrolls.get(i).getPayerCode(),
                    is(dataPayroll.get(i).getAsJsonObject().get(PAYERCODE).getAsString()));
            assertThat(availablePayrolls.get(i).getPayerName(),
                    is(dataPayroll.get(i).getAsJsonObject().get(PAYERNAME).getAsString()));
            assertThat(availablePayrolls.get(i).getPayerNit(),
                    is(dataPayroll.get(i).getAsJsonObject().get(PAYERNIT).getAsString()));
        }
    }

    public void validateDataConsistencyWithMariaDB() {
        List<Payroll> payrollsExcel = getFromSession(SessionHelper.SessionData.EXCEL_PAYROLLS);
        List<Payroll> payrollsMysql = getFromSession(SessionHelper.SessionData.MYSQL_PAYROLLS);

        for(var x = 0; x < payrollsMysql.size(); x++ ){
            Payroll mysqlItem= payrollsMysql.get(x);
            Payroll excelItem= payrollsExcel.get(x);
            if (excelItem.getSector() == 3) {
                assertThat(mysqlItem.getNit(), is(excelItem.getNit()));
                assertThat(mysqlItem.getSector(), is(excelItem.getSector()));
                assertThat(mysqlItem.getSubsector(), is(excelItem.getSubsector()));
                assertThat(mysqlItem.getName(), is(excelItem.getName()));
                assertThat(mysqlItem.getStatus(), is(excelItem.getStatus()));
            }
        }
    }
}
