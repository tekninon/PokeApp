package com.example.pokeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.se.omapi.Session;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class PodoActivity extends AppCompatActivity {

    SensorManager sensorManager;
    Boolean running = false;
    Float totalSteps = 0f;
    Float previousTotalSteps = 0f;

    private TextView textView;
    private double MagnitudePrevious = 0;
    private Integer stepCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podo);

        textView = findViewById(R.id.step_counter);
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        CircularProgressBar circularProgressBar = findViewById(R.id.progress_circular);

        SensorEventListener stepDetector = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
               if (sensorEvent != null) {
                   float x_acceleration = sensorEvent.values[0];
                   float y_acceleration = sensorEvent.values[1];
                   float z_acceleration = sensorEvent.values[2];

                   double Magnitude = Math.sqrt(x_acceleration*x_acceleration + y_acceleration*y_acceleration + z_acceleration*z_acceleration);
                   double MagnitudeDelta = Magnitude - MagnitudePrevious;
                   MagnitudePrevious = Magnitude;

                   if(MagnitudeDelta > 6) {
                       stepCount++;
                   }
                   textView.setText(stepCount.toString());
               }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        sensorManager.registerListener(stepDetector, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepCount", stepCount);
        editor.apply();
    }

    protected void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepCount", stepCount);
        editor.apply();
    }

    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        stepCount = sharedPreferences.getInt("stepCount", 0);
    }
}