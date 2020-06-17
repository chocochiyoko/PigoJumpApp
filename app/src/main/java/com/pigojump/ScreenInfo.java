package com.pigojump;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.SurfaceView;

public class ScreenInfo {
    private int screenWidth;
    private int screenHeight;
    private int cloudSpace  = 7;

    public ScreenInfo(){
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    }
    public int getScreenWidth(){
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getCloudSpace(){
        return cloudSpace;
    }
}
