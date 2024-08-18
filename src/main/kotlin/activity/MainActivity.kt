package org.aprilbrush.activity

import android.app.Activity
import android.os.Bundle
import org.aprilbrush.R
import android.app.ActivityManager
import android.content.Context
import org.aprilbrush.view.PaintView
import android.graphics.Bitmap

public class MainActivity() : Activity() {
    var memoryClass : Int? = 0
        private set
    private val TAG = "AB"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val activityManager = getBaseContext()?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
        memoryClass = activityManager?.getLargeMemoryClass()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val paintView = findViewById(R.id.paintView) as PaintView
        val bufferBitmap = paintView.brushEngine.bufferBitmap
        outState.putParcelable("bufferBitmap", bufferBitmap)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        val bufferBitmap: Bitmap? = savedInstanceState.getParcelable("bufferBitmap")
        val paintView: PaintView? = findViewById(R.id.paintView) as PaintView
        val orientation = getResources()?.getConfiguration()?.orientation
        paintView?.brushEngine?.setBitmap(bufferBitmap!!, orientation)
    }
}
