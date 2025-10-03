package co.com.bancopopular.automation.features.steps.cashieroffice;

import co.com.bancopopular.automation.models.payrolls.Payroll;
import co.com.bancopopular.automation.rest.apis.ServicePaths;
import co.com.bancopopular.automation.utils.RequestsUtil;
import co.com.bancopopular.automation.utils.SessionHelper;
import io.restassured.path.json.JsonPath;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;

import static co.com.bancopopular.automation.utils.SessionHelper.setInSession;
import static net.serenitybdd.rest.SerenityRest.given;

public class CashierOfficeRequests {

    private static final String EXCEL_DIR = "src/test/resources/files/insumo.xlsx";
    RequestsUtil requestsUtil = new RequestsUtil();

    public static void retrievePayrollsExcel() throws IOException {
        List<Payroll> payrolls = new ArrayList<>();
        Sheet sheet;
        try (var file = new FileInputStream(EXCEL_DIR); var workbook = new XSSFWorkbook(file)) {
            sheet = workbook.getSheetAt(0);

            for (var row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                payrolls.add(createPayrollFromRow(row));
            }
            setInSession(SessionHelper.SessionData.EXCEL_PAYROLLS, payrolls);
        }
    }
    private static Payroll createPayrollFromRow(Row row) {
        var nit = row.getCell(0).getStringCellValue().replace("N", "");
        var sector = (int) row.getCell(1).getNumericCellValue();
        var subsector = (int) row.getCell(2).getNumericCellValue();
        var name = row.getCell(3).getStringCellValue();
        var status = (int) row.getCell(4).getNumericCellValue();
        return new Payroll(nit, sector, subsector, name,status);
    }
    public void getList(String customerDocType,
                        String customerDocument, String sector)
            throws MalformedURLException {
        JsonPath authResponse = requestsUtil.getAuthOTP();
        given().relaxedHTTPSValidation().header("Authorization", authResponse.getString("token"))
                .contentType("application/json")
                .when().get(ServicePaths.getCashierOfficeList(customerDocType, customerDocument, sector));
    }
}
