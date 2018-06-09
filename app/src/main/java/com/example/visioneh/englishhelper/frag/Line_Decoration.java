package com.example.visioneh.englishhelper.frag;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/7/7.
 */

public class Line_Decoration extends RecyclerView.ItemDecoration {
    //系统内置风格的分割线
    public static final int[]attrs=new int[]{android.R.attr.listDivider};

    private Drawable myDivider;

    public Line_Decoration(Context context) {
        TypedArray typedArray=context.obtainStyledAttributes(attrs);
        myDivider=typedArray.getDrawable(0);
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        int left=parent.getPaddingLeft();
        int right=parent.getWidth()-parent.getPaddingRight();
        int childcount=parent.getChildCount();
        for (int i = 0; i < childcount; i++) {
            View childView=parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams= (RecyclerView.LayoutParams) childView.getLayoutParams();
            int top=childView.getBottom()+layoutParams.bottomMargin;
            int bottom=top+myDivider.getIntrinsicHeight();
            myDivider.setBounds(left,top,right,bottom);
            myDivider.draw(c);
        }
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0,0,0,myDivider.getIntrinsicHeight());
    }
}
