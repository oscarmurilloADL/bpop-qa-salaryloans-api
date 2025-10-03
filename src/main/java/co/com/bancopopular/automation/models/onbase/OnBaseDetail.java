package co.com.bancopopular.automation.models.onbase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

public class OnBaseDetail {

  @SerializedName("onBasePresent")
  @Expose @Getter @Setter
  private Boolean onBasePresent;

  @SerializedName("expired")
  @Expose @Getter @Setter
  private Boolean expired;

  @SerializedName("cucCode")
  @Expose @Getter @Setter
  private String cucCode;

  @SerializedName("cucCode")
  @Expose @Getter @Setter
  private String expirationDatePresent;

  @SerializedName("expired")
  @Expose @Getter @Setter
  private Boolean documentAlreadyInCloud;

}