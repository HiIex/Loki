package md.loki;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

public class DoubleArc extends LokiGroup {

    private final static String TAG="DoubleArc";

    @Override
    public ArrayList<Loki> onCreateChild() {
        ArrayList<Loki> lokis=new ArrayList<>();
        ArrayList<Integer> delays=new ArrayList<>();
        delays.add(0);
        DoubleArcLoki arcCircle=new DoubleArcLoki(-60,90,new PaintBuilder().isAntiAlias(true).strokeWidth(15f).style(Paint.Style.STROKE).build());
        arcCircle.setColor(Color.parseColor("#FF048EFB"));
        DoubleArcLoki arcCircle2=new DoubleArcLoki(60,90,new PaintBuilder().isAntiAlias(true).strokeWidth(15f).style(Paint.Style.STROKE).build());
        arcCircle2.setColor(Color.parseColor("#FF04DBF6"));
        DoubleArcLoki arcCircle3=new DoubleArcLoki(180,90,new PaintBuilder().isAntiAlias(true).strokeWidth(15f).style(Paint.Style.STROKE).build());
        arcCircle3.setColor(Color.parseColor("#FF04CA85"));
        DoubleArcLoki arcCircle4=new DoubleArcLoki(0,90,new PaintBuilder().isAntiAlias(true).strokeWidth(10f).style(Paint.Style.STROKE).build());
        arcCircle4.setColor(Color.parseColor("#FF9C27B0"));
        DoubleArcLoki arcCircle5=new DoubleArcLoki(120,90,new PaintBuilder().isAntiAlias(true).strokeWidth(10f).style(Paint.Style.STROKE).build());
        arcCircle5.setColor(Color.parseColor("#FFE91E63"));
        DoubleArcLoki arcCircle6=new DoubleArcLoki(240,90,new PaintBuilder().isAntiAlias(true).strokeWidth(10f).style(Paint.Style.STROKE).build());
        arcCircle6.setColor(Color.parseColor("#FFF44336"));
        arcCircle.setAnimationDelays(delays);
        arcCircle2.setAnimationDelays(delays);
        arcCircle3.setAnimationDelays(delays);
        arcCircle4.setAnimationDelays(delays);
        arcCircle5.setAnimationDelays(delays);
        arcCircle6.setAnimationDelays(delays);
        lokis.add(arcCircle);
        lokis.add(arcCircle2);
        lokis.add(arcCircle3);
        lokis.add(arcCircle4);
        lokis.add(arcCircle5);
        lokis.add(arcCircle6);
        return lokis;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        bounds=clipSquare(bounds);
        int cx=(bounds.right+bounds.left)/2;
        int cy=(bounds.top+bounds.bottom)/2;
        //外环的rectF是在原来bounds基础上缩小0.1倍
        int deta=(int)((bounds.right-bounds.left)*0.1);
        //内环的rectF在外环的基础上缩小0.25倍
        int deta2=(int)((bounds.right-bounds.left)*0.25);
        for(int i=0;i<getChildCount();i++){
            DoubleArcLoki arcCircle=(DoubleArcLoki) getChildAt(i);
            //arcCircle.setPivotX(cx);
            //arcCircle.setPivotY(cy);
            if(i<=2){
                arcCircle.setLokiBoundsAndPivot(bounds.left+deta,bounds.top+deta,bounds.right-deta,bounds.bottom-deta);
            }else{
                arcCircle.setLokiBoundsAndPivot(bounds.left+deta2,bounds.top+deta2,bounds.right-deta2,bounds.bottom-deta2);
            }
        }
    }

    @Override
    protected void onChildCreated(ArrayList<Loki> lokis) {

    }

    private static class DoubleArcLoki extends ArcLoki {

        public DoubleArcLoki(float startAngle, float sweepAngle, Paint paint) {
            super(startAngle, sweepAngle, paint);
        }

        @Override
        public Animator onCreateAnimator() {
            PropertyValuesHolder scaleHolder=PropertyValuesHolder.ofFloat("Scale",0.5f,1f,0.5f);
            PropertyValuesHolder rotationHolder=PropertyValuesHolder.ofInt("Rotate",0,1440,0);
            ObjectAnimator objectAnimator=ObjectAnimator.ofPropertyValuesHolder(this,scaleHolder,rotationHolder);
            objectAnimator.setInterpolator(input -> {
                if(input<=0.5){
                    return (float) (Math.pow(0.5,1f/2f)*Math.pow(input,1f/2f));
                }else{
                    return (float) (0.5+2*Math.pow(input-0.5,2));
                }
            });
            objectAnimator.setRepeatMode(ObjectAnimator.REVERSE);
            objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
            return objectAnimator;
        }
    }
}
