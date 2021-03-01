package com.example.md.loki;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public abstract class ShapeLoki extends Loki{

    private final static String TAG="ShapeLoki";

    protected Paint mPaint;
    private int mAlphaColor;

    public ShapeLoki(){
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
    }


    public ShapeLoki(Paint paint){
        this.mPaint=paint;
    }

    @Override
    protected void drawSelf(Canvas canvas) {
        mPaint.setColor(mAlphaColor);
        //mPaint.setColor(Color.parseColor("#FFE39BEF"));
        Log.d(TAG, "drawSelf: color= "+mAlphaColor);
        drawShape(canvas,mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        super.setAlpha(alpha);
        if(mColor!=0){
            enableAlpha();
        }
    }

    @Override
    public void setColor(int color) {
        super.setColor(color);
        mAlphaColor=color;
    }

    private void enableAlpha(){
        int alpha = getAlpha();
        Log.d(TAG, "enableAlpha: alpha= "+alpha );
        alpha += alpha >> 7;
        final int baseAlpha = mColor >>> 24;
        final int useAlpha = baseAlpha * alpha >> 8;
        mAlphaColor = (mColor << 8 >>> 8) | (useAlpha << 24);
    }

    protected abstract void drawShape(Canvas canvas,Paint paint);

    public void setPaint(Paint paint){
        this.mPaint=paint;
    }

}
