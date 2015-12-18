package com.example.shreyas.newdemo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class Navigation_Drawer extends android.support.v4.app.Fragment
{
    private ActionBarDrawerToggle nDrawerToggle;
    private DrawerLayout nDrawerLayout;

    public Navigation_Drawer()
    {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_navigation__drawer, container, false);

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
//                signin_btn = (Button) getView().findViewById(R.id.signinbutton);
//                signin_btn.setText("Sign Out");




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



}
