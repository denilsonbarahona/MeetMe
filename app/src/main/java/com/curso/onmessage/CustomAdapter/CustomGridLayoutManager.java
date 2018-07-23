package com.curso.onmessage.CustomAdapter;

import android.content.Context;
import android.icu.math.MathContext;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;

/**
 * Created by PC-PRAF on 10/12/2016.
 */

public class CustomGridLayoutManager extends GridLayoutManager {

    private int mColumnw;
    private boolean Change = true;

    public CustomGridLayoutManager(Context context, int spanCount) {
        super(context, 1);
        setColumnView(ColumnWidth(context , spanCount));
    }

    public CustomGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, 1, orientation, reverseLayout);
        setColumnView(ColumnWidth(context , spanCount));
    }

    private void setColumnView(int ColumnWidth){
            if(ColumnWidth>0 && ColumnWidth!=mColumnw){
                mColumnw=ColumnWidth;
                Change = true;
            }
    }

    private int ColumnWidth(Context context , int ColumnWidth){
        if(ColumnWidth <= 0){
            ColumnWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP , 48 ,
                                context.getResources().getDisplayMetrics() );
        }
        return  ColumnWidth;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {

        if(Change && mColumnw > 0 ){
            int total_space;
            if(getOrientation()==VERTICAL){
                total_space = getWidth() - getPaddingRight()-getPaddingLeft();
            }else{
                total_space = getHeight() - getPaddingTop() - getPaddingBottom();
            }

            int SpanCount = Math.max(1 , total_space / mColumnw);
            setSpanCount(SpanCount);
            Change = false;
        }

        super.onLayoutChildren(recycler, state);
    }
}
