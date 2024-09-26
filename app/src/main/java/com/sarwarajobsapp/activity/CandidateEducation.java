package com.sarwarajobsapp.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.app.preferences.SavePreferences;
import com.google.android.material.textfield.TextInputLayout;
import com.sarwarajobsapp.R;
import com.sarwarajobsapp.base.AppViewModel;
import com.sarwarajobsapp.base.BaseActivity;
import com.sarwarajobsapp.communication.ApiProductionS;
import com.sarwarajobsapp.communication.CallBack;
import com.sarwarajobsapp.communication.ServerHandler;
import com.sarwarajobsapp.dashboard.MainActivity;
import com.sarwarajobsapp.modelattend.AttendanceModell;
import com.sarwarajobsapp.modelattend.CanddiateAttendanceModell;
import com.sarwarajobsapp.util.FileUtilsss;
import com.sarwarajobsapp.utility.AppConstants;
import com.sarwarajobsapp.utility.PrefHelper;
import com.wallet.retrofitapi.api.RxAPICallHelper;
import com.wallet.retrofitapi.api.RxAPICallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import Communication.BuildRequestParms;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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
    EditText txtUploadResume;
    TextView txtResume;
    String imagePathUrlAdhar;
    File file1 ;
    ///
    private ArrayList<String> docPaths;
    private int REQUEST_CODE_OPEN = 101;
    Uri selectedImage;
    private String type;
    String  getImageFromGallery;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private int STORAGE_PERMISSION_CODE = 23;
    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static final int PICKFILE_RESULT_CODE = 1;
    Uri fileUriii;
    MultipartBody.Part bodyAdharfileupload_resume;
    String selectedSchool;
    File filePathsss;
    Spinner spinnerSchool;
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
     //   docPaths = new ArrayList<>();
       // verifyStoragePermissions(CandidateEducation.this);
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
        Log.i("@@CanddiateActivity", "onResume---");
        //verifyStoragePermissions(CandidateEducation.this);

    }

    private void initView() {
        txtInputDegree=findViewById(R.id.txtInputDegree);
        txtResume=(TextView)findViewById(R.id.txtResume);
        txtUploadResume=(EditText) findViewById(R.id.txtUploadResume);
        txtDegree=findViewById(R.id.txtDegree);
        txtInputTitle = findViewById(R.id.txtInputTitle);
        txtInputCompanyName = findViewById(R.id.txtInputCompanyName);
        txtInputLocation = findViewById(R.id.txtInputLocation);
        txtInputStartDate = findViewById(R.id.txtInputStartDate);
        txtInputEODDate =findViewById(R.id.txtInputEODDate);
        txtInputJOB = findViewById(R.id.txtInputJOB);
        verify_btn = findViewById(R.id.verify_btn);
     //   txtSchool = findViewById(R.id.txtSchool);
        txtFieldStudy = findViewById(R.id.txtFieldStudy);
        txtGradle = findViewById(R.id.txtGradle);
        etStartDate = findViewById(R.id.etStartDate);
        etEODDate = findViewById(R.id.etEODDate);
        txtJobRpleDescritpion = findViewById(R.id.txtJobRpleDescritpion);
        etStartDate.setOnClickListener(this);
        etEODDate.setOnClickListener(this);
        verify_btn.setOnClickListener(this);
        txtResume.setOnClickListener(this);
        // Get reference to the Spinner
        // Get reference to the Spinner

        findViewById(R.id.goback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
         spinnerSchool = findViewById(R.id.spinnerSchool);

        // Array of school options
        String[] schoolOptions = {"10th", "12th", "Graduation", "Masters"};

        // Adapter for Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, schoolOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set adapter to Spinner
        spinnerSchool.setAdapter(adapter);

        // Set OnItemSelectedListener to Spinner
        spinnerSchool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get selected item
                selectedSchool = parent.getItemAtPosition(position).toString();

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
        if(v==txtResume){
            String[] mimeTypes =
                    {"application/pdf","application/msword","application/vnd.ms-powerpoint","application/vnd.ms-excel","text/plain"};

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
                intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
            }
            startActivityForResult(Intent.createChooser(intent,"ChooseFile"), PICKFILE_RESULT_CODE);
        }
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

           /* try {

                reformattedStr = myFormat.format(myFormat.parse(etStartDate.getText().toString().trim()));
            } catch (ParseException e) {
                e.printStackTrace();
            }*/
            try {
                String dateString=etStartDate.getText().toString().trim();

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                reformattedStr=sdf2.format(sdf.parse(dateString));
                Log.i("@@@----",""+sdf2.format(sdf.parse(dateString)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat myFormatEnd = new SimpleDateFormat("yyyy-MM-dd");

        /*    try {

                EndreformattedStr = myFormat.format(myFormat.parse(etEODDate.getText().toString().trim()));
            } catch (ParseException e) {
                e.printStackTrace();
            }*/
            try {
                String etEODDates=etEODDate.getText().toString().trim();

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                EndreformattedStr=sdf2.format(sdf.parse(etEODDates));
                Log.i("@@@----",""+sdf2.format(sdf.parse(etEODDates)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println("EndreformattedStr====" + EndreformattedStr);

            validateSpinner();
            if (txtDegree.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter Degree name", Toast.LENGTH_SHORT).show();

                return;
            }


          /*  if (etStartDate.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter Start Date", Toast.LENGTH_SHORT).show();

                return;
            }
            if (etEODDate.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter End Date", Toast.LENGTH_SHORT).show();

                return;
            }
*/

            if (txtJobRpleDescritpion.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter Location", Toast.LENGTH_SHORT).show();

                return;
            } else {
                Toast.makeText(getApplicationContext(),"--"+PrefHelper.getInstance().getSharedValue("AppConstants.P_user_id"),Toast.LENGTH_SHORT).show();
                getCandidateEducation(PrefHelper.getInstance().getSharedValue("AppConstants.P_user_id"), selectedSchool
                        , txtDegree.getText().toString().trim(),
                      /*  reformattedStr,EndreformattedStr, */txtJobRpleDescritpion.getText().toString().trim(),filePathsss);
            }
        }
        //}
    }
    // Validation function for Spinner
    private void validateSpinner() {
        // Get selected item
        String selectedSchool = spinnerSchool.getSelectedItem().toString();

        // Check if user selected a valid option (e.g., not the default "Select School")
        if (selectedSchool.equals("Select School")) {
            Toast.makeText(this, "Please select a valid school option", Toast.LENGTH_SHORT).show();
        } else {
           // Toast.makeText(this, "You selected: " + selectedSchool, Toast.LENGTH_SHORT).show();
            // Proceed with form submission
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

    /* public void getCandidateEducation(String user_id, String school, String specialized, String started_at,
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
     }*/
    public String getPDFPath(Uri uri){

        final String id = DocumentsContract.getDocumentId(uri);
        final Uri contentUri = ContentUris.withAppendedId(
                Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    public void getCandidateEducation(String user_id, String school, String specialized, /*String started_at,
                                      String ended_at,*/String description,File upload_file) {
        BuildRequestParms buildRequestParms = new BuildRequestParms();

        AppViewModel apiParamsInterface = ApiProductionS.getInstance(getApplicationContext()).provideService(AppViewModel.class);

        Log.i("@@11", "11");

        Observable<CanddiateAttendanceModell> observable = null;


        //For Resume upload
       try{
        //   Log.i("@@File!!",getPDFPath(fileUriii));

         //  File fileupload_resume = new File(getPDFPath(fileUriii));
          //  RequestBody requestBodyfileupload_resume= RequestBody.create(MediaType.parse("*/*"), fileupload_resume);
           // bodyAdharfileupload_resume = MultipartBody.Part.createFormData("upload_file", fileupload_resume.getName(), requestBodyfileupload_resume);

        }catch (Exception e){
            e.printStackTrace();
        }





        observable = apiParamsInterface.candidateeducationeadd(
                buildRequestParms.getRequestBody(user_id),
                buildRequestParms.getRequestBody(school),
                buildRequestParms.getRequestBody(specialized),
              //  buildRequestParms.getRequestBody(started_at),
               // buildRequestParms.getRequestBody(ended_at),
                buildRequestParms.getRequestBody(description)
               // bodyAdharfileupload_resume


        );
        Log.i("@@user_id----", observable.toString());
        Log.i("@@candiateAdd", "candiateAdd");

        final ProgressDialog mProgressDialog = new ProgressDialog(CandidateEducation.this);
        mProgressDialog.show();
        mProgressDialog.setCancelable(false);
        mProgressDialog.setTitle("Please wait..");


        RxAPICallHelper.call(observable, new RxAPICallback<CanddiateAttendanceModell>() {

            @Override
            public void onSuccess(CanddiateAttendanceModell uploadFileResponse) {


                mProgressDialog.dismiss();
                Log.i("@@----", uploadFileResponse.toString());
                System.out.println("@@CanddiateAttendanceModell" + "CanddiateAttendanceModell");
                //    Toast.makeText(getActivity(), uploadFileResponse.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("@@CanddiateAttendanceModell" + uploadFileResponse.toString());
                try {
                    if (uploadFileResponse.getMsg().equalsIgnoreCase("User Education Added")) {
                        Toast.makeText(getApplicationContext(), uploadFileResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), NewPostionScreen.class));
                        finish();

                    } else {
                        showErrorDialog(uploadFileResponse.getMsg());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailed(Throwable throwable) {
                System.out.println("error===" + throwable.getMessage());
                mProgressDialog.dismiss();
            }


        });
    }

    private void setStartDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();

        toDatePickerDialog = new DatePickerDialog(CandidateEducation.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Set the date on the Calendar instance
                        bookDateAndTime = Calendar.getInstance();
                        bookDateAndTime.set(year, monthOfYear, dayOfMonth);

                        // Use a SimpleDateFormat to correctly format the date
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        String formattedDate = dateFormat.format(bookDateAndTime.getTime());

                        // Set the formatted date to the EditText
                        etStartDate.setText(formattedDate);
                    }
                },
                newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH)
        );
    }
    private void setEndDateTimeField() {


        Calendar newCalendar = Calendar.getInstance();

        toDatePickerDialogEnd = new DatePickerDialog(CandidateEducation.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Set the date on the Calendar instance
                        bookDateAndTime = Calendar.getInstance();
                        bookDateAndTime.set(year, monthOfYear, dayOfMonth);

                        // Use a SimpleDateFormat to correctly format the date
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        String formattedDate = dateFormat.format(bookDateAndTime.getTime());

                        // Set the formatted date to the EditText
                        etEODDate.setText(formattedDate);
                    }
                },
                newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH)
        );
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_OPEN && resultCode == RESULT_OK) {


            type = "File";
        //    docPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));
            if (docPaths != null && docPaths.size() != 0) {
                logWtf(docPaths.get(0));
            }
        }
        else if (requestCode == PICKFILE_RESULT_CODE && resultCode == RESULT_OK) {

            fileUriii = data.getData();
            Log.i("@@fileUriii----1111",""+fileUriii);

            filePathsss = new File(((fileUriii.getPath())));
            Log.i("@@filePathsss----1111",""+filePathsss);
            //txtUploadResume.setText(filePathsss);
            // getFileName(fileUriii);

            txtUploadResume.setText(getFileName(fileUriii));
            //new saveResume().execute(fileUriii);
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
    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}