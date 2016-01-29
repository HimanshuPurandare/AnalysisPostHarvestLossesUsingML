package com.example.shreyas.newdemo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddStock extends AppCompatActivity
{

    private Toolbar toolbar;
    private String warehousename,stock_name,farmer_name,amount,sow_start,sow_end,harvest_start,harvest_end,in_time;
    private Spinner spinner1,spinner2;
    private ImageButton ib_harvest_start,ib_harvest_end,ib_sow_start,ib_sow_end,ib_in_time;

    private int dateflag,year,month,day;
    private EditText et_stock_name,et_harvest_start,et_harvest_end,et_sow_start,et_sow_end,et_in_time,et_farmer_name,et_amount;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);
        setupToolbar(getString(R.string.activity_addstock_title));
        warehousename = getIntent().getStringExtra("WarehouseName");

        spinner1 = (Spinner) findViewById(R.id.crop_spinner_warehouse);
        spinner2 = (Spinner) findViewById(R.id.crop_type_spinner_warehouse);



        List<String> list = new ArrayList<String>();
        list.add(getString(R.string.WheatString));
        list.add(getString(R.string.RiceString));
        list.add(getString(R.string.OnionString));

        CustomOnItemSelectedListener.wheatlist = new ArrayList<String>();
        CustomOnItemSelectedListener.wheatlist.add(getString(R.string.AjantaString));
        CustomOnItemSelectedListener.wheatlist.add(getString(R.string.ArjunString));
        CustomOnItemSelectedListener.wheatlist.add(getString(R.string.ParabhaniString));
        CustomOnItemSelectedListener.wheatlist.add(getString(R.string.MalvikaString));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line,list);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_dropdown_item_1line);

        spinner1.setAdapter(dataAdapter);


        CustomOnItemSelectedListener.dataAdapter1 = new ArrayAdapter<String>
                (this, android.R.layout.simple_expandable_list_item_1,CustomOnItemSelectedListener.wheatlist);

        CustomOnItemSelectedListener.dataAdapter1.setDropDownViewResource
                (android.R.layout.simple_expandable_list_item_1);

        spinner2.setAdapter(CustomOnItemSelectedListener.dataAdapter1);

        addListenerOnSpinnerItemSelection();



        et_stock_name=(EditText)findViewById(R.id.stockname);
        et_in_time=(EditText)findViewById(R.id.wh_intime);

        et_harvest_start=(EditText)findViewById(R.id.wh_harvesting_start);
        et_harvest_end=(EditText)findViewById(R.id.wh_harvesting_end);
        et_sow_start=(EditText)findViewById(R.id.wh_sowing_start);
        et_sow_end=(EditText)findViewById(R.id.wh_sowing_end);

        et_farmer_name=(EditText)findViewById(R.id.source_farmer_name);
        et_amount=(EditText)findViewById(R.id.stock_amount);


        ib_sow_start=(ImageButton)findViewById(R.id.imageButton1_wh);
        ib_sow_end=(ImageButton)findViewById(R.id.imageButton2_wh);
        ib_harvest_start=(ImageButton)findViewById(R.id.imageButton3_wh);
        ib_harvest_end=(ImageButton)findViewById(R.id.imageButton4_wh);
        ib_in_time=(ImageButton)findViewById(R.id.imageButton5_wh);

        ib_sow_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(0);
                dateflag=0;
            }
        });

        ib_sow_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(1);
                dateflag=1;
            }
        });
        ib_harvest_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(2);
                dateflag=2;
            }
        });
        ib_harvest_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(3);
                dateflag=3;
            }
        });
        ib_in_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(4);
                dateflag=4;
            }
        });








        Button addstock = (Button) findViewById(R.id.add_stock_btn);
        addstock.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                Log.d("Clicked", "btn clicked");
                attemptAddStock();
            }
        });









    }


    private void attemptAddStock()
    {
        Log.d("Inside adfarm","inside");

        stock_name = et_stock_name.getText().toString();
        farmer_name= et_farmer_name.getText().toString();
        amount= et_amount.getText().toString();

        CustomOnItemSelectedListener.crop=spinner1.getSelectedItem().toString();
        CustomOnItemSelectedListener.type_of_crop=spinner2.getSelectedItem().toString();

        sow_start=et_sow_start.getText().toString();
        sow_end=et_sow_end.getText().toString();
        harvest_start=et_harvest_start.getText().toString();
        harvest_end=et_harvest_end.getText().toString();
        in_time=et_in_time.getText().toString();

        if(MainActivity.ConnectedToNetwork)
        {

            JSONObject j = new JSONObject();
            try {
                j.put("StockUID",MainActivity.Global_Email_Id);
                j.put("StockWareHouseName",warehousename);
                j.put("StockName",stock_name);
                j.put("StockFarmerName",farmer_name);
                j.put("StockCropName",CustomOnItemSelectedListener.crop);
                j.put("StockCropType",CustomOnItemSelectedListener.type_of_crop);
                j.put("StockSowStart",sow_start);
                j.put("StockSowEnd",sow_end);
                j.put("StockHarvestStart",harvest_start);
                j.put("StockHarvestEnd",harvest_end);
                j.put("StockInTime",in_time);
                j.put("StockAmount",amount);


            } catch (JSONException e)
            {
                e.printStackTrace();
            }

            String url = MainActivity.ServerIP + "/addstock/";
            JsonObjectRequest jsonRequest = new JsonObjectRequest
                    (Request.Method.POST, url, j, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // the response is already constructed as a JSONObject!
                            try {
                                response = response.getJSONObject("Android");
                                String signinresult = response.getString("Result");
                                if (signinresult.equals("Valid"))
                                {
                                    Intent i = new Intent(AddStock.this, MyWareHouse.class).putExtra("WarehouseName", warehousename);
                                    startActivity(i);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "Stock Addition Failed!!!", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });
            MainActivity.getInstance().addToRequestQueue(jsonRequest);

        }
        else
        {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }




    }


    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id)
    {
        return new DatePickerDialog(this, datePickerListener,2015, 1, 1);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener()
    {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay)
        {
            if(dateflag==0)
            {
                et_sow_start.setText(selectedDay + "-" + (selectedMonth + 1) + "-"
                        + selectedYear);

            }
            else if(dateflag==1)
            {
                et_sow_end.setText(selectedDay + "-" + (selectedMonth + 1) + "-"
                        + selectedYear);
            }
            else if(dateflag==2)
            {
                et_harvest_start.setText(selectedDay + "-" + (selectedMonth + 1) + "-"
                        + selectedYear);
            }
            else if(dateflag==3)
            {
                et_harvest_end.setText(selectedDay + "-" + (selectedMonth + 1) + "-"
                        + selectedYear);
            }
            else
            {
                et_in_time.setText(selectedDay + "-" + (selectedMonth + 1) + "-"
                        + selectedYear);
            }

        }
    };






    public void addListenerOnSpinnerItemSelection(){

        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        spinner2.setOnItemSelectedListener(new CustomOnItemSelectedListener());
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


    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(AddStock.this, MyWareHouse.class).putExtra("WarehouseName", warehousename);
        startActivity(i);
        finish();
    }


}
