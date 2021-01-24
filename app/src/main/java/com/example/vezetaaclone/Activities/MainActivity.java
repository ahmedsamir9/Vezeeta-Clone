package com.example.vezetaaclone;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.example.vezetaaclone.pojo.CategoryModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    Button showLocation;
    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager myFragmentManager = getSupportFragmentManager();
        mapFragment = (SupportMapFragment) myFragmentManager
                .findFragmentById(R.id.map);
        firebaseFirestore=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();


        //showLocation = findViewById(R.id.showlocation_btn);



    }
    public void logout (View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
    public void Search (View view){
        startActivity(new Intent(getApplicationContext(), MainActivity2.class));
        finish();
    }
    private String getCompleteAddress(double longtiude,double latitude)
    {
        String address="";
        Geocoder geocoder=new Geocoder(MainActivity.this, Locale.getDefault());
        try {
            List<Address> addressList= geocoder.getFromLocation(latitude,longtiude,1);
            if(address!=null)
            {
                Address returnAddress=addressList.get(0);
                StringBuilder stringBuilder=new StringBuilder("");
                for(int i=0;i<=returnAddress.getMaxAddressLineIndex();i++)
                {
                    stringBuilder.append(returnAddress.getAddressLine(i)).append("\n");
                }
                address=stringBuilder.toString();
            }
            else
            {
                Toast.makeText(this, "address Not Found", Toast.LENGTH_SHORT).show();
            }

        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return address;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;

        firebaseFirestore.collection("PharmacyUsers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
               //     Toast.makeText(MainActivity.this, mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                    for(QueryDocumentSnapshot documentSnapshot:task.getResult())
                    {
                        if(documentSnapshot.getString("Email").equals(mAuth.getCurrentUser().getEmail()))
                        {
                            double longi;
                            double lati;
                            longi=  documentSnapshot.getDouble("Longitude");
                            lati= documentSnapshot.getDouble("Latitude");
                        //    Toast.makeText(MainActivity.this, String.valueOf(lati), Toast.LENGTH_SHORT).show();
                            LatLng location=new LatLng(lati,longi);
                            mMap.addMarker(new MarkerOptions().position(location).title(getCompleteAddress(lati,longi)));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,14F));

                        }
                    }
                }
            }
        });


    }

    public void showMyLocation(View view) {
        mapFragment.getMapAsync(this);

    }
}
