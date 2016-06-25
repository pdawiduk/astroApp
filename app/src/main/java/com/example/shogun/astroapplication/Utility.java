package com.example.shogun.astroapplication;

import android.content.Context;
import android.content.SharedPreferences;

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
}
