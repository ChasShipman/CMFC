package com.warbs.cmfclogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.DefaultItemAnimator;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.warbs.cmfclogin.Model.User;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Composition> compList = new ArrayList<>();
    private int[] images = {R.drawable.lennon, R.drawable.lusth, R.drawable.bruno, R.drawable.havana, R.drawable.high};
    private RecyclerAdapter adapter;
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
                case R.id.navigation_logout:
                    Intent intentLog = new Intent(HomeActivity.this, MainActivity.class)
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navigation = /*(BottomNavigationView)*/ findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        recyclerView = findViewById(R.id.rvComp);
        layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(images);
        recyclerView.setAdapter(adapter);
        prepareMovieData();
            //private TextView mTextMessage;
        //zmTextMessage = (TextView) findViewById(R.id.message);

    }
        private void prepareMovieData()
    {
            Composition comp = new Composition("Crippled Inside", "04-03-19");//, "04-01-19");
            compList.add(comp);

            Composition comp1 = new Composition("CS 403 Inspiration Song", "04-03-19");//, "04-01-19");
            compList.add(comp1);

            Composition comp2 = new Composition("24k Magic", "04-03-19");//, "04-01-19");
            compList.add(comp2);

            Composition comp3 = new Composition("Havana", "04-03-19");//, "04-01-19");
            compList.add(comp3);

            Composition comp0 = new Composition("High Hopes", "04-03-19");//, "04-01-19");
            compList.add(comp0);
        }

}
