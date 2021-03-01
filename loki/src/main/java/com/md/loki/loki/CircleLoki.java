package com.example.md.loki;

import android.animation.Animator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class CircleLoki extends ShapeLoki {

    protected int mRadius=0;

    private final static String TAG="CircleLoki";

    public CircleLoki(){}

    public CircleLoki(int mRadius){
        this.mRadius=mRadius;
    }

    public int getRadius(){
        return this.mRadius;
    }

    public void setRadius(int radius){
        this.mRadius=radius;
    }

    @Override
    protected Animator onCreateAnimator() {
        return null;
    }

    @Override
    protected void reset() {
        resetAll();
    }

    @Override
    public void drawShape(Canvas canvas, Paint paint) {
        if(getBounds()!=null){
            Rect rect=getLokiBounds();
            canvas.drawCircle(rect.centerX(),rect.centerY(),mRadius,paint);
        }
    }
}
