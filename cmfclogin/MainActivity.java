package com.warbs.cmfclogin;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.Query;
import com.google.firebase.database.ChildEventListener;
import com.warbs.cmfclogin.Model.User;


public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference users;
    EditText user, password;
    CheckBox chk;
    TextView create;
    Button button;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");
        // auth = FirebaseAuth.getInstance();

        user = (EditText) findViewById(R.id.editText2);
        password = (EditText) findViewById(R.id.pass);

        TextView link = (TextView) findViewById(R.id.link);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openForgotPassword();
            }
        });

        create = (TextView) findViewById(R.id.account);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateAccount();
            }
        });
        chk = (CheckBox)findViewById(R.id.chk);
        chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                else
                {
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        button = (Button) findViewById(R.id.login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(user.getText().toString(),
                        password.getText().toString());

            }
        });


    }

    public void openForgotPassword()
    {
        Intent intent = new Intent(this, ForgotPassword.class);
        startActivity(intent);
    }
    public void openCreateAccount()
    {
        Intent intent = new Intent(this, Account.class);
        startActivity(intent);
    }

    private void signIn(final String username, final String password)
    {
        if(TextUtils.isEmpty(username))
        {
            Toast.makeText(getApplicationContext(), "Enter username!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(username).exists())
                {
                    if(!username.isEmpty())
                    {
                        User login = dataSnapshot.child(username).getValue(User.class);
                        if(login.getPassword().equals(password))
                        {
                            Toast.makeText(MainActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                            Intent s = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(s);
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Password is wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Username is Not Registered", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
