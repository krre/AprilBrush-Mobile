package org.krre.aprilbrush.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import org.krre.aprilbrush.R;
import org.krre.aprilbrush.view.PaintView;

public class MainActivity extends Activity {
    private int memoryClass;
    private String TAG = "AB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ActivityManager activityManager = (ActivityManager)getBaseContext().getSystemService(Context.ACTIVITY_SERVICE);
        memoryClass = activityManager.getLargeMemoryClass();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        PaintView paintView = (PaintView)findViewById(R.id.paintView);
        Bitmap bufferBitmap = paintView.getBrushEngine().getBufferBitmap();
        outState.putParcelable("bufferBitmap", bufferBitmap);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Bitmap bufferBitmap = savedInstanceState.getParcelable("bufferBitmap");
        if (bufferBitmap != null) {
            PaintView paintView = (PaintView) findViewById(R.id.paintView);
            int orientation = getResources().getConfiguration().orientation;
            paintView.getBrushEngine().setBitmap(bufferBitmap, orientation);
        }
    }
}
