package com.example.vezetaaclone.UI.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vezetaaclone.Activities.MainActivity;
import com.example.vezetaaclone.Activities.Pharmacyactivity;
import com.example.vezetaaclone.data.Drugs;
import com.example.vezetaaclone.Firestore_objs.Pharmacy;
import com.example.vezetaaclone.R;
import com.example.vezetaaclone.data.CardDrugPharmacyAdpter;
import com.example.vezetaaclone.viewmodel.LoginRegisterViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private TextView txtName,txtPhone1,txtPhone2;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private Button btnShowLocation,btnChangeImage;
    private CardDrugPharmacyAdpter cardDrugPharmacyAdpter;
    private ArrayList<Drugs>drugsList;
    Pharmacy pharmacy;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        txtName = view.findViewById(R.id.txt_pharmacy_name);
        txtPhone1 = view.findViewById(R.id.txt_pharmacy_phone1);
        txtPhone2 = view.findViewById(R.id.txt_pharmacy_phone2);
        imageView= view.findViewById(R.id.img_view_parmacy_profile);
        recyclerView=view.findViewById(R.id.recycle_view_parmacy_profile);
        btnShowLocation=view.findViewById(R.id.btn_location_phamacy_profile);
        btnChangeImage=view.findViewById(R.id.btn_change_imge_pharmacy_profile);

        //FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
        FirebaseFirestore fstore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DocumentReference docRef = fstore.collection("PharmacyUsers")
                .document(firebaseAuth.getCurrentUser().getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                //pharmacy=documentSnapshot.toObject(Pharmacy.class);
                //txtName.setText(pharmacy.getName());
                // txtPhone1.setText(pharmacy.getPhone());
                // txtPhone2.setText(pharmacy.getSecondPhone());
                txtName.setText(documentSnapshot.getString("name"));
                txtPhone1.setText(documentSnapshot.getString("phone"));
                txtPhone2.setText(documentSnapshot.getString("secondPhone"));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pharmacy=new Pharmacy();
                Toast.makeText(getContext(),"Error!!!",Toast.LENGTH_LONG).show();
            }
        });

        /// getDrugsList
        drugsList = new ArrayList<>();
        cardDrugPharmacyAdpter= new CardDrugPharmacyAdpter(drugsList,getContext());
        CollectionReference collRef = fstore.collection("PharmacyUsers");
        collRef.document(firebaseAuth.getCurrentUser().
                getUid()).collection("Drugs").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                        {
                            Drugs drug=documentSnapshot.toObject(Drugs.class);
                            drugsList.add(drug);
                        }
                        cardDrugPharmacyAdpter.setlist(drugsList);
                    }
                });


        recyclerView.setAdapter(cardDrugPharmacyAdpter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;

            /*loginRegisterViewModel = ViewModelProviders.of(this).get(LoginRegisterViewModel.class);
        loginRegisterViewModel.getUserLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    txtName.setText(firebaseUser.getDisplayName());
                    txtPhone1.setText(firebaseUser.getPhoneNumber());
                    txtPhone1.setText(firebaseUser.getPhoneNumber();
                }
            }
        });*/
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        ///
        btnChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickImagepharmacy();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100)
        {
            Uri uri=data.getData();
            imageView.setImageURI(uri);
        }
    }
    public void PickImagepharmacy()
    {
        Intent intent=new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        getActivity().startActivityForResult(intent,100);
    }

}