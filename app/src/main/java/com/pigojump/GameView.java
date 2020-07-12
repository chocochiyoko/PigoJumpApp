package com.pigojump;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.widget.ImageView;

import com.pigojump.MapElements.GameMap;

import java.util.ArrayList;

//https://o7planning.org/en/10521/android-2d-game-tutorial-for-beginners
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private Bitmap bMap, pigoImg;
    private GameMap pigoMap;
    private Pigo pigo;
    private Collisions collisions;
    //    private pigoControls controls;
    private int cam;
    private ArrayList<Bitmap> mapImages = new ArrayList<Bitmap>();
    private ArrayList<Bitmap> animationsImgs = new ArrayList<>();
    private boolean started = false;
    private Bitmap resizedBitmap;
    private ScreenInfo screen = new ScreenInfo();
    private boolean start = false;
    private boolean pause = false;
    private long lasttime;

    public GameView(Context context, AttributeSet attr) {

        super(context, attr);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);


        try {

            bMap = BitmapFactory.decodeResource(this.getResources(), R.drawable.pigobackground4);
            pigoImg = BitmapFactory.decodeResource(this.getResources(), R.drawable.pigoidle_0000);
            mapImages.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.cloudpurple));
            mapImages.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.cloudblue));
            mapImages.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.cloudorange));
            mapImages.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.cloudyellow));
            mapImages.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.clearcloud));
            mapImages.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.star));
            mapImages.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.apple));
            mapImages.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.bugo));
            animationsImgs.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.pigoidle_0000));
            animationsImgs.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.pigoidle_0001));
            animationsImgs.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.pigoidle_0002));
            animationsImgs.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.pigowalk_0000));
            animationsImgs.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.pigowalk_0001));
            animationsImgs.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.pigowalk_0002));
            animationsImgs.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.pigojump_0001));
            animationsImgs.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.pigojump_0002));
            animationsImgs.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.pigojump_0003));
            animationsImgs.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.pigojump_0009));
            animationsImgs.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.pigojump_0006));
            animationsImgs.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.pigojump_0007));
            animationsImgs.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.life1));
        } catch (Exception e) {
            Log.i("bleh", "errooooor");
        }
        resizedBitmap = Bitmap.createScaledBitmap(
                bMap, screen.getScreenWidth(), screen.getScreenHeight(), false);
        pigoMap = new GameMap(mapImages);
        pigo = new Pigo(pigoImg, 500, 1000, animationsImgs);
        collisions = new Collisions(pigoMap, pigo);
        setKeepScreenOn(true);
        lasttime = System.currentTimeMillis();



    }

    public GameView(Context c) {
        this(c, null);
    }

    public Pigo getPigo() {
        return this.pigo;
    }

    public void pause() {
        System.out.println("PAUSE");
        thread.togglepause();
        pause = !pause;
    }

    public void update() {


        pigoMap.updateElements();
        pigo.update(getWidth(), getHeight());
        collisions.checkCollisions(getWidth());
        cam = pigo.getcamy();


    }

    public void drawSkyMap(Canvas canvas) {

        canvas.drawBitmap(resizedBitmap, 0, 0, null);
    }

    public void endThread() {
        thread.setRunning(false);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        System.out.println("surface created");
        if (!started) {
            thread.setRunning(true);
            thread.start();
        }

        if (started) {
             thread.setRunning(true);
            this.pause();
        }
        started = true;


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        System.out.println("surface destroyed");
        this.pause();
//        boolean retry = true;
//        while (retry) {
//            try {
//                this.pause();
//                thread.setRunning(false);
//                thread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            retry = false;
//        }
    }

    @Override
    public void draw(Canvas canvas) {
        long thistime = System.currentTimeMillis()- lasttime;

        if (!pause && thistime > 16) {
            lasttime = System.currentTimeMillis();
            update();
        }


        //System.out.println(thistime);
        super.draw(canvas);
//        if(!start){
//            drawSkyMap(canvas);
//            start = false;
//        }
        //canvas.drawBitmap(resizedBitmap, 0, 0, null);

        //drawSkyMap(canvas);
        pigoMap.drawElements(canvas, cam);
        pigo.drawImage(canvas, cam);

        this.postInvalidate();


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
