package com.example.vezetaaclone.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vezetaaclone.R;
import com.example.vezetaaclone.pojo.Result;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;


public class cart_adapter extends RecyclerView.Adapter<cart_adapter.MyHolderView>{

    private  ArrayList<Result>data;
    private LayoutInflater mInflater;
    private Context context;
    private Animation add,cancel;
    DatabaseReference ref= FirebaseDatabase.getInstance("https://vezetaaclone-default-rtdb.firebaseio.com/").getReference().child("CartList");
    DatabaseReference refP= FirebaseDatabase.getInstance("https://vezetaaclone-default-rtdb.firebaseio.com/").getReference().child("PriceList");

    public cart_adapter(Context context,ArrayList<Result>data){
        mInflater=LayoutInflater.from(context);
        this.data=data;
        this.context=context;
    }


    @NonNull
    @Override
    public MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.drug_view_item,
                parent, false);
        return new MyHolderView(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolderView holder, int position) {
        String mCurrent = data.get(position).getGenericName();
        holder.wordItemView.setText(mCurrent);
        holder.ItemPrice.setText(CartData.prices.get(data.get(position).getProductId()));
        Random rand=new Random() ;
        if(CartData.images.size()>0)
        Picasso.get().load(CartData.images.get(data.get(position).getProductId())).into(holder.img);
        //String s="Total price   "+sumPrice();
        //holder.totalPrice.setText("dggf");
        if(!CartData.checked.containsKey(data.get(position)))
            CartData.checked.put(data.get(position),false);
        if(CartData.checked.get(data.get(position))){
            holder.addbtn.setBackgroundResource(R.drawable.circle_red_btn_bg);
        }
        else{
            holder.addbtn.setBackgroundResource(R.drawable.circle_btn_bg);
        }

        holder.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CartData.checked.get(data.get(position))){
                    holder.addbtn.startAnimation(cancel);
                    // CartData.data.remove(data.get(position));
                    // notifyDataSetChanged();
                    Toast.makeText(context,"Product Deleted From Cart "+position,Toast.LENGTH_SHORT).show();
                    holder.addbtn.setBackgroundResource(R.drawable.circle_btn_bg);
                   CartData.checked.remove(data.get(position));
                    ref.child(data.get(position).getProductId()).removeValue();
                    refP.child(data.get(position).getProductId()).removeValue();
                    data.remove(position);
                    //CartData.updateData(data);
                    //CartData.data.remove(position);
                    //setData(CartData.data);
                    notifyDataSetChanged();
                }
            }
        });




    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public int sumPrice(){
        int sum=0;
        for (int i=0;i<data.size();i++){
            int c=1,ans=0;
            String s;
            if(CartData.prices.containsKey(data.get(i).getProductId()))
             s= CartData.prices.get(data.get(i).getProductId());
            else continue;
            for(int j=s.length()-1;j>=0;j--){
                if(s.charAt(j)>='0'&&s.charAt(j)<='9'){
                    ans+=c*(s.charAt(j)-'0');
                    c*=10;
                }
            }
            sum+=ans;
        }
        return sum;
    }

    public void setData(ArrayList<Result>a){
        this.data=a;
        notifyDataSetChanged();
    }
    public class MyHolderView extends RecyclerView.ViewHolder {
        public final TextView wordItemView,ItemPrice,totalPrice;
        public final ImageView img;
        public final ImageButton addbtn;
        final cart_adapter mAdapter;
        public MyHolderView(@NonNull View itemView, cart_adapter adapter) {
            super(itemView);
            this.wordItemView=itemView.findViewById(R.id.name_item_search);
            this.img=itemView.findViewById(R.id.img_item_search);
            this.addbtn=itemView.findViewById(R.id.addbtn);
            this.mAdapter=adapter;
            this.ItemPrice=itemView.findViewById(R.id.item_price);
            this.totalPrice=itemView.findViewById(R.id.textView2);
            add= AnimationUtils.loadAnimation(context, R.anim.rotate_open_anim);
            cancel= AnimationUtils.loadAnimation(context, R.anim.rotate_close_anim);
        }
    }
}
