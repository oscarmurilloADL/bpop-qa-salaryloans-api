package co.com.bancopopular.automation.features.steps.cashieroffice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvailablePayrolls {
    @SerializedName("payerCode")
    @Expose
    private String payerCode;

    @SerializedName("payerName")
    @Expose
    private String payerName;

    @SerializedName("payerNit")
    @Expose
    private String payerNit;

    @SerializedName("extractor")
    @Expose
    private String extractor;

    @SerializedName("platform")
    @Expose
    private String platform;

    @SerializedName("sector")
    @Expose
    private String sector;

    @SerializedName("subSector")
    @Expose
    private String subSector;


    public String getPayerCode() {
        return payerCode;
    }

    public String getPayerName() {
        return payerName;
    }

    public String getPayerNit() {
        return payerNit;
    }

    public String getExtractor() {
        return extractor;
    }

    public String getPlatform() {
        return platform;
    }

    public String getSector() {
        return sector;
    }

    public String getSubSector() {
        return subSector;
    }

}
