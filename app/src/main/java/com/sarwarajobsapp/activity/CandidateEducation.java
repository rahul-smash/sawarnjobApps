package com.sarwarajobsapp.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.app.preferences.SavePreferences;
import com.google.android.material.textfield.TextInputLayout;
import com.sarwarajobsapp.R;
import com.sarwarajobsapp.base.BaseActivity;
import com.sarwarajobsapp.communication.CallBack;
import com.sarwarajobsapp.communication.ServerHandler;
import com.sarwarajobsapp.dashboard.MainActivity;
import com.sarwarajobsapp.utility.AppConstants;
import com.sarwarajobsapp.utility.PrefHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

//public class CandidateEducation extends Fragment implements View.OnClickListener {
public class CandidateEducation extends BaseActivity implements View.OnClickListener {
    public static final String TAG = "CandidateEducation";
    private MainActivity mainActivity;
    View rootView;
    TextInputLayout txtInputTitle, txtInputCompanyName, txtInputLocation, txtInputStartDate, txtInputEODDate, txtInputJOB;
    TextView verify_btn;
    Spinner txtDegree;
    EditText txtSchool, txtFieldStudy, txtGradle, etStartDate, etEODDate, txtJobRpleDescritpion;
    Calendar bookDateAndTime;
    private DatePickerDialog toDatePickerDialog;
    private DatePickerDialog toDatePickerDialogEnd;

    LinearLayout llAccount;
    String reformattedStr;

    /*public static Fragment newInstance(Context context) {
        return Fragment.instantiate(context,
                CandidateEducation.class.getName());
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_cand_education, container, false);
        mainActivity = (MainActivity) getActivity();

        initView();
        setStartDateTimeField();
        setEndDateTimeField();
        return rootView;
    }*/
   @Override
   protected void setUp() {
       initView();
       setStartDateTimeField();
       setEndDateTimeField();

   }
    @Override
    protected int setLayout() {
        return R.layout.activity_cand_education;
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.i("@@PersonInfoActivity", "onResume---");

    }

    private void initView() {
        txtInputTitle = findViewById(R.id.txtInputTitle);
        txtInputCompanyName = findViewById(R.id.txtInputCompanyName);
        txtInputLocation = findViewById(R.id.txtInputLocation);
        txtInputStartDate = findViewById(R.id.txtInputStartDate);
        txtInputEODDate =findViewById(R.id.txtInputEODDate);
        txtInputJOB = findViewById(R.id.txtInputJOB);
        verify_btn = findViewById(R.id.verify_btn);
        txtSchool = findViewById(R.id.txtSchool);
        txtFieldStudy = findViewById(R.id.txtFieldStudy);
        txtGradle = findViewById(R.id.txtGradle);
        etStartDate = findViewById(R.id.etStartDate);
        etEODDate = findViewById(R.id.etEODDate);
        txtJobRpleDescritpion = findViewById(R.id.txtJobRpleDescritpion);
        etStartDate.setOnClickListener(this);
        etEODDate.setOnClickListener(this);
        verify_btn.setOnClickListener(this);
        findViewById(R.id.goback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    public void onClick(View v) {
       if(v==verify_btn){
           startActivity(new Intent(getApplicationContext(), NewPostionScreen.class));
           finish();
       }
   /*     if (v == etStartDate) {
            //     setDateTimeField();
            toDatePickerDialog.show();
        }

        if (v == verify_btn) {
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

            try {

                reformattedStr = myFormat.format(myFormat.parse(etStartDate.getText().toString().trim()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println("reformattedStr====" +reformattedStr);

            if (etFirstName.getText().toString().length() <= 0) {
                Toast.makeText(getActivity(), "Enter First name", Toast.LENGTH_SHORT).show();
                return;
            }
            if (etLastName.getText().toString().length() <= 0) {
                Toast.makeText(getActivity(), "Enter Last name", Toast.LENGTH_SHORT).show();

                return;
            }
            if (!Utility.checkValidEmail(etEmail.getText().toString())) {
                etEmail.requestFocus();
                Toast.makeText(getActivity(), "Enter valid email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (etPhone.getText().toString().length() <= 0) {
                Toast.makeText(getActivity(), "Enter Phone", Toast.LENGTH_SHORT).show();

                return;
            }
            if (etStartDate.getText().toString().length() <= 0) {
                Toast.makeText(getActivity(), "Enter Start Date", Toast.LENGTH_SHORT).show();

                return;
            }

            if (etLookingJobType.getText().toString().length() <= 0) {
                Toast.makeText(getActivity(), "Enter Looking JobType", Toast.LENGTH_SHORT).show();

                return;
            }

            if (etLoction.getText().toString().length() <= 0) {
                Toast.makeText(getActivity(), "Enter Location", Toast.LENGTH_SHORT).show();

                return;
            } else {
                getPersonalInfoApi(getLoginData("id"), etFirstName.getText().toString().trim()
                        ,  etLastName.getText().toString().trim(), etEmail.getText().toString().trim(), etPhone.getText().toString().trim(),
                        reformattedStr, etLookingJobType.getText().toString().trim(), etLoction.getText().toString().trim());
            }*/

        //}
    }

    public String getLoginData(String dataType) {
        try {
            JSONObject data = new JSONObject(new SavePreferences().reterivePreference(this, AppConstants.logindata).toString());
            return data.getString(dataType);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public void getCandidateEducation(String user_id, String school, String specialized, String email, String started_at,
                                   String ended_at,String description) {

        LinkedHashMap<String, String> m = new LinkedHashMap<>();

        //   m.put("admin_user_id", getLoginData("id"));
        m.put("user_id", user_id);
        m.put("school", school);
        m.put("specialized", specialized);
        m.put("started_at", started_at);
        m.put("ended_at", ended_at);
        m.put("description", description);


        Map<String, String> headerMap = new HashMap<>();
        System.out.println("getPersonalInfoApi====" + AppConstants.apiUlr + "candidate/education/add" + m);

        new ServerHandler().sendToServer(this, AppConstants.apiUlr + "candidate/education/add", m, 0, headerMap, 20000, R.layout.loader_dialog, new CallBack() {
            @Override
            public void getRespone(String dta, ArrayList<Object> respons) {
                try {
                    System.out.println("getPersonalInfoApi====" + dta);
                    JSONObject obj = new JSONObject(dta);
                    JSONObject objPuser_id = obj.getJSONObject("data");

                    System.out.println("getPersonalInfoApi====" + obj.toString());
                    System.out.println("getPersonalInfoApi==1==" + obj.getString("message").toString());
                    if (obj.getString("message").equalsIgnoreCase("Candidate Created")) {
                        PrefHelper.getInstance().storeSharedValue("AppConstants.P_user_id", objPuser_id.getString("user_id"));
                        // startActivity(new Intent(getActivity(), MainActivity.class));
                        //  getActivity().finish();

                    } else {
                        showErrorDialog(obj.getString("message"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void setStartDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();

        toDatePickerDialog = new DatePickerDialog(CandidateEducation.this,
                new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        bookDateAndTime = Calendar.getInstance();
                        bookDateAndTime.set(year, monthOfYear, dayOfMonth);
                        // date to our edit text.
                        String dat = (dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        etStartDate.setText(dat);
                    }
                }, newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));


    }
    private void setEndDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();

        toDatePickerDialogEnd = new DatePickerDialog(CandidateEducation.this,
                new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        bookDateAndTime = Calendar.getInstance();
                        bookDateAndTime.set(year, monthOfYear, dayOfMonth);
                        // date to our edit text.
                        String dat = (dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        etEODDate.setText(dat);
                    }
                }, newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));


    }


}