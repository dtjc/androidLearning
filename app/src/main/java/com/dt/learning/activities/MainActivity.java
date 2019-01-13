package com.dt.learning.activities;

import android.Manifest;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Debug;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.dt.learning.R;
import com.dt.learning.Util.ConstantKt;
import com.dt.learning.Util.TestEvent;
import com.dt.learning.customerview.MyCircleView;
import com.dt.learning.customerview.StrokeTextView;
import com.dt.learning.receiver.NetworkStateReceive;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private NetworkStateReceive networkStateReceive;
    private StrokeTextView stv;
    private ActivityManager activityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        startActivity(new Intent(this, SplashActivity.class));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        init();
    }

    @Subscribe
    public void handleEvent(TestEvent event){
        Log.e("MainActivity",event.msg);
    }


    private void init() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkStateReceive = new NetworkStateReceive();
        registerReceiver(networkStateReceive, intentFilter);
        final MyCircleView circleView = (MyCircleView) findViewById(R.id.circle_view);
        circleView.setOnTouchListener(new View.OnTouchListener() {
            private int lastX;
            private int lastY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();
                Log.e("left", String.valueOf(v.getLeft()));
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        int offsetX = x - lastX;
                        int offsetY = y - lastY;
                        v.layout(v.getLeft() + offsetX, v.getTop() + offsetY, v.getRight() + offsetX, v.getBottom() + offsetY);
                        lastX = x;
                        lastY = y;
                        break;
                    case MotionEvent.ACTION_DOWN:
                        lastX = x;
                        lastY = y;
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onStop() {
        Log.e("MainActivity","onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.e("MainActivity","onDestroy");
        super.onDestroy();

        unregisterReceiver(networkStateReceive);
    }

    public void imageHandleClick(View view) {
        startActivity(new Intent(this, ImageHandleActivity.class));
    }

    public void aidlLearnClick(View view) {
        startActivity(new Intent(this, IPCActivity.class));
    }

    public void rerxClick(View view) {
        startActivity(new Intent(this, NetworkActivity.class));
    }

    public void socketClick(View view) {
        startActivity(new Intent(this, SocketActivity.class));
    }

    public void drawableClick(View view) {
//        startActivity(new Intent(this, DrawableActivity.class));
        startActivityForResult(new Intent(this, DrawableActivity.class),1000);
    }

    public void animationClick(View view) {
        startActivity(new Intent(this, AnimationActivity.class),
                ActivityOptions.makeScaleUpAnimation(view,0,0,100,100).toBundle());
    }

    public void filePathClick(View view) {
        startActivity(new Intent(this, StoragePathActivity.class));
    }

    public void takePhoto(){
        File file = new File(getExternalCacheDir(), "images");
        if (!file.exists()) file.mkdirs();
        File image = new File(file, "pic.jpg");
        Uri uri = FileProvider.getUriForFile(this, "com.dt.learning.fileprovider", image);
        Log.e(TAG, "takePhoto:uri " + uri.toString());
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(intent, ConstantKt.REQUEST_MAIN_CAMERA);
    }

    public void takePhotoClick(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            takePhoto();
        }else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, ConstantKt.PERMISSION_CAMERA);
        }
    }

    public void showSysArgs(View view) {
        Intent intent = new Intent(this, SysArgsActivity.class);
        startActivity(intent);
    }

    public void showWindowClick(View view) {

        if (stv != null){
            return;
        }

        stv = new StrokeTextView(this);

        stv.setIncludeFontPadding(false);
        stv.setTextSize(12);
        int windowType;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            windowType = WindowManager.LayoutParams.TYPE_TOAST;
        }else{
            if (!Settings.canDrawOverlays(getApplicationContext())){
                Toast.makeText(this,"无悬浮窗权限权限",Toast.LENGTH_SHORT).show();
                return;
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
                windowType = WindowManager.LayoutParams.TYPE_TOAST;
            }else {
                windowType = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            }
        }
        final WindowManager.LayoutParams wlp = new WindowManager.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                        windowType, 0, PixelFormat.RGBA_8888);
        wlp.gravity = Gravity.LEFT | Gravity.TOP;
        wlp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        getWindowManager().addView(stv, wlp);

        monitorAppStatus();
    }

    public void monitorAppStatus(){
        if (activityManager == null){
            activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Debug.MemoryInfo memoryInfo = activityManager.getProcessMemoryInfo(new int[]{android.os.Process.myPid()})[0];
                StringBuilder sb = new StringBuilder();
                Runtime runtime = Runtime.getRuntime();
                String mem;

                sb.append("Runtime used: ");
                mem = String.format(Locale.getDefault(),"%.2f", (runtime.totalMemory() - runtime.freeMemory()) / 1024.0 / 1024 + 0.05);
                sb.append(mem);
                sb.append(" M");
                sb.append("\n");

                sb.append("Runtime free: ");
                mem = String.format(Locale.getDefault(),"%.2f", runtime.freeMemory() / 1024.0 / 1024 + 0.05);
                sb.append(mem);
                sb.append(" M");
                sb.append("\n");

                mem = String.format(Locale.getDefault(),"%.2f", memoryInfo.dalvikPss / 1024.0 + 0.05);
                sb.append("ART PSS: ");
                sb.append(mem);
                sb.append(" M");
                sb.append("\n");

                sb.append("Native PSS: ");
                mem = String.format(Locale.getDefault(),"%.2f", memoryInfo.nativePss / 1024.0 + 0.05);
                sb.append(mem);
                sb.append(" M");

                stv.setText(sb.toString());
                stv.postDelayed(this,1000);
            }
        };

        stv.post(runnable);
    }

    public void SVGClick(View view){
        startActivity(new Intent(this,SVGActivity.class));
    }

    public void SensorCLick(View view){
        startActivity(new Intent(this,SensorActivity.class));
    }

    public void xpermodeClick(View view){
        startActivity(new Intent(this,XpermodeActivity.class));
    }

    public void surfaceViewClick(View view){
        startActivity(new Intent(this,SurfaceViewActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ConstantKt.REQUEST_MAIN_CAMERA:

                break;
            case 1000:
                Log.e("MainActivity","resultCode," + String.valueOf(resultCode));
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case ConstantKt.PERMISSION_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    takePhoto();
                }
                break;
        }
    }
}
