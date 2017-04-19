package com.dt.learning.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    public void aarTestClick(View view){
        Intent intent = new Intent(this,com.dt.dtlib.DtlibMainActivity.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            String name = data.getStringExtra("btnName");
            Button btn =(Button) findViewById(R.id.activity_main_btn_aar_test);
            btn.setText(name);
        }
    }
}
