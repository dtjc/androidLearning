package com.dt.learning.activities;

import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.dt.learning.R;

public class StoragePathActivity extends AppCompatActivity {
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_path);
        tv= (TextView) findViewById(R.id.env_get_data_dir);
        setText(Environment.getDataDirectory().getPath());
        tv = (TextView)findViewById(R.id.env_get_download_cache_dir);
        setText(Environment.getDownloadCacheDirectory().getPath());
        tv = (TextView)findViewById(R.id.env_get_root_dir);
        setText(Environment.getRootDirectory().getPath());
        tv = (TextView)findViewById(R.id.env_get_ext_storage_dir);
        setText(Environment.getExternalStorageDirectory().getPath());
        tv = (TextView)findViewById(R.id.env_get_ext_storage_pub);
        setText(Environment.getExternalStoragePublicDirectory("").getPath());
        tv = (TextView)findViewById(R.id.context_get_ext_cache_dir);
        setText(getExternalCacheDir().getPath());
        tv = (TextView)findViewById(R.id.context_get_ext_file_dir);
        setText(getExternalFilesDir("").getPath());
        tv = (TextView)findViewById(R.id.context_get_cache_dir);
        setText(getCacheDir().getPath());
        tv = (TextView)findViewById(R.id.context_get_file_dir);
        setText(getFilesDir().getPath());
    }

    public void setText(String txt){
        txt = tv.getText()+txt;
        tv.setText(txt);
    }
}
