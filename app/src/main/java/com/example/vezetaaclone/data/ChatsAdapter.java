package com.example.vezetaaclone.data;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vezetaaclone.Activities.MessageActivity;
import com.example.vezetaaclone.Firestore_objs.Pharmacy;
import com.example.vezetaaclone.Firestore_objs.User;
import com.example.vezetaaclone.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter <ChatsAdapter.ChatsViewHolder> {

   private Context chatContext;
   private List<User> PharaList;

   public ChatsAdapter(Context chatContext, List<User> PharaList)
   {
      this.chatContext = chatContext;
      this.PharaList = PharaList;
   }

   public class ChatsViewHolder extends RecyclerView.ViewHolder{

      public TextView username;
      public ImageView imageView;
      public ChatsViewHolder(View view)
      {
         super(view);
         username = view.findViewById(R.id.UserName);
         imageView = view.findViewById(R.id.profile_image);
      }
   }

   @NonNull
   @Override
   public ChatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(chatContext).inflate(R.layout.useritem, parent, false);
      return new ChatsAdapter.ChatsViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull ChatsViewHolder holder, int position) {
      User pharmacy = PharaList.get(position);
      holder.username.setText(pharmacy.getName());
      if(pharmacy.getImage()!=null)
         Picasso.get().load(pharmacy.getImage()).centerCrop().fit().into(holder.imageView);
      holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent(chatContext, MessageActivity.class);
            intent.putExtra("Name", pharmacy.getName());
            intent.putExtra("id", pharmacy.getId());
            chatContext.startActivity(intent);

         }
      });
   }
   @Override
   public long getItemId(int position) {
      return position;
   }

   @Override
   public int getItemViewType(int position) {
      return position;
   }

   @Override
   public int getItemCount() {
      return PharaList.size();
   }


}
