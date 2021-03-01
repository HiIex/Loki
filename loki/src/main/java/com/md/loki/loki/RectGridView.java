package com.example.md.loki;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.example.md.R;

import java.util.ArrayList;

/**
 * 3*3小正方形的大小渐变延迟动画
 * @date 2021-2-20
 */
public class RectGridView extends LokiView {

    private static final String TAG="RectGridView";

    public RectGridView(Context context){
        this(context,null);
    }

    public RectGridView(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }

    public RectGridView(Context context,AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
    }

    @Override
    protected LokiGroup onCreateLokiGroup() {
        LokiGroup lokiGroup=new RectGrid();
        ArrayList<Integer> durations=new ArrayList<>();
        durations.add(1300);
        lokiGroup.setChildDuration(durations);
        return lokiGroup;
    }
}
