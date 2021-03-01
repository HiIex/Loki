package com.example.md.loki;

import android.animation.Animator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class RectLoki extends ShapeLoki{

    private final static String TAG="RectLoki";

    @Override
    protected void drawShape(Canvas canvas, Paint paint) {
        if(getLokiBounds()!=null){
            canvas.drawRect(getLokiBounds(),paint);
        }
    }

    @Override
    protected Animator onCreateAnimator() {
        return null;
    }

    @Override
    protected void reset() {
        resetAll();
    }
}
