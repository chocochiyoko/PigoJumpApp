package com.example.pigojump.MapElements;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceView;

import com.example.pigojump.GameObject;
import com.example.pigojump.GameRectangle;
import com.example.pigojump.MapElements.Clouds.BreakCloud;
import com.example.pigojump.MapElements.Clouds.Cloud;
import com.example.pigojump.MapElements.Clouds.HorizontalCloud;
import com.example.pigojump.MapElements.Clouds.NormalCloud;
import com.example.pigojump.MapElements.Clouds.VerticalCloud;
import com.example.pigojump.Pigo;
import com.example.pigojump.R;
import com.example.pigojump.ScreenInfo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameMap {
    private Bitmap background, cloudNormalImg, cloudHoriImg, cloudVertImg, cloudClearImg, appleImg, starImg, cloudBreakImg, bugImg;
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
    private ScreenInfo screen = new ScreenInfo();
    private NormalCloud ruler;
    private int type, length, speed, apple, xlimit;

    public GameMap(ArrayList<Bitmap> images){
        try {
            cloudNormalImg = images.get(0);
            cloudHoriImg = images.get(1);
            cloudVertImg = images.get(2);
            cloudBreakImg = images.get(3);
            cloudClearImg = images.get(4);
            starImg = images.get(5);
            appleImg = images.get(6);
            bugImg = images.get(7);
            ruler = new NormalCloud(cloudNormalImg, -100, -100);

        }
        catch (Exception e){
            Log.i("oh no" , "Failed to load image in game main");
        }

        for (int i = 0; i < 7; i++){
            collideables.add(new NormalCloud(cloudNormalImg, i*(screen.getScreenWidth()/screen.getCloudSpace()), 200));
        }


        makeElements(20);
        makeStars(15);

    }
    public void updateElements (){
        for (int i = 0; i < collideables.size(); i++){

            collideables.get(i).update(collideables);
        }

    }
    public void drawElements (Canvas canvas, int camera){

        for (int i = 0; i< nonCollideables.size(); i++){
            nonCollideables.get(i).drawImage(canvas, camera);
        }

        for (int i = 0; i < collideables.size(); i++){

            collideables.get(i).drawImage(canvas, camera);
        }

    }

    public ArrayList<GameObject> checkPigoCollision (Pigo pigo) {
        ArrayList<GameObject> ReturnVal = new ArrayList<>();

        for (final int[] i = {0}; i[0] < collideables.size(); i[0]++) {

            GameObject object = collideables.get(i[0]);
            GameRectangle smallerRect = new GameRectangle(
                    object.getx() + object.getImgWidth() / 4,
                    object.gety() - object.getImgHeight() / 2,
                    object.getImgWidth() / 2,
                    object.getImgHeight() / 4
            );

//            if (Math.abs(pigo.gety() - pigo.getImgHeight() - object.gety()) <=screen.getScreenHeight()/20
//                    && (pigo.getx()>= object.getx() - pigo.getImgWidth())
//                    && (pigo.getx() <= object.getx() + pigo.getImgWidth())
//                    && pigo.getvy() <= 0
//                    && object instanceof Cloud) {
//                pigo.setIsOnLand(true);
//            }

            if (Math.abs(pigo.gety() - pigo.getImgHeight() - object.gety()) <= 20
                    && (pigo.getx() + 5 >= object.getx() - pigo.getImgWidth())
                    && (pigo.getx() - 5 <= object.getx() + ruler.getImgWidth())
                    && object instanceof Enemies
                    && pigo.getvy() <= 0
                    && pigo.getJumpEnd()
            ) {

                ((Enemies) object).killedBy(pigo);
                toRemove = object;
                TimerTask removeEnemy= new TimerTask() {
                    @Override
                    public void run() {
                        collideables.remove(toRemove);
                        i[0]++;
                        toRemove = null;
                    }
                };
                timer.schedule(removeEnemy, 3000);
            }

            else if (object instanceof Enemies
                    && pigo.getRect().intersects(smallerRect)){

                pigo.takeDamage();

            }
            if (pigo.getRect().intersects(object.getRect()) ||
                    (pigo.gety() - pigo.getImgHeight() -object.gety() < screen.getScreenHeight()/50
                        && pigo.gety() - pigo.getImgHeight() -object.gety() > 0)) {
                ReturnVal.add(object);
              //  pigo.setIsOnLand(true);

                if (object instanceof BreakCloud && object.getRect().intersects(pigo.getRect())
                        && pigo.getvy() <=0
                        &&  !((BreakCloud) object).isRunning()
                        && ((pigo.gety()-pigo.getImgHeight() - object.gety() <= (object.getImgHeight()/4)
                        && (pigo.gety()-pigo.getImgHeight() - object.gety() >= 0)
                        || (pigo.gety()-pigo.getImgHeight() - object.gety() >= -(object.getImgHeight()/4)
                        &&  (pigo.gety()-pigo.getImgHeight() - object.gety() <= 0)   )))){
                    ((BreakCloud) object).disappear(collideables, pigo);
                    i[0]++;
                    System.out.println("break cloud!");
                }

                if (pigo.getRect().intersects(object.getRect()) && object instanceof Powerup) {
                    ((Powerup) object).power(pigo);
                    ((Powerup) object).remove(collideables);

                }
//
//
////                if (i[0] % (collideables.size()*0.6) >= (collideables.size()*0.6)-10) {
////                    System.out.println("remake at on cloud y " + object.gety());
////                    remakeClouds();
////                    makeStars(maker);
////                }
                if (i[0] > collideables.size()*0.5) {
                    System.out.println("remake at on cloud y " + object.gety());
                    makeElements(maker);
                   // makeStars(maker);
                }

//
//
//            }

             }

            if (pigo.getmaxy()-collideables.get(i[0]).gety() > screen.getScreenHeight()){
                collideables.remove(i[0]);
            }

        }


        return ReturnVal;
    }

    public void makeElements (int cloudMaker){
        Random rand = new Random();



        for (int i = 0; i < cloudMaker; i++){
            //System.out.println("make cloud " + i);
            type = rand.nextInt(4);
            length = rand.nextInt(3);
            speed = 1 + rand.nextInt(2);
            apple = rand.nextInt(10);
            xlimit = screen.getScreenWidth()/2;
            if (lastspeed == speed){
                speed ++;
            }
            if (lastbreakable){
                type = 0;
                lastbreakable = false;
                xlimit = 300;
            }
            if (lastvertical){
                ymove = screen.getScreenHeight()/3 +rand.nextInt(screen.getScreenHeight()/4);
                while (type == 1){
                    type = rand.nextInt(4);
                }
                lastvertical = false;
            }
            else {
                ymove = screen.getScreenHeight()/4 + rand.nextInt(screen.getScreenHeight()/10);
            }

            do {
                xpos = rand.nextInt(screen.getScreenWidth()-((1+ length) * ruler.getImgWidth()));
            } while (Math.abs(xpos-lastxpos) > xlimit);

            if (apple >5 && lastEnemy + 1000 < lastypos  ){

                int enemyy = lastypos + rand.nextInt(ymove);
                int enemyx = 0;
                if (xpos < lastxpos){
                    enemyx = lastxpos + rand.nextInt(screen.getScreenWidth() - lastxpos+ ruler.getImgWidth()-bugImg.getWidth());
                }
                else {
                    enemyx = rand.nextInt(lastxpos -bugImg.getWidth());

                }
               collideables.add(new EnemyBug(bugImg, enemyx, enemyy));
                lastEnemy = enemyy;

            }

            
            if (type == 0){
                for (int j=0;  j <= length; j++){
                    collideables.add(new NormalCloud(cloudNormalImg, xpos+(j*ruler.getImgWidth()), ymove + lastypos));
                    lastxpos = xpos+((j*ruler.getImgWidth())/2);
                }


            }
            else if (type == 1){
                for (int k = 0; k <= length; k++){
                    collideables.add(new VerticalCloud(cloudVertImg, xpos+(k*ruler.getImgWidth()), ymove + lastypos, speed));
                    lastxpos = xpos+((k*ruler.getImgWidth())/2);
                }
                lastvertical = true;
            }
//            else if (type == 2){
//                for (int l = 0; l <= length; l++){
//                    collideables.add(new HorizontalCloud(cloudHoriImg, xpos+((l*ruler.getImgWidth())), ymove + lastypos, speed));
//                    lastxpos = xpos+((l*ruler.getImgWidth())/2);
//                }
//            }
            else if (type == 2){

                xpos = rand.nextInt(screen.getScreenWidth()*2/3);
//                for (int l = 0; l <= length; l++){
//                    collideables.add(new HorizontalCloud(cloudHoriImg, xpos+(l*ruler.getImgWidth()), ymove + lastypos, speed, l));
//                    lastxpos = xpos+((l*ruler.getImgWidth())/2);
//                }
                collideables.add(new HorizontalCloud(cloudHoriImg, xpos, ymove + lastypos, speed));
                lastxpos = xpos+((ruler.getImgWidth())/2);
            }
            else if (type == 3){
                for (int m = 0; m <= length; m++){
                    collideables.add(new BreakCloud(cloudBreakImg, xpos+(m*ruler.getImgWidth()), ymove + lastypos, cloudClearImg));
                    lastxpos = xpos+((m*ruler.getImgWidth())/2);
                }
                collideables.add(new BreakCloud(cloudBreakImg, xpos, ymove+lastypos, cloudClearImg));

                lastbreakable = true;
            }

            if (apple == 2){
                int appley = lastypos + rand.nextInt(ymove);
                int applex = Math.min(xpos, lastxpos) + rand.nextInt(Math.abs(xpos-lastxpos + 1));
                collideables.add(new Apple(appleImg, applex, appley));
            }

            System.out.println("cloud: " + collideables.get(collideables.size()-1).getx() + " " + collideables.get(collideables.size()-1).gety() );
            lastypos +=ymove;
            lastspeed = speed;
        }

    }

    public void makeStars (int starmaker){

       // nonCollideables.sort(Comparator.comparing(GameObject::gety));
        if (nonCollideables.size() > 25){
            for (int i = 0; i < 3; i++ ) {
                System.out.println("remove star y " + nonCollideables.get(0).gety());
                nonCollideables.remove(0);
            }
        }

        for (int i = 0; i < starmaker; i++){
            StarCounter++;
            Random rand = new Random();
            int xstar = rand.nextInt(screen.getScreenWidth()- starImg.getWidth());
            System.out.println("make star at " + xstar + " " + StarCounter*300);
            nonCollideables.add(new Stars(starImg, xstar, StarCounter*(screen.getScreenHeight()/3)));

        }

    }


}
