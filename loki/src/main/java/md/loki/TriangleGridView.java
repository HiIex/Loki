package md.loki;

import android.content.Context;
import android.util.AttributeSet;

import java.util.ArrayList;

public class TriangleGridView extends LokiView {

    public TriangleGridView(Context context){
        this(context,null);
    }

    public TriangleGridView(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }

    public TriangleGridView(Context context,AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
    }

    @Override
    protected LokiGroup onCreateLokiGroup() {
        LokiGroup lokiGroup=new TriangleGrid();
        ArrayList<Integer> durations=new ArrayList<>();
        durations.add(1500);
        durations.add(4500);
        lokiGroup.setChildDuration(durations);
        return lokiGroup;
    }

}
