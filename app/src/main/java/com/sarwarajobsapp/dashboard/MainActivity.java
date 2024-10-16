package com.sarwarajobsapp.dashboard;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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
import com.sarwarajobsapp.activity.PersonInfoFragment;
<<<<<<< HEAD
=======
import com.sarwarajobsapp.activity.PrivacyPolicy;
>>>>>>> 76b77736fd1331d2a5818195084ba5ec3f313bfe
import com.sarwarajobsapp.activity.ThanksActivity;
import com.sarwarajobsapp.base.BaseActivity;
import com.sarwarajobsapp.candidateList.CandidateListActionaleActivityConvert;
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
     LinearLayout llPersonalInfo,llCandidateList,llLogout,linearTaskList,linearMAnageOrder,linearMyPoints,linearSamriddhiDashboard,linearNotification,linearChangePasswords,linearPrivacyPolicy,linearChhoseanystartWork;
<<<<<<< HEAD
    TextView txtCandidateLsit, btn_samriddhiDashboards,home_btn,btn_attendance,btn_taskList,btn_manageOrder,btnMyPoints,btnNotification,btnChangePassword,btnMyOrders,txtLogout,btnPrivacyPolicy,btnChhossestartAnyWork;
=======
    TextView txtCandidateLsit, btn_samriddhiDashboards,home_btn,btn_attendance,btn_taskList,btn_manageOrder,btnMyPoints,btnNotification,btnChangePassword,btnMyOrders,txtLogout,txtPrivacyPolicy,btnPrivacyPolicy,btnChhossestartAnyWork;
>>>>>>> 76b77736fd1331d2a5818195084ba5ec3f313bfe
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
<<<<<<< HEAD

=======
        txtPrivacyPolicy=(TextView)findViewById(R.id.txtPrivacyPolicy);
        linearPrivacyPolicy=(LinearLayout) findViewById(R.id.linearPrivacyPolicy);
>>>>>>> 76b77736fd1331d2a5818195084ba5ec3f313bfe
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        menuBtn = (ImageButton) findViewById(R.id.menu_btn);
        llPersonalInfo=(LinearLayout) findViewById(R.id.llPersonalInfo);
        llLogout=(LinearLayout) findViewById(R.id.llLogout);
        txtLogout=(TextView) findViewById(R.id.txtLogout);
        llCandidateList=(LinearLayout) findViewById(R.id.llCandidateList);
        txtCandidateLsit=(TextView) findViewById(R.id.txtCandidateLsit);
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
        llLogout.setOnClickListener(this);
        txtLogout.setOnClickListener(this);
        llCandidateList.setOnClickListener(this);
        txtCandidateLsit.setOnClickListener(this);
<<<<<<< HEAD
=======
        txtPrivacyPolicy.setOnClickListener(this);
        linearPrivacyPolicy.setOnClickListener(this);
>>>>>>> 76b77736fd1331d2a5818195084ba5ec3f313bfe
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
            case R.id.llLogout:
//                it will check drawer state
                checkDrawerState();
//                this function will inflate the My payment screen
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        logoutDialog();
                    }
                }, drawerCloseTime);
                break;

            case R.id.txtLogout:
//                it will check drawer state
                checkDrawerState();
//                this function will inflate the My payment screen
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        logoutDialog();
                    }
                }, drawerCloseTime);
                break;

            case R.id.llCandidateList:
//                it will check drawer state
                checkDrawerState();
//                this function will inflate the My payment screen
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setUpCandidateLits();
                    }
                }, drawerCloseTime);
                break;

            case R.id.txtCandidateLsit:
//                it will check drawer state
                checkDrawerState();
//                this function will inflate the My payment screen
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setUpCandidateLits();
                    }
                }, drawerCloseTime);
                break;
<<<<<<< HEAD
=======


            case R.id.linearPrivacyPolicy:
//                it will check drawer state
                checkDrawerState();
//                this function will inflate the My payment screen
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showPrivacyPolicy();
                    }
                }, drawerCloseTime);
                break;

            case R.id.txtPrivacyPolicy:
//                it will check drawer state
                checkDrawerState();
//                this function will inflate the My payment screen
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showPrivacyPolicy();
                    }
                }, drawerCloseTime);
                break;
>>>>>>> 76b77736fd1331d2a5818195084ba5ec3f313bfe
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
        finish();
    }

    private void showFragment(Fragment fragment, String tag) {


        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, tag);
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();
    }
    public void setupHomeFragment() {
        txtToolbartext.setText("Personal Info");
        imgICBack.setVisibility(View.GONE);
        showFragment(PersonInfoFragment.newInstance(this), PersonInfoFragment.TAG);


    }
<<<<<<< HEAD
=======
    private void showPrivacyPolicy() {
        imgICBack.setVisibility(View.GONE);

        txtToolbartext.setText("Privacy Policy");
        showFragment(new PrivacyPolicy(), PrivacyPolicy.TAG);

    }
>>>>>>> 76b77736fd1331d2a5818195084ba5ec3f313bfe
    private void setUpCandidateLits() {



        txtToolbartext.setText("Candidate Info");
        imgICBack.setVisibility(View.GONE);
      //  showFragment(CandidateListActionaleActivity.newInstance(this), CandidateListActionaleActivity.TAG);
        startActivity(new Intent(getApplicationContext(), CandidateListActionaleActivityConvert.class));
        //  showFragment(ChangePassword.newInstance(this), ChangePassword.TAG);
        finish();
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
                startActivity(new Intent(MainActivity.this, ThanksActivity.class));
                finish();

            }
        });
        builder.show();
    }

     Dialog dialog;




}

