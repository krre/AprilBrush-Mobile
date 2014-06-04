package org.krre.aprilbrush.data;

import android.os.Build;

public class GlobalVar {
    private final String TAG = "AB";
    private static GlobalVar globalVar = new GlobalVar();
    private boolean penMode = false;
    private int memoryClass;

    private final boolean isEmulator = Build.BRAND.equals("generic");

    private GlobalVar() {
        setPenMode(!isEmulator);
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

    public void setMemoryClass(int memoryClass) {
        this.memoryClass = memoryClass;
    }
}
