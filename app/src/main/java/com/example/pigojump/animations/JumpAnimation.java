package com.example.pigojump.animations;

import android.graphics.Bitmap;

import com.example.pigojump.ScreenInfo;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class JumpAnimation {
    Bitmap JumpDown1, JumpUp3, JumpUp1, JumpUp2, JumpMid1, JumpMid2, JumpDown2, JumpDown3, Land1, Land2;
    ScreenInfo screen = new ScreenInfo();

    public JumpAnimation(ArrayList<Bitmap> imgs){
        try {

            JumpUp1 = Bitmap.createScaledBitmap(imgs.get(6), screen.getScreenWidth()/6, screen.getScreenHeight()/12, false);
            JumpUp2 = Bitmap.createScaledBitmap(imgs.get(7), screen.getScreenWidth()/6, screen.getScreenHeight()/12, false);
            JumpUp3 = Bitmap.createScaledBitmap(imgs.get(8), screen.getScreenWidth()/6, screen.getScreenHeight()/12, false);
            JumpDown2 = Bitmap.createScaledBitmap(imgs.get(9), screen.getScreenWidth()/6, screen.getScreenHeight()/12, false);
            JumpMid1 = Bitmap.createScaledBitmap(imgs.get(10), screen.getScreenWidth()/6, screen.getScreenHeight()/12, false);
            JumpMid2 = Bitmap.createScaledBitmap(imgs.get(11), screen.getScreenWidth()/6, screen.getScreenHeight()/12, false);


        }
        catch (Exception e){
            System.out.println("Failed to load image");
        }
    }
    public Bitmap returnFrame (int counter, int vy){

        Bitmap img = null;

        if (vy < -3){
            img = JumpDown2;
        }
        else if (vy > 3){

            img = JumpUp3;
        }
        else if ((vy <= 15 && vy > 12)){
            img = JumpUp2;
        }
        else if ((vy <= 10 && vy > 3)){
            img = JumpUp1;
        }
        else if ((vy <= 3 && vy >= 0)){
            img = JumpMid1;
        }
        else if ((vy >= -3 && vy <= 0)){
            img = JumpMid2;
        }


        return img;
    }
}
