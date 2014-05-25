package org.krre.aprilbrush.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import org.krre.aprilbrush.R;

public class MainActivity extends Activity {
    private int memoryClass;
    private String TAG = "AB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ActivityManager activityManager = (ActivityManager)getBaseContext().getSystemService(Context.ACTIVITY_SERVICE);
        memoryClass = activityManager.getLargeMemoryClass();
    }
}
