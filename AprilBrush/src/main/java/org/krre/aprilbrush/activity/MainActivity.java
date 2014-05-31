package org.krre.aprilbrush.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.krre.aprilbrush.R;
import org.krre.aprilbrush.logic.BrushEngine;
import org.krre.aprilbrush.view.ColorPickerView;
import org.krre.aprilbrush.view.PaintView;

public class MainActivity extends Activity {
    private int memoryClass;
    private String TAG = "AB";
    private BrushEngine brushEngine;
    private ColorpickerFragment colorpickerFragment;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        PaintView paintView = (PaintView)findViewById(R.id.paintView);
        brushEngine = paintView.getBrushEngine();

        colorpickerFragment = new ColorpickerFragment();
        ColorPickerView colorPickerView = (ColorPickerView)colorpickerFragment.getView();
//        colorPickerView.setBrushEngine(brushEngine);

        ActivityManager activityManager = (ActivityManager)getBaseContext().getSystemService(Context.ACTIVITY_SERVICE);
        memoryClass = activityManager.getLargeMemoryClass();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Bitmap bufferBitmap = brushEngine.getBufferBitmap();
        outState.putParcelable("bufferBitmap", bufferBitmap);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Bitmap bufferBitmap = savedInstanceState.getParcelable("bufferBitmap");
        if (bufferBitmap != null) {
            int orientation = getResources().getConfiguration().orientation;
            brushEngine.setBitmap(bufferBitmap, orientation);
        }
    }

    public void onColorButtonClick(View v) {
        fragmentTransaction = getFragmentManager().beginTransaction();
        Fragment currentFragment = getFragmentManager().findFragmentByTag("colorpicker");
        if (currentFragment != null) {
            fragmentTransaction.remove(colorpickerFragment);
        } else {
            fragmentTransaction.replace(R.id.rightFrame, colorpickerFragment, "colorpicker");
        }

        fragmentTransaction.commit();
    }

    public void onBrushButtonClick(View v) {
        Log.d(TAG, "brush");
    }

    public void onClearButtonClick(View v) {
        brushEngine.clear();
    }
}
