package com.dt.learning.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

import com.dt.learning.R

class SysArgsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sys_args)
        val metrics = resources.displayMetrics
        val runtime = Runtime.getRuntime()
        val tv = findViewById<TextView>(R.id.sys_args_tv_args)
        tv.text = "device: ${Build.MODEL}\n"
        tv.append("sdk: " + Build.VERSION.SDK_INT.toString() + "\n")
        tv.append("os: " + Build.VERSION.RELEASE + "\n")
        tv.append("width: " + metrics.widthPixels + "\n")
        tv.append("height: " + metrics.heightPixels + "\n")
        tv.append("xdpi: " + metrics.xdpi + "\n")
        tv.append("ydpi: " + metrics.ydpi + "\n")
        tv.append("density: " + metrics.density + "\n")
        tv.append("densityDpi: " + metrics.densityDpi + "\n")
        tv.append("processors: " + runtime.availableProcessors() + "\n")
        tv.append("appMemory: " + runtime.totalMemory().toDouble() / 1024.0 / 1024.0 + "M\n")
        tv.append("maxMemory: " + runtime.maxMemory().toDouble() / 1024.0 / 1024.0 + "M\n")
    }
}
