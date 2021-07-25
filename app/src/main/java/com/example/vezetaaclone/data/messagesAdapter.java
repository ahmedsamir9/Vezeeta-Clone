package com.example.vezetaaclone.data;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vezetaaclone.Activities.MessageActivity;
import com.example.vezetaaclone.Firestore_objs.Chat;
import com.example.vezetaaclone.Firestore_objs.Pharmacy;
import com.example.vezetaaclone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class messagesAdapter extends RecyclerView.Adapter <messagesAdapter.ChatsViewHolder> {
    public  static  final int right_msg = 0;
    public  static  final int left_msg = 1;
private Context chatContext;
private List<Chat> mChat;
FirebaseUser fuser;

public messagesAdapter(Context chatContext, List<Chat> mChat)
        {
        this.chatContext = chatContext;
        this.mChat = mChat;
        }
public class ChatsViewHolder extends RecyclerView.ViewHolder{

    public TextView showmessage;
    public TextView showTime;
    public ChatsViewHolder(View view)
    {
        super(view);
        showmessage = view.findViewById(R.id.show_msg);
        showTime = view.findViewById(R.id.timeView);
    }
}

    @NonNull
    @Override
    public messagesAdapter.ChatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    if (viewType == right_msg) {
        View view = LayoutInflater.from(chatContext).inflate(R.layout.chat_item_right, parent, false);
        return new messagesAdapter.ChatsViewHolder(view);
    }
    else
    {
        View view = LayoutInflater.from(chatContext).inflate(R.layout.chat_item_left, parent, false);
        return new messagesAdapter.ChatsViewHolder(view);
    }
    }

    @Override
    public void onBindViewHolder(@NonNull messagesAdapter.ChatsViewHolder holder, int position) {
      Chat chat = mChat.get(position);
       holder.showmessage.setText(chat.getMessage());
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getSender().equals(fuser.getUid()))
            return right_msg;
        else
            return left_msg;
    }
}

