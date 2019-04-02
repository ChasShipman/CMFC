package com.warbs.cmfclogin;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.Query;
import com.google.firebase.database.ChildEventListener;
import com.warbs.cmfclogin.Model.User;


public class Account extends AppCompatActivity {

    //creating the custom database name
    DatabaseReference users;
    //the database
    FirebaseDatabase database;
    //variables use to take in email, username and password
    EditText myEmail, myUser, myPass;
    //button to store info in database
    Button buttonSignUp;
    //  private FirebaseAuth auth;

  //  EditText myName;

    // private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        database = FirebaseDatabase.getInstance();
        users = database.getReference().child("Users");
        myUser = (EditText) findViewById(R.id.myUser);
        //myName = (EditText) findViewById(R.id.myName);
        myPass = (EditText) findViewById(R.id.myPass);
        myEmail = (EditText) findViewById(R.id.myEmail);

        buttonSignUp = (Button) findViewById(R.id.signUP);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = myEmail.getText().toString().trim();
                String password = myPass.getText().toString().trim();
               // String name = myName.getText().toString().trim();
                String username = myUser.getText().toString().trim();

                if(TextUtils.isEmpty(username))
                {
                    Toast.makeText(getApplicationContext(), "Enter username!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(getApplicationContext(), "Enter email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length() < 8)
                {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                final User user = new User(myUser.getText().toString(),
                        myPass.getText().toString(),
                        myEmail.getText().toString());
                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(user.getUsername()).exists())
                        {
                            Toast.makeText(Account.this, "Username already exists!", Toast.LENGTH_SHORT).show();
                        }

                        else
                        {
                            users.child(user.getUsername()).setValue(user);
                            Toast.makeText(Account.this, "Success", Toast.LENGTH_SHORT).show();
                            Intent s = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(s);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}