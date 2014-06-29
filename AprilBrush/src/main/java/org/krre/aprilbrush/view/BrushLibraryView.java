package org.krre.aprilbrush.view;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;

import org.krre.aprilbrush.logic.BrushDbHelper;

public class BrushLibraryView extends ListView {
    private static final String TAG = "AB";
    BrushDbHelper brushDbHelper;

    public BrushLibraryView(Context context, AttributeSet attrs) {
        super(context, attrs);

        brushDbHelper = new BrushDbHelper(context);
        SQLiteDatabase db = brushDbHelper.getWritableDatabase();
    }
}
