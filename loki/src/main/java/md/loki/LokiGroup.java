package md.loki;

import android.animation.Animator;
import android.graphics.Canvas;
import android.graphics.Rect;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * 基类Loki的集合类
 * @author Hilex
 * @date 2021-2-18
 * @version 1.0
 */
public abstract class LokiGroup extends Loki {

    private static final String TAG="LokiGroup";
    protected ArrayList<Loki> mLokis;

    public LokiGroup(){
        mLokis=onCreateChild();
        initCallback();
        onChildCreated(mLokis);
    }

    protected abstract ArrayList<Loki> onCreateChild();

    protected abstract void onChildCreated(ArrayList<Loki> lokis);

    @Override
    public void draw(@NonNull Canvas canvas) {
        super.draw(canvas);
        drawChild(canvas);
    }

    @Override
    protected void drawSelf(Canvas canvas) {

    }

    @Override
    protected void reset() {
        resetAll();
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        for(Loki loki:mLokis){
            loki.setBounds(bounds);
        }
    }

    @Override
    public void start() {
        super.start();
        AnimatorUtil.start(mLokis);
    }

    @Override
    public boolean isRunning() {
        return super.isRunning()||AnimatorUtil.isRunning(mLokis);
    }

    public boolean isStart(){
        return AnimatorUtil.isStarted(mLokis);
    }

    @Override
    public void stop() {
        super.stop();
        AnimatorUtil.stop(mLokis);
    }

    @Override
    protected Animator onCreateAnimator() {
        return null;
    }

    public void setGroupColor(int color) {
        for(Loki loki:mLokis){
            loki.setColor(color);
        }
    }

    private void drawChild(Canvas canvas){
        if(mLokis!=null){
            for(Loki loki:mLokis){
                int count=canvas.save();
                loki.draw(canvas);
                canvas.restoreToCount(count);
            }
        }
    }

    public int getChildCount(){
        return mLokis==null?0:mLokis.size();
    }

    public Loki getChildAt(int index){
        return mLokis==null?null:mLokis.get(index);
    }

    private void initCallback(){
        if(mLokis!=null){
            for(Loki loki:mLokis){
                if(loki!=null){
                    loki.setCallback(this);
                }
            }
        }
    }

    public ArrayList<Loki> getLokis(){
        return this.mLokis;
    }


    /**
     * 设置每一个子Loki的动画时间
     * @param durations AnimatorSet里的每个动画对应一个时间，如果是ValueAnimator，只取动态数组的第一个值
     */
    protected void setChildDuration(ArrayList<Integer> durations){
        if(mLokis!=null&&durations!=null&&durations.size()>0){
            for(Loki loki:mLokis){
                loki.setDurations(durations);
            }
        }
    }

    protected void setAllDRotate(int rotate){
        if(mLokis!=null){
            for(Loki loki:mLokis){
                loki.setDRotate(rotate);
            }
        }
    }



}
