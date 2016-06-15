package com.sdairy.simple_workout_daiary.activityes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sdairy.simple_workout_daiary.AppContextProgram;
import com.sdairy.simple_workout_daiary.Model.Exercise;
import com.sdairy.simple_workout_daiary.Model.Train;
import com.sdairy.simple_workout_daiary.R;
import com.sdairy.simple_workout_daiary.animation.SwipeDismissListViewTouchListener;
import com.sdairy.simple_workout_daiary.database.HelperFactory;
import com.sdairy.simple_workout_daiary.lists.ExerciseListEditor;

import java.sql.SQLException;
import java.util.List;

public class add_exercise extends AppCompatActivity {

    private ListView listView;
    private Train tr;
    private ExerciseListEditor exerciseListEditor;
    private List<Exercise> exerciseList = AppContextProgram.exerciseTempList;
    private ExerciseEditorFragment dlg;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_exercise);
        listView = (ListView) findViewById(R.id.ex_list);
        tr = AppContextProgram.tempTrain;
        if (tr.getExercises() != null && !tr.getExercises().isEmpty()) {
            exerciseList.addAll(tr.getExercises());
        }

        if (tr == null) {
            tr = new Train();
        }
        exerciseListEditor = new ExerciseListEditor(getBaseContext(), exerciseList);
        listView.setAdapter(exerciseListEditor);
        AppContextProgram.exerciseListEditorTemp = exerciseListEditor;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long duration) {
                dlg = new ExerciseEditorFragment();
                Bundle args = new Bundle();
                args.putSerializable("exercise", exerciseList.get(position));
                dlg.setArguments(args);
                dlg.show(getFragmentManager(), "dlg2");
            }
        });

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        listView,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (final int position : reverseSortedPositions) {
                                    try {
                                        HelperFactory.getHelper().getExerciseDAO().delete(exerciseList.get(position));
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                    exerciseListEditor.remove(exerciseListEditor.getItem(position));
                                }
                                exerciseListEditor.notifyDataSetChanged();
                            }
                        });
        listView.setOnTouchListener(touchListener);
        listView.setOnScrollListener(touchListener.makeScrollListener());
    }

    public void addStrings(View view) {
        ExerciseEditorFragment dlg = new ExerciseEditorFragment();
        Bundle args = new Bundle();
        args.putSerializable("train", tr);
        dlg.setArguments(args);
        dlg.show(getFragmentManager(), "dlg1");
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

        }

        return super.onOptionsItemSelected(item);
    }

}
