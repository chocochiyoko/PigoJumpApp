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

import java.util.ArrayList;

//https://o7planning.org/en/10521/android-2d-game-tutorial-for-beginners
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private Bitmap bMap, pigoImg;
    private GameMap pigoMap;
    private Pigo pigo;
    private Collisions collisions;
    private pigoControls controls;
    private int cam;
    private ArrayList<Bitmap> mapImages = new ArrayList<Bitmap>();

    public GameView(Context context){
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);

        try {

            bMap = BitmapFactory.decodeResource(this.getResources(), R.drawable.pigobackground);
            pigoImg = BitmapFactory.decodeResource(this.getResources(), R.drawable.pigoidle_0000);
            mapImages.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.cloudpurple));
            mapImages.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.cloudblue));
            mapImages.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.cloudorange));
            mapImages.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.cloudyellow));
            mapImages.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.clearcloud));
        }
        catch (Exception e){
            Log.i("bleh", "errooooor");
        }
        pigoMap = new GameMap(mapImages);
        pigo = new Pigo(pigoImg, 500, 1000);
        collisions = new Collisions(pigoMap,pigo);
        controls = new pigoControls(pigo);




    }
    public Pigo getPigo(){
        return this.pigo;
    }

    public void update() {
        pigo.update(getWidth(), getHeight());
        cam = pigo.getcamy();
        collisions.checkCollisions(getWidth());
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
        update();
        super.draw(canvas);

        drawSkyMap(canvas);
        pigoMap.drawElements(canvas,cam);
        pigo.drawImage(canvas, cam);

//        GameRectangle one = new GameRectangle( 300, 300, 300, 300);
//        GameRectangle two = new GameRectangle(340, 340, 300, 100);
//        GameRectangle three = new GameRectangle(0,0,0,0);
//        three = one.intersection(two);
//
//        canvas.drawColor(Color.WHITE);
//        Paint paint = new Paint();
//        paint.setColor(Color.rgb(250, 0, 0));
//        canvas.drawRect(one.getX(), one.getY(), one.getX() + one.getWidth(), one.getY() + one.getHeight(), paint);
//        paint.setColor(Color.rgb(0, 250, 0));
//        canvas.drawRect(two.getX(), two.getY(), two.getX() + two.getWidth(), two.getY() + two.getHeight(), paint);
//        paint.setColor(Color.rgb(0, 0, 250));
//        canvas.drawRect(three.getX(), three.getY(), three.getX() + three.getWidth(), three.getY() + three.getHeight(), paint);

    }
}
