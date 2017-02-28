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
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=connManager.getActiveNetworkInfo();
        if (info == null){
            Log.e("Info","null+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            return;
        }else {
            Log.e("info","not null+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }
        int type=info.getType();
        if (type==ConnectivityManager.TYPE_WIFI)
            Util.showToast("正在使用wifi");
        else if (type==ConnectivityManager.TYPE_MOBILE)  Util.showToast("正在使用流量");
        else Util.showToast("无网络");
    }
}
