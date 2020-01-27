package com.example.pigojump.MapElements.Clouds;

import android.graphics.Bitmap;

import com.example.pigojump.GameObject;

import java.util.ArrayList;

public class VerticalCloud extends Cloud {
    private int vy;
    private int distance = 150;
    private int starty;

    public VerticalCloud(Bitmap img, int x, int y, int speed) {
        super(img, x, y);
        starty = y;
        vy = speed;
    }
    public void update(ArrayList<GameObject> clouds){
        y += vy;
        if ( y <= starty ){
            vy = vy*-1;
        }
        else if ( y >= starty + distance){
            vy = vy*-1;
        }

    }
    public int getv(){
        return vy;
    }
}
