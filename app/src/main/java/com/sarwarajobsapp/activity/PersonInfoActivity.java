package com.sarwarajobsapp.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.sarwarajobsapp.R;
import com.sarwarajobsapp.base.BaseActivity;
import com.sarwarajobsapp.dashboard.MainActivity;
import com.sarwarajobsapp.util.Utility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PersonInfoActivity extends Fragment implements View.OnClickListener{
    public static final String TAG = "PersonInfoActivity";
    private MainActivity mainActivity;
     View rootView;
     TextInputLayout txtInputFirstName, txtInputLastName, txtInputEmail, txtInputPhone, txtInputStartDate, txtInputEndDate, txtInputLocation;
     TextView verify_btn;
     EditText etFirstName,etLastName,etEmail,etPhone,etStartDate,etEndDate,etLoction;
    Calendar bookDateAndTime;
    private DatePickerDialog toDatePickerDialog;
    private DatePickerDialog EndtoDatePickerDialog;


    public static Fragment newInstance(Context context) {
        return Fragment.instantiate(context,
                PersonInfoActivity.class.getName());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_personal_info, container, false);
        mainActivity = (MainActivity) getActivity();

        initView();
        setStartDateTimeField();
        setEndDateTimeField();
        //  getSamriddhiEntryDashboardData();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("@@PersonInfoActivity", "onResume---");
        //getSamriddhiEntryDashboardData();

    }

    private void initView() {
        txtInputFirstName = rootView.findViewById(R.id.txtInputFirstName);
        txtInputLastName = rootView.findViewById(R.id.txtInputLastName);
        txtInputEmail = rootView.findViewById(R.id.txtInputEmail);
        txtInputPhone = rootView.findViewById(R.id.txtInputPhone);
        txtInputStartDate = rootView.findViewById(R.id.txtInputStartDate);
        txtInputEndDate = rootView.findViewById(R.id.txtInputEndDate);
        txtInputLocation = rootView.findViewById(R.id.txtInputLocation);
        verify_btn = rootView.findViewById(R.id.verify_btn);
        etFirstName= rootView.findViewById(R.id.etFirstName);
                etLastName= rootView.findViewById(R.id.etLastName);
        etEmail= rootView.findViewById(R.id.etEmail);
                etPhone= rootView.findViewById(R.id.etPhone);
        etStartDate= rootView.findViewById(R.id.etStartDate);
                etEndDate= rootView.findViewById(R.id.etEOD);
        etLoction= rootView.findViewById(R.id.etLocation);
        etStartDate.setOnClickListener(this);
        etEndDate.setOnClickListener(this);
        verify_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == etStartDate) {
            //     setDateTimeField();
            toDatePickerDialog.show();
        }
        if (v == etEndDate) {
            //     setDateTimeField();
            EndtoDatePickerDialog.show();
        }
        if (v == verify_btn) {
            if (etFirstName.getText().toString().length() <= 0) {
                Toast.makeText(getActivity(),"Enter First name",Toast.LENGTH_SHORT).show();
                return;
            }
            if (etLastName.getText().toString().length() <= 0) {
                Toast.makeText(getActivity(),"Enter Last name",Toast.LENGTH_SHORT).show();

                return;
            }
            if (!Utility.checkValidEmail(etEmail.getText().toString())) {
                etEmail.requestFocus();
                Toast.makeText(getActivity(), "Enter valid email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (etPhone.getText().toString().length() <= 0) {
                Toast.makeText(getActivity(),"Enter Phone",Toast.LENGTH_SHORT).show();

                return;
            }
            if (etStartDate.getText().toString().length() <= 0) {
                Toast.makeText(getActivity(),"Enter Start Date",Toast.LENGTH_SHORT).show();

                return;
            }

            if (etEndDate.getText().toString().length() <= 0) {
                Toast.makeText(getActivity(),"Enter End Date",Toast.LENGTH_SHORT).show();

                return;
            }

            if (etLoction.getText().toString().length() <= 0) {
                Toast.makeText(getActivity(),"Enter Location",Toast.LENGTH_SHORT).show();

                return;
            }


            else {
                Bundle bundle = new Bundle();

            }

        }
    }



    private void setStartDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();

        toDatePickerDialog = new DatePickerDialog(getActivity(),
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

        EndtoDatePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        bookDateAndTime = Calendar.getInstance();
                        bookDateAndTime.set(year, monthOfYear, dayOfMonth);
                        // date to our edit text.
                        String dat = (dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        etEndDate.setText(dat);
                    }
                }, newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));


    }

}