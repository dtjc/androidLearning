package com.dt.learning.activities;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dt.learning.R;
import com.dt.learning.Util.TestEvent;
import com.dt.learning.Util.Util;
import com.dt.learning.aidl.IFirstAidlInterface;
import com.dt.learning.aidl.User;
import com.dt.learning.service.AIDLService;
import com.dt.learning.service.MessengerService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class IPCActivity extends AppCompatActivity {
    //IPC,Inter-Process Communication,进程间通信
    private static final int CREATE_USER=1;
    private TextView msgTv;
    private TextView aidlTv;
    private Messenger mService;
    private IFirstAidlInterface aidlInterface;

    private ServiceConnection aidlConn =new ServiceConnection() {
        //当Binder死亡时，系统回调binderDied，用于重新连接
        private IBinder.DeathRecipient recipient=new IBinder.DeathRecipient() {
            @Override
            public void binderDied() {
                Log.e("ServiceConnection","binderDied()");
                if (aidlInterface==null) return;
                aidlInterface.asBinder().unlinkToDeath(recipient,0);
                aidlInterface=null;
                Intent aidlService=new Intent(IPCActivity.this, AIDLService.class);
                bindService(aidlService,aidlConn,BIND_AUTO_CREATE);
            }
        };

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("ServiceConnection","onServiceConnected()");
            aidlInterface=IFirstAidlInterface.Stub.asInterface(service);
            //设置死亡代理
            try {
                service.linkToDeath(recipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            //亦可在此处重连，区别在于onServiceDisconnected在客户端的UI线程中被回调，
            // 而binderDied在客户端的binder线程池中被回调
        }
    };

    private ServiceConnection msgConn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService=new Messenger(service);
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {}
    };

    @SuppressLint("HandlerLeak")
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
        EventBus.getDefault().register(this);

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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    @Subscribe
    public void handleEvent(TestEvent event){
        Log.e("IPCActivity",event.msg);
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
//        msgFromClient.setTarget();
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
        EventBus.getDefault().unregister(this);
        if (aidlConn!=null) unbindService(aidlConn);
        if (msgConn!=null)  unbindService(msgConn);
    }
}
