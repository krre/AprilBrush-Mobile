package org.krre.aprilbrush.activity;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.krre.aprilbrush.R;
import org.krre.aprilbrush.view.SliderView;

public class BrushSettingsFragment extends Fragment {
    private final String TAG = "AB";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup scrollView = (ViewGroup)inflater.inflate(R.layout.brush_settings, null);
        LinearLayout layout = (LinearLayout)scrollView.getChildAt(0);

        MainActivity mainActivity = (MainActivity)getActivity();
        Resources res = getResources();
        String[] brushNames = res.getStringArray(R.array.brush_names);
        int[] brushMins = res.getIntArray(R.array.brush_mins);
        int[] brushMaxes = res.getIntArray(R.array.brush_maxes);
        int[] brushValues = res.getIntArray(R.array.brush_values);

        for (int i = 0; i < brushNames.length; i++) {
            SliderView sliderView = new SliderView(getActivity().getBaseContext());
            sliderView.setName(brushNames[i]);
            sliderView.setMin(brushMins[i]);
            sliderView.setMax(brushMaxes[i]);
            sliderView.setValue(brushValues[i]);
            sliderView.setId(i);
            sliderView.addObserver(mainActivity.getBrushEngine());

            layout.addView(sliderView);
        }

        return scrollView;
    }
}
