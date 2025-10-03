package co.com.bancopopular.automation.models.onbase;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OnBaseDocument {

  @SerializedName("codeDocument")
  @Expose
  private int codeDocument;

  @SerializedName("nameDocument")
  @Expose
  private String nameDocument;

  @SerializedName("required")
  @Expose
  private Boolean required;

  @SerializedName("required")
  @Expose
  private Boolean special;

  @SerializedName("required")
  @Expose
  private Boolean bankingInsurance;

  @SerializedName("onbase")
  @Expose
  private OnBaseDetail onbase;

  @SerializedName("applyMandatoryFront")
  @Expose
  private Boolean applyMandatoryFront;

  @SerializedName("checked")
  @Expose
  private Boolean checked;

}