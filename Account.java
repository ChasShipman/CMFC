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
    DatabaseReference users;
    FirebaseDatabase database;
    EditText myEmail, username, myPass;
    Button buttonSignUp;
  //  private FirebaseAuth auth;

   // private EditText myName;
   // private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);


        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");
       // auth = FirebaseAuth.getInstance();

        myEmail = (EditText) findViewById(R.id.editText4);
        username = (EditText) findViewById(R.id.editText3);
        myPass = (EditText) findViewById(R.id.editText6);
        buttonSignUp = (Button) findViewById(R.id.button2);
        //myName = (EditText) findViewById(R.id.editText1);

       // progressBar = (ProgressBar) findViewById(R.id.progressBar)
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = myEmail.getText().toString().trim();
                String password = myPass.getText().toString().trim();
                String user = username.getText().toString().trim();
                if(TextUtils.isEmpty(user))
                {
                    Toast.makeText(getApplicationContext(), "Enter username!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length() < 8)
                {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 8 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                final User user1 = new User(username.getText().toString(),
                        myEmail.getText().toString(),
                        myPass.getText().toString());
                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(user1.getUsername()).exists())
                        {
                            Toast.makeText(Account.this, "The Username already exists!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            users.child(user1.getUsername()).setValue(user1);
                            Toast.makeText(Account.this, "Success!", Toast.LENGTH_SHORT).show();
                            Intent s = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(s);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }

                });
               /* auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Account.this, new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                Toast.makeText(Account.this, "Account Registered!", Toast.LENGTH_SHORT).show();
                                if(!task.isSuccessful())
                                {
                                    Toast.makeText(Account.this, "Authentication failed." + task.getException(), Toast
                                            .LENGTH_SHORT).show();
                                }
                                else
                                {
                                    startActivity(new Intent(Account.this, MainActivity.class));
                                    finish();
                                }

                            }
                        });*/

            }

        });
    }


}

