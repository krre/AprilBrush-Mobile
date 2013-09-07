package ua.inf.krre.aprilbrush.logic;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class BrushEngine {
    private Paint paint;
    private Canvas canvas;

    private float prevX;
    private float prevY;

    private int spacing = 50;
    private int size = 20;

    public BrushEngine() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setAlpha(128);
    }

    public void setTouch(Canvas canvas, MotionEvent event) {
        this.canvas = canvas;
        float x = event.getX();
        float y = event.getY();
        canvas.drawCircle(x, y, size, paint);
        prevX = x;
        prevY = y;
    }

    public void paintDab(MotionEvent event) {
        float x, y;
        for (int i = 0; i < event.getHistorySize(); i++) {
            x = event.getHistoricalX(i);
            y = event.getHistoricalY(i);
            interpolateDab(x, y);
        }
        x = event.getX();
        y = event.getY();
        interpolateDab(x, y);
    }

    private void interpolateDab(float x, float y) {
        double length = Math.sqrt(Math.pow(prevX - x, 2)
                + Math.pow(prevY - y, 2));
        float deltaDab = size * spacing / 100;
        // drawing dabs between the events
        if (length >= deltaDab) {
            long numDabs = Math.round(length / deltaDab);
            double angle = Math.atan2(x - prevX, y - prevY);
            double deltaX = deltaDab * Math.sin(angle);
            double deltaY = deltaDab * Math.cos(angle);
            float betweenX = 0;
            float betweenY = 0;
            for (int i = 1; i <= numDabs; i++) {
                betweenX = (float) (prevX + deltaX * i);
                betweenY = (float) (prevY + deltaY * i);
                canvas.drawCircle(betweenX, betweenY, size, paint);
            }
            prevX = betweenX;
            prevY = betweenY;
        }
    }
}
