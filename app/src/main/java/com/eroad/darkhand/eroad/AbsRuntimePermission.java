package com.eroad.darkhand.eroad;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseIntArray;
import android.view.View;

/**
 * Created by Arun Girivasan on 15-Dec-17.
 */

public abstract class AbsRuntimePermission extends AppCompatActivity {
    private SparseIntArray mErrorString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mErrorString=new SparseIntArray();
    }

    public abstract void onPermissionGranted(int requestCode);

    public void
    requestAppPermissions(final String[]requestedPermissions, final String stringId, final int requestCode){
        mErrorString.put(requestCode, Integer.parseInt(stringId));

        int permissionCheck= PackageManager.PERMISSION_GRANTED;
        boolean showRequestedPermissions=false;
        for (String permission: requestedPermissions){
            permissionCheck=permissionCheck + ContextCompat.checkSelfPermission(this,permission);
            showRequestedPermissions=showRequestedPermissions || ActivityCompat.shouldShowRequestPermissionRationale(this,permission);
        }

        if (permissionCheck!=PackageManager.PERMISSION_GRANTED){
            if (showRequestedPermissions){
                Snackbar.make(findViewById(android.R.id.content),stringId,Snackbar.LENGTH_INDEFINITE).setAction("GRANT", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(AbsRuntimePermission.this,requestedPermissions,requestCode);
                    }
                }).show();
            }else {
                ActivityCompat.requestPermissions(this,requestedPermissions,requestCode);
            }
        }else {
            onPermissionGranted(requestCode);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int permissionCheck=PackageManager.PERMISSION_GRANTED;
        for (int permission:grantResults){
            permissionCheck=permissionCheck + permission;
        }
        if ((grantResults.length > 0)&& PackageManager.PERMISSION_GRANTED == permissionCheck){
            onPermissionGranted(requestCode);
        }else {
            //Display message when some Danger permissions not accept
            Snackbar.make(findViewById(android.R.id.content),mErrorString.get(requestCode),
                    Snackbar.LENGTH_INDEFINITE).setAction("ENABLE", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent();
                    i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    i.setData(Uri.parse("package:" +getPackageName()));
                    i.addCategory(Intent.CATEGORY_DEFAULT);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(i);
                }
            }).show();
        }
    }
}
