package com.warbs.cmfclogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

public class CreateActivity extends AppCompatActivity {

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDefaultTextEncodingName("utf-8");

        webView.loadUrl("file:///android_asset/CMFC.html");

    }*/


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    //dont need to open a new activity here
                    Intent intentMain = new Intent(CreateActivity.this, HomeActivity.class);
                    startActivity(intentMain);
                    return true;
                case R.id.navigation_create:
                    //mTextMessage.setText(R.string.title_create);
                    // Intent intentCreate = new Intent(MainActivity.this, CreateActivity.class);
                    // startActivity(intentCreate);
                    return true;
                case R.id.navigation_find:
                    //mTextMessage.setText(R.string.title_find);
                    Intent intentRecycler = new Intent(CreateActivity.this, RecyclerActivity.class);
                    startActivity(intentRecycler);
                    return true;
                case R.id.navigation_user:
                    //  mTextMessage.setText(R.string.title_user);
                    Intent intentUser = new Intent(CreateActivity.this, UserActivity.class);
                    startActivity(intentUser);
                    return true;
                case R.id.navigation_logout:
                    Intent intentLog = new Intent(CreateActivity.this, MainActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    intentLog.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intentLog.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intentLog);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_activity);
        TextView title = /*(TextView)*/ findViewById(R.id.create_act);
        title.setText(R.string.nav_create);

        BottomNavigationView navigation = /*(BottomNavigationView)*/ findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

    }
}
