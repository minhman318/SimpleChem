package com.manminh.simplechem.ui.main;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.manminh.simplechem.R;
import com.manminh.simplechem.data.XmlDataManager;
import com.manminh.simplechem.model.ElementDictionary;

public class SplashActivity extends AppCompatActivity {
    private static final int DELAY = 600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        (new Thread(new Runnable() {
            @Override
            public void run() {
                next();
            }
        })).start();

    }

    private void next() {
        (new Handler(Looper.getMainLooper())).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        }, DELAY);
    }
}
