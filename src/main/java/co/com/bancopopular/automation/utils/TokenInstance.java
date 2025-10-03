package co.com.bancopopular.automation.utils;

import io.restassured.path.json.JsonPath;

public final class TokenInstance {


  private static TokenInstance instance;
  private JsonPath data;
  private String userDocument;

  private TokenInstance() {
  }

  public static TokenInstance getInstance() {
    if (instance == null) {
      instance = new TokenInstance();
    }
    return instance;
  }

  public JsonPath getData() {
    return data;
  }

  public void setData(JsonPath data) {
    this.data = data;
  }

  public String getDocumentData() {
    return userDocument;
  }

  public void setUserDocumentData(String userDocument) {
    this.userDocument = userDocument;
  }
}
