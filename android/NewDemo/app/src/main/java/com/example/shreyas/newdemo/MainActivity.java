package com.example.shreyas.newdemo;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    ViewPager viewpager=null;
    PagerTitleStrip titleStrip;
    private Navigation_Drawer drawerfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();

        setupNavigationDrawer();

        viewpager=(ViewPager)findViewById(R.id.pager);
        titleStrip=(PagerTitleStrip)findViewById(R.id.pagetitles);

        FragmentManager fragmentmanager=getSupportFragmentManager();
        viewpager.setAdapter(new MyAdapter(fragmentmanager));


        titleStrip.setTextColor(getResources().getColor(R.color.Text_Icon));
        titleStrip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);


    }

    void setupToolbar()
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





}


