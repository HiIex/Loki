package com.example.md.loki;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.text.BoringLayout;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.md.R;

import java.util.ArrayList;

/**
 * 最小动画单元Drawable类
 * @author Hilex
 * @date 2021-2-18
 * @version 1.0
 */
public abstract class Loki extends Drawable implements ValueAnimator.AnimatorUpdateListener, Animatable,Drawable.Callback {

    private static final String TAG="Loki";

    private float scale=1;
    private float scaleX=1;
    private float scaleY=1;

    private float pivotX;
    private float pivotY;

    private int rotate;
    private int rotateX;
    private int rotateY;

    private int dRotate;

    private int translateX;
    private int translateY;
    private int translatePercentageX;
    private int translatePercentageY;

    private int alpha=255;

    private ArrayList<Integer> mAnimationDelays;//对付动画集的
    private ArrayList<Integer> mDurations;//还是对付动画集的

    protected Animator mAnimator;
    private Rect mRect;
    private Camera mCamera;
    private Matrix mMatrix;

    public int mColor;

    public Loki(){
        mCamera=new Camera();
        mMatrix=new Matrix();
        mAnimationDelays=new ArrayList<>();
    }

    protected abstract Animator onCreateAnimator();

    protected abstract void reset();

    protected abstract void drawSelf(Canvas canvas);

    @Override
    public void start() {
        if(AnimatorUtil.isStarted(mAnimator)){
            return;
        }
        initAnimator();
        if(mAnimator==null){
            Log.d(TAG, "start: animator is null");
            return;
        }
        AnimatorUtil.start(mAnimator);
        invalidateSelf();
    }

    @Override
    public void stop() {
        if (AnimatorUtil.isStarted(mAnimator)) {
            if(mAnimator instanceof ValueAnimator){
                ((ValueAnimator) mAnimator).removeAllUpdateListeners();
            }else if(mAnimator instanceof AnimatorSet){
                for(Animator animator:((AnimatorSet)mAnimator).getChildAnimations()){
                    ((ValueAnimator)animator).removeAllUpdateListeners();
                }
            }
            mAnimator.end();
            reset();
        }
    }

    @Override
    public boolean isRunning() {
        return AnimatorUtil.isRunning(mAnimator);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        setLokiBoundsAndPivot(bounds);
    }

    @Override
    public void invalidateDrawable(Drawable who) {
        invalidateSelf();
    }

    @Override
    public void scheduleDrawable(Drawable who, Runnable what, long when) {

    }

    @Override
    public void unscheduleDrawable(Drawable who, Runnable what) {

    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Log.d(TAG, "draw: ");
        translateX=translateX==0?(int)(getBounds().width()*translatePercentageX):translateX;
        translateY=translateY==0?(int)(getBounds().width()*translatePercentageY):translateY;
        canvas.translate(translateX,translateY);
        canvas.scale(scaleX,scaleY, pivotX, pivotY);
        canvas.rotate(rotate, pivotX, pivotY);

        if(rotateX!=0||rotateY!=0){
            mCamera.save();
            mCamera.rotateX(getRotateX());
            mCamera.rotateY(getRotateY());
            mCamera.getMatrix(mMatrix);
            mMatrix.preTranslate(-pivotX, -pivotY);
            mMatrix.postTranslate(pivotX, pivotY);
            mCamera.restore();
            canvas.concat(mMatrix);
        }
        drawSelf(canvas);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    /**
     * 按最小损耗把rect剪成正方形
     * @param rect rect
     * @return 正方形rect
     */
    protected Rect clipSquare(Rect rect) {
        int w = rect.width();
        int h = rect.height();
        int min = Math.min(w, h);
        int cx = rect.centerX();
        int cy = rect.centerY();
        int r = min / 2;
        return new Rect(cx - r, cy - r, cx + r, cy + r);
    }

    /**
     * 设置bound的同时，将bound的中心设为锚点;在canvas上画画时用到该rect
     * @param bounds rect
     */
    public void setLokiBoundsAndPivot(Rect bounds){
        mRect=new Rect(bounds.left,bounds.top,bounds.right,bounds.bottom);
        pivotX =mRect.centerX();
        pivotY =mRect.centerY();
    }

    public void setLokiBoundsAndPivot(float left,float top,float right,float bottom){
        mRect=new Rect((int)left,(int)top,(int)right,(int)bottom);
        pivotX=(left+right)/2f;
        pivotY=(top+bottom)/2f;
    }

    public void setLokiBoundsAndPivot(int left,int top,int right,int bottom){
        mRect=new Rect(left,top,right,bottom);
        pivotX=(left+right)/2f;
        pivotY=(top+bottom)/2f;
    }

    protected void setLokiBounds(int left,int top,int right,int bottom){
        mRect=new Rect(left,top,right,bottom);
    }

    protected void setLokiBounds(Rect rect){
        mRect=rect;
    }

    public Rect getLokiBounds(){
        return mRect;
    }


    /**
     * 重置所有动画值，但不包括锚点
     */
    protected void resetAll(){
        scale=1;
        scaleX=1;
        scaleY=1;
        rotate=0;
        rotateX=0;
        rotateY=0;
        translatePercentageX=0;
        translatePercentageY=0;
        translateX=0;
        translateY=0;
        alpha=255;
    }

    protected void resetScale(){
        scale=1;
        scaleX=1;
        scaleY=1;
    }

    protected void resetRotate(){
        rotate=0;
        rotateX=0;
        rotateY=0;
    }

    protected void resetTranslate(){
        translatePercentageX=0;
        translatePercentageY=0;
        translateX=0;
        translateY=0;
    }

    protected void resetAlpha(){
        alpha=255;
    }


    /**
     * 初始化Animator，得到实例，并设置启动时延、监听器等
     */
    private void initAnimator(){
        if(mAnimator==null){
            mAnimator=onCreateAnimator();
            setDuration(mDurations);
        }
        if(mAnimator!=null){
            if(mAnimator instanceof ValueAnimator){
                mAnimator.setStartDelay(mAnimationDelays.get(0)==null?0:mAnimationDelays.get(0));
                if(mAnimator instanceof ObjectAnimator){
                    ((ObjectAnimator) mAnimator).addUpdateListener(this);
                }else{
                    ((ValueAnimator) mAnimator).addUpdateListener(this);
                }
            }else if(mAnimator instanceof AnimatorSet){
                setAnimationSetDelayAndListener();
            }
        }
    }

    /**
     * animator是AnimatorSet类时调用，分别设置动画集里各动画的启动延迟
     */
    private void setAnimationSetDelayAndListener(){
        if(mAnimator!=null&&mAnimator instanceof AnimatorSet){
            if(mAnimationDelays.size()!=((AnimatorSet) mAnimator).getChildAnimations().size()){
                Log.w(TAG, "setAnimationDelay: The size of animatorSet and animatorDelays do not match!" );
                return;
            }
            for(int i=0;i<mAnimationDelays.size();i++){
                Animator animator=((AnimatorSet) mAnimator).getChildAnimations().get(i);
                if(animator!=null){
                    animator.setStartDelay(mAnimationDelays.get(i)==null?0:mAnimationDelays.get(i));
                    ((ValueAnimator)animator).addUpdateListener(this);
                }
            }
        }
    }




    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
        this.scaleX=scale;
        this.scaleY=scale;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public float getPivotX() {
        return pivotX;
    }

    public void setPivotX(float pivotX) {
        this.pivotX = pivotX;
    }

    public float getPivotY() {
        return pivotY;
    }

    public void setPivotY(float pivotY) {
        this.pivotY = pivotY;
    }

    public int getRotate() {
        return rotate;
    }

    public void setRotate(int rotate) {
        this.rotate = rotate;
    }

    public int getRotateX() {
        return rotateX;
    }

    public void setRotateX(int rotateX) {
        this.rotateX = rotateX;
    }

    public int getRotateY() {
        return rotateY;
    }

    public void setRotateY(int rotateY) {
        this.rotateY = rotateY;
    }

    public int getTranslateX() {
        return translateX;
    }

    public void setTranslateX(int translateX) {
        this.translateX = translateX;
    }

    public int getTranslateY() {
        return translateY;
    }

    public void setTranslateY(int translateY) {
        this.translateY = translateY;
    }

    public int getTranslatePercentageX() {
        return translatePercentageX;
    }

    public void setTranslatePercentageX(int translatePercentageX) {
        this.translatePercentageX = translatePercentageX;
    }

    public int getTranslatePercentageY() {
        return translatePercentageY;
    }

    public void setTranslatePercentageY(int translatePercentageY) {
        this.translatePercentageY = translatePercentageY;
    }

    public void setAnimationDelays(ArrayList<Integer> mAnimationDelays) {
        this.mAnimationDelays = mAnimationDelays;
    }

    @Override
    public int getAlpha() {
        return alpha;
    }

    @Override
    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public Animator getAnimator() {
        return mAnimator;
    }

    public void setAnimator(Animator mAnimator) {
        this.mAnimator = mAnimator;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public int getDRotate() {
        return dRotate;
    }

    public void setDRotate(int dRotate) {
        this.dRotate = dRotate;
        rotate=(rotate+dRotate)%360;
    }

    protected void setDuration(ArrayList<Integer> durations) {
        if(mAnimator!=null&&durations.size()>0){
            if(mAnimator instanceof ValueAnimator){
                mAnimator.setDuration(durations.get(0));
            }else if(mAnimator instanceof AnimatorSet){
                if(durations.size()==((AnimatorSet) mAnimator).getChildAnimations().size()){
                    ArrayList<Animator> animators=((AnimatorSet) mAnimator).getChildAnimations();
                    for(int i=0;i<durations.size();i++){
                        animators.get(i).setDuration(durations.get(i));
                    }
                }else{
                    Log.w(TAG, "setDuration: The size of Durations and Animators do not match!" );
                }

            }
        }
    }

    public void setDurations(ArrayList<Integer> durations){
        mDurations=durations;
    }
}
