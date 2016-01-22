package com.example.shreyas.newdemo;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by niranjan on 22/1/16.
 */
public class StockInfoDialog extends Dialog implements View.OnClickListener
{

    Activity a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.stock_info_dialog);
//        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.stock_info_dialog_linearlayout);
//        linearLayout.setMinimumWidth();


    }
    public StockInfoDialog(Activity a)
    {
        super(a);

        // TODO Auto-generated constructor stub

    }





    @Override
    public void onClick(View v) {

    }
}
