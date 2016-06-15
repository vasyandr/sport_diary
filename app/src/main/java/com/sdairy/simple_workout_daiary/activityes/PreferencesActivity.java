package com.sdairy.simple_workout_daiary.activityes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.sdairy.simple_workout_daiary.AppContextProgram;
import com.sdairy.simple_workout_daiary.R;


/**
 * Created by Sergey_Dresvyanin on 10.06.2016.
 */
public class PreferencesActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();


        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean strUserName = SP.getBoolean("vibroflag", false);
        AppContextProgram.NOTIFICATE_BY_VIBRO = strUserName;
        boolean bAppUpdates = SP.getBoolean("soundflag", false);
        AppContextProgram.planTime = (long) (Float.parseFloat(SP.getString("time_value", String.valueOf(3))) * 60000);
        System.out.println(  AppContextProgram.planTime );
        AppContextProgram.NOTIFICATE_BY_SOUND = bAppUpdates;
    }

    public static class MyPreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

        }
    }

}
