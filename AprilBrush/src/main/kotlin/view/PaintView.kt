package org.krre.aprilbrush.view

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.util.Log
import org.krre.aprilbrush.logic.BrushEngine
import org.krre.aprilbrush.drawable.Dab
import android.graphics.Bitmap

class PaintView(context : Context, attrs : AttributeSet) : View(context, attrs) {
    private val TAG = "AB"
    private val bufferPaint : Paint = Paint()
    private val brushEngine = BrushEngine()
    private val dabDrawable = Dab();
    {
        brushEngine.dab = dabDrawable
    }

    override fun onSizeChanged(w : Int, h : Int, oldw : Int, oldh : Int) {
        super.onSizeChanged(w, h, oldw, oldh);
        brushEngine.setBufferSize(w, h)
    }

    override fun onDraw(canvas : Canvas ) {
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(brushEngine.bufferBitmap!!, 0f, 0f, bufferPaint)
    }

    override fun onTouchEvent(event : MotionEvent ) : Boolean {
        when (event.getAction()) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(TAG, "down")
                brushEngine.paintDab(event)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                brushEngine.paintDab(event)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                Log.d(TAG, "up")
                invalidate()
            }
        }
        return true;
    }
}
