//mlab

package com.pigojump;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.pigojump.MapElements.Clouds.BreakCloud;
import com.pigojump.MapElements.Clouds.Cloud;
import com.pigojump.MapElements.Clouds.HorizontalCloud;
import com.pigojump.MapElements.Clouds.VerticalCloud;
import com.pigojump.MapElements.Powerup;
import com.pigojump.animations.IdleAnimation;
import com.pigojump.animations.JumpAnimation;
import com.pigojump.animations.WalkAnimation;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.transform.Transformer;

public class Pigo extends GameObject{
    private boolean JumpPressed = false;
    private float RightPressed;
    private float LeftPressed;
    private boolean JumpEnd;
    private float vy = 0, vx = 0, jumpPower = 10, friction = 2, lastvx = 0;
    private boolean isOnLand = false;
    private int camy;
    public static int counter = 0;
    private int lives = 3, scoreInt = 0, apples = 0, timeOnLand, maxy;
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
    private Bitmap heart, heart2;
    private int scalecheck, scalecheck2;


    public Pigo(Bitmap img, int x, int y, ArrayList<Bitmap> animationImgs){
        super(img, x, y);
        this.img = img.createScaledBitmap(img, screen.getScreenWidth()/6, screen.getScreenHeight()/12, false);
        this.heart2 = Bitmap.createScaledBitmap(animationImgs.get(12), screen.getScreenWidth()/7, screen.getScreenHeight()/12, false);
        this.heart = Bitmap.createScaledBitmap(animationImgs.get(12), screen.getScreenWidth()/9, screen.getScreenHeight()/15, false);
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
        vy = -10;
        if (screen.getscale()/3 < 1) {
            scalecheck = 1;
            scalecheck2 = 1;
        }
        else {
            scalecheck = screen.getscale()/3;
            scalecheck2 =  2 * (screen.getscale()/3);
        }


    }

    void toggleRightPressed(float i) {
        this.RightPressed = i;
    }

    void toggleLeftPressed(float i) {
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

    public float getvy() {
        return vy;
    }
    public boolean getIsOnLand (){
        return isOnLand;
    }
    public boolean getJumpEnd (){
        return JumpEnd;
    }
    public int getmaxy (){return maxy; }

    public boolean isRestingOn(GameObject platform) {
        boolean isFalling = vy <= 0;
        boolean isVerticallyAligned = Math.abs(y - img.getHeight() - platform.gety()) < 15;
        boolean isLeftAligned = x + getImgWidth() > platform.getx();
        boolean isRightAligned = x < platform.getx() + platform.getImgWidth();
        if (isFalling && isVerticallyAligned && isLeftAligned && isRightAligned ){
            return true;
        }
        return false;

    }
    public boolean isIntersecting(GameObject object) {
        boolean isUpAligned = y + getImgHeight() > object.gety();
        boolean isDownAligned = y < object.gety() + object.getImgHeight();
        boolean isLeftAligned = x + getImgWidth() > object.getx();
        boolean isRightAligned = x < object.getx() + object.getImgWidth();
        if (isDownAligned && isLeftAligned && isRightAligned && isUpAligned) {
            return true;
        }
        else{
            return false;
        }
    }


    public void update(int screenWidth, int screenHeight) {
        counter ++;

        sideAccelerate(screenWidth);
        if (lastvertical){
            vy = lastvy;
        }
        if (lastvertical && counter % 10 == 0 ){
            lastvertical = false;
        }

        if (this.JumpPressed && jumpPower <= (38*(screen.getscale()))) {
            jumpPower += 2;
        }

        if (this.JumpPressed && this.JumpEnd){
            this.StartJump(jumpPower, false);
            JumpPressed = false;
        }

        else if (this.JumpEnd){
            jumpPower = 10;
        }

        if (vy >= -20/screen.getscale()){
            //900

            vy -= 1
            ;
        }

//        x+=(vx*.5);
//        y+=vy*1.5;
        x += vx;
        y+=vy;




        camy = y+screen.getScreenHeight()/2;
        if (isOnLand){
            timeOnLand ++;
        }
        if (y> maxy){
            maxy = y;

        }
        if (maxy-y > screen.getScreenHeight() * 5){
            freeFall = true;
        }
        if (maxy-y > screenHeight*2){
            KO = true;
        }
        scoreInt = (maxy*10) + apples;

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
        if (this.LeftPressed > 0 && vx > -7 / screen.getscale()) {
            if ( vx > 0) {
                vx = 0;
            }
            if (isOnLand && vx <= 0) {
                vx -= (Math.pow(this.LeftPressed/8, 2)) ;
            }
            else if (isOnLand && vx > 0) {
                vx -= vx/5;
                if (vx < 1) {
                    vx = 0;
                }
            }
            else {
                vx -= (this.LeftPressed/5);
            }

        }
        else if (this.RightPressed > 0 && vx < 7 / screen.getscale()) {
            if ( vx < 0) {
                vx = 0;
            }
            if (isOnLand && vx >= 0) {
                vx += (Math.pow(this.RightPressed/8, 2));

            }
            else if (isOnLand && vx < 0) {
                vx -= vx/5;
                if (vx > -1) {
                    vx = 0;
                }
            }
            else {
                vx += (this.RightPressed/5);
            }

        }
        else if (LeftPressed == 0 && RightPressed == 0){
            System.out.println("hello?");
            if (vx <= -1){
                vx -= vx/17;
            }
            else if (vx >= 1) {
                vx -= vx/15;
            }
            else {
                vx = 0;

            }
        }

    }

    public void StartJump (float power, boolean override){
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
    public void allCollisions2 (ArrayList<GameObject> objects) {
        for (int i = 0; i <objects.size(); i++) {

            if (objects.get(i) instanceof HorizontalCloud){
                if (((HorizontalCloud) objects.get(i)).getv() > 0 && vx > 0) {

                }
                else if (((HorizontalCloud) objects.get(i)).getv() < 0 && vx < 0) {

                }
                else {
                    x += ((HorizontalCloud) objects.get(i)).getv();
                }

            }if (objects.get(i) instanceof VerticalCloud) {
                if (vy >=0){
                    y -= 15;
                }
                y += ((VerticalCloud) objects.get(i)).getv();
            }
            if(objects.get(i) instanceof Cloud) {
                vy = 0;
                y = objects.get(i).gety() + img.getHeight();
            }

        }
    }
//
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


        if (isOnLand && Math.abs(vx) > 1){
            img = walkanim.returnFrame(counter, JumpPressed);
        }
        else if (isOnLand){
            img = idleanim.returnFrame(counter, JumpPressed);
        }
        else if (!isOnLand) {
            img = jumpanim.returnFrame(counter, (int) vy);
        }
        canvas.drawBitmap(this.img, outputMatrix, null);

        canvas.drawRect(x, coordy-screen.getScreenHeight()/40, x+img.getWidth()+8, coordy, borderPaint);
        canvas.drawRect(x, coordy-screen.getScreenHeight()/40, x+((jumpPower)*img.getWidth()/(38*(scalecheck))), coordy, fillPaint);
        canvas.drawText(scoreString, 10, 100, textPaint);

        for (int i = 1; i <= lives; i++){
            canvas.drawBitmap(heart, screen.getScreenWidth()-heart.getWidth()*i, 0, null);
        }

        if (damageTaken){
            canvas.drawBitmap(heart2, (screen.getScreenWidth()/2) - heart2.getWidth()/2, (screen.getScreenHeight()/3), null);
        }


    }




}
