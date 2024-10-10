package com.sarwarajobsapp.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.app.preferences.SavePreferences;
import com.google.android.material.textfield.TextInputLayout;
import com.sarwarajobsapp.R;
import com.sarwarajobsapp.base.AppViewModel;
import com.sarwarajobsapp.base.BaseActivity;
import com.sarwarajobsapp.candidateList.CandidateListActionaleActivityConvert;
import com.sarwarajobsapp.communication.ApiProductionS;
import com.sarwarajobsapp.communication.CallBack;
import com.sarwarajobsapp.communication.ServerHandler;
import com.sarwarajobsapp.dashboard.MainActivity;
import com.sarwarajobsapp.modelattend.AttendanceModell;
import com.sarwarajobsapp.modelattend.CanddiateEditProfileModell;
import com.sarwarajobsapp.util.DbBitmapUtility;
import com.sarwarajobsapp.util.FileUtil;
import com.sarwarajobsapp.util.ProgressDialogUtil;
import com.sarwarajobsapp.util.Utility;
import com.sarwarajobsapp.utility.AppConstants;
import com.sarwarajobsapp.utility.PrefHelper;
import com.wallet.retrofitapi.api.RxAPICallHelper;
import com.wallet.retrofitapi.api.RxAPICallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import Communication.BuildRequestParms;
import io.reactivex.Observable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.HttpException;

public class EditPersonInfoActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = "PersonInfoActivity";
    private MainActivity mainActivity;
    View rootView;
    TextInputLayout txtInputAddress, txtInputFirstName, txtInputLastName, txtInputEmail, txtInputPhone, txtInputStartDate, txtInputEndDate, txtInputLocation;
    TextView verify_btn, customeToolbartext, txtADDFile, txtADDImage;
    EditText etFirstName, etLastName, etEmail, etPhone, etStartDate, etLookingJobType, etLoction, etAddress;
    Calendar bookDateAndTime;
    private DatePickerDialog toDatePickerDialog;
    LinearLayout llAccount;
    String reformattedStr;
    String FirstName, LastName, email, phone, dob, llokingJobType, location;
    File filePathsss;
    EditText etUploadAdharCard;
    String imagePathUrlAdhar;
    File file1, file3;

    public static final int PICKFILE_RESULT_CODE = 1;
    ArrayList<String> docPaths;

    Uri imageFeatureUri;
    public static final int IMAGE_REQUEST_GALLERY_register_adhar = 325;
    public static final int IMAGE_REQUEST_CAMERA_register_adhar = 326;
    public static final int IMAGE_REQUEST_GALLERY_register_adhars = 328;
    public static final int IMAGE_REQUEST_CAMERA_register_adhars = 329;
    Uri source;
    String getClcikIDValue;
    EditText etImageUSer;
    String imagePathUrlAdhar3;
    TextView txtResume;
    EditText txtUploadResume;
    MultipartBody.Part bodyAdharfileupload_resume;
    Uri fileUriii;
    private int REQUEST_CODE_OPEN = 101;
    String type;
    Uri fileUri;
    private Map<String, String> stateMap = new HashMap<>();  // Map to store state name and ID
    private String selectedStateId;  // Variable to store selected state's ID
    String selectedCityId;

    Spinner spinnerGender;
    String selectedGender;
    String selectedCity;
    Spinner stateSpinner, citySpinner;
//{"message":"Candidate Listing","data":[{"id":109,"full_name":"gddsfgg","email":"ds@gmail.com","phone":"2665353","address":"ss","dob":"01-01-1970","looking_job_type":"spo","aadhar":"https:\/\/sarwarajobs.com\/storage\/uploads\/6IVezlLDDx3pPNPZLeNnJpmk17gqWtirBJrhsrDt.png","resume":"https:\/\/sarwarajobs.com\/storage\/uploads\/UwayVP7vIXhSGWataoQsBom7fHiSOYJ7wDs5z8H0.png","gender":"male","state_id":40,"city_id":3275,"profile_img":"https:\/\/sarwarajobs.com\/storage\/LnCX9msRw2EhXTqUHCJyH3FXMOrm8pIKnguJCFC2.png","description":null},{"id":108,"full_name":"dgv","email":null,"phone":"6666235689","address":"ghn","dob":"09-10-2024","looking_job_type":"ghjj","aadhar":"https:\/\/sarwarajobs.com\/storage\/uploads\/xfAGNjGl4yw98FjEXeby78EpPWeSAbLUrQ0vg4bh.png","resume":"https:\/\/sarwarajobs.com\/storage\/uploads\/kaeQEd5e6xgeb2jZT7bzMT6QxOxb06woqvE8DlnM.pdf","gender":"Male","state_id":null,"city_id":14,"profile_img":"https:\/\/sarwarajobs.com\/storage\/IiVzWRyKPEaBehPTLxAhGnbQKPbshlXCJtTJBrPS.png","description":null},{"id":107,"full_name":"testiser","email":"twst@gmail.xom","phone":"22252222","address":"jo","dob":"06-10-2024","looking_job_type":"job","aadhar":"https:\/\/sarwarajobs.com\/storage\/uploads\/Z1YIu7EWZlXNITLMPqHRKy6dMXNFEHN5nFmxnlgG.png","resume":"https:\/\/sarwarajobs.com\/storage\/uploads\/3KlUYtoEgZqRq7obl6ZY8HyEULC5pOzBUMZtfJl9.pdf","gender":null,"state_id":null,"city_id":null,"profile_img":"https:\/\/sarwarajobs.com\/storage\/Zc6AdqYKjFpu23YgAMQuDCvIhMGmGaELulit2qZo.png","description":null},{"id":104,"full_name":"cjfufifig","email":"jfcjfufi@gmail.com","phone":"5353535653","address":"fjcfuf","dob":"07-10-2024","looking_job_type":"ccjfufh","aadhar":"https:\/\/sarwarajobs.com\/storage\/uploads\/ZCb5vklEkBVoSvnnS1HFhyOR3j0qd0bMmY8f69qp.png","resume":"https:\/\/sarwarajobs.com\/storage\/uploads\/bVhtSCjAbrKabxSc9twFG7hS9oHJrWVDvUg2UWYF.pdf","gender":null,"state_id":null,"city_id":null,"profile_img":"https:\/\/sarwarajobs.com\/storage\/vt1Z6u7fWry2yNG9NySTt7ma0XnobRTT6D3A7sMm.png","description":null}]}
String getGender,getSpinnerStyate,getSpinnerCity,getcity_name,getstate_name;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void setUp() {

        getClcikIDValue = PrefHelper.getInstance().getSharedValue("clickEditID");
        Log.i("@@getClcikIDValue", getClcikIDValue);
        initView();
        setStartDateTimeField();
        fetchStates();

    }

    @Override
    protected int setLayout() {
        return R.layout.activity_edit_personal_info_duplicate;
    }


    //  @Override
    public void onResume() {
        super.onResume();
        fetchStates();
        Log.i("@@PersonInfoActivity", "onResume---");

    }

    private void initView() {
        spinnerGender = findViewById(R.id.spinnerGender);

        citySpinner = findViewById(R.id.citySpinner);
        stateSpinner = findViewById(R.id.spinnerState);
        txtADDImage = findViewById(R.id.txtADDImage);
        etImageUSer = findViewById(R.id.etImageUSer);
        txtResume = (TextView) findViewById(R.id.txtResume);
        txtUploadResume = (EditText) findViewById(R.id.txtUploadResume);
        customeToolbartext = findViewById(R.id.customeToolbartext);
        etUploadAdharCard = findViewById(R.id.etUploadAdharCard);
        txtADDFile = findViewById(R.id.txtADDFile);
        llAccount = findViewById(R.id.llAccount);
        txtInputFirstName = findViewById(R.id.txtInputFirstName);
        txtInputLastName = findViewById(R.id.txtInputLastName);
        txtInputEmail = findViewById(R.id.txtInputEmail);
        txtInputPhone = findViewById(R.id.txtInputPhone);
        txtInputStartDate = findViewById(R.id.txtInputStartDate);
        txtInputAddress = findViewById(R.id.txtInputAddress);

        txtInputEndDate = findViewById(R.id.txtInputEndDate);
        txtInputLocation = findViewById(R.id.txtInputLocation);
        verify_btn = findViewById(R.id.verify_btn);
        etFirstName = findViewById(R.id.etFirstName);
        etAddress = findViewById(R.id.etAddress);

        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etStartDate = findViewById(R.id.etStartDate);
        etLookingJobType = findViewById(R.id.etLookingJobType);
        etLoction = findViewById(R.id.etLocation);
        etStartDate.setOnClickListener(this);
        // etEndDate.setOnClickListener(this);
        verify_btn.setOnClickListener(this);
        txtADDFile.setOnClickListener(this);
        txtResume.setOnClickListener(this);
        txtADDImage.setOnClickListener(this);
        customeToolbartext.setText("Personal Info");
        try {
            etFirstName.setText(getIntent().getStringExtra("first_name"));
            etLastName.setText(getIntent().getStringExtra("last_name"));
            etEmail.setText(getIntent().getStringExtra("email"));
            etPhone.setText(getIntent().getStringExtra("phone"));
            etStartDate.setText(getIntent().getStringExtra("dob"));
            etAddress.setText(getIntent().getStringExtra("address"));
            etLookingJobType.setText(getIntent().getStringExtra("looking_job_type"));
            etLoction.setText(getIntent().getStringExtra("address"));
            etUploadAdharCard.setText(getIntent().getStringExtra("aadhar"));
            txtUploadResume.setText(getIntent().getStringExtra("resume"));
            etImageUSer.setText(getIntent().getStringExtra("profile_img"));
            Log.i("@@get|ResumeData--", getIntent().getStringExtra("resume"));
            getGender=(getIntent().getStringExtra("gender"));
                    getSpinnerStyate=(getIntent().getStringExtra("state_id"));
                    getSpinnerCity=(getIntent().getStringExtra("city_id"));
            getcity_name=(getIntent().getStringExtra("city_name"));
            getstate_name=(getIntent().getStringExtra("state_name"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        findViewById(R.id.goback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CandidateListActionaleActivityConvert.class));

                finish();
        /*    Fragment fragment = new CandidateListActionaleActivity();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();*/
            }
        });
        String[] schoolOptions = {"Male", "Female",};

        // Adapter for Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, schoolOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set adapter to Spinner
        spinnerGender.setAdapter(adapter);

        // Set OnItemSelectedListener to Spinner
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get selected item
                selectedGender = parent.getItemAtPosition(position).toString();
                Log.i("@@selectedGender-", selectedGender);
                // Show selected item in a Toast
                //  Toast.makeText(CandidateEducation.this, "Selected: " + selectedSchool, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional: Handle the case when nothing is selected
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v == txtADDImage) {
            openDailogForImagePickOptionRegisterAdhars();
        }
        if (v == txtResume) {
            String[] mimeTypes =
                    {"application/pdf", "application/msword", "application/vnd.ms-powerpoint", "application/vnd.ms-excel", "text/plain"};

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
                if (mimeTypes.length > 0) {
                    intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                }
            } else {
                String mimeTypesStr = "";
                for (String mimeType : mimeTypes) {
                    mimeTypesStr += mimeType + "|";
                }
                intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
            }
            startActivityForResult(Intent.createChooser(intent, "ChooseFile"), PICKFILE_RESULT_CODE);
        }
        if (v == txtADDFile) {
            openDailogForImagePickOptionRegisterAdhar();
        }
        if (v == etStartDate) {
            //     setDateTimeField();
            toDatePickerDialog.show();
        }

        if (v == verify_btn) {


            try {
                String dateString = etStartDate.getText().toString().trim();

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                reformattedStr = sdf2.format(sdf.parse(dateString));
                Log.i("@@@----", "" + sdf2.format(sdf.parse(dateString)));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (etFirstName.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter First name", Toast.LENGTH_SHORT).show();
                return;
            }
          /*  if (etLastName.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter Last name", Toast.LENGTH_SHORT).show();

                return;
            }*/
           /* if (!Utility.checkValidEmail(etEmail.getText().toString())) {
                etEmail.requestFocus();
                Toast.makeText(this, "Enter valid email", Toast.LENGTH_SHORT).show();
                return;
            }*/

            if (etPhone.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter Phone", Toast.LENGTH_SHORT).show();

                return;
            }
            if (etStartDate.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter Start Date", Toast.LENGTH_SHORT).show();

                return;
            }

            if (etAddress.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter Address", Toast.LENGTH_SHORT).show();

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
                Log.i("@@getClcikIDValue",""+getClcikIDValue);
                Log.i("@@etFirstName",""+etFirstName.getText().toString().trim());
                Log.i("@@etEmail",""+etEmail.getText().toString().trim());
                Log.i("@@etPhone",""+ etPhone.getText().toString().trim());
                Log.i("@@reformattedStr",""+reformattedStr.trim());
                Log.i("@@etAddress",""+etAddress.getText().toString().trim());
                Log.i("@@etLookingJobType",""+etLookingJobType.getText().toString().trim());
                Log.i("@@selectedGender",""+selectedGender);
                Log.i("@@selectedStateId",""+selectedStateId);
                Log.i("@@selectedCityId",""+selectedCityId);
                getPersonalInfoApiExtra(getClcikIDValue, etFirstName.getText().toString().trim()
                        ,etEmail.getText().toString().trim(), etPhone.getText().toString().trim(),
                        reformattedStr, etAddress.getText().toString().trim(), etLookingJobType.getText().toString().trim(),selectedGender,selectedStateId,selectedCityId, etLoction.getText().toString().trim(), file1,filePathsss,file3);
            }

        }
    }


    @SuppressLint("Range")
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
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

    private MultipartBody.Part createMultipartFromUri(Uri fileUri, String paramName) {
        try {
            // Open InputStream from the Uri
            InputStream inputStream = getContentResolver().openInputStream(fileUri);

            // Create a temporary file in the cache directory
            File tempFile = new File(getCacheDir(), "temp_file");
            OutputStream outputStream = new FileOutputStream(tempFile);

            // Write input stream data to the temp file
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }

            // Close the streams
            inputStream.close();
            outputStream.close();

            // Create the MultipartBody.Part
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), tempFile);
            return MultipartBody.Part.createFormData(paramName, tempFile.getName(), requestBody);

        } catch (Exception e) {
            Log.e("createMultipartFromUri", "Failed to create multipart from Uri: " + e.getMessage());
            return null;
        }
    }
    //I am not using this method because resum,and profile_image are mnot need now.

    public void getPersonalInfoApis(String admin_user_id, String first_name, String email, String phone,
                                   String dob, String etLookingJobTypes, String location,String gender,String state,String city,  String description,File adhar, File reume, File adhars) {

        BuildRequestParms buildRequestParms = new BuildRequestParms();
        AppViewModel apiParamsInterface = ApiProductionS.getInstance(getApplicationContext()).provideService(AppViewModel.class);

        Log.i("getPersonalInfoApi", "API Call Initiated");

        // Validate mandatory inputs to avoid crashes
        if (admin_user_id == null || admin_user_id.isEmpty()) {
            Log.e("getPersonalInfoApi", "Admin User ID is required.");
            return;
        }
        if (first_name == null || first_name.isEmpty()) {
            Log.e("getPersonalInfoApi", "First Name is required.");
            return;
        }
    /*    if (email == null || email.isEmpty()) {
            Log.e("getPersonalInfoApi", "Email is required.");
            return;
        }*/
        if (phone == null || phone.isEmpty()) {
            Log.e("getPersonalInfoApi", "Phone is required.");
            return;
        }

        Observable<CanddiateEditProfileModell> observable = null;
        MultipartBody.Part adharPart = null;
        // Handle Aadhaar file
        if (adhar != null && adhar.exists()) {
            RequestBody adharRequestBody = RequestBody.create(MediaType.parse("*/*"), adhar);
            adharPart = MultipartBody.Part.createFormData("aadhar", adhar.getName(), adharRequestBody);
            Log.i("@@adhar1__image", adhar.getAbsolutePath());
            Log.i("@@adhars1__resume", adhar.getName());
        } else {
            Log.e("@@adhar1__image", "A valid Aadhaar file is required_1.");
            return;
        }
// Resume file part from Uri
        MultipartBody.Part resumePart = createMultipartFromUri(fileUri, "resume");
        Log.i("@@adhar1__resume", resumePart.toString());
        if (resumePart == null) {
            Log.i("@@adhar1__resume_null", resumePart.toString());
            return;
        }


        MultipartBody.Part adharParts = null;
        // Handle Aadhaar file
        if (adhars != null && adhars.exists()) {
            RequestBody adharRequestBodys = RequestBody.create(MediaType.parse("*/*"), adhars);
            adharParts = MultipartBody.Part.createFormData("profile_img", adhars.getName(), adharRequestBodys);
            Log.i("@@adhars2__resume", adhars.getAbsolutePath());
            Log.i("@@adhars2__resume", adhars.getName());
        }
        observable = apiParamsInterface.candidateedit(
                buildRequestParms.getRequestBody(admin_user_id),
                buildRequestParms.getRequestBody(first_name),
                // buildRequestParms.getRequestBody(last_name),
                buildRequestParms.getRequestBody(email),
                buildRequestParms.getRequestBody(phone),
                buildRequestParms.getRequestBody(dob),
                buildRequestParms.getRequestBody(etLookingJobTypes),
                buildRequestParms.getRequestBody(gender),
                buildRequestParms.getRequestBody(state),
                buildRequestParms.getRequestBody(city),
                buildRequestParms.getRequestBody(location),
                buildRequestParms.getRequestBody(description),
                adharPart,
                resumePart,
                adharParts

        );

        Log.i("@@candiateAdd", "candiateAdd");

        final ProgressDialog mProgressDialog = new ProgressDialog(EditPersonInfoActivity.this);
        mProgressDialog.show();
        mProgressDialog.setCancelable(false);
        mProgressDialog.setTitle("Please wait..");


        RxAPICallHelper.call(observable, new RxAPICallback<CanddiateEditProfileModell>() {

            @Override
            public void onSuccess(CanddiateEditProfileModell uploadFileResponse) {
                mProgressDialog.dismiss();
                System.out.println("@@CanddiateEditProfileModell" + "CanddiateEditProfileModell");
                //    Toast.makeText(getActivity(), uploadFileResponse.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("@@CanddiateEditProfileModell" + uploadFileResponse.toString());
                try {
                    if (uploadFileResponse.getMsg().equalsIgnoreCase("Email already exist")) {
                        Toast.makeText(getApplicationContext(), uploadFileResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        showErrorDialog(uploadFileResponse.getMsg());
                    } else {
                        //          System.out.println("@@AttendanceModell_2" + uploadFileResponse.getData().getId());
                        if (uploadFileResponse.getMsg().equalsIgnoreCase("Candidate Updated")) {
                            //            PrefHelper.getInstance().storeSharedValue("AppConstants.P_user_id", uploadFileResponse.getData().getId());
                          //  startActivity(new Intent(getApplicationContext(), CandidateEducation.class));
                            Intent mIntent = new Intent(getApplicationContext(), CandidateEducation.class);
                            Bundle mBundle = new Bundle();
                            mBundle.putString("EditProfile", "Edit"); // Example: Sending a String
                            mIntent.putExtras(mBundle); // Attach the bundle to the intent
                            startActivity(mIntent); // Start the new activity
                            Toast.makeText(getApplicationContext(), uploadFileResponse.getMsg(), Toast.LENGTH_SHORT).show();

                            finish();



                        } else {
                            showErrorDialog(uploadFileResponse.getMsg());
                        }
                    }
                } catch (Exception e) {
                    mProgressDialog.dismiss();
                    showErrorDialog(uploadFileResponse.getMsg());
                    e.printStackTrace();
                }


            }


            @Override
            public void onFailed(Throwable throwable) {
                System.out.println("error===" + throwable.getMessage());
                mProgressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Email already exist!", Toast.LENGTH_SHORT).show();


            }
        });


    }


    public void getPersonalInfoApi(String admin_user_id, String first_name, String email, String phone,
                                   String dob, String etLookingJobTypes, String location, String gender,
                                   String state, String city, String description, File adhar, File resume, File adhars) {

        BuildRequestParms buildRequestParms = new BuildRequestParms();
        AppViewModel apiParamsInterface = ApiProductionS.getInstance(getApplicationContext()).provideService(AppViewModel.class);

        Log.i("getPersonalInfoApi", "API Call Initiated");

        // Mandatory validation
        if (admin_user_id == null || admin_user_id.isEmpty()) {
            Log.e("getPersonalInfoApi", "Admin User ID is required.");
            return;
        }
        if (first_name == null || first_name.isEmpty()) {
            Log.e("getPersonalInfoApi", "First Name is required.");
            return;
        }
        if (phone == null || phone.isEmpty()) {
            Log.e("getPersonalInfoApi", "Phone is required.");
            return;
        }

        // Validate Aadhaar file
        MultipartBody.Part adharPart = null;
        if (adhar != null && adhar.exists()) {
            RequestBody adharRequestBody = RequestBody.create(MediaType.parse("*/*"), adhar);
            adharPart = MultipartBody.Part.createFormData("aadhar", adhar.getName(), adharRequestBody);
            Log.i("@@adhar1__image", adhar.getAbsolutePath());
        } else {
            Log.e("@@adhar1__image", "A valid Aadhaar file is required.");
            return;
        }

        // Resume file is optional
        MultipartBody.Part resumePart = null;
        if (resume != null && resume.exists()) {
            RequestBody resumeRequestBody = RequestBody.create(MediaType.parse("*/*"), resume);
            resumePart = MultipartBody.Part.createFormData("resume", resume.getName(), resumeRequestBody);
            Log.i("@@resume", "Resume file included.");
        } else {
            Log.i("@@resume", "No resume file provided. It will be skipped in the API call.");
        }

        // Profile image is optional
        MultipartBody.Part adharParts = null;
        if (adhars != null && adhars.exists()) {
            RequestBody adharPartsRequestBody = RequestBody.create(MediaType.parse("*/*"), adhars);
            adharParts = MultipartBody.Part.createFormData("profile_img", adhars.getName(), adharPartsRequestBody);
            Log.i("@@profile_img", "Profile image included.");
        } else {
            Log.i("@@profile_img", "No profile image provided. It will be skipped in the API call.");
        }

        // Prepare API call
        Observable<CanddiateEditProfileModell> observable = apiParamsInterface.candidateedit(
                buildRequestParms.getRequestBody(admin_user_id),
                buildRequestParms.getRequestBody(first_name),
                buildRequestParms.getRequestBody(email),
                buildRequestParms.getRequestBody(phone),
                buildRequestParms.getRequestBody(dob),
                buildRequestParms.getRequestBody(etLookingJobTypes),
                buildRequestParms.getRequestBody(gender),
                buildRequestParms.getRequestBody(state),
                buildRequestParms.getRequestBody(city),
                buildRequestParms.getRequestBody(location),
                buildRequestParms.getRequestBody(description),
                adharPart,
                resumePart,      // Include resumePart even if null
                adharParts       // Include adharParts even if null
        );

        Log.i("@@candiateAdd", "candiateAdd");

        final ProgressDialog mProgressDialog = new ProgressDialog(EditPersonInfoActivity.this);
        mProgressDialog.show();
        mProgressDialog.setCancelable(false);
        mProgressDialog.setTitle("Please wait...");

        RxAPICallHelper.call(observable, new RxAPICallback<CanddiateEditProfileModell>() {

            @Override
            public void onSuccess(CanddiateEditProfileModell uploadFileResponse) {
                mProgressDialog.dismiss();
                try {
                    if (uploadFileResponse.getMsg().equalsIgnoreCase("Email already exist")) {
                        Toast.makeText(getApplicationContext(), uploadFileResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        showErrorDialog(uploadFileResponse.getMsg());
                    } else if (uploadFileResponse.getMsg().equalsIgnoreCase("Candidate Updated")) {
                        Intent mIntent = new Intent(getApplicationContext(), CandidateEducation.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putString("EditProfile", "Edit");
                        mIntent.putExtras(mBundle);
                        startActivity(mIntent);
                        Toast.makeText(getApplicationContext(), uploadFileResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        showErrorDialog(uploadFileResponse.getMsg());
                    }
                } catch (Exception e) {
                    mProgressDialog.dismiss();
                    showErrorDialog(uploadFileResponse.getMsg());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(Throwable throwable) {
                mProgressDialog.dismiss();
                Log.i("@@throwable--", "" + throwable.toString());

                // Check if the throwable is an instance of HttpException
                if (throwable instanceof HttpException) {
                    HttpException httpException = (HttpException) throwable;
                    int statusCode = httpException.code(); // Get the HTTP status code
                    String errorMessage = httpException.message(); // Get the error message

                    // You can extract the error body if needed
                    String responseBody;
                    try {
                        // Convert response body to string if needed
                        responseBody = httpException.response().errorBody().string();
                        Log.e("@@HTTP Error Body", responseBody);
                    } catch (Exception e) {
                        responseBody = "Error while retrieving error body";
                        e.printStackTrace();
                    }

                    // Handle specific HTTP status codes
                    switch (statusCode) {
                        case 400:
                            Toast.makeText(getApplicationContext(), "Bad Request: " + errorMessage, Toast.LENGTH_SHORT).show();
                            break;
                        case 401:
                            Toast.makeText(getApplicationContext(), "Unauthorized: " + errorMessage, Toast.LENGTH_SHORT).show();
                            break;
                        case 403:
                            Toast.makeText(getApplicationContext(), "Forbidden: " + errorMessage, Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(getApplicationContext(), "Not Found: " + errorMessage, Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(getApplicationContext(), "Internal Server Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(getApplicationContext(), "Unexpected error: " + errorMessage, Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else {
                    // Handle other exceptions
                    Toast.makeText(getApplicationContext(), "An error occurred: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    public void getPersonalInfoApiExtra(String admin_user_id, String first_name, String email, String phone,
                                        String dob, String etLookingJobTypes, String location,String gender,String state,String city,  String description, File adhar, File resume, File profileImage) {

        BuildRequestParms buildRequestParms = new BuildRequestParms();
        AppViewModel apiParamsInterface = ApiProductionS.getInstance(getApplicationContext()).provideService(AppViewModel.class);

        Log.i("getPersonalInfoApi", "API Call Initiated");

        // Validate mandatory inputs to avoid crashes
        if (admin_user_id == null || admin_user_id.isEmpty()) {
            Log.e("getPersonalInfoApi", "Admin User ID is required.");
            return;
        }
        if (first_name == null || first_name.isEmpty()) {
            Log.e("getPersonalInfoApi", "First Name is required.");
            return;
        }
        if (phone == null || phone.isEmpty()) {
            Log.e("getPersonalInfoApi", "Phone is required.");
            return;
        }

        Observable<CanddiateEditProfileModell> observable = null;
        MultipartBody.Part adharPart = null;
        MultipartBody.Part resumePart = null;
        MultipartBody.Part profileImagePart = null;

        // Handle Aadhaar file (mandatory)
        if (adhar != null && adhar.exists()) {
            RequestBody adharRequestBody = RequestBody.create(MediaType.parse("*/*"), adhar);
            adharPart = MultipartBody.Part.createFormData("aadhar", adhar.getName(), adharRequestBody);
            Log.i("@@adhar__image", adhar.getAbsolutePath());
        } else {
            Log.e("@@adhar__image", "A valid Aadhaar file is required.");
            return;
        }

        // Handle resume file (optional)
        if (resume != null && resume.exists()) {
            RequestBody resumeRequestBody = RequestBody.create(MediaType.parse("*/*"), resume);
            resumePart = MultipartBody.Part.createFormData("resume", resume.getName(), resumeRequestBody);
            Log.i("@@resume__file", resume.getAbsolutePath());
        } else {
            Log.i("@@resume__file", "Resume file is null or doesn't exist.");
        }

        // Handle profile image file (optional)
        if (profileImage != null && profileImage.exists()) {
            RequestBody profileImageRequestBody = RequestBody.create(MediaType.parse("*/*"), profileImage);
            profileImagePart = MultipartBody.Part.createFormData("profile_img", profileImage.getName(), profileImageRequestBody);
            Log.i("@@profile_img__file", profileImage.getAbsolutePath());
        } else {
            Log.i("@@profile_img__file", "Profile image file is null or doesn't exist.");
        }


        // Making the API call
        observable = apiParamsInterface.candidateedit(
                buildRequestParms.getRequestBody(admin_user_id),
                buildRequestParms.getRequestBody(first_name),
                // buildRequestParms.getRequestBody(last_name),
                buildRequestParms.getRequestBody(email),
                buildRequestParms.getRequestBody(phone),
                buildRequestParms.getRequestBody(dob),
                buildRequestParms.getRequestBody(etLookingJobTypes),
                buildRequestParms.getRequestBody(gender),
                buildRequestParms.getRequestBody(state),
                buildRequestParms.getRequestBody(city),
                buildRequestParms.getRequestBody(location),
                buildRequestParms.getRequestBody(description),
                adharPart,
                resumePart,
                profileImagePart
        );

        Log.i("getPersonalInfoApi", "candiateAdd API called");

        final ProgressDialog mProgressDialog = new ProgressDialog(EditPersonInfoActivity.this);
        mProgressDialog.show();
        mProgressDialog.setCancelable(false);
        mProgressDialog.setTitle("Please wait...");

        // API Call using RxJava Helper
        RxAPICallHelper.call(observable, new RxAPICallback<CanddiateEditProfileModell>() {

            @Override
            public void onSuccess(CanddiateEditProfileModell uploadFileResponse) {
                mProgressDialog.dismiss();

                Log.i("getPersonalInfoApi", "API call successful: " + uploadFileResponse.toString());

                try {
                    if (uploadFileResponse.getMsg().equalsIgnoreCase("Email already exist")) {
                        Toast.makeText(getApplicationContext(), uploadFileResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        showErrorDialog(uploadFileResponse.getMsg());
                    } else {
                        //          System.out.println("@@AttendanceModell_2" + uploadFileResponse.getData().getId());
                        if (uploadFileResponse.getMsg().equalsIgnoreCase("Candidate Updated")) {
                            //            PrefHelper.getInstance().storeSharedValue("AppConstants.P_user_id", uploadFileResponse.getData().getId());
                            //  startActivity(new Intent(getApplicationContext(), CandidateEducation.class));
                            Intent mIntent = new Intent(getApplicationContext(), CandidateEducation.class);
                            Bundle mBundle = new Bundle();
                            mBundle.putString("EditProfile", "Edit"); // Example: Sending a String
                            mIntent.putExtras(mBundle); // Attach the bundle to the intent
                            startActivity(mIntent); // Start the new activity
                            Toast.makeText(getApplicationContext(), uploadFileResponse.getMsg(), Toast.LENGTH_SHORT).show();

                            finish();



                        } else {
                            showErrorDialog(uploadFileResponse.getMsg());
                        }
                    }
                } catch (Exception e) {
                    Log.e("getPersonalInfoApi", "Error processing response.", e);
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onFailed(Throwable throwable) {
                Log.i("@@getPersonalInfoApi", "API call failed: " + throwable.getMessage().toString());
                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();
            }
        });
    }
    private void setStartDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();

        toDatePickerDialog = new DatePickerDialog(EditPersonInfoActivity.this,
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

    public void openDailogForImagePickOptionRegisterAdhar() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.layout_popup_image_option, null, false);
        final Dialog dialog = new Dialog(EditPersonInfoActivity.this);
        RelativeLayout relativeLayoutCamera = (RelativeLayout) dialogView.findViewById(R.id.relativeBlockCamera);
        RelativeLayout relativeLayoutGallery = (RelativeLayout) dialogView.findViewById(R.id.relativeBlockGallery);

        relativeLayoutCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageFromCameraRegisterPicAdhar();
                dialog.dismiss();
            }
        });
        relativeLayoutGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImagefromGalleryRegisterIcAdhar();
                dialog.dismiss();
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(dialogView);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void getImageFromCameraRegisterPicAdhar() {


        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageFeatureUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFeatureUri);
        startActivityForResult(intent, IMAGE_REQUEST_CAMERA_register_adhar);
    }

    private void getImagefromGalleryRegisterIcAdhar() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_REQUEST_GALLERY_register_adhar);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check if the request is for capturing image using the camera (Aadhar)
        if (requestCode == IMAGE_REQUEST_CAMERA_register_adhar && resultCode == RESULT_OK) {
            new SaveCaputureImageTaskRegisterPlateAdhar().execute();
        }
        // Check if the request is for selecting image from gallery (Aadhar)
        else if (requestCode == IMAGE_REQUEST_GALLERY_register_adhar && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            Log.i("@@GalleryFirstImage", "Image Uri: " + selectedImage);

            // Process the selected image from gallery
            try {
                new SaveGalleryImageTaskRegisterPlateAdhar().execute(selectedImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Check if the request is for selecting a file
        else if (requestCode == REQUEST_CODE_OPEN && resultCode == RESULT_OK && data != null) {
            // List<String> docPaths = data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS);
            if (docPaths != null && !docPaths.isEmpty()) {
                mainActivity.logWtf(docPaths.get(0));
            }
        }
        // Check if the request is for picking a file
        else if (requestCode == PICKFILE_RESULT_CODE && resultCode == RESULT_OK && data != null) {
            fileUri = data.getData();
            Log.i("@@FileUri", "File Uri: " + fileUri);

            // Convert URI to file path and display in the TextView
            File filePath = new File(fileUri.getPath());
            Log.i("@@FilePath", "File Path: " + filePath);
            txtUploadResume.setText(getFileName(fileUri));
        }
        // Check if the request is for capturing image using the camera (Adhars)
        else if (requestCode == IMAGE_REQUEST_CAMERA_register_adhars && resultCode == RESULT_OK) {
            new SaveCaputureImageTaskRegisterPlateAdhars().execute();
        }
        // Check if the request is for selecting image from gallery (Adhars)
        else if (requestCode == IMAGE_REQUEST_GALLERY_register_adhars && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            Log.i("@@GallerySecondImage", "Image Uri: " + selectedImage);

            // Process the selected image from gallery
            try {
                new SaveGalleryImageTaskRegisterPlateAdhars().execute(selectedImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    ////Adhar
    class SaveCaputureImageTaskRegisterPlateAdhar extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... bitmaps) {
            Bitmap scaledBitmap = null;
            String path = null;
            path = DbBitmapUtility.getRealPath(getApplicationContext(), imageFeatureUri);
//            Uri fileUri = Uri.fromFile(file);
            try {
                scaledBitmap = DbBitmapUtility.compressImage(path);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // File scaledFile = FileUtil.getFile(getApplicationContext());
            file1 = FileUtil.getFile(EditPersonInfoActivity.this);
            imagePathUrlAdhar = file1.getAbsolutePath();
            Log.i("@@FinallyGotSolution--", imagePathUrlAdhar);
            try {
                file1.createNewFile();
                FileOutputStream ostream = new FileOutputStream(file1);
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                ostream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                scaledBitmap.recycle();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                new File(path).delete();
            } catch (Exception e) {
                e.printStackTrace();
            }

            source = Uri.fromFile(file1);
            return source.toString();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressDialogUtil.showProgressDialog(EditPersonInfoActivity.this);
        }

        @Override
        protected void onPostExecute(String picturePath) {
            super.onPostExecute(picturePath);
            ProgressDialogUtil.hideProgressDialog();

            if (picturePath != null & !picturePath.isEmpty()) {
                //   Picasso.with(getActivity()).load(Uri.parse("file://" + picturePath)).
                //         fit().centerInside().error(R.drawable.side_image).into(vehicleImagePreview);
                String fileNameset = Uri.parse("file://" + picturePath).getLastPathSegment();
                Log.e("fileNameset", fileNameset);
                etUploadAdharCard.setText(fileNameset.toString());
            } else {
                Toast.makeText(getApplicationContext(), "Error while saving image!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class SaveGalleryImageTaskRegisterPlateAdhar extends AsyncTask<Uri, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressDialogUtil.showProgressDialog(EditPersonInfoActivity.this);
        }

        @Override
        protected String doInBackground(Uri... params) {
            Uri selectedImage = params[0];
            String path = null;
            path = FileUtil.getPath(EditPersonInfoActivity.this, selectedImage);

            Bitmap bitMap = null;
            if (path.startsWith("https") || path.startsWith("http")) {
                return null;
            }
            try {
                bitMap = decodeUri(path);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            bitMap = FileUtil.checkImageRotation(bitMap, path);
            file1 = FileUtil.getFile(EditPersonInfoActivity.this);
            imagePathUrlAdhar = file1.getAbsolutePath();
            //      txtSelectYourPhoto.setText(file1.getAbsolutePath().toString());
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            try {
                bitMap.compress(Bitmap.CompressFormat.PNG, 100, bytes);

            } catch (Exception e) {
                e.printStackTrace();
            }
            //you can create a new file name "test.jpg" in sdcard folder.
            try {
                file1.createNewFile();
                FileOutputStream fo = new FileOutputStream(file1);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return imagePathUrlAdhar;
        }

        @Override
        protected void onPostExecute(String picturePath) {
            super.onPostExecute(picturePath);
            ProgressDialogUtil.hideProgressDialog();
            if (picturePath != null & !picturePath.isEmpty()) {
                //     Picasso.with(getActivity()).load(Uri.parse("file://" + picturePath)).
                //           fit().centerInside().error(R.drawable.side_image).into(vehicleImagePreview);
                /*Picasso.with(getActivity()).load(Uri.parse("file://" + picturePath)).
                        resize(100, 100).error(R.mipmap.ic_launcher).into(imageAddProfile);*/
                String fileNameset = Uri.parse("file://" + picturePath).getLastPathSegment();
                Log.e("fileNameset", fileNameset);
                etUploadAdharCard.setText(fileNameset.toString());

            } else {
                Toast.makeText(getApplicationContext(), "Error while saving image!!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    private Bitmap decodeUri(String selectedImage) throws FileNotFoundException {

        // Decode image size

        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImage,
                o);
//        BitmapFactory.decodeStream(
//                context.getContentResolver().openInputStream(selectedImage), null, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 400;

        // Find the correct scale value. It should be the power of 2.
        Log.e("Image_Size_original", o.outWidth + "X" + o.outHeight);
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if ((width_tmp / 2) < REQUIRED_SIZE
                    || (height_tmp / 2) < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        Log.e("Scale Factor", scale + "");

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();

        o2.inSampleSize = scale;

//        Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver()
//                .openInputStream(selectedImage), null, o2);
        Bitmap bitmap = BitmapFactory.decodeFile(selectedImage,
                o2);
        Log.e("", o2.outWidth + "X" + o2.outHeight);
        return bitmap;

    }

    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{permission},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    public void openDailogForImagePickOptionRegisterAdhars() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.layout_popup_image_option, null, false);
        final Dialog dialog = new Dialog(EditPersonInfoActivity.this);
        RelativeLayout relativeLayoutCamera = (RelativeLayout) dialogView.findViewById(R.id.relativeBlockCamera);
        RelativeLayout relativeLayoutGallery = (RelativeLayout) dialogView.findViewById(R.id.relativeBlockGallery);

        relativeLayoutCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageFromCameraRegisterPicAdhars();
                dialog.dismiss();
            }
        });
        relativeLayoutGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("@@GAlleryfileNameset", "1");

                getImagefromGalleryRegisterIcAdhars();
                dialog.dismiss();
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(dialogView);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
    private void getImageFromCameraRegisterPicAdhars() {


        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageFeatureUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFeatureUri);
        startActivityForResult(intent, IMAGE_REQUEST_CAMERA_register_adhars);
    }
    private void getImagefromGalleryRegisterIcAdhars() {
        Log.i("@@GAlleryfileNameset", "2");

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_REQUEST_GALLERY_register_adhars);
    }
    class SaveCaputureImageTaskRegisterPlateAdhars extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... bitmaps) {
            Bitmap scaledBitmap = null;
            String path = null;
            path = DbBitmapUtility.getRealPath(getApplicationContext(), imageFeatureUri);
//            Uri fileUri = Uri.fromFile(file);
            try {
                scaledBitmap = DbBitmapUtility.compressImage(path);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // File scaledFile = FileUtil.getFile(getApplicationContext());
            file3 = FileUtil.getFile(EditPersonInfoActivity.this);
            imagePathUrlAdhar = file3.getAbsolutePath();
            Log.i("@@FinallyGotSolution--",imagePathUrlAdhar);
            try {
                file1.createNewFile();
                FileOutputStream ostream = new FileOutputStream(file3);
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                ostream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                scaledBitmap.recycle();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                new File(path).delete();
            } catch (Exception e) {
                e.printStackTrace();
            }

            source = Uri.fromFile(file1);
            return source.toString();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressDialogUtil.showProgressDialog(EditPersonInfoActivity.this);
        }

        @Override
        protected void onPostExecute(String picturePath) {
            super.onPostExecute(picturePath);
            ProgressDialogUtil.hideProgressDialog();

            if (picturePath != null & !picturePath.isEmpty()) {
                //   Picasso.with(getActivity()).load(Uri.parse("file://" + picturePath)).
                //         fit().centerInside().error(R.drawable.side_image).into(vehicleImagePreview);
                String fileNameset = Uri.parse("file://" + picturePath).getLastPathSegment();
                Log.e("fileNameset", fileNameset);
                etImageUSer.setText(fileNameset.toString());
            } else {
                Toast.makeText(getApplicationContext(), "Error while saving image!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    class SaveGalleryImageTaskRegisterPlateAdhars extends AsyncTask<Uri, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressDialogUtil.showProgressDialog(EditPersonInfoActivity.this);
        }

        @Override
        protected String doInBackground(Uri... params) {
            Log.i("@@GAlleryfileNameset__!","");

            Uri selectedImage = params[0];
            String path = null;
            path = FileUtil.getPath(EditPersonInfoActivity.this, selectedImage);

            Bitmap bitMap = null;
            if (path.startsWith("https") || path.startsWith("http")) {
                return null;
            }
            try {
                bitMap = decodeUri(path);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            bitMap = FileUtil.checkImageRotation(bitMap, path);
            file3 = FileUtil.getFile(EditPersonInfoActivity.this);
            imagePathUrlAdhar = file3.getAbsolutePath();
            // Log.i("@@GAlleryfileNameset__!2",imagePathUrlAdhar);

            //      txtSelectYourPhoto.setText(file1.getAbsolutePath().toString());
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            try {
                bitMap.compress(Bitmap.CompressFormat.PNG, 100, bytes);

            } catch (Exception e) {
                e.printStackTrace();
            }
            //you can create a new file name "test.jpg" in sdcard folder.
            try {
                file3.createNewFile();
                FileOutputStream fo = new FileOutputStream(file3);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return imagePathUrlAdhar;
        }

        @Override
        protected void onPostExecute(String picturePath) {
            super.onPostExecute(picturePath);
            ProgressDialogUtil.hideProgressDialog();
            if (picturePath != null & !picturePath.isEmpty()) {
                //     Picasso.with(getActivity()).load(Uri.parse("file://" + picturePath)).
                //           fit().centerInside().error(R.drawable.side_image).into(vehicleImagePreview);
                /*Picasso.with(getActivity()).load(Uri.parse("file://" + picturePath)).
                        resize(100, 100).error(R.mipmap.ic_launcher).into(imageAddProfile);*/
                String fileNameset = Uri.parse("file://" + picturePath).getLastPathSegment();
                Log.i("@@GAlleryfileNameset", fileNameset);
                etImageUSer.setText(fileNameset.toString());

            } else {
                Toast.makeText(getApplicationContext(), "Error while saving image!!", Toast.LENGTH_SHORT).show();
            }
        }

    }
    private void fetchStates() {
        OkHttpClient client = new OkHttpClient();

        String url = "https://sarwarajobs.com/api/v1/app/state/8";
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();
                    parseJson(jsonData);  // Parse the JSON data
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(getApplicationContext(), "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }

    private void parseJson(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray dataArray = jsonObject.getJSONArray("data").getJSONArray(0);
            List<String> stateNames = new ArrayList<>();
            List<String> stateIds = new ArrayList<>();  // List to store state IDs

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject stateObject = dataArray.getJSONObject(i);
                String stateId = stateObject.getString("id");
                String stateName = stateObject.getString("name");

                stateNames.add(stateName);
                stateIds.add(stateId);
            }

            runOnUiThread(() -> populateSpinner(stateNames, stateIds));  // Update the UI with state names and IDs

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void populateSpinner(List<String> stateNames, List<String> stateIds) {
        // Add "Select State" as the first item in the list
        stateNames.add(0, getstate_name);
        stateIds.add(0, getSpinnerStyate);  // Add an empty ID for "Select State"

        // Populate the stateMap with state names and their corresponding IDs
        for (int i = 1; i < stateNames.size(); i++) {
            stateMap.put(stateNames.get(i), stateIds.get(i));  // Store the state ID mapped to its name
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stateNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(adapter);

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) { // Ignore the "Select State" option
                    String selectedStateName = stateNames.get(position);
                    selectedStateId = stateMap.get(selectedStateName);  // Get the state ID from the map

                    Log.i("@@selectedState", "State Name: " + selectedStateName + ", State ID: " + selectedStateId);
                    fetchCitiesForState(selectedStateId);  // Call method to fetch and populate cities based on selectedStateId
                } else {
                    // Handle "Select State" option, for example, clear city spinner
                    selectedStateId = "";  // Reset the selectedStateId if "Select State" is selected
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing if nothing is selected
            }
        });
    }

    private void fetchCitiesForState(String stateId) {
        OkHttpClient client = new OkHttpClient();

        String url = "https://sarwarajobs.com/api/v1/app/cities/" + stateId;  // Use the selected stateId in the URL
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();
                    parseCityJson(jsonData);  // Parse the JSON data for cities
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(getApplicationContext(), "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }

    private void parseCityJson(String jsonData) {
        try {
            // Parse the incoming data as a JSONObject
            JSONObject jsonObject = new JSONObject(jsonData);

            // Access the 'data' field, which is an array containing another array
            JSONArray dataArray = jsonObject.getJSONArray("data").getJSONArray(0); // Get the first nested array

            List<String> cityNames = new ArrayList<>();
            List<String> cityIds = new ArrayList<>(); // To store city IDs

            // Loop through the nested array and extract city names and IDs
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject cityObject = dataArray.getJSONObject(i);
                cityNames.add(cityObject.getString("name"));
                cityIds.add(cityObject.getString("id")); // Assuming "id" is the key for city ID
            }

            // Update the city spinner with the list of city names
            runOnUiThread(() -> populateCitySpinner(cityNames, cityIds));

        } catch (JSONException e) {
            e.printStackTrace();
            runOnUiThread(() -> {
                Toast.makeText(getApplicationContext(), "Error parsing city data", Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void populateCitySpinner(List<String> cityNames, List<String> cityIds) {
        // Add "Select City" as the first item in the list
        cityNames.add(0, "Select City");
      //  cityIds.add(0, getSpinnerCity);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cityNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter);

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = parent.getItemAtPosition(position).toString();
                // Get the corresponding city ID based on selected city
                if (position > 0) { // Ensure a valid city is selected (not "Select City")
                    selectedCityId = cityIds.get(position - 1); // Adjusting index because we added "Select City" at position 0
                } else {
                    selectedCityId = null; // Reset ID if "Select City" is chosen
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing or handle the case when no item is selected
            }
        });
    }
}