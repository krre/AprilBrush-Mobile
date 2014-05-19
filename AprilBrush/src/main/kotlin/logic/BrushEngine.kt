package org.krre.aprilbrush.logic

import android.view.MotionEvent
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color
import org.krre.aprilbrush.view.PaintView
import android.graphics.Path
import android.graphics.PathMeasure
import android.content.res.Configuration
import android.util.Log
import android.graphics.Matrix

class BrushEngine(paintView : PaintView) {
    private val TAG = "AB"
    private var dabBitmap : Bitmap? = null
    var bufferBitmap : Bitmap? = null
        private set
    private val paintView = paintView
    private val bufferPaint = Paint()
    private val dabPaint = Paint()
    private val bufferCanvas = Canvas()
    private val dabCanvas = Canvas()
    private val path = Path()
    private val pathMeasure = PathMeasure()
    private var pathLength = 0f
    private var toolType = 0
    private var prevX = 0f
    private var prevY = 0f
    private var prevPressure = 0f

    private var diameter = 50
    private var spacing = 100
    {
        dabPaint.setAntiAlias(true)
        dabBitmap = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888)
        dabBitmap?.eraseColor(Color.TRANSPARENT)
        dabCanvas.setBitmap(dabBitmap!!)
        dabCanvas.drawCircle(diameter / 2f, diameter / 2f, diameter / 2f, dabPaint)
    }

    fun setBitmap(value : Bitmap, orientation : Int?) {
        val width  = value.getWidth()
        val height = value.getHeight()
        if ((orientation == Configuration.ORIENTATION_LANDSCAPE && width < height) ||
            (orientation == Configuration.ORIENTATION_PORTRAIT && width > height)) {
            val matrix = Matrix()
            matrix.postRotate(90f);
            bufferBitmap = Bitmap.createBitmap(value, 0, 0, width, height, matrix, true);
        } else {
            bufferBitmap = Bitmap.createBitmap(value, 0, 0, width, height)
        }
        bufferCanvas.setBitmap(bufferBitmap!!);
        paintView.invalidate()
    }

    fun setBufferSize(width : Int, height : Int) {
        bufferBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bufferCanvas.setBitmap(bufferBitmap!!);
    }

    fun paintDab(event : MotionEvent) {
        val x = event.getX()
        val y = event.getY()
        val pressure  = event.getPressure()
        when (event.getAction()) {
            MotionEvent.ACTION_DOWN -> {
                toolType = event.getToolType(0)
                paintOneDab(x, y, pressure)

                path.reset()
                path.moveTo(x, y)
                pathLength = 0f

                prevX = x
                prevY = y
                prevPressure = pressure
            }
            MotionEvent.ACTION_MOVE -> {
                if (event.getHistorySize() > 0) {
                    for (i in event.getHistorySize().indices) {
                        interpolateDab(event.getHistoricalX(i), event.getHistoricalY(i), event.getHistoricalPressure(i))
                    }
                } else {
                    interpolateDab(x, y, pressure)
                }
            }
            MotionEvent.ACTION_UP -> {
            }
        }
    }

    private fun interpolateDab(x : Float, y : Float, p : Float) {
        val pointSpace = Math.sqrt(Math.pow(prevX.toDouble() - x, 2.toDouble())
        + Math.pow(prevY.toDouble() - y, 2.toDouble()))

        val deltaDab = diameter * spacing / 100f
        if (pointSpace >= deltaDab) {
            path.quadTo(prevX, prevY, (x + prevX) / 2, (y + prevY) / 2)
        } else {
            path.lineTo(x, y)
        }
        pathMeasure.setPath(path, false)
        val pathMeasurePos = FloatArray(2)
        val pathMeasureTan = FloatArray(2)
        while (pathMeasure.getLength() >= pathLength) {
            pathMeasure.getPosTan(pathLength, pathMeasurePos, pathMeasureTan)
            if (pathLength > 0) {
                paintOneDab(pathMeasurePos[0], pathMeasurePos[1], p) // TODO: interpolate pressure
            }
            pathLength += deltaDab
        }
        prevX = x
        prevY = y
        prevPressure = p
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
        paintView.invalidate(paintX.toInt() - 1, paintY.toInt() - 1, paintX.toInt() + diameter + 1, paintY.toInt() + diameter + 1)
    }
}
