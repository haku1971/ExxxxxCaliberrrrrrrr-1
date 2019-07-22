package com.example.exxxxxcaliberrrrrrrr;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

public class Result extends AppCompatActivity {

    MyHelper myHelper;
    SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        myHelper = new MyHelper(this, "Player.db");
        db=myHelper.getWritableDatabase();
        String insert = "INSERT INTO Player(UserID,score) VALUES ('Na',5000)";
        db.execSQL(insert);

    }
}
