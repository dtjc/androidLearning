package com.dt.learning.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.dt.learning.aidl.User;

public class MessengerService extends Service {
    private static final int CREATE_USER=1;
    private Messenger mMessenger=new Messenger(new Handler(){
        @Override
        public void handleMessage(Message msgFromClient) {
            Message msgToClient=Message.obtain(msgFromClient);
            switch (msgFromClient.what){
                case CREATE_USER:
                    msgToClient.what=CREATE_USER;
                    Bundle fromClient=msgFromClient.getData();
                    User user=new User();
                    user.setName(fromClient.getString("name"));
                    user.setAge(fromClient.getInt("age"));
                    Bundle toClient=new Bundle();
                    toClient.putParcelable("user",user);
                    msgToClient.setData(toClient);
                    try {
                        msgFromClient.replyTo.send(msgToClient);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;

            }
            super.handleMessage(msgFromClient);
        }
    });
    public MessengerService() {}

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mMessenger.getBinder();
    }
}
