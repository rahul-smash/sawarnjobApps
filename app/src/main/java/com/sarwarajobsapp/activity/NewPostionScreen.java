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
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.app.preferences.SavePreferences;
import com.google.android.material.textfield.TextInputLayout;
import com.sarwarajobsapp.R;
import com.sarwarajobsapp.base.BaseActivity;
import com.sarwarajobsapp.candidateList.CandidateListActionaleActivity;
import com.sarwarajobsapp.candidateList.CandidateListActionaleActivityConvert;
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

//public class NewPostionScreen extends Fragment implements View.OnClickListener {
public class NewPostionScreen extends BaseActivity implements View.OnClickListener {

    public static final String TAG = "NewPostionScreen";
    TextInputLayout txtInputEmployeeType,txtInputPostion, txtInputTitle, txtInputCompanyName, txtInputLocation, txtInputStartDate, txtInputEODDate, txtInputJOB;
    TextView verify_btn,customeToolbartext;
    Spinner txtSPinnerEmployeerType;
    EditText txtEmployeeType,txtPosition,txtTitle, txtCompanyName, txtLocation, etStartDate, etEODDate, txtJobRpleDescritpion;
    Calendar bookDateAndTime;
     DatePickerDialog toDatePickerDialog;
     DatePickerDialog toDatePickerDialogEnd;
    LinearLayout llAccount;
    String reformattedStr,EndreformattedStr;
  /*  public static Fragment newInstance(Context context) {
        return Fragment.instantiate(context,
                NewPostionScreen.class.getName());
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void setUp() {
        initView();
        setStartDateTimeField();
        setEndDateTimeField();
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_new_position;
    }

    @Override
/*    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_new_position, container, false);
        mainActivity = (MainActivity) getActivity();

        initView();
        setStartDateTimeField();
        return rootView;
    }*/

    public void onResume() {
        super.onResume();
        Log.i("@@PersonInfoActivity", "onResume---");

    }


    private void initView() {
        customeToolbartext=findViewById(R.id.customeToolbartext);
        txtInputTitle = findViewById(R.id.txtInputTitle);
        txtInputCompanyName = findViewById(R.id.txtInputCompanyName);
        txtInputPostion= findViewById(R.id.txtInputPostion);
        txtPosition= findViewById(R.id.txtPosition);
        txtInputLocation = findViewById(R.id.txtInputLocation);
        txtInputStartDate = findViewById(R.id.txtInputStartDate);
        txtInputEODDate = findViewById(R.id.txtInputEODDate);
        txtInputJOB = findViewById(R.id.txtInputJOB);
        verify_btn = findViewById(R.id.verify_btn);
        txtTitle = findViewById(R.id.txtTitle);
        txtEmployeeType=findViewById(R.id.txtEmployeeType);
        txtCompanyName = findViewById(R.id.txtCompanyName);
        txtLocation = findViewById(R.id.txtLocation);
        etStartDate = findViewById(R.id.etStartDate);
        etEODDate = findViewById(R.id.etEODDate);
        txtJobRpleDescritpion = findViewById(R.id.txtJobRpleDescritpion);
        customeToolbartext.setText("Add Previous Experience");
        findViewById(R.id.goback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
              /*  Fragment fragment = new CandidateListActionaleActivity();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();*/
            }
        });
        etStartDate.setOnClickListener(this);
        etEODDate.setOnClickListener(this);
        verify_btn.setOnClickListener(this);
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

                EndreformattedStr = myFormatEnd.format(myFormatEnd.parse(etEODDate.getText().toString().trim()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println("reformattedStr====" +reformattedStr);

          /*  if (txtTitle.getText().toString().length() <= 0) {
                Toast.makeText(this,"Enter Title", Toast.LENGTH_SHORT).show();
                return;
            }*/
           /* if (txtEmployeeType.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter Employee Type", Toast.LENGTH_SHORT).show();

                return;
            }*/

            if (txtCompanyName.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter Company Name", Toast.LENGTH_SHORT).show();

                return;
            }
            if (txtPosition.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter Position", Toast.LENGTH_SHORT).show();

                return;
            }

            if (etStartDate.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter Start Date", Toast.LENGTH_SHORT).show();

                return;
            }

            if (etEODDate.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter Looking JobType", Toast.LENGTH_SHORT).show();

                return;
            }
            if (txtJobRpleDescritpion.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter Job Description", Toast.LENGTH_SHORT).show();

                return;
            }
            else {
                getPostionDataTypeApi(getLoginData("id"),
                        txtCompanyName.getText().toString().trim(),  txtPosition.getText().toString().trim(),
                        reformattedStr, EndreformattedStr, txtJobRpleDescritpion.getText().toString().trim());
            }

        }
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


    public void getPostionDataTypeApi(String user_id, String company, String position, String started_at,
                                      String ended_at,String description) {

        LinkedHashMap<String, String> m = new LinkedHashMap<>();

        m.put("user_id", user_id);
        m.put("company", company);
        m.put("position", position);
        m.put("started_at", started_at);
        m.put("ended_at", ended_at);
        m.put("description", description);


        Map<String, String> headerMap = new HashMap<>();
        System.out.println("getPostionDataTypeApi====" + AppConstants.apiUlr + "candidate/experience/add" + m);

        new ServerHandler().sendToServer(this, AppConstants.apiUlr + "candidate/experience/add", m, 0, headerMap, 20000, R.layout.loader_dialog, new CallBack() {
            @Override
            public void getRespone(String dta, ArrayList<Object> respons) {
                try {
                    JSONObject obj = new JSONObject(dta);

                    System.out.println("getPostionDataTypeApi====" + obj.toString());
                    if (obj.getString("message").equalsIgnoreCase("User Experience Added")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"),Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), CandidateListActionaleActivityConvert.class));
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

        toDatePickerDialog = new DatePickerDialog(NewPostionScreen.this,
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

        toDatePickerDialogEnd = new DatePickerDialog(NewPostionScreen.this,
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