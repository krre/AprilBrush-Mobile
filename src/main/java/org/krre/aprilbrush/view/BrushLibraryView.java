package org.krre.aprilbrush.view;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import org.krre.aprilbrush.R;
import org.krre.aprilbrush.logic.BrushDbHelper;

import java.util.ArrayList;

public class BrushLibraryView extends ListView implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "AB";
    private BrushDbHelper brushDbHelper;
    private SQLiteDatabase db;
    private Context context;
    private SimpleCursorAdapter adapter;

    public BrushLibraryView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        brushDbHelper = new BrushDbHelper(context);
        db = brushDbHelper.getWritableDatabase();
        String[] from = new String[] { "name" };
        int[] to = new int[] { R.id.brushlibTextView };
        adapter = new SimpleCursorAdapter(context, R.layout.brushlib_item, null, from, to, 0);
        setAdapter(adapter);

        Activity hostActivity = (Activity)context;
        hostActivity.getLoaderManager().initLoader(0, null, this);
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

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        return new CursorLoader(context) {
            @Override
            public Cursor loadInBackground() {
                return db.query(BrushDbHelper.TABLE, null, null, null, null, null, null);
            }
        };
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
