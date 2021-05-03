package com.kacamata.kacamataplusminus.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.kacamata.kacamataplusminus.R;

public class SplashActivity extends AppCompatActivity {
    TextView tvAnc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tvAnc = findViewById(R.id.tv_welcome);
        Typeface type = Typeface.SANS_SERIF;
        tvAnc.setTypeface(type);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();
                }
            }
        });
        thread.start();
    }
    }
