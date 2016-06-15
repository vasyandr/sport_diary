package com.sdairy.simple_workout_daiary.activityes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.sdairy.simple_workout_daiary.AppContextProgram;
import com.sdairy.simple_workout_daiary.Model.ValuesOfBody;
import com.sdairy.simple_workout_daiary.R;
import com.sdairy.simple_workout_daiary.database.HelperFactory;

import java.sql.SQLException;
import java.util.Calendar;

public class ValuesBodyEditor extends AppCompatActivity {

    private ValuesOfBody valuesOfBody;

    private EditText biceps;
    private EditText cahest;
    private EditText ikri;
    private EditText leg;
    private EditText shoulder;
    private EditText talia;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_values_body_editor);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getResources().getString(R.string.bodyval));
        valuesOfBody = AppContextProgram.tempValuesOfBody;
        biceps = (EditText) findViewById(R.id.biceps_ed);
        cahest = (EditText) findViewById(R.id.chest_ed);
        ikri = (EditText) findViewById(R.id.ikri_ed);
        leg = (EditText) findViewById(R.id.leg_ed);
        shoulder = (EditText) findViewById(R.id.shoulder_ed);
        talia = (EditText) findViewById(R.id.talia_ed);

        if (valuesOfBody != null) {
            try {
                String bics = String.format("%.1f", valuesOfBody.getBiceps());
                String chests = String.format("%.1f", valuesOfBody.getChest());
                String shoulders = String.format("%.1f", valuesOfBody.getShoulder());
                String legs = String.format("%.1f", valuesOfBody.getLeg());
                String ikris = String.format("%.1f", valuesOfBody.getIkri());
                String talias = String.format("%.1f", valuesOfBody.getTalia());

                biceps.setText(bics);
                cahest.setText(chests);
                ikri.setText(ikris);
                leg.setText(legs);
                shoulder.setText(shoulders);
                talia.setText(talias);
            } catch (Exception e) {

            }

        } else {
            flag = true;
            valuesOfBody = new ValuesOfBody();
            valuesOfBody.setDataex(Calendar.getInstance().getTimeInMillis());
        }
    }

    public void saveValue(View view) {
        try {
            save();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }

    private void save() throws SQLException {
        try {
            valuesOfBody.setBiceps(Float.parseFloat(String.valueOf(biceps.getText())));
        } catch (Exception e) {
        }
        try {
            valuesOfBody.setChest(Float.parseFloat(String.valueOf(cahest.getText())));

        } catch (Exception f) {
        }
        try {
            valuesOfBody.setLeg(Float.parseFloat(String.valueOf(leg.getText())));

        } catch (Exception f) {
        }
        try {
            valuesOfBody.setTalia(Float.parseFloat(String.valueOf(talia.getText())));

        } catch (Exception f) {
        }
        try {
            valuesOfBody.setIkri(Float.parseFloat(String.valueOf(ikri.getText())));

        } catch (Exception f) {
        }
        try {
            valuesOfBody.setShoulder(Float.parseFloat(String.valueOf(shoulder.getText())));

        } catch (Exception f) {
        }

        if (!flag) {
            HelperFactory.getHelper().getValuesDAO().update(valuesOfBody);
        } else {
            HelperFactory.getHelper().getValuesDAO().create(valuesOfBody);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                try {
                    save();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.close: {
                onBackPressed();
                try {
                    save();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }

        }

        return super.onOptionsItemSelected(item);
    }
}
