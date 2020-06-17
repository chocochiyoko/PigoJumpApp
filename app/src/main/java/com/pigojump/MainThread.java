package com.pigojump;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
//import Thread;

public class MainThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running, pause = false;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    @Override
    public void run() {
        while (running) {
            canvas = null;
            if (!pause){
                try {
                    canvas = this.surfaceHolder.lockCanvas();
                    synchronized(surfaceHolder) {
                        this.gameView.update();
                        this.gameView.draw(canvas);
                    }
                } catch (Exception e) {} finally {
                    if (canvas != null) {
                        try {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
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


