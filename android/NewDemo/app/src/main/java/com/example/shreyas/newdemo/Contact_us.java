package com.example.shreyas.newdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class Contact_us extends AppCompatActivity {


    private Toolbar toolbar;

    TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);


        t1 = (TextView) findViewById(R.id.names);

        t1.setText(R.string.member_info_shreyas+"\n"+R.string.member_info_niranjan+"\n"+R.string.member_info_himanshu+"\n"+R.string.member_info_prathamesh);
        setupToolbar();
    }

    private void setupToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contact Us");
        toolbar.setTitleTextColor(getResources().getColor(R.color.Text_Icon));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.LightPrimaryColor));
    }

}
