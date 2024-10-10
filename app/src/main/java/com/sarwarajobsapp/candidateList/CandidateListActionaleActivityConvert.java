package com.sarwarajobsapp.candidateList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.preferences.SavePreferences;
import com.sarwarajobsapp.R;
import com.sarwarajobsapp.activity.PersonInfoActivity;
import com.sarwarajobsapp.adapter.CandidateSamriddhiActionAdadpterConvert;
import com.sarwarajobsapp.base.BaseActivity;
import com.sarwarajobsapp.communication.CallBack;
import com.sarwarajobsapp.communication.ServerHandler;
import com.sarwarajobsapp.dashboard.MainActivity;
import com.sarwarajobsapp.util.ProgressDialogUtil;
import com.sarwarajobsapp.utility.AppConstants;
import com.sarwarajobsapp.utility.PrefHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CandidateListActionaleActivityConvert extends BaseActivity implements View.OnClickListener ,SwipeRefreshLayout.OnRefreshListener {
    View rootView;
    public static final String TAG = "CandidateSamraddhiActionaleActivity";
    private ImageView back_btn;
     CandidateSamriddhiActionAdadpterConvert candidateSamriddhiActionAdadpterConvert;
     CandidateListActionaleActivityConvert candidateSamraddhiActionaleActivity;
    private TextView txtSignOut,txtAddUSer;
     MainActivity mainActivity;
    TextView customeToolbartext;
    private SwipeRefreshLayout srLayout;

    public static Fragment newInstance(Context context) {
        return Fragment.instantiate(context,
                CandidateListActionaleActivityConvert.class.getName());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void setUp() {
        init();
        getMember();
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_candidate_booking_list_convert;
    }

  /*  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_candidate_booking_list, container, false);
        mainActivity = (MainActivity) getActivity();

        init();
        getMember();
        return rootView;
    }*/



    @Override
    public void onResume() {
        super.onResume();
        Log.i("@@PersonInfoActivity", "onResume---");
        getMember();
    }


    private void init() {
        customeToolbartext=findViewById(R.id.customeToolbartext);
        srLayout = findViewById(R.id.srLayout);
        srLayout.setOnRefreshListener(this);
        txtAddUSer=(TextView)findViewById(R.id.txtAddUSer);
        txtAddUSer.setOnClickListener(this);
        customeToolbartext.setText("Candidate Info");
        findViewById(R.id.goback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));

                finish();
            /*    Fragment fragment = new CandidateListActionaleActivity();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();*/
            }
        });
    }


    private void showSamriddhiActionable(JSONArray dataAr) {

        RecyclerView recyclerlistview = findViewById(R.id.samraddhiiRecyclerView);
        candidateSamriddhiActionAdadpterConvert = new CandidateSamriddhiActionAdadpterConvert(dataAr, candidateSamriddhiActionAdadpterConvert);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false);
        recyclerlistview.setLayoutManager(horizontalLayoutManagaer);
        //      recyclerlistview.setNestedScrollingEnabled(false);
        recyclerlistview.setItemAnimator(new DefaultItemAnimator());
        recyclerlistview.setAdapter(candidateSamriddhiActionAdadpterConvert);
    }


    @Override
    public void onClick(View v) {

        if (v == txtAddUSer) {
            startActivity(new Intent(getApplicationContext(), PersonInfoActivity.class));
            finish();
        }
    }

    public String getLoginData(String dataType) {
        try {
            JSONObject data = new JSONObject(new SavePreferences().reterivePreference(getApplicationContext(), AppConstants.logindata).toString());
            return data.getString(dataType);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
    private void getMember() {
      //  System.out.println("wdcodes====" +  PrefHelper.getInstance().getSharedValue("AppConstants.P_user_id"));
        ProgressDialogUtil.showProgressDialog(CandidateListActionaleActivityConvert.this);
        LinkedHashMap<String, String> m = new LinkedHashMap<>();
        Map<String, String> headerMap = new HashMap<>();
        m.put("admin_user_id",  getLoginData("id"));

        System.out.println("wdcodes===="+ AppConstants.apiUlr+"candidate/list"+m);

        new ServerHandler().sendToServer(this, AppConstants.apiUlr + "candidate/list", m, 0, headerMap, 20000, R.layout.loader_dialog, new CallBack() {

            @Override
            public void getRespone(String dta, ArrayList<Object> respons) {
                srLayout.setRefreshing(false);
                ProgressDialogUtil.hideProgressDialog();
                try {
                    System.out.println("wdcodes====" + dta);
                    JSONObject obj = new JSONObject(dta);
                    if (obj.getString("message") .equalsIgnoreCase("Candidate Listing")) {
                        JSONArray dataAr = obj.getJSONArray("data");
                        System.out.println("dataAr====" + dataAr);
                        showSamriddhiActionable(dataAr);


                    } else {
                        srLayout.setRefreshing(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


    @Override
    public void onRefresh() {
        srLayout.setRefreshing(true);

        getMember();
    }
}