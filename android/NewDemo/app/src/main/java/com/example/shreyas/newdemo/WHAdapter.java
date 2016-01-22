package com.example.shreyas.newdemo;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

/**
 * Created by niranjan on 20/1/16.
 */
public class WHAdapter extends RecyclerView.Adapter<WHAdapter.StockViewHolder>
{

    List<StockList> stocks;
    WHAdapter(List<StockList> stocks)
    {
//        this.stocks=new ArrayList<StockList>();
        this.stocks = stocks;
//        this.stocks.add(new StockList("abc","abc",14));
//        this.stocks.add(new StockList("abc","abc",14));
//        this.stocks.add(new StockList("abc","abc",14));

        Log.d("stocks",stocks.toString());
    }


    @Override
    public StockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.warehouse_card, parent, false);
        StockViewHolder svh = new StockViewHolder(v);
        Log.d("oncreateviewholder","called");
        return svh;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        Log.d("onattached called","---");
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(StockViewHolder holder, int pos)
    {
        holder.stockname.setText(stocks.get(pos).Stockname);
        holder.cropname.setText(stocks.get(pos).Cropname);
        holder.amount.setText(stocks.get(pos).amount+"");
        Log.d("Onbindvwhldr compl",stocks.get(pos).Cropname);

    }

    @Override
    public int getItemCount()
    {
        Log.d("getitemcount called",stocks.size()+"");
        return stocks.size();
    }


    public static class StockViewHolder extends RecyclerView.ViewHolder
    {
        CardView cv;
        TextView stockname,cropname,amount;

        StockViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.stocklistcv);
            stockname = (TextView)itemView.findViewById(R.id.stock_list_name);
            cropname = (TextView)itemView.findViewById(R.id.crop_name);
            amount = (TextView)itemView.findViewById(R.id.amount);

            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    show_stock_info(v,stockname.getText().toString());
                }
            });
            
            cv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    RelativeLayout relativeLayout = (RelativeLayout) v.getParent();
                    relativeLayout.setBackgroundColor(Color.LTGRAY);
                    return false;
                }
            });


        }

        private void show_stock_info(View v,final String stockname)
        {

            final View v1=v;


            Log.d("Connected to network", MainActivity.ConnectedToNetwork + "");
            if(MainActivity.ConnectedToNetwork==true)
            {

                JSONObject j;
                j = new JSONObject();
                try {
                    j.put("Email",MainActivity.Global_Email_Id);
                    j.put("WareHouseName",MyWareHouse.warehousename);
                    j.put("StockName",stockname);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String url = MainActivity.ServerIP + "/getstockinfo/";


                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, j, new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        Log.d("Onresponse", "yes");
                        try {
                            response = response.getJSONObject("Android");
                            Log.d("Stockinfo received as", response.toString());

                            StockInfo stockInfo=new StockInfo(
                                    stockname,
                                    MyWareHouse.warehousename,
                                    response.getString("StockCropName"),
                                    response.getString("StockCropType"),
                                    response.getString("StockSowStart"),
                                    response.getString("StockSowEnd"),
                                    response.getString("StockHarvestStart"),
                                    response.getString("StockHarvestEnd"),
                                    response.getString("StockAmount"),
                                    response.getString("StockInTime"),
                                    response.getString("StockFarmerName")
                                    );

                            StockInfoDialog stockInfoDialog=new StockInfoDialog((Activity)v1.getContext(),stockInfo);
                            stockInfoDialog.show();
                            Window window = stockInfoDialog.getWindow();
                            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);





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
                Toast.makeText(MyWareHouse.mywarehousecontext, "No Internet Connection", Toast.LENGTH_LONG).show();
            }









        }



    }
}
