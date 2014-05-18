package org.krre.aprilbrush.bitmap

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Color

class DabBitmap() {
    var bitmap : Bitmap? = null
        private set
    val canvas : Canvas = Canvas()
    val paint : Paint = Paint();
    {
        paint.setAntiAlias(true)
    }

    fun setSize(diameter : Int) {
        bitmap = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888)
        bitmap?.eraseColor(Color.TRANSPARENT)
        canvas.setBitmap(bitmap!!)
        canvas.drawCircle(diameter / 2f, diameter / 2f, diameter / 2f, paint)
    }
}
