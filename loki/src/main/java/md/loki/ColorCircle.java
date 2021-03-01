package md.loki;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;

public class ColorCircle  extends LokiGroup{

    private final static String TAG="ColorCircle";

    @Override
    public ArrayList<Loki> onCreateChild() {
        ArrayList<Loki> lokis=new ArrayList<>();
        for(int i=0;i<10;i++){
            Loki loki=new ColorCircleLoki();
            ArrayList<Integer> delays=new ArrayList<>();
            delays.add(i*130);
            loki.setAnimationDelays(delays);
            lokis.add(loki);
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
        int cx = (bounds.right + bounds.left) / 2;
        int cy = (bounds.top + bounds.bottom) / 2;
        //大圆的半径,小圆在7/8的大圆圆周上跑
        int r = (bounds.bottom - bounds.top) / 2;
        for(int i=0;i<getChildCount();i++){
            ColorCircleLoki colorCircleLoki =(ColorCircleLoki)getChildAt(i);
            //每个小圆的实时半径
            int sr= r /9;
            colorCircleLoki.setRadius(r /9);
            Log.d(TAG, "onBoundsChange: r= "+ r);
            //每个小圆的实时圆心坐标
            int pr= r /9*8;
            int scx=(int)(cx);
            int scy=(int)(cy -pr);
            colorCircleLoki.setLokiBounds(scx-sr,scy-sr,scx+sr,scy+sr);
            colorCircleLoki.setPivotX(cx);
            colorCircleLoki.setPivotY(cy);
        }
    }

    private static class ColorCircleLoki extends CircleLoki{

        public ColorCircleLoki(){
        }

        public ColorCircleLoki(int r){
            this.mRadius=r;
        }

        @Override
        public Animator onCreateAnimator() {
            //绝对转角
            PropertyValuesHolder rotateHolder=PropertyValuesHolder.ofInt("Rotate",0,120,180,240,360,360);
            PropertyValuesHolder colorHolder=PropertyValuesHolder.ofInt("Color"
                    , Color.parseColor("#FF037FE4"),Color.parseColor("#FF84F81F"),Color.parseColor("#FFFCEB3B")
                    ,Color.parseColor("#FF84F81F"),Color.parseColor("#FF0370E4"),Color.parseColor("#FF0370E4"));
            colorHolder.setEvaluator(new ArgbEvaluator());
            ObjectAnimator objectAnimator=ObjectAnimator.ofPropertyValuesHolder(this,rotateHolder,colorHolder);
            //开始和结束的时候慢，中间加速
            objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            //动画周期:1300+130*10
            objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
            objectAnimator.setRepeatMode(ObjectAnimator.RESTART);
            return objectAnimator;
        }

    }
}
