package com.example.vezetaaclone.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.vezetaaclone.R;
import com.example.vezetaaclone.viewmodel.LoginRegisterViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    private EditText lMail, lPassword;
    private Button btn_login;
    private TextView createAcc;
    private LoginRegisterViewModel loginRegisterViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        lMail = findViewById(R.id.Email_login);
        lPassword = findViewById(R.id.Password_login);
        btn_login = findViewById(R.id.btn_login);
        createAcc = findViewById(R.id.LoginHere_l);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = lMail.getText().toString();
                String password = lPassword.getText().toString();
                loginRegisterViewModel.login(Email,password);
            }
        });
        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }

    public void RegisterAsPharamcy(View view) {
        startActivity(new Intent(getApplicationContext(), RegisterPharmacyActivity.class));
    }

    public void Forget_passwordd(View view) {
        EditText RestMail = new EditText(view.getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Reset password?");
        builder.setMessage("Enter your email to receive reset link ");
        builder.setView(RestMail);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String maill = RestMail.getText().toString();
                loginRegisterViewModel.reset(maill);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}