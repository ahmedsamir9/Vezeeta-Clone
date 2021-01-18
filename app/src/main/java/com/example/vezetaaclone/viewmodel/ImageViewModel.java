package com.example.vezetaaclone.viewmodel;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ImageViewModel extends ViewModel {
    public MutableLiveData<List<String>> imageMutableLiveData=new MutableLiveData<List<String>>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child("ProductImages");
    List<String> Data=new ArrayList<>();
    ProductViewModel productViewModel;
    Context context;
    public void getImages(Context context,ProductViewModel productViewModel){
        this.context=context;
        this.productViewModel=productViewModel;
        getimagesfromdp();
        imageMutableLiveData.setValue(Data);
    }
    public void getimagesfromdp(){
        this.context=context;
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    String s=snapshot.getValue(String.class);
                    Data.add(s);
                }
                productViewModel.getProduct(context);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(context,"Failed",Toast.LENGTH_LONG).show();
            }
        });

    }
}
