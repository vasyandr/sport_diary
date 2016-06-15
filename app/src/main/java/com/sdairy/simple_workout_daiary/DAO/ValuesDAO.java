package com.sdairy.simple_workout_daiary.DAO;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.sdairy.simple_workout_daiary.Model.Train;
import com.sdairy.simple_workout_daiary.Model.ValuesOfBody;

import java.util.List;

/**
 * Created by Sergey_Dresvyanin on 10.06.2016.
 */
public class ValuesDAO extends BaseDaoImpl<ValuesOfBody, Integer> {

    public ValuesDAO(ConnectionSource connectionSource, Class<ValuesOfBody> dataClass) throws java.sql.SQLException {
        super(connectionSource, dataClass);
    }


    public List<ValuesOfBody> getAll() throws java.sql.SQLException {
      return queryForAll();
    }

}
