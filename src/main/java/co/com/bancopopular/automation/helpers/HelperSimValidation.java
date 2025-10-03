package co.com.bancopopular.automation.helpers;

import co.com.bancopopular.automation.models.simvalidation.RequestBodySimValidationCRM;
import co.com.bancopopular.automation.utils.CipherUtil;
import com.google.gson.JsonObject;

public class HelperSimValidation {
    private HelperSimValidation() {
    }

    public static RequestBodySimValidationCRM getBodySimValidationCrm(JsonObject data, String advisorJourneyId){
        return RequestBodySimValidationCRM.builder()
                .administrativeOfficeCode(CipherUtil.cipherRSAOAEP512(data.get("officeCode").getAsString()))
                .administrativeOfficeName(CipherUtil.cipherRSAOAEP512(data.get("administrativeOfficeName").getAsString()))
                .advisorJourneyId(CipherUtil.cipherRSAOAEP512(advisorJourneyId))
                .businessAdvisorDocument(CipherUtil.cipherRSAOAEP512(data.get("advisorDocument").getAsString()))
                .documentNumber(CipherUtil.cipherRSAOAEP512(data.get("customerDocument").getAsString()))
                .documentType(CipherUtil.cipherRSAOAEP512(data.get("customerDocType").getAsString()))
                .officeCode(CipherUtil.cipherRSAOAEP512(data.get("officeCode").getAsString()))
                .officeName(CipherUtil.cipherRSAOAEP512(data.get("officeName").getAsString()))
                .salesModel(CipherUtil.cipherRSAOAEP512(data.get("salesModel").getAsString()))
                .build();
    }
}
