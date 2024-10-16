package com.sarwarajobsapp.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


import com.sarwarajobsapp.R;
import com.sarwarajobsapp.communication.CallBack;
import com.sarwarajobsapp.communication.ServerHandler;
import com.sarwarajobsapp.dashboard.MainActivity;
import com.sarwarajobsapp.utility.AppConstants;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class PrivacyPolicy extends Fragment {
    public static final String TAG = "PrivacyPolicy";

    private MainActivity mainActivity;
    WebView Webview;
    private View rootView;

    public static Fragment newInstance(Context context) {
        return Fragment.instantiate(context,
                PrivacyPolicy.class.getName());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_privacypolicy, container, false);

        mainActivity = (MainActivity) getActivity();
        init();
        privacypolicy();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("@@MainHome_ONResume---", "onResume---");
        privacypolicy();
    }

    private void init() {
        Webview = (WebView) rootView.findViewById(R.id.Webview);
        Webview.getSettings().setLoadsImagesAutomatically(true);
        Webview.getSettings().setJavaScriptEnabled(true);
        Webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
    }


    public void privacypolicy() {
        LinkedHashMap<String, String> m = new LinkedHashMap<>();


        Map<String, String> headerMap = new HashMap<>();

        new ServerHandler().sendToServers(getActivity(), AppConstants.apiUlr + "privacy", m, 0, headerMap, 20000, R.layout.loader_dialog, new CallBack() {
            @Override
            public void getRespone(String dta, ArrayList<Object> respons) {
                try {
                    System.out.println("privacyPolicy====" + dta);
                    JSONObject obj = new JSONObject(dta);
                    if (obj.getString("message").equalsIgnoreCase("Privacy Policy")) {

                        Webview.loadData(obj.getString("data"), "text/html", "utf-8");


                    } else {
                        System.out.println("Inside else==");

                        mainActivity.showErrorDialog(obj.getString("msg"));


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}



