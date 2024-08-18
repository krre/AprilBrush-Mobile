package org.krre.aprilbrush.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import org.krre.aprilbrush.R;
import org.krre.aprilbrush.logic.BrushEngine;

import java.util.ArrayList;

public class BrushSettingsView extends ScrollView {
    private final String TAG = "AB";
    private ArrayList<SliderView> sliderViews = new ArrayList<SliderView>();

    public BrushSettingsView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        addView(layout);

        if (!isInEditMode()) {

            Resources res = getResources();
            String[] brushNames = res.getStringArray(R.array.brush_names);
            int[] brushMins = res.getIntArray(R.array.brush_mins);
            int[] brushMaxes = res.getIntArray(R.array.brush_maxes);
            int[] brushValues = res.getIntArray(R.array.brush_values);

            for (int i = 0; i < brushNames.length; i++) {
                SliderView sliderView = new SliderView(context, attrs);
                layout.addView(sliderView);
                sliderViews.add(sliderView);
                sliderView.addObserver(BrushEngine.getInstance());
                sliderView.setId(i);
                sliderView.setName(brushNames[i]);
                sliderView.setMin(brushMins[i]);
                sliderView.setMax(brushMaxes[i]);
                sliderView.setValue(brushValues[i]);
            }
        }
    }

    public void reset() {
        int[] brushValues = getResources().getIntArray(R.array.brush_values);
        for (int i = 0; i < brushValues.length; i++) {
            sliderViews.get(i).setValue(brushValues[i]);
        }
    }

    public ArrayList<SliderView> getSliderViews() {
        return sliderViews;
    }
}
