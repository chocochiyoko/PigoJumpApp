package com.pigojump.MapElements;

import android.graphics.Bitmap;

import com.pigojump.Pigo;

public class EnemyBug extends Enemies {

    private int vy = 0;
    public EnemyBug(Bitmap img, int x, int y) {
        super(img, x, y);
    }

    @Override
    public void killedBy(Pigo pigo){
        System.out.println("bug killed by pigo");
        pigo.addPoints(25000);
        pigo.StartJump(70, true);

    }
}
