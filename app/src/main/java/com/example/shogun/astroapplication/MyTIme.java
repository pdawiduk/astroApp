package com.example.shogun.astroapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;


import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import java.util.Calendar;

/**
 * Created by Shogun on 2016-05-30.
 */
public class MyTIme {

    static Calendar calendar = Calendar.getInstance();

    public static String getTime() {
         LocalDateTime now = LocalDateTime.now();
        return String.valueOf(now.getHourOfDay()+2) + " : " + String.valueOf(now.getMinuteOfHour()) + ":" + String.valueOf(now.getSecondOfMinute());
    }





}
