package ua.inf.krre.aprilbrush.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import org.json.JSONArray;
import org.json.JSONException;

import ua.inf.krre.aprilbrush.R;
import ua.inf.krre.aprilbrush.data.BrushData;
import ua.inf.krre.aprilbrush.data.CanvasData;
import ua.inf.krre.aprilbrush.dialog.ColorDialog;
import ua.inf.krre.aprilbrush.logic.JSONSharedPreferences;
import ua.inf.krre.aprilbrush.logic.UndoManager;
import ua.inf.krre.aprilbrush.view.PaintView;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    public static final String PREFS_NAME = "prefs";
    private ColorDialog colorDialog;
    private UndoManager undoManager;
    private PaintView paintView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        ImageButton undoButton = (ImageButton) findViewById(R.id.undoImageButton);
        undoButton.setOnClickListener(this);
        ImageButton brushButton = (ImageButton) findViewById(R.id.brushImageButton);
        brushButton.setOnClickListener(this);
        ImageButton colorButton = (ImageButton) findViewById(R.id.colorImageButton);
        colorButton.setOnClickListener(this);
        ImageButton fillButton = (ImageButton) findViewById(R.id.fillImageButton);
        fillButton.setOnClickListener(this);
        ImageButton redoButton = (ImageButton) findViewById(R.id.redoImageButton);
        redoButton.setOnClickListener(this);

        try {
            JSONSharedPreferences.loadJSONArray(getBaseContext(), PREFS_NAME, BrushData.PREF_ITEM_NAME);
        } catch (JSONException e) {
            throw new RuntimeException();
        }

        undoManager = UndoManager.getInstance();
        paintView = (PaintView) findViewById(R.id.paintView);
        colorDialog = new ColorDialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.undoImageButton:
                undoManager.undo();
                paintView.invalidate();
                break;
            case R.id.brushImageButton:
                Intent intent = new Intent(this, BrushSettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.colorImageButton:
                colorDialog.show(getSupportFragmentManager(), "color");
                break;
            case R.id.fillImageButton:
                CanvasData.getInstance().clear();
                paintView.invalidate();
                break;
            case R.id.redoImageButton:
                undoManager.redo();
                paintView.invalidate();
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        JSONSharedPreferences.saveJSONArray(getBaseContext(), PREFS_NAME, BrushData.PREF_ITEM_NAME, new JSONArray(BrushData.getInstance().getList()));
    }
}
