package org.krre.aprilbrush.logic;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.krre.aprilbrush.R;

public class BrushDbHelper extends SQLiteOpenHelper {
    private static final String TAG = "AB";
    private static final int VERSION = 1;
    public static final String NAME = "brushes.db";
    public static final String TABLE = "base";

    private Context context;

    public BrushDbHelper(Context context) {
        super(context, NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put("name", "Default");
        String query = "CREATE TABLE " + TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, ";
        String[] brushNames = context.getResources().getStringArray(R.array.brush_names);
        int[] brushValues = context.getResources().getIntArray(R.array.brush_values);
        for (int i = 0; i < brushValues.length; i++) {
            String name = brushNames[i].toLowerCase().replace(" ", "_");
            query += name + " INTEGER, ";
            cv.put(name, brushValues[i]);
        }

        query = query.substring(0, query.length() - 2); // remove ending chars ", "
        query += ");";
        db.execSQL(query);
        db.insert(TABLE, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {

    }
}
