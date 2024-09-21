package com.sarwarajobsapp.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
import com.sarwarajobsapp.util.DbBitmapUtility;
import com.sarwarajobsapp.util.FileUtil;
import com.sarwarajobsapp.util.ProgressDialogUtil;
import com.sarwarajobsapp.util.Utility;
import com.sarwarajobsapp.utility.AppConstants;
import com.sarwarajobsapp.utility.PrefHelper;
import com.wallet.retrofitapi.api.RxAPICallHelper;
import com.wallet.retrofitapi.api.RxAPICallback;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import Communication.BuildRequestParms;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PersonInfoActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = "PersonInfoActivity";
     MainActivity mainActivity;
    View rootView;
    TextInputLayout txtInputFirstName, txtInputLastName, txtInputEmail, txtInputPhone, txtInputStartDate, txtInputEndDate, txtInputLocation;
    TextView verify_btn,customeToolbartext,txtADDFile;
    EditText etFirstName, etLastName, etEmail, etPhone, etStartDate, etLookingJobType, etLoction;
    Calendar bookDateAndTime;
     DatePickerDialog toDatePickerDialog;
    LinearLayout llAccount;
    String reformattedStr;
        String FirstName,LastName,email,phone,dob,llokingJobType,location;
         Uri imageFeatureUri;
        public static final int IMAGE_REQUEST_GALLERY_register_adhar = 325;
        public static final int IMAGE_REQUEST_CAMERA_register_adhar = 326;
        Uri source;
EditText etUploadAdharCard;
        String imagePathUrlAdhar;
        File file1 ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void setUp() {
     /*   SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

         FirstName = sh.getString("FirstName", "");
        LastName = sh.getString("LastName", "");
         email = sh.getString("email", "");
         phone = sh.getString("phone", "");
         dob = sh.getString("dob", "");
         llokingJobType = sh.getString("llokingJobType", "");
         location = sh.getString("location", "");*/

Log.i("@@@@@@@FirstName--",getIntent().getStringExtra("FirstName")+getIntent().getStringExtra("LastName"));
        initView();
        setStartDateTimeField();
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_personal_info_duplicate;
    }


  //  @Override
    public void onResume() {
        super.onResume();
        Log.i("@@PersonInfoActivity", "onResume---");

    }

    private void initView() {
        customeToolbartext=findViewById(R.id.customeToolbartext);
        etUploadAdharCard=findViewById(R.id.etUploadAdharCard);
        txtADDFile=findViewById(R.id.txtADDFile);
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
        txtADDFile.setOnClickListener(this);
       // etEndDate.setOnClickListener(this);
        verify_btn.setOnClickListener(this);
        customeToolbartext.setText("Personal Info");
        try{
            etFirstName.setText(getIntent().getStringExtra("first_name"));
            etLastName.setText(getIntent().getStringExtra("last_name"));
            etEmail.setText(getIntent().getStringExtra("email"));
            etPhone.setText(getIntent().getStringExtra("phone"));
            etStartDate.setText(getIntent().getStringExtra("dob"));
            etLookingJobType.setText(getIntent().getStringExtra("looking_job_type"));
            etLoction.setText(getIntent().getStringExtra("description"));

        }catch (Exception e){
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
    }


    @Override
    public void onClick(View v) {
        if(v==txtADDFile){
            openDailogForImagePickOptionRegisterAdhar();
        }
        if (v == etStartDate) {
            //     setDateTimeField();
            toDatePickerDialog.show();
        }

        if (v == verify_btn) {
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
/*
            try {

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
            System.out.println("reformattedStr====" +reformattedStr);

            if (etFirstName.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter First name", Toast.LENGTH_SHORT).show();
                return;
            }
           /* if (etLastName.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter Last name", Toast.LENGTH_SHORT).show();

                return;
            }*/
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
            } if (etUploadAdharCard.getText().toString().length() <= 0) {
                Toast.makeText(getApplicationContext(), "Select AadharCard", Toast.LENGTH_SHORT).show();

                return;
            }
            else {
                getPersonalInfoApi(getLoginData("id"), etFirstName.getText().toString().trim()
                        ,  /*etLastName.getText().toString().trim(),*/ etEmail.getText().toString().trim(), etPhone.getText().toString().trim(),
                        reformattedStr, etLookingJobType.getText().toString().trim(), etLoction.getText().toString().trim(),file1);
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

   /* public void getPersonalInfoApi(String admin_user_id, String first_name, String last_name, String email, String phone,
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
                    if(obj.getString("message").equalsIgnoreCase("Email already exist")){
                        Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_SHORT).show();
                    }else {
                        JSONObject objPuser_id = obj.getJSONObject("data");

                        System.out.println("getPersonalInfoApi====" + obj.toString());
                        System.out.println("getPersonalInfoApi==1==" + obj.getString("message").toString());
                        if (obj.getString("message").equalsIgnoreCase("Candidate Created")) {
                            PrefHelper.getInstance().storeSharedValue("AppConstants.P_user_id", objPuser_id.getString("user_id"));
                            startActivity(new Intent(getApplicationContext(), CandidateEducation.class));

                            finish();

                        } else {
                            showErrorDialog(obj.getString("message"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }*/
        ///////////////////////////////////////////////////////
   public void getPersonalInfoApi(String admin_user_id, String first_name,/* String last_name, */String email, String phone,
                                  String dob, String etLookingJobTypes, String location, File adhar) {
       BuildRequestParms buildRequestParms = new BuildRequestParms();

       AppViewModel apiParamsInterface = ApiProductionS.getInstance(getApplicationContext()).provideService(AppViewModel.class);

       Log.i("@@11", "11");

       Observable<AttendanceModell> observable = null;
//


       File file = new File(imagePathUrlAdhar);
       Log.i("@@file", file.toString());
       Log.i("@@imagePa----", imagePathUrlAdhar.toString());

       RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
       MultipartBody.Part body = MultipartBody.Part.createFormData("aadhar", file.getName(), requestBody);




       System.out.println("candiateAdd====" + body);
       observable = apiParamsInterface.candiateAdd(
               buildRequestParms.getRequestBody(admin_user_id),
               buildRequestParms.getRequestBody(first_name),
             //  buildRequestParms.getRequestBody(last_name),
               buildRequestParms.getRequestBody(email),
               buildRequestParms.getRequestBody(phone),
               buildRequestParms.getRequestBody(dob),
               buildRequestParms.getRequestBody(etLookingJobTypes),
               buildRequestParms.getRequestBody(location),
               body


       );

       Log.i("@@candiateAdd", "candiateAdd");

       final ProgressDialog mProgressDialog = new ProgressDialog(PersonInfoActivity.this);
       mProgressDialog.show();
       mProgressDialog.setCancelable(false);
       mProgressDialog.setTitle("Please wait..");


       RxAPICallHelper.call(observable, new RxAPICallback<AttendanceModell>() {

           @Override
           public void onSuccess(AttendanceModell uploadFileResponse) {
               mProgressDialog.dismiss();
               System.out.println("@@AttendanceModell_1" + "AttendanceModell");
               //    Toast.makeText(getActivity(), uploadFileResponse.toString(), Toast.LENGTH_SHORT).show();
               System.out.println("@@AttendanceModell_2" + uploadFileResponse.toString());
               try {
                   if (uploadFileResponse.getMsg().equalsIgnoreCase("Email already exist")) {
                       Toast.makeText(getApplicationContext(), uploadFileResponse.getMsg(), Toast.LENGTH_SHORT).show();
                   } else {
                       System.out.println("@@AttendanceModell_2" + uploadFileResponse.getData().getUserID());
                       if (uploadFileResponse.getMsg().equalsIgnoreCase("Candidate Created")) {
                           PrefHelper.getInstance().storeSharedValue("AppConstants.P_user_id", uploadFileResponse.getData().getUserID());
                           startActivity(new Intent(getApplicationContext(), CandidateEducation.class));


                           finish();

                       } else {

                         showErrorDialog(uploadFileResponse.getMsg());
                       }
                   }
               } catch (Exception e) {
                   mProgressDialog.dismiss();
                   Toast.makeText(getApplicationContext(),"Email Already Exist!",Toast.LENGTH_SHORT).show();
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
        ////////////////////////////////////////////////////

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

        public void openDailogForImagePickOptionRegisterAdhar() {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = inflater.inflate(R.layout.layout_popup_image_option, null, false);
            final Dialog dialog = new Dialog(PersonInfoActivity.this);
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

             if (requestCode == IMAGE_REQUEST_CAMERA_register_adhar) {
                if (resultCode == RESULT_OK) {
                    new SaveCaputureImageTaskRegisterPlateAdhar().execute();
                }
            } else if (requestCode == IMAGE_REQUEST_GALLERY_register_adhar) {
                if (resultCode == RESULT_OK) {
                    final Uri selectedImage = data.getData();
//                 performCrop(selectedImage);
                  /*  if (checkPermissionREAD_EXTERNAL_STORAGE(PersonInfoActivity.this)) {
                        // do your stuff..
                        new SaveGalleryImageTaskRegisterPlateAdhar().execute(selectedImage);

                    }*/
                    try{
                        new SaveGalleryImageTaskRegisterPlateAdhar().execute(selectedImage);

                    }catch (Exception e){
                        e.printStackTrace();
                    }

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
                file1 = FileUtil.getFile(PersonInfoActivity.this);
                imagePathUrlAdhar = file1.getAbsolutePath();
                Log.i("@@FinallyGotSolution--",imagePathUrlAdhar);
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
                ProgressDialogUtil.showProgressDialog(PersonInfoActivity.this);
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
                ProgressDialogUtil.showProgressDialog(PersonInfoActivity.this);
            }

            @Override
            protected String doInBackground(Uri... params) {
                Uri selectedImage = params[0];
                String path = null;
                path = FileUtil.getPath(PersonInfoActivity.this, selectedImage);

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
                file1 = FileUtil.getFile(PersonInfoActivity.this);
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
    }