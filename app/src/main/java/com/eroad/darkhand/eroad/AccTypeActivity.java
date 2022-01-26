package com.eroad.darkhand.eroad;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;



public class AccTypeActivity extends AppCompatActivity {

    private ListView acc_type;
    public static final String ACCOUNT_TYPE="account_type";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_type);
        //fields
        acc_type=(ListView)findViewById(R.id.acc_type);
        //listeners
        acc_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i=0;i<acc_type.getChildCount();i++){
                    if (position==i){
                        acc_type.getChildAt(i).setBackgroundColor(R.color.colorAccent);
                    }else {
                        acc_type.getChildAt(i).setBackgroundColor(android.R.color.transparent);
                    }

                    String accType= String.valueOf(acc_type.getItemAtPosition(position));
                    if (accType!=null){
                        Intent intent=new Intent(AccTypeActivity.this, DetailedInformation.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra(ACCOUNT_TYPE,accType);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }


}
