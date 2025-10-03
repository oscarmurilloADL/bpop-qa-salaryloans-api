package co.com.bancopopular.automation.questions;

import co.com.bancopopular.automation.models.search.DataOriginToValidate;
import co.com.bancopopular.automation.models.search.DataResponse;
import com.amazonaws.services.connect.model.UserNotFoundException;
import com.google.gson.Gson;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import java.util.Optional;

public class TheCustomerInquiry implements Question <DataResponse>{

    private final String jsonResponse;

    public TheCustomerInquiry(String jsonResponse) {
        this.jsonResponse = jsonResponse;
    }


    public static TheCustomerInquiry ofTheLasResponse(String jsonResponse){
        return new TheCustomerInquiry(jsonResponse);
    }

    @Override
    public DataResponse answeredBy(Actor actor) {
        var gson = new Gson();
        Optional<String> dataOptional = Optional.ofNullable(jsonResponse);
        var dataOrigen = dataOptional.orElseThrow(() -> new UserNotFoundException("Data no encontrada"));
        DataOriginToValidate dataOriginToValidate = gson.fromJson(dataOrigen, DataOriginToValidate.class);

        return dataOriginToValidate.getDataResponse();
    }
}
