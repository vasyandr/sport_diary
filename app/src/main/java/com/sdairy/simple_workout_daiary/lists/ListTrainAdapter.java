package com.sdairy.simple_workout_daiary.lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sdairy.simple_workout_daiary.Model.ExerciseData;
import com.sdairy.simple_workout_daiary.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * Created by poq on 01.05.2016.
 */
public class ListTrainAdapter extends ArrayAdapter<ExerciseData> {

    private Context context;
    private ArrayList<ExerciseData> exercises;

    public ListTrainAdapter(Context context, ArrayList<ExerciseData> exercises) {
        super(context, R.layout.list_train_item, exercises);
        this.exercises = exercises;
        this.context = context;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            ViewHolder holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_train_item, parent, false);
            holder.weightItem = (TextView) convertView.findViewById(R.id.weight_coun_item);
            holder.countItem = (TextView) convertView.findViewById(R.id.coun_item);
            holder.restItem = (TextView) convertView.findViewById(R.id.rest_time_item);
            holder.nameItem = (TextView) convertView.findViewById(R.id.name_item);
            convertView.setTag(holder);
        }

        final ViewHolder holder = (ViewHolder) convertView.getTag();
        ExerciseData item = getItem(position);
        holder.nameItem.setText(String.valueOf(item.getName_id().getName()));
        holder.weightItem.setText(String.valueOf(item.getWeight()));
        holder.countItem.setText(String.valueOf(item.getReps()));
        SimpleDateFormat formating = new SimpleDateFormat("mm:ss");
        holder.restItem.setText(formating.format(item.getRestTime()));
        return convertView;

    }

    protected static class ViewHolder {
        public TextView weightItem;
        public TextView countItem;
        public TextView restItem;
        public TextView nameItem;
    }

    @Override
    public int getCount() {
        return exercises.size();
    }

    @Override
    public ExerciseData getItem(int position) {
        return exercises.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
