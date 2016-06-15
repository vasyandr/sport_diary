package com.sdairy.simple_workout_daiary.lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sdairy.simple_workout_daiary.Model.Train;
import com.sdairy.simple_workout_daiary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey_Dresvyanin on 03.06.2016.
 */
public class ExpListAdapter extends ArrayAdapter<Train> {
    private Context context;
    private List<Train> trains;

    public ExpListAdapter(Context context, List<Train> exercises) {
        super(context, R.layout.exp_list_item, exercises);
        this.trains = exercises;
        this.context = context;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            ViewHolder holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.exp_list_item, parent, false);
            holder.nameItem = (TextView) convertView.findViewById(R.id.name_item);
            convertView.setTag(holder);
        }
        final ViewHolder holder = (ViewHolder) convertView.getTag();
        Train item = getItem(position);
        holder.nameItem.setText(String.valueOf(item.getName()));
        return convertView;

    }

    public Train get(int position) {
        return null;
    }

    protected static class ViewHolder {
        public TextView nameItem;
    }

    @Override
    public int getCount() {
        return trains.size();
    }

    @Override
    public Train getItem(int position) {
        return trains.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
