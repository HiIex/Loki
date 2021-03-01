package md.loki;

import android.animation.Animator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class BezierLoki extends ShapeLoki {

    private float mLength;//大正方形bound的边长的三分之一
    private float mLeft;
    private float mTop;
    private Path mPath;

    public BezierLoki(){}

    public BezierLoki(float length,float mLeft,float mTop){
        this.mLength =length;
        this.mLeft=mLeft;
        this.mTop =mTop;
        initPath();
    }

    public float getLength() {
        return mLength;
    }

    public void setLength(float mLength) {
        this.mLength = mLength;
        initPath();
    }

    public float getLeft() {
        return mLeft;
    }

    public void setLeft(float mLeft) {
        this.mLeft = mLeft;
    }

    public float getTop() {
        return mTop;
    }

    public void setTop(float mTop) {
        this.mTop = mTop;
    }

    /**
     * 初始化心形曲线
     */
    private void initPath(){
        mPath=new Path();
        mPath.moveTo((float) (mLeft+0.2*mLength), (float) (mTop+0.5*mLength));
        mPath.cubicTo((float)(mLeft+0.2*mLength),(float)(mTop+0.34*mLength),(float)(mLeft+0.4*mLength),(float)(mTop+0.24*mLength),(float)(mLeft+0.5*mLength), (float) (mTop+0.4*mLength));
        mPath.cubicTo(mLeft+0.6f*mLength,mTop+0.24f*mLength,mLeft+0.8f*mLength,mTop+0.34f*mLength,mLeft+0.8f*mLength,mTop+0.5f*mLength);
        mPath.cubicTo(mLeft+0.8f*mLength,mTop+0.66f*mLength,mLeft+0.76f*mLength,mTop+0.72f*mLength,mLeft+0.5f*mLength,mTop+mLength);
        mPath.cubicTo(mLeft+0.24f*mLength,mTop+0.72f*mLength,mLeft+0.2f*mLength,mTop+0.66f*mLength,mLeft+0.2f*mLength,mTop+0.5f*mLength);
        mPath.close();
    }

    @Override
    protected void drawShape(Canvas canvas, Paint paint) {
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.FILL);
        if(mLength!=0){
            canvas.drawPath(mPath,paint);
        }
    }

    @Override
    protected Animator onCreateAnimator() {
        return null;
    }

    @Override
    protected void reset() {
        resetAll();
    }
}
