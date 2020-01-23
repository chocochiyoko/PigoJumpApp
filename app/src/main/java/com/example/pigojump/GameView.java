package com.example.pigojump;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.widget.ImageView;

import com.example.pigojump.MapElements.GameMap;

//https://o7planning.org/en/10521/android-2d-game-tutorial-for-beginners
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private Bitmap bMap;
    private GameMap pigoMap;

    public GameView(Context context){
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);

        try {

            bMap = BitmapFactory.decodeResource(this.getResources(), R.drawable.pigobackground);

        }
        catch (Exception e){
            Log.i("bleh", "errooooor");
        }
        pigoMap = new GameMap(this);

    }

    public void update() {

    }

    public void drawSkyMap(Canvas canvas){
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(
                bMap, getWidth(), getHeight(), false);
        canvas.drawBitmap(resizedBitmap, getLeft(), getTop(), null);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        drawSkyMap(canvas);
        pigoMap.drawElements(canvas,600);
    }
}
