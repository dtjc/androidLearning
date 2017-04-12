package com.dt.learning.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.dt.learning.R;
import com.dt.learning.receiver.NetworkStateReceive;

public class MainActivity extends AppCompatActivity {

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
}
