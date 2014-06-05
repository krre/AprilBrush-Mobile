package org.krre.aprilbrush.activity;

import android.app.Fragment;
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

//        for (int i = 0; i < brushEngine.getBrushList().length; i++) {
        for (int i = 0; i < 15; i++) {
            SliderView sliderView = new SliderView(getActivity().getBaseContext());
//            BrushData.Brush brush = brushData.getList().get(i);

//            sliderView.setName(brush.getName());
            sliderView.setName("name");
//            sliderView.setMin(brush.getMinValue());
            sliderView.setMin(0);
//            sliderView.setMax(brush.getMaxValue());
            sliderView.setMax(100);
//            sliderView.setValue(brushEngine.getValue(i));
            sliderView.setValue(20);
            sliderView.setId(i);
//            sliderView.addObserver(brushEngine);

            layout.addView(sliderView);
        }

        return scrollView;
    }
}
