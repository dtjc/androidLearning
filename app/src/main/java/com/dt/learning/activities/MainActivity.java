package com.dt.learning.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import com.dt.learning.R;
import com.dt.learning.customerview.MyCircleView;
import com.dt.learning.receiver.NetworkStateReceive;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private NetworkStateReceive networkStateReceive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkStateReceive=new NetworkStateReceive();
        registerReceiver(networkStateReceive,intentFilter);
        MyCircleView circleView = (MyCircleView)findViewById(R.id.circle_view);
        circleView.setOnTouchListener(new View.OnTouchListener() {
            private int lastX;
            private int lastY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();
                switch (event.getAction()){
                    case MotionEvent.ACTION_MOVE:
                        int offsetX = x - lastX;
                        int offsetY = y - lastY;
                        v.layout(v.getLeft()+offsetX,v.getTop()+offsetY,v.getRight()+offsetX,v.getBottom()+offsetY);
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

    public void takePhotoClick(View view){
        File file = new File(getExternalCacheDir(),"images");
//        File file = new File(getCacheDir(),"pics");
        if (!file.exists()) file.mkdirs();
        File image = new File(file,"pic.jpg");
        Uri uri = FileProvider.getUriForFile(this,"com.dt.learning.fileprovider",image);
        Log.e(TAG, "takePhoto:uri "+uri.toString() );
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(intent,100);
    }

    public void navigationClick(View view){
        Intent intent = new Intent(this,NavigationActivity.class);
        startActivity(intent);
    }

    public void showWindowClick(View view){
        Button button = new Button(this);
        button.setText("button window");
        final WindowManager.LayoutParams wlp = new WindowManager.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_TOAST,0, PixelFormat.TRANSPARENT);
        wlp.gravity = Gravity.LEFT | Gravity.TOP;
        wlp.flags= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        wlp.x=100;
        wlp.y=100;
        getWindowManager().addView(button,wlp);
        button.setOnTouchListener(new View.OnTouchListener() {
            private int lastX;
            private int lastY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int)event.getRawX();
                int y = (int)event.getRawY();
                switch (event.getAction()){
                    case MotionEvent.ACTION_MOVE:
                        int dx = x - lastX;
                        int dy = y - lastY;
                        wlp.x += dx;
                        wlp.y += dy;
                        getWindowManager().updateViewLayout(v,wlp);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            String name = data.getStringExtra("btnName");
            Button btn =(Button) findViewById(R.id.activity_main_btn_aar_test);
            btn.setText(name);
        }
    }
}
