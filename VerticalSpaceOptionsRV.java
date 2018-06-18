package com.folashocky.add_subtract_multiply;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class VerticalSpaceOptionsRV extends RecyclerView.ItemDecoration {
    private final int verticalSpacing;

    public VerticalSpaceOptionsRV(int verticalSpacing) {
        this.verticalSpacing = verticalSpacing;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view ,RecyclerView parent,RecyclerView.State state) {
        outRect.bottom = verticalSpacing;
    }
}
