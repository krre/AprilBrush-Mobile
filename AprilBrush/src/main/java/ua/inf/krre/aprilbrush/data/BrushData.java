package ua.inf.krre.aprilbrush.data;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

import ua.inf.krre.aprilbrush.AppAprilBrush;
import ua.inf.krre.aprilbrush.R;

public class BrushData {
    private static final BrushData brushData = new BrushData();
    private List<Brush> list;

    private BrushData() {
        Context context = AppAprilBrush.getContext();
        Resources res = context.getResources();
        list = new ArrayList<Brush>();

        list.add(new Brush(res.getString(R.string.brush_size), 1, 50, 7));
        list.add(new Brush(res.getString(R.string.brush_spacing), 1, 100, 25));
        list.add(new Brush(res.getString(R.string.brush_roundness), 1, 100, 100));
        list.add(new Brush(res.getString(R.string.brush_angle), 0, 360, 0));

        list.add(new Brush(res.getString(R.string.color_hue), 0, 360, 0));
        list.add(new Brush(res.getString(R.string.color_saturation), 0, 100, 100));
        list.add(new Brush(res.getString(R.string.color_value), 0, 100, 100));
        list.add(new Brush(res.getString(R.string.brush_opacity), 0, 100, 50));
    }

    ;

    public static BrushData getInstance() {
        return brushData;
    }

    public List<Brush> getList() {
        return list;
    }

    // order of the elements is same as adding its to the list
    public enum Property {
        SIZE, SPACING, ROUNDNESS, ANGLE, HUE, SATURATION, VALUE, OPACITY
    }

    public class Brush {
        private String name;
        private int minValue;
        private int maxValue;
        private int currentValue;

        public Brush(String name, int minValue, int maxValue, int currentValue) {
            this.name = name;
            this.minValue = minValue;
            this.maxValue = maxValue;
            this.currentValue = currentValue;
        }

        public String getName() {
            return name;
        }

        public int getMinValue() {
            return minValue;
        }

        public int getMaxValue() {
            return maxValue;
        }

        public int getCurrentValue() {
            return currentValue;
        }

        public void setCurrentValue(int currentValue) {
            this.currentValue = currentValue;
        }
    }
}
