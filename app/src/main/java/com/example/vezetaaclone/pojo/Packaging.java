
package com.example.vezetaaclone.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Packaging {

    @SerializedName("marketing_start_date")
    @Expose
    private String marketingStartDate;
    @SerializedName("package_ndc")
    @Expose
    private String packageNdc;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("sample")
    @Expose
    private Boolean sample;

    public String getMarketingStartDate() {
        return marketingStartDate;
    }

    public void setMarketingStartDate(String marketingStartDate) {
        this.marketingStartDate = marketingStartDate;
    }

    public String getPackageNdc() {
        return packageNdc;
    }

    public void setPackageNdc(String packageNdc) {
        this.packageNdc = packageNdc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getSample() {
        return sample;
    }

    public void setSample(Boolean sample) {
        this.sample = sample;
    }

}
