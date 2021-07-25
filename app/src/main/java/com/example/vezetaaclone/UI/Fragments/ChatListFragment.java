package com.example.vezetaaclone.UI.Fragments;

import android.content.SharedPreferences;
import android.graphics.Path;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vezetaaclone.Firestore_objs.User;
import com.example.vezetaaclone.R;
import com.example.vezetaaclone.data.ChatsAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChatListFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<User> userList;
    SharedPreferences sharedPref;
    TextView noMessages;
    ImageView noMessagesPic;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView = view.findViewById(R.id.chatsReView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);


        userList = new ArrayList<>();
        noMessages = view.findViewById(R.id.noMsgsText);
        noMessagesPic = view.findViewById(R.id.noMsgsPic);

        readUsers();
        showOnNoneUsers();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void getUserByID(String id) {
        //deciding collection
        String collection = "PharmacyUsers";
        String type = getType();
        if (type.equals("pharmacy"))
            collection = "users";

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(collection).document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //save user
                        User u = document.toObject(User.class);
                        userList.add(u);
                        Log.i("user List has", String.valueOf(userList.size()));
                    }
                } else {
                    Log.i("TAG", "get failed with ", task.getException());
                }

                ChatsAdapter chatsAdapter = new ChatsAdapter(getContext(), userList);
                recyclerView.setAdapter(chatsAdapter);
            }
        });

    }



    void readUsers() {
        String type = getType();

        //filtering users
        FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //tasks
        Task<QuerySnapshot> wantedUsers = db.collection("chats").whereEqualTo("sender", fuser.getUid())
                //order by
                .get();

        if (type.equals("pharmacy"))
             wantedUsers = db.collection("chats").whereEqualTo("receiver", fuser.getUid())
                     //order by
                    .get();


        wantedUsers.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot querySnapshot) {
                userList.clear();
                    for (QueryDocumentSnapshot doc : querySnapshot) {
                        if  (type.equals("pharmacy"))
                            getUserByID(doc.getString("sender"));
                        else
                            getUserByID(doc.getString("receiver"));
                    }
            }
        });

    }

     void showOnNoneUsers()
    {
        if (userList.size() > 0)
        {
            noMessages.setVisibility(View.INVISIBLE);
            noMessagesPic.setVisibility(View.INVISIBLE);
        }

    }

    private String getType(){
        sharedPref = getActivity().getSharedPreferences("type", 0);
        String type = sharedPref.getString("type", "DEFAULT");
        return type;
    }
}

