package com.sdairy.simple_workout_daiary.DAO;

import android.database.SQLException;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.sdairy.simple_workout_daiary.Model.Train;
import com.sdairy.simple_workout_daiary.database.HelperFactory;

import java.util.List;

/**
 * Created by Sergey_Dresvyanin on 29.05.2016.
 */
public class TrainDAO extends BaseDaoImpl<Train, Integer> {

    public TrainDAO(ConnectionSource connectionSource,
                    Class<Train> dataClass) throws SQLException, java.sql.SQLException {
        super(connectionSource, dataClass);
    }


    public List<Train> getAllTrains() throws SQLException, java.sql.SQLException {
        return this.queryForAll();
    }

    public List<Train> getTrainDay(int dayid) throws SQLException, java.sql.SQLException {
        QueryBuilder<Train, Integer> queryBuilder = queryBuilder();
        queryBuilder.where().eq("day_id", dayid);
        PreparedQuery<Train> preparedQuery = queryBuilder.prepare();
        List<Train> train = query(preparedQuery);
        return train;
    }

    public List<Train> getTrainByID(int id) throws SQLException, java.sql.SQLException {
        QueryBuilder<Train, Integer> queryBuilder = queryBuilder();
        queryBuilder.where().eq("_id", id);
        PreparedQuery<Train> preparedQuery = queryBuilder.prepare();
        List<Train> train = query(preparedQuery);
        return train;
    }

    @Override
    public int delete(Train data) throws java.sql.SQLException {
        HelperFactory.getHelper().getExerciseDAO().delete(data.getExercises());
        return super.delete(data);

    }
}
