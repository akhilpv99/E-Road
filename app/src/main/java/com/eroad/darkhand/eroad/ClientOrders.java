package com.eroad.darkhand.eroad;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClientOrders extends AppCompatActivity {

    ListView lview;
    DatabaseReference myordersRef;
    List<Orders> clientordersList;
    private Button button;

    @Override
    protected void onStart() {
        super.onStart();

        myordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ordersnapshot : dataSnapshot.getChildren()){
                    Orders orders = ordersnapshot.getValue(Orders.class);
                    clientordersList.add(orders);
                }
                orderlist adapter = new orderlist(ClientOrders.this,clientordersList);
                lview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_orders);
        clientordersList = new ArrayList<>();
        lview = (ListView) findViewById(R.id.listView22);
        myordersRef= FirebaseDatabase.getInstance().getReference(AppManager.PLACED_ORDERS);


        button = (Button)  findViewById(R.id.backordersFDP);



        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Intent myIntent = new Intent(view.getContext(), DisplayActivity.class);
                    startActivityForResult(myIntent, 0);
                }
            }
        });






        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientOrders.this, FDPActivity.class);
                startActivity(intent);
            }
        });


    }

}
