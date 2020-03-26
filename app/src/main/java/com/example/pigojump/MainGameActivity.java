package com.example.pigojump;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainGameActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private float xAccel = 0;
    private Pigo pigo;
    private GameView view;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent = getIntent();
        view = new GameView(this);
        //setContentView(view);
        setContentView(view);
        pigo = view.getPigo();
        SensorActivity();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void sendMessage(View view) {
        // Do something in response to button
    }

    public void SensorActivity() {
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            xAccel = event.values[0];
            if (xAccel >= 0.5 && xAccel <= 1){
                this.pigo.toggleRightPressed(0);
                this.pigo.toggleLeftPressed(1);

            }
            else if (xAccel <= -0.5 && xAccel >= -1){
                this.pigo.toggleLeftPressed(0);
                this.pigo.toggleRightPressed(1);
            }
            else if (xAccel > 1){
                this.pigo.toggleRightPressed(0);
                this.pigo.toggleLeftPressed(2);
            }
            else if (xAccel < -1){
                this.pigo.toggleLeftPressed(0);
                this.pigo.toggleRightPressed(2);
            }
            else {
                this.pigo.toggleLeftPressed(0);
                this.pigo.toggleRightPressed(0);
            }
        }
        if (pigo.isKO()){
            Intent intent = new Intent(this, EndScreen.class);
            int score = pigo.getScore();
            intent.putExtra("Score", score);
            startActivity(intent);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public boolean onTouchEvent(MotionEvent event){

        int action = MotionEventCompat.getActionMasked(event);

        if (action == MotionEvent.ACTION_DOWN){
            pigo.ToggleJumpPressed();
            pigo.unToggleJumpEnd();
        }
        if (action == MotionEvent.ACTION_UP){
            pigo.ToggleJumpEnd();
        }
        return true;
    }

}