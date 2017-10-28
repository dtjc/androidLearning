package com.dt.learning.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.dt.learning.aidl.IFirstAidlInterface;
import com.dt.learning.aidl.User;

public class AIDLService extends Service {
    //  AIDL,Android Interface Definition Language,安卓接口定义语言
    //  使用AIDL要传输自定义类型数据必须创建相应类型的aidl文件和java文件，java文件还必须实现Parcelable接口
    private Binder aidlBinder=new IFirstAidlInterface.Stub() {
        @Override
        public User createUser(String name,int age) throws RemoteException {
            User user=new User();
            user.setName(name);
            user.setAge(age);
            int pid = android.os.Process.myPid();//获取进程pid
            String processName = null;
            ActivityManager am = (ActivityManager)getBaseContext().getSystemService(Context.ACTIVITY_SERVICE);//获取系统的ActivityManager服务
            for (ActivityManager.RunningAppProcessInfo appProcess : am.getRunningAppProcesses()){
                if(appProcess.pid == pid){
                    processName = appProcess.processName;
                    break;
                }
            }
            Log.e("processname",processName);
            return user;
        }
    };
    public AIDLService() {}

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        if (checkCallingOrSelfPermission("com.dt.learning.permission.ACCESS_AIDL_SERVICE")== PackageManager.PERMISSION_DENIED)
            return null;
        return aidlBinder;
    }
}
