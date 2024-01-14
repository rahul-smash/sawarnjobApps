package com.sarwarajobsapp.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.Toast;

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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    TextInputLayout txtInputTitle, txtInputDegree,txtInputCompanyName, txtInputLocation, txtInputStartDate, txtInputEODDate, txtInputJOB;
    TextView verify_btn,txtADDREsume;
    //Spinner txtDegree;
    EditText txtDegree,txtSchool, txtFieldStudy, txtGradle, etStartDate, etEODDate, txtJobRpleDescritpion;
    Calendar bookDateAndTime;
    private DatePickerDialog toDatePickerDialog;
    private DatePickerDialog toDatePickerDialogEnd;

    LinearLayout llAccount;
    String reformattedStr,EndreformattedStr;
    private Uri imageFeatureUri;
    public static final int IMAGE_REQUEST_GALLERY_register_adhar = 325;
    public static final int IMAGE_REQUEST_CAMERA_register_adhar = 326;
    Uri source;
    EditText etResumeUpload;
    String imagePathUrlAdhar;
    File file1 ;
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
       txtInputDegree=findViewById(R.id.txtInputDegree);
        etResumeUpload=findViewById(R.id.etResumeUpload);
        txtADDREsume=findViewById(R.id.txtADDREsume);
       txtDegree=findViewById(R.id.txtDegree);
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

        if (v == etStartDate) {
            //     setDateTimeField();
            toDatePickerDialog.show();
        }
        if (v == etEODDate) {
            //     setDateTimeField();
            toDatePickerDialogEnd.show();
        }
        if (v == verify_btn) {
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

            try {

                reformattedStr = myFormat.format(myFormat.parse(etStartDate.getText().toString().trim()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat myFormatEnd = new SimpleDateFormat("yyyy-MM-dd");

            try {

                EndreformattedStr = myFormat.format(myFormat.parse(etEODDate.getText().toString().trim()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println("reformattedStr====" + reformattedStr);

            if (txtSchool.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter School name", Toast.LENGTH_SHORT).show();
                return;
            }
            if (txtDegree.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter Degree name", Toast.LENGTH_SHORT).show();

                return;
            }


            if (etStartDate.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter Start Date", Toast.LENGTH_SHORT).show();

                return;
            }
            if (etEODDate.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter End Date", Toast.LENGTH_SHORT).show();

                return;
            }


            if (txtJobRpleDescritpion.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter Location", Toast.LENGTH_SHORT).show();

                return;
            } else {
                getCandidateEducation(getLoginData("id"), txtSchool.getText().toString().trim()
                        , txtDegree.getText().toString().trim(),
                        reformattedStr,EndreformattedStr, txtJobRpleDescritpion.getText().toString().trim());
            }
        }
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

    public void getCandidateEducation(String user_id, String school, String specialized, String started_at,
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
        System.out.println("getCandidateEducation====" + AppConstants.apiUlr + "candidate/education/add" + m);

        new ServerHandler().sendToServer(this, AppConstants.apiUlr + "candidate/education/add", m, 0, headerMap, 20000, R.layout.loader_dialog, new CallBack() {
            @Override
            public void getRespone(String dta, ArrayList<Object> respons) {
                try {
                    JSONObject obj = new JSONObject(dta);

                    System.out.println("getCandidateEducation====" + obj.toString());
                    if (obj.getString("message").equalsIgnoreCase("User Education Added")) {
                       Toast.makeText(getApplicationContext(), obj.getString("message"),Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), NewPostionScreen.class));
                          finish();

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