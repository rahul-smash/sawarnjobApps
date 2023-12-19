package com.sarwarajobsapp.splash;


import android.content.Intent;
import android.os.Handler;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.preferences.SavePreferences;
import com.sarwarajobsapp.R;
import com.sarwarajobsapp.base.BaseActivity;
import com.sarwarajobsapp.dashboard.MainActivity;
import com.sarwarajobsapp.login.LoginActivity;
import com.sarwarajobsapp.utility.AppConstants;


public class SplashActivity extends BaseActivity {

    private static final int DEFAULT_DELAY = 1000;
    private TextView  txtNext;
    private ImageView imageViewLogo;

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            startActivity(LoginActivity.getIntent(SplashActivity.this));
           finish();
        }
    };

    @Override
    protected void setUp()
    {

        setUpStatusBar();
        initViews();
        animateLogo();
        animateAppName();
        //requestPermissions();
       checkSession();
    }



    private void animateAppName() {
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 30, 0);
        translateAnimation.setDuration(500);
        translateAnimation.setFillAfter(true);
        translateAnimation.setStartOffset(200);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimation.setDuration(1000);
        translateAnimation.setStartOffset(200);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);
    }

    private void animateLogo() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.9f, 1, 0.9f, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleAnimation.setDuration(1000);
        scaleAnimation.setStartOffset(200);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        alphaAnimation.setDuration(500);
        alphaAnimation.setStartOffset(200);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);

        imageViewLogo.setAnimation(animationSet);
        imageViewLogo.animate();
    }

    private void initViews() {
        txtNext=findViewById(R.id.txtNext);
        imageViewLogo = findViewById(R.id.iv_logo);
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_splash;
    }



    private void checkSession()
    {
        String data=new SavePreferences().reterivePreference(this, AppConstants.logindata).toString();
        System.out.println("obj==1=="+data.toString());

        if(data.length()==0)
        {
            openMainScreen();
        }
        else
        {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    }

    private void openMainScreen() {
        new Handler().postDelayed(runnable, DEFAULT_DELAY);
    }



    /*private void foregroundService()
    {

        checkSession();

    }*/



  /*  private void requestPermissions() {
        try {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1001);
            }
            else
            {
                foregroundService();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/
 /*   @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        System.out.println("Request code==="+requestCode);
        if(requestCode == 1001)
         {
            for (int i = 0; i < permissions.length; i++)
              {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if (permission.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        foregroundService();
                    } else {
                        requestPermissions();
                    }
                }
            }
        }
    }
*/




}