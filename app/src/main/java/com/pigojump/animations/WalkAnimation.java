package com.pigojump.animations;



import android.graphics.Bitmap;

import com.pigojump.ScreenInfo;

import java.util.ArrayList;

public class WalkAnimation {
    Bitmap Frame1, Frame2, Frame3, Frame4;
    ScreenInfo screen = new ScreenInfo();

    public WalkAnimation(ArrayList<Bitmap> imgs){
        try {
            Frame1 = Bitmap.createScaledBitmap(imgs.get(3), screen.getScreenWidth()/6, screen.getScreenHeight()/12, false);
            Frame2 = Bitmap.createScaledBitmap(imgs.get(4), screen.getScreenWidth()/6, screen.getScreenHeight()/12, false);
            Frame3 = Bitmap.createScaledBitmap(imgs.get(5), screen.getScreenWidth()/6, screen.getScreenHeight()/12, false);
            Frame4 = Bitmap.createScaledBitmap(imgs.get(2), screen.getScreenWidth()/6, screen.getScreenHeight()/12, false);

        }
        catch (Exception e){
            System.out.println("walk load failed");
        }
    }

    public Bitmap returnFrame (int counter, boolean JumpPressed){
        Bitmap img;
        if (JumpPressed){
            img = Frame4;
        }
        else if (counter % 18 < 7){
            img = Frame1;
        }
        else if (counter % 18 <= 12){
            img = Frame2;
        }
        else {
            img = Frame3;
        }

        return img;
    }
}

