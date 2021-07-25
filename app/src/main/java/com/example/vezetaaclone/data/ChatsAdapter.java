package com.example.vezetaaclone.data;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vezetaaclone.Activities.MessageActivity;
import com.example.vezetaaclone.Firestore_objs.User;
import com.example.vezetaaclone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
      public TextView lastMsgView;
      public TextView lastTimeView;

      public ChatsViewHolder(View view)
      {
         super(view);
         username = view.findViewById(R.id.UserName);
         imageView = view.findViewById(R.id.profile_image);
         lastMsgView = view.findViewById(R.id.lastMsg);
         lastTimeView = view.findViewById(R.id.timeView);

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
      FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();;
      holder.username.setText(pharmacy.getName());

      String chatID;
      SharedPreferences sharedPref;
      sharedPref = chatContext.getSharedPreferences("type",0);
      String type = sharedPref.getString("type", "DEFAULT");

      chatID = pharmacy.getId() + user.getUid();
      if (type.equals("user"))
         chatID = user.getUid() + pharmacy.getId();



      FirebaseFirestore db = FirebaseFirestore.getInstance();

      DocumentReference docRef = db.collection("chats").document(chatID);
      docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
         @Override
         public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
             {
               DocumentSnapshot document = documentSnapshot;
               if (document.exists()) {
                  //Log.i("CHATID", chatID);
                  //setting last message view
                  String lastMessage = document.get("lastMessage").toString();

                  holder.lastMsgView.setText(lastMessage);
                  if (lastMessage.length() > 18)
                  {
                     String newLastMessage = lastMessage.substring(0,17);
                     newLastMessage+="...";
                     holder.lastMsgView.setText(newLastMessage);

                  }


                  //setting last date view
                  Date lastMsgTime = (Date) document.get("lastDate");
                  SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
                  String dateString = formatter.format(new Date(String.valueOf(lastMsgTime)));
                  holder.lastTimeView.setText(dateString);
               } else {
                  holder.lastMsgView.setText("");
                  holder.lastTimeView.setText("");
               }

            }
         }
      });




      if(pharmacy.getImage()!=null)
         Picasso.get().load(pharmacy.getImage()).centerCrop().fit().into(holder.imageView);


      holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent(chatContext, MessageActivity.class);
            intent.putExtra("Name", pharmacy.getName());
            intent.putExtra("id", pharmacy.getId());
            intent.putExtra("image", pharmacy.getImage());

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
