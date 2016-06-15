package com.sdairy.simple_workout_daiary.DAO;

import android.database.SQLException;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.sdairy.simple_workout_daiary.Model.Exercise;
import com.sdairy.simple_workout_daiary.Model.Train;
import com.sdairy.simple_workout_daiary.database.HelperFactory;

import java.util.Collection;
import java.util.List;

/**
 * Created by Sergey_Dresvyanin on 29.05.2016.
 */
public class ExerciseDAO extends BaseDaoImpl<Exercise, Integer> {
    public ExerciseDAO(ConnectionSource connectionSource, Class<Exercise> dataClass) throws SQLException, java.sql.SQLException {
        super(connectionSource, dataClass);
    }

    public List<Exercise> getExercises(Train train) throws java.sql.SQLException {
        QueryBuilder<Exercise, Integer> queryBuilder = queryBuilder();
        queryBuilder.where().eq("tr_id", train.getId());
        PreparedQuery<Exercise> preparedQuery = queryBuilder.prepare();
        List<Exercise> exerciseList = query(preparedQuery);
        return exerciseList;
    }

    @Override
    public int delete(Exercise data) throws java.sql.SQLException {
        return super.delete(data);
    }

    @Override
    public int delete(Collection<Exercise> datas) throws java.sql.SQLException {
        for (Exercise x : datas)
        {
            HelperFactory.getHelper().getExerciseDataDAO().deleteAlldataAboutEx(x);
        }
        return super.delete(datas);
    }
}
