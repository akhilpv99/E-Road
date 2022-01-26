package com.eroad.darkhand.eroad;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

public class DpartnerList extends AppCompatActivity {
    DatabaseReference dpartnerlistRef;
    private Button back,refresh;
    ListView dpartnerList;
    List<RegisterAdapter> onlinedplist;



    @Override
    protected void onStart() {
        super.onStart();

        dpartnerlistRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                onlinedplist.clear();
                for (DataSnapshot ordersnapshot : dataSnapshot.getChildren()) {
                    RegisterAdapter registerAdapter = ordersnapshot.getValue(RegisterAdapter.class);
                    onlinedplist.add(registerAdapter);



                }
                OnlineDpartners adapter = new OnlineDpartners(DpartnerList.this,onlinedplist);
                dpartnerList.setAdapter(adapter);


                if(onlinedplist.size()==0)
                {
                    Toast.makeText(getApplicationContext(), "No Delivery Partners are available...", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_dpartner_list);


        dpartnerlistRef= FirebaseDatabase.getInstance().getReference(AppManager.APP_REFERENCE_PATH);
        back = (Button) findViewById(R.id.btnback);
        refresh = (Button) findViewById(R.id.btnrefresh);
        dpartnerList = (ListView) findViewById(R.id.dpartnerlistview);
        onlinedplist = new ArrayList<>();


        dpartnerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Intent myIntent = new Intent(view.getContext(), Paynow.class);
                    startActivityForResult(myIntent, 0);
                }
                else if (i == 1) {
                    Intent myIntent = new Intent(view.getContext(), Paynow.class);
                    startActivityForResult(myIntent, 0);
                }
                else if (i == 1) {
                    Intent myIntent = new Intent(view.getContext(), Paynow.class);
                    startActivityForResult(myIntent, 0);
                }
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DpartnerList.this, DriverActivity.class);
                startActivity(intent);
                finish();
            }
        });


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DpartnerList.this, DpartnerList.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

