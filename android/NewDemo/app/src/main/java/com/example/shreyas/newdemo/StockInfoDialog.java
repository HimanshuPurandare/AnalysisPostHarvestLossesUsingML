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

    TextView tv_StockInfoTitle,tv_StockCropName,tv_StockCropType,tv_StockSowingPeriod,tv_StockHarvestingPeriod,tv_StockAmount,tv_StockArrivalDate,tv_StockFarmer;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.stock_info_dialog);
//        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.stock_info_dialog_linearlayout);
//        linearLayout.setMinimumWidth();

        tv_StockInfoTitle=(TextView)findViewById(R.id.stock_info_title);
        tv_StockCropType=(TextView)findViewById(R.id.dialog_stock_crop_type);
        tv_StockAmount=(TextView)findViewById(R.id.dialog_stock_amount);
        tv_StockSowingPeriod=(TextView)findViewById(R.id.dialog_stock_sowing_period);
        tv_StockHarvestingPeriod=(TextView)findViewById(R.id.dialog_stock_harvesting_period);
        tv_StockArrivalDate=(TextView)findViewById(R.id.dialog_stock_arrival_date);
        tv_StockFarmer=(TextView)findViewById(R.id.dialog_stock_farmer);



        tv_StockInfoTitle.setText(stockinfo.getStockName());
        tv_StockCropType.setText(stockinfo.getStockCropName()+" ( "+stockinfo.getStockCropType()+" )");
        tv_StockAmount.setText(stockinfo.getStockAmount()+getContext().getString(R.string.kgasstring));
        tv_StockSowingPeriod.setText(stockinfo.getStockSowStart() + getContext().getString(R.string.toasstring)  +stockinfo.getStockSowEnd());
        tv_StockHarvestingPeriod.setText(stockinfo.getStockHarvestStart()+getContext().getString(R.string.toasstring)+stockinfo.getStockHarvestEnd());
        tv_StockArrivalDate.setText(stockinfo.getStockInTime());
        tv_StockFarmer.setText(stockinfo.getStockFarmerName());



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
