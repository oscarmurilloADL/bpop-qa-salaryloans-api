package co.com.bancopopular.automation.features.steps.toggles;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TogglesDescription {

  @SerializedName("featureName")
  @Expose
  private String featureName;

  @SerializedName("featureEnabled")
  @Expose
  private String featureEnabled;

  @SerializedName("featureDescription")
  @Expose
  private String featureDescription;


  public String getFeatureName() {
    return featureName;
  }

  public void setFeatureName(String featureName) {
    this.featureName = featureName;
  }

  public String getFeatureEnabled() {
    return featureEnabled;
  }

  public void setFeatureEnabled(String featureEnabled) {
    this.featureEnabled = featureEnabled;
  }

  public String getFeatureDescription() {
    return featureDescription;
  }

  public void setFeatureDescription(String featureDescription) {
    this.featureDescription = featureDescription;
  }

}