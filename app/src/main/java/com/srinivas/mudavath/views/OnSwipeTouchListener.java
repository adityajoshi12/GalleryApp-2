package com.srinivas.mudavath.views;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Mudavath Srinivas on 27-02-2016.
 */
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnTouchListener;

public class OnSwipeTouchListener extends ScaleGestureDetector.SimpleOnScaleGestureListener implements OnTouchListener {

    private final GestureDetector gestureDetector;
    private float endScale;
    private float startScale;

    public OnSwipeTouchListener (Context ctx){
        gestureDetector = new GestureDetector(ctx, new GestureListener());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        startScale = detector.getScaleFactor();
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        endScale = detector.getScaleFactor();

        if ((startScale - endScale) > 0.0100 || (endScale - startScale) > 0.0100) {
            if (startScale > endScale) {
                onPinch();
            } else if (startScale < endScale) {
                onZoom();
            }
        }
    }

    private final class GestureListener extends SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 20;
        private static final int SWIPE_VELOCITY_THRESHOLD = 50;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD ) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                    }
                    result = true;
                }
                else if (Math.abs(diffY) > SWIPE_THRESHOLD ) {
                    if (diffY > 0) {
                        onSwipeBottom();
                    } else {
                        onSwipeTop();
                    }
                }
                result = true;

            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return false;
        }
    }

    public void onSwipeRight() {
    }

    public void onSwipeLeft() {
    }

    public void onSwipeTop() {
    }

    public void onSwipeBottom() {
    }

    public void onZoom() {
    }
    public void onPinch() {
    }
}
