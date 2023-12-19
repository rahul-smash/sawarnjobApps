package com.sarwarajobsapp.dashboard;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.preferences.SavePreferences;
import com.sarwarajobsapp.R;
import com.sarwarajobsapp.activity.PersonInfoActivity;
import com.sarwarajobsapp.base.BaseActivity;
import com.sarwarajobsapp.splash.SplashActivity;
import com.sarwarajobsapp.utility.AppConstants;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    public static final String TAG = "MainActivity";
    public static final String mBroadcastStringAction = "location";
    RelativeLayout relativeLayouts;
    private int drawerCloseTime = 300;
    private DrawerLayout drawerLayout;
    private ImageButton menuBtn;
    public  static TextView txtToolbartext;
    public  static ImageView imgICBack;
    private LinearLayout llPersonalInfo,linearHome_btn,linearAttendance,linearTaskList,linearMAnageOrder,linearMyPoints,linearSamriddhiDashboard,linearNotification,linearChangePasswords,llLogout,linearPrivacyPolicy,linearChhoseanystartWork;
    TextView btnDatWorkPla, btn_samriddhiDashboards,home_btn,btn_attendance,btn_taskList,btn_manageOrder,btnMyPoints,btnNotification,btnChangePassword,btnMyOrders,btnLogout,btnPrivacyPolicy,btnChhossestartAnyWork;
    private boolean doubleBackToExitPressedOnce;

    public static Intent getIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        initListners();
        setupHomeFragment();

        //Intent intent = new Intent(this, MyService.class);
        //startService(intent);

        //registerReceiver(receiver,new IntentFilter(mBroadcastStringAction));


    }

    private void initView() {
        txtToolbartext =findViewById(R.id.txtToolbartext);
        imgICBack=(ImageView)findViewById(R.id.imgICBack);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        menuBtn = (ImageButton) findViewById(R.id.menu_btn);
        llPersonalInfo=(LinearLayout) findViewById(R.id.llPersonalInfo);



    }

    @Override
    protected void setUp() {

    }

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }


    private void initListners() {
        menuBtn.setOnClickListener(this);
        llPersonalInfo.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.menu_btn:
//                it will check drawer state
                checkDrawerState();
                break;
            case R.id.llPersonalInfo:
//                it will check drawer state
                checkDrawerState();
//                this function will inflate the My payment screen
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setupHomeFragment();
                    }
                }, drawerCloseTime);

                break;

    }


    }


    private void checkDrawerState() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            closeDrawer();
        } else {
            openDrawer();
        }
    }

    private void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    private void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }



    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void showFragment(Fragment fragment, String tag) {


        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, tag);
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();
    }
    public void setupHomeFragment() {
        txtToolbartext.setText("Personal Information");
        imgICBack.setVisibility(View.GONE);
        showFragment(PersonInfoActivity.newInstance(this), PersonInfoActivity.TAG);


    }
    private void setupAttendanceFragment() {




        /*Intent in =new Intent(getApplicationContext(), AttendanceActivity.class);
        startActivity(in);*/

    }
    private void setupTaskListFragment() {

    }
    private void setUPSamriddhiDashboard() {


    }


    private void logoutDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure?");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new SavePreferences().savePreferencesData(MainActivity.this, "", AppConstants.logindata);
                startActivity(new Intent(MainActivity.this, SplashActivity.class));
                finishAffinity();

            }
        });
        builder.show();
    }

     Dialog dialog;





}

