package com.sdairy.simple_workout_daiary.activityes;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import com.sdairy.simple_workout_daiary.AppContextProgram;
import com.sdairy.simple_workout_daiary.ExortToExcell;
import com.sdairy.simple_workout_daiary.Model.Train;
import com.sdairy.simple_workout_daiary.R;
import com.sdairy.simple_workout_daiary.database.HelperFactory;
import com.sdairy.simple_workout_daiary.lists.ResultsListAdapter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Results extends AppCompatActivity {
    private final static int WRITE_DATA_TO_STORAGE = 232;

    private List<Train> trainList = new ArrayList<>();
    private ExpandableListView expandalbeListView;
    private ResultsListAdapter resultListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        try {
            trainList.addAll(HelperFactory.getHelper().getTrainDAO().getAllTrains());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        expandalbeListView = (ExpandableListView) findViewById(R.id.expandableListView);
        resultListAdapter = new ResultsListAdapter(this, trainList);
        expandalbeListView.setAdapter(resultListAdapter);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getResources().getString(R.string.results_edit_title));
        expandalbeListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getBaseContext(), ResultsEdit.class);
                AppContextProgram.tempExercise = resultListAdapter.getChild(groupPosition, childPosition);
                startActivity(intent);
                return true;
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.close: {
                onBackPressed();
                return true;
            }
            case R.id.export_to_excell: {
                if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 1, 1) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            WRITE_DATA_TO_STORAGE);
                } else {
                    if (ExortToExcell.hasExternalStoragePublicPicture()) {
                        ExortToExcell.deleteExternalStoragePublicPicture();
                    }
                    try {
                        ExortToExcell.export();
                        Snackbar.make(findViewById(R.id.content), getResources().getString(R.string.file_is_saved), Snackbar.LENGTH_LONG).show();
                    } catch (Exception e) {
                    }
                }

            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case WRITE_DATA_TO_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ExortToExcell.hasExternalStoragePublicPicture()) {
                        ExortToExcell.deleteExternalStoragePublicPicture();
                    }
                    try {
                        ExortToExcell.export();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.save, menu);
        return true;
    }
}
