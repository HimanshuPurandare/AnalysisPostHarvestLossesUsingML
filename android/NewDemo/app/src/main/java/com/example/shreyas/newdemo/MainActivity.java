package com.example.shreyas.newdemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.astuetz.PagerSlidingTabStrip;

public class MainActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private Navigation_Drawer drawerfragment;
    private Menu optionsMenu;
    private static MainActivity sInstance;

    public static String Global_User_Name,Global_Email_Id,Global_Regi_Token,GlobalUser_Role;


//    public static String ServerIP="http://192.168.1.131:5000";
    public static String ServerIP="http://10.42.0.249:5000";

    public static int signedin=0;
    public static boolean ConnectedToNetwork = true;

    SharedPreferencesClass sharedPreferencesClass;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

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
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sInstance = this;

        sharedPreferencesClass=new SharedPreferencesClass();
        sharedPreferencesClass.PullSharedPreferences(getApplicationContext());
        setupToolbar();

        setupNavigationDrawer();

        setupViewPager();

    }


    private void setupToolbar()

    {
        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Team END!!!");
        getSupportActionBar().setSubtitle("Pune");
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
                setRefreshActionButtonState(false);
                // Complete with your code
                return true;
        }        Log.d("buutton","pressed");
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

        private final String[] TITLES = { "      Info       ", "      Home       ", "       News       "};

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

}


