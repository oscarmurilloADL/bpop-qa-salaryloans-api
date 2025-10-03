package co.com.bancopopular.automation.features.steps.bankinsurance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class BankInsuranceCoverages {

  @SerializedName("code")
  @Expose
  private String code;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("price")
  @Expose
  private String price;
  @SerializedName("spouseCoveraged")
  @Expose
  private Boolean spouseCoveraged;
  @SerializedName("maxCoverage")
  @Expose
  private Boolean maxCoverage;
  @SerializedName("coverages")
  @Expose
  private List<Coverage> coverages = null;

  @SerializedName("services")
  @Expose
  private List<Services> services = null;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public Boolean getSpouseCoveraged() {
    return spouseCoveraged;
  }

  public void setSpouseCoveraged(Boolean spouseCoveraged) {
    this.spouseCoveraged = spouseCoveraged;
  }

  public Boolean getMaxCoverage() {
    return maxCoverage;
  }

  public void setMaxCoverage(Boolean maxCoverage) {
    this.maxCoverage = maxCoverage;
  }

  public List<Coverage> getCoverages() {
    return coverages;
  }

  public void setCoverages(List<Coverage> coverages) {
    this.coverages = coverages;
  }

  public List<Services> getServices() {return services;}

  public void setServices(List<Services> services) {this.services = services;}
}
