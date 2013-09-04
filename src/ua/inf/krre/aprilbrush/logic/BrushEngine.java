package ua.inf.krre.aprilbrush.logic;

import android.util.Log;
import android.view.MotionEvent;

public class BrushEngine {
    public void paintDab(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        Log.d("AB", "x = " + x + " y = " + y);
    }

}
