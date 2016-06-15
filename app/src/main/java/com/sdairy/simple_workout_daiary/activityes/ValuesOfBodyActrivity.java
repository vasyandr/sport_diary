package com.sdairy.simple_workout_daiary.activityes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.sdairy.simple_workout_daiary.AppContextProgram;
import com.sdairy.simple_workout_daiary.Model.ValuesOfBody;
import com.sdairy.simple_workout_daiary.R;
import com.sdairy.simple_workout_daiary.animation.SwipeDismissListViewTouchListener;
import com.sdairy.simple_workout_daiary.database.HelperFactory;
import com.sdairy.simple_workout_daiary.lists.ValuesExpList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ValuesOfBodyActrivity extends AppCompatActivity {


    private List<ValuesOfBody> valuesOfBodies = new ArrayList<>();
    private ExpandableListView expandableListView;
    private ValuesExpList valuesExpList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_values_of_body);
        expandableListView = (ExpandableListView) findViewById(R.id.list_values);
        try {
            valuesOfBodies.addAll(HelperFactory.getHelper().getValuesDAO().getAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        valuesExpList = new ValuesExpList(this, valuesOfBodies);

        expandableListView.setAdapter(valuesExpList);


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
                                        HelperFactory.getHelper().getValuesDAO().delete(valuesOfBodies.get(position));
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                    valuesOfBodies.remove(position);
                                }
                                valuesExpList.notifyDataSetChanged();
                            }
                        });
        expandableListView.setOnTouchListener(touchListener);
        expandableListView.setOnScrollListener(touchListener.makeScrollListener());

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    public void editItemValues(View view) {
        Intent intent = new Intent(this, ValuesBodyEditor.class);
        AppContextProgram.tempValuesOfBody = valuesOfBodies.get((Integer) view.getTag());
        startActivityForResult(intent, 1);
    }


    public void addValue(View view) {
        AppContextProgram.tempValuesOfBody = null;

        Intent intent = new Intent(this, ValuesBodyEditor.class);
        startActivityForResult(intent, 1);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            valuesOfBodies.clear();
            valuesOfBodies.addAll(HelperFactory.getHelper().getValuesDAO().getAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        valuesExpList.notifyDataSetChanged();
    }
}

