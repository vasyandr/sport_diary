package com.sdairy.simple_workout_daiary.Model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Sergey_Dresvyanin on 21.05.2016.
 */
@DatabaseTable(tableName = "trains")
public class Train implements Serializable {

    @DatabaseField(generatedId = true, columnName = "_id")
    private int id;

    @DatabaseField(columnName = "day_id")
    private int day_id;

    @DatabaseField(canBeNull = true, columnName = "name")
    private String name;

    @ForeignCollectionField
    private ForeignCollection<Exercise> exercises;

    public Train() {}

    public ForeignCollection<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(ForeignCollection<Exercise> exercises) {
        this.exercises = exercises;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDay_id() {
        return day_id;
    }

    public void setDay_id(int day_id) {
        this.day_id = day_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public final boolean hasId() {
        return 0 != this.id;
    }
}
