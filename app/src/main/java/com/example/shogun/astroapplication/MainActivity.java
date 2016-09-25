package com.example.shogun.astroapplication;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements MoonFragment.OnFragmentInteractionListener, SunFragment.OnFragmentInteractionListener, CurrentWeatherInformationFragment.OnFragmentInteractionListener, ExtraInformationFragment.OnFragmentInteractionListener, WeatherForecast.OnFragmentInteractionListener{
    private Timer autoUpdateForData;
    private static boolean twoPane = false;


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo().isConnected() != false;
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
    }



    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        if(findViewById(R.id.MoonFragment_container)== null) {
            twoPane =false;

            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(mViewPager);

        }
        else {
            twoPane =true;

}

    }

    @Override
    public void onResume() {
        boolean isConnected;
        try{
        isConnected= isNetworkConnected();}
        catch(Exception e) {
            isConnected = false;
        }
        if(isConnected){
            Toast.makeText(getBaseContext(),"polaczony",Toast.LENGTH_SHORT);
        }
        else{
            Toast.makeText(getBaseContext()," nie polaczony",Toast.LENGTH_SHORT);
        }


        MoonFragment moonFragment =(MoonFragment) getSupportFragmentManager().findFragmentById(R.id.MoonFragment_container);
        SunFragment sunFragment = (SunFragment)  getSupportFragmentManager().findFragmentById(R.id.sunFragment);

        super.onResume();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }


        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch(position) {

                case 0: return MoonFragment.newInstance("MoonFragment, Instance 1");
                case 1: return SunFragment.newInstance("SunFragment, Instance 1");
                case 2: return CurrentWeatherInformationFragment.newInstance("CurrentWeatherInformationFragment , Instance 1");
                case 3: return ExtraInformationFragment.newInstance("ExtraInformationFragment");
                case 4: return WeatherForecast.newInstance("WeatherForecastFragment");
                default: return new Fragment();
            }

        }

        @Override
        public int getCount() {

            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Moon";
                case 1:
                    return "Sun";
                case 2:
                    return "Basic information";
                case 3:
                    return "Weather Information";
                case 4:
                    return "Forecast";


            }
            return null;
        }
    }
}
