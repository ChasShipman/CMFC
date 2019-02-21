package com.example.cmfcdatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private final String DB = "CMFCDB";

    public void insertUserIntoDB(String name, String username, String email, String password)
    {
        if (username.equals(null)) return;
        if (password.equals(null)) return;
        if (email.equals(null)) return;

        SQLiteDatabase sqlDB = openOrCreateDatabase(DB,MODE_PRIVATE,null);
        //Cursor resultSet = sqlDB.rawQuery("insert into Users values(" + "\'" +name+ "\'" +username+ "\'" +email+ "\'" +password+ "\'" +");",null);
        ContentValues con = new ContentValues();

        long ins = insert("Users",null,);
    }

    public boolean verifyLogin(String username, String password, String email)
    {
        SQLiteDatabase sqlDB = openOrCreateDatabase(DB,MODE_PRIVATE,null);

        if (username.equals(null)) return false;

        if (!password.equals(null)) {
            Cursor resultSet = sqlDB.rawQuery("Select * from Users where User.username = " + username + " and Users.password = " + password, null);
            if (resultSet.getCount() > 0) return true;
            return false;
        }
        else if (!email.equals(null)) {
            Cursor resultSet = sqlDB.rawQuery("Select * from Users where User.username = " + username + " and Users.email = " + email, null);
            if (resultSet.getCount() > 0) return true;
            return false;
        }
        else return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
