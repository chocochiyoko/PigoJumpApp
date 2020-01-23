package com.example.pigojump.MapElements;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceView;

import com.example.pigojump.GameObject;
import com.example.pigojump.MapElements.Clouds.NormalCloud;
import com.example.pigojump.R;

import java.util.ArrayList;
import java.util.Timer;

public class GameMap {
    private Bitmap background, cloudNormalImg, cloudHoriImg, cloudVertImg, appleImg, starImg, cloudBreakImg, bugImg;
    private ArrayList<GameObject> collideables= new ArrayList<> ();
    private ArrayList<GameObject> nonCollideables = new ArrayList<>();
    // private int highestCloud = 0, cloudMaker = 25;
    private int lastypos = 200, lastlastypos = 200;
    private int lastxpos = 400;
    private int xpos = 300;
    private int ymove = 0;
    private int maker = 10;
    private double cloudLimit = 0.75;
    private int StarCounter = 0;
    private boolean lastvertical = false;
    private boolean lastbreakable = false;
    private int lastspeed = 0;
    private int lastEnemy = 600;
    private Timer timer = new Timer();
    private GameObject toRemove;

    public GameMap(SurfaceView SV){
        try {
            BitmapFactory.decodeResource(SV.getResources(), R.drawable.cloudpurple);
//            cloudHoriImg = ImageIO.read(getClass().getResource("/resources/clouds/cloudblue.png"));
//            cloudVertImg = ImageIO.read(getClass().getResource("/resources/clouds/cloudorange.png"));
//            cloudBreakImg = ImageIO.read(getClass().getResource("/resources/clouds/cloudyellow.png"));
//            appleImg = ImageIO.read(getClass().getResource("/resources/apple.png"));
//            starImg = ImageIO.read(getClass().getResource("/resources/star.png"));
//            bugImg = ImageIO.read(getClass().getResource("/resources/bugo.png"));

        }
        catch (Exception e){
            Log.i("oh no" , "Failed to load image in game main");
        }

        for (int i = 0; i < 7; i++){
            collideables.add(new NormalCloud(cloudNormalImg, i*112, 200));
        }

//
//        makeElements(20);
//        makeStars(15);

    }
    public void drawElements (Canvas canvas, int camera){

        for (int i = 0; i< nonCollideables.size(); i++){
            nonCollideables.get(i).drawImage(canvas, camera);
        }

        for (int i = 0; i < collideables.size(); i++){
            collideables.get(i).update(collideables);
            collideables.get(i).drawImage(canvas, camera);
        }

    }
}
