package com.example.md.loki;

import android.content.Context;
import android.util.AttributeSet;

import java.util.ArrayList;

public class ShamRockView extends LokiView {

    public ShamRockView(Context context){
        this(context,null);
    }

    public ShamRockView(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }

    public ShamRockView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
    }

    @Override
    protected LokiGroup onCreateLokiGroup() {
        LokiGroup lokiGroup=new ShamRock();
        ArrayList<Integer> durations=new ArrayList<>();
        durations.add(3000);
        lokiGroup.setChildDuration(durations);
        return lokiGroup;
    }
}
