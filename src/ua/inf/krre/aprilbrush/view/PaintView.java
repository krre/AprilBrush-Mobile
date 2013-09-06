package ua.inf.krre.aprilbrush.view;

import ua.inf.krre.aprilbrush.logic.BrushEngine;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class PaintView extends View {
    private BrushEngine engine;
    
    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        engine = new BrushEngine();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        engine.paintDab(event);

        return true;
    }

}
