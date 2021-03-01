package com.example.md.loki;

import android.content.Context;
import android.util.AttributeSet;

import java.util.ArrayList;

public class LightingView extends LokiView{

    public LightingView(Context context){
        this(context,null);
    }

    public LightingView(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }

    public LightingView(Context context,AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
    }

    @Override
    protected LokiGroup onCreateLokiGroup() {
        LokiGroup lokiGroup=new Lighting();
        ArrayList<Integer> durations=new ArrayList<>();
        durations.add(5000);
        lokiGroup.setChildDuration(durations);
        return lokiGroup;
    }
}
