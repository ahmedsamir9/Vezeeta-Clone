package com.example.vezetaaclone.UI.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.vezetaaclone.Firestore_objs.Pharmacy;
import com.example.vezetaaclone.Firestore_objs.User;
import com.example.vezetaaclone.R;
import com.example.vezetaaclone.data.ChatsAdapter;
import com.example.vezetaaclone.data.PharmaciesAdapter;
import com.example.vezetaaclone.pojo.Result;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PharmaciesFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class PharmaciesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    ListenerRegistration registration = null;
    private String mParam2;
    private EditText ET;
    private RecyclerView rv;
    private List<User> pharmacies;
    private PharmaciesAdapter chatsAdapter;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PharmaciesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PharmaciesFragment newInstance(String param1, String param2) {
        PharmaciesFragment fragment = new PharmaciesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PharmaciesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pharmacies,container, false);
        rv = view.findViewById(R.id.recyclerView);
        ET = view.findViewById(R.id.ET_search);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        pharmacies = new ArrayList<>();

        readPhara();
        ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(pharmacies.size()!=0)
                filter(s.toString());


            }
        });
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        if(registration!=null)
        registration.remove();
    }

    private void filter(String text){
        ArrayList<User>filted_list=new ArrayList<User>();
        for(User item:pharmacies){
            if(item.getName()!=null)
            if(item.getName().toLowerCase().contains(text.toLowerCase())){
                filted_list.add(item);
            }
        }

        chatsAdapter.setList(filted_list);
    }
    void readPhara()
    {
        String collection = "PharmacyUsers";


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(collection)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.d("PharamaciesFrag", "Error:" + e.getMessage());
                        } else {
                            pharmacies.clear();

                            for (QueryDocumentSnapshot doc : value) {

                                Pharmacy u = doc.toObject(Pharmacy.class);

                                pharmacies.add(u);

                            }

                            Log.i("user List has:", String.valueOf(pharmacies.size()));
                            chatsAdapter = new PharmaciesAdapter(getContext(), pharmacies);
                            rv.setAdapter(chatsAdapter);
                        }
                    }
                });
    }
}