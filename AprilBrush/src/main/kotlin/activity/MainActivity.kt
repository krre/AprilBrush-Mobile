package org.krre.aprilbrush.activity

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import org.krre.aprilbrush.R
import android.util.Log

public class MainActivity() : Activity() {
    private val TAG = "AB"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "test")
    }
}
