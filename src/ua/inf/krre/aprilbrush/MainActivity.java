package ua.inf.krre.aprilbrush;

import ua.inf.krre.aprilbrush.logic.BrushEngine;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;

public class MainActivity extends Activity {
    private BrushEngine engine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        engine = new BrushEngine();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        engine.paintDab(event);
        return true;
    }
}
