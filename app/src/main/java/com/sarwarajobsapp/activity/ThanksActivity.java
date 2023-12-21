package com.sarwarajobsapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sarwarajobsapp.R;
import com.sarwarajobsapp.base.BaseActivity;
import com.sarwarajobsapp.login.LoginActivity;

public class ThanksActivity extends BaseActivity implements View.OnClickListener {

    private static int thank_Screen_timout = 3000;

    @Override
    protected void setUp() {
        init();
    }

    private void init() {
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(ThanksActivity.this, LoginActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, thank_Screen_timout);

    }

    @Override
    protected int setLayout() {
        return R.layout.activity_thanks;
    }

    @Override
    public void onClick(View v) {


    }
}