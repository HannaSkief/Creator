package com.example.ic2.quickReportActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ic2.R;
import com.example.ic2.adapter.ImagesRecyclerViewAdapter;
import com.example.ic2.async.AsyncTaskCallback;
import com.example.ic2.async.location.GetLocationsNameAsync;
import com.example.ic2.async.reportImage.GetReportImagesAsync;
import com.example.ic2.async.reportImage.HandleImageAsync;
import com.example.ic2.async.typeOfIncident.GetTypeOfIncidentNamesAsync;
import com.example.ic2.common.Common;
import com.example.ic2.common.CustomToast;
import com.example.ic2.common.PickImage;
import com.example.ic2.images.AddImage;
import com.example.ic2.images.DeleteImage;
import com.example.ic2.images.ImageHandler;
import com.example.ic2.model.QuickReportOption;
import com.example.ic2.model.Report;
import com.example.ic2.model.ReportImage;
import com.example.ic2.model.User;
import com.example.ic2.presenter.QuickReportPresenter;
import com.example.ic2.view.IQuickReportView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class QuickReportActivity extends AppCompatActivity implements IQuickReportView, ImagesRecyclerViewAdapter.OnImageClicked, View.OnFocusChangeListener {

    private static final int GALLERY = 12, CAMERA = 13, OPEN_CAMERA = 14, OPEN_GALLERY = 15;
    private FloatingActionButton fab_main, fabSend, fabSave, fabClear;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;
    TextView tvSend, tvSave, tvClear;
    Boolean isOpen = false;
    TextView tvReportType;
    RecyclerView rvImages;
    ImageView imgPickImage, imgReportIcon, imgBack;
    EditText etDate, etTime, etProjectReference, etDepartment, etDescribeOfIssue, etDoAboutIt;
    Spinner spLocation, spTypeOfIncident;
    private Uri cameraImageUri;
    private String imagePath;

    private List<ReportImage> imageList;
    private ImagesRecyclerViewAdapter rvAdapter;
    private QuickReportPresenter quickReportPresenter;
    private String reportType;
    private Report report;
    List<ImageHandler> imageHandlerList;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_report);
        getWindow().setBackgroundDrawableResource(R.drawable.layout_bg_style);

        initFabAnimation();
        initView();

        quickReportPresenter = new QuickReportPresenter(this, getApplicationContext());

        report = Common.selected_report;
        if (report == null) {
            report = new Report();
            reportType = getIntent().getExtras().getString("reportType");
        } else {
            getReport(report);
        }
        tvReportType.setText(reportType);
        setReportIcon();
        imageHandlerList = new ArrayList<>();
    }

    private void getReport(Report report) {

        QuickReportOption quickReportOption = new Gson().fromJson(report.getOptions(), QuickReportOption.class);
        etDate.setText(quickReportOption.getDate());
        etTime.setText(quickReportOption.getTime());
        etProjectReference.setText(quickReportOption.getProjectReference());
        etDescribeOfIssue.setText(quickReportOption.getDescribeOfIssue());
        etDepartment.setText(quickReportOption.getDepartment());
        etDoAboutIt.setText(quickReportOption.getDoAboutId());
        reportType = report.getType();

        new GetReportImagesAsync(report, this, new AsyncTaskCallback<List<ReportImage>>() {
            @Override
            public void handleResponse(List<ReportImage> response) {
                imageList.addAll(response);
                rvAdapter.notifyDataSetChanged();
            }

            @Override
            public void handleFault(Exception e) {

            }
        }).execute();

    }

    private void setReportIcon() {
        if (reportType.equalsIgnoreCase("Accident") || reportType.equalsIgnoreCase(getString(R.string.accident))) {
            imgReportIcon.setImageResource(R.drawable.ic_accident);
        } else if (reportType.equalsIgnoreCase("Non conformance") || reportType.equalsIgnoreCase(getString(R.string.non_conformance))) {
            imgReportIcon.setImageResource(R.drawable.ic_non_conformance);
        } else if (reportType.equalsIgnoreCase("Observation") || reportType.equalsIgnoreCase(getString(R.string.observation))) {
            imgReportIcon.setImageResource(R.drawable.ic_observ);
        } else if (reportType.equalsIgnoreCase("Near miss") || reportType.equalsIgnoreCase(getString(R.string.near_miss))) {
            imgReportIcon.setImageResource(R.drawable.ic_near_miss);
        } else if (reportType.equalsIgnoreCase("Improvement") || reportType.equalsIgnoreCase(getString(R.string.improvement))) {
            imgReportIcon.setImageResource(R.drawable.ic_improvement);
        } else if (reportType.equalsIgnoreCase("Prevention") || reportType.equalsIgnoreCase(getString(R.string.prevention))) {
            imgReportIcon.setImageResource(R.drawable.ic_prevention);
        }
    }

    private void initFabAnimation() {

        fab_main = findViewById(R.id.fab);
        fabSend = findViewById(R.id.fabSend);
        fabSave = findViewById(R.id.fabSave);
        fabClear = findViewById(R.id.fabClear);

        tvClear = findViewById(R.id.tvClear);
        tvSave = findViewById(R.id.tvSave);
        tvSend = findViewById(R.id.tvSend);

        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_clock);
        fab_anticlock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_anticlock);

        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpen) {
                    closeMainFab();
                } else {
                    openMainFab();
                }
            }
        });


        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                save();
            }
        });

        fabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send();
            }
        });

        fabClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });
    }

    private void openMainFab() {
        fabSave.startAnimation(fab_open);
        fabSend.startAnimation(fab_open);
        fabClear.startAnimation(fab_open);
        tvSend.startAnimation(fab_open);
        tvClear.startAnimation(fab_open);
        tvSave.startAnimation(fab_open);

        fab_main.startAnimation(fab_clock);
        fabSave.setClickable(true);
        fabSend.setClickable(true);
        fabClear.setClickable(true);
        isOpen = true;
    }

    private void closeMainFab() {
        fabSave.startAnimation(fab_close);
        fabSend.startAnimation(fab_close);
        fabClear.startAnimation(fab_close);
        tvSend.startAnimation(fab_close);
        tvClear.startAnimation(fab_close);
        tvSave.startAnimation(fab_close);
        fab_main.startAnimation(fab_anticlock);
        fabSave.setClickable(false);
        fabSend.setClickable(false);
        fabClear.setClickable(false);
        isOpen = false;
    }

    private void initView() {
        rvImages = findViewById(R.id.rvImages);
        rvImages.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        imageList = new ArrayList<>();
        rvAdapter = new ImagesRecyclerViewAdapter(imageList, getApplicationContext(), (ImagesRecyclerViewAdapter.OnImageClicked) this);
        rvImages.setAdapter(rvAdapter);

        tvReportType = findViewById(R.id.tvReportType);
        imgPickImage = findViewById(R.id.imgPickImage);
        imgPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

        etDate = findViewById(R.id.etDate);
        etTime = findViewById(R.id.etTime);
        initDateAndTime();

        etDepartment = findViewById(R.id.etDepartment);
        etProjectReference = findViewById(R.id.etProjectReference);
        etDescribeOfIssue = findViewById(R.id.etDescribeOfIssue);
        etDoAboutIt = findViewById(R.id.etDoAboutIt);

        spLocation = findViewById(R.id.spLocation);
        spTypeOfIncident = findViewById(R.id.spTypeOfIncident);

        etDepartment.setOnFocusChangeListener(this);
        etDoAboutIt.setOnFocusChangeListener(this);
        etDescribeOfIssue.setOnFocusChangeListener(this);
        etProjectReference.setOnFocusChangeListener(this);
        etTime.setOnFocusChangeListener(this);
        etDate.setOnFocusChangeListener(this);
        spTypeOfIncident.setOnFocusChangeListener(this);
        spLocation.setOnFocusChangeListener(this);

        imgReportIcon = findViewById(R.id.imgReportIcon);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initLocationSpinner();
        initTypeOfIncidentSpinner();
    }

    private void initTypeOfIncidentSpinner() {
        final List<String> typeOfIncidentList = new ArrayList<>();
        typeOfIncidentList.add("");

        new GetTypeOfIncidentNamesAsync(this, new AsyncTaskCallback<List<String>>() {
            @Override
            public void handleResponse(List<String> response) {
                typeOfIncidentList.addAll(response);
                ArrayAdapter adapter = new ArrayAdapter(QuickReportActivity.this, android.R.layout.simple_dropdown_item_1line, typeOfIncidentList);
                spTypeOfIncident.setAdapter(adapter);
                if (response.isEmpty())
                    Toast.makeText(QuickReportActivity.this, "Add some Type of incident from setting", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleFault(Exception e) {
                ArrayAdapter adapter = new ArrayAdapter(QuickReportActivity.this, android.R.layout.simple_dropdown_item_1line, typeOfIncidentList);
                spTypeOfIncident.setAdapter(adapter);
                Toast.makeText(QuickReportActivity.this, "Add some Type of incident from setting", Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }

    private void initLocationSpinner() {
        final List<String> locationList = new ArrayList<>();
        locationList.add("");
        new GetLocationsNameAsync(this, new AsyncTaskCallback<List<String>>() {
            @Override
            public void handleResponse(List<String> response) {
                locationList.addAll(response);
                ArrayAdapter adapter = new ArrayAdapter(QuickReportActivity.this, android.R.layout.simple_dropdown_item_1line, locationList);
                spLocation.setAdapter(adapter);
                if (response.isEmpty())
                    Toast.makeText(QuickReportActivity.this, "Add some locations from setting", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleFault(Exception e) {
                ArrayAdapter adapter = new ArrayAdapter(QuickReportActivity.this, android.R.layout.simple_dropdown_item_1line, locationList);
                spLocation.setAdapter(adapter);
                Toast.makeText(QuickReportActivity.this, "Add some locations from setting", Toast.LENGTH_SHORT).show();
            }
        }).execute();


    }

    private void initDateAndTime() {
        Date c = Calendar.getInstance().getTime();
        etDate.setText(new SimpleDateFormat("dd-MM-yyyy").format(c));
        etTime.setText(new SimpleDateFormat("kk:mm").format(c));
        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTime();
            }
        });
        etDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                updateDate();
            }
        });
    }

    private void updateTime() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etTime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    private void updateDate() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        etDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void pickImage() {
        new PickImage(this).show(new PickImage.IPickClick() {
            @Override
            public void onCameraClick() {
                pickImageFromCamera();
            }

            @Override
            public void onGalleryClick() {
                pickImageFromGallery();
            }
        });

    }

    private void pickImageFromCamera() {
        //check for permission
        if (ActivityCompat.checkSelfPermission(QuickReportActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(QuickReportActivity.this, new String[]{Manifest.permission.CAMERA}, OPEN_CAMERA);
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                photoFile = createImageFile();
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(QuickReportActivity.this,
                            getApplicationContext().getPackageName() + ".provider", photoFile);
                    cameraImageUri = photoURI;
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, CAMERA);
                }
            }

        }

    }

    private void pickImageFromGallery() {
        //check for permission
        if (ActivityCompat.checkSelfPermission(QuickReportActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(QuickReportActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, OPEN_GALLERY);
        } else {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, GALLERY);
        }

    }

    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES + "/temp");
        File imageFile = null;
        try {
            imageFile = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        imagePath = imageFile.getAbsolutePath();
        // Save a file: path for use with ACTION_VIEW intents
        return imageFile;
    }

    private void saveImage(Bitmap bitmap) {
        try {
            FileOutputStream fos = new FileOutputStream(createImageFile());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == OPEN_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImageFromCamera();
            }
        } else if (requestCode == OPEN_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImageFromGallery();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA) {
            if (resultCode == RESULT_OK) {
                Uri contentURI = cameraImageUri;
                // Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                ReportImage image = new ReportImage(imagePath);
                imageList.add(image);
                rvAdapter.notifyDataSetChanged();
                imageHandlerList.add(new AddImage(image));
            }

        } else if (requestCode == GALLERY) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Uri contentURI = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        saveImage(bitmap);
                        ReportImage image = new ReportImage(imagePath);
                        imageList.add(image);
                        rvAdapter.notifyDataSetChanged();
                        imageHandlerList.add(new AddImage(image));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void clear() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Clear all ?");
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                etDepartment.setText("");
                etDescribeOfIssue.setText("");
                etDoAboutIt.setText("");
                etProjectReference.setText("");

            }
        });
        dialog.setNegativeButton("Cancel", null);
        dialog.show();
    }

    private void save() {
        createReport();
        report.setStatus("saved");
        quickReportPresenter.save(report);

    }

    private void send() {
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        createReport();
        report.setStatus("send");
        Log.e("REPORT_INFO",new Gson().toJson(report));
        quickReportPresenter.send(report);
    }

    private void createReport() {
        report.setSrc_id(User.getCurrentUser().getId());
        report.setType(this.reportType);
        report.setCreatedAt(new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime()));
        report.setDate(etDate.getText().toString());
        report.setTime(etTime.getText().toString());
        report.setCompany_id(User.getCurrentUser().getCompany_id());

        QuickReportOption option = new QuickReportOption();
        option.setDate(etDate.getText().toString());
        option.setTime(etTime.getText().toString());
        option.setDepartment(etDepartment.getText().toString().trim());
        option.setDescribeOfIssue(etDescribeOfIssue.getText().toString().trim());
        option.setDoAboutId(etDoAboutIt.getText().toString().trim());
        option.setProjectReference(etProjectReference.getText().toString().trim());
        option.setLocation(spLocation.getSelectedItem().toString());
        option.setTypeOfIncident(spTypeOfIncident.getSelectedItem().toString());
        report.setOptions(new Gson().toJson(option));

    }

    @Override
    public void onSendSuccess() {
        progressDialog.dismiss();
        CustomToast.show("Report sended successfully",QuickReportActivity.this,Toast.LENGTH_SHORT);
        finish();
    }

    @Override
    public void onSaveSuccess(Report report) {
        this.report.setId(report.getId());
        new HandleImageAsync(imageHandlerList, report, getApplicationContext(), new AsyncTaskCallback<String>() {
            @Override
            public void handleResponse(String response) {
                CustomToast.show("Report saved successfully", QuickReportActivity.this, Toast.LENGTH_SHORT);
                finish();
            }

            @Override
            public void handleFault(Exception e) {

            }
        }).execute();

    }

    @Override
    public void OnError(String message) {
        progressDialog.dismiss();
        CustomToast.show(message,QuickReportActivity.this,Toast.LENGTH_SHORT);
    }

    @Override
    public void showImage(ReportImage reportImage) {
        File file = new File(reportImage.getPath());
        Uri photoURI = FileProvider.getUriForFile(QuickReportActivity.this,
                getApplicationContext().getPackageName() + ".provider", file);
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(photoURI, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }

    @Override
    public void deleteImage(final ReportImage reportImage) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getString(R.string.delete_this_image));
        dialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                imageHandlerList.add(new DeleteImage(reportImage));
                imageList.remove(reportImage);
                rvAdapter.notifyDataSetChanged();
            }
        });
        dialog.setNegativeButton(getString(R.string.cancel), null);
        dialog.show();

    }

    // close main fab on focus on edit text

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b) {
            if (isOpen) {
                closeMainFab();
            }
        }
    }
}
