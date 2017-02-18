package com.dt.learning.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dt.learning.R;
import com.dt.learning.Util.Util;
import com.dt.learning.aidl.User;
import com.dt.learning.netservice.Data;
import com.dt.learning.receiver.NetworkStateReceive;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

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
}
