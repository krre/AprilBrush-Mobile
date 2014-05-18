package org.krre.aprilbrush.logic

import android.util.Log
import android.view.MotionEvent
import android.graphics.drawable.GradientDrawable
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color
import org.krre.aprilbrush.view.PaintView

class BrushEngine(paintView : PaintView) {
    private val TAG = "AB"
    private var dabBitmap : Bitmap? = null
    var bufferBitmap : Bitmap? = null
        private set
    private val paintView : PaintView = paintView
    private val bufferPaint : Paint = Paint()
    private val dabPaint : Paint = Paint()
    private val bufferCanvas : Canvas = Canvas();
    private val dabCanvas : Canvas = Canvas();
    private var toolType : Int = 0

    var diameter : Int = 20
    {
        dabPaint.setAntiAlias(true)
        dabBitmap = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888)
        dabBitmap?.eraseColor(Color.TRANSPARENT)
        dabCanvas.setBitmap(dabBitmap!!)
        dabCanvas.drawCircle(diameter / 2f, diameter / 2f, diameter / 2f, dabPaint)
    }

    fun setBufferSize(width : Int, height : Int) {
        bufferBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bufferCanvas.setBitmap(bufferBitmap!!);
    }

    fun paintDab(event : MotionEvent) {
        when (event.getAction()) {
            MotionEvent.ACTION_DOWN -> {
                toolType = event.getToolType(0)
                Log.d(TAG, "down ")
            }
            MotionEvent.ACTION_MOVE -> {
            }
            MotionEvent.ACTION_UP -> {
                Log.d(TAG, "up")
                return
            }
        }

        var x : Float
        var y : Float
        var pressure : Float
        val historySize = event.getHistorySize()
        if (historySize > 0) {
            for (i in event.getHistorySize().indices) {
                x = event.getHistoricalX(i)
                y = event.getHistoricalY(i)
                pressure = event.getHistoricalPressure(i)
                paintOneDab(x, y, pressure)
            }
        } else {
            x = event.getX()
            y = event.getY()
            pressure = event.getPressure()
            paintOneDab(x, y, pressure)
        }
    }

    private fun paintOneDab(x : Float, y : Float, p : Float) {
        val pressure = if (toolType == MotionEvent.TOOL_TYPE_STYLUS) p else 1.0f
        bufferCanvas.save()
        val alpha : Int = Math.round((pressure * 255f))
        bufferPaint.setAlpha(alpha)
        val paintX = x - diameter / 2f
        val paintY = y - diameter / 2f
        bufferCanvas.drawBitmap(dabBitmap!!, paintX, paintY, bufferPaint)
        bufferCanvas.restore()

        paintView.invalidate(paintX.toInt(), paintY.toInt(), paintX.toInt() + diameter, paintY.toInt() + diameter)
//        Log.d(TAG, "x = ${x.toString()} y = ${y.toString()} pressure = ${pressure.toString()} alpha = ${alpha}")
    }
}
