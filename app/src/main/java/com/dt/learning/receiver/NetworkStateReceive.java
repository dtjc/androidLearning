package com.dt.learning.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.dt.learning.Util.Util;

/**
 * Created by dnnt9 on 2017/2/13.
 */

public class NetworkStateReceive extends BroadcastReceiver {
    private boolean wifiConnected;//当网络处于开的状态时，wifi的一次连接或断开会收到两次broadcast(据网上说，有的机子甚至收到3次)，
                                    // 该属性用于判断是否是第一次收到broadcast
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=connManager.getActiveNetworkInfo();
        Log.e("no connect",String.valueOf(intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false))); //网络是否全部断开
        Log.e("change",String.valueOf(intent.getBooleanExtra(ConnectivityManager.EXTRA_IS_FAILOVER, false))); //wifi和gprs是否在切换
        String reason = intent.getStringExtra(ConnectivityManager.EXTRA_REASON);
        if (reason != null){
            Log.e("reason",reason);
        }
        if (info == null){
            Util.showToast("无网络");
            return;
        }
        int type=info.getType();
        if (wifiConnected){
            if (type == ConnectivityManager.TYPE_MOBILE){
                Util.showToast("正在使用流量");
                wifiConnected = false;
            }else if (type != ConnectivityManager.TYPE_WIFI){
                Util.showToast("未知网络");
                wifiConnected = false;
            }
        }else {
            if (type == ConnectivityManager.TYPE_WIFI){
                Util.showToast("正在使用wifi");
                wifiConnected = true;
            }else if (type == ConnectivityManager.TYPE_MOBILE){
                Util.showToast("正在使用流量");
            }else {
                Util.showToast("未知网络");
            }
        }
    }
}
