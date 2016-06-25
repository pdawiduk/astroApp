package com.example.shogun.astroapplication;

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

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import java.util.Calendar;

public class MoonFragment extends Fragment {

    private static AstroCalculator.MoonInfo moonInfo ;
    private SharedPreferences preferences;

    private static Calendar calendar = Calendar.getInstance();
    private AstroCalculator.Location location;
    private static AstroDateTime astroDateTime;
    private static AstroCalculator astroCalculator;

    private static double longitude;
    private static double latitude;

    TextView wschod ;
    TextView zachod ;
    TextView faza;

    TextView pelnia;
    TextView synod;

    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MoonFragment() {
        // Required empty public constructor
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

        astroDateTime = new AstroDateTime(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND),
                2,
                true
        );
     //   Log.d(MoonFragment.class.getSimpleName())
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        longitude= Utility.longitudeDouble(preferences,getContext());
        latitude = Utility.latitudeDouble(preferences, getContext());
        location = new AstroCalculator.Location(latitude, longitude);
        astroCalculator = new AstroCalculator(astroDateTime, location);

        moonInfo = astroCalculator.getMoonInfo();


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
        wschod.setText(moonInfo.getMoonrise().toString());
        zachod.setText(moonInfo.getMoonset().toString());
        faza.setText(String.valueOf(moonInfo.getIllumination()));
        synod.setText(String.valueOf(moonInfo.getAge()));
        pelnia.setText(moonInfo.getNextFullMoon().toString());
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
}
