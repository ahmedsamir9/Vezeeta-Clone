package com.example.vezetaaclone.UI.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vezetaaclone.R;
import com.example.vezetaaclone.data.cat_adapter;
import com.example.vezetaaclone.data.item_adapter;
import com.example.vezetaaclone.pojo.CategoryModel;
import com.example.vezetaaclone.pojo.Result;
import com.example.vezetaaclone.pojo.productmodel;
import com.example.vezetaaclone.viewmodel.CatViewModel;
import com.example.vezetaaclone.viewmodel.ImageViewModel;
import com.example.vezetaaclone.viewmodel.ProductViewModel;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Search extends Fragment {
    private RecyclerView Cat_recyclerView,items_recyclerView;
    private EditText search_et;
    private cat_adapter adapter;
    private com.example.vezetaaclone.data.item_adapter item_adapter;
    private ConstraintLayout mykonten;
    private LinearLayout overBox;
    private Animation togo;
   private ProgressBar progressBar;
    ProductViewModel productViewModel;
    ImageViewModel imageViewModel;
    CatViewModel catViewModel;
    private TextView labeler_name,description,ingredients,type;
    private RadioButton DarkRadioBtn;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    List<String> Data =new ArrayList<>();
    List<CategoryModel> proData =new ArrayList<>();
    List<Result> productData =new ArrayList<>();
    public Search() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_search, container, false);
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        imageViewModel=ViewModelProviders.of(this).get(ImageViewModel.class);
        catViewModel=ViewModelProviders.of(this).get(CatViewModel.class);
        catViewModel.get_Cat(getActivity(),imageViewModel,productViewModel);
        labeler_name=v.findViewById(R.id.tv_prodname);
        description=v.findViewById(R.id.tv_Desc);
        ingredients=v.findViewById(R.id.tv_ingredients);
        type=v.findViewById(R.id.tv_type);
        Cat_recyclerView=v.findViewById(R.id.RV_Cat);
        items_recyclerView=v.findViewById(R.id.RV_items);
        progressBar=v.findViewById(R.id.progBar);
        mykonten=v.findViewById(R.id.myKonter);
        overBox=v.findViewById(R.id.overbox);
        mykonten.setVisibility(View.GONE);
        overBox.setVisibility(View.GONE);
        togo= AnimationUtils.loadAnimation(getActivity(),R.anim.togo);
        search_et=v.findViewById(R.id.ET_search);
        search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString(), (ArrayList<Result>) productData);
            }
        });
        overBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* overBox.startAnimation(togo);
                mykonten.startAnimation(togo);*/
                mykonten.setVisibility(View.GONE);
                overBox.setVisibility(View.GONE);
            }
        });
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false);
        Cat_recyclerView.setLayoutManager(layoutManager);
        Cat_recyclerView.setFocusable(false);
        Cat_recyclerView.setNestedScrollingEnabled(false);
        adapter = new cat_adapter(getActivity());
        Cat_recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager2=new LinearLayoutManager(getActivity());
        items_recyclerView.setLayoutManager(layoutManager2);
        items_recyclerView.setFocusable(false);
        items_recyclerView.setNestedScrollingEnabled(false);
        item_adapter = new item_adapter(getActivity(),mykonten,overBox,labeler_name,description,ingredients,type);
        productViewModel.ProductMutableLiveData.observe(this, new Observer<productmodel>() {
            @Override
            public void onChanged(productmodel productmodel) {
                productData=productmodel.getResults();
                items_recyclerView.setAdapter(item_adapter);
                item_adapter.setList((ArrayList<Result>) productmodel.getResults(), (ArrayList) Data);
                adapter.setlist(proData);
                progressBar.setVisibility(View.GONE);
            }
        });
        imageViewModel.imageMutableLiveData.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                Data=strings;
            }
        });
        catViewModel.CatMutableLiveData.observe(this, new Observer<List<CategoryModel>>() {
            @Override
            public void onChanged(List<CategoryModel> categoryModels) {
                proData= categoryModels;
            }
        });
        DarkRadioBtn=v.findViewById(R.id.Darkbutton);
        if(AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES) {
            DarkRadioBtn.setChecked(true);
        }
        else{
            DarkRadioBtn.setChecked(false);
        }
        DarkRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
            }
        });
        return v;
    }
    private void filter(String text,ArrayList<Result>products){
        ArrayList<Result>filted_list=new ArrayList<Result>();
        for(Result item:products){
            if(item.getGenericName().toLowerCase().contains(text.toLowerCase())){
                filted_list.add(item);
            }
        }
        if(Data!=null&&filted_list!=null&&item_adapter!=null)
        item_adapter.setList(filted_list, (ArrayList) Data);
    }
}