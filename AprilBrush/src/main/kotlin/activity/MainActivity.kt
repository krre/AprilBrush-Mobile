package org.krre.aprilbrush.activity

import android.app.Activity
import android.os.Bundle
import org.krre.aprilbrush.R
import android.util.Log
import android.app.ActivityManager
import android.content.Context

public class MainActivity() : Activity() {
    private val TAG = "AB"
    public var memoryClass : Int? = 0
        get
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val activityManager = getBaseContext()?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
        memoryClass = activityManager?.getLargeMemoryClass()
    }
}
