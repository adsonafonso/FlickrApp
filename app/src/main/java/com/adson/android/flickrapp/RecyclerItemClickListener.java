package com.adson.android.flickrapp;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

    private GestureDetectorCompat gestureDetector;

    public interface OnRecyclerClickListener {
        void onItemClick(View view, int position);
    }

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, final OnRecyclerClickListener listener) {
        gestureDetector = new GestureDetectorCompat(context, (new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View childView = recyclerView.findChildViewUnder(e.getX(),e.getY());
                listener.onItemClick(childView, recyclerView.getChildAdapterPosition(childView));
                return true;
            }
        }));
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        boolean result = gestureDetector.onTouchEvent(e);
        return result;
    }
}
