package com.dt.learning.activities

import android.content.Context
import android.graphics.Matrix
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dt.learning.R
import kotlinx.android.synthetic.main.activity_sensor.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SensorActivity : BaseActivity(), SensorEventListener{

    private val mAccelerometerReading = FloatArray(3)
    private val mMagnetometerReading = FloatArray(3)
    private val mRotationMatrix = FloatArray(9)
    private val mOrientationAngles = FloatArray(3)

    private var mPause = false

    private val a = 180/Math.PI

    private val mSensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor)
    }

    override fun onResume() {
        super.onResume()
        mPause = false
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) ,
            SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI)
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
            SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI)
        showDataInUi()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {
        when(event.sensor.type){
            Sensor.TYPE_ACCELEROMETER -> System.arraycopy(event.values,0,mAccelerometerReading,0,mAccelerometerReading.size)
            Sensor.TYPE_MAGNETIC_FIELD -> System.arraycopy(event.values,0,mMagnetometerReading,0,mMagnetometerReading.size)
        }
    }

    override fun onPause() {
        super.onPause()
        mPause = true
        mSensorManager.unregisterListener(this)
    }

    fun updateOrientationAngles(){

        SensorManager.getRotationMatrix(mRotationMatrix, null,
            mAccelerometerReading, mMagnetometerReading)

        SensorManager.getOrientation(mRotationMatrix, mOrientationAngles)
    }

    fun showDataInUi(){
        launch(Dispatchers.Main){
            while (!mPause){
                delay(250)
                updateOrientationAngles()
                value_azimuth.text = angleToDegree(mOrientationAngles[0])
                value_pitch.text = angleToDegree(mOrientationAngles[1])
                value_roll.text = angleToDegree(mOrientationAngles[2])
                //手机平面的法线(方向为屏幕指向背面)与地面的夹角。
                value_angle.text = (Math.asin(-mRotationMatrix[8].toDouble()) * a).toString()
            }
        }

    }

    fun angleToDegree(angle: Float): String{
        return (angle * a).toString()
    }
}