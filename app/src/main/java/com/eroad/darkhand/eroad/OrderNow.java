package com.eroad.darkhand.eroad;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderNow extends AppCompatActivity {

    private Spinner f_type;
    private EditText fuelquantity;
    private EditText fullname;
    private EditText mobilenumber;
    private Button placeorder;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    private DatabaseReference placedordersRef;
    private DatabaseReference paidref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_now);









        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        fuelquantity= (EditText) findViewById(R.id.f_quantity);
        fullname = (EditText) findViewById(R.id.fullname);
        mobilenumber = (EditText) findViewById(R.id.mobilenumber);
        placeorder = (Button) findViewById(R.id.btn_placeorder);
        f_type = (Spinner) findViewById(R.id.f_type);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.f_type, android.R.layout.simple_spinner_item);
        f_type.setAdapter(adapter);


        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addorder();
            }
        });

        placedordersRef= FirebaseDatabase.getInstance().getReference(AppManager.PLACED_ORDERS);
        paidref = FirebaseDatabase.getInstance().getReference(AppManager.PAID_MONEY);





    }
    private void addorder(){
        String type = f_type.getSelectedItem().toString();
        String quantity = fuelquantity.getText().toString().trim();
        String fname=fullname.getText().toString().trim();
        String pnumber = mobilenumber.getText().toString().trim();

        if(!TextUtils.isEmpty(quantity)){
            String oid = placedordersRef.push().getKey();
            Orders orders = new Orders(type,quantity,fname,pnumber);
            placedordersRef.child(oid).setValue(orders);

            Toast.makeText(this , "Order Placed", Toast.LENGTH_LONG).show();

            startActivity(new Intent(this, DpartnerList.class));








        }else{
            Toast.makeText(this, "Enter quantity" , Toast.LENGTH_LONG).show();
        }
    }



}
