package com.dt.learning.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.dt.learning.R;

public class ProviderActivity extends AppCompatActivity {
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        tv = (TextView)findViewById(R.id.provider_contacts);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){
            queryContacts();
        }else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1){
            if (permissions[0].equals(Manifest.permission.READ_CONTACTS) && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                queryContacts();
            }
        }
    }

    public void queryContacts(){
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,new String[]{ContactsContract.Contacts.DISPLAY_NAME},null,null,null);
        while (cursor.moveToNext()){
            tv.append(cursor.getString(0)+"\n");
        }
        cursor.close();
    }
}
