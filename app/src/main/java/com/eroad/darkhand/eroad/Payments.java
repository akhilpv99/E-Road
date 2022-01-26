package com.eroad.darkhand.eroad;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Payments extends AppCompatActivity {
    ListView PaymentList;
    List<PaidmoneyAdapter> paymentplist;
    private DatabaseReference paidref;
    private Button back,refresh;


    @Override
    protected void onStart() {
        super.onStart();

        paidref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                paymentplist.clear();
                for (DataSnapshot ordersnapshot : dataSnapshot.getChildren()) {
                    PaidmoneyAdapter paidmoneyAdapter = ordersnapshot.getValue(PaidmoneyAdapter.class);
                    paymentplist.add(paidmoneyAdapter);



                }
                PaymentsDone adapter = new PaymentsDone(Payments.this,paymentplist);
                PaymentList.setAdapter(adapter);


                if(paymentplist.size()==0)
                {
                    Toast.makeText(getApplicationContext(), "Payment History is not available...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);


        back = (Button) findViewById(R.id.btnback);
        refresh = (Button) findViewById(R.id.btnrefresh);
        PaymentList = (ListView) findViewById(R.id.paymentlistview);
        paymentplist = new ArrayList<>();

        paidref= FirebaseDatabase.getInstance().getReference(AppManager.PAID_MONEY);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Payments.this, FDPActivity.class);
                startActivity(intent);
                finish();
            }
        });


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Payments.this, Payments.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
