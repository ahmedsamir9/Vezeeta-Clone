package com.example.vezetaaclone.Activities;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.example.vezetaaclone.Firestore_objs.Location;
import com.example.vezetaaclone.Firestore_objs.Pharmacy;
import com.example.vezetaaclone.R;
import com.example.vezetaaclone.viewmodel.LoginRegisterViewModel;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RegisterPharmacyActivity extends AppCompatActivity {

    private EditText Pharmacy_Name1, Pharmacy_Email1, Pharmacy_Pass, Pharmacy_Phone_1, Pharmacy_Phone1_1;
    private ImageButton Ph_btn_register;
    private TextView Pharmacy_login;
    private LoginRegisterViewModel loginRegisterViewModel;
    private EditText get_place;
    int PLACE_PICKER_REQUEST=1;
    Intent intent;
    Activity activity;
    Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
        setContentView(R.layout.activity_register_pharmacy);
        Pharmacy_Name1 = findViewById(R.id.Pharmacy_Name);
        Pharmacy_Email1 = findViewById(R.id.Pharmacy_Email);
        Pharmacy_Pass = findViewById(R.id.Pharmacy_Password);
        Pharmacy_Phone_1 = findViewById(R.id.Pharmacy_phone);
        Pharmacy_Phone1_1 = findViewById(R.id.Pharmacy_phone1);
        Pharmacy_login = findViewById(R.id.Pharmacy_LoginHere);
        Ph_btn_register = findViewById(R.id.Pharmacy_btn_register);
        get_place=findViewById(R.id.location);
        PlacePicker.IntentBuilder builder=new PlacePicker.IntentBuilder();
        try {
            intent=builder.build(activity);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
        get_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    startActivityForResult(intent,PLACE_PICKER_REQUEST);




            }
        });
        loginRegisterViewModel = ViewModelProviders.of(this).get(LoginRegisterViewModel.class);
        loginRegisterViewModel.getUserLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        });

        Ph_btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = Pharmacy_Email1.getText().toString();
                String password = Pharmacy_Pass.getText().toString();
                String fullName = Pharmacy_Name1.getText().toString();
                String Phone = Pharmacy_Phone_1.getText().toString();
                String Phone1 = Pharmacy_Phone1_1.getText().toString();
                Pharmacy pharmacy =  new Pharmacy("deafult",Email,Phone,fullName,Phone1,new Location(get_place.getText().toString()
                ,place.getLatLng().longitude,place.getLatLng().latitude));
                loginRegisterViewModel.registerpharma(Email,password,pharmacy);

            }
        });
        Pharmacy_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PLACE_PICKER_REQUEST)
        {
            if(resultCode==RESULT_OK)
            {
                if(data!=null)
                place=PlacePicker.getPlace(this,data);
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 1);
                    String address = addresses.get(0).getAddressLine(0);
                    //String address=String.format("place: %s",place.getAddress());
                    Toast.makeText(activity, String.valueOf(place.getLatLng().latitude), Toast.LENGTH_SHORT).show();
                    get_place.setText(address);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    public void Pharmacy_Login(View view) {
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}