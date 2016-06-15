package com.sdairy.simple_workout_daiary.DAO;

import android.database.SQLException;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.sdairy.simple_workout_daiary.Model.Exercise;
import com.sdairy.simple_workout_daiary.Model.ExerciseData;

import java.util.List;

/**
 * Created by Sergey_Dresvyanin on 29.05.2016.
 */
public class ExerciseDataDAO extends BaseDaoImpl<ExerciseData, Integer> {
    public ExerciseDataDAO(ConnectionSource connectionSource,
                           Class<ExerciseData> dataClass) throws SQLException, java.sql.SQLException {
        super(connectionSource, dataClass);
    }

    public List<ExerciseData> getPreviuosExerciseData(Exercise exercise) throws java.sql.SQLException {
        QueryBuilder<ExerciseData, Integer> queryBuilder = queryBuilder();
        Where where = queryBuilder.where();

        where.eq("name_id", exercise.getId());
        /*where.and();
        where.eq("data_exec", Calendar.getInstance().getTimeInMillis() - 384521715);
        */
        PreparedQuery<ExerciseData> preparedQuery = queryBuilder.prepare();
        List<ExerciseData> exerciseList = query(preparedQuery);
        return exerciseList;
    }

    public List<ExerciseData> getAllDataAboutExercise(Exercise exercise) throws java.sql.SQLException {
        QueryBuilder<ExerciseData, Integer> queryBuilder = queryBuilder();
        Where where = queryBuilder.where();
        where.eq("name_id", exercise.getId());
        PreparedQuery<ExerciseData> preparedQuery = queryBuilder.prepare();
        List<ExerciseData> exerciseList = query(preparedQuery);
        return exerciseList;
    }

    public List<ExerciseData> getDataByIntervalAboutExercise(Exercise exercise, long start, long end) throws java.sql.SQLException {

        QueryBuilder<ExerciseData, Integer> queryBuilder = queryBuilder();

        Where where = queryBuilder.where();

        where.between("data_exec", start, end);
        where.and();
        where.eq("name_id", exercise.getId());

        PreparedQuery<ExerciseData> preparedQuery = queryBuilder.prepare();

        List<ExerciseData> exerciseList = query(preparedQuery);

        return exerciseList;

    }

    public List<ExerciseData> getAllData() throws java.sql.SQLException {
        return this.queryForAll();
    }

    public void deleteAlldataAboutEx(Exercise exercise) throws java.sql.SQLException {
        DeleteBuilder<ExerciseData, Integer> deleteBuilder = deleteBuilder();
        deleteBuilder.where().eq("name_id", exercise.getId());
        deleteBuilder.delete();
    }


}
