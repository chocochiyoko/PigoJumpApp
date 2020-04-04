package com.example.pigojump.MapElements;

import android.graphics.Bitmap;

import com.example.pigojump.Pigo;

public class Apple extends Powerup{

    int points= 10000;
    public Apple(Bitmap img, int x, int y) {
        super(img, x, y);

    }

    public void power (Pigo pigo){
        pigo.addPoints(points);
        System.out.println("apple get!");
    }
}
