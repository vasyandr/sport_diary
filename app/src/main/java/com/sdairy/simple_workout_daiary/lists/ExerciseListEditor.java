package com.sdairy.simple_workout_daiary.lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sdairy.simple_workout_daiary.Model.Exercise;
import com.sdairy.simple_workout_daiary.R;

import java.util.List;

/**
 * Created by Sergey_Dresvyanin on 04.06.2016.
 */
public class ExerciseListEditor extends ArrayAdapter<Exercise> {
    private Context context;
    private List<Exercise> exercises;

    public ExerciseListEditor(Context context, List<Exercise> exercises) {
        super(context, R.layout.item_exercise_list, exercises);
        this.exercises = exercises;
        this.context=context;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            ViewHolder holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_exercise_list, parent, false);
            holder.nameItem = (TextView) convertView.findViewById(R.id.ex_name);
            holder.countItem = (TextView) convertView.findViewById(R.id.sets_item);
            convertView.setTag(holder);
        }

        final ViewHolder holder = (ViewHolder) convertView.getTag();
        Exercise item = getItem(position);
        holder.nameItem.setText(String.valueOf(item.getName()));
        holder.countItem.setText(context.getResources().getString(R.string.sets) + " " + item.getSets());
        return convertView;

    }

    protected static class ViewHolder {
        public TextView countItem;
        public TextView nameItem;
    }

    @Override
    public int getCount() {
        return exercises.size();
    }

    @Override
    public Exercise getItem(int position) {
        return exercises.get(position);
    }

    @Override
    public long getItemId(int position) {
        return exercises.get(position).getId();
    }
}
