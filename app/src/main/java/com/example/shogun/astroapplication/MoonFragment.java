package com.example.shogun.astroapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import org.joda.time.LocalDateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MoonFragment extends Fragment {

    private static AstroCalculator.MoonInfo moonInfo ;
    private static SharedPreferences preferences;

    private static Calendar calendar = Calendar.getInstance();
    private static AstroCalculator.Location location;
    private static AstroDateTime astroDateTime;
    private static AstroCalculator astroCalculator;

    private static double longitude;
    private static double latitude;
    private static Context context;
    private static final int valueOfMinute = 60 * 1000;

    private static LocalDateTime date = MyTIme.getDate();

    static TextView wschod ;
    static TextView zachod ;
    static TextView faza;

    static TextView pelnia;
    static TextView synod;
    private Timer autoUpdateForData;

    public static void update() {
        calendar = Calendar.getInstance();
        astroDateTime = new AstroDateTime(
                date.getYear(),
                date.getMonthOfYear(),
                date.getDayOfMonth(),
                date.getHourOfDay(),
                date.getMinuteOfHour(),
                date.getSecondOfMinute(),
                2,
                true
        );

        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        longitude= Utility.longitudeDouble(preferences,context);
        latitude = Utility.latitudeDouble(preferences, context);
        location = new AstroCalculator.Location(latitude, longitude);
        astroCalculator = new AstroCalculator(astroDateTime, location);

        moonInfo = astroCalculator.getMoonInfo();

        wschod.setText(moonInfo.getMoonrise().toString());
        zachod.setText(moonInfo.getMoonset().toString());
        faza.setText(String.valueOf(moonInfo.getIllumination()));
        synod.setText(String.valueOf(moonInfo.getAge()));
        pelnia.setText(moonInfo.getNextFullMoon().toString());


    }


    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }

    private OnFragmentInteractionListener mListener;

    public MoonFragment() {

    }


    public static MoonFragment newInstance(String param1) {
        MoonFragment fragment = new MoonFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        context = getContext();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moon, container, false);
        wschod = (TextView) view.findViewById(R.id.wschod);
        zachod = (TextView) view.findViewById(R.id.zachod);
        faza = (TextView) view.findViewById(R.id.faza);
        synod = (TextView) view.findViewById(R.id.synod);
        pelnia = (TextView) view.findViewById(R.id.pelnia);

        update();
        return view;
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
    autoUpdateForData= new Timer();
        autoUpdateForData.schedule(new TimerTask() {
            @Override
            public void run() {
                if(getActivity()!=null){
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        update();
                    }
                });}
            }
        },  Utility.getRefreshPeriodTime(preferences,getContext())*valueOfMinute);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        autoUpdateForData.cancel();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
