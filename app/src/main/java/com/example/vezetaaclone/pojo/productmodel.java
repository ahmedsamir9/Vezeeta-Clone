
package com.example.vezetaaclone.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class productmodel {

   /* @SerializedName("meta")
    @Expose
    private Meta meta;*/
    @SerializedName("results")
    @Expose
    private List<Result> results = null;
  /*  public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
*/
    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

}
