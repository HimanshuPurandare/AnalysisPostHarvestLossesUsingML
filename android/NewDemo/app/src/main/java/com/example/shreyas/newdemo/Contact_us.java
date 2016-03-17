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

        t1.setText("Shreyas Pansare(9421947780)\nNiranjan Ketkar(9881362827)\nHimanshu Purandare(7387545150)\nPrathamesh Padhye(9405436083)");
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
