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

public class NewPostionScreen extends Fragment implements View.OnClickListener {
    public static final String TAG = "NewPostionScreen";
    private MainActivity mainActivity;
    View rootView;
    TextInputLayout txtInputTitle, txtInputCompanyName, txtInputLocation, txtInputStartDate, txtInputEODDate, txtInputJOB;
    TextView verify_btn;
    Spinner txtSPinnerEmployeerType;
    EditText txtTitle, txtCompanyName, txtLocation, etStartDate, etEODDate, txtJobRpleDescritpion;
    Calendar bookDateAndTime;
    private DatePickerDialog toDatePickerDialog;
    LinearLayout llAccount;
    String reformattedStr;
    public static Fragment newInstance(Context context) {
        return Fragment.instantiate(context,
                NewPostionScreen.class.getName());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_new_position, container, false);
        mainActivity = (MainActivity) getActivity();

        initView();
        setStartDateTimeField();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("@@PersonInfoActivity", "onResume---");

    }

    private void initView() {
        txtInputTitle = rootView.findViewById(R.id.txtInputTitle);
        txtInputCompanyName = rootView.findViewById(R.id.txtInputCompanyName);
        txtInputLocation = rootView.findViewById(R.id.txtInputLocation);
        txtInputStartDate = rootView.findViewById(R.id.txtInputStartDate);
        txtInputEODDate = rootView.findViewById(R.id.txtInputEODDate);
        txtInputJOB = rootView.findViewById(R.id.txtInputJOB);
        verify_btn = rootView.findViewById(R.id.verify_btn);
        txtTitle = rootView.findViewById(R.id.txtTitle);
        txtCompanyName = rootView.findViewById(R.id.txtCompanyName);
        txtLocation = rootView.findViewById(R.id.txtLocation);
        etStartDate = rootView.findViewById(R.id.etStartDate);
        etEODDate = rootView.findViewById(R.id.etEODDate);
        txtJobRpleDescritpion = rootView.findViewById(R.id.txtJobRpleDescritpion);
        etStartDate.setOnClickListener(this);
        etEODDate.setOnClickListener(this);
        verify_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
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
            JSONObject data = new JSONObject(new SavePreferences().reterivePreference(getActivity(), AppConstants.logindata).toString());
            return data.getString(dataType);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public void getNewPosition(String admin_user_id, String first_name, String last_name, String email, String phone,
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

    new ServerHandler().sendToServer(getActivity(), AppConstants.apiUlr + "candidate/add", m, 0, headerMap, 20000, R.layout.loader_dialog, new CallBack() {
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
                    // startActivity(new Intent(getActivity(), MainActivity.class));
                    //  getActivity().finish();

                } else {
                    ((MainActivity) getActivity()).showErrorDialog(obj.getString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    });
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


}