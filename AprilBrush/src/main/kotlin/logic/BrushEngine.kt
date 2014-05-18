package org.krre.aprilbrush.logic

import android.util.Log
import android.view.MotionEvent
import android.graphics.drawable.GradientDrawable
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color

class BrushEngine {
    private val TAG = "AB"
    var dabBitmap : Bitmap? = null
        private set
    var bufferBitmap : Bitmap? = null
        private set
    private val bufferPaint : Paint = Paint()
    private val dabPaint : Paint = Paint()
    private val bufferCanvas : Canvas = Canvas();
    private val dabCanvas : Canvas = Canvas();

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

    private fun paintOneDab(x : Float, y : Float, pressure : Float) {
        bufferCanvas.save()
        val alpha : Int = Math.round((pressure * 255f))
        bufferPaint.setAlpha(alpha)
        bufferCanvas.drawBitmap(dabBitmap!!, x, y, bufferPaint)
        bufferCanvas.restore()

        Log.d(TAG, "x = ${x.toString()} y = ${y.toString()} pressure = ${pressure.toString()} alpha = ${alpha}")
    }
}
