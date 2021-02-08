package com.example.vezetaaclone.Activities;

import android.app.Activity;
import android.content.Intent;
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

import com.example.vezetaaclone.Firestore_objs.Patient;
import com.example.vezetaaclone.Firestore_objs.User;
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

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    String userID;
    FirebaseFirestore fstore;
    private EditText rFullname, rEmail, rPassword, rPhone;
    private ImageButton rBtn_register;
    private TextView login;
    private LoginRegisterViewModel loginRegisterViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        rFullname = findViewById(R.id.Name);
        rEmail = findViewById(R.id.Email);
        rPassword = findViewById(R.id.Password_);
        rPhone = findViewById(R.id.phone);
        rBtn_register = findViewById(R.id.btn_register);
        login = findViewById(R.id.LoginHere);

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
        rBtn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = rEmail.getText().toString();
                String password = rPassword.getText().toString();
                String fullName = rFullname.getText().toString();
                String Phone = rPhone.getText().toString();
                Patient patient = new Patient("deafult",Email,Phone,fullName);
                loginRegisterViewModel.register(Email, password,patient);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

            }
        });
    }


}