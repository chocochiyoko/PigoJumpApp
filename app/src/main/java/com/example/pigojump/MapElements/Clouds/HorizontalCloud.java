package com.example.pigojump.MapElements.Clouds;

import android.graphics.Bitmap;

import com.example.pigojump.GameObject;

import java.util.ArrayList;

public class HorizontalCloud extends Cloud{
    private int vx;
    private int distance = 300;
    private int startx, length;
    public HorizontalCloud(Bitmap img, int x, int y, int speed) {

        super(img, x, y);
        startx = x;
        vx = speed;
    }
    @Override
    public void update(ArrayList<GameObject> clouds){
        x += vx;
        if ( x <= startx ){
            vx = vx*-1;
        }
        else if ( x >= startx + distance){
            vx = vx*-1;
        }
        else if ( x <= 0 ){
            vx = vx*-1;
        }
        else if ( x >= 800-img.getWidth() ){
            vx = vx*-1;
        }

    }
    public int getv(){
        return vx;
    }

}
