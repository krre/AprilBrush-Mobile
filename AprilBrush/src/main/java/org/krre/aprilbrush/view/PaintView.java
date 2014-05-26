package org.krre.aprilbrush.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import org.krre.aprilbrush.logic.BrushEngine;

public class PaintView extends View {
    private String TAG = "AB";
    private Paint bufferPaint = new Paint();
    private BrushEngine brushEngine = new BrushEngine(this);
    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public BrushEngine getBrushEngine() {
        return brushEngine;
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (brushEngine.getBufferBitmap() == null) {
            brushEngine.setBufferSize(w, h);
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(brushEngine.getBufferBitmap(), 0, 0, bufferPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        brushEngine.paintDab(motionEvent);
        return true;
    }
}
