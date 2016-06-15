package com.sdairy.simple_workout_daiary;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sdairy.simple_workout_daiary.Model.Train;
import com.sdairy.simple_workout_daiary.activityes.PreferencesActivity;
import com.sdairy.simple_workout_daiary.activityes.Results;
import com.sdairy.simple_workout_daiary.activityes.ValuesOfBodyActrivity;
import com.sdairy.simple_workout_daiary.activityes.train_days_prog;
import com.sdairy.simple_workout_daiary.activityes.train_process;
import com.sdairy.simple_workout_daiary.database.HelperFactory;
import com.sdairy.simple_workout_daiary.lists.ExpListAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.view.LineChartView;

public class MainActivity extends AppCompatActivity {

    private List<Train> trains = new ArrayList<>();
    private Button button;
    private Train curDay;
    private AlertDialog.Builder builder;
    private ListView trainlist;
    private ExpListAdapter expListAdapter;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadTrains();

        trainlist = (ListView) findViewById(R.id.trainlist);
        expListAdapter = new ExpListAdapter(this, trains);
        trainlist.setAdapter(expListAdapter);
        trainlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long duration) {
                if (trains.get(position).getExercises().isEmpty()) {
                    Snackbar.make(findViewById(R.id.content), getResources().getString(R.string.no_exercises), Snackbar.LENGTH_LONG).show();
                } else {
                    AppContextProgram.tempTrain = trains.get(position);
                    Intent intent = new Intent(getBaseContext(), train_process.class);
                    startActivity(intent);
                }
            }
        });
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);


        LineChartView chartView = (LineChartView) findViewById(R.id.chart1);
        chartView.setZoomEnabled(false);


        if (trains == null) {

        } else {

            builder = new AlertDialog.Builder(this);
            button = (Button) findViewById(R.id.startdate);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    final Intent intent = new Intent(getBaseContext(), train_process.class);
                    if (curDay == null || curDay.getExercises().isEmpty()) {
                        builder.setTitle(getResources().getString(R.string.today_dont_have_train));
                        ArrayList<String> tmp = new ArrayList<>();
                        for (Train x : trains) {
                            tmp.add(x.getName());
                        }
                        String[] output = new String[tmp.size()];
                        for (int i = 0; i != tmp.size(); i++) {
                            output[i] = tmp.get(i);
                        }
                        builder.setItems(output, new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (trains.get(which).getExercises().isEmpty()) {
                                    Snackbar.make(findViewById(R.id.content), getResources().getString(R.string.no_exercises), Snackbar.LENGTH_LONG).show();
                                } else {
                                    try {
                                        AppContextProgram.tempTrain = trains.get(which);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    startActivity(intent);
                                }
                            }
                        });
                        builder.show();
                    } else {
                        try {
                            AppContextProgram.tempTrain = curDay;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        startActivity(intent);
                    }
                }
            });

        }

        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean strUserName = SP.getBoolean("vibroflag", false);
        AppContextProgram.NOTIFICATE_BY_VIBRO = strUserName;
        boolean bAppUpdates = SP.getBoolean("soundflag", false);
        AppContextProgram.planTime = (long) (Float.parseFloat(SP.getString("time_value", String.valueOf(3))) * 60000);
        AppContextProgram.NOTIFICATE_BY_SOUND = bAppUpdates;


    }


    public void loadTrains() {
        try {
            trains.clear();
            trains.addAll(HelperFactory.getHelper().getTrainDAO().getAllTrains());
            curDay = HelperFactory.getHelper().getTrainDAO().getTrainDay(new Date().getDay()).get(0);
        } catch (Exception e) {
            Log.i("erorr", e.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.add_prog) {
            Intent intent = new Intent(this, train_days_prog.class);
            startActivityForResult(intent, 1);
            return true;
        }
        if (id == R.id.action_results) {
            Intent intent = new Intent(this, Results.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.settings) {
            Intent intent = new Intent(this, PreferencesActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.volumess) {
            Intent intent = new Intent(this, ValuesOfBodyActrivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        loadTrains();
        expListAdapter.notifyDataSetChanged();
    }


}
