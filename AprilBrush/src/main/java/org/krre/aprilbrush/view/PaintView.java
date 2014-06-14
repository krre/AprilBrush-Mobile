package org.krre.aprilbrush.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import org.krre.aprilbrush.logic.BrushEngine;
import org.krre.aprilbrush.logic.Transform;

public final class PaintView extends View {
    private String TAG = "AB";
    private Bitmap mainBitmap;
    private Paint mainPaint = new Paint();
    private Paint bufferPaint = new Paint();
    private BrushEngine brushEngine = new BrushEngine(this);
    private Transform transform = new Transform(this);
    public BrushEngine getBrushEngine() {
        return brushEngine;
    }

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mainBitmap == null) {
            mainBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mainBitmap.eraseColor(Color.TRANSPARENT);
            brushEngine.setBufferSize(w, h);
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.translate(transform.getPan().x, transform.getPan().y);
        canvas.rotate(transform.getRotate());
        canvas.scale(transform.getZoom(), transform.getZoom());
        canvas.drawBitmap(mainBitmap, 0, 0, mainPaint);
        canvas.drawBitmap(brushEngine.getBufferBitmap(), 0, 0, bufferPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (transform.getCurrentTransform() == Transform.NONE) {
            brushEngine.paintDab(event);
        } else {
            transform.change(event);
        }

        return true;
    }

    public void clear() {
        mainBitmap.eraseColor(Color.TRANSPARENT);
        invalidate();
    }

    public void resetTransform() {
        transform.reset();
    }

    public Transform getTransform() {
        return transform;
    }

    public void setOpacity(int opacity) {
        bufferPaint.setAlpha(opacity * 255 / 100);
    }

    public void applyBuffer() {
        Canvas canvas = new Canvas(mainBitmap);
        canvas.drawBitmap(brushEngine.getBufferBitmap(), 0, 0, bufferPaint);
        invalidate();
    }
}
