package com.example.shogun.astroapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;



import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class SunFragment extends Fragment {

    private static final String LOG_TAG= "SunFRAGMENT";
    private Timer autoUpdate;
    private  TextView clock;
    private SharedPreferences preferences;

    private static TextView rise;
    private static TextView sunset;
    private static TextView zmierzch;

    double longitude;
    double latitude;

    private AstroCalculator.Location location;
    private AstroDateTime astroDateTime;
    private AstroCalculator astroCalculator;
    private Handler handler = new Handler();




    private String hourOfRise() {
        int godzina = sunInfo.getSunrise().getHour();
        int minuta = sunInfo.getSunrise().getMinute();
        return String.valueOf(godzina) + " : " + String.valueOf(minuta);
    }

    private String hourOfSunset() {
        int godzina = sunInfo.getSunset().getHour();
        int minuta = sunInfo.getSunset().getMinute();
        return String.valueOf(godzina) + " : " + String.valueOf(minuta);
    }

    private String hourOfZmierzch() {
        if ((sunInfo.getSunrise().getHour() > MyTIme.calendar.get(Calendar.HOUR)) | (sunInfo.getSunset().getHour() > MyTIme.calendar.get(Calendar.HOUR))) {


            int godzina = sunInfo.getTwilightMorning().getHour();
            int minuta = sunInfo.getTwilightMorning().getMinute();
            return String.valueOf(godzina) + " : " + String.valueOf(minuta);
        } else {
            int godzina = sunInfo.getTwilightEvening().getHour();
            int minuta = sunInfo.getTwilightEvening().getMinute();
            return String.valueOf(godzina) + " : " + String.valueOf(minuta);
        }
    }

    Calendar calendar = Calendar.getInstance();

    private AstroCalculator.SunInfo sunInfo ;

    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }

    // TODO: Rename parameter arguments, choose names that match

    private static final String ARG_PARAM1 = "param1";

    private String mParam1;


    private OnFragmentInteractionListener mListener;

    public SunFragment() {
        // Required empty public constructor
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    public static SunFragment newInstance(String param1) {
        SunFragment fragment = new SunFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }


        astroDateTime = new AstroDateTime(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND),
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
            v = inflater.inflate(R.layout.fragment_sun_land, container, false);
        }

        clock = (TextView) v.findViewById(R.id.clock);
        rise = (TextView) v.findViewById(R.id.riseText);
        sunset = (TextView) v.findViewById(R.id.sunsetText);
        zmierzch = (TextView) v.findViewById(R.id.twilightText);

        rise.setText(hourOfRise());
        sunset.setText(hourOfSunset());
        zmierzch.setText(hourOfZmierzch());
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




//clock.setText(MyTIme.getTime());

        autoUpdate = new Timer();
        autoUpdate.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        clock.setText(MyTIme.getTime());
                        Log.i(LOG_TAG,MyTIme.getTime());
                    }
                });
            }
        }, 0, 1000); // updates each 40 secs


    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
