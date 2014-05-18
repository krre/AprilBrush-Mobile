package org.krre.aprilbrush.view

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import org.krre.aprilbrush.logic.BrushEngine

class PaintView(context : Context, attrs : AttributeSet) : View(context, attrs) {
    private val TAG = "AB"
    private val bufferPaint : Paint = Paint()
    private val brushEngine = BrushEngine(this);

    override fun onSizeChanged(w : Int, h : Int, oldw : Int, oldh : Int) {
        super.onSizeChanged(w, h, oldw, oldh);
        brushEngine.setBufferSize(w, h)
    }

    override fun onDraw(canvas : Canvas ) {
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(brushEngine.bufferBitmap!!, 0f, 0f, bufferPaint)
    }

    override fun onTouchEvent(event : MotionEvent ) : Boolean {
        brushEngine.paintDab(event)
        return true;
    }
}
