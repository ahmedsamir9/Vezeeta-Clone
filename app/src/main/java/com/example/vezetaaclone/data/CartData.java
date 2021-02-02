package com.example.vezetaaclone.data;

import androidx.annotation.NonNull;

import com.example.vezetaaclone.pojo.Result;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class CartData {
    public static ArrayList<Result> data=new ArrayList<>();
    public static HashMap<String,String> images=new HashMap<>();
    public static HashMap<String,String>prices=new HashMap<>();
    public static HashMap<Result,Boolean>checked=new HashMap<>();
    private static DatabaseReference ref= FirebaseDatabase.getInstance("https://vezetaaclone-default-rtdb.firebaseio.com/").getReference().child("CartList");
    private static DatabaseReference refP= FirebaseDatabase.getInstance("https://vezetaaclone-default-rtdb.firebaseio.com/").getReference().child("PriceList");
    private static DatabaseReference refI= FirebaseDatabase.getInstance("https://vezetaaclone-default-rtdb.firebaseio.com/").getReference().child("ImageList");
    private static boolean OneRetrive=false;

    public static void RetriveData(){
        if (OneRetrive)return;
        OneRetrive=true;


        refI.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap:snapshot.getChildren()){
                    HashMap<String,String>mp=(HashMap<String, String>)snap.getValue();
                    for(String val: mp.values())
                        CartData.images.put(snap.getKey(),val);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        refP.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap:snapshot.getChildren()){
                    HashMap<String,String>mp=(HashMap<String, String>)snap.getValue();
                   // CartData.prices=mp;
                    for(String val: mp.values())
                        CartData.prices.put(snap.getKey(),val);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Result> dataTemp=new ArrayList<>();
                for(DataSnapshot snap:snapshot.getChildren()){
                    for (DataSnapshot snap2:snap.getChildren()) {
                        Result mp = (Result) snap2.getValue(Result.class);
                            dataTemp.add((Result) mp);
                            checked.put(mp, true);
                    }

                }
                CartData.data=dataTemp;
                //madapter.setData(CartData.data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    public static void updateData(ArrayList<Result>newdata){
        ref.removeValue();
        return;
       /* ref= FirebaseDatabase.getInstance().getReference().child("CartList");
        data.clear();
        for(int i=0;i<newdata.size();i++) {
            data.set(i, newdata.get(i));
            ref.removeValue(new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                }
            });
            ref.push().getKey();
            ref.push().setValue(newdata.get(i));
        }*/
    }
}
