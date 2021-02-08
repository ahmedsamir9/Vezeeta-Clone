package com.example.vezetaaclone.UI.Fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vezetaaclone.Firestore_objs.Pharmacy;
import com.example.vezetaaclone.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Locale;

import static com.example.vezetaaclone.RegisterActivity.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends DialogFragment implements OnMapReadyCallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GoogleMap mMap;
    private FirebaseFirestore fstore;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
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
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fstore = FirebaseFirestore.getInstance();
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        fstore.collection("PharmacyUsers").document(getArguments().getString("ID")).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Pharmacy currphar = document.toObject(Pharmacy.class);
                        double longi;
                        double lati;
                        longi=  currphar.getLocation().getLongitude();
                        lati=  currphar.getLocation().getLatitude();
                        LatLng location=new LatLng(lati,longi);
                        mMap.addMarker(new MarkerOptions().position(location).title(getCompleteAddress(lati,longi)));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,14F));


                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }
    private String getCompleteAddress(double longtiude,double latitude)
    {
        String address="";
        Geocoder geocoder=new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addressList= geocoder.getFromLocation(latitude,longtiude,1);
            if(address!=null)
            {
                Address returnAddress=addressList.get(0);
                StringBuilder stringBuilder=new StringBuilder("");
                for(int i=0;i<=returnAddress.getMaxAddressLineIndex();i++)
                {
                    stringBuilder.append(returnAddress.getAddressLine(i)).append("\n");
                }
                address=stringBuilder.toString();
            }
            else
            {
                Toast.makeText(getActivity(), "address Not Found", Toast.LENGTH_SHORT).show();
            }

        }
        catch (Exception e)
        {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return address;

    }
}