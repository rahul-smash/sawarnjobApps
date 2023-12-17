package com.sarwarajobsapp.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.sarwarajobsapp.R;
import com.sarwarajobsapp.base.BaseActivity;

public class NewPostionScreen extends BaseActivity implements View.OnClickListener {
    TextView verify_btn;

    public static Intent getIntent(Context context) {
        return new Intent(context, NewPostionScreen.class);
    }

    @Override
    protected void setUp() {
        init();
    }

    private void init() {

    }

    @Override
    protected int setLayout() {
        return R.layout.activity_new_position;
    }

    @Override
    public void onClick(View v) {

   /*     if (v == verify_btn) {


        }*/
    }
}