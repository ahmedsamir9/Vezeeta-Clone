package com.example.vezetaaclone.data;

import com.example.vezetaaclone.pojo.productmodel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface productInterface {
    @GET("?limit=100")

    public Call<productmodel> getproducts(
            @Query("search") String search
    );
}
