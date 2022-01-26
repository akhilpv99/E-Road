package com.eroad.darkhand.eroad;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class AppManager extends AbsRuntimePermission {

    private FirebaseAuth mAuth;
    private boolean dialogShown=false;
    private DB_controller controller;
    private static final int REQUEST_PERMISSION=10;

    public static final String STORAGE_PATH_ONLINE_MECHANIC = "ONLINE_DPARTNER";
    public static final String STORAGE_PATH_ONLINE_DRIVER = "ONLINE_DRIVER";
    public static final String MECHANIC_CONTACT="dpartner_contact";
    public static final String DRIVER_CONTACT="driver_contact";
    public static final String CONTACT="contact";



    public static final String STORAGE_PATH_REQUEST_ACCEPTED_CLIENTS = "DPARTNER_UNDER_WORK";
    public static final String STORAGE_PATH_CLIENTLIST = "CLIENT_LIST";
    public static final String STORAGE_PATH_MECHANIC_IN_WORK = "ACCEPTED_CLIENTS";
    public static final String APP_REFERENCE_PATH="ONLINE DPARTNER";

    public static final String PAID_MONEY = "PAID_MONEY";

    public static final String REQUEST_STATUS ="REQUEST_STATUS";
    public static final String ADD_MONEY ="ADD_MONEY";
    public static final String APPROVED_STATUS="APPROVED_STATUS";
    public static final String USER_DETAILS_PATH= "USER_DETAILS";

    public static final String PLACED_ORDERS= "PLACED_ORDERS";
    public static final String ONLINE_DPARTNER="ONLINE_DPARTNER";


    public static final String NOTIFICATIONS="NOTIFICATIONS";
    public static final String NOTIFICATIONDATA="NOTIFICATION_DATA";
    public static final String WALLET="WALLET";
    public static final String PAYMENTS="PAYMENTS";
    public static final String TRANSACTIONS="TRANSACTIONS";
    public static final String LOCATIONTYPE="locationType";
    public static final String LATITUDE="latitude";
    public static final String LONGITUDE="longitude";
    public static final String PHONE="phone";
    public static final String NAME="name";

    public static final String EMAIL="email";
    public static final String CHATS="CHATS";
    public static final String ACCTYPE="acctype";
    public static final String CHAT_TITLE ="chat_title";
    public static final String REQUESTSTOBANK="BANK_TRANSFER";
    public static final String REGISTRATIONRECORDS="REGISTRATION_RECORDS";
    public static final String BYCASHPAYMENTS="BY_CASH_PAYMENTS";
    public static final String[] nearbyPlaces = {
            "airport", "atm", "bank", "bar", "bus_station", "car_dealer", "car_rental", "car_wash", "church", "clothing_store", "doctor",
            "electrician", "electronics_store", "fire_station", "gas_station", "hardware_store", "hindu_temple", "home_goods_store",
            "hospital", "insurance_agency", "jewelry_store", "local_government_office", "lodging", "meal_delivery", "locksmith",
            "mosque", "movie_theater", "night_club", "museum", "park", "parking", "pharmacy", "physiotherapist", "police", "post_office",
            "restaurant", "school", "shopping_mall", "shoe_store", "stadium", "store", "taxi_stand", "train_station", "transit_station",
            "university", "zoo"};
    public static final String[] mapType = {"Normal", "Satellite", "Terrain"};
    public static final int REQUEST_CODE = 1234;
    public DatabaseReference approvedStatusRef;
    private String status=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_manager);


        //firebase
        mAuth = FirebaseAuth.getInstance();
        //sqlite
        controller =new DB_controller(this,"",null,1);
        approvedStatusRef= FirebaseDatabase.getInstance().getReference(APPROVED_STATUS);


        //permission listener
        Timer permissionListener=new Timer();
        permissionListener.schedule(new TimerTask() {
            @Override
            public void run() {
                requestAppPermissions(new String[]{
                                android.Manifest.permission.ACCESS_FINE_LOCATION,
                                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                android.Manifest.permission.CALL_PHONE},
                        String.valueOf(R.string.msg_enable_permission),REQUEST_PERMISSION);
            }
        },0,1000);

    }

    @Override
    public void onPermissionGranted(int requestCode) {

    }

    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null){
            Intent intent=new Intent(AppManager.this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else {
            //check registered account type in phone database
            String actype = controller.getAccType();
            if (actype != null) {
                if (actype.equals("Driver")) {
                    Intent intent = new Intent(this, DriverActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else if (actype.equals("Delivery Partner")){
                    String ph=controller.getPhone();
                    gotoHome();

                    approvedStatusRef.child(ph).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }else{
                Intent intent = new Intent(this, AccTypeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                Toast.makeText(AppManager.this,"You need to complete registration first", Toast.LENGTH_LONG).show();
            }
        }

        startNetworkListener();

    }

    private void gotoHome(){
        Intent intent = new Intent(AppManager.this, FDPActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void startNetworkListener() {
        Timer networkListener=new Timer();
        networkListener.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isConnected(AppManager.this)){
                    if (!dialogShown){
                        Intent intent=new Intent(AppManager.this,NoNetwork.class);
                        startActivity(intent);
                        dialogShown=true;
                    }
                }else {
                    if (dialogShown){
                        Intent intent = new Intent("finish_activity");
                        sendBroadcast(intent);
                        dialogShown=false;
                    }
                }
            }
        },0,1000);
    }

    public boolean isConnected(Context context){
        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        if (networkInfo!=null && networkInfo.isConnectedOrConnecting()){
            android.net.NetworkInfo wifi=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile!=null && mobile.isConnectedOrConnecting()) || (wifi!=null && wifi.isConnectedOrConnecting())){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }
}
