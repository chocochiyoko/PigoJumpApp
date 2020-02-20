package com.example.pigojump.MapElements.Clouds;

import android.graphics.Bitmap;

import com.example.pigojump.GameObject;

import java.util.ArrayList;

public class VerticalCloud extends Cloud {
    private int vy;
    private int distance = screen.getScreenHeight()/5;
    private int starty;
    private boolean bound;

    public VerticalCloud(Bitmap img, int x, int y, int speed) {
        super(img, x, y);
        starty = y;
        vy = speed * screen.getScreenHeight()/350;
    }
    public void update(ArrayList<GameObject> clouds){
        y += vy;
        if ( y <= starty ){
            vy = vy*-1;
        }
        else if ( y >= starty + distance){
            vy = vy*-1;
        }

        if ( y == starty || y == starty + distance){
            bound = true;
        }
        else {
            bound = false;
        }
    }
    public boolean getbound (){
        return bound;
    }
    public int getv(){
        return vy;
    }
}
