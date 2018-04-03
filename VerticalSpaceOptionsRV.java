package com.folashocky.add_subtract_multiply;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by folas on 26/03/2018.
 */

public class VerticalSpaceOptionsRV extends RecyclerView.ItemDecoration
{
    private final int verticalSpacing;

    public VerticalSpaceOptionsRV(int verticalSpacing)
    {
        this.verticalSpacing = verticalSpacing;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view ,RecyclerView parent,RecyclerView.State state)
    {
        outRect.bottom = verticalSpacing;
    }
}
