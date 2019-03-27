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

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Account extends AppCompatActivity implements View.OnClickListener {
    Button button;
    private FirebaseAuth mAuth;
    private EditText value;
    private EditText value2;

    private EditText value4;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        button = (Button) findViewById(R.id.button2);
        value = (EditText) findViewById(R.id.editText1);
        value2 = (EditText) findViewById(R.id.editText4);

        value4 = (EditText) findViewById(R.id.editText6);
        button.setOnClickListener(this);

       // button.setOnClickListener(new View.OnClickListener() {
      /*      @Override
            public void onClick(View v) {
                openLogin();
            }
        });*/
    }
    public void registerUser(){
        String name = value.getText().toString().trim();
        String email = value2.getText().toString().trim();

        String password = value4.getText().toString().trim();
        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();

            return;
        }
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(Account.this, MainActivity.class));
                            }
                        });
                    }
                });

    }
    public void onClick(View view)
    {
        if(view == button){
            registerUser();
        }
            }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) this.finish();

        return super.onOptionsItemSelected(item);
    }



}

