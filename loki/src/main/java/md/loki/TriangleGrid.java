package md.loki;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Rect;

import java.util.ArrayList;


public class TriangleGrid extends LokiGroup {

    @Override
    protected ArrayList<Loki> onCreateChild() {
        ArrayList<Loki> lokis=new ArrayList<>();
        for(int i=0;i<9;i++){
            TriangleGridLoki triangleGridLoki=new TriangleGridLoki();
            ArrayList<Integer> delays=new ArrayList<>();
            delays.add(0);
            if(i==0){
                delays.add(0);
            }
            if(i>=1&&i<=3){
                delays.add(0,200);
                triangleGridLoki.setAnimationDelays(delays);
            }else if(i >= 4){
                delays.add(0,400);
                triangleGridLoki.setAnimationDelays(delays);
            }
            lokis.add(triangleGridLoki);
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
        //每个三角形的边长
        float detaLength=(bounds.right-bounds.left)/3f;
        //每个三角形的高
        float detaHeight= (float) ((bounds.bottom-bounds.top)/6f*Math.pow(3,0.5));
        //第二层y轴坐标
        float y2=bounds.bottom-detaHeight;
        //第三层y轴坐标
        float y3=y2-detaHeight;
        //顶层y轴坐标
        float y4=y3-detaHeight;
        TriangleGridLoki triangleItem0=(TriangleGridLoki) getChildAt(0);
        triangleItem0.setAll(bounds.left,bounds.bottom,bounds.left+detaLength,bounds.bottom,bounds.left+detaLength/2f,y2);
        TriangleGridLoki triangleItem1=(TriangleGridLoki)getChildAt(1);
        triangleItem1.setAll(bounds.left+detaLength/2f,y2,bounds.left+detaLength/2f*3f,y2,bounds.left+detaLength,y3);
        TriangleGridLoki triangleItem2=(TriangleGridLoki) getChildAt(2);
        triangleItem2.setAll(bounds.left+detaLength/2f,y2,bounds.left+detaLength/2f*3f,y2,bounds.left+detaLength,bounds.bottom);
        TriangleGridLoki triangleItem3=(TriangleGridLoki)getChildAt(3);
        triangleItem3.setAll(bounds.left+detaLength,bounds.bottom,bounds.left+2*detaLength,bounds.bottom,bounds.left+detaLength*3f/2f,y2);
        TriangleGridLoki triangleItem4=(TriangleGridLoki) getChildAt(4);
        triangleItem4.setAll(bounds.left+detaLength,y3,bounds.left+2*detaLength,y3,bounds.left+detaLength*3f/2f,y4);
        TriangleGridLoki triangleItem5=(TriangleGridLoki) getChildAt(5);
        triangleItem5.setAll(bounds.left+detaLength,y3,bounds.left+detaLength*2,y3,bounds.left+detaLength*3f/2f,y2);
        TriangleGridLoki triangleItem6=(TriangleGridLoki)getChildAt(6);
        triangleItem6.setAll(bounds.left+detaLength*3f/2f,y2,bounds.left+detaLength*5f/2f,y2,bounds.left+detaLength*2,y3);
        TriangleGridLoki triangleItem7=(TriangleGridLoki) getChildAt(7);
        triangleItem7.setAll(bounds.left+detaLength*3f/2f,y2,bounds.left+detaLength*5f/2f,y2,bounds.left+detaLength*2,bounds.bottom);
        TriangleGridLoki triangleItem8=(TriangleGridLoki)getChildAt(8);
        triangleItem8.setAll(bounds.left+detaLength*2,bounds.bottom,bounds.left+detaLength*3,bounds.bottom,bounds.left+detaLength*5f/2f,y2);
        //将整个LokiGroup的中心设为所有Loki的锚点
        for(int i=0;i<getChildCount();i++){
            getChildAt(i).setPivotX(bounds.left+(bounds.right-bounds.left)/2f);
            getChildAt(i).setPivotY(bounds.bottom-detaHeight);
        }
    }

    private class TriangleGridLoki extends TriangleLoki {

        public TriangleGridLoki(){}

        public TriangleGridLoki(float x1, float y1, float x2, float y2, float x3, float y3) {
            super(x1, y1, x2, y2, x3, y3);
        }

        @Override
        protected Animator onCreateAnimator() {
            PropertyValuesHolder alphaHolder=PropertyValuesHolder.ofInt("Alpha",255,0);
            alphaHolder.setEvaluator((fraction, startValue, endValue) -> {
                if(fraction>=0.1f&&fraction<=0.8f){
                    return (int)(255-255*10f/7f*(fraction-0.1));
                }else {
                    return 0;
                }
            });
            ObjectAnimator objectAnimator1=ObjectAnimator.ofPropertyValuesHolder(this,alphaHolder);
            //在1.25-1.5的时候所有Loki的alpha都是0，在这个时间内进行旋转操作
            //objectAnimator1.setDuration(1500);
            objectAnimator1.setRepeatCount(ValueAnimator.INFINITE);

            PropertyValuesHolder rotateHolder=PropertyValuesHolder.ofInt("Rotate",0,360);
            rotateHolder.setEvaluator(new TypeEvaluator() {
                @Override
                public Object evaluate(float fraction, Object startValue, Object endValue) {
                    if(fraction<11f/36f){
                        return 0;
                    }else if(fraction>=11f/36f&&fraction<13f/18f){
                        return 120;
                    }else if(fraction>=13f/18f){
                        return 240;
                    }
                    return 0;
                }
            });
            ObjectAnimator objectAnimator2=ObjectAnimator.ofPropertyValuesHolder(this,rotateHolder);
            //objectAnimator2.setDuration(4500);
            objectAnimator2.setRepeatCount(ValueAnimator.INFINITE);

            AnimatorSet animatorSet=new AnimatorSet();
            animatorSet.playTogether(objectAnimator1,objectAnimator2);
            return animatorSet;
        }
    }
}
