package com.example.vezetaaclone.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vezetaaclone.Firestore_objs.Location;
import com.example.vezetaaclone.Firestore_objs.Pharmacy;
import com.example.vezetaaclone.R;
import com.example.vezetaaclone.pojo.ActiveIngredient;
import com.example.vezetaaclone.pojo.CategoryModel;
import com.example.vezetaaclone.pojo.Result;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import retrofit2.http.OPTIONS;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class item_adapter extends RecyclerView.Adapter<item_adapter.MyHolderView> {
    ArrayList<Result> data=new ArrayList<>();
    ArrayList<String> images=new ArrayList<>();
    private Context context;
    private ConstraintLayout mykonten;
    private LinearLayout overBox;
    float r;
    private TextView labeler_name,description,ingredients,type;
    DatabaseReference ref=FirebaseDatabase.getInstance("https://vezetaaclone-default-rtdb.firebaseio.com/").getReference().child("CartList");
    DatabaseReference refP=FirebaseDatabase.getInstance("https://vezetaaclone-default-rtdb.firebaseio.com/").getReference().child("PriceList");
    DatabaseReference refI=FirebaseDatabase.getInstance("https://vezetaaclone-default-rtdb.firebaseio.com/").getReference().child("ImageList");
    Random rand = new Random();
    private Animation add,cancel;
    public item_adapter(Context context, ConstraintLayout mykonten, LinearLayout overBox, TextView labeler_name, TextView description,
                        TextView ingredients, TextView type) {
        this.context = context;
        this.mykonten=mykonten;
        this.overBox=overBox;
        this.labeler_name=labeler_name;
        this.description=description;
        this.ingredients=ingredients;
        this.type=type;
    }

    @NonNull
    @Override
    public item_adapter.MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CartData.RetriveData();
        return new MyHolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.drug_view_item,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull item_adapter.MyHolderView holder, int position) {





        int top = 16;
        int bottom = 16;
        int left = 20;
        int right = 20;

        if (position == 0){
            top = 0;
        }else{
            top = 16;
        }

        if (position==data.size()-1){
            bottom =8;
        }
        holder.DrugName.setText(data.get(position).getGenericName());
        int curImageRand=rand.nextInt(images.size());
        Picasso.get().load(images.get(curImageRand)).into(holder.img);
        r=1+rand.nextInt(5);
        if(r==5)r--;
        holder.ratingBar.setRating(r);
        holder.item_price.setText("Price "+String.valueOf(10+rand.nextInt(30)+"P"));
        holder.item_rating_value.setText("("+String.valueOf(r)+")");

        if(CartData.checked.containsKey(data.get(position))){
            holder.addbtn.setBackgroundResource(R.drawable.circle_red_btn_bg);
        }
        else{
            holder.addbtn.setBackgroundResource(R.drawable.circle_btn_bg);
        }

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overBox.setVisibility(View.VISIBLE);
                mykonten.setVisibility(View.VISIBLE);
                mykonten.setAlpha(0);
                overBox.setAlpha(0);
                overBox.setAlpha(1);
                overBox.startAnimation(holder.fromnothing);
                mykonten.setAlpha(1);
                mykonten.startAnimation(holder.fromsmall);
                labeler_name.setText(data.get(position).getLabelerName());
                description.setText(data.get(position).getPackaging().get(0).getDescription());
                String s="\n\n";
                int m=0;
                for(ActiveIngredient i : data.get(position).getActiveIngredients()){
                    s+=" "+i.getName()+" "+i.getStrength()+" "+"\n";
                    m++;
                    if(m==5)break;
                }
                ingredients.setText(s);
                type.setText(data.get(position).getProductType());
            }
        });
        holder.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = context.getSharedPreferences("type",0);
                if(sharedPref.getString("type",null)!=null)
                {
                    if (sharedPref.getString("type", null).equals("pharmacy"))
                    {
                        ///Pharmacy pharmacy=new Pharmacy();
                        //pharmacy.setId(firebaseAuth.getCurrentUser()getUid());
                        FirebaseFirestore fstore = FirebaseFirestore.getInstance();
                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                        Drugs drug= new Drugs();
                        drug.setName(data.get(position).getBrandName());
                        drug.setImage(images.get(curImageRand));
                        drug.setRate(r);
                        drug.setPrice(holder.item_price.getText().toString());
                        CollectionReference collRef = fstore.collection("PharmacyUsers");
                        collRef.document(firebaseAuth.getCurrentUser().
                                getUid()).collection("Drugs").add(drug).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {                             Toast.makeText(context,"Product Added To Cart",Toast.LENGTH_SHORT).show();
                                Toast.makeText(context,"Product Added To Pharmacy Profile",Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context," Error Can't  Add Product To Pharmacy Profile",Toast.LENGTH_SHORT).show();
                            }
                        });
                        /*docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                pharmacy.setId(firebaseAuth.getCurrentUser().
                                        getUid());
                            }
                        });*/




                    }
                    else if (sharedPref.getString("type", null).equals("user"))
                    {
                        if(!CartData.checked.containsKey(data.get(position)))
                            CartData.checked.put(data.get(position),false);
                        if(!CartData.checked.get(data.get(position))){
                            holder.addbtn.startAnimation(add);
                            CartData.data.add(data.get(position));
                            (ref.child(data.get(position).getProductId())).push().setValue(data.get(position));
                            (refP.child(data.get(position).getProductId())).push().setValue(holder.item_price.getText().toString());
                            (refI.child(data.get(position).getProductId())).push().setValue(images.get(curImageRand));
                            //CartData.images.add(images.get(position));
                            Toast.makeText(context,"Product Added To Cart",Toast.LENGTH_SHORT).show();
                            holder.addbtn.setBackgroundResource(R.drawable.circle_red_btn_bg);
                            boolean temp=!CartData.checked.get(data.get(position));
                            CartData.checked.put(data.get(position),temp);
                        }else{
                            holder.addbtn.startAnimation(cancel);
                            ref.child(data.get(position).getProductId()).removeValue();
                            refP.child(data.get(position).getProductId()).removeValue();
                            refI.child(data.get(position).getProductId()).removeValue();
                            CartData.data.remove(data.get(position));
                            Toast.makeText(context,"Product Deleted From Cart",Toast.LENGTH_SHORT).show();
                            holder.addbtn.setBackgroundResource(R.drawable.circle_btn_bg);
                            boolean temp=!CartData.checked.get(data.get(position));
                            CartData.checked.put(data.get(position),temp);
                        }
                    }
                }
            }
        });
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(holder.itemView.getLayoutParams());
        marginLayoutParams.setMargins(left,top,right,bottom);
        holder.itemView.setLayoutParams(marginLayoutParams);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public void setList(ArrayList<Result> itemList,ArrayList images){
        this.data=itemList;
        this.images=images;
        notifyDataSetChanged();
    }
    public void setimages(ArrayList<String>images){
        this.images=images;
        notifyDataSetChanged();
    }
    public class MyHolderView extends RecyclerView.ViewHolder {
        private TextView DrugName,item_price,item_rating_value;
        private ImageButton addbtn;

        private ImageView img;
        private Animation togo,fromnothing,fromsmall;
        private RatingBar ratingBar;
        public MyHolderView(@NonNull View itemView) {
            super(itemView);
            DrugName=itemView.findViewById(R.id.name_item_search);
            ratingBar=itemView.findViewById(R.id.RatingBar);
            addbtn=itemView.findViewById(R.id.addbtn);
            img=itemView.findViewById(R.id.img_item_search);
            item_price=itemView.findViewById(R.id.item_price);
            item_rating_value=itemView.findViewById(R.id.item_rating_value);
            fromnothing= AnimationUtils.loadAnimation(context,R.anim.fromnothing);
            fromsmall= AnimationUtils.loadAnimation(context,R.anim.fromsmall);
            add= AnimationUtils.loadAnimation(context,R.anim.rotate_open_anim);
            cancel= AnimationUtils.loadAnimation(context,R.anim.rotate_close_anim);

        }
    }
}
