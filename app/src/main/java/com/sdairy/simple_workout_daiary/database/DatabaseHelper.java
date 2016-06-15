package com.sdairy.simple_workout_daiary.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.sdairy.simple_workout_daiary.Model.Exercise;
import com.sdairy.simple_workout_daiary.Model.ExerciseData;
import com.sdairy.simple_workout_daiary.Model.Train;
import com.sdairy.simple_workout_daiary.DAO.*;
import com.sdairy.simple_workout_daiary.Model.ValuesOfBody;

import java.io.IOException;

/**
 * Created by Sergey_Dresvyanin on 29.05.2016.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    //имя файла базы данных который будет храниться в /data/data/APPNAME/DATABASE_NAME.db
    private static final String DATABASE_NAME = "sport_data.db";

    //с каждым увеличением версии, при нахождении в устройстве БД с предыдущей версией будет выполнен метод onUpgrade();
    private static final int DATABASE_VERSION = 2;

    //ссылки на DAO соответсвующие сущностям, хранимым в БД
    private ExerciseDAO exerciseDAO = null;
    private ExerciseDataDAO exerciseDataDAO = null;
    private TrainDAO trainDAO = null;
    private ValuesDAO valuessDAO=null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        DatabaseInitializer initializer = new DatabaseInitializer(context);
        try {
            initializer.createDatabase();
            initializer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");

            TableUtils.createTable(connectionSource, Train.class);
            TableUtils.createTable(connectionSource, ExerciseData.class);
            TableUtils.createTable(connectionSource, Exercise.class);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");

            TableUtils.dropTable(connectionSource, Train.class, true);
            TableUtils.dropTable(connectionSource, Exercise.class, true);
            TableUtils.dropTable(connectionSource, ExerciseData.class, true);
            onCreate(db);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public ExerciseDAO getExerciseDAO() throws java.sql.SQLException {
        if (exerciseDAO == null) {
            exerciseDAO = new ExerciseDAO(getConnectionSource(), Exercise.class);
        }
        return exerciseDAO;
    }

    public ExerciseDataDAO getExerciseDataDAO() throws java.sql.SQLException {
        if (exerciseDataDAO == null) {
            exerciseDataDAO = new ExerciseDataDAO(getConnectionSource(), ExerciseData.class);
        }
        return exerciseDataDAO;
    }

    public TrainDAO getTrainDAO() throws java.sql.SQLException {
        if (trainDAO == null) {
            trainDAO = new TrainDAO(getConnectionSource(), Train.class);
        }
        return trainDAO;
    }
    public ValuesDAO getValuesDAO() throws java.sql.SQLException {
        if (valuessDAO == null) {
            valuessDAO = new ValuesDAO(getConnectionSource(), ValuesOfBody.class);
        }
        return valuessDAO;
    }

    @Override
    public void close() {
        super.close();
        exerciseDAO = null;
        trainDAO = null;
        exerciseDataDAO = null;
        valuessDAO = null;
    }
}