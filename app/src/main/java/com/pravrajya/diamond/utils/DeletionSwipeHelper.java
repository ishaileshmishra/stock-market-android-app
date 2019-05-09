/**
 *
 * Copyright © 2012-2020 [Shailesh Mishra](https://github.com/mshaileshr/). All Rights Reserved
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.pravrajya.diamond.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import com.pravrajya.diamond.R;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class DeletionSwipeHelper extends ItemTouchHelper.SimpleCallback {

    private OnSwipeListener listener;
    private Context context;
    private int backgroundColor;
    private int deleteColor;
    private int iconPadding;
    private int iconColorFilter;
    private Drawable deleteIcon = null;
    private Paint circlePaint = null;

    public static float CIRCLE_ACCELERATION = 3;

    public DeletionSwipeHelper(int dragDirs, int swipeDirs, Context context, OnSwipeListener listener)
    {
        super(dragDirs, swipeDirs);
        this.context = context;
        this.listener = listener;
        Resources res = context.getResources();
        backgroundColor = ContextCompat.getColor(context, R.color.background_deletion);
        deleteColor = ContextCompat.getColor(context, R.color.background_deletion_swipe);
        iconColorFilter = deleteColor;
        iconPadding = res.getDimensionPixelSize(R.dimen.fab_margin);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
    {
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder, viewHolder.getAdapterPosition());
    }

    @Override
    public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder)
    {
        return makeMovementFlags(0, ItemTouchHelper.START);
    }

    @Override
    public float getSwipeEscapeVelocity(float defaultValue)
    {
        float values = defaultValue * 5f;
        return values;
    }

    @Override
    public boolean isLongPressDragEnabled()
    {
        return false;
    }

    @Override
    public void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive)
    {
        if (dX == 0f)
        {
            super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            return;
        }

        float left = viewHolder.itemView.getLeft();
        float top = viewHolder.itemView.getTop();
        float right = viewHolder.itemView.getRight();
        float bottom = viewHolder.itemView.getBottom();
        float width = right - left;
        float height = bottom - top;
        float saveCount = canvas.save();

        canvas.clipRect(right + dX, top, right, bottom);
        canvas.drawColor(backgroundColor);
        deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete);
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(deleteColor);
        float progress = -dX / width;
        float swipeThreshold = getSwipeThreshold(viewHolder);
        float opacity = 1f;
        float iconScale = 1f;
        float circleRadius = 0f;

        circleRadius = (progress - swipeThreshold) * width * CIRCLE_ACCELERATION;
        if (deleteIcon != null)
        {
            float cx = right - iconPadding - deleteIcon.getIntrinsicWidth() / 2f;
            float cy = top + height / 2f;
            float halfIconSize = deleteIcon.getIntrinsicWidth() * iconScale / 2f;

            deleteIcon.setBounds((int)(cx - halfIconSize), (int)(cy - halfIconSize), (int)(cx + halfIconSize), (int)(cy + halfIconSize));
            deleteIcon.setAlpha(Math.round(opacity * 255f));

            deleteIcon.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(context, R.color.grey), PorterDuff.Mode.SRC_IN));
            if (circleRadius > 0f)
            {
                canvas.drawCircle(cx, cy, circleRadius, circlePaint);
                deleteIcon.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(context, R.color.colorWhite), PorterDuff.Mode.SRC_ATOP));
            }
            deleteIcon.draw(canvas);
        }
        canvas.restoreToCount(Math.round(saveCount));
        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    public interface OnSwipeListener {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int position);
    }
}

