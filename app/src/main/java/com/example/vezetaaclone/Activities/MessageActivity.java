package com.example.vezetaaclone.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vezetaaclone.Firestore_objs.Chat;
import com.example.vezetaaclone.R;
import com.example.vezetaaclone.data.messagesAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    CircleImageView profile_img ;
    TextView userText;
    EditText msg;
    ImageButton sendBtn;
    FirebaseUser user;
    Intent intent;
    messagesAdapter msgsAdapter;
    List<Chat> mchat;
    RecyclerView chatView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        chatView = findViewById(R.id.recyclerMsg);
        chatView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setStackFromEnd(true);
        chatView.setLayoutManager(manager);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        userText = findViewById(R.id.UserName);
        msg = findViewById(R.id.msgcontxt);
        sendBtn = findViewById(R.id.btn_send);
        intent = getIntent();
        String PhId = intent.getStringExtra("id");
        String name = intent.getStringExtra("Name");
        user = FirebaseAuth.getInstance().getCurrentUser();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = msg.getText().toString();
                if (!(message.trim().length() <= 0))
                {
                    SendMsg(message, user.getUid(), PhId);
                    msg.setText("");
                }
                else
                    Toast.makeText(getApplicationContext(),"You can't send empty messages!",
                            Toast.LENGTH_SHORT).show();
            }
        });

        userText.setText(name);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference docRef = db.collection("chats").document("chatsFromTo").
                collection("messages");
        docRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                readMsg(user.getUid(), PhId);
            }
        });

    }
    private void SendMsg(String msg, String sender, String reciever)
    {
        String chatsFromTo = sender+reciever;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("reciever", reciever);
        hashMap.put("message", msg);
        db.collection("chats").document(chatsFromTo).
                collection("messages").add(hashMap);
    }

    private void readMsg(String currID, String userID)
    {
        mchat = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference docRef = db.collection("chats").document(currID+userID).
                collection("messages");
        docRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                mchat.clear();
                for (QueryDocumentSnapshot QS : queryDocumentSnapshots)
                {
                    Chat chat =  QS.toObject(Chat.class);
                     {
                        mchat.add(chat);
                     }
                }
                Log.i("chatList has", String.valueOf(mchat.size()));
                msgsAdapter = new messagesAdapter(MessageActivity.this, mchat);
                chatView.setAdapter(msgsAdapter);
            }
        });


    }
}