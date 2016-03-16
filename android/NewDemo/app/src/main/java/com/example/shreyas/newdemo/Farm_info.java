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

    String crop;
    String croptype;
    String sowingperiod;
    String hwid;
    String area;

    String growth;

    Farm_info(String a,String b,String c,String d,String e,String a1,String b1,String c1,String d1,String e1,String f)
    {
        this.farm_name = a;
        this.temp = b;
        this.Humi = c;
        this.sm = d;
        this.location_url = e;

        this.crop = a1;
        this.croptype = b1;
        this.sowingperiod = c1;
        this.hwid = d1;
        this.area = e1;

        this.growth = f;
    }
}
