package com.example.vezetaaclone.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vezetaaclone.R;
import com.example.vezetaaclone.pojo.CategoryModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class cat_adapter extends RecyclerView.Adapter<cat_adapter.MyViewHolder> {
    private List<CategoryModel> data=new ArrayList<>();;
    private Context context;

    public cat_adapter( Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public cat_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull cat_adapter.MyViewHolder holder, int position) {
        int left = 20;
        int right = 20;
        int bottom = 8;

        if (position == 0){
            left = 16;
        }else{
            left = 0;
        }

        if (position==data.size()-1){
            right = 16;
        }
        holder.Cat_Name.setText(data.get(position).getCatName());
        Picasso.get().load(data.get(position).getCatImg()).into(holder.img);
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(holder.itemView.getLayoutParams());
        marginLayoutParams.setMargins(left,0,right,bottom);
        holder.itemView.setLayoutParams(marginLayoutParams);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public void setlist(List<CategoryModel> data){
        this.data=data;
        notifyDataSetChanged();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView Cat_Name;
        private ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Cat_Name=(TextView) itemView.findViewById(R.id.Cat_Name);
            img=itemView.findViewById(R.id.Image_Cat_Item);
        }
    }
}
