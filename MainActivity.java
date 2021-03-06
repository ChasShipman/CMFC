package com.example.cmfc_nav;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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
                    Intent intentCreate = new Intent(MainActivity.this, CreateActivity.class);
                    startActivity(intentCreate);
                    return true;
                case R.id.navigation_find:
                    //mTextMessage.setText(R.string.title_find);
                    Intent intentRecycler = new Intent(MainActivity.this, RecyclerActivity.class);
                    startActivity(intentRecycler);
                    return true;
                case R.id.navigation_user:
                  //  mTextMessage.setText(R.string.title_user);
                    Intent intentUser = new Intent(MainActivity.this, UserActivity.class);
                    startActivity(intentUser);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView title = /*(TextView)*/ findViewById(R.id.message);
        title.setText(R.string.nav_home);
        //zmTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = /*(BottomNavigationView)*/ findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
