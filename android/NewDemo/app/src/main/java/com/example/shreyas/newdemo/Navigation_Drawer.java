package com.example.shreyas.newdemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;


public class Navigation_Drawer extends Fragment implements NavigationView.OnCreateContextMenuListener,NavigationView.OnNavigationItemSelectedListener
{

    SharedPreferencesClass sharedPreferencesClass;
    private ActionBarDrawerToggle nDrawerToggle;
    private DrawerLayout nDrawerLayout;
    NavigationView navigationView;
    Menu nav_menu;
    MenuItem signin_item;

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
        nav_menu=navigationView.getMenu();
        signin_item=nav_menu.getItem(0);

        sharedPreferencesClass=new SharedPreferencesClass();
        sharedPreferencesClass.PullSharedPreferences(getContext());

//        View headerLayout = navigationView.getHeaderView(0);
        View headerLayout =
                navigationView.inflateHeaderView(R.layout.nav_header_main);



        if(MainActivity.signedin==1)
        {
            signin_item.setTitle(R.string.signoutstring);

            TextView tv_nav_name=(TextView)headerLayout.findViewById(R.id.nav_name);
            TextView tv_nav_mail=(TextView)headerLayout.findViewById(R.id.nav_mailid);

            Log.d(tv_nav_name.getText().toString(),"test");
            Log.d("test name",headerLayout.findViewById(R.id.nav_name).toString());
            Log.d("test mail", R.id.nav_mailid + "");
            tv_nav_mail.setText(MainActivity.Global_Email_Id);
            tv_nav_name.setText(MainActivity.Global_User_Name+" ("+MainActivity.GlobalUser_Role+")");


        }


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
        Log.d(item.getItemId()+"",item.getTitle().toString());

        if (item.getItemId()==R.id.nav_signin)
        {
            if(MainActivity.signedin==0)
            {
                Log.d("towards ","signing in");
                Intent i = new Intent(getActivity(), SignIn.class);
                startActivity(i);
                getActivity().finish();
            }
            else if(MainActivity.ConnectedToNetwork)
            {
                Log.d("towards ", "signing out");
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("SignInData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent i = new Intent(getActivity(), MainActivity.class);
                MainActivity.signedin=0;

                StorageDBHandler db=new StorageDBHandler(this.getContext());
                db.deleteAll();


                startActivity(i);
                getActivity().finish();

            }
            else
            {
                Log.d("doing ","nothing");
                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();

            }
        }
        else if(item.getItemId()==R.id.nav_myprofile)
        {

        }
        else if(item.getItemId()==R.id.nav_notification)
        {

        }
        else if(item.getItemId()==R.id.nav_conse)
        {

        }
        else if(item.getItemId()==R.id.nav_settings)
        {

        }
        else if(item.getItemId()==R.id.nav_share)
        {

        }
        else if(item.getItemId()==R.id.nav_help)
        {
            Log.d("Help","pressed by niranjan");
        }
        else if(item.getItemId()==R.id.nav_contactus)
        {

        }

        return false;
    }
}
