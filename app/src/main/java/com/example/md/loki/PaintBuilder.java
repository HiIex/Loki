package com.example.md.loki;

import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.util.Log;

public class PaintBuilder {

    private Paint paint;
    private final static String TAG="PaintBuilder";

    public PaintBuilder(){
        paint=new Paint();
    }

    public PaintBuilder color(int color){
        paint.setColor(color);
        Log.d(TAG, "color: "+paint.getColor());
        return this;
    }

    public PaintBuilder colorFilter(ColorFilter colorFilter){
        paint.setColorFilter(colorFilter);
        return this;
    }

    public PaintBuilder isAntiAlias(boolean isAntiAlias){
        paint.setAntiAlias(isAntiAlias);
        return this;
    }

    public PaintBuilder strokeWidth(float strokeWidth){
        paint.setStrokeWidth(strokeWidth);
        return this;
    }

    public PaintBuilder style(Paint.Style style){
        paint.setStyle(style);
        return this;
    }

    public Paint build(){
        return this.paint;
    }


}
