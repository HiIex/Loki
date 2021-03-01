package md.loki;

import android.content.Context;
import android.util.AttributeSet;

import java.util.ArrayList;

public class DoubleArcView extends LokiView {

    public DoubleArcView(Context context){
        this(context,null);
    }

    public DoubleArcView(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }

    public DoubleArcView(Context context,AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
    }

    @Override
    protected LokiGroup onCreateLokiGroup() {
        LokiGroup lokiGroup=new DoubleArc();
        ArrayList<Integer> durations=new ArrayList<>();
        durations.add(10000);
        lokiGroup.setChildDuration(durations);
        return lokiGroup;
    }
}
