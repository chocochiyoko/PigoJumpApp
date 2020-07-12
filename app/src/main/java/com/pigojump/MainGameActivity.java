package com.pigojump;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


import static com.pigojump.R.layout.activity_main_game;

public class MainGameActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private float xAccel = 0;
    private Pigo pigo;
    private GameView gameview;
    private MediaPlayer mediaPlayer;
    private Button pause_button;
    private boolean isPaused = false, screenPause = false;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent = getIntent();
        //gameview = new GameView(this);
        setContentView(activity_main_game);
        gameview = findViewById(R.id.myView);
        //setContentView(gameview);

        pigo = gameview.getPigo();
        SensorActivity();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.pigojump1);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        pause_button = (Button)findViewById(R.id.pauseButton);
        pause_button.setText("||");

    }

    public void sendMessage(View view) {
        gameview.pause();
        if(!isPaused){
            pause_button.setText("▷");
            mediaPlayer.pause();
            isPaused = true;
        }
        else{
            pause_button.setText("||");
            mediaPlayer.start();
            isPaused = false;
        }

    }

    public void SensorActivity() {
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    protected void onResume() {
        super.onResume();
        //gameview.pause();
        mediaPlayer.start();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        //gameview.pause();
        mSensorManager.unregisterListener(this);
        mediaPlayer.pause();
        screenPause = true;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            xAccel = event.values[0];
            if (xAccel >= 0.5 && xAccel <= 2){
                this.pigo.toggleRightPressed(0);
                this.pigo.toggleLeftPressed(1);

            }
            else if (xAccel <= -0.5 && xAccel >= -2){
                this.pigo.toggleLeftPressed(0);
                this.pigo.toggleRightPressed(1);
            }
            else if (xAccel > 2){
                this.pigo.toggleRightPressed(0);
                this.pigo.toggleLeftPressed(2);
            }
            else if (xAccel < -2){
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
            gameview.endThread();
            intent.putExtra("Score", score);
            startActivity(intent);
            //finish();
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public boolean onTouchEvent(MotionEvent event){

//        int action = MotionEventCompat.getActionMasked(event);
        int action = event.getAction();

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
