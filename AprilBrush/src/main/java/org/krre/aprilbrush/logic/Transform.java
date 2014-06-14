package org.krre.aprilbrush.logic;

import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;

import org.krre.aprilbrush.view.PaintView;

public final class Transform {
    private final String TAG = "AB";
    public static final int NONE = 0;
    public static final int PAN = 1;
    public static final int ZOOM = 2;
    public static final int ROTATE = 3;

    private PointF pan;
    private float zoom;
    private float rotate;

    private static int currentTransform;
    private PaintView paintView;
    private PointF touchPoint;
    private PointF prevPan = new PointF(0, 0);

    public Transform(PaintView paintView) {
        this.paintView = paintView;
        reset();
    }

    public static void setMode(int mode) {
        currentTransform = mode;
    }

    public void reset() {
        pan = new PointF(0, 0);
        zoom = 1.0f;
        rotate = 0;
        currentTransform = NONE;
        paintView.invalidate();
    }

    public PointF getPan() {
        return pan;
    }

    public float getZoom() {
        return zoom;
    }

    public float getRotate() {
        return rotate;
    }

    public int getCurrentTransform() {
        return currentTransform;
    }

    public void change(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchPoint = new PointF(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                if (currentTransform == PAN) {
                    pan.x = prevPan.x + event.getX() - touchPoint.x;
                    pan.y = prevPan.y + event.getY() - touchPoint.y;
                    paintView.invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                prevPan.set(pan.x, pan.y);
                break;
        }
    }
}
