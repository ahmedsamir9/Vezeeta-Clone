package com.example.vezetaaclone;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterPharmacyActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    String userID;
    FirebaseFirestore fstore;
    private EditText Pharmacy_Name1, Pharmacy_Email1, Pharmacy_Pass, Pharmacy_Phone_1, Pharmacy_Phone1_1;
    private Button Ph_btn_register;
    private TextView Pharmacy_login;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register_pharmacy);
        Pharmacy_Name1 = findViewById(R.id.Pharmacy_Name);
        Pharmacy_Email1 = findViewById(R.id.Pharmacy_Email);
        Pharmacy_Pass = findViewById(R.id.Pharmacy_Password);
        Pharmacy_Phone_1 = findViewById(R.id.Pharmacy_phone);
        Pharmacy_Phone1_1 = findViewById(R.id.Pharmacy_phone1);
        Pharmacy_login = findViewById(R.id.Pharmacy_LoginHere);
        Ph_btn_register = findViewById(R.id.Pharmacy_btn_register);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();

        }
        Ph_btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = Pharmacy_Email1.getText().toString();
                String password = Pharmacy_Pass.getText().toString();
                String fullName = Pharmacy_Name1.getText().toString();
                String Phone = Pharmacy_Phone_1.getText().toString();
                String Phone1 = Pharmacy_Phone1_1.getText().toString();

                if (TextUtils.isEmpty(Email)) {
                    Toast.makeText(RegisterPharmacyActivity.this, "Please Enter the Email !", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterPharmacyActivity.this, "Please Enter the password !", Toast.LENGTH_SHORT).show();
                }
                if (password.length() < 6) {
                    Toast.makeText(RegisterPharmacyActivity.this, "the password must be greater than 6 character !", Toast.LENGTH_SHORT).show();
                }
                fAuth.createUserWithEmailAndPassword(Email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterPharmacyActivity.this, "Pharmacy Created", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("PharmacyUsers").document(userID);
                            Map<String, Object> PharmacyUsers = new HashMap<>();
                            PharmacyUsers.put("Pharmacy Name", fullName);
                            PharmacyUsers.put("Email", Email);
                            PharmacyUsers.put("First Phone", Phone);
                            PharmacyUsers.put("Second Phone", Phone1);
                            documentReference.set(PharmacyUsers).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: Pharmacy user profile is created for " + userID);
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(RegisterPharmacyActivity.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        Pharmacy_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

            }
        });
    }

    public void Pharmacy_Login(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

}