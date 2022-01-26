package com.eroad.darkhand.eroad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

import java.io.File;

/**
 * Created by Arun Girivasan on 26-Nov-17.
 */

public class DB_controller extends SQLiteOpenHelper {
    private static String DB_PATH = "/data/data/com.eroad.darkhand.eroad/databases/";
    private static final int DATABASE_VERSION = 1;


    public DB_controller(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "eroadDB.db", null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS eroadTB (ACTYPE VARCHAR,NAME VARCHAR,PHNO VARCHAR,EMAIL VARCHAR);");
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS eroadTB");
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    public void logout() {
        SQLiteDatabase.deleteDatabase(new File(DB_PATH + "eroadDB"));
    }

    public void insert_data(String actype, String name, String phno, String email) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("ACTYPE", actype);
            contentValues.put("NAME", name);
            contentValues.put("PHNO", phno);
            contentValues.put("EMAIL", email);
            this.getWritableDatabase().insertOrThrow("eroadTB", "", contentValues);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    public void getData(TextView usrname, TextView phno, TextView actype) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM eroadTB", null);

            while (cursor.moveToNext()) {
                String name = cursor.getString(1);
                String ph = cursor.getString(2);
                String acctype = cursor.getString(0);

                byte[] img = cursor.getBlob(4);
                if (name != null && ph != null && acctype != null && img != null) {
                    usrname.setText(name);
                    phno.setText(ph);
                    actype.setText(acctype);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean databaseEmpty() {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM eroadTB", null);
        if (cursor.moveToFirst()) {
            return false;
        } else {
            return true;
        }
    }

    public String getAccType() {
        String actype = null;
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM eroadTB", null);
        while (cursor.moveToNext()) {
            try {
                actype = cursor.getString(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return actype;
    }

    public String getPhone() {
        String phone = null;
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM eroadTB", null);
        while (cursor.moveToNext()) {
            try {
                phone = cursor.getString(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return phone;
    }

    public String getEmail() {
        String email = null;
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM eroadTB", null);
        while (cursor.moveToNext()) {
            try {
                email = cursor.getString(3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return email;
    }

    public String getName() {
        String name = null;
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM eroadTB", null);
        while (cursor.moveToNext()) {
            try {
                name = cursor.getString(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return name;
    }

    public Void deleteData() {
        Cursor cursor = this.getWritableDatabase().rawQuery("DELETE FROM eroadTB", null);
        return null;
    }
}


