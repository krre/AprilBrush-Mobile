package ua.inf.krre.aprilbrush.logic;

import android.util.Log;
import android.view.MotionEvent;

public class BrushEngine {
    private final String TAG = "AB";

    public void paintDab(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        Log.d(TAG, "x = " + x + " y = " + y);
    }
}
