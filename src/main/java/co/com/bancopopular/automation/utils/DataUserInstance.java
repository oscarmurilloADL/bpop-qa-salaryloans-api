package co.com.bancopopular.automation.utils;

import com.google.gson.JsonObject;

public final class DataUserInstance {


  private static DataUserInstance instance;
  private JsonObject data;

  private DataUserInstance() {
  }

  public static DataUserInstance getInstance() {
    if (instance == null) {
      instance = new DataUserInstance();
    }
    return instance;
  }

  public JsonObject getData() {
    return data;
  }

  public void setData(JsonObject data) {
    this.data = data;
  }

}