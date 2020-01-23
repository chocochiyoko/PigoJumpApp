package com.example.pigojump.MapElements.Clouds;

import android.graphics.Bitmap;

import com.example.pigojump.GameObject;

public abstract class Cloud extends GameObject {
   
    
    public Cloud(Bitmap img, int x, int y) {
        super(img, x, y);
    }
    public int getv(){return 0;};
}

