package com.example.vezetaaclone.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.vezetaaclone.R;
import com.example.vezetaaclone.UI.Fragments.CartFragment;
import com.example.vezetaaclone.UI.Fragments.ChatListFragment;
import com.example.vezetaaclone.UI.Fragments.ProfileFragment;
import com.example.vezetaaclone.UI.Fragments.Search;
import com.example.vezetaaclone.UI.Fragments.moreFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Pharmacyactivity extends AppCompatActivity {
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacyactivity);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        tv = findViewById(R.id.toolbar_title);
        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            tv.setText("Medicines");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Search()).commit();
        }
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = new Search();
                    switch (item.getItemId()) {
                        case R.id.page_1:
                            tv.setText("Medicines");
                            selectedFragment = new Search();
                            break;
                        case R.id.page_2:
                            tv.setText("Chats");
                            selectedFragment = new ChatListFragment();
                            break;
                        case R.id.page_4:
                            tv.setText("More");
                            selectedFragment = new moreFragment();
                            break;
                        case R.id.page_5:
                            tv.setText("Profile");
                            selectedFragment = new ProfileFragment();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };
}