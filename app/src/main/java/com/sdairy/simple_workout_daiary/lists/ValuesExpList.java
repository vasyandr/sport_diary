package com.sdairy.simple_workout_daiary.lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sdairy.simple_workout_daiary.Model.ValuesOfBody;
import com.sdairy.simple_workout_daiary.R;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Sergey_Dresvyanin on 10.06.2016.
 */
public class ValuesExpList extends BaseExpandableListAdapter {
    private Context context;
    private List<ValuesOfBody> valuesOfBodies;

    public ValuesExpList(Context context, List<ValuesOfBody> trainDays) {
        this.context = context;
        this.valuesOfBodies = trainDays;
    }

    @Override
    public int getGroupCount() {
        return valuesOfBodies.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public ValuesOfBody getGroup(int groupPosition) {
        return valuesOfBodies.get(groupPosition);
    }

    @Override
    public ValuesOfBody getChild(int groupPosition, int childPosition) {
        return valuesOfBodies.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ValuesOfBody val = valuesOfBodies.get(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.item_group_result, null);
        }
        TextView dayName = (TextView) convertView.findViewById(R.id.date_exec_value);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MM:yyyy");
        ImageButton imageButton = (ImageButton) convertView.findViewById(R.id.edit_item);
        imageButton.setTag(groupPosition);
        dayName.setText(simpleDateFormat.format(val.getDataex()));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ValuesOfBody val = valuesOfBodies.get(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.values_item_of_lits, parent, false);
        }
        String bic = String.format("%.1f", val.getBiceps());
        String chest = String.format("%.1f", val.getChest());
        String shoulder = String.format("%.1f", val.getShoulder());
        String leg = String.format("%.1f", val.getLeg());
        String ikri = String.format("%.1f", val.getIkri());
        String talia = String.format("%.1f", val.getTalia());

        TextView bicepstv = (TextView) convertView.findViewById(R.id.biceps_item);
        TextView shouldertv = (TextView) convertView.findViewById(R.id.shoulder_Item);
        TextView chesttv = (TextView) convertView.findViewById(R.id.chest_item);
        TextView taliatv = (TextView) convertView.findViewById(R.id.talia_item);
        TextView ikritv = (TextView) convertView.findViewById(R.id.ikri_item);
        TextView legtv = (TextView) convertView.findViewById(R.id.bedro_item);


        bicepstv.setText(context.getResources().getString(R.string.biceps) + ": " + bic + " " + context.getResources().getString(R.string.cm));
        shouldertv.setText(context.getResources().getString(R.string.shoulder) + ": " + shoulder + " " + context.getResources().getString(R.string.cm));
        chesttv.setText(context.getResources().getString(R.string.chest) + ": " + chest + " " + context.getResources().getString(R.string.cm));
        taliatv.setText(context.getResources().getString(R.string.talia) + ": " + talia  + " " + context.getResources().getString(R.string.cm));
        ikritv.setText(context.getResources().getString(R.string.ikri) + ": " + ikri + " " + context.getResources().getString(R.string.cm));
        legtv.setText(context.getResources().getString(R.string.leg) + ": " + leg + " " + context.getResources().getString(R.string.cm));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
