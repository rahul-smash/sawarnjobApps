package com.sarwarajobsapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sarwarajobsapp.R;
import com.sarwarajobsapp.base.BaseActivity;

public class ThanksActivity extends BaseActivity implements View.OnClickListener {
    TextView verify_btn;

    public static Intent getIntent(Context context) {
        return new Intent(context, ThanksActivity.class);
    }

    @Override
    protected void setUp() {
        init();
    }

    private void init() {

    }

    @Override
    protected int setLayout() {
        return R.layout.activity_thanks;
    }

    @Override
    public void onClick(View v) {


    }
}