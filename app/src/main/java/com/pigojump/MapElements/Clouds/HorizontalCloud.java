package com.pigojump.MapElements.Clouds;

import android.graphics.Bitmap;

import com.pigojump.GameObject;

import java.util.ArrayList;

public class HorizontalCloud extends Cloud {
    private int vx;
    private int distance = screen.getScreenWidth() / 2;
    private int startx, length;
    private int offset, offset2;

    public HorizontalCloud(Bitmap img, int x, int y, int speed) {

        super(img, x, y);
        startx = x;
        vx = (speed * 3) / screen.getscale();
        this.offset = offset * img.getWidth();

    }

    @Override
    public void update(ArrayList<GameObject> clouds) {
        x += vx;
        if (x <= startx) {
            vx = vx * -1;
        } else if (x >= startx + distance) {
            vx = vx * -1;
        } else if (x <= offset) {
            vx = vx * -1;
        } else if (x >= screen.getScreenWidth() - img.getWidth() - screen.getScreenWidth() / 8) {
            vx = vx * -1;
        }

    }

    public int getv() {
        return vx;
    }

}
