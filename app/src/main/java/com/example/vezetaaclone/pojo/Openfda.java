
package com.example.vezetaaclone.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Openfda {

    @SerializedName("nui")
    @Expose
    private List<String> nui = null;
    @SerializedName("is_original_packager")
    @Expose
    private List<Boolean> isOriginalPackager = null;
    @SerializedName("pharm_class_moa")
    @Expose
    private List<String> pharmClassMoa = null;
    @SerializedName("spl_set_id")
    @Expose
    private List<String> splSetId = null;
    @SerializedName("pharm_class_cs")
    @Expose
    private List<String> pharmClassCs = null;
    @SerializedName("manufacturer_name")
    @Expose
    private List<String> manufacturerName = null;
    @SerializedName("pharm_class_pe")
    @Expose
    private List<String> pharmClassPe = null;
    @SerializedName("pharm_class_epc")
    @Expose
    private List<String> pharmClassEpc = null;
    @SerializedName("rxcui")
    @Expose
    private List<String> rxcui = null;
    @SerializedName("unii")
    @Expose
    private List<String> unii = null;

    public List<String> getNui() {
        return nui;
    }

    public void setNui(List<String> nui) {
        this.nui = nui;
    }

    public List<Boolean> getIsOriginalPackager() {
        return isOriginalPackager;
    }

    public void setIsOriginalPackager(List<Boolean> isOriginalPackager) {
        this.isOriginalPackager = isOriginalPackager;
    }

    public List<String> getPharmClassMoa() {
        return pharmClassMoa;
    }

    public void setPharmClassMoa(List<String> pharmClassMoa) {
        this.pharmClassMoa = pharmClassMoa;
    }

    public List<String> getSplSetId() {
        return splSetId;
    }

    public void setSplSetId(List<String> splSetId) {
        this.splSetId = splSetId;
    }

    public List<String> getPharmClassCs() {
        return pharmClassCs;
    }

    public void setPharmClassCs(List<String> pharmClassCs) {
        this.pharmClassCs = pharmClassCs;
    }

    public List<String> getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(List<String> manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public List<String> getPharmClassPe() {
        return pharmClassPe;
    }

    public void setPharmClassPe(List<String> pharmClassPe) {
        this.pharmClassPe = pharmClassPe;
    }

    public List<String> getPharmClassEpc() {
        return pharmClassEpc;
    }

    public void setPharmClassEpc(List<String> pharmClassEpc) {
        this.pharmClassEpc = pharmClassEpc;
    }

    public List<String> getRxcui() {
        return rxcui;
    }

    public void setRxcui(List<String> rxcui) {
        this.rxcui = rxcui;
    }

    public List<String> getUnii() {
        return unii;
    }

    public void setUnii(List<String> unii) {
        this.unii = unii;
    }

}
