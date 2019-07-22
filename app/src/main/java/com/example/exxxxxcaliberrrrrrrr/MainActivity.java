package com.example.exxxxxcaliberrrrrrrr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
    public static Intent myIntent;
    boolean isRunninggame = true;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        myIntent = new Intent(getApplicationContext(), Result.class);
//        startActivity(myIntent);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        GameSurface gameSurface = new GameSurface(this);
        this.setContentView(gameSurface);
//        this.mContext=context;/
    }

    public static void callMe(String valuePassed) {
        Log.d("DEBUG","MainActivity was passed ["+ valuePassed +"]");
    }

//    public void ChangeActivity(Context context){
//        Intent myIntent = new Intent(context, Result.class);
//        startActivity(myIntent);
//    }
}
