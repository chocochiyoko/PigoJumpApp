package com.example.pigojump;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;


import java.util.ArrayList;

public abstract class GameObject {

        protected Bitmap img;
        protected int x, y, height, width, coordy;

    public GameObject (Bitmap img, int x, int y){
            this.img = img;
            this.x = x;
            this.y = y;
            this.height = img.getHeight();
            this.width = img.getWidth();
        }
        public GameRectangle getRect (){
            return new GameRectangle(x, y-img.getHeight(), img.getWidth(), img.getHeight());
        }
        public int getx () {return x;}
        public int gety () {return y; }
        public int getImgHeight (){
            return img.getHeight();
        }
        public int getImgWidth (){
            return img.getWidth();
        }
        public void update (ArrayList<GameObject> objects){}


        public void drawImage (Canvas canvas, int camera){
            coordy = camera-y;
            canvas.drawBitmap(this.img, x, coordy, null);
        }
    }

