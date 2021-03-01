package md.loki;

import android.content.Context;
import android.util.AttributeSet;

import java.util.ArrayList;

public class ColorCircleView extends LokiView {

    public ColorCircleView(Context context){
        this(context,null);
    }

    public ColorCircleView(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }

    public ColorCircleView(Context context,AttributeSet attrs,int defSytle){
        super(context,attrs,defSytle);
    }

    @Override
    protected LokiGroup onCreateLokiGroup() {
        LokiGroup lokiGroup=new ColorCircle();
        ArrayList<Integer> durations=new ArrayList<>();
        durations.add(2300);
        lokiGroup.setChildDuration(durations);
        return lokiGroup;
    }
}
