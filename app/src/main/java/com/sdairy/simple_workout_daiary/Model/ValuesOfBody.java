package com.sdairy.simple_workout_daiary.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Sergey_Dresvyanin on 10.06.2016.
 */
@DatabaseTable(tableName = "values_of_body")
public class ValuesOfBody implements Serializable {

    @DatabaseField(generatedId = true, columnName = "_id")
    private int id;

    @DatabaseField(canBeNull = true, columnName = "data_ex")
    private long dataex;
    @DatabaseField(canBeNull = true, columnName = "biceps")
    private float biceps;
    @DatabaseField(canBeNull = true, columnName = "chest")
    private float chest;
    @DatabaseField(canBeNull = true, columnName = "shoulder")
    private float shoulder;
    @DatabaseField(canBeNull = true, columnName = "leg")
    private float leg;
    @DatabaseField(canBeNull = true, columnName = "ikri")
    private float ikri;
    @DatabaseField(canBeNull = true, columnName = "talia")
    private float talia;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDataex() {
        return dataex;
    }

    public void setDataex(long dataex) {
        this.dataex = dataex;
    }

    public float getBiceps() {
        return biceps;
    }

    public void setBiceps(float biceps) {
        this.biceps = biceps;
    }

    public float getChest() {
        return chest;
    }

    public void setChest(float chest) {
        this.chest = chest;
    }

    public float getShoulder() {
        return shoulder;
    }

    public void setShoulder(float shoulder) {
        this.shoulder = shoulder;
    }

    public float getLeg() {
        return leg;
    }

    public void setLeg(float leg) {
        this.leg = leg;
    }

    public float getIkri() {
        return ikri;
    }

    public void setIkri(float ikri) {
        this.ikri = ikri;
    }

    public float getTalia() {
        return talia;
    }

    public void setTalia(float talia) {
        this.talia = talia;
    }
}
