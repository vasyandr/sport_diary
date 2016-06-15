package com.sdairy.simple_workout_daiary.activityes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.sdairy.simple_workout_daiary.AppContextProgram;
import com.sdairy.simple_workout_daiary.Model.Exercise;
import com.sdairy.simple_workout_daiary.Model.Train;
import com.sdairy.simple_workout_daiary.R;
import com.sdairy.simple_workout_daiary.database.HelperFactory;

import java.sql.SQLException;

/**
 * Created by Sergey_Dresvyanin on 04.06.2016.
 */
public class ExerciseEditorFragment extends DialogFragment {

    private EditText editTrainName;
    private EditText editTrainSets;
    private Context context;
    private Exercise exercise;
    private Train tr;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        context = getActivity().getBaseContext();
        boolean flag = true;

        exercise = (Exercise) getArguments().get("exercise");
        tr = AppContextProgram.tempTrain;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        builder.setInverseBackgroundForced(false);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setTitle(R.string.exerciseEditor);
        View view = inflater.inflate(R.layout.edit_exercise, null);
        builder.setView(view);

        editTrainName = (EditText) view.findViewById(R.id.exercise_name);
        editTrainSets = (EditText) view.findViewById(R.id.sets_count_item);
        if (exercise != null) {
            flag = false;
            editTrainName.setText(exercise.getName());
        } else {
            exercise = new Exercise();
            exercise.setTrainid(tr);
        }

        final boolean finalFlag = flag;
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (!editTrainName.getText().toString().trim().isEmpty()) {
                        try {
                            exercise.setName(String.valueOf(editTrainName.getText()));
                        } catch (Exception f) {
                        }
                        try {
                            exercise.setSets(Integer.parseInt(String.valueOf(editTrainSets.getText())));
                        } catch (NumberFormatException f) {
                            exercise.setSets(0);
                        }
                        if (!finalFlag) {
                            HelperFactory.getHelper().getExerciseDAO().update(exercise);
                        } else {
                            HelperFactory.getHelper().getExerciseDAO().create(exercise);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        AppContextProgram.exerciseTempList.clear();
        try {
            AppContextProgram.exerciseTempList.addAll(HelperFactory.getHelper().getExerciseDAO().getExercises(AppContextProgram.tempTrain));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        AppContextProgram.exerciseListEditorTemp.notifyDataSetChanged();
        super.onDismiss(dialog);
    }
}
