package org.krre.aprilbrush.view;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import org.krre.aprilbrush.R;

public class TransformView extends View {
    private final String TAG = "AB";

    public static final int NONE = 0;
    public static final int PAN = 1;
    public static final int ZOOM = 2;
    public static final int ROTATE = 3;

    private PointF pan = new PointF(0, 0);;
    private float zoom = 1.0f;
    private float rotate = 0;

    private int currentTransform;

    private View view;
    private PointF touchPoint;
    private PointF prevPan = new PointF(0, 0);
    private float prevZoom = 1.0f;

    public TransformView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setCurrentTransform(int currentTransform) {
        this.currentTransform = currentTransform;
        if (currentTransform > 0) {
            setVisibility(View.VISIBLE);
        } else {
            setVisibility(INVISIBLE);
        }
    }

    public void reset() {
        pan = new PointF(0, 0);
        prevPan = new PointF(0, 0);
        view.setTranslationX(pan.x);
        view.setTranslationY(pan.y);
        zoom = 1.0f;
        view.setScaleX(zoom);
        view.setScaleY(zoom);
        rotate = 0;

        currentTransform = NONE;
        setVisibility(INVISIBLE);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchPoint = new PointF(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = event.getX() - touchPoint.x;
                float dy = event.getY() - touchPoint.y;
                if (currentTransform == PAN) {
                    pan.x = prevPan.x + dx;
                    view.setTranslationX(pan.x);
                    pan.y = prevPan.y + dy;
                    view.setTranslationY(pan.y);
                } else if (currentTransform == ZOOM) {
                    if (dx > 0 & dy > 0) {
                        zoom = prevZoom * (1 + (float)Math.sqrt(dx * dx + dy * dy) / 10);
                    } else if (dx < 0 & dy < 0) {
                        zoom = prevZoom / (1 + (float)Math.sqrt(dx * dx + dy * dy) / 10);
                    }
                    view.setScaleX(zoom);
                    view.setScaleY(zoom);
                }
//                Log.d(TAG, "dx = " + dx + " dy = " + dy + " zoom = " + zoom);
                break;
            case MotionEvent.ACTION_UP:
                prevPan.set(pan.x, pan.y);
                prevZoom = zoom;
                break;
        }
        return true;
    }
}
