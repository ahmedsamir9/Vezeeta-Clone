package com.example.vezetaaclone.Activities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.vezetaaclone.Firestore_objs.Pharmacy;
import com.example.vezetaaclone.R;
import com.example.vezetaaclone.UI.Fragments.CartFragment;
import com.example.vezetaaclone.UI.Fragments.ChatListFragment;
import com.example.vezetaaclone.UI.Fragments.Search;
import com.example.vezetaaclone.UI.Fragments.moreFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.example.vezetaaclone.pojo.CategoryModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button showLocation;
    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;
    private static final String TAG = "MainActivity";
    Toolbar mActionBarToolbar;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        tv = findViewById(R.id.toolbar_title);
        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Search()).commit();
            tv.setText("Medicines");
        }

        //FragmentManager myFragmentManager = getSupportFragmentManager();
        //mapFragment = (SupportMapFragment) myFragmentManager.findFragmentById(R.id.map);
        firebaseFirestore=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();




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
    public void OpenChatFragment(View view)
    {
        startActivity(new Intent(getApplicationContext(), ChatFragActivity.class));
        finish();
    }

   /* @Override
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
                        if (documentSnapshot.getString("email")!=null)
                        if(documentSnapshot.getString("email").equals(mAuth.getCurrentUser().getEmail()))
                        {
                            double longi;
                            double lati;
                            Pharmacy pharmacy=  documentSnapshot.toObject(Pharmacy.class);

                        //    Toast.makeText(MainActivity.this, String.valueOf(lati), Toast.LENGTH_SHORT).show();
                            LatLng location=new LatLng(pharmacy.getLocation().getLatitude(),
                                    pharmacy.getLocation().getLongitude());
                            mMap.addMarker(new MarkerOptions().position(location).title(getCompleteAddress(pharmacy.getLocation().getLatitude()
                                    ,pharmacy.getLocation().getLongitude())));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,14F));

                        }
                    }
                }
            }
        });


    }*/

   /* public void showMyLocation(View view) {
        mapFragment.getMapAsync(this);

    }*/
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = new Search();
                    switch (item.getItemId()) {
                        case R.id.page_1:
                            tv.setText("Medicines");
                            selectedFragment = new Search();

                            break;
                        case R.id.page_2:
                            tv.setText("Chats");
                            selectedFragment = new ChatListFragment();
                            break;
                        case R.id.page_3:
                            tv.setText("Cart");
                            selectedFragment = new CartFragment();
                            break;
                        case R.id.page_4:
                            tv.setText("More");
                            selectedFragment = new moreFragment();
                            break;
                        case R.id.page_5:
                            tv.setText("Pharmacies");
                            selectedFragment = new Search();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };
}
