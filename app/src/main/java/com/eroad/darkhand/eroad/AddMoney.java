package com.eroad.darkhand.eroad;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AddMoney extends AppCompatActivity {

    private Button add;
    private DatabaseReference addmoneyref;
    private EditText amount,cardnumber,cvv,expmm,expyy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);

        add = (Button) findViewById(R.id.addmoney);
        amount =(EditText) findViewById(R.id.amount);
        cardnumber =(EditText) findViewById(R.id.cardno);
        cvv =(EditText) findViewById(R.id.cvv);
        expmm =(EditText) findViewById(R.id.expdatemonth);
        expyy =(EditText) findViewById(R.id.expdateyear);


        addmoneyref= FirebaseDatabase.getInstance().getReference(AppManager.ADD_MONEY);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addmoney();
            }
        });



    }

    private void addmoney(){
        String amountt = amount.getText().toString().trim();
        //String cardno=cardnumber.getText().toString().trim();
        //String cvvno = cvv.getText().toString().trim();
        //String expmonth = expmm.getText().toString().trim();
        //String expyear = expyy.getText().toString().trim();

        if(!TextUtils.isEmpty(amountt)){
            AddmoneyAdapter addmoneyAdapter = new AddmoneyAdapter(amountt);
            addmoneyref.setValue(addmoneyAdapter);

            Toast.makeText(this , "Amount Added", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, Wallet.class));



        }else{
            Toast.makeText(this, "Enter Card Details" , Toast.LENGTH_LONG).show();
        }
    }

}
