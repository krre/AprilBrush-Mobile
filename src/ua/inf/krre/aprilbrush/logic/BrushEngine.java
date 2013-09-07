package ua.inf.krre.aprilbrush.logic;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

public class BrushEngine {
    private final String TAG = "AB";
    Paint paint;

    public BrushEngine() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
    }

    public void paintDab(Canvas canvas, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        float radius = 15;

        for (int i = 0; i < event.getHistorySize(); i++) {
            x = event.getHistoricalX(i);
            y = event.getHistoricalY(i);
            //Log.d(TAG, "history x = " + x + " history y = " + y);
            canvas.drawCircle(x, y, radius, paint);
        }
        //Log.d(TAG, "x = " + x + " y = " + y);
        canvas.drawCircle(x, y, radius, paint);
    }
}
