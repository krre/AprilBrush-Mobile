package org.krre.aprilbrush.logic

import android.util.Log
import android.view.MotionEvent

class BrushEngine {
    private val TAG = "AB"

    fun paintDab(event : MotionEvent) {
        for (i in event.getHistorySize().indices) {
            Log.d(TAG, "history: x = ${event.getHistoricalX(i).toString()} y = ${event.getHistoricalY(i).toString()} pressure = ${event.getHistoricalPressure(i).toString()}")
        }
        Log.d(TAG, "x = ${event.getX().toString()} y = ${event.getY().toString()} pressure = ${event.getPressure().toString()}")
    }
}

