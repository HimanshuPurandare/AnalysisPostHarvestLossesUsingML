package com.example.shreyas.newdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.astuetz.PagerSlidingTabStrip;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
{

//GCM Notification Related
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    private BroadcastReceiver mRegistrationBroadcastReceiver;


    private Locale myLocale;



    private Toolbar toolbar;
    private Navigation_Drawer drawerfragment;
    private Menu optionsMenu;
    static MainActivity sInstance;

//Global Variables related to User
    public static String Global_User_Name,Global_Email_Id,Global_Regi_Token,GlobalUser_Role,Global_Lang_Choice;
    public static String Saved_Global_Temp,Saved_Global_Hum,Saved_Global_SM;

//    public static String ServerIP="http://192.168.0.3:5000";
//    public static String ServerIP="http://10.42.0.249:5000";
//    public static String ServerIP="http://192.168.0.105:5000";
    public static String ServerIP="http://10.42.0.1:5000";
//      public static String ServerIP = "http://192.168.1.131:5000";
//    public static String ServerIP = "http://reviewpager.com";
//    public static String ServerIP = "http://192.168.0.117:5000";


    public static int signedin=0;
    public static boolean ConnectedToNetwork = true;

//SharedPreferences Related
    SharedPreferencesClass sharedPreferencesClass;


//Volley Data Send/Recv related
    private RequestQueue mRequestQueue;
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

        VolleyLog.d("Adding request to queue: %s", req.getUrl());

        getRequestQueue().add(req);
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     *
     * @param req
     * */
    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        req.setTag(TAG);

        getRequestQueue().add(req);
    }

    public static synchronized MainActivity getInstance() {
        return sInstance ;
    }

    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRegistrationBroadcastReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {

            }
        };
        if (checkPlayServices()) {
            Log.d("checkplayservis","services checked");
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }


        sInstance = this;

        sharedPreferencesClass=new SharedPreferencesClass();
        sharedPreferencesClass.PullSharedPreferences(getApplicationContext());
        setLanguage();

        setupToolbar();

        setupNavigationDrawer();

        setupViewPager();

    }

    private void setLanguage()
    {
        SharedPreferences sharedpreflang=getSharedPreferences("Language", Context.MODE_PRIVATE);
        Global_Lang_Choice=sharedpreflang.getString("lang", "eng");


        if(Global_Lang_Choice.equals("eng"))
        {
            myLocale = new Locale("eng");

            Locale.setDefault(myLocale);
            android.content.res.Configuration config = new android.content.res.Configuration();
            config.locale = myLocale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        }
        else{
            myLocale = new Locale("mar");

            Locale.setDefault(myLocale);
            android.content.res.Configuration config = new android.content.res.Configuration();
            config.locale = myLocale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        }



    }


    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause()
    {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }




    private void setupToolbar()

    {
        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SmartFS");
        toolbar.setTitleTextColor(getResources().getColor(R.color.Text_Icon));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.LightPrimaryColor));
    }

    void setupNavigationDrawer()
    {
        drawerfragment=(Navigation_Drawer)getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerfragment.setUP((DrawerLayout) findViewById(R.id.drawer_layout_id), toolbar);
    }

    void setupViewPager()
    {
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.optionsMenu = menu;

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refreshbutton, menu);

        MenuItem item=menu.getItem(1);
        if(Global_Lang_Choice.equals("eng"))
        {
            item.setIcon(R.drawable.ma_lang_icon);
//            myLocale = new Locale("eng");
//
//            Locale.setDefault(myLocale);
//            android.content.res.Configuration config = new android.content.res.Configuration();
//            config.locale = myLocale;
//            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        }
        else{
            item.setIcon(R.drawable.en_lang_icon);
//            myLocale = new Locale("mar");
//
//            Locale.setDefault(myLocale);
//            android.content.res.Configuration config = new android.content.res.Configuration();
//            config.locale = myLocale;
//            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        }

        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.airport_menuRefresh:
                setRefreshActionButtonState(true);
                /*
                    Set code of fetching data from database here
                    and till then animation will be displayed
                 */
                Toast.makeText(getApplicationContext(),"Refreshing data",Toast.LENGTH_SHORT).show();
                setRefreshActionButtonState(false);
                // Complete with your code
                return true;

            case R.id.lang_change_menuitem: {
                Log.d("inside item", "inside langitem");



                if (Global_Lang_Choice.equals("eng")) {
                    Log.d("inside item","inside langitem eng");

                    item.setIcon(R.drawable.ma_lang_icon);


                    SharedPreferences sharedpreflang = getSharedPreferences("Language", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreflang.edit();
                    editor.putString("lang", "mar");
                    editor.commit();

                    myLocale = new Locale("mar");

                    Locale.setDefault(myLocale);
                    android.content.res.Configuration config = new android.content.res.Configuration();
                    config.locale = myLocale;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

                    Global_Lang_Choice="mar";


                    Intent i=new Intent(MainActivity.this,MainActivity.class);
                 startActivity(i);
                    finish();
                }

                else {

                    item.setIcon(R.drawable.en_lang_icon);

                    SharedPreferences sharedpreflang = getSharedPreferences("Language", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreflang.edit();
                    editor.putString("lang", "eng");
                    editor.commit();

                    myLocale = new Locale("eng");

                    Locale.setDefault(myLocale);
                    android.content.res.Configuration config = new android.content.res.Configuration();
                    config.locale = myLocale;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

                    Global_Lang_Choice="eng";


                    Intent i=new Intent(MainActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();


                }
            }
        }

        Log.d("buutton","pressed");
        return super.onOptionsItemSelected(item);
    }



    public void setRefreshActionButtonState(final boolean refreshing) {
        if (optionsMenu != null) {
            final MenuItem refreshItem = optionsMenu
                    .findItem(R.id.airport_menuRefresh);
            if (refreshItem != null) {
                if (refreshing) {
                    refreshItem.setActionView(R.layout.actionbar_indeterminate_progress);
                } else {
                    refreshItem.setActionView(null);
                }
            }
        }
    }



    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {getString(R.string.vp_home_option),getString(R.string.vp_info_option),getString(R.string.vp_news_option)};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return SuperAwesomeCardFragment.newInstance(position);
        }

    }


    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (myLocale != null){
            newConfig.locale = myLocale;
            Locale.setDefault(myLocale);
            getBaseContext().getResources().updateConfiguration(newConfig, getBaseContext().getResources().getDisplayMetrics());
        }
    }


}


