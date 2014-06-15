package org.krre.aprilbrush.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import org.krre.aprilbrush.R;
import org.krre.aprilbrush.data.GlobalVar;
import org.krre.aprilbrush.logic.BrushEngine;
import org.krre.aprilbrush.view.PaintView;
import org.krre.aprilbrush.view.SliderView;
import org.krre.aprilbrush.view.TransformView;

public class MainActivity extends Activity {
    private String TAG = "AB";
    private BrushEngine brushEngine;
    private PaintView paintView;
    private TransformView transformView;

    public BrushEngine getBrushEngine() {
        return brushEngine;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        paintView = (PaintView)findViewById(R.id.paintView);
        transformView = (TransformView)findViewById(R.id.transformView);
        transformView.setView(paintView);
        brushEngine = paintView.getBrushEngine();

        ToggleButton penToggleButton = (ToggleButton)findViewById(R.id.penToggleButton);
        penToggleButton.setChecked(GlobalVar.getInstance().isPenMode());

        // memory class
        ActivityManager activityManager = (ActivityManager)getBaseContext().getSystemService(Context.ACTIVITY_SERVICE);
        GlobalVar.getInstance().setMemoryClass(activityManager.getLargeMemoryClass());

        // brush settings
        ViewGroup scrollView = (ViewGroup)findViewById(R.id.brushSettingsScrollView);
        LinearLayout layout = (LinearLayout)scrollView.getChildAt(0);

        Resources res = getResources();
        String[] brushNames = res.getStringArray(R.array.brush_names);
        int[] brushMins = res.getIntArray(R.array.brush_mins);
        int[] brushMaxes = res.getIntArray(R.array.brush_maxes);
        int[] brushValues = res.getIntArray(R.array.brush_values);

        for (int i = 0; i < brushNames.length; i++) {
            SliderView sliderView = new SliderView(getBaseContext());
            layout.addView(sliderView);
            sliderView.addObserver(brushEngine);
            sliderView.setId(i);
            sliderView.setName(brushNames[i]);
            sliderView.setMin(brushMins[i]);
            sliderView.setMax(brushMaxes[i]);
            sliderView.setValue(brushValues[i]);
        }
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

    public void onPenToggleClick(View v) {
        ToggleButton tb = (ToggleButton)v;
        GlobalVar.getInstance().setPenMode(tb.isChecked());
    }

    public void onPaintButtonClick(View v) {
        transformView.setCurrentTransform(TransformView.NONE);
    }

    public void onPanButtonClick(View v) {
        transformView.setCurrentTransform(TransformView.PAN);
    }

    public void onZoomButtonClick(View v) {
        transformView.setCurrentTransform(TransformView.ZOOM);
    }

    public void onRotateButtonClick(View v) {
        transformView.setCurrentTransform(TransformView.ROTATE);
    }

    public void onResetButtonClick(View v) {
        transformView.reset();
    }

    public void onColorButtonClick(View v) {
        View colorPickerView = findViewById(R.id.colorpickerView);
        View brushSettingsScrollView = findViewById(R.id.brushSettingsScrollView);

        if (brushSettingsScrollView.getVisibility() == View.VISIBLE) {
            brushSettingsScrollView.setVisibility(View.INVISIBLE);
        }

        if (colorPickerView.getVisibility() == View.VISIBLE) {
            colorPickerView.setVisibility(View.INVISIBLE);
        } else {
            colorPickerView.setVisibility(View.VISIBLE);
        }
    }

    public void onBrushButtonClick(View v) {
        View brushSettingsScrollView = findViewById(R.id.brushSettingsScrollView);
        View colorPickerView = findViewById(R.id.colorpickerView);

        if (colorPickerView.getVisibility() == View.VISIBLE) {
            colorPickerView.setVisibility(View.INVISIBLE);
        }

        if (brushSettingsScrollView.getVisibility() == View.VISIBLE) {
            brushSettingsScrollView.setVisibility(View.INVISIBLE);
        } else {
            brushSettingsScrollView.setVisibility(View.VISIBLE);
        }
    }

    public void onClearButtonClick(View v) {
        paintView.clear();
    }
}
