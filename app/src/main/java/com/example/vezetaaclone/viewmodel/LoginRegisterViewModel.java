package com.example.vezetaaclone.viewmodel;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.AndroidViewModel;

import com.example.vezetaaclone.Activities.LoginActivity;
import com.example.vezetaaclone.Firestore_objs.Patient;
import com.example.vezetaaclone.Firestore_objs.Pharmacy;
import com.example.vezetaaclone.Repository.AuthRepository;
import com.google.firebase.auth.FirebaseUser;


public class LoginRegisterViewModel extends AndroidViewModel{
        private AuthRepository authAppRepository;
        private MutableLiveData<FirebaseUser> userLiveData;

        public LoginRegisterViewModel(@NonNull Application application) {
            super(application);
            authAppRepository = new AuthRepository(application.getApplicationContext());
            userLiveData = authAppRepository.getUserLiveData();
        }

         public void login(String email, String password) {
            authAppRepository.login(email, password);
        }


        public void register(String email, String password, Patient patient) {
            boolean success = true;
            if (TextUtils.isEmpty(patient.getEmail())) {
                success = false;
                Toast.makeText(getApplication().getApplicationContext(), "Please Enter the Email !", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(password)) {
                success = false;
                Toast.makeText(getApplication().getApplicationContext(), "Please Enter the password !", Toast.LENGTH_SHORT).show();
            }
            if (password.length() < 6) {
                success = false;
                Toast.makeText(getApplication().getApplicationContext(), "the password must be greater than 6 character !", Toast.LENGTH_SHORT).show();
            }
            if(success)
            authAppRepository.register(email, password,patient);
        }
    public void registerpharma(String email, String password, Pharmacy pharma) {
        boolean success = true;
        if (TextUtils.isEmpty(pharma.getEmail())) {
            success = false;
            Toast.makeText(getApplication().getApplicationContext(), "Please Enter the Email !", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)) {
            success = false;
            Toast.makeText(getApplication().getApplicationContext(), "Please Enter the password !", Toast.LENGTH_SHORT).show();
        }
        if (password.length() < 6) {
            success = false;
            Toast.makeText(getApplication().getApplicationContext(), "the password must be greater than 6 character !", Toast.LENGTH_SHORT).show();
        }

        if(success)
            authAppRepository.register(email, password,pharma);
    }
    public void reset(String email) {
            authAppRepository.resetpass(email);
    }

        public MutableLiveData<FirebaseUser> getUserLiveData() {
            return userLiveData;
        }

}
