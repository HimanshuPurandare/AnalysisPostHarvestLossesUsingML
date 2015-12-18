package com.example.shreyas.newdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

/**
 * Created by niranjan on 18/12/15.
 */

class MyAdapter extends FragmentPagerAdapter
{
    public MyAdapter(FragmentManager fm)
    {
        super(fm);
    }

    public Fragment getItem(int i)
    {
        //    Log.d("inside getItem", "The count is"+i);
        Fragment fragment=null;
        if(i==0)
        {
            fragment=new Sliding_Page_Home();
        }
        else if(i==1)
        {
            fragment=new Sliding_Page_Info();
        }
        else if(i==2)
        {
            fragment=new Sliding_Page_News();
        }
        return fragment;
    }

    public int getCount()
    {
        //    Log.d("Inside getCount","getcount is called");
        return 3;
    }
    @Override
    public CharSequence getPageTitle(int position)
    {
        if(position == 0)
        {
            Log.d("Inside oth string", "0");
            return "Farm Info";
        }
        else if(position == 1)
        {
            Log.d("Inside 1st string","1");
            return "General Info";
        }
        else if (position==2)
        {
            Log.d("Inside 1st string","2");
            return "Current News";

        }
        return null;
    }

}
