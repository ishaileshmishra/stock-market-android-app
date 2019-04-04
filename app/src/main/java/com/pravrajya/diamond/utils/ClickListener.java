package com.pravrajya.diamond.utils;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ClickListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector gestureDetector;
    private TapListener tapListener;

    public ClickListener(Context context, final RecyclerView recyclerView, final TapListener tapListener) {
        this.tapListener = tapListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
            @Override
            public void onLongPress(MotionEvent e) { View child = recyclerView.findChildViewUnder(e.getX(), e.getY()); }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && tapListener != null && gestureDetector.onTouchEvent(e)) { tapListener.onClick(child, rv.getChildPosition(child)); }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView view, @NonNull MotionEvent event) { }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) { }

    public interface TapListener { void onClick(View view, int position); }
}