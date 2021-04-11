package com.example.vezetaaclone.UI.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vezetaaclone.Firestore_objs.Pharmacy;
import com.example.vezetaaclone.Firestore_objs.User;
import com.example.vezetaaclone.R;
import com.example.vezetaaclone.data.ChatsAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        userList = new ArrayList<>();
        noMessages = view.findViewById(R.id.noMsgsText);
        noMessagesPic = view.findViewById(R.id.noMsgsPic);

        readUsers();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void getUserByID(String id) {
        //deciding collection
        String collection = "PharmacyUsers";
        sharedPref = getActivity().getSharedPreferences("type", 0);
        String type = sharedPref.getString("type", "DEFAULT");
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
                    Log.d("TAG", "get failed with ", task.getException());
                }
                if (userList.size() == 0)
                {
                    noMessages.setVisibility(View.VISIBLE);
                    noMessagesPic.setVisibility(View.VISIBLE);
                }
                else {
                    noMessages.setVisibility(View.INVISIBLE);
                    noMessagesPic.setVisibility(View.INVISIBLE);
                }

                ChatsAdapter chatsAdapter = new ChatsAdapter(getContext(), userList);
                recyclerView.setAdapter(chatsAdapter);
            }
        });

    }

    void readUsers() {


        //deciding collection
        String collection = "PharmacyUsers";
        sharedPref = getActivity().getSharedPreferences("type", 0);
        String type = sharedPref.getString("type", "DEFAULT");
        if (type.equals("pharmacy"))
            collection = "users";


        //filtering users
        FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //tasks
        Task checkSender = db.collection("chats").whereEqualTo("sender", fuser.getUid()).get();
        Task checkReceiver = db.collection("chats").whereEqualTo("receiver", fuser.getUid()).get();
        Task<List<QuerySnapshot>> checkAll = Tasks.whenAllSuccess(checkSender, checkReceiver);

        checkAll.addOnSuccessListener(new OnSuccessListener<List<QuerySnapshot>>() {
            @Override
            public void onSuccess(List<QuerySnapshot> querySnapshots) {

                userList.clear();

                for (QuerySnapshot value : querySnapshots) {
                    for (QueryDocumentSnapshot doc : value) {
                        if (fuser.getUid().equals(doc.getString("sender")))
                            getUserByID(doc.getString("receiver"));
                        else
                            getUserByID(doc.getString("sender"));

                    }
                }


            }
        });


    }
}

