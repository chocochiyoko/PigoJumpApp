//mlab

package com.example.pigojump;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.example.pigojump.MapElements.Clouds.Cloud;
import com.example.pigojump.MapElements.Clouds.HorizontalCloud;
import com.example.pigojump.MapElements.Clouds.VerticalCloud;
import com.example.pigojump.MapElements.Powerup;
import com.example.pigojump.animations.IdleAnimation;
import com.example.pigojump.animations.JumpAnimation;
import com.example.pigojump.animations.WalkAnimation;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.transform.Transformer;

public class Pigo extends GameObject{
    private boolean JumpPressed = false;
    private int RightPressed;
    private int LeftPressed;
    private boolean JumpEnd;
    private int vy = -20, vx = 0, jumpPower = 10, slipspeed = 8;
    private boolean isOnLand = false;
    private int camy;
    public static int counter = 0;
    private int lives = 3, scoreInt = 0, apples = 0, timeOnLand, maxy, lastvx = 0;
    private String scoreString;
    private boolean KO = false, damageTaken, damageStatus, freeFall;
    private WalkAnimation walkanim;
    private IdleAnimation idleanim;
    private JumpAnimation jumpanim;
    private Timer timer = new Timer();
    private ScreenInfo screen = new ScreenInfo();
    private boolean lastvertical = false;
    private int lastvy;
    private Paint borderPaint, fillPaint, textPaint, damagePaint;


    public Pigo(Bitmap img, int x, int y, ArrayList<Bitmap> animationImgs){
        super(img, x, y);
        this.img = img.createScaledBitmap(img, screen.getScreenWidth()/6, screen.getScreenHeight()/12, false);
        jumpanim = new JumpAnimation(animationImgs);
        walkanim = new WalkAnimation(animationImgs);
        idleanim = new IdleAnimation(animationImgs);
        borderPaint = new Paint();
        borderPaint.setColor(Color.WHITE);
        borderPaint.setStrokeWidth(3);
        borderPaint.setStyle(Paint.Style.STROKE);
        fillPaint = new Paint();
        fillPaint.setColor(Color.GREEN);
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(100);
        damagePaint = new Paint();
        damagePaint.setColor(Color.RED);
        damagePaint.setAlpha(100);


    }

    void toggleRightPressed(int i) {
        this.RightPressed = i;
    }

    void toggleLeftPressed(int i) {
        this.LeftPressed = i;
    }


    void ToggleJumpPressed () {this.JumpPressed = true; }

    void unToggleJumpPressed () {this.JumpPressed = false; }

    void ToggleJumpEnd () { this.JumpEnd = true;

        TimerTask jumpEndBuffer = new TimerTask() {
            @Override
            public void run() {
                JumpEnd = false;
            }
        };
        timer.schedule(jumpEndBuffer, 300);
    }

    void unToggleJumpEnd () {this.JumpEnd = false; }

    public int getImageWidth (){
        return img.getWidth();
    }

    public int getvy() {
        return vy;
    }
    public boolean getIsOnLand (){
        return isOnLand;
    }
    public boolean getJumpEnd (){
        return JumpEnd;
    }
    public int getmaxy (){return maxy; }

    public void update(int screenWidth, int screenHeight) {
        counter ++;

        sideAccelerate(screenWidth);
        if (lastvertical){
            vy = lastvy;
        }
        if (lastvertical && counter % 10 == 0 ){
            lastvertical = false;
        }

        if (this.JumpPressed && jumpPower <= screenHeight/48 ) {
            jumpPower ++;
        }

        if (this.JumpPressed && this.JumpEnd){
            this.StartJump(jumpPower, false);
            JumpPressed = false;
        }

        else if (this.JumpEnd){
            jumpPower = 10;
        }

        if (vy >= -(screenHeight/60)){
            vy -= screenHeight/900;
        }

        x+=(vx*.5);
        y+=vy;




        camy = y+screen.getScreenHeight()/2;
        if (isOnLand){
            timeOnLand ++;
        }
        if (y> maxy){
            maxy = y;

        }
        if (maxy-y > 1000){
            freeFall = true;
        }
        if (maxy-y > screenHeight*2){
            KO = true;
        }
        scoreInt = (maxy*10) + apples - timeOnLand;

        //System.out.println(scoreInt);
        scoreString = String.valueOf(scoreInt);

        if (vx != 0){
            lastvx = vx;
        }
        isOnLand = false;


    }
    public void setIsOnLand (boolean land){
        isOnLand = land;
    }
    public void addPoints (int points ){
        apples += points;
    }
    public boolean isFreeFall (){ return freeFall; }

    public void sideAccelerate (int screenWidth){

        if (this.LeftPressed == 1 && vx > -(screenWidth/300)){
            vx -= screenWidth/1000;
        }
        else if (this.RightPressed == 1 && vx < (screenWidth/300)){
            vx += screenWidth/1000;
        }
        if (this.LeftPressed == 2 && vx > -(screenWidth/35)){
            vx -= screenWidth/600;
        }
        else if (this.RightPressed == 2 && vx < (screenWidth/35)){
            vx += screenWidth/600;
        }
        else if (this.RightPressed == 0 && this.LeftPressed == 0){
            if (vx > 0){
                vx -= screenWidth/1000;
            }
            else if (vx < 0){
                vx += screenWidth/1000;
            }
        }
//        if (this.LeftPressed && vx > -(screenWidth/25) && counter % 4 == 0) {
////            if (vx <= 0 && vx > -7){
////
////                vx = -7;
////            }
//            if (vx > (screenWidth/10)){
//                vx -=screenWidth/40;
//            }
//            vx -= screenWidth/300;
//            //vx--;
//        }
//        else if (this.RightPressed && vx < (screenWidth/25) && counter % 4 == 0) {
////            if (vx >= 0 && vx < 7){
////                vx = 7;
////            }
//            if (vx < -(screenWidth/10)){
//                vx += screenWidth/40;
//            }
//            vx += screenWidth/300;
//            //vx++;
//        }
//        else if (!this.RightPressed && vx > 0 && counter % slipspeed == 0){
//            vx -= screenWidth/1000;
//        }
//        else if (!this.LeftPressed && vx < 0 && counter % slipspeed ==0){
//            vx += screenWidth/1000;
//        }
////        else {
////            if ( vx < 0){
////                vx++;
////            }
////            else if (vx > 0){
////                vx--;
////            }
////        }
//

    }

    public void StartJump (int power, boolean override){
        if (isOnLand || override){
            isOnLand = false;
            vy = power;
            jumpPower = 10;
        }
        unToggleJumpPressed();
    }
    public int getImgHeight(){
        return img.getHeight();
    }
    public void allCollisions(ArrayList<GameObject> objects){

        for (int i = 0; i < objects.size(); i++){
            if (vy <= 0 && ((y-img.getHeight() - objects.get(i).gety() <= (objects.get(i).getImgHeight()/2)
                        && (y-img.getHeight() - objects.get(i).gety() >= 0))
                    || (y-img.getHeight() - objects.get(i).gety() >= -(objects.get(i).getImgHeight()/2)
                         &&  (y-img.getHeight() - objects.get(i).gety() <= 0)   ))
                    && getRect().intersection(objects.get(i).getRect()).getWidth() > screen.getScreenWidth()/650
                    && objects.get(i) instanceof VerticalCloud){
                y = objects.get(i).gety()+img.getHeight();
                vy = 0;
                isOnLand = true;
            }
            else if (vy <= 0 && ((y-img.getHeight() - objects.get(i).gety() <= (objects.get(i).getImgHeight()/4)
                    && (y-img.getHeight() - objects.get(i).gety() >= 0))
                    || (y-img.getHeight() - objects.get(i).gety() >= -(objects.get(i).getImgHeight()/4)
                    &&  (y-img.getHeight() - objects.get(i).gety() <= 0)   ))
                    && getRect().intersection(objects.get(i).getRect()).getWidth() > screen.getScreenWidth()/300
                    && objects.get(i) instanceof Cloud) {
                isOnLand = true;
                vy = 0;
                y += getRect().intersection(objects.get(i).getRect()).getHeight();



                if (objects.get(i) instanceof HorizontalCloud){
                    x += ((HorizontalCloud) objects.get(i)).getv() ;
//

                }

            }
            else if (vy <= -screen.getScreenHeight()/100 && ((y-img.getHeight() - objects.get(i).gety() <= (objects.get(i).getImgHeight()*2)
                    && (y-img.getHeight() - objects.get(i).gety() >= 0))
                    )
                    && getRect().intersection(objects.get(i).getRect()).getWidth() > screen.getScreenWidth()/300
                    && objects.get(i) instanceof Cloud){
                vy = 0;
                y = objects.get(i).gety()+this.getImgHeight();
            }
        }
        camy = y+screen.getScreenHeight()/2;
    }
    public void checkBorder(int width) {

        if (x < 0) {
            x = 3;
            vx = 5;

            takeDamage();
        }
        if (x >= width - img.getWidth()) {
            x = width- img.getWidth()-3;
            vx = -5;
            takeDamage();
        }

    }
    public int getcamy (){
        return camy;
    }

    public int getlives(){
        return lives;
    }
    public boolean isKO(){
        return KO;
    }

    public int getScore () {
        return scoreInt;
    }

    public void takeDamage(){
        if (!damageTaken){
            lives --;
            System.out.println(" lost life");

            if (lives == 0){
                KO = true;
            }
        }

        damageTaken = true;
        TimerTask damageTimer = new TimerTask() {
            @Override
            public void run() {
                damageTaken = false;
            }
        };
        timer.schedule(damageTimer, 2000);
    }
    public boolean getDamageStatus (){
        return damageTaken;
    }

    public void drawImage (Canvas canvas, int camera){
        coordy = camera-y;
        Matrix outputMatrix = new Matrix();
        outputMatrix.postTranslate(x, coordy);

        if ((lastvx < 0 || LeftPressed > 0 ) && RightPressed == 0){

            outputMatrix.postScale(-1,1, x + img.getWidth()/2, screen.getScreenHeight());

        }

        if (isOnLand && vx==0){

            img = idleanim.returnFrame(counter, JumpPressed);

        }


        if (isOnLand && vx!=0){
            img = walkanim.returnFrame(counter, JumpPressed);
        }
        if (isOnLand && vx==0){
            img = idleanim.returnFrame(counter, JumpPressed);
        }
        else if (!isOnLand) {
            img = jumpanim.returnFrame(counter, vy);
        }
        canvas.drawBitmap(this.img, outputMatrix, null);

        canvas.drawRect(x, coordy-screen.getScreenHeight()/40, x+img.getWidth()+6, coordy, borderPaint);
        canvas.drawRect(x, coordy-screen.getScreenHeight()/40, x+((jumpPower-10)*img.getWidth()/((screen.getScreenHeight()/48)-10)), coordy, fillPaint);
        canvas.drawText(scoreString, 10, 100, textPaint);

        if (damageTaken){
            canvas.drawRect(0, 0, screen.getScreenWidth(), screen.getScreenHeight(), damagePaint);
        }


    }
//    public void drawImage (Graphics g, int camera){
//        coordy = camera-y;
//        AffineTransform flip = AffineTransform.getTranslateInstance(x, coordy);
//
//        if ((lastvx < 0 || LeftPressed) && !RightPressed){
//            flip.scale(-1, 1);
//            flip.translate(-img.getWidth(), 0);
//        }
//
//        if (isOnLand && vx!=0){
//            img = walkanim.returnFrame(counter, JumpPressed);
//        }
//        else if (isOnLand && vx==0){
//            img = idleanim.returnFrame(counter, JumpPressed);
//        }
//        else if (!isOnLand) {
//            img = jumpanim.returnFrame(counter, vy);
//        }
//        Graphics2D g2d = (Graphics2D) g;
//
//        g2d.drawImage(this.img, flip, null);
//
//        //super.drawImage(g, camera);
//        g.setColor(Color.green);
//        g.fillRect(x, coordy-15, ((jumpPower-10)*img.getWidth()/30), 10);
//        g.setColor(Color.white);
//        g.drawRect(x, coordy-15, img.getWidth(), 10);
//        g.setFont(new Font("Lucida Handwriting", Font.BOLD, 20));
//        g.drawString(scoreString, 0, 50);
//        g.setColor(new Color(200, 0, 0 , 100));
//        if (damageTaken){
//            g.fillRect(0, 0, 800, 800);
//        }

//    }



}
