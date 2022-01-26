package com.eroad.darkhand.eroad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyOrders extends Activity implements OnItemClickListener
{
    /** Called when the activity is first created. */

    ListView lview;
    List<Orders> ordersList;
    ListViewAdapter lviewAdapter;
    DatabaseReference myordersRef;
    private Button button;


    @Override
    protected void onStart() {
        super.onStart();

        myordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ordersList.clear();
                for (DataSnapshot ordersnapshot : dataSnapshot.getChildren()){
                    Orders orders = ordersnapshot.getValue(Orders.class);
                    ordersList.add(orders);
                }
                orderlist adapter = new orderlist(MyOrders.this,ordersList);
                lview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        ordersList = new ArrayList<>();
        lview = (ListView) findViewById(R.id.listView2);
        myordersRef= FirebaseDatabase.getInstance().getReference(AppManager.PLACED_ORDERS);


        button = (Button) findViewById(R.id.backorders);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyOrders.this, DriverActivity.class));
                finish();
            }
        });


    }

    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
    }
}