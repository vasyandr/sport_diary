package com.sdairy.simple_workout_daiary.activityes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.sdairy.simple_workout_daiary.AppContextProgram;
import com.sdairy.simple_workout_daiary.Model.Train;
import com.sdairy.simple_workout_daiary.R;
import com.sdairy.simple_workout_daiary.database.HelperFactory;

import java.sql.SQLException;


/**
 * Created by Sergey_Dresvyanin on 04.06.2016.
 */
public class AddTrainDialogFragment extends DialogFragment {

    private EditText editTrainName;
    private Train train;
    private Context context;


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        context = getActivity().getBaseContext();
        boolean flag = true;
        try {
            train = AppContextProgram.tempTrain;
        } catch (Exception e) {
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        builder.setInverseBackgroundForced(false);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setTitle(R.string.editorTrain);
        View view = inflater.inflate(R.layout.add_train_day, null);
        builder.setView(view);
        editTrainName = (EditText) view.findViewById(R.id.editTrainName);
        final Spinner dayOfTheWeek = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(context, R.array.days, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dayOfTheWeek.setAdapter(adapter);
        if (train != null) {
            flag = false;
            editTrainName.setText(train.getName());
            dayOfTheWeek.setSelection(train.getDay_id() - 1);
        } else train = new Train();

        final boolean finalFlag = flag;
        builder.setPositiveButton(R.string.next, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (!editTrainName.getText().toString().trim().isEmpty()) {
                        Intent intent = new Intent(context, add_exercise.class);
                        train.setName(String.valueOf(editTrainName.getText()));
                        train.setDay_id(dayOfTheWeek.getSelectedItemPosition() + 1);
                        if (!finalFlag) {
                            HelperFactory.getHelper().getTrainDAO().update(train);
                        } else {
                            HelperFactory.getHelper().getTrainDAO().create(train);
                        }
                        AppContextProgram.tempTrain = train;
                        startActivityForResult(intent, 1);
                    }
                } catch (SQLException e) {
                }
            }
        });
        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        AppContextProgram.tempTrainList.clear();
        try {
            AppContextProgram.tempTrainList.addAll(HelperFactory.getHelper().getTrainDAO().getAllTrains());
        } catch (SQLException e) {
        }
        AppContextProgram.expListAdapterTemp.notifyDataSetChanged();
    }
}
