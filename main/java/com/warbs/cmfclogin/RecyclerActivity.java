package com.warbs.cmfclogin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {

    private List<Composition> compList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CompositionAdapter mAdapter;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_activity);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.rvComp);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        mAdapter = new CompositionAdapter(compList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addOnItemTouchListener(new RecyclerTouch(getApplicationContext(), recyclerView, new RecyclerTouch.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Composition comp = compList.get(position);
                Toast.makeText(getApplicationContext(), comp.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareMovieData();
        enableSwipeToDeleteAndUndo();
    }


    private void prepareMovieData() {
        Composition comp = new Composition("Crippled Inside", "04-03-19");//, "04-01-19");
        compList.add(comp);

        Composition comp1 = new Composition("Purple Rain", "04-03-19");//, "04-01-19");
        compList.add(comp1);

        Composition comp2 = new Composition("Enter Sandman", "04-03-19");//, "04-01-19");
        compList.add(comp2);

        Composition comp3 = new Composition("Fade to Black", "04-03-19");//, "04-01-19");
        compList.add(comp3);

        Composition comp0 = new Composition("Imagine", "04-03-19");//, "04-01-19");
        compList.add(comp0);

        Composition comp4 = new Composition("Yea! Alabama", "04-03-19");//, "04-01-19");
        compList.add(comp4);

        Composition comp5 = new Composition("Dixieland Delight", "04-03-19");//, "04-01-19");
        compList.add(comp5);

        Composition comp6 = new Composition("Yeet", "04-03-19");//, "04-01-19");
        compList.add(comp6);

        Composition comp7 = new Composition("NASA", "04-03-19");//, "04-01-19");
        compList.add(comp7);

        Composition comp8 = new Composition("CS403-Inspiration Song", "04-03-19");//, "04-01-19");
        compList.add(comp8);

        Composition comp9 = new Composition("CS403-Deinspirational Song", "04-03-19");//, "04-01-19");
        compList.add(comp9);

        Composition comp10 = new Composition("OOF", "04-03-19");//, "04-01-19");
        compList.add(comp10);

        Composition comp11 = new Composition("High Hopes", "04-03-19");//, "04-01-19");
        compList.add(comp11);

        Composition comp12 = new Composition("Havana", "04-03-19");//, "04-01-19");
        compList.add(comp12);

        Composition comp13 = new Composition("24K Magic", "04-03-19");//, "04-01-19");
        compList.add(comp13);

        Composition comp14 = new Composition("Star Wars Theme", "04-03-19");//, "04-01-19");
        compList.add(comp14);

        Composition comp15 = new Composition("That's What I like", "04-03-19");//, "04-01-19");
        compList.add(comp15);

        Composition comp16 = new Composition("Auburn's Trash Song", "04-03-19");//, "04-01-19");
        compList.add(comp16);

        Composition comp17 = new Composition("Baby Shark", "04-03-19");//, "04-01-19");
        compList.add(comp17);

        Composition comp18 = new Composition("Sweet Victory", "04-03-19");//, "04-01-19");
        compList.add(comp18);

        Composition comp19 = new Composition("Dream On", "04-03-19");//, "04-01-19");
        compList.add(comp19);

        Composition comp21 = new Composition("Staying Alive", "04-03-19");//, "04-01-19");
        compList.add(comp21);

        Composition comp22 = new Composition("Dancing Queen", "04-03-19");//, "04-01-19");
        compList.add(comp22);

        Composition comp23 = new Composition("We Are The Champions", "04-03-19");//, "04-01-19");
        compList.add(comp23);

        Composition comp24 = new Composition("Bohemian Rhapsody", "04-03-19");//, "04-01-19");
        compList.add(comp24);

        mAdapter.notifyDataSetChanged();
    }

    private void emptyData(){
        compList.clear();
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final Composition item = mAdapter.getData().get(position);

                mAdapter.removeItem(position);


                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mAdapter.restoreItem(item, position);
                        recyclerView.scrollToPosition(position);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}


/*
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    //dont need to open a new activity here
                    Intent intentMain = new Intent(RecyclerActivity.this, MainActivity.class);
                    startActivity(intentMain);
                    return true;
                case R.id.navigation_create:
                    //mTextMessage.setText(R.string.title_create);
                    Intent intentCreate = new Intent(RecyclerActivity.this, CreateActivity.class);
                    startActivity(intentCreate);
                    return true;
                case R.id.navigation_find:
                    //mTextMessage.setText(R.string.title_find);
                    //Intent intentRecycler = new Intent(CreateActivity.this, RecyclerActivity.class);
                    //startActivity(intentRecycler);
                    return true;
                case R.id.navigation_user:
                    //  mTextMessage.setText(R.string.title_user);
                    Intent intentUser = new Intent(RecyclerActivity.this, UserActivity.class);
                    startActivity(intentUser);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_activity);
        TextView title = (TextView) findViewById(R.id.recycler_act);
        title.setText(R.string.nav_find);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

    }*/
