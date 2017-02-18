package com.dt.learning.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.dt.learning.R;
import com.dt.learning.Util.Util;
import com.dt.learning.aidl.IFirstAidlInterface;
import com.dt.learning.aidl.User;
import com.dt.learning.service.AIDLService;
import com.dt.learning.service.MessengerService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class IPCActivity extends AppCompatActivity {
    //IPC,Inter-Process Communication,进程间通信
    private static final int CREATE_USER=1;
    private TextView msgTv;
    private TextView aidlTv;
    private Messenger mService;
    private IFirstAidlInterface aidlInterface;

    private ServiceConnection aidlConn =new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            aidlInterface=IFirstAidlInterface.Stub.asInterface(service);
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {}
    };

    private ServiceConnection msgConn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService=new Messenger(service);
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {}
    };

    Messenger mMessenger=new Messenger(new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CREATE_USER:
                    Bundle bundle=msg.getData();
                    bundle.setClassLoader(User.class.getClassLoader());
                    User user=bundle.getParcelable("user");
                    msgTv.setText("name:"+user.getName()+",age:"+String.valueOf(user.getAge()));
                    break;
            }
            super.handleMessage(msg);
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        aidlTv=(TextView)findViewById(R.id.content_ipc_tv_aidl);
        Intent aidlService=new Intent(this, AIDLService.class);
        bindService(aidlService,aidlConn,BIND_AUTO_CREATE);

        msgTv=(TextView)findViewById(R.id.content_ipc_tv_msg);
        Intent msgerService=new Intent(this, MessengerService.class);
        bindService(msgerService,msgConn,BIND_AUTO_CREATE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener((view)->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    public void aidlClick(final View view){
        try {
            User user=aidlInterface.createUser(Util.randomLetter(5),Util.randomAge());
            aidlTv.setText("name:"+user.getName()+",");
            aidlTv.append("age:"+ String.valueOf(user.getAge()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void messengerClick(View view){
        Message msgFromClient=Message.obtain(null,CREATE_USER);
        Bundle bundle=new Bundle();
        bundle.putString("name",Util.randomLetter(5));
        bundle.putInt("age",Util.randomAge());
        msgFromClient.setData(bundle);
        msgFromClient.replyTo=mMessenger;
        if (mService!=null){
            try {
                mService.send(msgFromClient);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (aidlConn!=null) unbindService(aidlConn);
        if (msgConn!=null)  unbindService(msgConn);
    }
}
