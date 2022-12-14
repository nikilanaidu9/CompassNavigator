package com.example.compassnavigator

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.roundToInt


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), SensorEventListener {
    var currentDegree = 0f
    var sensorManager: SensorManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sensorManager = this.getSystemService(SENSOR_SERVICE) as SensorManager
    }
    override fun onSensorChanged(event: SensorEvent) {

        val degree = event.values[0].roundToInt().toFloat()
        val degreeText = degree.toString().split("\\.".toRegex()).toTypedArray()[0] + "°"
        val textDegree = findViewById<TextView>(R.id.textDegree)
        textDegree.text = degreeText


        val ra = RotateAnimation(
            currentDegree,
            -degree,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )


        ra.duration = 210


        ra.fillAfter = true


        val compass = findViewById<ImageView>(R.id.compass)
        compass.startAnimation(ra)
        currentDegree = -degree
    }
    override fun onAccuracyChanged(sensor: Sensor?, i: Int) {

    }

    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(
            this,
            sensorManager?.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }
    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }

}