package org.krre.aprilbrush.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.krre.aprilbrush.R;
import org.krre.aprilbrush.view.PaintView;

public class MainActivity extends Activity implements View.OnClickListener {
    private int memoryClass;
    private String TAG = "AB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        setupButtons();

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.colorButton:
                Log.d(TAG, "color");
                break;
            case R.id.brushButton:
                Log.d(TAG, "brush");
                break;
            case R.id.clearButton:
                Log.d(TAG, "clear");
                break;
        }
    }

    private void setupButtons() {
        Button colorButton = (Button)findViewById(R.id.colorButton);
        colorButton.setOnClickListener(this);

        Button brushButton = (Button)findViewById(R.id.brushButton);
        brushButton.setOnClickListener(this);

        Button clearButton = (Button)findViewById(R.id.clearButton);
        clearButton.setOnClickListener(this);
    }
}
