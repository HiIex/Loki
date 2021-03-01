package md.loki;

import android.animation.Animator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class ArcLoki extends ShapeLoki {

    private float startAngle;
    private float sweepAngle;

    private final static String TAG="ArcLoki";

    public ArcLoki(float startAngle,float sweepAngle){
        this.startAngle=startAngle;
        this.sweepAngle=sweepAngle;
    }

    public ArcLoki(float startAngle,float sweepAngle,Paint paint){
        this.startAngle=startAngle;
        this.sweepAngle=sweepAngle;
        this.mPaint=paint;
    }

    public void setStartAngle(float startAngle){
        this.startAngle=startAngle;
    }

    public void setSweepAngle(float sweepAngle){
        this.sweepAngle=sweepAngle;
    }

    public float getStartAngle(){
        return this.startAngle;
    }

    public float getSweepAngle(){
        return this.sweepAngle;
    }

    @Override
    public Animator onCreateAnimator() {
        return null;
    }

    @Override
    public void drawShape(Canvas canvas, Paint paint) {
        if(getBounds()!=null){
            Rect rect=getLokiBounds();
            RectF rectF=new RectF(rect.left,rect.top, rect.right,rect.bottom);
            canvas.drawArc(rectF,startAngle,sweepAngle,false,paint);
        }
    }

    @Override
    protected void reset() {
        resetAll();
    }
}
