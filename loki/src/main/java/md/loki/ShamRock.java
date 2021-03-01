package md.loki;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Rect;

import java.util.ArrayList;

public class ShamRock extends LokiGroup{

    private final static String TAG="ShamRock";

    @Override
    protected ArrayList<Loki> onCreateChild() {
        ArrayList<Loki> lokis=new ArrayList<>();
        for(int i=0;i<3;i++){
            ShamRockLoki shamRockLoki=new ShamRockLoki(i);
            ArrayList<Integer> delays=new ArrayList<>();
            delays.add(0);
            shamRockLoki.setAnimationDelays(delays);
            lokis.add(shamRockLoki);
        }
        return lokis;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        bounds=clipSquare(bounds);
        float length=(bounds.right-bounds.left)/3f;
        int cx=bounds.centerX();
        int cy=bounds.centerY();
        for(Loki loki:mLokis){
            loki.setLokiBounds((int)(cx-length/2f), (int) (cy-length),(int)(cx+length/2f), cy);
            loki.setPivotX(cx);
            loki.setPivotY(cy);
            if(loki instanceof ShamRockLoki){
                ((ShamRockLoki) loki).setLeft(bounds.left+length);
                ((ShamRockLoki) loki).setTop(bounds.top+length/2f);
                ((ShamRockLoki) loki).setLength(length);
            }
        }
    }

    @Override
    protected void onChildCreated(ArrayList<Loki> lokis) {

    }

    private static class ShamRockLoki extends BezierLoki{

        private final int index;

        public ShamRockLoki(int index){
            this.index=index;
        }

        @Override
        protected Animator onCreateAnimator() {
            PropertyValuesHolder rotateHolder=PropertyValuesHolder.ofInt("Rotate",120*index,1080+120*index);
            PropertyValuesHolder scaleHolder=PropertyValuesHolder.ofFloat("Scale",1,0.4f,1);
            ObjectAnimator objectAnimator=ObjectAnimator.ofPropertyValuesHolder(this,rotateHolder,scaleHolder);
            objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
            objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
            return objectAnimator;
        }
    }

}
