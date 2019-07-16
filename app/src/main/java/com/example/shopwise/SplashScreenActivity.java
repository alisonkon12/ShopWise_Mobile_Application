package com.example.shopwise;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPALSH_TIME = 3000;// 5 Seconds
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent intent = new Intent(SplashScreenActivity.this,
                        MainActivity.class);
                startActivity(intent);
                SplashScreenActivity.this.finish();

            }
        }, SPALSH_TIME);
    }

    @Override
    public void onBackPressed() {
        SplashScreenActivity.this.finish();
        super.onBackPressed();


    }

}
