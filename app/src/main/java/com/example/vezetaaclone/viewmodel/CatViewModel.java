package com.example.vezetaaclone.viewmodel;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vezetaaclone.pojo.CategoryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CatViewModel extends ViewModel {
    public MutableLiveData<List<CategoryModel>> CatMutableLiveData = new MutableLiveData<List<CategoryModel>>();
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://vezetaaclone-default-rtdb.firebaseio.com/");
    DatabaseReference myRef = database.getReference().child("Category");
    List<CategoryModel>Data=new ArrayList<>() ;
    ImageViewModel imageViewModel;
    ProductViewModel productViewModel;
    Context context;

    public void get_Cat(Context context, ImageViewModel imageViewModel,ProductViewModel productViewModel) {
        this.context = context;
        this.imageViewModel=imageViewModel;
        this.productViewModel=productViewModel;
        get_cat_fromdp();
        CatMutableLiveData.setValue(Data);
    }

    private void get_cat_fromdp() {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    CategoryModel u=snapshot.getValue(CategoryModel.class);
                    Data.add(u);
                }
                imageViewModel.getImages(context,productViewModel);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(context,"Failed",Toast.LENGTH_LONG).show();
            }
        });
    }
}
