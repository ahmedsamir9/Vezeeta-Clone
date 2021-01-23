package com.example.vezetaaclone.data;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vezetaaclone.Firestore_objs.Pharmacy;
import com.example.vezetaaclone.Firestore_objs.User;
import com.example.vezetaaclone.R;

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
      public ChatsViewHolder(View view)
      {
         super(view);
         username = view.findViewById(R.id.UserName);
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
   }

   @Override
   public int getItemCount() {
      return PharaList.size();
   }


}
