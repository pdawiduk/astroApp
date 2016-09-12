package com.example.shogun.astroapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CurrentWeatherInformationFragment extends Fragment  {


    private OnFragmentInteractionListener mListener;

    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }


    public CurrentWeatherInformationFragment() {

    }

    TextView pressure;
    TextView longitiude;
    TextView latitiude;
    TextView weatherDescripion;
    TextView temp;
    ImageView weatherIcon;

    public static CurrentWeatherInformationFragment newInstance(String param1) {
        CurrentWeatherInformationFragment fragment = new CurrentWeatherInformationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_current_weather_information, container, false);
        pressure = (TextView) rootView.findViewById(R.id.pressure);
        longitiude = (TextView) rootView.findViewById(R.id.longitiude);
        latitiude = (TextView) rootView.findViewById(R.id.latitiude);
        weatherDescripion = (TextView) rootView.findViewById(R.id.weather_description);
        temp = (TextView) rootView.findViewById(R.id.celcius_temp);
        weatherIcon = (ImageView) rootView.findViewById(R.id.iconweather);
        return rootView;
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}

