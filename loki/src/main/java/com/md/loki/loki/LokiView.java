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

public abstract class LokiView extends ProgressBar{

    private static final String TAG="LokiView";

    protected int mColor;
    private boolean isColorUnify;
    protected LokiGroup mLokiGroup;

    public LokiView(Context context){
        this(context,null);
    }

    public LokiView(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }

    public LokiView(Context context,AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.LokiView,defStyle,0);
        mColor=typedArray.getColor(R.styleable.LokiView_color, Color.parseColor("#FFFFFFFF"));
        isColorUnify=typedArray.getBoolean(R.styleable.LokiView_unifyColor,true);
        typedArray.recycle();

        init();
    }

    protected abstract LokiGroup onCreateLokiGroup();

    private void init(){
        mLokiGroup=onCreateLokiGroup();
        setIndeterminate(true);
        if(mLokiGroup!=null){
            if(isColorUnify){
                mLokiGroup.setGroupColor(mColor);
            }
            setIndeterminateDrawable(mLokiGroup);
        }

    }

    @Override
    public void setIndeterminateDrawable(Drawable d) {
        if(!(d instanceof LokiGroup)){
            throw new IllegalArgumentException("The drawable must be instanced of LokiGroup!");
        }
        setIndeterminateDrawable((LokiGroup) d);
    }

    private void setIndeterminateDrawable(LokiGroup lokiGroup){
        super.setIndeterminateDrawable(lokiGroup);
        mLokiGroup=lokiGroup;
        onSizeChanged(getWidth(),getHeight(),getWidth(),getHeight());
        if(getVisibility()==VISIBLE){
            mLokiGroup.start();
        }
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        switch(visibility){
            case VISIBLE:
                if(mLokiGroup!=null){
                    //setIndeterminateDrawable(mLokiGroup);
                }
                break;
            case INVISIBLE: case GONE:
                if(mLokiGroup!=null){
                    mLokiGroup.stop();
                }
                break;
        }
    }

    @Override
    public Drawable getIndeterminateDrawable() {
        return mLokiGroup;
    }

    @Override
    public void unscheduleDrawable(@NonNull Drawable who, @NonNull Runnable what) {
        super.unscheduleDrawable(who, what);
        if(who instanceof LokiGroup){
            ((LokiGroup) who).stop();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if(hasWindowFocus){
            if(mLokiGroup!=null&&getVisibility()==VISIBLE&&!mLokiGroup.isStart()){
                mLokiGroup.start();
            }
        }
    }

    @Override
    public void onScreenStateChanged(int screenState) {
        super.onScreenStateChanged(screenState);
        if(screenState==SCREEN_STATE_OFF){
            if(mLokiGroup!=null&&mLokiGroup.isRunning()){
                mLokiGroup.stop();
            }
        }
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int mColor) {
        this.mColor = mColor;
    }
}
