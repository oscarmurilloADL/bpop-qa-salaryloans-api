package co.com.bancopopular.automation.utils;

import co.com.bancopopular.automation.models.onbase.OnBaseDetail;
import co.com.bancopopular.automation.models.onbase.OnBaseDocument;

public class CreateOnBaseDocuments {

  public OnBaseDocument createIdDocument(){
    OnBaseDetail onBaseDetail = new OnBaseDetail();
    OnBaseDocument idDocument = new OnBaseDocument();
    idDocument.setCodeDocument(1);
    idDocument.setNameDocument("DOCUMENTO DE IDENTIDAD");
    idDocument.setRequired(true);
    idDocument.setSpecial(null);
    idDocument.setBankingInsurance(false);
    idDocument.setApplyMandatoryFront(false);
    idDocument.setChecked(true);

    onBaseDetail.setOnBasePresent(false);
    onBaseDetail.setExpired(false);
    onBaseDetail.setCucCode("0001");
    onBaseDetail.setExpirationDatePresent(null);
    onBaseDetail.setDocumentAlreadyInCloud(false);
    idDocument.setOnbase(onBaseDetail);
    return idDocument;
  }
}
