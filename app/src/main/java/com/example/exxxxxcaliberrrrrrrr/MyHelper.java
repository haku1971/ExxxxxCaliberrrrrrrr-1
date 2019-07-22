package com.example.exxxxxcaliberrrrrrrr;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyHelper extends SQLiteOpenHelper {

    //phải gọi contructor
    public MyHelper(Context context, String databaseName){

        super(context, databaseName,null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE Player(" +
                "ID integer primary key autoincrement,"+
                "UserID String,"+
                "score float)";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
