package com.example.shreyas.newdemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

    private List<StockList> stocks;
    private FloatingActionButton fab_add_stock,fab_dispatch_stock;
    private LinearLayout ll_dispatch_finalizer;
    private RelativeLayout rl_fab;

    private Toolbar toolbar;
    RecyclerView whRecyclerView;
    LinearLayoutManager whLayoutManager;
    public WHAdapter whAdapter;


    public static int totalDispatchingAmount,totalSelectedAmount;
    public static String DispatchingCropName;
    public static int isSelectedCount;
    public static boolean isDispatchedProcessStarted;


    private TextView tv_total_selected_amount,tv_to_dispatch;
    public static Button btn_final_dispatch,btn_cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ware_house);
        warehousename = getIntent().getStringExtra("WarehouseName");
        setupToolbar(warehousename);

        DispatchingCropName="";
        totalDispatchingAmount=0;
        isSelectedCount=0;
        isDispatchedProcessStarted=false;
        mywarehousecontext=getApplicationContext();


        ll_dispatch_finalizer=(LinearLayout)findViewById(R.id.ll_dispatch_finalizer);
        tv_to_dispatch=(TextView)findViewById(R.id.tv_finalizer_to_dispatch_amount);
        tv_total_selected_amount=(TextView)findViewById(R.id.tv_total_selected_amount);

        btn_cancel=(Button)findViewById(R.id.btn_dispatch_finalizer_cancel);
        btn_final_dispatch=(Button)findViewById(R.id.btn_final_dispatch);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                MyWareHouse.isDispatchedProcessStarted=false;
                MyWareHouse.DispatchingCropName="";
                MyWareHouse.totalDispatchingAmount=0;
                MyWareHouse.isSelectedCount=0;
                ll_dispatch_finalizer.setVisibility(View.GONE);
                rl_fab.setVisibility(View.VISIBLE);
//                fab_add_stock.setVisibility(View.VISIBLE);
//                fab_dispatch_stock.setVisibility(View.VISIBLE);
                whAdapter.notifyDataSetChanged();

            }
        });


        btn_final_dispatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                final ProgressDialog progressDialog = new ProgressDialog(MyWareHouse.this);
                whAdapter.FinalizeDispatch(progressDialog);

            }
        });


        rl_fab=(RelativeLayout)findViewById(R.id.rl_fab);


        fab_add_stock = (FloatingActionButton)findViewById(R.id.fab_add_stock);
        fab_add_stock .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MyWareHouse.this, AddStock.class).putExtra("WarehouseName", warehousename);
                startActivity(i);
                finish();
            }
        });

        fab_dispatch_stock = (FloatingActionButton)findViewById(R.id.fab_dispatch_stock);
        fab_dispatch_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Log.d("Before showing ", "dispatch dialog----");
                GetDispatchAmountDialog getDispatchAmountDialog=new GetDispatchAmountDialog((Activity)v.getContext(),v.getContext(),whAdapter);
                getDispatchAmountDialog.show();

                Log.d("After showing ", "dispatch dialog----");


            }
        });

        whRecyclerView = (RecyclerView) findViewById(R.id.warehouse_recycler_view);
        whRecyclerView.setHasFixedSize(true);
        whLayoutManager = new LinearLayoutManager(this);
        whRecyclerView.setLayoutManager(whLayoutManager);



        stocks= new ArrayList<StockList>();
//        stocks.add(new StockList("z","z",14));
        initializeData();
        whAdapter = new WHAdapter(stocks,tv_total_selected_amount,tv_to_dispatch,fab_add_stock,fab_dispatch_stock,ll_dispatch_finalizer,rl_fab);
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
        final ProgressDialog progressDialog = new ProgressDialog(this);
        final long load_time_start=System.currentTimeMillis();
        progressDialog.isIndeterminate();
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setMessage(getString(R.string.stock_list_loading_progress_dialog_message));

        progressDialog.show();

        Thread temp_timer_thread=new Thread(new Runnable() {
            @Override
            public void run()
            {
                while(progressDialog.isShowing() && System.currentTimeMillis()-load_time_start<10000)
                {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (progressDialog.isShowing() && System.currentTimeMillis()-load_time_start>10000)
                {
                    progressDialog.dismiss();
                    showToast(getString(R.string.problem_in_loading_message));
                }
            }
        });


        stocks.clear();



        final StorageDBHandler db=new StorageDBHandler(this);

        List<StockInfo> TempstockInfoList = db.getstocks(warehousename);


        if(TempstockInfoList.size()==0)
        {


            Log.d("Connected to network", MainActivity.ConnectedToNetwork + "");
            if (MainActivity.ConnectedToNetwork == true) {

                JSONObject j;
                j = new JSONObject();
                try {
                    j.put("Email", MainActivity.Global_Email_Id);
                    j.put("WareHouseName", warehousename);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String url = MainActivity.ServerIP + "/getstockslist/";

                temp_timer_thread.start();


                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, j, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        Log.d("Onresponse", "yes");
                        try {
                            response = response.getJSONObject("Android");
                            JSONArray a = new JSONArray();
                            a = response.getJSONArray("Stocklist");
                            Log.d("stringed", "" + a.length());

                            int l = a.length();
                            for (int i = 0; i < l; i++) {
                                JSONObject temp = a.getJSONObject(i);
                                Log.d("stringed", temp.getString("StockName"));

                                stocks.add(new StockList(temp.getString("StockName"), temp.getString("StockCropName"), Integer.parseInt(temp.getString("StockAmount"))));

                                db.addIncompleteStock(temp.getString("StockName"), warehousename,temp.getString("StockAmount"),temp.getString("StockCropName"));



                                //                            MyWareHouse.stocks.add(new StockList("a", "b", 12));
                                Log.d("stocklist is", stocks.size() + "");


                            }
                            Log.d("Loaded stocks","from Internet");
                            whAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing()) {

                                progressDialog.dismiss();
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
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            for(StockInfo stockInfo:TempstockInfoList)
            {
                stocks.add(new StockList(stockInfo.getStockName(),stockInfo.getStockCropName(),Integer.parseInt(stockInfo.getStockAmount())));
            }
            if (progressDialog.isShowing())
            {

                progressDialog.dismiss();
            }

            Log.d("Loaded stocks","from db");
        }
    }
    public void showToast(final String toast)
    {
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_LONG).show();
            }
        });
    }


}
