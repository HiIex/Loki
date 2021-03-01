package md.loki;

import android.animation.Animator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * 三角形形状的Loki，根据提供的三点坐标绘制
 * @date 2021-2-22
 * @author Hilex
 * @version 1.0
 */
public class TriangleLoki extends ShapeLoki {

    //三角形三个点的坐标
    private float x1;
    private float y1;
    private float x2;
    private float y2;
    private float x3;
    private float y3;

    public TriangleLoki(){}

    public TriangleLoki(float x1,float y1,float x2,float y2,float x3,float y3 ){
        this.x1=x1;
        this.y1=y1;
        this.x2=x2;
        this.y2=y2;
        this.x3=x3;
        this.y3=y3;
    }

    public float getX1() {
        return x1;
    }

    public void setX1(float x1) {
        this.x1 = x1;
    }

    public float getY1() {
        return y1;
    }

    public void setY1(float y1) {
        this.y1 = y1;
    }

    public float getX2() {
        return x2;
    }

    public void setX2(float x2) {
        this.x2 = x2;
    }

    public float getY2() {
        return y2;
    }

    public void setY2(float y2) {
        this.y2 = y2;
    }

    public float getX3() {
        return x3;
    }

    public void setX3(float x3) {
        this.x3 = x3;
    }

    public float getY3() {
        return y3;
    }

    public void setY3(float y3) {
        this.y3 = y3;
    }

    public void setAll(float x1, float y1, float x2, float y2,float x3,float y3){
        this.x1=x1;
        this.y1=y1;
        this.x2=x2;
        this.y2=y2;
        this.x3=x3;
        this.y3=y3;
        init();
    }

    /**
     * 计算三角形中心坐标，设置中心坐标为锚点,设置bounds
     */
    private void init(){
        float sideLength=Math.max(Math.max(Math.abs(x1-x2),Math.abs(x1-x3)),Math.abs(x2-x3));
        float height=Math.max(Math.max(Math.abs(y1-y2),Math.abs(y1-y3)),Math.abs(y2-y3));
        float minX=Math.min(Math.min(x1,x2),x3);
        float minY=Math.min(Math.min(y1,y2),y3);
        setLokiBoundsAndPivot((int)minX,(int)minY,(int)(minX+sideLength),(int)(minY+height));
    }

    @Override
    public void drawShape(Canvas canvas, Paint paint) {
        if(getBounds()!=null){
            Path path=new Path();
            path.moveTo(x1,y1);
            path.lineTo(x2,y2);
            path.lineTo(x3,y3);
            path.close();
            canvas.drawPath(path,paint);
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
