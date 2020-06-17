package com.pigojump.animations;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.pigojump.R;
import com.pigojump.ScreenInfo;

import java.util.ArrayList;

public class IdleAnimation {
    private Bitmap Frame1, Frame2, Frame3;
    private ScreenInfo screen = new ScreenInfo();
    int length = 10;
    public IdleAnimation(ArrayList<Bitmap> imgs){

        Frame1 = Bitmap.createScaledBitmap(imgs.get(0), screen.getScreenWidth()/6, screen.getScreenHeight()/12, false);
        Frame2 = Bitmap.createScaledBitmap(imgs.get(1), screen.getScreenWidth()/6, screen.getScreenHeight()/12, false);
        Frame3 = Bitmap.createScaledBitmap(imgs.get(2), screen.getScreenWidth()/6, screen.getScreenHeight()/12, false);
    }

    public Bitmap returnFrame (int counter, boolean space){
        Bitmap img;
        if (space){
            img = Frame3;
        }
        else if (counter % length >= 0 && counter % length <= (length/4)-1 ){
            img = Frame1;
        }
        else if ((counter % length >= length/4 && counter % length <= ((2*length)/4)-1) || (counter % length >= ((length*3)/4) && counter % length <= length-1) ){
            img = Frame2;
        }
        else {
            img = Frame3;
        }
//        if (vx < 0){
//            AffineTransform flip = AffineTransform.getScaleInstance(1, -1);
//
//        }
        return img;
    }
    public Bitmap test (){
        return Frame3;
    }
    public void drawImage (Canvas canvas, int camera){
        canvas.drawBitmap(Frame3, 0, 0, null);
    }
}
