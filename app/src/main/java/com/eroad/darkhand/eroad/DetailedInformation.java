package com.eroad.darkhand.eroad;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailedInformation extends AppCompatActivity {

    private String accountType, filename,foldername;
    private EditText DpartnerName, DpartnerPhone, DpartnerMail;
    private EditText DriverName, DriverPhone, DriverMail;
    private Button btnSubmit;
    private ViewGroup userRegLayout, mechanicRegLayout;
    //firebase
    private FirebaseAuth mAuth;
    private DatabaseReference onlinestat, userDetailsRef,appReferenceRef,approvedStatusRef;
    //SQLite declaration
    private DB_controller controller;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_information);
        //fields
        DriverName = (EditText) findViewById(R.id.DriverName);
        DriverMail = (EditText) findViewById(R.id.DriverMail);
        DriverPhone = (EditText) findViewById(R.id.DriverPhone);
        DpartnerName = (EditText) findViewById(R.id.DpartnerName);
        DpartnerMail = (EditText) findViewById(R.id.DpartnerMail);
        DpartnerPhone = (EditText) findViewById(R.id.DpartnerPhone);

        userRegLayout = (ViewGroup) findViewById(R.id.userRegLayout);
        mechanicRegLayout = (ViewGroup) findViewById(R.id.mechanicRegLayout);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        progressDialog = new ProgressDialog(DetailedInformation.this);

        userRegLayout.setVisibility(View.INVISIBLE);
        mechanicRegLayout.setVisibility(View.INVISIBLE);
        btnSubmit.setEnabled(true);
        //read data from previous activity
        Intent intent = getIntent();
        accountType = intent.getStringExtra(AccTypeActivity.ACCOUNT_TYPE);
        if (accountType.equals("Driver")){
            userRegLayout.setVisibility(View.VISIBLE);
            mechanicRegLayout.setVisibility(View.INVISIBLE);
        }else if (accountType.equals("Delivery Partner")){
            userRegLayout.setVisibility(View.INVISIBLE);
            mechanicRegLayout.setVisibility(View.VISIBLE);
        }
        //firebase
        mAuth = FirebaseAuth.getInstance();
        appReferenceRef=FirebaseDatabase.getInstance().getReference(AppManager.APP_REFERENCE_PATH);
        onlinestat = FirebaseDatabase.getInstance().getReference(AppManager.ONLINE_DPARTNER);
        userDetailsRef = FirebaseDatabase.getInstance().getReference(AppManager.USER_DETAILS_PATH);
        approvedStatusRef= FirebaseDatabase.getInstance().getReference(AppManager.USER_DETAILS_PATH);
        //sqlite
        controller = new DB_controller(this, "", null, 1);

        //listeners

        //button submit click
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputsReady(accountType)) {

                    if (isValidEmailID(accountType)){

                        btnSubmit.setEnabled(false);

                        if (accountType.equals("Driver")) {
                            foldername = DriverPhone.getText().toString();
                        } else if (accountType.equals("Delivery Partner")) {
                            foldername = DpartnerPhone.getText().toString();
                        }
                        filename=mAuth.getUid();

                        progressDialog.setTitle("Please wait...");
                        progressDialog.setMessage("Uploading...");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        registeruser(accountType);


                    /*

                    //upload original image
                    ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            //display toast succes message

                            //Toast.makeText(getApplicationContext(), R.string.image_uploaded, Toast.LENGTH_SHORT).show();
                            ImageUploadAdapter imageUpload = new ImageUploadAdapter(filename, taskSnapshot.getDownloadUrl().toString());
                            //save message info in to firebase database

                            //String uploadId = ImageDatabaseRef.push().getKey();
                            ImageDatabaseRef.child(foldername).setValue(imageUpload);
                            //profile uploading task completed

                            //uploading text data
                            registeruser(accountType);

                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    btnSubmit.setEnabled(true);
                                    //dismiss progressDialog when error
                                    progressDialog.hide();
                                    //display toast error message
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                                    //show upload progress
                                    double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                    progressDialog.setMessage("Uploaded" + " " + (int) progress + "%");

                                }
                            });
                    */

                    }else {
                        if (accountType.equals("Driver")){
                            DriverMail.setError("Invalid Email Address!");
                        }else
                            DpartnerMail.setError("Invalid Email Address!");
                    }
                }
            }
        });


    }

    private boolean isValidEmailID(String accountType) {
        if (accountType.equals("Driver")) {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(DriverMail.getText().toString()).matches();
        }else
            return android.util.Patterns.EMAIL_ADDRESS.matcher(DpartnerMail.getText().toString()).matches();
    }

    private void registeruser(String accountType) {
        //progressDialog.setMessage("This will not take a long time.Please wait..");
        if (accountType.equals("Driver")) {
            uploadRegistrationDetails(accountType);
            insertRegistrationDetails(accountType);
        } else if (accountType.equals("Delivery Partner")) {
            uploadRegistrationDetails(accountType);
            insertRegistrationDetails(accountType);
        }
    }

    private void insertRegistrationDetails(String accountType) {
        if (accountType.equals("Driver")){
            String name=DriverName.getText().toString().toUpperCase();
            String mail=DriverMail.getText().toString();
            String ph_no= DriverPhone.getText().toString();

            try {
                controller.insert_data(accountType,name,ph_no,mail);
            }catch (SQLiteException e){
                // e.printStackTrace();
                Toast.makeText(this, e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }else if (accountType.equals("Delivery Partner")){
            String name=DpartnerName.getText().toString().toUpperCase();
            String mail=DpartnerMail.getText().toString();
            String ph_no=DpartnerPhone.getText().toString();





            try {
                controller.insert_data(accountType,name,ph_no,mail);
            }catch (SQLiteException e){
                // e.printStackTrace();
                Toast.makeText(this, e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
    }

    private void uploadRegistrationDetails(final String accountType) {
        if (accountType.equals("Driver")){
            final String name=DriverName.getText().toString().toUpperCase();
            final String mail=DriverMail.getText().toString();
            final String ph_no=DriverPhone.getText().toString();

            RegisterAdapter registerAdapter =new RegisterAdapter(accountType,name,ph_no,mail);
            userDetailsRef.child(ph_no).setValue(registerAdapter)
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            btnSubmit.setEnabled(true);
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.hide();
                        }
                    })

                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //insert data to firebase for app
                           // createAppReference(accountType,name,mail,ph_no);
                            Intent intent = new Intent(DetailedInformation.this, AppManager.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            finish();
                            startActivity(intent);
                        }
                    });
        }else if (accountType.equals("Delivery Partner")){
            final String name=DpartnerName.getText().toString().toUpperCase();
            final String mail=DpartnerMail.getText().toString();
            final String ph_no=DpartnerPhone.getText().toString();


            RegisterAdapter registerAdapter =new RegisterAdapter(accountType,name,ph_no,mail);

            userDetailsRef.child(ph_no).setValue(registerAdapter)

                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //insert data to firebase for app
                            createAppReference(accountType,name,mail,ph_no);

                           // ApproveAdapter adapter=new ApproveAdapter("online");
                            //userDetailsRef.child(ph_no).child("state").setValue(adapter);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            btnSubmit.setEnabled(true);
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.hide();
                        }
                    });
        }
    }

    private void createAppReference(String accountType, String name, String mail, String ph_no) {
        String uid=mAuth.getCurrentUser().getUid();
        RegisterAdapter registerAdapter =new RegisterAdapter(accountType,name,ph_no,mail);
        appReferenceRef.child(uid).setValue(registerAdapter)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        btnSubmit.setEnabled(true);
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.hide();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Registration Completed Successfully", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        Intent intent = new Intent(DetailedInformation.this, AppManager.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    private void createAppReference(String accountType, String name, String mail, String ph_no, String shop, String place) {
        String uid=mAuth.getCurrentUser().getUid();
        RegisterAdapter registerAdapter =new RegisterAdapter(accountType,name,ph_no,mail,shop,place);
        appReferenceRef.child(uid).setValue(registerAdapter)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        btnSubmit.setEnabled(true);
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.hide();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Registration Completed Successfully", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        Intent intent = new Intent(DetailedInformation.this, AppManager.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
                        startActivity(intent);
                    }
                });
    }

    private boolean inputsReady(String accountType) {
        if (accountType.equals("Driver")) {
            if (DriverName.getText().toString().matches("")){
                Toast.makeText(getApplicationContext(), R.string.warning_no_name, Toast.LENGTH_LONG).show();
                DriverName.requestFocus();
                return false;
            }else if (DriverPhone.getText().toString().matches("")){
                Toast.makeText(getApplicationContext(), R.string.warning_no_phoneNumber, Toast.LENGTH_LONG).show();
                DriverPhone.requestFocus();
                return false;
            }else if (DriverPhone.getText().toString().length() < 10) {
                Toast.makeText(getApplicationContext(), R.string.warning_error_in_phoneNumber, Toast.LENGTH_SHORT).show();
                DriverPhone.requestFocus();
                return false;
            }else if (DriverMail.getText().toString().matches("")){
                Toast.makeText(getApplicationContext(), R.string.warning_no_email, Toast.LENGTH_LONG).show();
                DriverMail.requestFocus();
                return false;
            }
        }else if (accountType.equals("Delivery Partner")) {
            if (DpartnerName.getText().toString().matches("")){
                Toast.makeText(getApplicationContext(), R.string.warning_no_name, Toast.LENGTH_LONG).show();
                DpartnerName.requestFocus();
                return false;
            }else if (DpartnerPhone.getText().toString().matches("")){
                Toast.makeText(getApplicationContext(), R.string.warning_no_phoneNumber, Toast.LENGTH_LONG).show();
                DpartnerPhone.requestFocus();
                return false;
            }else if (DpartnerPhone.getText().toString().length() < 10) {
                Toast.makeText(getApplicationContext(),  R.string.warning_error_in_phoneNumber, Toast.LENGTH_SHORT).show();
            }else if (DpartnerMail.getText().toString().matches("")){
                Toast.makeText(getApplicationContext(),  R.string.warning_no_email, Toast.LENGTH_LONG).show();
                DpartnerMail.requestFocus();
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==AppManager.REQUEST_CODE && resultCode==RESULT_OK && data!=null && data.getData() !=null){
            Uri uri=data.getData();

            //crop

        }


    }

}

    /*public String getImageExt(Uri uri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap= MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }*/


