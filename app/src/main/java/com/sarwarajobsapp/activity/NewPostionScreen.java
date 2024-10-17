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
import com.sarwarajobsapp.modelattend.CanddiateAttendanceModell;
import com.sarwarajobsapp.modelattend.NewPostionExperience;
import com.sarwarajobsapp.util.DbBitmapUtility;
import com.sarwarajobsapp.util.FileUtil;
import com.sarwarajobsapp.util.FileUtilsss;
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
import java.util.Locale;
import java.util.Map;

import Communication.BuildRequestParms;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

//public class NewPostionScreen extends Fragment implements View.OnClickListener {
public class NewPostionScreen extends BaseActivity implements View.OnClickListener {

    public static final String TAG = "NewPostionScreen";
    TextInputLayout txtInputAmount, txtInputPostion, txtInputTitle, txtInputCompanyName, txtInputLocation, txtInputStartDate, txtInputEODDate, txtInputJOB;
    TextView verify_btn, customeToolbartext, txtADDImage;
    Spinner txtSPinnerEmployeerType;
    EditText etAmount, txtEmployeeType, txtPosition, txtTitle, txtCompanyName, txtLocation, etStartDate, etEODDate, txtJobRpleDescritpion;
    Calendar bookDateAndTime;
    DatePickerDialog toDatePickerDialog;
    DatePickerDialog toDatePickerDialogEnd;
    LinearLayout llAccount;
    //String reformattedStr,EndreformattedStr;
    Uri imageFeatureUri;
    public static final int IMAGE_REQUEST_GALLERY_register_adhar = 325;
    public static final int IMAGE_REQUEST_CAMERA_register_adhar = 326;
    Uri source;
    EditText etImageUSer;
    String imagePathUrlAdhar;
    File file1;
    Spinner spinPaymentMethod;
    String selectedPayment;

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
        customeToolbartext = findViewById(R.id.customeToolbartext);
        txtInputAmount = findViewById(R.id.txtInputAmount);
        etAmount = findViewById(R.id.etAmount);
        spinPaymentMethod = findViewById(R.id.spinPaymentMethod);
        etImageUSer = findViewById(R.id.etImageUSer);
        txtADDImage = findViewById(R.id.txtADDImage);
        txtInputTitle = findViewById(R.id.txtInputTitle);
        txtInputCompanyName = findViewById(R.id.txtInputCompanyName);
        txtInputPostion = findViewById(R.id.txtInputPostion);
        txtPosition = findViewById(R.id.txtPosition);
        txtInputLocation = findViewById(R.id.txtInputLocation);
        txtInputStartDate = findViewById(R.id.txtInputStartDate);
        txtInputEODDate = findViewById(R.id.txtInputEODDate);
        txtInputJOB = findViewById(R.id.txtInputJOB);
        verify_btn = findViewById(R.id.verify_btn);
        txtTitle = findViewById(R.id.txtTitle);
        txtEmployeeType = findViewById(R.id.txtEmployeeType);
        txtCompanyName = findViewById(R.id.txtCompanyName);
        txtLocation = findViewById(R.id.txtLocation);
        etStartDate = findViewById(R.id.etStartDate);
        etEODDate = findViewById(R.id.etEODDate);
        txtJobRpleDescritpion = findViewById(R.id.txtJobRpleDescritpion);
        customeToolbartext.setText("Add Previous Experience");

/*        String[] paymentMethod = {"Cash", "Online", "Banking"};

        // Adapter for Spinner
        ArrayAdapter<String> adapters = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, paymentMethod);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_item);

        // Set adapter to Spinner
        spinPaymentMethod.setAdapter(adapters);

        // Set OnItemSelectedListener to Spinner
        spinPaymentMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get selected item
                selectedPayment = parent.getItemAtPosition(position).toString();
                Log.i("@@selectedPayment--", selectedPayment);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional: Handle the case when nothing is selected
            }
        });*/
        findViewById(R.id.goback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
              /*  Fragment fragment = new CandidateListActionaleActivity();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();*/
            }
        });
      /*  etStartDate.setOnClickListener(this);
        etEODDate.setOnClickListener(this);*/
        verify_btn.setOnClickListener(this);
        txtADDImage.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == txtADDImage) {
            openDailogForImagePickOptionRegisterAdhar();
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

          /*  try {

                reformattedStr = myFormat.format(myFormat.parse(etStartDate.getText().toString().trim()));
            } catch (ParseException e) {
                e.printStackTrace();
            }*/
       /*     try {
                String dateString=etStartDate.getText().toString().trim();

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                reformattedStr=sdf2.format(sdf.parse(dateString));
                Log.i("@@@----",""+sdf2.format(sdf.parse(dateString)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
*/
            SimpleDateFormat myFormatEnd = new SimpleDateFormat("yyyy-MM-dd");

          /*  try {

                EndreformattedStr = myFormatEnd.format(myFormatEnd.parse(etEODDate.getText().toString().trim()));
            } catch (ParseException e) {
                e.printStackTrace();
            }*/
       /*     try {
                String dateString2=etEODDate.getText().toString().trim();

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                EndreformattedStr=sdf2.format(sdf.parse(dateString2));
                Log.i("@@@----",""+sdf2.format(sdf.parse(dateString2)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
*/
            // System.out.println("EndreformattedStr====" +EndreformattedStr);

          /*  if (txtTitle.getText().toString().length() <= 0) {
                Toast.makeText(this,"Enter Title", Toast.LENGTH_SHORT).show();
                return;
            }*/
           /* if (txtEmployeeType.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter Employee Type", Toast.LENGTH_SHORT).show();

                return;
            }*/

           /* if (txtCompanyName.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter Company Name", Toast.LENGTH_SHORT).show();

                return;
            }*/
            if (txtPosition.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter Position", Toast.LENGTH_SHORT).show();

                return;
            }

           /* if (etStartDate.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter Start Date", Toast.LENGTH_SHORT).show();

                return;
            }

            if (etEODDate.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter Looking JobType", Toast.LENGTH_SHORT).show();

                return;
            }*/
            if (txtJobRpleDescritpion.getText().toString().length() <= 0) {
                Toast.makeText(this, "Enter Job Description", Toast.LENGTH_SHORT).show();

                return;
            } else {
                getPostionDataTypeApi(PrefHelper.getInstance().getSharedValue("AppConstants.P_user_id"),
                        txtCompanyName.getText().toString().trim(), txtPosition.getText().toString().trim(),
                        /*  reformattedStr, EndreformattedStr,*/ txtJobRpleDescritpion.getText().toString().trim()/*,file1*/);
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


    /*  public void getPostionDataTypeApi(String user_id, String company, String position, String started_at,
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
      }*/
    public void getPostionDataTypeApi(String user_id, String company, String position, /*String started_at,
                                    String ended_at,*/String description/*, File upload_file*/) {
        BuildRequestParms buildRequestParms = new BuildRequestParms();

        AppViewModel apiParamsInterface = ApiProductionS.getInstance(getApplicationContext()).provideService(AppViewModel.class);

        Log.i("@@11", "11");

        Observable<NewPostionExperience> observable = null;
//


        //  File file = new File(imagePathUrlAdhar);
        //  Log.i("@@file", file.toString());
        //  Log.i("@@NewPnExpeimagePa", imagePathUrlAdhar.toString());

        // RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        // MultipartBody.Part body = MultipartBody.Part.createFormData("upload_file", file.getName(), requestBody);


        // System.out.println("NewPostionExperience====" + body);
        observable = apiParamsInterface.candidateExperienceAdd(
                buildRequestParms.getRequestBody(user_id),
                buildRequestParms.getRequestBody(company),
                buildRequestParms.getRequestBody(position),
            /*  buildRequestParms.getRequestBody(started_at),
              buildRequestParms.getRequestBody(ended_at),*/
                buildRequestParms.getRequestBody(description)
                // body


        );

        Log.i("@@NewPostionExperience", "NewPostionExperience");

        final ProgressDialog mProgressDialog = new ProgressDialog(NewPostionScreen.this);
        mProgressDialog.show();
        mProgressDialog.setCancelable(false);
        mProgressDialog.setTitle("Please wait..");

        RxAPICallHelper.call(observable, new RxAPICallback<NewPostionExperience>() {

            @Override
            public void onSuccess(NewPostionExperience uploadFileResponse) {


                mProgressDialog.dismiss();
                System.out.println("@@newEXperienceModel" + "newEXperienceModel");
                //    Toast.makeText(getActivity(), uploadFileResponse.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("@@newEXperienceModel" + uploadFileResponse.toString());
                try {
                    if (uploadFileResponse.getMsg().equalsIgnoreCase("User Experience Added")) {
                        Toast.makeText(getApplicationContext(), uploadFileResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), CandidateListActionaleActivityConvert.class));
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

        toDatePickerDialog = new DatePickerDialog(NewPostionScreen.this,
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

        toDatePickerDialogEnd = new DatePickerDialog(NewPostionScreen.this,
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

    public void openDailogForImagePickOptionRegisterAdhar() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.layout_popup_image_option, null, false);
        final Dialog dialog = new Dialog(NewPostionScreen.this);
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
                // if (checkPermissionREAD_EXTERNAL_STORAGE(NewPostionScreen.this)) {
                // do your stuff..
                try {
                    new SaveGalleryImageTaskRegisterPlateAdhar().execute(selectedImage);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //}
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
            file1 = FileUtil.getFile(NewPostionScreen.this);
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
            ProgressDialogUtil.showProgressDialog(NewPostionScreen.this);
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

    class SaveGalleryImageTaskRegisterPlateAdhar extends AsyncTask<Uri, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressDialogUtil.showProgressDialog(NewPostionScreen.this);
        }

        @Override
        protected String doInBackground(Uri... params) {
            Uri selectedImage = params[0];
            String path = null;
            path = FileUtil.getPath(NewPostionScreen.this, selectedImage);

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
            file1 = FileUtil.getFile(NewPostionScreen.this);
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
                etImageUSer.setText(fileNameset.toString());

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