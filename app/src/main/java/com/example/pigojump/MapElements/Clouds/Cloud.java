package com.example.pigojump.MapElements.Clouds;

import android.graphics.Bitmap;

import com.example.pigojump.GameObject;
import com.example.pigojump.ScreenInfo;

public abstract class Cloud extends GameObject {

    private ScreenInfo screen = new ScreenInfo();
    private int height, width;
    
    public Cloud(Bitmap img, int x, int y) {
        super(img, x, y);
        height = screen.getScreenHeight()/40;
        width = screen.getScreenWidth()/screen.getCloudSpace();
        this.img = img.createScaledBitmap(img, width, height, false);

    }
    public int getv(){return 0;};
}

