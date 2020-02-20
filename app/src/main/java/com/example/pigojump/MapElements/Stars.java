package com.example.pigojump.MapElements;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.example.pigojump.GameObject;
import com.example.pigojump.ScreenInfo;

import java.util.Random;

public class Stars extends GameObject {
    private ScreenInfo screen = new ScreenInfo();
    public Stars (Bitmap img, int x, int y) {
        super(img, x, y);
        height = screen.getScreenHeight()/12;
        width = screen.getScreenWidth()/12;
        this.img = img.createScaledBitmap(img, width, height, false);
        Random rand = new Random();
        int random = rand.nextInt(360);

        Matrix matrix = new Matrix();

        matrix.postRotate(random);

       this.img = Bitmap.createBitmap(this.img, 0, 0, this.img.getWidth(), this.img.getHeight(), matrix, true);

    }
    public void drawImage (Canvas canvas, int camera){
        coordy = camera-y;


        canvas.drawBitmap(this.img, x, coordy, null);
    }
}
