package com.codility.lightsensor

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import com.codility.lightsensor.adapter.MyAdapter
import com.codility.lightsensor.model.Light
import kotlinx.android.synthetic.main.activity_main.*

@SuppressLint("NewApi")
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class MainActivity : AppCompatActivity(), SensorEventListener {

    private var sensorManager: SensorManager? = null
    private var lightSensor: Sensor? = null

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event!!.sensor.type == Sensor.TYPE_LIGHT){
            getLightSensor(event)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager?
        lightSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    private fun getLightSensor(event: SensorEvent) {
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val lights = ArrayList<Light>()
        lights.add(Light("Light Sensor Reading ", event.values[0].toString()))
        lights.add(Light("Name ", lightSensor!!.name))
        lights.add(Light("Vendor ", lightSensor!!.vendor))
        lights.add(Light("Version ", lightSensor!!.version.toString()))
        lights.add(Light("Resolution ", lightSensor!!.resolution.toString()))
        lights.add(Light("Power ", lightSensor!!.power.toString()))
        lights.add(Light("Max Range ", lightSensor!!.maximumRange.toString()))
        lights.add(Light("Max Delay ", lightSensor!!.maxDelay.toString()))
        lights.add(Light("Min Delay ", lightSensor!!.minDelay.toString()))
        recyclerView.adapter =  MyAdapter(lights)
    }

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(this, sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }
}