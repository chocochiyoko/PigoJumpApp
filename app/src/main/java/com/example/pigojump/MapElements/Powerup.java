package com.example.pigojump.MapElements;

import android.graphics.Bitmap;

import com.example.pigojump.GameObject;
import com.example.pigojump.Pigo;

import java.util.ArrayList;

public class Powerup extends GameObject {

    public Powerup(Bitmap img, int x, int y) {
        super(img, x, y);
    }
    public void remove (ArrayList<GameObject> objects){
        objects.remove(this);
    }
    public void power (Pigo pigo){
    }
}
