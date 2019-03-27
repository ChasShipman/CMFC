package com.warbs.cmfclogin;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    CheckBox chk;
    EditText password;
    Button button;
    Button button2;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        TextView link = (TextView) findViewById(R.id.link);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openForgotPassword();
            }
        });
        password = (EditText)findViewById(R.id.pass);
        button = (Button) findViewById(R.id.account);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateAccount();
            }
        });
        button2 = (Button) findViewById(R.id.login);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateSheet();
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
    public void openCreateSheet()
    {
        Intent intent = new Intent(this, Create.class);
        startActivity(intent);
    }


}
