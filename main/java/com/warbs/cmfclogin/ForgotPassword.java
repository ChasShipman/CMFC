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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    private Button link;
    private EditText reset;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        auth = FirebaseAuth.getInstance();
        link = (Button) findViewById(R.id.button3);
        reset = (EditText) findViewById(R.id.editText3);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = reset.getText().toString();
                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(ForgotPassword.this, "Enter EmaiL!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(ForgotPassword.this, "Please check your email to reset password", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ForgotPassword.this, MainActivity.class));
                            }
                            else
                            {
                                String message = task.getException().getMessage();
                                Toast.makeText(ForgotPassword.this,"ERROR" +message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
    public void openForgotPassword()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) this.finish();

        return super.onOptionsItemSelected(item);
    }
}
