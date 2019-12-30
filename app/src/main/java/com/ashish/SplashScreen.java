package com.ashish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sharedPreferences=getSharedPreferences("FIRENOTEDATA", Context.MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sharedPreferences.getBoolean("LOGINSTATUS",false)){
                    Intent intent=new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);

                }else{
                    Intent intent=new Intent(SplashScreen.this,Login.class);
                    startActivity(intent);

                }
                finish();
            }
        },3000);
    }
}
