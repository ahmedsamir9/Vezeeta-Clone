package com.example.vezetaaclone.data;

import android.content.Context;
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

import com.example.vezetaaclone.R;
import com.example.vezetaaclone.pojo.ActiveIngredient;
import com.example.vezetaaclone.pojo.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

public class item_adapter extends RecyclerView.Adapter<item_adapter.MyHolderView> {
    ArrayList<Result> data=new ArrayList<>();
    ArrayList<String> images=new ArrayList<>();
    private Context context;
    private ConstraintLayout mykonten;
    private LinearLayout overBox;
    private TextView labeler_name,description,ingredients,type;
    Random rand = new Random();
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
            Picasso.get().load(images.get(rand.nextInt(images.size()))).into(holder.img);
            float r=1+rand.nextInt(5);
            if(r==5)r--;
            holder.ratingBar.setRating(r);
            holder.item_price.setText("Price "+String.valueOf(10+rand.nextInt(30)+"P"));
            holder.item_rating_value.setText("("+String.valueOf(r)+")");
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
        private Animation add,cancel;
        private ImageButton addbtn;
        private boolean checked=false;
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
            addbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!checked){
                        addbtn.startAnimation(add);
                        Toast.makeText(context,"Product Added To Cart",Toast.LENGTH_SHORT).show();
                        addbtn.setBackgroundResource(R.drawable.circle_red_btn_bg);
                        checked=!checked;
                    }else{
                        addbtn.startAnimation(cancel);
                        Toast.makeText(context,"Product Deleted From Cart",Toast.LENGTH_SHORT).show();
                        addbtn.setBackgroundResource(R.drawable.circle_btn_bg);
                        checked=!checked;
                    }
                }
            });
        }
    }
}
