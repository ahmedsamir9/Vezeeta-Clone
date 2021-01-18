
package com.example.vezetaaclone.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {

    @SerializedName("product_ndc")
    @Expose
    private String productNdc;
    @SerializedName("generic_name")
    @Expose
    private String genericName;
    @SerializedName("labeler_name")
    @Expose
    private String labelerName;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("active_ingredients")
    @Expose
    private List<ActiveIngredient> activeIngredients = null;
    @SerializedName("finished")
    @Expose
    private Boolean finished;
    @SerializedName("packaging")
    @Expose
    private List<Packaging> packaging = null;
    @SerializedName("listing_expiration_date")
    @Expose
    private String listingExpirationDate;
    @SerializedName("openfda")
    @Expose
    private Openfda openfda;
    @SerializedName("marketing_category")
    @Expose
    private String marketingCategory;
    @SerializedName("dosage_form")
    @Expose
    private String dosageForm;
    @SerializedName("spl_id")
    @Expose
    private String splId;
    @SerializedName("product_type")
    @Expose
    private String productType;
    @SerializedName("route")
    @Expose
    private List<String> route = null;
    @SerializedName("marketing_start_date")
    @Expose
    private String marketingStartDate;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("application_number")
    @Expose
    private String applicationNumber;
    @SerializedName("brand_name_base")
    @Expose
    private String brandNameBase;

    public String getProductNdc() {
        return productNdc;
    }

    public void setProductNdc(String productNdc) {
        this.productNdc = productNdc;
    }

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public String getLabelerName() {
        return labelerName;
    }

    public void setLabelerName(String labelerName) {
        this.labelerName = labelerName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public List<ActiveIngredient> getActiveIngredients() {
        return activeIngredients;
    }

    public void setActiveIngredients(List<ActiveIngredient> activeIngredients) {
        this.activeIngredients = activeIngredients;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public List<Packaging> getPackaging() {
        return packaging;
    }

    public void setPackaging(List<Packaging> packaging) {
        this.packaging = packaging;
    }

    public String getListingExpirationDate() {
        return listingExpirationDate;
    }

    public void setListingExpirationDate(String listingExpirationDate) {
        this.listingExpirationDate = listingExpirationDate;
    }

    public Openfda getOpenfda() {
        return openfda;
    }

    public void setOpenfda(Openfda openfda) {
        this.openfda = openfda;
    }

    public String getMarketingCategory() {
        return marketingCategory;
    }

    public void setMarketingCategory(String marketingCategory) {
        this.marketingCategory = marketingCategory;
    }

    public String getDosageForm() {
        return dosageForm;
    }

    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }

    public String getSplId() {
        return splId;
    }

    public void setSplId(String splId) {
        this.splId = splId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public List<String> getRoute() {
        return route;
    }

    public void setRoute(List<String> route) {
        this.route = route;
    }

    public String getMarketingStartDate() {
        return marketingStartDate;
    }

    public void setMarketingStartDate(String marketingStartDate) {
        this.marketingStartDate = marketingStartDate;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public String getBrandNameBase() {
        return brandNameBase;
    }

    public void setBrandNameBase(String brandNameBase) {
        this.brandNameBase = brandNameBase;
    }

}
