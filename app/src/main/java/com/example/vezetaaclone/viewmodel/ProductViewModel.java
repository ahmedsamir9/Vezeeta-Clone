package com.example.vezetaaclone.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vezetaaclone.data.ProductClient;
import com.example.vezetaaclone.pojo.productmodel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductViewModel extends ViewModel {
    public MutableLiveData<productmodel> ProductMutableLiveData=new MutableLiveData<>();
    Context context;
    public void getProduct(Context context){
        this.context=context;
        ProductClient.getINSTANCE().getProducts().enqueue(new Callback<productmodel>() {
            @Override
            public void onResponse(Call<productmodel> call, Response<productmodel> response) {
                ProductMutableLiveData.setValue(response.body());
            }
            @Override
            public void onFailure(Call<productmodel> call, Throwable t) {
               // Toast.makeText(context,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
