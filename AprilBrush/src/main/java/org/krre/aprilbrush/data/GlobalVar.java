package org.krre.aprilbrush.data;

import android.util.Log;

public class GlobalVar {
    private final String TAG = "AB";
    private static GlobalVar globalVar = new GlobalVar();
    private boolean penMode = false;

    private boolean isEmulator = false;

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
    }

    public boolean isEmulator() {
        return isEmulator;
    }

    public void setEmulator(boolean isEmulator) {
        this.isEmulator = isEmulator;
        setPenMode(!isEmulator);
    }
}
