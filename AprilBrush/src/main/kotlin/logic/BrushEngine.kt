package org.krre.aprilbrush.logic

import android.util.Log
import android.view.MotionEvent
import android.graphics.drawable.GradientDrawable
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Canvas

class BrushEngine {
    private val TAG = "AB"
    var dab : GradientDrawable? = null
    private val diameter : Int = 0
    var bufferBitmap : Bitmap? = null
        private set
    private val paint : Paint = Paint()
    private val canvas : Canvas = Canvas();
    {
        paint.setAntiAlias(true)
        paint.setAlpha(128)
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

        val diameter : Float = 10.0f
        canvas.drawCircle(x, y, diameter, paint)

        canvas.restore()

        Log.d(TAG, "x = ${x.toString()} y = ${y.toString()} pressure = ${pressure.toString()}")
    }
}
