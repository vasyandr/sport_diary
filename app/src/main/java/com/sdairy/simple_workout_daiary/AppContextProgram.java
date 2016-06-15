package com.sdairy.simple_workout_daiary;

import android.app.Application;
import android.content.Context;

import com.sdairy.simple_workout_daiary.Model.Exercise;
import com.sdairy.simple_workout_daiary.Model.Train;
import com.sdairy.simple_workout_daiary.Model.ValuesOfBody;
import com.sdairy.simple_workout_daiary.database.HelperFactory;
import com.sdairy.simple_workout_daiary.lists.ExerciseListEditor;
import com.sdairy.simple_workout_daiary.lists.ExpListAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Sergey on 26.04.2016.
 */
public class AppContextProgram extends Application {

    private static AppContextProgram context;
    public static boolean NOTIFICATE_BY_SOUND = false;
    public static boolean NOTIFICATE_BY_VIBRO = false;

    public static Train tempTrain;
    public static Exercise tempExercise;
    public static List<Exercise> exerciseTempList = new ArrayList<>();
    public static List<Train> tempTrainList = new ArrayList<>();
    public static ExerciseListEditor exerciseListEditorTemp;
    public static ExpListAdapter expListAdapterTemp;

    public static ValuesOfBody tempValuesOfBody;
    public static long planTime = 180000;

    public static Context getInstance() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        HelperFactory.setHelper(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        HelperFactory.releaseHelper();
        super.onTerminate();
    }


}
