package com.example.md.loki;

import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

public class Lighting extends LokiGroup{

    private static final String TAG="Lighting";

    @Override
    protected ArrayList<Loki> onCreateChild() {
        ArrayList<Loki> lokis=new ArrayList<>();
        for(int i=0;i<10;i++){
            Loki loki=new LightingLoki(i);
            ArrayList<Integer> delays=new ArrayList<>();
            delays.add(0);
            loki.setAnimationDelays(delays);
            lokis.add(loki);
        }
        return lokis;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        bounds=clipSquare(bounds);
        int cx=bounds.centerX();
        int cy=bounds.centerY();
        float length=(bounds.right-bounds.left)/2f;
        Log.d(TAG, "onBoundsChange: cx= "+cx+"  cy= "+cy);
        for(Loki loki:mLokis){
            loki.setLokiBounds((int)(cx-length/2f),(int)(cy-length),(int)(cx+length/2f),cy);
            loki.setPivotX(cx);
            loki.setPivotY(cy);
            if(loki instanceof LightingLoki){
                ((LightingLoki) loki).setLeft(bounds.left+length);
                ((LightingLoki) loki).setTop(bounds.top+length/2f);
                ((LightingLoki) loki).setLength(length);
                Log.d(TAG, "onBoundsChange: length= "+length);
            }
        }
    }

    @Override
    protected void onChildCreated(ArrayList<Loki> lokis) {

    }
}
