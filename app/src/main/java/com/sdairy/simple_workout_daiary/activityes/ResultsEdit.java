package com.sdairy.simple_workout_daiary.activityes;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.sdairy.simple_workout_daiary.AppContextProgram;
import com.sdairy.simple_workout_daiary.Model.ExerciseData;
import com.sdairy.simple_workout_daiary.R;
import com.sdairy.simple_workout_daiary.activityes.utils.DateRangePickerFragment;
import com.sdairy.simple_workout_daiary.animation.SwipeDismissListViewTouchListener;
import com.sdairy.simple_workout_daiary.database.HelperFactory;
import com.sdairy.simple_workout_daiary.lists.ExerciseDataAdapterList;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

public class ResultsEdit extends AppCompatActivity implements DateRangePickerFragment.OnDateRangeSelectedListener {

    private ListView listView;
    private TextView startDate;
    private TextView endDate;
    private String myFormat = "dd:MM:yy";
    private SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    private Calendar myCalendar = Calendar.getInstance();
    private LineChartView chart;
    private DateRangePickerFragment dialog;

    private List<ExerciseData> exerciseDatas;
    private ExerciseDataAdapterList exerciseDataAdapterList;
    private long endData = myCalendar.getTimeInMillis();
    private long startData = 0;
    private TextView empty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_edit);
        listView = (ListView) findViewById(R.id.data_list);
        startDate = (TextView) findViewById(R.id.startdate);
        endDate = (TextView) findViewById(R.id.endDate);
        empty = (TextView) findViewById(R.id.empty);
        startDate.setText(sdf.format(myCalendar.getTimeInMillis()));
        endDate.setText(sdf.format(myCalendar.getTimeInMillis()));
        try {
            exerciseDatas = HelperFactory.getHelper().getExerciseDataDAO().getAllDataAboutExercise(AppContextProgram.tempExercise);
            if (exerciseDatas.size()==0)
            {
                empty.setVisibility(View.VISIBLE);
            }
        } catch (SQLException e) {
            empty.setVisibility(View.VISIBLE);
            e.printStackTrace();
        }
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(AppContextProgram.tempExercise.getName());

        chart = (LineChartView) findViewById(R.id.chart1);
        if (exerciseDatas.size() == 0 || exerciseDatas == null) {
            ((ViewGroup) chart.getParent()).removeView(chart);
        }
        try {
            chart.setLineChartData(getPoints(exerciseDatas));
        } catch (Exception e) {
        }
        exerciseDataAdapterList = new ExerciseDataAdapterList(this, exerciseDatas);
        listView.setAdapter(exerciseDataAdapterList);
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
                                        HelperFactory.getHelper().getExerciseDataDAO().delete(exerciseDataAdapterList.getItem(position));
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                    exerciseDataAdapterList.remove(exerciseDataAdapterList.getItem(position));
                                }
                                exerciseDataAdapterList.notifyDataSetChanged();
                            }
                        });
        listView.setOnTouchListener(touchListener);
        listView.setOnScrollListener(touchListener.makeScrollListener());
        dialog = new DateRangePickerFragment().newInstance(this, false);

    }

    public void datePick(View view) {
        dialog.show(getFragmentManager(), "DatePicker");

    }


    public LineChartData getPoints(List<ExerciseData> exerciseDatas) {

        List<PointValue> weight = new ArrayList<PointValue>();
        List<PointValue> reps = new ArrayList<PointValue>();
        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        for (int i = 0; i < exerciseDatas.size(); i++) {
            ExerciseData tmp = exerciseDatas.get(i);
            weight.add(new PointValue(i, (float) tmp.getWeight()));
            reps.add(new PointValue(i, tmp.getReps()));
            axisValues.add(new AxisValue(i).setLabel(sdf.format(exerciseDatas.get(i).getDataExec())));
        }

        Axis tempoAxis = new Axis(axisValues).setName(getResources().getString(R.string.date)).setHasLines(true).setMaxLabelChars(20).setTextColor(ChartUtils.COLOR_BLUE);

        Line weightLine = new Line(weight).setColor(Color.BLUE).setCubic(true);
        Line repsCount = new Line(reps).setColor(Color.RED).setCubic(true);

        repsCount.setHasLabels(true);
        weightLine.setHasLabels(true);
        repsCount.setHasLines(true);

        List<Line> lines = new ArrayList<Line>();

        lines.add(weightLine);
        lines.add(repsCount);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        data.setAxisXBottom(tempoAxis);
        return data;
    }

    public float getMinWeigth() {
        float minVal = Integer.MAX_VALUE;

        for (ExerciseData x : exerciseDatas) {
            if (x.getWeight() < minVal) {
                minVal = (float) x.getWeight();
            }
        }
        return minVal;
    }

    public float getInterval() {
        float maxVal = getMaxWeight();
        float minVal = getMinWeigth();
        return (minVal - minVal) / exerciseDatas.size();
    }

    public float getMaxWeight() {
        float maxVal = 0;

        for (ExerciseData x : exerciseDatas) {
            if (x.getWeight() > maxVal) {
                maxVal = (float) x.getWeight();
            }
        }
        return maxVal;
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
    public void onDateRangeSelected(int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear) {
        if (exerciseDatas.size()==0)
        {
            empty.setVisibility(View.INVISIBLE);
        }
        myCalendar.set(startYear, startMonth, startDay);
        startData = myCalendar.getTimeInMillis();
        myCalendar.set(endYear, endMonth, endDay);
        endData = myCalendar.getTimeInMillis();
        startDate.setText(sdf.format(startData));
        endDate.setText(sdf.format(endData));

        if (startData < endData) {
            try {
                exerciseDatas.clear();
                exerciseDatas.addAll(HelperFactory.getHelper().getExerciseDataDAO().getDataByIntervalAboutExercise(AppContextProgram.tempExercise, startData, endData));
                exerciseDataAdapterList.notifyDataSetChanged();
                chart.setLineChartData(getPoints(exerciseDatas));
                chart.refreshDrawableState();
                if (exerciseDatas.size()==0)
                {
                    empty.setVisibility(View.VISIBLE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Snackbar.make(findViewById(R.id.content), getResources().getString(R.string.enter_correct_data), Snackbar.LENGTH_LONG).show();
        }
    }
}
