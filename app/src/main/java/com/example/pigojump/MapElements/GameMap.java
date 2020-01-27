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

public class GameMap {
    private Bitmap background, cloudNormalImg, cloudHoriImg, cloudVertImg, cloudClearImg, cloudappleImg, starImg, cloudBreakImg, bugImg;
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

    public GameMap(ArrayList<Bitmap> images){
        try {
            cloudNormalImg = images.get(0);
            cloudHoriImg = images.get(1);
            cloudVertImg = images.get(2);
            cloudBreakImg = images.get(3);
            cloudClearImg = images.get(4);


//
//            cloudNormalImg = BitmapFactory.decodeResource(SV.getResources(), R.drawable.cloudpurple);
//            cloudHoriImg = BitmapFactory.decodeResource(SV.getResources(), R.drawable.cloudblue);
//            cloudVertImg = BitmapFactory.decodeResource(SV.getResources(), R.drawable.cloudorange);
//            cloudBreakImg = BitmapFactory.decodeResource(SV.getResources(), R.drawable.cloudyellow);
//            appleImg = ImageIO.read(getClass().getResource("/resources/apple.png"));
//            starImg = ImageIO.read(getClass().getResource("/resources/star.png"));
//            bugImg = ImageIO.read(getClass().getResource("/resources/bugo.png"));

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
    public void drawElements (Canvas canvas, int camera){

        for (int i = 0; i< nonCollideables.size(); i++){
            nonCollideables.get(i).drawImage(canvas, camera);
        }

        for (int i = 0; i < collideables.size(); i++){
            collideables.get(i).update(collideables);
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

            if (Math.abs(pigo.gety() - pigo.getImgHeight() - object.gety()) <=3
                    && (pigo.getx() + 5 >= object.getx() - pigo.getImgWidth())
                    && (pigo.getx() - 5 <= object.getx() + cloudNormalImg.getWidth())
                    && pigo.getvy() <= 0
                    && object instanceof Cloud) {
                pigo.setIsOnLand(true);
            }

//            if (Math.abs(pigo.gety() - pigo.getImgHeight() - object.gety()) <= 20
//                    && (pigo.getx() + 5 >= object.getx() - pigo.getImgWidth())
//                    && (pigo.getx() - 5 <= object.getx() + cloudNormalImg.getWidth())
//                    && object instanceof Enemies
//                    && pigo.getvy() <= 0
//                    && pigo.getJumpEnd()
//            ) {
//
//                ((Enemies) object).killedBy(pigo);
//                toRemove = object;
//                TimerTask removeEnemy= new TimerTask() {
//                    @Override
//                    public void run() {
//                        collideables.remove(toRemove);
//                        i[0]++;
//                        toRemove = null;
//                    }
//                };
//                timer.schedule(removeEnemy, 3000);
//            }
//
//            else if (object instanceof Enemies
//                    && pigo.getRect().intersects(smallerRect)){
//
//                pigo.takeDamage();
//
//            }
            if (pigo.getRect().intersects(object.getRect())) {
                ReturnVal.add(object);}
//                if (object instanceof PowerUps) {
//                    ((PowerUps) object).power(pigo);
//                    ((PowerUps) object).remove(collideables);
//
//                }
//                else if (object instanceof BreakCloud && pigo.getIsOnLand()
//                        &&  !((BreakCloud) object).isRunning()){
//                    ((BreakCloud) object).disappear(collideables, pigo);
//                    i[0]++;
//                }
//
//
////                if (i[0] % (collideables.size()*0.6) >= (collideables.size()*0.6)-10) {
////                    System.out.println("remake at on cloud y " + object.gety());
////                    remakeClouds();
////                    makeStars(maker);
////                }
//                if (i[0] > collideables.size()*0.5) {
//                    System.out.println("remake at on cloud y " + object.gety());
//                    makeElements(maker);
//                    makeStars(maker);
//                }
//
//
//            }
//            if (pigo.getmaxy()-collideables.get(i[0]).gety() > 1000){
//                collideables.remove(i[0]);
//            }
//



        }


        return ReturnVal;
    }

    public void makeElements (int cloudMaker){
        Random rand = new Random();



        for (int i = 0; i < cloudMaker; i++){
            //System.out.println("make cloud " + i);
            int type = rand.nextInt(4);
            int length = rand.nextInt(3);
            int speed = 1 + rand.nextInt(2);
            int apple = rand.nextInt(10);
            int xlimit = 350;
            if (lastspeed == speed){
                speed ++;
            }
            if (lastbreakable){
                type = 0;
                lastbreakable = false;
                xlimit = 300;
            }
            if (lastvertical){
                ymove = screen.getScreenHeight()/4 +rand.nextInt(200);
                lastvertical = false;
            }
            else {
                ymove = screen.getScreenHeight()/4 + rand.nextInt(130);
            }

//            do {
//                xpos = rand.nextInt(screen.getScreenWidth()-((1+ length) * cloudNormalImg.getWidth()));
//            } while (Math.abs(xpos-lastxpos) > xlimit);

//            if (apple >5 && lastEnemy + 1000 < lastypos  ){
//
//                int enemyy = lastypos + rand.nextInt(ymove);
//                int enemyx = 0;
//                if (xpos < lastxpos){
//                    enemyx = lastxpos+ cloudNormalImg.getWidth() + rand.nextInt(screen.getScreenWidth() - lastxpos+ cloudNormalImg.getWidth()-bugImg.getWidth());
//                }
//                else {
//                    enemyx = rand.nextInt(lastxpos);
//
//                }
//               // collideables.add(new EnemyBug(bugImg, enemyx, enemyy));
//                lastEnemy = enemyy;
//
//            }


            if (type == 0){
                for (int j=0;  j <= length; j++){
                    collideables.add(new NormalCloud(cloudNormalImg, xpos+(j*cloudNormalImg.getWidth()), ymove + lastypos));
                    lastxpos = xpos+((j*cloudNormalImg.getWidth())/2);
                }


            }
            else if (type == 1){
                for (int k = 0; k <= length; k++){
                    collideables.add(new VerticalCloud(cloudVertImg, xpos+(k*cloudNormalImg.getWidth()), ymove + lastypos, speed));
                    lastxpos = xpos+((k*cloudNormalImg.getWidth())/2);
                }
                lastvertical = true;
            }
//            else if (type == 2){
//                for (int l = 0; l <= length; l++){
//                    collideables.add(new HorizontalCloud(cloudHoriImg, xpos+((l*cloudNormalImg.getWidth())), ymove + lastypos, speed));
//                    lastxpos = xpos+((l*cloudNormalImg.getWidth())/2);
//                }
//            }
            else if (type == 2){

                xpos = rand.nextInt(500);

                collideables.add(new HorizontalCloud(cloudHoriImg, xpos, ymove + lastypos, speed));
                lastxpos = xpos+((cloudNormalImg.getWidth())/2);
            }
            else if (type == 3){
                for (int m = 0; m <= length; m++){
                    collideables.add(new BreakCloud(cloudBreakImg, xpos+(m*cloudNormalImg.getWidth()), ymove + lastypos, cloudClearImg));
                    lastxpos = xpos+((m*cloudNormalImg.getWidth())/2);
                }
                collideables.add(new BreakCloud(cloudBreakImg, xpos, ymove+lastypos, cloudClearImg));

                lastbreakable = true;
            }

//            if (apple == 2){
//                int appley = lastypos + rand.nextInt(ymove);
//                int applex = Math.min(xpos, lastxpos) + rand.nextInt(Math.abs(xpos-lastxpos + 1));
//              //  collideables.add(new Apple(appleImg, applex, appley));
//            }

            System.out.println("cloud: " + collideables.get(collideables.size()-1).getx() + " " + collideables.get(collideables.size()-1).gety() );
            lastypos +=ymove;
            lastspeed = speed;
        }

    }
//    public void drawMapFloor(Graphics g) {
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.drawImage(background, 0, 0, null);
//
//
//    }
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
            int xstar = rand.nextInt(750);
            System.out.println("make star at " + xstar + " " + StarCounter*300);
            //nonCollideables.add(new Stars(starImg, xstar, StarCounter*300));

        }

    }


}
