package org.krre.aprilbrush.logic;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.Log;
import android.view.MotionEvent;

import org.krre.aprilbrush.data.GlobalVar;
import org.krre.aprilbrush.view.PaintView;
import org.krre.aprilbrush.view.SliderView;

import java.util.Observable;
import java.util.Observer;

public final class BrushEngine implements Observer {
    public static final int SIZE = 0;
    public static final int OPACITY = 1;
    public static final int FLOW = 2;
    public static final int SPACING = 3;
    public static final int HARDNESS = 4;
    public static final int ROUNDNESS = 5;
    public static final int ANGLE = 6;
    public static final int SCATTER = 7;

    private static BrushEngine brushEngine;
    private String TAG = "AB";
    private Bitmap dabBitmap;
    private Bitmap bufferBitmap;
    private PaintView paintView;
    private Paint bufferPaint = new Paint();
    private Paint dabPaint = new Paint();
    private Canvas bufferCanvas = new Canvas();
    private Canvas dabCanvas = new Canvas();
    private Path path = new Path();
    private PathMeasure pathMeasure = new PathMeasure();
    private float pathLength;
    private int toolType;
    private float prevX;
    private float prevY;
    private float prevPressure;

    private int size = 20;
    private int spacing = 100;
    private int color = Color.BLUE;
    private int hardness = 80;
    private int roundness = 100;
    private int angle = 0;
    private int scatter = 0;

    public BrushEngine(PaintView paintView) {
        BrushEngine.brushEngine = this;
        this.paintView = paintView;
        dabPaint.setAntiAlias(true);
        dabBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        dabCanvas.setBitmap(dabBitmap);
        updateDab();
    }

    public static BrushEngine getInstance() {
        return BrushEngine.brushEngine;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        updateDab();
    }

    private void updateDab() {
        dabPaint.setColor(color);
        dabBitmap.eraseColor(Color.TRANSPARENT);
        float radius = (100 - hardness) / 100f * size / 4;
        if (radius > 0) {
            // TODO: change box blur on gaussian blur
            dabPaint.setMaskFilter(new BlurMaskFilter(radius, BlurMaskFilter.Blur.NORMAL));
        } else {
            dabPaint.setMaskFilter(null);
        }
        dabCanvas.save();
        dabCanvas.rotate(angle, size / 2f, size / 2f);
        dabCanvas.scale(1.0f, roundness / 100f, size / 2f, size / 2f);
        dabCanvas.drawCircle(size / 2f, size / 2f, size / 2f - radius, dabPaint);
        dabCanvas.restore();
    }

    public Bitmap getBufferBitmap() {
        return bufferBitmap;
    }

    public void setBitmap(Bitmap value, int orientation) {
        int width  = value.getWidth();
        int height = value.getHeight();
        if ((orientation == Configuration.ORIENTATION_LANDSCAPE && width < height) ||
            (orientation == Configuration.ORIENTATION_PORTRAIT && width > height)) {
            Matrix matrix = new Matrix();
            matrix.postRotate(90f);
            bufferBitmap = Bitmap.createBitmap(value, 0, 0, width, height, matrix, true);
        } else {
            bufferBitmap = Bitmap.createBitmap(value, 0, 0, width, height);
        }
        bufferCanvas.setBitmap(bufferBitmap);
        paintView.invalidate();
    }

    public void setBufferSize(int width, int height) {
        bufferBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bufferCanvas.setBitmap(bufferBitmap);
    }

    public void paintDab(MotionEvent event) {
        toolType = event.getToolType(0);
        if (toolType == MotionEvent.TOOL_TYPE_FINGER && GlobalVar.getInstance().isPenMode()) {
            return;
        }

        float x = event.getX();
        float y = event.getY();
        float pressure  = event.getPressure();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                paintOneDab(x, y, pressure);

                path.reset();
                path.moveTo(x, y);
                pathLength = 0f;

                prevX = x;
                prevY = y;
                prevPressure = pressure;
                break;

            case MotionEvent.ACTION_MOVE:
                if (event.getHistorySize() > 0) {
                    for (int i = 0; i < event.getHistorySize(); i++) {
                        interpolateDab(event.getHistoricalX(i), event.getHistoricalY(i), event.getHistoricalPressure(i));
                    }
                } else {
                    interpolateDab(x, y, pressure);
                }
                break;
            case MotionEvent.ACTION_UP:
                paintView.applyBuffer();
                bufferBitmap.eraseColor(Color.TRANSPARENT);
                break;
        }
    }

    private void interpolateDab(float x, float y, float p) {
        double pointSpace = Math.sqrt(Math.pow(prevX - x, 2) + Math.pow(prevY - y, 2));

        float deltaDab = size * roundness / 100f * spacing / 100f;
        if (pointSpace >= deltaDab) {
            path.quadTo(prevX, prevY, (x + prevX) / 2, (y + prevY) / 2);
        } else {
            path.lineTo(x, y);
        }
        pathMeasure.setPath(path, false);
        float[] pathMeasurePos = new float[2];
        float[] pathMeasureTan = new float[2];
        while (pathMeasure.getLength() >= pathLength) {
            pathMeasure.getPosTan(pathLength, pathMeasurePos, pathMeasureTan);
            if (pathLength > 0) {
                paintOneDab(pathMeasurePos[0], pathMeasurePos[1], p); // TODO: interpolate pressure
            }
            pathLength += deltaDab;
        }
        prevX = x;
        prevY = y;
        prevPressure = p;
    }

    private void paintOneDab(float x, float y, float p) {
        float pressure = toolType == MotionEvent.TOOL_TYPE_STYLUS ? p : 1.0f;
        bufferCanvas.save();
        int alpha = Math.round((pressure * 255f));
        bufferPaint.setAlpha(alpha);
        float paintX = x - size / 2f + size * scatter / 100f * (1 - 2 * (float) Math.random());
        float paintY = y - size / 2f + size * scatter / 100f * (1 - 2 * (float) Math.random());

        bufferCanvas.drawBitmap(dabBitmap, paintX, paintY, bufferPaint);
        bufferCanvas.restore();
        paintView.invalidate((int)paintX - 1, (int)paintY - 1, (int)paintX + size + 1, (int)paintY + size + 1);
    }

    public void update(Observable obj, Object arg) {
        SliderView sliderView = (SliderView) arg;
        int index = sliderView.getId();
        int value = sliderView.getValue();

        switch (index) {
            case SIZE:
                size = value;
                dabBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
                dabCanvas.setBitmap(dabBitmap);
                updateDab();
                break;
            case OPACITY:
                paintView.setOpacity(value);
                break;
            case FLOW:
                color = Color.argb(value * 255 / 100, Color.red(color), Color.green(color), Color.blue(color));
                updateDab();
                break;
            case SPACING:
                spacing = value;
                break;
            case HARDNESS:
                hardness = value;
                updateDab();
                break;
            case ROUNDNESS:
                roundness = value;
                updateDab();
                break;
            case ANGLE:
                angle = value;
                updateDab();
                break;
            case SCATTER:
                scatter = value;
                break;
        }
    }
}
