package com.erdemorman.mdx.ui.icons;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.logging.Logger;

import timber.log.Timber;

import static java.util.logging.Logger.*;

public class IconGroupDividerItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDivider;

    public IconGroupDividerItemDecoration(Context context, int resId) {
        mDivider = ContextCompat.getDrawable(context, resId);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            if (shouldDrawDivider(parent, child)) {
                RecyclerView.LayoutParams params =
                        (RecyclerView.LayoutParams) child.getLayoutParams();

                int bottom = child.getTop() - params.topMargin;
                int top = bottom - mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        if (shouldDrawDivider(parent, view)) {
            outRect.top = mDivider.getIntrinsicHeight();
        }
    }

    private boolean shouldDrawDivider(RecyclerView parent, View child) {
        boolean shouldDraw = false;
        int adapterPosition = parent.getChildAdapterPosition(child);

        if (adapterPosition != 0) {
            IconsAdapter iconsAdapter = (IconsAdapter) parent.getAdapter();
            shouldDraw = iconsAdapter.isGroupHeader(adapterPosition);
        }

        return shouldDraw;
    }
}
