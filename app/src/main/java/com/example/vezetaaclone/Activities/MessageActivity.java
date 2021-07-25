package com.example.vezetaaclone.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    ImageView profile_img;
    TextView userText;
    EditText msg;
    ImageButton sendBtn;
    FirebaseUser user;
    Intent intent;
    messagesAdapter msgsAdapter;
    List<Chat> mchat;
    RecyclerView chatView;
    SharedPreferences sharedPref;
    String reader;
    Calendar calendar ;
    ListenerRegistration registration = null;
    String lastMessage;
    TextView noMsgs;


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
        profile_img = findViewById(R.id.profile_image);
        noMsgs = findViewById(R.id.noChat);
        msg = findViewById(R.id.msgcontxt);
        sendBtn = findViewById(R.id.btn_send);
        intent = getIntent();
        String ClickedUserID = intent.getStringExtra("id");
        String name = intent.getStringExtra("Name");
        user = FirebaseAuth.getInstance().getCurrentUser();


        //generating chat ID
        String chatsFromTo = ClickedUserID + user.getUid(); ;
        String type = getType();

        if (type.equals("user"))
            chatsFromTo = user.getUid() + ClickedUserID;


        String finalChatsFromTo = chatsFromTo;
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = msg.getText().toString();
                if (!(message.trim().length() <= 0))
                {
                    SendMsg(message, user.getUid(), ClickedUserID, finalChatsFromTo, calendar);

                    msg.setText("");
                }
                else
                    Toast.makeText(getApplicationContext(),"You can't send empty messages!",
                            Toast.LENGTH_SHORT).show();
            }
        });


        String picPath = intent.getStringExtra("image");
        Picasso.get().load(picPath).centerCrop().fit().into(profile_img);
        userText.setText(name);


        readMsg(chatsFromTo);


    }

    @Override
    protected void onStop() {
        super.onStop();
        if(registration!=null)
        {
            registration.remove();
        }
    }

    private void SendMsg(String msg, String sender, String receiver, String chatsFromTo, Calendar calendar)
    {
        String lastMessage = msg;
        calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        Date LastDate = date;
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        HashMap<String, Object> XtoY = new HashMap<>();
        XtoY.put("sender", sender);
        XtoY.put("receiver", receiver);
        XtoY.put("lastMessage", lastMessage);
        XtoY.put("lastDate", LastDate);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", msg);
        hashMap.put("time", date);

        db.collection("chats").document(chatsFromTo).
                collection("messages").add(hashMap);
        db.collection("chats").document(chatsFromTo)
                .set(XtoY, SetOptions.merge());

    }

    private void readMsg(String location)
    {
        mchat = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query docRef = db.collection("chats").document(location).
                collection("messages").orderBy("time", Query.Direction.ASCENDING);
         registration = docRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d("PharamaciesFrag", "Error:" + e.getMessage());
                } else {
                    mchat.clear();
                    for (QueryDocumentSnapshot QS : queryDocumentSnapshots) {
                        Chat chat = QS.toObject(Chat.class);
                        {
                            mchat.add(chat);

                        }
                    }
                    if (mchat.size() != 0)  noMsgs.setVisibility(View.INVISIBLE);


                    Log.i("chatList has", String.valueOf(mchat.size()));
                    msgsAdapter = new messagesAdapter(MessageActivity.this, mchat);
                    chatView.setAdapter(msgsAdapter);
                }
            }
        });
    }
    private String getType(){
        sharedPref = getSharedPreferences("type", 0);
        String type = sharedPref.getString("type", "DEFAULT");
        return type;
    }
}