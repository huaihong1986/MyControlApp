package com.cloudtv.hahong.mycontrolapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.cloudtv.hahong.mycontrolapp.config.Config;
import com.cloudtv.hahong.mycontrolapp.fragment.MainListFragment;
import com.cloudtv.hahong.mycontrolapp.fragment.ProductFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;

    private CharSequence mTitle;
    private MainFragment1 mFragment1;
    private MainListFragment mMainListFragment;
    private ProductFragment mProductFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    private MainFragment1 getFragment1() {
        if (mFragment1 == null) {
            mFragment1 = MainFragment1.newInstance(0);
        }
        return mFragment1;
    }

    private MainListFragment getMainListFragment() {
        if (mMainListFragment == null) {
            mMainListFragment = MainListFragment.newInstance(1);
        }
        return mMainListFragment;
    }

    private ProductFragment getProductFragment() {
        if (mProductFragment == null) {
            mProductFragment = ProductFragment.newInstance(2);
        }
        return mProductFragment;
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (position) {
            case 0:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, getFragment1())
                        .commit();
                break;

            case 1:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, getMainListFragment())
                        .commit();
                break;

            case 2:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, getProductFragment())
                        .commit();
                break;
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 0:
                mTitle = getString(R.string.title_section1);
                break;
            case 1:
                mTitle = getString(R.string.title_section2);
                break;
            case 2:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            //getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class MainFragment1 extends Fragment implements View.OnClickListener {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String TAG = "DoControl";

        private EditText mInputIp;
        private Button mHomeBtn;
        private Button mYouBtn;
        private Button mMartBtn;
        private Button mMonitorBtn;
        private Button mWifiBtn;

        private boolean mIsDoControl;


        public static MainFragment1 newInstance(int sectionNumber) {
            MainFragment1 fragment = new MainFragment1();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public MainFragment1() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            mInputIp = (EditText) rootView.findViewById(R.id.input_ip);

            mHomeBtn = (Button) rootView.findViewById(R.id.btn_home);
            mYouBtn = (Button) rootView.findViewById(R.id.btn_you);
            mMartBtn = (Button) rootView.findViewById(R.id.btn_mart);
            mMonitorBtn = (Button) rootView.findViewById(R.id.btn_monitor);
            mWifiBtn = (Button) rootView.findViewById(R.id.btn_wifi);
            mHomeBtn.setOnClickListener(this);
            mYouBtn.setOnClickListener(this);
            mMartBtn.setOnClickListener(this);
            mMonitorBtn.setOnClickListener(this);
            mWifiBtn.setOnClickListener(this);

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }

        @Override
        public void onPause() {
            super.onPause();
            Config.sWebHost = "http://" + mInputIp.getEditableText().toString().trim() + ":8080";
        }

        @Override
        public void onClick(View view) {
            if (mIsDoControl) {
                return;
            }
            mIsDoControl = true;

            String api = null;

            switch (view.getId()) {
                case R.id.btn_home:
                    api = "/home";
                    break;

                case R.id.btn_you:
                    api = "/you";
                    break;

                case R.id.btn_mart:
                    api = "/mart";
                    break;

                case R.id.btn_monitor:
                    api = "/monitor";
                    break;

                case R.id.btn_wifi:
                    api = "/wifi";
                    break;

                default:
                    break;
            }

            final URL url;
            try {
                url = new URL("http://" + mInputIp.getEditableText().toString().trim() + ":8080" + api);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return;
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpURLConnection conn;
                    try {
                        Log.d(TAG, "URL=" + url);
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setReadTimeout(10000);
                        conn.setConnectTimeout(15000);
                        conn.setDoInput(true);
                        conn.setDoOutput(true);
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        String line;
                        while (((line = br.readLine()) != null)) {
                            Log.d(TAG, "status=" + line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mIsDoControl = false;
                }
            }).start();

        }
    }

}
