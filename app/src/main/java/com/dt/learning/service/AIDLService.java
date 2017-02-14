package com.dt.learning.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import com.dt.learning.aidl.IFirstAidlInterface;
import com.dt.learning.aidl.User;

public class AIDLService extends Service {
    public static final int AIDL_SERVICE=1;
    //  AIDL,Android Interface Definition Language,安卓接口定义语言
    //  使用AIDL要传输自定义类型数据必须创建相应类型的aidl文件和java文件，java文件还必须实现Parcelable接口
    private Binder aidlBinder=new IFirstAidlInterface.Stub() {
        @Override
        public User createUser(String name,int age) throws RemoteException {
            User user=new User();
            user.setName(name);
            user.setAge(age);
            return user;
        }
    };
    public AIDLService() {}

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return aidlBinder;
    }
}
