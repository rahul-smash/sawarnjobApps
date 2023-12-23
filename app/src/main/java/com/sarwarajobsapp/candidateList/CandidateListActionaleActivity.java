/*
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

import com.app.preferences.SavePreferences;
import com.sarwarajobsapp.R;
import com.sarwarajobsapp.activity.PersonInfoActivity;
import com.sarwarajobsapp.activity.PersonInfoFragment;
import com.sarwarajobsapp.adapter.CandidateSamriddhiActionAdadpter;
import com.sarwarajobsapp.communication.CallBack;
import com.sarwarajobsapp.communication.ServerHandler;
import com.sarwarajobsapp.dashboard.MainActivity;
import com.sarwarajobsapp.utility.AppConstants;
import com.sarwarajobsapp.utility.PrefHelper;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CandidateListActionaleActivity extends Fragment implements View.OnClickListener {
    View rootView;
    public static final String TAG = "CandidateSamraddhiActionaleActivity";
    private ImageView back_btn;
     CandidateSamriddhiActionAdadpter candidateSamriddhiActionAdadpter;
     CandidateListActionaleActivity candidateSamraddhiActionaleActivity;
    private TextView txtSignOut,txtAddUSer;
     MainActivity mainActivity;

    public static Fragment newInstance(Context context) {
        return Fragment.instantiate(context,
                CandidateListActionaleActivity.class.getName());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_candidate_booking_list, container, false);
        mainActivity = (MainActivity) getActivity();

        init();
        getMember();
        return rootView;
    }



    @Override
    public void onResume() {
        super.onResume();
        Log.i("@@PersonInfoActivity", "onResume---");
        getMember();
    }


    private void init() {
        txtAddUSer=(TextView)rootView.findViewById(R.id.txtAddUSer);
        txtAddUSer.setOnClickListener(this);
    }


    private void showSamriddhiActionable(JSONArray dataAr) {

        RecyclerView recyclerlistview = rootView.findViewById(R.id.samraddhiiRecyclerView);
        candidateSamriddhiActionAdadpter = new CandidateSamriddhiActionAdadpter(dataAr, candidateSamraddhiActionaleActivity);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false);
        recyclerlistview.setLayoutManager(horizontalLayoutManagaer);
        //      recyclerlistview.setNestedScrollingEnabled(false);
        recyclerlistview.setItemAnimator(new DefaultItemAnimator());
        recyclerlistview.setAdapter(candidateSamriddhiActionAdadpter);
    }


    @Override
    public void onClick(View v) {

        if (v == txtAddUSer) {
            startActivity(new Intent(getActivity(), PersonInfoActivity.class));
          //  showFragment(ChangePassword.newInstance(this), ChangePassword.TAG);
            getActivity().finish();
        }
    }

    public String getLoginData(String dataType) {
        try {
            JSONObject data = new JSONObject(new SavePreferences().reterivePreference(getActivity(), AppConstants.logindata).toString());
            return data.getString(dataType);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
    private void getMember() {
        System.out.println("wdcodes====" +  PrefHelper.getInstance().getSharedValue("AppConstants.P_user_id"));
        LinkedHashMap<String, String> m = new LinkedHashMap<>();
        Map<String, String> headerMap = new HashMap<>();
        m.put("admin_user_id",  getLoginData("id"));

        System.out.println("wdcodes===="+ AppConstants.apiUlr+"candidate/list"+m);

        new ServerHandler().sendToServer(getActivity(), AppConstants.apiUlr + "candidate/list", m, 0, headerMap, 20000, R.layout.loader_dialog, new CallBack() {

            @Override
            public void getRespone(String dta, ArrayList<Object> respons) {
                try {
                    System.out.println("wdcodes====" + dta);
                    JSONObject obj = new JSONObject(dta);
                    if (obj.getString("message") .equalsIgnoreCase("Candidate Listing")) {
                        JSONArray dataAr = obj.getJSONArray("data");
                        System.out.println("dataAr====" + dataAr);
                        showSamriddhiActionable(dataAr);


                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


}*/
