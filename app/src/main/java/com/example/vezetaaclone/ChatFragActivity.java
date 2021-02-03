package com.example.vezetaaclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.vezetaaclone.UI.Fragments.ChatListFragment;

public class ChatFragActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_frag);
        OpenChatFragment(new ChatListFragment());
    }
    public void OpenChatFragment(Fragment fragment)
    {
        FragmentManager chatFragManger = getSupportFragmentManager();
        chatFragManger.beginTransaction().replace(R.id.chatContainer,fragment).commit();
    }
}