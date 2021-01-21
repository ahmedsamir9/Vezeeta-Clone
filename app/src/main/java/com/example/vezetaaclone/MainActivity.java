package com.example.vezetaaclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
    public void Search(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity2.class));
        finish();
    }

    public void OpenChatFragment(View view)
    {
        startActivity(new Intent(getApplicationContext(), ChatFragActivity.class));
        finish();
    }
}