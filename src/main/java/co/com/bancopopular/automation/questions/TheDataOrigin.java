package co.com.bancopopular.automation.questions;

import co.com.bancopopular.automation.models.search.DataOriginToValidate;
import com.amazonaws.services.connect.model.UserNotFoundException;
import com.google.gson.Gson;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import java.util.Optional;

public class TheDataOrigin implements Question<DataOriginToValidate> {
    private final String dataJsonRequest;

    public TheDataOrigin(String dataJsonRequest) {
        this.dataJsonRequest = dataJsonRequest;
    }

    public static TheDataOrigin forConsultPayrollLoan(String dataJsonRequest){
        return new TheDataOrigin(dataJsonRequest);
    }

    @Override
    public DataOriginToValidate answeredBy(Actor actor) {
        var gson = new Gson();
        Optional<String> dataOptional = Optional.ofNullable(dataJsonRequest);
        var dataOrigen = dataOptional.orElseThrow(() -> new UserNotFoundException("Data no encontrada"));
        DataOriginToValidate dataOriginToValidate = gson.fromJson(dataOrigen, DataOriginToValidate.class);

        return DataOriginToValidate.builder()
                .administrativeOffice(dataOriginToValidate.getAdministrativeOffice())
                .administrativeOfficeName(dataOriginToValidate.getAdministrativeOfficeName())
                .advisorDocument(dataOriginToValidate.getAdvisorDocument())
                .customerDocType(dataOriginToValidate.getCustomerDocType())
                .customerDocument(dataOriginToValidate.getCustomerDocument())
                .obligationID(dataOriginToValidate.getObligationID())
                .officeCode(dataOriginToValidate.getOfficeCode())
                .officeName(dataOriginToValidate.getOfficeName())
                .payerUniqueName(dataOriginToValidate.getPayerUniqueName())
                .salesModel(dataOriginToValidate.getSalesModel())
                .build();

    }
}
