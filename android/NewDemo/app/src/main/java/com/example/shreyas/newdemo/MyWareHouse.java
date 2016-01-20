package com.example.shreyas.newdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class MyWareHouse extends AppCompatActivity
{

    private Toolbar toolbar;
    private String warehousename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ware_house);
        warehousename = getIntent().getStringExtra("WarehouseName");
        setupToolbar(warehousename);







    }


    void setupToolbar(String title)
    {
        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        //getSupportActionBar().setSubtitle("Pune");
        toolbar.setTitleTextColor(getResources().getColor(R.color.Text_Icon));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.LightPrimaryColor));
    }

}
