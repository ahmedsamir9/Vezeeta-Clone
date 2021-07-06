package com.example.vezetaaclone.Repository;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import com.example.vezetaaclone.Activities.LoginActivity;
import com.example.vezetaaclone.Activities.MainActivity;
import com.example.vezetaaclone.Activities.Pharmacyactivity;
import com.example.vezetaaclone.Firestore_objs.Patient;
import com.example.vezetaaclone.Firestore_objs.Pharmacy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class AuthRepository {
    private Application application;
    private Context context;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore ;
    private MutableLiveData<FirebaseUser> userLiveData;
    private  MutableLiveData<String> Type;
    private MutableLiveData<Boolean> loggedOutLiveData;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public  AuthRepository(Context context) {
        this.context = context;

         fstore = FirebaseFirestore.getInstance();
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.userLiveData = new MutableLiveData<>();
        this.loggedOutLiveData = new MutableLiveData<>();
        pref = context.getSharedPreferences("type",0);
        editor = pref.edit();
        this.Type = new MutableLiveData<>();
        Type.setValue("not set");
        if (firebaseAuth.getCurrentUser() != null)
        {
            userLiveData.postValue(firebaseAuth.getCurrentUser());
            loggedOutLiveData.postValue(false);
        }
    }


    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                    {
                        puttype();
                    } else {
                        userLiveData.setValue(null);
                        Toast.makeText(context, "Login Failure: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void puttype()
    {
        DocumentReference docRef = fstore.collection("users").document(firebaseAuth.getCurrentUser().
                getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists())
                    {
                        editor.putString("type","user");
                        editor.apply();
                        userLiveData.postValue(firebaseAuth.getCurrentUser());
                    }
                    else
                    {

                        editor.putString("type","pharmacy");
                        editor.apply();
                        userLiveData.postValue(firebaseAuth.getCurrentUser());
                    }

                }
                else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    public void register(String email, String password, Patient patient) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                           puttype();
                            patient.setId(firebaseAuth.getCurrentUser().getUid());
                            DocumentReference documentReference = fstore.collection("users").
                                    document( firebaseAuth.getCurrentUser().getUid());
                            documentReference.set(patient).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user profile is created for " +
                                            firebaseAuth.getCurrentUser().getUid());

                                }
                            });
                        } else {
                            Toast.makeText(context, "Registration Failure: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                });
    }
    public void register(String email, String password, Pharmacy pharmacy)
    {

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        puttype();
                        pharmacy.setId(firebaseAuth.getCurrentUser().getUid());
                        DocumentReference documentReference = fstore.collection("PharmacyUsers").
                                document( firebaseAuth.getCurrentUser().getUid());
                        documentReference.set(pharmacy).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSuccess: Pharmacy profile is created for " +
                                        firebaseAuth.getCurrentUser().getUid());


                            }
                        });
                    } else {
                        Toast.makeText(context, "Registration Failure: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });
    }
    public void resetpass(String email) {
        firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Reset Link send to your mail ", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Reset Link is not send to your mail " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void logOut() {
        Type.setValue(null);
        firebaseAuth.signOut();
        editor.clear();
        editor.apply();
        loggedOutLiveData.postValue(true);
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }
    public MutableLiveData<String> getType() {
        return Type;
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }
}
