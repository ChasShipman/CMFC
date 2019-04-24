package com.warbs.cmfclogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends AppCompatActivity {
    private TextView name, instrument, experience;
     private CircleImageView  profile;

    private DatabaseReference profileName;
    private FirebaseAuth auth;

    private String currentUserID;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    //dont need to open a new activity here
                    Intent intentMain = new Intent(UserActivity.this, HomeActivity.class);
                    startActivity(intentMain);
                    return true;
                case R.id.navigation_create:
                    //mTextMessage.setText(R.string.title_create);
                     Intent intentCreate = new Intent(UserActivity.this, CreateActivity.class);
                     startActivity(intentCreate);
                    return true;
                case R.id.navigation_find:
                    //mTextMessage.setText(R.string.title_find);
                    Intent intentRecycler = new Intent(UserActivity.this, RecyclerActivity.class);
                    startActivity(intentRecycler);
                    return true;
                case R.id.navigation_user:
                    //  mTextMessage.setText(R.string.title_user);
                   // Intent intentUser = new Intent(CreateActivity.this, UserActivity.class);
                   // startActivity(intentUser);
                    return true;
                case R.id.navigation_logout:
                    Intent intentLog = new Intent(UserActivity.this, MainActivity.class)
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
        setContentView(R.layout.user_activity);
        BottomNavigationView navigation = /*(BottomNavigationView)*/ findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        auth = FirebaseAuth.getInstance();
        currentUserID = auth.getCurrentUser().getUid();
        profileName = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);
        name = (TextView) findViewById(R.id.theName);
        instrument = (TextView) findViewById(R.id.instrument);
        experience = (TextView) findViewById(R.id.experience);
      //  profile = (CircleImageView) findViewById(R.id.profile);

        profileName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    String image = dataSnapshot.child("profileimage").getValue().toString();
                    String myName = dataSnapshot.child("Name").getValue().toString();
                    String myInstrument = dataSnapshot.child("Instrument").getValue().toString();
                    String myExperience = dataSnapshot.child("Experience").getValue().toString();
                  Picasso.with(UserActivity.this).load(image).placeholder(R.drawable.bruno).into(profile);
                    name.setText(myName);
                    instrument.setText(myInstrument);
                    experience.setText("Experience: " + myExperience);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

