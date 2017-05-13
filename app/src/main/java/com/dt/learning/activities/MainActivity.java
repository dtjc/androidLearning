package com.dt.learning.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.dt.learning.R;
import com.dt.learning.receiver.NetworkStateReceive;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private NetworkStateReceive networkStateReceive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkStateReceive=new NetworkStateReceive();
        registerReceiver(networkStateReceive,intentFilter);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(networkStateReceive);
    }

    public void imageHandleClick(View view){
        startActivity(new Intent(this,ImageHandleActivity.class));
    }

    public void aidlLearnClick(View view){
        startActivity(new Intent(this,IPCActivity.class));
    }

    public void rerxClick(View view){
        startActivity(new Intent(this,NetworkActivity.class));
    }

    public void socketClick(View view){
        startActivity(new Intent(this,SocketActivity.class));
    }

    public void drawableClick(View view){
        startActivity(new Intent(this,DrawableActivity.class));
    }

    public void animationClick(View view){
        startActivity(new Intent(this,AnimationActivity.class));
    }

    public void filePathClick(View view){
        startActivity(new Intent(this,StoragePathActivity.class));
    }

    public void aarTestClick(View view){
        Intent intent = new Intent(this,com.dt.dtlib.DtlibMainActivity.class);
        startActivityForResult(intent,1);
    }

    public void takePhoto(View view){
        File file = new File(getExternalCacheDir(),"images");
//        File file = new File(getCacheDir(),"pics");
        if (!file.exists()) file.mkdirs();
        File image = new File(file,"pic.jpg");
        Uri uri = FileProvider.getUriForFile(this,"com.dt.learning.fileprovider",image);
        Log.e(TAG, "takePhoto:uri "+uri.toString() );
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
//            if ()
            String name = data.getStringExtra("btnName");
            Button btn =(Button) findViewById(R.id.activity_main_btn_aar_test);
            btn.setText(name);
        }
    }
}
