package com.dt.learning.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.PixelFormat
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider

import android.os.Bundle

import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import com.dt.learning.MyApplication

import com.dt.learning.R
import com.dt.learning.util.TestEvent
import com.dt.learning.customerview.MyCircleView
import com.dt.learning.customerview.StrokeTextView
import com.dt.learning.receiver.NetworkStateReceive
import com.dt.learning.util.*;
import kotlinx.coroutines.*

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

import java.io.File
import java.util.Locale

class MainActivity : BaseActivity() {
    private val networkStateReceive by lazy { NetworkStateReceive() }
    private val stv by lazy { StrokeTextView(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        startActivity(Intent(this, SplashActivity::class.java))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EventBus.getDefault().register(this)
        init()
    }

    @Subscribe
    fun handleEvent(event: TestEvent) {
        Log.e("MainActivity", event.msg)
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun init() {
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(networkStateReceive, intentFilter)
        val circleView = findViewById<View>(R.id.circle_view) as MyCircleView
        circleView.setOnTouchListener(object : View.OnTouchListener {
            private var lastX: Int = 0
            private var lastY: Int = 0

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                val x = event.rawX.toInt()
                val y = event.rawY.toInt()
                Log.e("left", v.left.toString())
                when (event.action) {
                    MotionEvent.ACTION_MOVE -> {
                        val offsetX = x - lastX
                        val offsetY = y - lastY
                        v.layout(
                            v.left + offsetX,
                            v.top + offsetY,
                            v.right + offsetX,
                            v.bottom + offsetY
                        )
                        lastX = x
                        lastY = y
                    }
                    MotionEvent.ACTION_DOWN -> {
                        lastX = x
                        lastY = y
                    }
                }
                return true
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkStateReceive)
    }

    fun imageHandleClick(view: View) {
        startActivity(Intent(this, ImageHandleActivity::class.java))
    }

    fun aidlLearnClick(view: View) {
        startActivity(Intent(this, IPCActivity::class.java))
    }

    fun rerxClick(view: View) {
        startActivity(Intent(this, NetworkActivity::class.java))
    }

    fun socketClick(view: View) {
        startActivity(Intent(this, SocketActivity::class.java))
    }

    fun drawableClick(view: View) {
        //        startActivity(new Intent(this, DrawableActivity.class));
        startActivityForResult(Intent(this, DrawableActivity::class.java), 1000)
    }

    fun animationClick(view: View) {
        startActivity(
            Intent(this, AnimationActivity::class.java),
            ActivityOptions.makeScaleUpAnimation(view, 0, 0, 100, 100).toBundle()
        )
    }

    fun filePathClick(view: View) {
        startActivity(Intent(this, StoragePathActivity::class.java))
    }

    fun takePhoto() {
        val file = File(externalCacheDir, "images")
        if (!file.exists()) file.mkdirs()
        val image = File(file, "pic.jpg")
        val uri = FileProvider.getUriForFile(this, "com.dt.learning.fileprovider", image)
        Log.e(TAG, "takePhoto:uri " + uri.toString())
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        startActivityForResult(intent, REQUEST_MAIN_CAMERA)
    }

    fun takePhotoClick(view: View) {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            takePhoto()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                PERMISSION_CAMERA
            )
        }

    }

    fun showSysArgs(view: View) {
        val intent = Intent(this, SysArgsActivity::class.java)
        startActivity(intent)
    }

    fun showWindowClick(view: View) {

        if ((application as MyApplication).windowStv != null){
            return
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)){
            Toast.makeText(this, "无悬浮窗权限权限", Toast.LENGTH_SHORT).show()
            return
        }

        stv.includeFontPadding = false
        stv.textSize = 12f

        (application as MyApplication).windowStv = stv

        val windowType: Int = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            WindowManager.LayoutParams.TYPE_TOAST
        } else {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_TOAST
            } else {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            }
        }

        val wlp = WindowManager.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
            windowType, 0, PixelFormat.RGBA_8888
        )
        wlp.gravity = Gravity.LEFT or Gravity.TOP
        wlp.flags =
                (WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        windowManager.addView(stv, wlp)

        monitorAppStatus()
    }

    private fun monitorAppStatus() {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        GlobalScope.launch(Dispatchers.Default) {
            while (isActive){
                val memoryInfo =
                    activityManager.getProcessMemoryInfo(intArrayOf(android.os.Process.myPid()))[0]
                val sb = StringBuilder()
                val runtime = Runtime.getRuntime()

                var mem = String.format(Locale.getDefault(), "%.2f",
                    (runtime.totalMemory() - runtime.freeMemory()).toDouble() / 1024.0 / 1024.0 + 0.05)
                appendMemInfo(sb,"Runtime used: ",mem)

                mem = String.format(Locale.getDefault(), "%.2f",
                    runtime.freeMemory().toDouble() / 1024.0 / 1024.0 + 0.05)
                appendMemInfo(sb,"Runtime free: ",mem)

                mem = String.format(Locale.getDefault(), "%.2f", memoryInfo.dalvikPss / 1024.0 + 0.05)
                appendMemInfo(sb,"ART PSS: ",mem)

                mem = String.format(Locale.getDefault(), "%.2f", memoryInfo.nativePss / 1024.0 + 0.05)

                appendMemInfo(sb,"Native PSS: ",mem)

                launch(Dispatchers.Main) {
                    stv.text = sb.toString()
                }
                delay(1000)
            }

        }

    }

    private fun appendMemInfo(sb: StringBuilder,key: String,value: String){
        with(sb){
            append(key)
            append(": ")
            append(value)
            append(" M")
            append("\n")
        }
    }

    fun svgClick(view: View) {
        startActivity(Intent(this, SVGActivity::class.java))
    }

    fun sensorClick(view: View) {
        startActivity(Intent(this, SensorActivity::class.java))
    }

    fun xpermodeClick(view: View) {
        startActivity(Intent(this, XpermodeActivity::class.java))
    }

    fun surfaceViewClick(view: View) {
        startActivity(Intent(this, SurfaceViewActivity::class.java))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_MAIN_CAMERA -> {
            }
            1000 -> Log.e("MainActivity", "resultCode," + resultCode.toString())
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CAMERA -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhoto()
            }
        }
    }

    companion object {

        private val TAG = "MainActivity"
    }
}
