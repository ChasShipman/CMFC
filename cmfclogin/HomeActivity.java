package com.warbs.cmfclogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    //private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    //dont need to open a new activity here
                    return true;
                case R.id.navigation_create:
                    //mTextMessage.setText(R.string.title_create);
                    Intent intentCreate = new Intent(HomeActivity.this, CreateActivity.class);
                    startActivity(intentCreate);
                    return true;
                case R.id.navigation_find:
                    //mTextMessage.setText(R.string.title_find);
                    Intent intentRecycler = new Intent(HomeActivity.this, RecyclerActivity.class);
                    startActivity(intentRecycler);
                    return true;
                case R.id.navigation_user:
                  //  mTextMessage.setText(R.string.title_user);
                    Intent intentUser = new Intent(HomeActivity.this, UserActivity.class);
                    startActivity(intentUser);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        TextView title = /*(TextView)*/ findViewById(R.id.message);
        title.setText(R.string.nav_home);
        //zmTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = /*(BottomNavigationView)*/ findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
