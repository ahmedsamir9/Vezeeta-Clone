package com.example.vezetaaclone.data;

import com.example.vezetaaclone.pojo.productmodel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface productInterface {
    @GET("?search=aspirin&limit=100")
    public Call<productmodel> getproducts();
}
