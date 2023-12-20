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
import com.sarwarajobsapp.util.Utility;
import com.sarwarajobsapp.utility.AppConstants;
import com.sarwarajobsapp.utility.PrefHelper;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

    public class PersonInfoActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = "PersonInfoActivity";
    private MainActivity mainActivity;
    View rootView;
    TextInputLayout txtInputFirstName, txtInputLastName, txtInputEmail, txtInputPhone, txtInputStartDate, txtInputEndDate, txtInputLocation;
    TextView verify_btn,customeToolbartext;
    EditText etFirstName, etLastName, etEmail, etPhone, etStartDate, etLookingJobType, etLoction;
    Calendar bookDateAndTime;
    private DatePickerDialog toDatePickerDialog;
    LinearLayout llAccount;
    String reformattedStr;
  /*  public static Fragment newInstance(Context context) {
        return Fragment.instantiate(context,
                PersonInfoActivity.class.getName());
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void setUp() {
        initView();
        setStartDateTimeField();
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_personal_info_duplicate;
    }

  //  @Override
  /*  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_personal_info, container, false);
        mainActivity = (MainActivity) getActivity();

        initView();
        setStartDateTimeField();
        return rootView;
    }*/

  //  @Override
    public void onResume() {
        super.onResume();
        Log.i("@@PersonInfoActivity", "onResume---");

    }

    private void initView() {
        customeToolbartext=findViewById(R.id.customeToolbartext);

        llAccount = findViewById(R.id.llAccount);
        txtInputFirstName = findViewById(R.id.txtInputFirstName);
        txtInputLastName = findViewById(R.id.txtInputLastName);
        txtInputEmail = findViewById(R.id.txtInputEmail);
        txtInputPhone = findViewById(R.id.txtInputPhone);
        txtInputStartDate = findViewById(R.id.txtInputStartDate);
        txtInputEndDate = findViewById(R.id.txtInputEndDate);
        txtInputLocation = findViewById(R.id.txtInputLocation);
        verify_btn = findViewById(R.id.verify_btn);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etStartDate = findViewById(R.id.etStartDate);
        etLookingJobType = findViewById(R.id.etLookingJobType);
        etLoction = findViewById(R.id.etLocation);
        etStartDate.setOnClickListener(this);
       // etEndDate.setOnClickListener(this);
        verify_btn.setOnClickListener(this);
        customeToolbartext.setText("Personal Info");
        findViewById(R.id.goback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));

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

        if (v == verify_btn) {
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

            try {

                 reformattedStr = myFormat.format(myFormat.parse(etStartDate.getText().toString().trim()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println("reformattedStr====" +reformattedStr);

            if (etFirstName.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter First name", Toast.LENGTH_SHORT).show();
                return;
            }
            if (etLastName.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter Last name", Toast.LENGTH_SHORT).show();

                return;
            }
            if (!Utility.checkValidEmail(etEmail.getText().toString())) {
                etEmail.requestFocus();
                Toast.makeText(this, "Enter valid email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (etPhone.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter Phone", Toast.LENGTH_SHORT).show();

                return;
            }
            if (etStartDate.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter Start Date", Toast.LENGTH_SHORT).show();

                return;
            }

            if (etLookingJobType.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter Looking JobType", Toast.LENGTH_SHORT).show();

                return;
            }

            if (etLoction.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter Location", Toast.LENGTH_SHORT).show();

                return;
            } else {
                getPersonalInfoApi(getLoginData("id"), etFirstName.getText().toString().trim()
                        ,  etLastName.getText().toString().trim(), etEmail.getText().toString().trim(), etPhone.getText().toString().trim(),
                        reformattedStr, etLookingJobType.getText().toString().trim(), etLoction.getText().toString().trim());
            }

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

    public void getPersonalInfoApi(String admin_user_id, String first_name, String last_name, String email, String phone,
                                   String sdob, String etLookingJobType, String location) {

        LinkedHashMap<String, String> m = new LinkedHashMap<>();

        //   m.put("admin_user_id", getLoginData("id"));
        m.put("admin_user_id", admin_user_id);
        m.put("first_name", first_name);
        m.put("last_name", last_name);
        m.put("email", email);
        m.put("phone", phone);
        m.put("dob", sdob);
        m.put("looking_job_type", etLookingJobType);
        m.put("address", location);


        Map<String, String> headerMap = new HashMap<>();
        System.out.println("getPersonalInfoApi====" + AppConstants.apiUlr + "candidate/add" + m);

        new ServerHandler().sendToServer(this, AppConstants.apiUlr + "candidate/add", m, 0, headerMap, 20000, R.layout.loader_dialog, new CallBack() {
            @Override
            public void getRespone(String dta, ArrayList<Object> respons) {
                try {
                    System.out.println("getPersonalInfoApi====" + dta);
                    JSONObject obj = new JSONObject(dta);
                    JSONObject objPuser_id =obj.getJSONObject("data");
                    
                    System.out.println("getPersonalInfoApi====" + obj.toString());
                    System.out.println("getPersonalInfoApi==1==" + obj.getString("message").toString());
                    if (obj.getString("message").equalsIgnoreCase("Candidate Created")) {
                        PrefHelper.getInstance().storeSharedValue("AppConstants.P_user_id", objPuser_id.getString("user_id"));
                        startActivity(new Intent(getApplicationContext(), CandidateEducation.class));

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

        toDatePickerDialog = new DatePickerDialog(PersonInfoActivity.this,
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


}