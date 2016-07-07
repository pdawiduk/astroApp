package com.example.shogun.astroapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;


import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalDateTime;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class SunFragment extends Fragment {

    private static final String LOG_TAG = "SunFRAGMENT";
    private Timer autoUpdate;
    private static ScrollView scrollView;
    private TextView clock;
    private static SharedPreferences preferences;


    private static TextView rise;
    private static TextView sunset;
    private static TextView zmierzchRano;
    private static TextView zmierzchWieczor;
    private static final int valueOfMinute = 60 * 1000;
    private static TextView azymutRano;
    private static TextView azymutWieczor;

    private static double longitude;
    private static double latitude;
    private static Context context;

    private static LocalDateTime calendar = MyTIme.getDate();
    private static AstroCalculator.Location location;
    private static AstroDateTime astroDateTime;
    private static AstroCalculator astroCalculator;
    private Handler handler;
    private static AstroCalculator.SunInfo sunInfo ;

    private static String hourOfRise() {
        int hour = sunInfo.getSunrise().getHour();
        int minute = sunInfo.getSunrise().getMinute();
        return String.valueOf(hour) + " : " + String.valueOf(minute);
    }

    private static String hourOfSunset() {
        int hour = sunInfo.getSunset().getHour();
        int minute = sunInfo.getSunset().getMinute();
        return String.valueOf(hour) + " : " + String.valueOf(minute);
    }

    private static String hourOfZmierzch() {
        if ((sunInfo.getSunrise().getHour() < MyTIme.calendar.get(Calendar.HOUR)) | (sunInfo.getSunset().getHour() > MyTIme.calendar.get(Calendar.HOUR))) {


            int godzina = sunInfo.getTwilightMorning().getHour();
            int minuta = sunInfo.getTwilightMorning().getMinute();
            return String.valueOf(godzina) + " : " + String.valueOf(minuta);
        } else {
            int godzina = sunInfo.getTwilightEvening().getHour();
            int minuta = sunInfo.getTwilightEvening().getMinute();
            return String.valueOf(godzina) + " : " + String.valueOf(minuta);
        }
    }


    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }

    private OnFragmentInteractionListener mListener;

    public SunFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();

      //  Toast.makeText(getContext(),Calendar.getInstance().getTimeZone().toString(),Toast.LENGTH_LONG).show();
    }

    public  static void update() {

        calendar = MyTIme.getDate();
        astroDateTime = new AstroDateTime(
                calendar.getYear(),
                calendar.getMonthOfYear(),
                calendar.getDayOfMonth(),
                calendar.getHourOfDay(),
                calendar.getMinuteOfHour(),
                calendar.getSecondOfMinute(),
                1,
                true
        );

        longitude = Utility.longitudeDouble(preferences, context);
        latitude = Utility.latitudeDouble(preferences, context);
        location = new AstroCalculator.Location(latitude, longitude);
        astroCalculator = new AstroCalculator(astroDateTime, location);
        sunInfo= astroCalculator.getSunInfo();

        rise.setText(hourOfRise());
        sunset.setText(hourOfSunset());
        zmierzchRano.setText(sunInfo.getTwilightMorning().toString());
        zmierzchWieczor.setText(sunInfo.getTwilightEvening().toString());
        azymutRano.setText(String.valueOf(sunInfo.getAzimuthRise()));
        azymutWieczor.setText(String.valueOf(sunInfo.getAzimuthSet()));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    public static SunFragment newInstance(String param1) {
        SunFragment fragment = new SunFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);

        astroDateTime = new AstroDateTime(
                calendar.getYear(),
                calendar.getMonthOfYear(),
                calendar.getDayOfMonth(),
                calendar.getHourOfDay(),
                calendar.getMinuteOfHour(),
                calendar.getSecondOfMinute(),
                2,
                true
        );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        astroDateTime = new AstroDateTime(
                calendar.getYear(),
                calendar.getMonthOfYear(),
                calendar.getDayOfMonth(),
                calendar.getHourOfDay(),
                calendar.getMinuteOfHour(),
                calendar.getSecondOfMinute(),
                2,
                true
        );

        longitude = Utility.longitudeDouble(preferences, getContext());
        latitude = Utility.latitudeDouble(preferences, getContext());
        location = new AstroCalculator.Location(latitude, longitude);
        astroCalculator = new AstroCalculator(astroDateTime, location);
        sunInfo = astroCalculator.getSunInfo();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sun, container, false);
        if (v == null) {
            v = inflater.inflate(R.layout.fragment_sun, container, false);
        }

        clock = (TextView) v.findViewById(R.id.clock);
        rise = (TextView) v.findViewById(R.id.riseText);
        sunset = (TextView) v.findViewById(R.id.sunsetText);
        zmierzchRano = (TextView) v.findViewById(R.id.twilightText);
        zmierzchWieczor= (TextView) v.findViewById(R.id.twilightnightText);
        azymutRano = (TextView) v.findViewById(R.id.asimutRise);
        azymutWieczor = (TextView) v.findViewById(R.id.asimutDown);


        update();

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        handler = new Handler();

        handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    update();

                                }
                            },
                Utility.getRefreshPeriodTime(preferences, getContext()) * valueOfMinute
        );
            if(getActivity()!=null) {
                autoUpdate = new Timer();
                autoUpdate.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        if(getActivity() == null) return;
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                clock.setText(MyTIme.getTime());

                            }
                        });
                    }
                }, 0, 1000);
            }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        update();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        autoUpdate.cancel();
    }
}
