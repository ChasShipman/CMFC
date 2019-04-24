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


public class MainActivity extends AppCompatActivity implements  View.OnClickListener {

    EditText email, password;
    CheckBox chk;
    TextView create;
    Button button;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null)
        {

        }
        email = (EditText) findViewById(R.id.editText2);
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
        button.setOnClickListener(this);



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

    private void login()
    {
        String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();
        if(TextUtils.isEmpty(Email))
        {
            Toast.makeText(getApplicationContext(), "Enter email!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(Password))
        {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Password.length() < 8)
        {
            Toast.makeText(getApplicationContext(), "Password must be at least 8 characters!", Toast.LENGTH_SHORT).show();
            return;
        }
        auth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            finish();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v ==button)
        {
            login();
        }
        if(v == create )
        {
            finish();
            startActivity(new Intent(this, Account.class));
        }
    }
}
