package com.example.shreyas.newdemo;

/**
 * Created by shreyas on 12/17/15.
 */
public class Farm_info
{
    String farm_name;

    String temp;
    String Humi;
    String sm;

    String location_url;

    Farm_info(String a,String b,String c,String d,String e)
    {
        this.farm_name = a;
        this.temp = b;
        this.Humi = c;
        this.sm = d;
        this.location_url = e;
    }
}
