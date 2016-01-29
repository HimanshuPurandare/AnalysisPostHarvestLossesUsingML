package com.example.shreyas.newdemo;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by niranjan on 22/1/16.
 */
public class StockInfoDialog extends Dialog implements View.OnClickListener
{

    Activity a;
    StockInfo stockinfo;

    TextView tv_StockCropName,tv_StockCropType,tv_StockSowStart,tv_StockSowEnd,tv_StockHarvestStart,tv_StockHarvestEnd,tv_StockAmount,tv_StockInTime,tv_StockFarmerName;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.stock_info_dialog);
//        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.stock_info_dialog_linearlayout);
//        linearLayout.setMinimumWidth();


        tv_StockCropName=(TextView)findViewById(R.id.dialog_stock_crop_name);
        tv_StockCropType=(TextView)findViewById(R.id.dialog_stock_crop_type);
        tv_StockSowStart=(TextView)findViewById(R.id.dialog_stock_sow_start);
        tv_StockSowEnd=(TextView)findViewById(R.id.dialog_stock_sow_end);
        tv_StockHarvestStart=(TextView)findViewById(R.id.dialog_stock_harvest_start);
        tv_StockHarvestEnd=(TextView)findViewById(R.id.dialog_stock_harvest_end);
        tv_StockAmount=(TextView)findViewById(R.id.dialog_stock_amount);
        tv_StockInTime=(TextView)findViewById(R.id.dialog_stock_in_time);
        tv_StockFarmerName=(TextView)findViewById(R.id.dialog_stock_farmer_name);



        tv_StockCropName.setText(this.getContext().getString(R.string.dialog_stock_crop_name)+stockinfo.getStockCropName());
        tv_StockCropType.setText(this.getContext().getString(R.string.dialog_stock_crop_type)+stockinfo.getStockCropType());
        tv_StockSowStart.setText(this.getContext().getString(R.string.dialog_stock_sow_start)+stockinfo.getStockSowStart());
        tv_StockSowEnd.setText(this.getContext().getString(R.string.dialog_stock_sow_end)+stockinfo.getStockSowEnd());
        tv_StockHarvestStart.setText(this.getContext().getString(R.string.dialog_stock_harvest_start)+stockinfo.getStockHarvestStart());
        tv_StockHarvestEnd.setText(this.getContext().getString(R.string.dialog_stock_harvest_end)+stockinfo.getStockHarvestEnd());
        tv_StockAmount.setText(this.getContext().getString(R.string.dialog_stock_amount)+stockinfo.getStockAmount());
        tv_StockInTime.setText(this.getContext().getString(R.string.dialog_stock_in_time)+stockinfo.getStockInTime());
        tv_StockFarmerName.setText(this.getContext().getString(R.string.dialog_stock_farmer_name)+stockinfo.getStockFarmerName());



    }
    public StockInfoDialog(Activity a,StockInfo stockinfo)
    {
        super(a);

        this.stockinfo=stockinfo;
        // TODO Auto-generated constructor stub

    }





    @Override
    public void onClick(View v)
    {
        //Used after adding edit button!!!

    }
}
