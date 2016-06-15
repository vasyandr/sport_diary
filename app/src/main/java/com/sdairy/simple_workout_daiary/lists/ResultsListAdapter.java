package com.sdairy.simple_workout_daiary.lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.sdairy.simple_workout_daiary.Model.Exercise;
import com.sdairy.simple_workout_daiary.Model.Train;
import com.sdairy.simple_workout_daiary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey_Dresvyanin on 07.06.2016.
 */
public class ResultsListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Train> trainDays;

    public ResultsListAdapter(Context context, List<Train> trainDays) {
        this.context = context;
        this.trainDays = trainDays;
    }

    @Override
    public int getGroupCount() {
        return trainDays.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return trainDays.get(groupPosition).getExercises().size();
    }

    @Override
    public List<Exercise> getGroup(int groupPosition) {
        List<Exercise> tmp = new ArrayList<>();
        tmp.addAll(trainDays.get(groupPosition).getExercises());
        return tmp;
    }

    @Override
    public Exercise getChild(int groupPosition, int childPosition) {
        List<Exercise> tmp = new ArrayList<>();
        tmp.addAll(getGroup(groupPosition));
        return tmp.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Train tr = trainDays.get(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.exp_list_item, null);
        }
        TextView dayName = (TextView) convertView.findViewById(R.id.name_item);
        dayName.setText(tr.getName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.results_item, parent, false);
        }
        TextView exname = (TextView) convertView.findViewById(R.id.res_second_item);
        Exercise exercise = getChild(groupPosition, childPosition);
        exname.setText(exercise.getName());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

