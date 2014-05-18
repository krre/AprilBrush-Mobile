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
    var bufferBitmap : Bitmap? = null
        private set
    private val paint : Paint = Paint()
    private val canvas : Canvas = Canvas();
    {
        paint.setAntiAlias(true)
    }

    fun setBufferSize(width : Int, height : Int) {
        bufferBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        canvas.setBitmap(bufferBitmap!!);
    }

    fun paintDab(event : MotionEvent) {
        var x : Float
        var y : Float
        var pressure : Float

        for (i in event.getHistorySize().indices) {
            x = event.getHistoricalX(i)
            y = event.getHistoricalY(i)
            pressure = event.getHistoricalPressure(i)
            paintOneDab(x, y, pressure)
        }
        x = event.getX()
        y = event.getY()
        pressure = event.getPressure()
        paintOneDab(x, y, pressure)
    }

    private fun paintOneDab(x : Float, y : Float, pressure : Float) {
        canvas.save()
        val alpha : Int = Math.round((pressure * 255f))
        paint.setAlpha(alpha)
        canvas.drawBitmap(dabBitmap!!, x, y, paint)
        canvas.restore()

        Log.d(TAG, "x = ${x.toString()} y = ${y.toString()} pressure = ${pressure.toString()} alpha = ${alpha}")
    }
}
