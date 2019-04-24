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


public class Account extends AppCompatActivity implements View.OnClickListener {

    //creating the custom database name
  //  DatabaseReference users;
    //the database
    FirebaseAuth firebaseAuth;
  //  FirebaseDatabase database;
    //variables use to take in email, username and password
    EditText myEmail, myPass;
    //button to store info in database
   // String UID;
    Button buttonSignUp;
    //  private FirebaseAuth auth;
    TextView login;

  //  EditText myName;

    // private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        firebaseAuth = FirebaseAuth.getInstance();
     //   database = FirebaseDatabase.getInstance();
      //  users = database.getReference().child("Users");
        login = (TextView) findViewById(R.id.Login);
        //myName = (EditText) findViewById(R.id.myName);
        myPass = (EditText) findViewById(R.id.myPass);
        myEmail = (EditText) findViewById(R.id.myEmail);
       // myName = (EditText) findViewById(R.id.name);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });

        buttonSignUp = (Button) findViewById(R.id.signUP);

        buttonSignUp.setOnClickListener(this);


    }
    private void register()
    {
        String email = myEmail.getText().toString().trim();
        String password = myPass.getText().toString().trim();
       // String name = myName.getText().toString().trim();
       /* if(TextUtils.isEmpty(name))
        {
            Toast.makeText(getApplicationContext(), "Enter name!", Toast.LENGTH_SHORT).show();
            return;
        }*/
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(getApplicationContext(), "Enter email!", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(password.length() < 8)
        {
            Toast.makeText(getApplicationContext(), "Password must be at least 8 characters!", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                openLogin();
                                Toast.makeText(Account.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Account.this, setUp.class);
                                startActivity(intent);
                            }
                        }
                    });
        }






    }
    @Override
    public void onClick(View view)
    {
        register();
    }
    public void openLogin()
    {
        Intent intent = new Intent(Account.this, setUp.class);
        startActivity(intent);
    }

}