package com.example.md.loki;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.util.Log;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class RectGrid extends LokiGroup {

    private final static String TAG="RectGrid";

    @Override
    protected ArrayList<Loki> onCreateChild() {
        int[] delayArray =new int[]{200,300,400,100,200,300,0,100,200};
        ArrayList<Loki> lokis=new ArrayList<>();
        for(int i=0;i<9;i++){
            RectGirdLoki rectGirdLoki= new RectGirdLoki();
            ArrayList<Integer> delays=new ArrayList<>();
            delays.add(delayArray[i]);
            rectGirdLoki.setAnimationDelays(delays);
            lokis.add(rectGirdLoki);
        }
        return lokis;
    }

    @Override
    protected void onChildCreated(ArrayList<Loki> lokis) {

    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        bounds=clipSquare(bounds);
        Log.d(TAG, "onBoundsChange: "+bounds.left+"  "+bounds.top+"  "+bounds.right+"  "+bounds.bottom);
        float width=bounds.width()/3f;
        float height=bounds.height()/3f;
        for(int i=0;i<9;i++){
            int x=i%3;
            int y=i/3;
            float left=bounds.left+x*width;
            float top=bounds.top+y*height;
            Loki loki=getChildAt(i);
            loki.setLokiBoundsAndPivot(left,top,left+width,top+height);
        }
    }

    private static class RectGirdLoki extends RectLoki{
        @Override
        protected Animator onCreateAnimator() {
            Keyframe keyframe0= Keyframe.ofFloat(0f,1f);
            Keyframe keyframe1= Keyframe.ofFloat(0.35f,0f);
            Keyframe keyframe2=Keyframe.ofFloat(0.7f,1f);
            Keyframe keyframe3=Keyframe.ofFloat(1f,1f);
            PropertyValuesHolder scaleHolder=PropertyValuesHolder.ofKeyframe("Scale",keyframe0,keyframe1,keyframe2,keyframe3);
            ObjectAnimator animator= ObjectAnimator.ofPropertyValuesHolder(this,scaleHolder);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            return animator;
        }
    }

}
