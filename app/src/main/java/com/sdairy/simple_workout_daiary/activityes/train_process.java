package com.sdairy.simple_workout_daiary.activityes;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sdairy.simple_workout_daiary.AppContextProgram;
import com.sdairy.simple_workout_daiary.Model.Exercise;
import com.sdairy.simple_workout_daiary.Model.ExerciseData;
import com.sdairy.simple_workout_daiary.Model.Train;
import com.sdairy.simple_workout_daiary.R;
import com.sdairy.simple_workout_daiary.database.HelperFactory;
import com.sdairy.simple_workout_daiary.lists.ListTrainAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class train_process extends AppCompatActivity {

    private Exercise currentExercise;
    private TextView nameExercise;
    private EditText weight;
    private EditText count;
    private Snackbar toast;
    private long resultTime;
    private ListView listView;
    private Chronometer chronometer;
    private ListTrainAdapter listTrainAdapter;
    private long multTime = 0;

    private ArrayList<ExerciseData> exerciseDatas = new ArrayList<>();
    private int exercise_index = 0;
    private int curSet = 0;
    private ArrayList<Exercise> exercises = new ArrayList<>();
    private ExerciseData tempExData;
    private long tmp;
    private List<ExerciseData> previuosData;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final Train tr = AppContextProgram.tempTrain;
        exercises.addAll(tr.getExercises());
        currentExercise = exercises.get(exercise_index);
        setContentView(R.layout.activity_train_process);
        nameExercise = (TextView) findViewById(R.id.exercise_name);
        weight = (EditText) findViewById(R.id.weight_count);
        count = (EditText) findViewById(R.id.count_reps);
        listView = (ListView) findViewById(R.id.results_today);
        nameExercise.setText(currentExercise.getName());
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long elapsedMillis = SystemClock.elapsedRealtime()
                        - chronometer.getBase();
                tmp = SystemClock.elapsedRealtime() - chronometer.getBase();
            }
        });
        chronometer.start();
        toast = Snackbar.make(findViewById(R.id.contenttr), "", Snackbar.LENGTH_LONG);

        listTrainAdapter = new ListTrainAdapter(getBaseContext(), exerciseDatas);
        listView.setAdapter(listTrainAdapter);
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getResources().getString(R.string.train));
        callAsynchronousTask();
        try {
            previuosData = HelperFactory.getHelper().getExerciseDataDAO().getPreviuosExerciseData(currentExercise);
            ExerciseData prevData = previuosData.get(curSet);
            weight.setHint(weight.getHint() + ": " + String.valueOf(prevData.getWeight()));
            count.setHint(getResources().getString(R.string.shortReps) + ": " + String.valueOf(prevData.getReps()));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        final Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {

                handler.post(new Runnable() {
                    public void run() {
                        try {
                            MyCheckTraed performBackgroundTask = new MyCheckTraed();
                            performBackgroundTask.execute(tmp);
                            performBackgroundTask.execute();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 1000);
    }

    private class MyCheckTraed extends AsyncTask<Long, String, String> {

        @Override
        protected String doInBackground(Long... params) {
            long chekcedTime = params[params.length - 1] - AppContextProgram.planTime;
            if (chekcedTime > multTime) {

                NotificationCompat.Builder nv = new NotificationCompat.Builder(train_process.this);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                multTime = multTime + AppContextProgram.planTime;
                if (AppContextProgram.NOTIFICATE_BY_VIBRO) {
                    nv.setVibrate(new long[]{1000, 2000});
                }
                if (AppContextProgram.NOTIFICATE_BY_SOUND) {
                    nv.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
                }
                notificationManager.notify(1, nv.build());
            }

            return null;
        }
    }


    private void addExercise(boolean flag) {
        try {
            Double weightNubmer = Double.parseDouble(String.valueOf(weight.getText()));
            try {

                int countNumber = Integer.parseInt(String.valueOf(count.getText()));
                tempExData = new ExerciseData();
                tempExData.setName_id(currentExercise);
                tempExData.setWeight(weightNubmer);
                tempExData.setReps(countNumber);
                tempExData.setRestTime(resultTime);
                tempExData.setDataExec(Calendar.getInstance().getTimeInMillis());
                toast.setText(curSet + 1 + getResources().getString(R.string.asdadaddadawa) + " " + currentExercise.getSets() + " " + getResources().getString(R.string.planned)).show();
                exerciseDatas.add(tempExData);
                HelperFactory.getHelper().getExerciseDataDAO().create(tempExData);
                listTrainAdapter.notifyDataSetChanged();
                curSet++;

            } catch (Exception e) {
                if (!flag) {
                    e.printStackTrace();
                    toast.setText(getResources().getString(R.string.enter_reps_count)).show();
                }
            }
        } catch (Exception e) {
            if (!flag) {
                toast.setText(getResources().getString(R.string.enter_weight)).show();
            }
        }

    }

    public void nextSet(View view) {

        multTime = 0;
        resultTime = SystemClock.elapsedRealtime() - chronometer.getBase();
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.stop();
        chronometer.start();
        addExercise(false);
        clearForms();

        try {
            ExerciseData prevData = previuosData.get(curSet);
            weight.setHint(getResources().getString(R.string.weight) + ": " + String.valueOf(prevData.getWeight()));
            count.setHint(getResources().getString(R.string.shortReps) + ": " + String.valueOf(prevData.getReps()));
        } catch (Exception e) {
            weight.setHint(getResources().getString(R.string.weight));
            count.setHint(getResources().getString(R.string.reps));
        }
    }

    public void nextExercise(View view) {

        multTime = 0;
        resultTime = SystemClock.elapsedRealtime() - chronometer.getBase();
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.stop();
        chronometer.start();
        curSet = 0;
        if (exercise_index == exercises.size() - 1) {
            toast.setText(getResources().getString(R.string.train_was_ended)).show();
            toast.show();
            finish();
        } else {
            exercise_index++;
            currentExercise = exercises.get(exercise_index);
            nameExercise.setText(currentExercise.getName());
            addExercise(true);
            clearForms();
        }
        try {
            previuosData = HelperFactory.getHelper().getExerciseDataDAO().getPreviuosExerciseData(currentExercise);
            ExerciseData prevData = previuosData.get(curSet);
            weight.setHint(weight.getHint() + ": " + String.valueOf(prevData.getWeight()));
            count.setHint(getResources().getString(R.string.shortReps) + ": " + String.valueOf(prevData.getReps()));
        } catch (Exception e) {

            weight.setHint(getResources().getString(R.string.weight));
            count.setHint(getResources().getString(R.string.reps));

            e.printStackTrace();
        }

    }


    private void clearForms() {
        weight.setText("");
        count.setText("");
        count.clearFocus();
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


    @Override
    public void onBackPressed() {
        addExercise(true);
        Snackbar.make(findViewById(R.id.contenttr), getResources().getString(R.string.are_you_shure_end_train), Snackbar.LENGTH_LONG).setAction(getResources().getString(R.string.yes), mOnClickListener).setActionTextColor(Color.RED).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.closetrain, menu);
        return true;
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
