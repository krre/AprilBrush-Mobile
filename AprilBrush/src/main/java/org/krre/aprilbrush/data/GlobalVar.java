package org.krre.aprilbrush.data;

import android.util.Log;

public class GlobalVar {
    private final String TAG = "AB";
    private static GlobalVar globalVar = new GlobalVar();
    private boolean penMode = false;

    private GlobalVar() {
    }

    public static GlobalVar getInstance() {
        return globalVar;
    }

    public boolean isPenMode() {
        return penMode;
    }

    public void setPenMode(boolean penMode) {
        this.penMode = penMode;
        Log.d(TAG, "Pen = " + penMode);
    }
}
