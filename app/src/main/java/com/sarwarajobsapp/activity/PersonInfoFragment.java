package com.sarwarajobsapp.activity;

import static android.app.Activity.RESULT_OK;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

import static com.sarwarajobsapp.base.BaseActivity.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;

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
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import androidx.fragment.app.Fragment;

import com.app.preferences.SavePreferences;
import com.google.android.material.textfield.TextInputLayout;
import com.sarwarajobsapp.communication.CallBack;
import com.sarwarajobsapp.communication.ImageAttendanceCallBack;
import com.sarwarajobsapp.communication.ServerHandler;
import com.sarwarajobsapp.modelattend.AttendanceModell;
import com.sarwarajobsapp.R;
import com.sarwarajobsapp.base.AppViewModel;
import com.sarwarajobsapp.communication.ApiProductionS;
import com.sarwarajobsapp.dashboard.MainActivity;
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
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import Communication.BuildRequestParms;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PersonInfoFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "PersonInfoActivity";
    private MainActivity mainActivity;
    View rootView;
    TextInputLayout txtInputAmount,txtInputFirstName, txtInputLastName, txtInputEmail, txtInputPhone, txtInputStartDate, txtInputEndDate, txtInputLocation;
    TextView verify_btn, txtADDFile, txtADDImage;
    EditText etAmount,etFirstName, etLastName, etEmail, etPhone, etStartDate, etLookingJobType, etLoction;
    Calendar bookDateAndTime;
    private DatePickerDialog toDatePickerDialog;
    LinearLayout llAccount;
    String reformattedStr;
    private Uri imageFeatureUri;
    Uri imageFeatureUris;
    public static final int IMAGE_REQUEST_GALLERY_register_adhar = 325;
    public static final int IMAGE_REQUEST_CAMERA_register_adhar = 326;
    public static final int IMAGE_REQUEST_GALLERY_register_adhars = 328;
    public static final int IMAGE_REQUEST_CAMERA_register_adhars = 329;
    Uri source, source1;
    EditText etUploadAdharCard;
    String imagePathUrlAdhar;
    String imagePathUrlAdhar3;
    File file1, file3;
    Uri imageUrli;
    EditText txtUploadResume;
    TextView txtResume;
    public static final int PICKFILE_RESULT_CODE = 1;
    ArrayList<String> docPaths;
    private int REQUEST_CODE_OPEN = 101;
    String type;
    Uri fileUriii;
    Uri fileUri;
    MultipartBody.Part bodyAdharfileupload_resume;
    File filePathsss;
    EditText etImageUSer;
    Spinner stateSpinner, citySpinner;
    String selectedState;
    private Map<String, String> stateMap = new HashMap<>();  // Map to store state name and ID
     String selectedStateId;  // Variable to store selected state's ID

    Spinner spinnerGender;
    String selectedGender;
    String selectedCity;
    String selectedCityId;
    Spinner spinPaymentMethod;
    String selectedPayment;

    public static Fragment newInstance(Context context) {
        return Fragment.instantiate(context,
                PersonInfoFragment.class.getName());
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
        fetchStates();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("@@PersonInfofragment", "onResume---");

        fetchStates();

    }

    private void initView() {
        txtInputAmount = rootView.findViewById(R.id.txtInputAmount);
        etAmount = rootView.findViewById(R.id.etAmount);
          spinPaymentMethod=rootView.findViewById(R.id.spinPaymentMethod);
        spinnerGender = rootView.findViewById(R.id.spinnerGender);

        citySpinner = rootView.findViewById(R.id.citySpinner);
        stateSpinner = rootView.findViewById(R.id.spinnerState);
        etImageUSer = rootView.findViewById(R.id.etImageUSer);
        txtADDImage = rootView.findViewById(R.id.txtADDImage);
        txtResume = (TextView) rootView.findViewById(R.id.txtResume);
        txtUploadResume = (EditText) rootView.findViewById(R.id.txtUploadResume);
        llAccount = rootView.findViewById(R.id.llAccount);
        etUploadAdharCard = rootView.findViewById(R.id.etUploadAdharCard);
        txtADDFile = rootView.findViewById(R.id.txtADDFile);
        txtInputFirstName = rootView.findViewById(R.id.txtInputFirstName);
        txtInputLastName = rootView.findViewById(R.id.txtInputLastName);
        txtInputEmail = rootView.findViewById(R.id.txtInputEmail);
        txtInputPhone = rootView.findViewById(R.id.txtInputPhone);
        txtInputStartDate = rootView.findViewById(R.id.txtInputStartDate);
        txtInputEndDate = rootView.findViewById(R.id.txtInputEndDate);
        txtInputLocation = rootView.findViewById(R.id.txtInputLocation);
        verify_btn = rootView.findViewById(R.id.verify_btn);
        etFirstName = rootView.findViewById(R.id.etFirstName);
        etLastName = rootView.findViewById(R.id.etLastName);
        etEmail = rootView.findViewById(R.id.etEmail);
        etPhone = rootView.findViewById(R.id.etPhone);
        etStartDate = rootView.findViewById(R.id.etStartDate);
        etLookingJobType = rootView.findViewById(R.id.etLookingJobType);
        etLoction = rootView.findViewById(R.id.etLocation);
        etStartDate.setOnClickListener(this);
   //     etAmount.setText(todayCollection);
        // etEndDate.setOnClickListener(this);
        verify_btn.setOnClickListener(this);
        txtADDFile.setOnClickListener(this);
        txtResume.setOnClickListener(this);
        txtADDImage.setOnClickListener(this);

        // Array of school options
        String[] schoolOptions = {"Male", "Female",};

        // Adapter for Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, schoolOptions);
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
        // Array of school options
        // Payment method array with a default prompt at the 0th index
        String[] paymentMethod = {"Select Payment Method", "Cash", "Online", "Banking"};

// Adapter for Spinner
        ArrayAdapter<String> adapters = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, paymentMethod);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // use dropdown layout

// Set adapter to Spinner
        spinPaymentMethod.setAdapter(adapters);

// Set OnItemSelectedListener to Spinner
        spinPaymentMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Check if the first item (index 0) is selected
                if (position == 0) {
                    // Handle case when "Select Payment Method" is selected (if needed)
                    selectedPayment = null;
                    Log.i("@@selectedPayment--", "No payment method selected");
                } else {
                    // Get the selected payment method
                    selectedPayment = parent.getItemAtPosition(position).toString();
                    Log.i("@@selectedPayment--", selectedPayment);
                }
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
                Log.i("@@@---Date-", "" + sdf2.format(sdf.parse(dateString)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (etFirstName.getText().toString().length() <= 0) {
                Toast.makeText(getActivity(), "Enter First name", Toast.LENGTH_SHORT).show();
                return;
            }
           /* if (etLastName.getText().toString().length() <= 0) {
                Toast.makeText(getActivity(), "Enter Last name", Toast.LENGTH_SHORT).show();

                return;
            }*/
          /*  if (!Utility.checkValidEmail(etEmail.getText().toString())) {
                etEmail.requestFocus();
                Toast.makeText(getActivity(), "Enter valid email", Toast.LENGTH_SHORT).show();
                return;
            }*/

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
            // Validate State Spinner
           /* if (selectedStateId == null || selectedStateId.equals("Select State")) {
                Toast.makeText(getActivity(), "Select a State", Toast.LENGTH_SHORT).show();

                return ;
            }

            // Validate City Spinner
            if (selectedCity == null || selectedCity.equals("Select City")) {
                Toast.makeText(getActivity(), "Select a City", Toast.LENGTH_SHORT).show();

                return ;
            }

            // Validate Gender Selection
            if (TextUtils.isEmpty(selectedGender)) {
                Toast.makeText(getActivity(), "Select Gender", Toast.LENGTH_SHORT).show();

                return ;
            }*/
            if (etLoction.getText().toString().length() <= 0) {
                Toast.makeText(getActivity(), "Enter Location", Toast.LENGTH_SHORT).show();

                return;
            }
            if (etUploadAdharCard.getText().toString().length() <= 0) {
                Toast.makeText(getActivity(), "Select AadharCard", Toast.LENGTH_SHORT).show();
                return;
            }   if (etAmount.getText().toString().length() <= 0) {
                Toast.makeText(getActivity(), "Enter Amount", Toast.LENGTH_SHORT).show();
                return;
            }

            else {
                Log.i("@@selectedGender",""+selectedGender);
                Log.i("@@selectedStateId",""+selectedStateId);
                Log.i("@@selectedCityId",""+selectedCityId);
                getPersonalInfoApi(getLoginData("id"), etFirstName.getText().toString().trim()
                        , /*etLastName.getText().toString().trim(), */etEmail.getText().toString().trim(), etPhone.getText().toString().trim(),
                        reformattedStr, etLookingJobType.getText().toString().trim(),selectedGender,selectedStateId,selectedCityId, etLoction.getText().toString().trim(),selectedPayment,etAmount.getText().toString().trim(), file1, filePathsss, file3);
            }

        }
    }

    public String getPDFPath(Uri uri) {
        String uriAuthority = uri.getAuthority();

        if ("com.android.providers.downloads.documents".equals(uriAuthority)) {
            return getDownloadsDocumentPath(uri);
        } else if ("com.android.providers.media.documents".equals(uriAuthority)) {
            return getMediaDocumentPath(uri);
        } else if ("com.android.externalstorage.documents".equals(uriAuthority)) {
            return getExternalStorageDocumentPath(uri);
        }

        return null;
    }

    private String getDownloadsDocumentPath(Uri uri) {
        String id = DocumentsContract.getDocumentId(uri);
        String[] split = id.split(":");
        String actualId = split.length > 1 ? split[1] : split[0];

        Uri contentUri = ContentUris.withAppendedId(
                Uri.parse("content://downloads/public_downloads"), Long.parseLong(actualId));

        return getDataColumn(contentUri);
    }

    private String getMediaDocumentPath(Uri uri) {
        String docId = DocumentsContract.getDocumentId(uri);
        String[] split = docId.split(":");
        String type = split[0];
        Uri contentUri = null;

        if ("image".equals(type)) {
            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        } else if ("video".equals(type)) {
            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        } else if ("audio".equals(type)) {
            contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }

        String selection = "_id=?";
        String[] selectionArgs = new String[]{split[1]};

        return getDataColumn(contentUri, selection, selectionArgs);
    }

    private String getExternalStorageDocumentPath(Uri uri) {
        String docId = DocumentsContract.getDocumentId(uri);
        String[] split = docId.split(":");
        String type = split[0];

        if ("primary".equalsIgnoreCase(type)) {
            return Environment.getExternalStorageDirectory() + "/" + split[1];
        }

        return null;
    }

    private String getDataColumn(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        try (Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                return cursor.getString(column_index);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getDataColumn(Uri uri, String selection, String[] selectionArgs) {
        String[] projection = {MediaStore.Images.Media.DATA};
        try (Cursor cursor = getActivity().getContentResolver().query(uri, projection, selection, selectionArgs, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                return cursor.getString(column_index);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @SuppressLint("Range")
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
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
            JSONObject data = new JSONObject(new SavePreferences().reterivePreference(getActivity(), AppConstants.logindata).toString());
            return data.getString(dataType);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    private void setStartDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();

        toDatePickerDialog = new DatePickerDialog(getActivity(),
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


//for photo code


   //I am not using this method because resum,and profile_image are mnot need now.

    public void getPersonalInfoApi(String admin_user_id, String first_name, String email, String phone,
                                   String dob, String etLookingJobTypes, String gender, String state, String city, String location,String payment_method,String amount,
                                   File adhar, File resume, File profileImage) {

        BuildRequestParms buildRequestParms = new BuildRequestParms();
        AppViewModel apiParamsInterface = ApiProductionS.getInstance(mainActivity).provideService(AppViewModel.class);

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
        if (payment_method == null || payment_method.isEmpty()) {
            Log.e("getPersonalInfoApi", "payment_method is required.");
            Toast.makeText(getActivity(), "Select Payment Method is required.", Toast.LENGTH_SHORT).show();
            return; // Exit if city is not provided
        }
        Observable<AttendanceModell> observable = null;
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
        observable = apiParamsInterface.candiateAdd(
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
                buildRequestParms.getRequestBody(payment_method),
                buildRequestParms.getRequestBody(amount),
                adharPart,
                resumePart,     // Optional
                profileImagePart // Optional
        );

        Log.i("getPersonalInfoApi", "candiateAdd API called");

        final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.show();
        mProgressDialog.setCancelable(false);
        mProgressDialog.setTitle("Please wait...");

        // API Call using RxJava Helper
        RxAPICallHelper.call(observable, new RxAPICallback<AttendanceModell>() {

            @Override
            public void onSuccess(AttendanceModell uploadFileResponse) {
                mProgressDialog.dismiss();

                Log.i("getPersonalInfoApi", "API call successful: " + uploadFileResponse.toString());

                try {
                    if ("Email already exist".equalsIgnoreCase(uploadFileResponse.getMsg())) {
                        Toast.makeText(getActivity(), uploadFileResponse.getMsg(), Toast.LENGTH_SHORT).show();
                    } else if ("Candidate Created".equalsIgnoreCase(uploadFileResponse.getMsg())) {
                        PrefHelper.getInstance().storeSharedValue("AppConstants.P_user_id", uploadFileResponse.getData().getUserID());
                        startActivity(new Intent(getActivity(), CandidateEducation.class));
                        getActivity().finish();
                    } else {
                        ((MainActivity) getActivity()).showErrorDialog(uploadFileResponse.getMsg());
                    }
                } catch (Exception e) {
                    Log.e("getPersonalInfoApi", "Error processing response.", e);
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onFailed(Throwable throwable) {
                Log.e("getPersonalInfoApi", "API call failed: " + throwable.getMessage());
                Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();
            }
        });
    }

    private MultipartBody.Part createMultipartFromUri(Uri fileUri, String paramName) {
        try {
            // Open InputStream from the Uri
            InputStream inputStream = getActivity().getContentResolver().openInputStream(fileUri);

            // Create a temporary file in the cache directory
            File tempFile = new File(getActivity().getCacheDir(), "temp_file");
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

    private String getDate(String date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd hh:mm:ss");
        Date myDate = null;
        try {
            myDate = dateFormat.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
        return timeFormat.format(myDate).toString();
    }

    public void openDailogForImagePickOptionRegisterAdhar() {
        LayoutInflater inflater = (LayoutInflater) mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.layout_popup_image_option, null, false);
        final Dialog dialog = new Dialog(getActivity());
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
                Log.i("@@GAlleryfileNameset", "1");

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
        imageFeatureUri = getActivity().getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFeatureUri);
        startActivityForResult(intent, IMAGE_REQUEST_CAMERA_register_adhar);
    }

    private void getImagefromGalleryRegisterIcAdhar() {
        Log.i("@@GAlleryfileNameset", "2");

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
            path = DbBitmapUtility.getRealPath(getActivity(), imageFeatureUri);
//            Uri fileUri = Uri.fromFile(file);
            try {
                scaledBitmap = DbBitmapUtility.compressImage(path);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // File scaledFile = FileUtil.getFile(getApplicationContext());
            file1 = FileUtil.getFile(getActivity());
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
            ProgressDialogUtil.showProgressDialog(getActivity());
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
                Toast.makeText(getActivity(), "Error while saving image!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class SaveGalleryImageTaskRegisterPlateAdhar extends AsyncTask<Uri, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressDialogUtil.showProgressDialog(getActivity());
        }

        @Override
        protected String doInBackground(Uri... params) {
            Log.i("@@GAlleryfileNameset__!", "");

            Uri selectedImage = params[0];
            String path = null;
            path = FileUtil.getPath(getActivity(), selectedImage);

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
            file1 = FileUtil.getFile(getActivity());
            imagePathUrlAdhar = file1.getAbsolutePath();
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
                Log.i("@@GAlleryfileNameset", fileNameset);
                etUploadAdharCard.setText(fileNameset.toString());

            } else {
                Toast.makeText(getActivity(), "Error while saving image!!", Toast.LENGTH_SHORT).show();
            }
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
        LayoutInflater inflater = (LayoutInflater) mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.layout_popup_image_option, null, false);
        final Dialog dialog = new Dialog(getActivity());
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
        imageFeatureUri = getActivity().getContentResolver().insert(
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
            path = DbBitmapUtility.getRealPath(getActivity(), imageFeatureUri);
//            Uri fileUri = Uri.fromFile(file);
            try {
                scaledBitmap = DbBitmapUtility.compressImage(path);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // File scaledFile = FileUtil.getFile(getApplicationContext());
            file3 = FileUtil.getFile(getActivity());
            imagePathUrlAdhar = file3.getAbsolutePath();
            Log.i("@@FinallyGotSolution--", imagePathUrlAdhar);
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
            ProgressDialogUtil.showProgressDialog(getActivity());
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
                Toast.makeText(getActivity(), "Error while saving image!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class SaveGalleryImageTaskRegisterPlateAdhars extends AsyncTask<Uri, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressDialogUtil.showProgressDialog(getActivity());
        }

        @Override
        protected String doInBackground(Uri... params) {
            Log.i("@@GAlleryfileNameset__!", "");

            Uri selectedImage = params[0];
            String path = null;
            path = FileUtil.getPath(getActivity(), selectedImage);

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
            file3 = FileUtil.getFile(getActivity());
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
                Toast.makeText(getActivity(), "Error while saving image!!", Toast.LENGTH_SHORT).show();
            }
        }

    }


    private void fetchStates() {
        // Show progress dialog before starting the API call
        ProgressDialogUtil.showProgressDialog(getActivity());

        OkHttpClient client = new OkHttpClient();
        String url = "https://career-finder.co.in/api/v1/app/state/8";
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Dismiss progress dialog on failure
                getActivity().runOnUiThread(() -> {
                    ProgressDialogUtil.hideProgressDialog();
                    Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();
                    parseJson(jsonData);  // Parse the JSON data

                    // Dismiss progress dialog after successful response
                    getActivity().runOnUiThread(() -> {
                        ProgressDialogUtil.hideProgressDialog();
                    });
                } else {
                    // Dismiss progress dialog on error response
                    getActivity().runOnUiThread(() -> {
                        ProgressDialogUtil.hideProgressDialog();
                        Toast.makeText(getActivity(), "Error: " + response.message(), Toast.LENGTH_SHORT).show();
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

            getActivity().runOnUiThread(() -> populateSpinner(stateNames, stateIds));  // Update the UI with state names and IDs

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void populateSpinner(List<String> stateNames, List<String> stateIds) {
        // Add "Select State" as the first item in the list
        stateNames.add(0, "Select State");
        stateIds.add(0, "");  // Add an empty ID for "Select State"

        // Populate the stateMap with state names and their corresponding IDs
        for (int i = 1; i < stateNames.size(); i++) {
            stateMap.put(stateNames.get(i), stateIds.get(i));  // Store the state ID mapped to its name
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, stateNames);
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
        // Show progress dialog before starting the API call
        ProgressDialogUtil.showProgressDialog(getActivity());

        OkHttpClient client = new OkHttpClient();
        String url = "https://career-finder.co.in/api/v1/app/cities/" + stateId;
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Dismiss progress dialog on failure
                getActivity().runOnUiThread(() -> {
                    ProgressDialogUtil.hideProgressDialog();
                    Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();
                    parseCityJson(jsonData);  // Parse the JSON data for cities

                    // Dismiss progress dialog after successful response
                    getActivity().runOnUiThread(() -> {
                        ProgressDialogUtil.hideProgressDialog();
                    });
                } else {
                    // Dismiss progress dialog on error response
                    getActivity().runOnUiThread(() -> {
                        ProgressDialogUtil.hideProgressDialog();
                        Toast.makeText(getActivity(), "Error: " + response.message(), Toast.LENGTH_SHORT).show();
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
            getActivity().runOnUiThread(() -> populateCitySpinner(cityNames, cityIds));

        } catch (JSONException e) {
            e.printStackTrace();
            getActivity().runOnUiThread(() -> {
                Toast.makeText(getActivity(), "Error parsing city data", Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void populateCitySpinner(List<String> cityNames, List<String> cityIds) {
        // Add "Select City" as the first item in the list
        cityNames.add(0, "Select City");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, cityNames);
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
    public void summery(String admin_user_id)
    {
        LinkedHashMap<String, String> m = new LinkedHashMap<>();
        m.put("admin_user_id", admin_user_id);


        Map<String, String> headerMap = new HashMap<>();

        new ServerHandler().sendToServer(getActivity(), AppConstants.apiUlr+"today/summery", m, 0, headerMap, 20000, R.layout.loader_dialog, new CallBack() {
            @Override
            public void getRespone(String dta, ArrayList<Object> respons) {
                try {
                    System.out.println("summery===="+dta);
                    JSONObject obj = new JSONObject(dta);
                    // Get the "data" JSONObject
                    JSONObject dataObj = obj.getJSONObject("data");

                    // Fetch "today_collection" (it could be null, so handle it safely)
                    Integer todayCollection = dataObj.isNull("today_collection") ? null : dataObj.getInt("today_collection");

                    // Check if "today_collection" is null before using it
                    if (todayCollection == null) {
                        // Handle the case when "today_collection" is null
                        Log.d("JSON Parsing", "today_collection is null");
                        etAmount.setText("0"); // Set default value if null
                    } else {
                        // Use "today_collection" safely
                        int todayCollectionValue = todayCollection; // This is safe because it's not null
                        Log.d("JSON Parsing", "today_collection: " + todayCollectionValue);

                        // Convert the integer to String before setting the text
                        etAmount.setText(String.valueOf(todayCollectionValue));
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}