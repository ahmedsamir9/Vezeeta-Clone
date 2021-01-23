package com.example.vezetaaclone.UI.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import com.example.vezetaaclone.Firestore_objs.Pharmacy;
import com.example.vezetaaclone.Firestore_objs.User;
import com.example.vezetaaclone.R;
import com.example.vezetaaclone.data.ChatsAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ChatListFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<User> pharmacies;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat,container, false);
        recyclerView = view.findViewById(R.id.chatsReView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        pharmacies = new ArrayList<>();

        readPhara();
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
     void readPhara()
    {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("users");
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pharmacies.clear();
                for (DataSnapshot ds : snapshot.getChildren())
                {
                    User u = ds.getValue(User.class);
                    pharmacies.add(u);
                }
                Log.i("user List has:",String.valueOf(pharmacies.size()) );
                ChatsAdapter chatsAdapter = new ChatsAdapter(getContext(), pharmacies);
                recyclerView.setAdapter(chatsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " );
            }
        });


    }
}
