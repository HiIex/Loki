package com.example.md.loki;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.ViewGroup;

import static java.lang.Math.cos;
import static java.lang.Math.log;
import static java.lang.Math.sin;

public class LightingLoki extends ShapeLoki {

    private final static String TAG="LightingLoki";

    private Path mPath;
    private float mLength;//小Loki的bounds的边长
    private final int index;
    private float mLeft;
    private float mTop;

    public LightingLoki(int index){
        this.index=index;
    }

    public void setLength(float length) {
        this.mLength = length;
        Log.d(TAG, "setLength: length= "+mLength);
        initPath();
    }

    private void initPath(){
        if(getBounds()!=null){
            float x1,y1;
            float x2,y2;
            float x3,y3;
            float x4,y4;
            float x5,y5;
            float x6,y6;
            x1=mLeft+mLength/2f;
            y1=mTop;
            Log.d(TAG, "init length= "+mLength);
            Log.d(TAG,"init mLeft= "+ mLeft);
            Log.d(TAG,"init mTop= "+ mTop);
            x2= (float) (x1-mLength/3f*sin(4));
            y2= (float) (y1+mLength/3f*cos(4));
            x3= (float) (x2+mLength/10f*cos(20));
            y3= (float) (y2+mLength/10f*sin(20));
            x4= (float) (x3+mLength/3f*sin(4));
            y4= (float) (y3+mLength/3f*cos(4));
            x5= (float) (x4+mLength/3f*sin(4));
            y5=y3;
            x6= (float) (x2+mLength/3f*sin(4)*2);
            y6=y2;
            mPath=new Path();
            mPath.moveTo(x1,y1);
            mPath.lineTo(x2,y2);
            mPath.lineTo(x3,y3);
            mPath.lineTo(x4,y4);
            mPath.lineTo(x5,y5);
            mPath.lineTo(x6,y6);
            mPath.lineTo(x1,y1);
            mPath.close();
        }

    }


    @Override
    protected void drawShape(Canvas canvas, Paint paint) {
        if(getBounds()!=null){
            paint.setStyle(Paint.Style.FILL);
            //paint.setColor(Color.parseColor("#FFE39BEF"));
            setColor(Color.parseColor("#FFE39BEF"));
            paint.setShadowLayer(15,4,4, Color.parseColor("#80760588"));
            paint.setMaskFilter(new BlurMaskFilter(15, BlurMaskFilter.Blur.SOLID));
            canvas.drawPath(mPath,paint);
        }
    }

    @Override
    protected void reset() {
        resetAll();
    }

    @Override
    protected Animator onCreateAnimator() {
        PropertyValuesHolder rotateHolder=PropertyValuesHolder.ofInt("Rotate",36*index,1080+36*index);
        ObjectAnimator objectAnimator=ObjectAnimator.ofPropertyValuesHolder(this,rotateHolder);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        return objectAnimator;
    }

    public float getLength() {
        return mLength;
    }

    public float getLeft() {
        return mLeft;
    }

    public void setLeft(float left) {
        this.mLeft = left;
    }

    public float getTop() {
        return mTop;
    }

    public void setTop(float top) {
        this.mTop = top;
    }

}
