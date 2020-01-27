package com.example.pigojump;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import androidx.constraintlayout.solver.widgets.Rectangle;

public class GameRectangle {
    private int x, y, width, height;
    private Point upperRight, upperLeft, lowerRight, lowerLeft;

    public GameRectangle(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.upperLeft = new Point(x,y);
        this.upperRight = new Point (x + width, y);
        this.lowerLeft = new Point (x, y+height);
        this.lowerRight = new Point (x + width, y+ height);

    }

    public int getX (){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getWidth(){
        return  width;
    }
    public int getHeight(){
        return height;
    }

    public Point getLowerLeft() {
        return lowerLeft;
    }

    public Point getLowerRight() {
        return lowerRight;
    }

    public Point getUpperLeft() {
        return upperLeft;
    }

    public Point getUpperRight() {
        return upperRight;
    }

    public boolean pointInside(Point point){
        boolean inWidth = (point.x > upperLeft.x && point.x < upperRight.x);
        boolean inHeight = (point.y > upperLeft.y && point.y < lowerLeft.x);
        if (inHeight && inWidth){
            return true;
        }
        else return false;
    }
    public boolean intersects (GameRectangle rectangle){

        if (this.upperLeft.x > rectangle.upperRight.x || this.upperRight.x < rectangle.upperLeft.x){
            return false;
        }
        else if (this.upperLeft.y > rectangle.lowerLeft.y || this.lowerLeft.y < rectangle.upperLeft.y){
            return false;
        }
        else {
            return true;
        }

    }
    public GameRectangle intersection (GameRectangle rectangle){

        Point UL = new Point (Math.max(this.upperLeft.x, rectangle.upperLeft.x), Math.max(this.upperLeft.y, rectangle.upperLeft.y));
        Point UR = new Point (Math.min(this.upperRight.x, rectangle.upperRight.x), Math.max(this.upperRight.y, rectangle.upperRight.y));
        Point LL = new Point(Math.max(this.upperLeft.x, rectangle.upperLeft.x), Math.min(this.lowerLeft.y, rectangle.lowerLeft.y));
        Point LR = new Point (Math.min(this.upperRight.x, rectangle.upperRight.x), Math.min(this.lowerRight.y, rectangle.lowerRight.y));

        int height = LL.y-UL.y;
        int width = LR.x-LL.x;

        return new GameRectangle(UL.x, UL.y, width, height);

    }


}
