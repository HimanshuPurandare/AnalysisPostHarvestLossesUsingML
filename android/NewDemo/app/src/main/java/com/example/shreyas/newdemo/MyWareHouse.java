package com.example.shreyas.newdemo;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyWareHouse extends AppCompatActivity
{
    public static String warehousename;
    public static Context mywarehousecontext;

    private static List<StockList> stocks;
    FloatingActionButton fab_warehouse;


    private Toolbar toolbar;
    RecyclerView whRecyclerView;
    LinearLayoutManager whLayoutManager;
    WHAdapter whAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ware_house);
        warehousename = getIntent().getStringExtra("WarehouseName");
        setupToolbar(warehousename);

        mywarehousecontext=getApplicationContext();
        fab_warehouse = (FloatingActionButton)findViewById(R.id.fab_warehouse);
        fab_warehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MyWareHouse.this, AddStock.class).putExtra("WarehouseName", warehousename);
                startActivity(i);
                finish();
            }
        });


        whRecyclerView = (RecyclerView) findViewById(R.id.warehouse_recycler_view);
        whRecyclerView.setHasFixedSize(true);
        whLayoutManager = new LinearLayoutManager(this);
        whRecyclerView.setLayoutManager(whLayoutManager);



        stocks= new ArrayList<StockList>();
//        stocks.add(new StockList("z","z",14));
        initializeData();
        whAdapter = new WHAdapter(stocks);
        whAdapter.notifyDataSetChanged();
        whRecyclerView.setAdapter(whAdapter);



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
    private void initializeData()
    {

        Log.d("Connected to network", MainActivity.ConnectedToNetwork + "");
        if(MainActivity.ConnectedToNetwork==true)
        {

            JSONObject j;
            j = new JSONObject();
            try {
                j.put("Email",MainActivity.Global_Email_Id);
                j.put("WareHouseName",warehousename);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            String url = MainActivity.ServerIP + "/getstockslist/";


            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, j, new Response.Listener<JSONObject>()
            {
                @Override
                public void onResponse(JSONObject response) {
                    // the response is already constructed as a JSONObject!
                    Log.d("Onresponse", "yes");
                    try {
                        response = response.getJSONObject("Android");
                        JSONArray a = new JSONArray();
                        a=response.getJSONArray("Stocklist");
                        Log.d("stringed",""+a.length());

                        int l = a.length();
                        for(int i=0;i<l;i++)
                        {
                            JSONObject temp = a.getJSONObject(i);
                            Log.d("stringed", temp.getString("StockName"));
                            stocks.add(new StockList(temp.getString("StockName"),temp.getString("StockCropName"),Integer.parseInt(temp.getString("StockAmount"))));
//                            MyWareHouse.stocks.add(new StockList("a", "b", 12));
                            Log.d("stocklist is",stocks.size()+"");
                        }
                        whAdapter.notifyDataSetChanged();


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
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
        }

    }

}
