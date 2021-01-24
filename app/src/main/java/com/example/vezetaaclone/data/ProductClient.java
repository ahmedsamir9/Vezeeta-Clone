package com.example.vezetaaclone.data;

import com.example.vezetaaclone.pojo.productmodel;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductClient {
    private static final String BASE_URL="https://api.fda.gov/drug/ndc.json/";
    private productInterface productInterface;
    private static ProductClient INSTANCE;

    public ProductClient() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        productInterface =retrofit.create(productInterface.class);
    }

    public static ProductClient getINSTANCE() {
        if(INSTANCE==null)
            INSTANCE=new ProductClient();
        return INSTANCE;
    }
    public Call<productmodel>getProducts(String search){
        return productInterface.getproducts(search);
    }
}
