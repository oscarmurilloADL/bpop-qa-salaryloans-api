package co.com.bancopopular.automation.features.steps.bankinsurance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coverage {

  @SerializedName("description")
  @Expose
  private String description;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}