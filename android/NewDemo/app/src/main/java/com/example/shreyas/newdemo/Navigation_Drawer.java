package com.example.shreyas.newdemo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class Navigation_Drawer extends Fragment implements NavigationView.OnCreateContextMenuListener,NavigationView.OnNavigationItemSelectedListener
{
    private ActionBarDrawerToggle nDrawerToggle;
    private DrawerLayout nDrawerLayout;
    NavigationView navigationView;
    Menu menu;

    public Navigation_Drawer()
    {

    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_navigation__drawer, container, false);
        navigationView=(NavigationView)view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }
    public void setUP(DrawerLayout drawerlayout,Toolbar toolbar) {
        nDrawerLayout = drawerlayout;
        nDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerlayout, toolbar, R.string.drawer_open, R.string.drawer_close){

            public void onDrawerOpened(View drawerview) {
                super.onDrawerOpened(drawerview);




            }
            public void onDrawerClosed(View drawerview)
            {
                super.onDrawerClosed(drawerview);
            }
        };
        nDrawerLayout.setDrawerListener(nDrawerToggle);

        nDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                nDrawerToggle.syncState();
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        Log.d("id = ",item.getTitle().toString());

        if (item.getTitle().toString().equals("SignIn"))
        {
            // Handle the signin action
            Intent i = new Intent(getActivity(), SignIn.class);
            startActivity(i);
            getActivity().finish();

        }




        return false;
    }
}
