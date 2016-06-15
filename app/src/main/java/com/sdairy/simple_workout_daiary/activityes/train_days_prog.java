package com.sdairy.simple_workout_daiary.activityes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sdairy.simple_workout_daiary.AppContextProgram;
import com.sdairy.simple_workout_daiary.Model.Train;
import com.sdairy.simple_workout_daiary.R;
import com.sdairy.simple_workout_daiary.animation.SwipeDismissListViewTouchListener;
import com.sdairy.simple_workout_daiary.database.HelperFactory;
import com.sdairy.simple_workout_daiary.lists.ExpListAdapter;

import java.sql.SQLException;
import java.util.List;

public class train_days_prog extends AppCompatActivity {

    private ListView expandableListView;
    private List<Train> trains = AppContextProgram.tempTrainList;
    private ExpListAdapter expListAdapter;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_days_prog);
        expandableListView = (ListView) findViewById(R.id.train_exp_list);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        try {
            trains.addAll(HelperFactory.getHelper().getTrainDAO().getAllTrains());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        expListAdapter = new ExpListAdapter(getBaseContext(), trains);
        expandableListView.setAdapter(expListAdapter);
        AppContextProgram.expListAdapterTemp = expListAdapter;

        expandableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long duration) {
                AddTrainDialogFragment dlg = new AddTrainDialogFragment();
                AppContextProgram.tempTrain = trains.get(position);
                dlg.show(getFragmentManager(), "dlg1");
            }
        });

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        expandableListView,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (final int position : reverseSortedPositions) {
                                    try {
                                        HelperFactory.getHelper().getTrainDAO().delete(trains.get(position));
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                    expListAdapter.remove(expListAdapter.getItem(position));
                                }
                                expListAdapter.notifyDataSetChanged();
                            }
                        });

        expandableListView.setOnTouchListener(touchListener);
        expandableListView.setOnScrollListener(touchListener.makeScrollListener());

    }


    public void addTrainDay(View view) {
        AddTrainDialogFragment dlg = new AddTrainDialogFragment();
        AppContextProgram.tempTrain = null;
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
