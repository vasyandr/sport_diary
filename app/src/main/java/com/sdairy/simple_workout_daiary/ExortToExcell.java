package com.sdairy.simple_workout_daiary;

import android.os.Environment;

import com.sdairy.simple_workout_daiary.Model.ExerciseData;
import com.sdairy.simple_workout_daiary.database.HelperFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sergey_Dresvyanin on 09.06.2016.
 */
public class ExortToExcell {

    public static void export() throws SQLException {
        try {

            String path = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)) + "/data.csv";

            List<ExerciseData> s = new ArrayList<>();
            s.addAll(HelperFactory.getHelper().getExerciseDataDAO().queryForAll());
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            SimpleDateFormat format1 = new SimpleDateFormat("mm:ss");
            if (s.size() != 0) {
                BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "windows-1251"));
                StringBuilder sb = new StringBuilder();

                sb.append("Название;" + "\t");
                sb.append(String.valueOf("Вес;") + "\t");
                sb.append(String.valueOf("Повторений;") + "\t");
                sb.append(String.valueOf("Отдых;") + "\t");
                sb.append("Дата;" + "\t");
                sb.append("\n");

                for (ExerciseData x : s) {
                    sb.append(x.getName_id().getName() + ";\t");
                    sb.append(String.valueOf(x.getWeight()) + ";\t");
                    sb.append(String.valueOf(x.getReps()) + ";\t");
                    sb.append(format1.format(new Date(x.getRestTime())) + ";\t");
                    sb.append(format.format(new Date(x.getDataExec())) + ";\t");
                    sb.append("\n");
                }

                br.write(sb.toString());
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteExternalStoragePublicPicture() {
        // Create a path where we will place our picture in the user's
        // public pictures directory and delete the file.  If external
        // storage is not currently mounted this will fail.
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS);
        File file = new File(path, "data.csv");
        file.delete();
    }

    public static boolean hasExternalStoragePublicPicture() {
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS);
        File file = new File(path, "data.csv");
        return file.exists();
    }

}
