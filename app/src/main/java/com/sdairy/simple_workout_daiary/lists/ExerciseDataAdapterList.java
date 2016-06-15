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
import java.util.List;
import java.util.Locale;

/**
 * Created by Sergey_Dresvyanin on 09.06.2016.
 */
public class ExerciseDataAdapterList extends ArrayAdapter<ExerciseData> {
    private Context context;
    private List<ExerciseData> exercises;

    public ExerciseDataAdapterList(Context context, List<ExerciseData> exercises) {
        super(context, R.layout.item_results_edit, exercises);
        this.exercises = exercises;
        this.context = context;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            ViewHolder holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_results_edit, parent, false);
            holder.weightItem = (TextView) convertView.findViewById(R.id.weight_item);
            holder.countItem = (TextView) convertView.findViewById(R.id.reps_item);
            holder.restItem = (TextView) convertView.findViewById(R.id.rest_time_item);
            holder.dataitem = (TextView) convertView.findViewById(R.id.date_item);

            convertView.setTag(holder);
        }

        final ViewHolder holder = (ViewHolder) convertView.getTag();
        ExerciseData item = getItem(position);
        holder.weightItem.setText(context.getResources().getString(R.string.weight) + ": " + String.valueOf(item.getWeight()));
        holder.countItem.setText(context.getResources().getString(R.string.reps) + ": " + String.valueOf(item.getReps()));
        SimpleDateFormat formating = new SimpleDateFormat("mm:ss");
        holder.restItem.setText(context.getResources().getString(R.string.rest) + ": " + formating.format(item.getRestTime()));
        SimpleDateFormat sdf = new SimpleDateFormat("MM:dd:yy", Locale.US);
        holder.dataitem.setText(sdf.format(item.getDataExec()));
        return convertView;

    }

    protected static class ViewHolder {
        public TextView weightItem;
        public TextView countItem;
        public TextView restItem;
        public TextView dataitem;
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
