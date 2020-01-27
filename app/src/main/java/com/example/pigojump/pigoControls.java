package com.example.pigojump;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class pigoControls implements SensorEventListener {
    private float xAccel;
    private Pigo pigo;
    pigoControls(Pigo pigo){
        this.pigo = pigo;
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            xAccel = event.values[0];
            if (xAccel < 0){
                this.pigo.unToggleRightPressed();
                this.pigo.toggleLeftPressed();

            }
            if (xAccel > 0){
                this.pigo.unToggleLeftPressed();
                this.pigo.toggleRightPressed();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
