package com.eroad.darkhand.eroad;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Paynow extends AppCompatActivity {

    private Button paynow,cod,gpay,paytm;
    private DatabaseReference addmoneyref;
    private DatabaseReference paidref;
    private TextView bal;
    private EditText paidmon,payeenme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paynow);

        paynow = (Button) findViewById(R.id.walletpay);
        cod = (Button) findViewById(R.id.codpay);
        gpay = (Button) findViewById(R.id.googlepay);
        paytm = (Button) findViewById(R.id.paytm);
        paidmon = (EditText) findViewById(R.id.payment);
        payeenme = (EditText) findViewById(R.id.paymentname);
        bal = (TextView) findViewById(R.id.textView6);
        addmoneyref= FirebaseDatabase.getInstance().getReference(AppManager.ADD_MONEY);
        paidref= FirebaseDatabase.getInstance().getReference(AppManager.PAID_MONEY);



        paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Intent.ACTION_MAIN);
                PackageManager manager = getPackageManager();
                i= manager.getLaunchIntentForPackage("net.one97.paytm");
                i.addCategory(Intent.CATEGORY_LAUNCHER);
                startActivity(i);
            }
        });

        gpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Intent.ACTION_MAIN);
                PackageManager manager = getPackageManager();
                i= manager.getLaunchIntentForPackage("com.google.android.apps.nbu.paisa.user");
                i.addCategory(Intent.CATEGORY_LAUNCHER);
                startActivity(i);
            }
        });


        cod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Paynow.this,DriverActivity.class);
                startActivity(intent);
                finish();
            }
        });

        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                paidmoney();
                Intent intent = new Intent(Paynow.this,DriverActivity.class);
                startActivity(intent);
                finish();
            }
        });



        addmoneyref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postsnapshot : dataSnapshot.getChildren()){
                    AddmoneyAdapter addmoneyAdapter = dataSnapshot.getValue(AddmoneyAdapter.class);
                    String amount = addmoneyAdapter.getAmount();

                    bal.setText(amount);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void paidmoney(){
        String amountt = paidmon.getText().toString().trim();
        String paymentnme=payeenme.getText().toString().trim();
        //String cvvno = cvv.getText().toString().trim();
        //String expmonth = expmm.getText().toString().trim();
        //String expyear = expyy.getText().toString().trim();

        if(!TextUtils.isEmpty(amountt)){
            String oid = paidref.push().getKey();
            PaidmoneyAdapter paidmoneyAdapter = new PaidmoneyAdapter(amountt,paymentnme);
            paidref.child(oid).setValue(paidmoneyAdapter);



            Toast.makeText(this , "Amount Paid", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, DriverActivity.class));



        }else{
            Toast.makeText(this, "Enter Amount to be Paid" , Toast.LENGTH_LONG).show();
        }
    }
}
