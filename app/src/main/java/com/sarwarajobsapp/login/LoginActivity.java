package com.sarwarajobsapp.login;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.app.preferences.SavePreferences;
import com.google.android.material.textfield.TextInputLayout;
import com.sarwarajobsapp.R;
import com.sarwarajobsapp.base.BaseActivity;
import com.sarwarajobsapp.communication.CallBack;
import com.sarwarajobsapp.communication.ServerHandler;
import com.sarwarajobsapp.dashboard.MainActivity;
import com.sarwarajobsapp.utility.AppConstants;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    TextView verify_btn;

    public static Intent getIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void setUp() {
        init();
    }

    private void init() {
        verify_btn=(TextView) findViewById(R.id.verify_btn);
        verify_btn.setOnClickListener(this);
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void onClick(View v) {
        {
            if (verify_btn == v) {
                TextInputLayout textinputUsername = findViewById(R.id.textinputUsername);
                TextInputLayout textPassword = findViewById(R.id.textPassword);

                if (textinputUsername.getEditText().getText().toString().length() <= 0) {
                    showErrorDialog("Enter Username");
                    return;
                }
                if (textPassword.getEditText().getText().toString().length() <= 0) {
                    showErrorDialog("Enter Password");
                    return;
                } else {
                   login(textinputUsername.getEditText().getText().toString(), textPassword.getEditText().getText().toString());
                   // startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    //finish();
                }
            }


        }
    }


    public void login(String username,String password)
    {
        LinkedHashMap<String, String> m = new LinkedHashMap<>();
        m.put("username", username);
        m.put("password", password);


        Map<String, String> headerMap = new HashMap<>();
        System.out.println("Login===="+ AppConstants.apiUlr+"login"+m);

        new ServerHandler().sendToServer(this, AppConstants.apiUlr+"user/login", m, 0, headerMap, 20000, R.layout.loader_dialog, new CallBack() {
            @Override
            public void getRespone(String dta, ArrayList<Object> respons) {
                try {
                    System.out.println("Login===="+dta);
                    JSONObject obj = new JSONObject(dta);
                    System.out.println("obj===="+obj.toString());
                    System.out.println("obj==1=="+obj.getString("message").toString());
                    if (obj.getString("message").equalsIgnoreCase("User Found"))
                    {
                        new SavePreferences().savePreferencesData(LoginActivity.this,obj.getString("data"),AppConstants.logindata);
//
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();

                    }
                    else
                    {
                        showErrorDialog(obj.getString("message"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
