package org.krre.aprilbrush.view;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.AttributeSet;
import android.widget.ListView;

import org.krre.aprilbrush.R;
import org.krre.aprilbrush.logic.BrushDbHelper;

import java.util.ArrayList;

public class BrushLibraryView extends ListView {
    private static final String TAG = "AB";
    private BrushDbHelper brushDbHelper;
    private SQLiteDatabase db;
    private Context context;

    public BrushLibraryView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        brushDbHelper = new BrushDbHelper(context);
        db = brushDbHelper.getWritableDatabase();
    }

    public void add(ArrayList<SliderView> sliderViews) {
        String countQuery = "SELECT * FROM " + BrushDbHelper.TABLE;
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        cursor.close();

        ContentValues cv = new ContentValues();
        cv.put("name", "Brush " + rowCount);

        String[] brushNames = context.getResources().getStringArray(R.array.brush_names);
        for (int i = 0; i < sliderViews.size(); i++) {
            String name = brushNames[i].toLowerCase().replace(" ", "_");
            int value = sliderViews.get(i).getValue();
            cv.put(name, value);
        }

        db.insert(BrushDbHelper.TABLE, null, cv);
    }
}
