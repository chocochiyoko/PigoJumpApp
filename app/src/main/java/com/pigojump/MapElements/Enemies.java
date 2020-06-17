package com.pigojump.MapElements;

import android.graphics.Bitmap;

import com.pigojump.GameObject;
import com.pigojump.Pigo;

public class Enemies extends GameObject {
    public Enemies(Bitmap img, int x, int y) {
        super(img, x, y);
    }
    public void killedBy(Pigo pigo){
    }
//    public void paintComponent(Graphics g){
//
//    }
}
