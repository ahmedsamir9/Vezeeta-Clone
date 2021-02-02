package com.example.vezetaaclone.UI.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vezetaaclone.R;
import com.example.vezetaaclone.data.CartData;
import com.example.vezetaaclone.data.cart_adapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private cart_adapter mAdapter;
    private TextView textView;
    private CartFragment inst=this;
    final FirebaseDatabase database = FirebaseDatabase.getInstance("https://vezetaaclone-default-rtdb.firebaseio.com/");
    DatabaseReference ref= FirebaseDatabase.getInstance("https://vezetaaclone-default-rtdb.firebaseio.com/").getReference().child("CartList");

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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
        View view =inflater.inflate(R.layout.fragment_cart, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerview);
        textView = view.findViewById(R.id.textView2);
        //CartData.data.clear();

        CartData.RetriveData();
        mAdapter = new cart_adapter(getActivity(), CartData.data);
        mAdapter.setData(CartData.data);
        mAdapter.notifyDataSetChanged();
// Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
// Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        String s = textView.getText() + String.valueOf(mAdapter.sumPrice());
        textView.setText(s);



        // Toast.makeText(this,c,Toast.LENGTH_LONG).show();

// Create an adapter and supply the data to be displayed.


        //Toast.makeText(this,"on creat !!!!!!!!!!!!!!!!!!!!",Toast.LENGTH_SHORT).show();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "Total price   " + String.valueOf(mAdapter.sumPrice());
                textView.setText(s);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter.setData(CartData.data);
    }
}