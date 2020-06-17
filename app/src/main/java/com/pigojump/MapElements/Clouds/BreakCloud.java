package com.pigojump.MapElements.Clouds;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.pigojump.GameObject;
import com.pigojump.Pigo;
import com.pigojump.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class BreakCloud extends Cloud {

    private BreakCloud temp;
    private Bitmap clearImg;
    private Bitmap tempImg;
    private final Object LOCK = new Object();
    boolean running = false;

    public BreakCloud(Bitmap img, int x, int y, Bitmap clearImg) {
        super(img, x, y);
       this.clearImg = clearImg;
        temp = this;
        tempImg = this.img;
        int height = screen.getScreenHeight()/45;
        int width = screen.getScreenWidth()/screen.getCloudSpace();
        this.clearImg = img.createScaledBitmap(clearImg, width, height, false);

    }

    public boolean isRunning (){
        return running;
    }
    public void disappear (final ArrayList<GameObject> objects, final Pigo pigo) {
        running = true;
        System.out.println("calling disappear");
        final Timer timer = new Timer();



        final int index = objects.indexOf(this);
        TimerTask reInsert = new TimerTask() {
            @Override
            public void run() {
                //if (!pigo.isFreeFall()){
                    System.out.println("reinsert");
                    objects.add(index, temp);
                //}

            }
        };
        TimerTask wait = new TimerTask() {
            @Override
            public void run() {
                System.out.println("wait done");
                objects.remove(temp);
            }
        };
        TimerTask stop = new TimerTask() {
            @Override
            public void run() {
                System.out.println("stop calling disappear");
                running = false;
                img = tempImg;
                timer.cancel();
            }
        };
        TimerTask toggleClearImg = new TimerTask() {
            @Override
            public void run() {
                img = clearImg;
            }
        };
        TimerTask toggleRealImg = new TimerTask() {
            @Override
            public void run() {
                img = tempImg;
            }
        };

        timer.schedule(wait, 1300);
        timer.schedule(reInsert, 5000);
        timer.schedule(toggleClearImg, 200, 200);
        timer.schedule(toggleRealImg, 300, 200);

        timer.schedule(stop, 5010);


    }
}
