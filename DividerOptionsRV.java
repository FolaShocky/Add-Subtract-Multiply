package com.folashocky.add_subtract_multiply;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DividerOptionsRV extends RecyclerView.ItemDecoration {
    private static final int[] Attributes = new int[]{android.R.attr.listDivider};
    private Drawable divider;

    public DividerOptionsRV(Context context) {
        TypedArray styledAttrs = context.obtainStyledAttributes(Attributes);
        divider = styledAttrs.getDrawable(0);
        styledAttrs.recycle();
    }
    public DividerOptionsRV(Context context,int resId) {
        divider = ContextCompat.getDrawable(context,resId);
    }
    public void onDraw(Canvas c ,RecyclerView recyclerView,RecyclerView.State state) {
        int left = recyclerView.getPaddingLeft();
        int right = recyclerView.getWidth()-recyclerView.getPaddingRight();
        int itemCount = recyclerView.getChildCount();
        for(int i = 0;i<itemCount;i++) {
            View child = recyclerView.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();
            int top= child.getBottom()+params.bottomMargin;
            int bottom = top+ divider.getIntrinsicHeight();
            divider.setBounds(left,top,right,bottom);
            divider.draw(c);
        }
    }
}
