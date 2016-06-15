package com.sdairy.simple_workout_daiary.Model;

import android.text.Editable;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Sergey_Dresvyanin on 21.05.2016.
 */

@DatabaseTable(tableName = "exercise_prog")
public class Exercise implements Serializable {

    @DatabaseField(generatedId = true, columnName = "_id")
    private int id;


    @DatabaseField(foreign = true, canBeNull = false,
            foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "tr_id")
    private Train trainid;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "sets")
    private int sets;

    @ForeignCollectionField
    private ForeignCollection<ExerciseData> exerciseDatas;

    public Exercise() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Train getTrainid() {
        return trainid;
    }

    public void setTrainid(Train trainid) {
        this.trainid = trainid;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public final boolean hasId() {
        return 0 != this.id;
    }


}
