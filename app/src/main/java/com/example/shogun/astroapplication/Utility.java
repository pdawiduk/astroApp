package com.example.shogun.astroapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.concurrent.TimeUnit;

/**
 * Created by Shogun on 2016-05-30.
 */
public class Utility {
    static double latitudeDouble(SharedPreferences sharedPreferences, Context context) {
        return Double.valueOf(sharedPreferences.getString(context.getString(R.string.latitude_key), context.getString(R.string.default_latitude)));
    }

    static double longitudeDouble(SharedPreferences sharedPreferences,Context context) {
        return Double.valueOf(sharedPreferences.getString(context.getString(R.string.longitude_key), context.getString(R.string.pref_logitude)));
    }

    static int getRefreshPeriodTime(SharedPreferences sharedPreferences,Context context){
        return Integer.valueOf(sharedPreferences.getString("time","15"));
    }

    static String getCity(SharedPreferences sharedPreferences){
        return sharedPreferences.getString("city","lodz");
    }

    public static int getCurrentTimeZoneOffset() {
        DateTimeZone tz = DateTimeZone.getDefault();
        Long instant = DateTime.now().getMillis();

        long offsetInMilliseconds = tz.getOffset(instant);
        long hours = TimeUnit.MILLISECONDS.toHours( offsetInMilliseconds );
        return (int) hours;
    }

    public static String getPrefferedLocation(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return null;
    }



}
