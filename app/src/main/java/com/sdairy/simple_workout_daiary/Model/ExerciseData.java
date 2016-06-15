package com.sdairy.simple_workout_daiary.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Sergey_Dresvyanin on 21.05.2016.
 */
@DatabaseTable(tableName = "exercise_data")
public class ExerciseData implements Serializable {

    @DatabaseField(generatedId = true, columnName = "_id")
    private int id;

    @DatabaseField(foreign = true, canBeNull = false,
            foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "name_id")
    private Exercise name_id;

    @DatabaseField(columnName = "weight")
    private double weight;

    @DatabaseField(columnName = "reps")
    private int reps;

    @DatabaseField(columnName = "rest_time")
    private long restTime;

    @DatabaseField(columnName = "data_exec")
    private long dataExec;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Exercise getName_id() {
        return name_id;
    }

    public void setName_id(Exercise name_id) {
        this.name_id = name_id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public long getRestTime() {
        return restTime;
    }

    public void setRestTime(long restTime) {
        this.restTime = restTime;
    }

    public long getDataExec() {
        return dataExec;
    }

    public void setDataExec(long dataExec) {
        this.dataExec = dataExec;
    }

    public final boolean hasId() {
        return 0 != this.id;
    }



}
