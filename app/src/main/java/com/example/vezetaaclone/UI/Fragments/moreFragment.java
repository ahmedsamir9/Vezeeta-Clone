package com.example.vezetaaclone.UI.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vezetaaclone.Activities.LoginActivity;
import com.example.vezetaaclone.Activities.MainActivity;
import com.example.vezetaaclone.R;
import com.example.vezetaaclone.viewmodel.LoginRegisterViewModel;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link moreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class moreFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private LoginRegisterViewModel loginRegisterViewModel;
    private ConstraintLayout logout;
    SharedPreferences sharedPref ;
    SharedPreferences.Editor editor;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public moreFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment moreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static moreFragment newInstance(String param1, String param2) {
        moreFragment fragment = new moreFragment();
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
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(this);
        sharedPref = getActivity().getSharedPreferences("type",0);
        editor = sharedPref.edit();
        loginRegisterViewModel = ViewModelProviders.of(this).get(LoginRegisterViewModel.class);

        return view;
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.logout)
        {
            editor.clear();
            editor.apply();
            loginRegisterViewModel.logout();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        }
    }
}