package com.example.electromagneticfield

import android.annotation.SuppressLint
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var magneticFieldSensos:Sensor
    private lateinit var value: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        value = findViewById(R.id.value)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        magneticFieldSensos = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        if(magneticFieldSensos == null)
        {
            Toast.makeText(this, "Magnetic field sensor is not available on this device", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(event: SensorEvent?) {
        if(event==null)
            return
        if(event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD)
        {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            val magnitude = sqrt(((x*x)+(y*y) +(z*z)).toDouble())

            val micro = magnitude -65
            val maginuteFormatted = String.format("%2f", micro)
            val color = if(micro > 0.40) {
                Color.RED
            }else {
                Color.WHITE
            }
            if(micro >= 0.01){
                value.setTextColor(color)
                value.text = "$maginuteFormatted \u00B5Tesla"
            }
            else
            {
                value.setTextColor(Color.WHITE)
                value.text = 0.toString() + " \u00B5Tesla"
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("Not yet implemented")
    }
}