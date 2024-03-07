package com.example.iatimetableapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.preference.PreferenceManager;

import java.lang.reflect.Array;
import java.time.temporal.TemporalField;
import java.util.*;
import java.time.*;

public class timeHandlerActivity {

    public static final String tag = "timeHandlerActivity";

    int currentDay;
    int previousDate;

    public timeHandlerActivity() {
    }

    public String getCurrentTime(){
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.toString();
    }

    public int getDay(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return preferences.getInt("current day", 1);
    }

    public int updateDay(Context context){
        Calendar calendar = Calendar.getInstance();
        int currentDate = calendar.get(Calendar.DAY_OF_YEAR);

        LocalDateTime currentTime = LocalDateTime.now();
        int dayOfWeek = currentTime.getDayOfWeek().getValue();

        Log.i(tag, "currentDate = " + currentDate);
        Log.i(tag, "dayOfWeek = " + dayOfWeek);

        //Sets up SharedPreferences for updating.
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor edit = preferences.edit();

        currentDay = preferences.getInt("current day", 1);
        previousDate = preferences.getInt("previous date", 1);

        //determines how many days to increment by
        if(currentDate != previousDate){
            int diff = currentDate - previousDate;

            Log.i(tag, "stored previousDate is " + previousDate + ", current is " + currentDate);
            Log.i(tag, "diff is " + diff);

            //returns dayOfWeek to origin from which to count
            for (int i = 0; i < diff; i++) {
                dayOfWeek--;
                if(dayOfWeek==0) dayOfWeek = 7;
            }

            //increments currentDay for all mondays to fridays.
            for (int i = 0; i < diff; i++) {
                if(dayOfWeek <= 5) currentDay++;
                dayOfWeek++;
                if(dayOfWeek > 7) dayOfWeek = 1;

                if(currentDay > 7) currentDay = 1;
            }

            Log.i(tag, "currentDay now " + currentDay);

            //Updates previousdate in preferences to last run time, i.e. now.
            edit.putInt("previous date", currentDate);
            edit.apply();
        }

        //pushes new value to SharedPreference
        edit.putInt("current day", currentDay);
        edit.apply();

        return currentDay;
    }
}
