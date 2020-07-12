package com.pigojump;

import android.graphics.Canvas;
import android.net.http.SslCertificate;
import android.view.Surface;
import android.view.SurfaceHolder;

import java.util.Timer;
import java.util.TimerTask;
//import Thread;

public class MainThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running, pause = false;
    public static Canvas canvas;
    public long lastTime = 0, thisTime = 0;
    public Surface surface;
    public TimerTask FPS;
    public Timer timer;
    public ScreenInfo screen;

    public MainThread(final SurfaceHolder surfaceHolder, final GameView gameView){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
        lastTime = System.currentTimeMillis();
        surface = surfaceHolder.getSurface();
        FPS = new TimerTask() {
            @Override
            public void run() {
                if(!pause) {
                    canvas = surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        gameView.update();
                        gameView.draw(canvas);
                    }
                }
            }
        };
    }

    @Override
    public void run() {
        canvas = null;
        while (running) {

            thisTime = System.currentTimeMillis() - lastTime;
            if (!pause && thisTime > 16){
                System.out.println(pause);
                lastTime = System.currentTimeMillis();
                try {

                    canvas = this.surfaceHolder.lockCanvas();
                    synchronized(surfaceHolder) {
//                        gameView.draw(canvas);
//                        final Timer timer = new Timer();
//                        TimerTask FPS = new TimerTask(){
//                            @Override
//                            public void run() {
//                                gameView.update();
//
//                            }
//                        };
//
//
//                        timer.schedule(FPS, 1);
//                        timer.cancel();
                        //thisTime = System.currentTimeMillis() - lastTime;
                        System.out.println("this time" + thisTime);
                        //lastTime = System.currentTimeMillis();
                        //System.out.println("beep" + screen.getScale());
                        //this.gameView.update();
                        this.gameView.draw(canvas);



//
//                        if (80 - thisTime > 0) {
//                            System.out.println("beep");
//                            currentThread().sleep(80 - (thisTime));
//                        }
//
//                        System.out.println("boop");


                    }
                } catch (Exception e) {} finally {
                    if (canvas != null) {
                        //System.out.println("hello?");
                        try {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            else {
                System.out.println("nope");
            }

        }
    }
    public void setRunning(boolean isRunning) {
        running = isRunning;
    }
    public void togglepause(){
        pause = !pause;
    }
}


