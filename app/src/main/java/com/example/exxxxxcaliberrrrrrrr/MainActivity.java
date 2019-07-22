package com.example.exxxxxcaliberrrrrrrr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
//
//        Intent myIntent = new Intent(this, Result.class);
//        startActivity(myIntent);

    }

    public static void callMe(String valuePassed) {
        Log.d("DEBUG","MainActivity was passed ["+ valuePassed +"]");
    }


    public void start(View view){
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        GameSurface gameSurface = new GameSurface(this);
        this.setContentView(gameSurface);
    }

}
