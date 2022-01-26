package com.eroad.darkhand.eroad;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Wallet extends AppCompatActivity {

    private Button addmoney;
    private TextView balance;
    private DatabaseReference addmoneyref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);


        addmoney = (Button) findViewById(R.id.btn_addmoney);
        balance = (TextView) findViewById(R.id.textView6);



        addmoneyref= FirebaseDatabase.getInstance().getReference(AppManager.ADD_MONEY);




        addmoneyref.addValueEventListener(new ValueEventListener() {
            @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           for (DataSnapshot postsnapshot : dataSnapshot.getChildren()){
             AddmoneyAdapter addmoneyAdapter = dataSnapshot.getValue(AddmoneyAdapter.class);
            String amount = addmoneyAdapter.getAmount();
           balance.setText(amount);
           }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
        })  ;


        addmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Wallet.this, AddMoney.class);
                startActivity(intent);
            }
        });

    }

}
