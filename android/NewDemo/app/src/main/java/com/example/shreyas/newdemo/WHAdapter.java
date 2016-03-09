package com.example.shreyas.newdemo;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.AutoText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
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

import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by niranjan on 20/1/16.
 */
public class WHAdapter extends RecyclerView.Adapter<WHAdapter.StockViewHolder> implements GetDispatchAmountDialog.onDispatchStarted
{
    static List<StockList> stocks,dispatchable_stocks;
    TextView tv_total_selected_amount,tv_to_dispatch;
    FloatingActionButton fab_add_stock,fab_dispatch_stock;
    LinearLayout ll_dispatch_finalizer;
    RelativeLayout rl_fab;

    WHAdapter(List<StockList> stocks,TextView tv_total_selected_amount,TextView tv_to_dispatch,FloatingActionButton fab_add_stock,FloatingActionButton fab_dispatch_stock,LinearLayout ll_dispatch_finalizer,RelativeLayout rl_fab)
    {
//        this.stocks=new ArrayList<StockList>();
        this.stocks = stocks;
        this.tv_to_dispatch=tv_to_dispatch;
        this.tv_total_selected_amount=tv_total_selected_amount;
        this.ll_dispatch_finalizer=ll_dispatch_finalizer;
        this.fab_add_stock=fab_add_stock;
        this.fab_dispatch_stock=fab_dispatch_stock;
        this.rl_fab=rl_fab;

//        this.stocks.add(new StockList("abc","abc",14));
//        this.stocks.add(new StockList("abc","abc",14));
//        this.stocks.add(new StockList("abc","abc",14));

        Log.d("stocks",stocks.toString());
    }


    @Override
    public StockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.warehouse_card, parent, false);
        StockViewHolder svh = new StockViewHolder(v);
        Log.d("oncreateviewholder", "called");
        return svh;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        Log.d("onattached called","---");
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(final StockViewHolder holder, final int pos)
    {
        holder.stockname.setText(stocks.get(pos).getStockname());
        holder.cropname.setText(stocks.get(pos).getCropname());
        holder.amount.setText(stocks.get(pos).getAmount() + "");

        holder.position=pos;
        final int temp_pos=pos;
        final StockViewHolder temp_holder=holder;



        if(MyWareHouse.isDispatchedProcessStarted)
        {
            holder.et_dispatch_amount_selector.setVisibility(View.VISIBLE);

            if(stocks.get(pos).getIsSelected())
            {
                holder.cv.setBackgroundColor(Color.LTGRAY);
                holder.linearLayout.setBackgroundColor(Color.LTGRAY);
                holder.et_dispatch_amount_selector.setText(stocks.get(pos).getSelected_amount() + "");
                holder.et_dispatch_amount_selector.setFocusableInTouchMode(true);
                holder.et_dispatch_amount_selector.setFocusable(true);




            }
            else
            {
                holder.cv.setBackgroundColor(Color.WHITE);
                holder.linearLayout.setBackgroundColor(Color.WHITE);


                holder.et_dispatch_amount_selector.setFocusable(false);
                holder.et_dispatch_amount_selector.setFocusableInTouchMode(false);
                holder.et_dispatch_amount_selector.setText("0");
            }

        }
        else
        {
            holder.cv.setBackgroundColor(Color.WHITE);
            holder.linearLayout.setBackgroundColor(Color.WHITE);

            holder.et_dispatch_amount_selector.setText("0");
            holder.et_dispatch_amount_selector.setVisibility(View.GONE);
            stocks.get(pos).setIsSelected(false);
            stocks.get(pos).setSelected_amount(0);
        }





        holder.et_dispatch_amount_selector.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s)
            {
                try {
                    String et_current_string=holder.et_dispatch_amount_selector.getText().toString();
                    int current_num = Integer.parseInt(et_current_string);
                    int old_num = stocks.get(pos).getSelected_amount();
//                            while(et_current_string.length()>1&&(et_current_string).charAt(0)=='0')
//                            {
//                                holder.et_dispatch_amount_selector.setText(et_current_string.substring(1));
//                            }


                    if (current_num != old_num) {
                        if (current_num <= stocks.get(pos).getAmount()) {
                            stocks.get(pos).setSelected_amount(current_num);
                        } else {
                            stocks.get(pos).setSelected_amount(stocks.get(pos).getAmount());
                            holder.et_dispatch_amount_selector.setText(stocks.get(pos).getAmount() + "");
                            current_num = stocks.get(pos).getAmount();
                        }
                        MyWareHouse.totalSelectedAmount -= old_num;
                        MyWareHouse.totalSelectedAmount += current_num;
                        tv_total_selected_amount.setText(MyWareHouse.totalSelectedAmount + "");
                    }


                }catch (NumberFormatException e)
                {
                    holder.et_dispatch_amount_selector.setText("0");
                }
            }
        });








        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Ids are" + v.getId(), holder.et_dispatch_amount_selector.getId() + "");
                Log.d("Clicked", "cv");
                if (MyWareHouse.isDispatchedProcessStarted) {

                    if (stocks.get(temp_pos).getIsSelected()) {
                        Log.d("selected", "inside if");
                        temp_holder.cv.setBackgroundColor(Color.WHITE);
                        temp_holder.linearLayout.setBackgroundColor(Color.WHITE);
                        MyWareHouse.totalSelectedAmount -= stocks.get(temp_pos).getSelected_amount();
                        tv_total_selected_amount.setText(MyWareHouse.totalSelectedAmount + "");
                        stocks.get(temp_pos).setSelected_amount(0);
                        stocks.get(temp_pos).setIsSelected(false);
                        MyWareHouse.isSelectedCount -= 1;

                        holder.et_dispatch_amount_selector.setFocusable(false);
                        holder.et_dispatch_amount_selector.setFocusableInTouchMode(false);
                        holder.et_dispatch_amount_selector.setText("0");


//                        if (MyWareHouse.isSelectedCount == 0) {
//                            MyWareHouse.isDispatchedProcessStarted = false;
//                            MyWareHouse.totalDispatchingAmount = 0;
//                            MyWareHouse.totalSelectedAmount = 0;
//                            MyWareHouse.DispatchingCropName = "";
//                            notifyDataSetChanged();
//
//                        }
                    } else {
                        Log.d("unselected", "inside else");
                        temp_holder.cv.setBackgroundColor(Color.LTGRAY);
                        temp_holder.linearLayout.setBackgroundColor(Color.LTGRAY);
                        stocks.get(temp_pos).setSelected_amount(0);
                        stocks.get(temp_pos).setIsSelected(true);
                        MyWareHouse.isSelectedCount += 1;

                        holder.et_dispatch_amount_selector.setFocusable(true);
                        holder.et_dispatch_amount_selector.setFocusableInTouchMode(true);
                        holder.et_dispatch_amount_selector.setText("0");
//                        holder.et_dispatch_amount_selector.setTextIsSelectable(true);
                        Log.d("status", holder.et_dispatch_amount_selector.isTextSelectable() + "");
                        Log.d("status", holder.et_dispatch_amount_selector.isClickable() + "");
                        Log.d("status", holder.et_dispatch_amount_selector.isFocusable() + "");

                    }
                }
            }
        });


        Log.d("Onbindvwhldr compl", stocks.get(pos).getCropname());

    }

    @Override
    public int getItemCount() {
        Log.d("getitemcount called", stocks.size() + "");
        return stocks.size();
    }

    public void FinalizeDispatch()
    {


        final ProgressDialog progressDialog = new ProgressDialog(MyWareHouse.mywarehousecontext.getApplicationContext());
        Log.d("context in finalize fun", MyWareHouse.mywarehousecontext.toString());

        final long load_time_start=System.currentTimeMillis();
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setMessage(MyWareHouse.mywarehousecontext.getString(R.string.finalize_stock_dispatch_progress_dialog_message));

//        progressDialog.show();

        Thread temp_timer_thread=new Thread(new Runnable() {
            @Override
            public void run()
            {
                while(progressDialog.isShowing() && System.currentTimeMillis()-load_time_start<5000)
                {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (progressDialog.isShowing() && System.currentTimeMillis()-load_time_start>5000)
                {
                    progressDialog.dismiss();
                    showToast(MyWareHouse.mywarehousecontext.getString(R.string.problem_in_loading_message));
                }
            }
        });

        temp_timer_thread.start();


        if(MyWareHouse.totalSelectedAmount==MyWareHouse.totalDispatchingAmount)
        {
            JSONArray final_dispatch_array=new JSONArray();
            for(int i=0;i<stocks.size();++i)
            {
                StockList temp_stocklist_item = stocks.get(i);
                JSONObject temp_j = new JSONObject();
                try {
                    temp_j.put("StockName", temp_stocklist_item.getStockname());
                    temp_j.put("DispatchedAmount", temp_stocklist_item.getSelected_amount() + "");
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                final_dispatch_array.put(temp_j);
            }



            if(MainActivity.ConnectedToNetwork==true)
            {

                final StorageDBHandler db=new StorageDBHandler(MyWareHouse.mywarehousecontext);

                JSONObject j;
                j = new JSONObject();
                try {
                    j.put("Email",MainActivity.Global_Email_Id);
                    j.put("WareHouseName",MyWareHouse.warehousename);
                    j.put("Final_Dispatch_Array",final_dispatch_array);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String url = MainActivity.ServerIP + "/finalizedispatch/";

                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, j, new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        // the response is already constructed as a JSONObject!
                        Log.d("Onresponse", "yes");
                        try
                        {
                            Log.d("response of dispatch",response.toString());
                            String response_of_dispatch = response.getString("Android");
                            if(response_of_dispatch.equals("Valid"))
                            {

                                for (int i = 0; i < stocks.size(); ++i)
                                {
                                    StockList temp_stocklist_item = stocks.get(i);

                                    if (temp_stocklist_item.getIsSelected())
                                    {
                                        temp_stocklist_item.setAmount(temp_stocklist_item.getAmount() - temp_stocklist_item.getSelected_amount());

                                        if (temp_stocklist_item.getAmount() == 0)
                                        {

                                            db.deletestock(MyWareHouse.warehousename,temp_stocklist_item.getStockname());
                                            stocks.remove(i);

                                        }
                                        else
                                        {
                                            temp_stocklist_item.setIsSelected(false);
                                            temp_stocklist_item.setSelected_amount(0);
                                            db.updateStockAmount(MyWareHouse.warehousename,temp_stocklist_item.getStockname(),temp_stocklist_item.getAmount()+"");
                                        }
                                    }
                                }

                                progressDialog.dismiss();
                                notifyDataSetChanged();

                                MyWareHouse.DispatchingCropName="";
                                MyWareHouse.totalDispatchingAmount=0;
                                MyWareHouse.isSelectedCount=0;
                                MyWareHouse.totalSelectedAmount=0;
                                MyWareHouse.isDispatchedProcessStarted=false;

//                                fab_add_stock.setVisibility(View.VISIBLE);
//                                fab_dispatch_stock.setVisibility(View.VISIBLE);
                                rl_fab.setVisibility(View.VISIBLE);
                                ll_dispatch_finalizer.setVisibility(View.GONE);
                                tv_total_selected_amount.setText("0");
                                tv_to_dispatch.setText("0");



                            }
                            else
                            {
                                if(progressDialog.isShowing())
                                {
                                    progressDialog.dismiss();
                                }
                                Toast.makeText(MyWareHouse.mywarehousecontext, "The selected stock(s) cannot be dispatched", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        error.printStackTrace();
                    }
                });


                MainActivity.getInstance().addToRequestQueue(jsonRequest);
            }
            else
            {
                if(progressDialog.isShowing())
                {
                    progressDialog.dismiss();
                }
                Toast.makeText(MyWareHouse.mywarehousecontext, "No Internet Connection", Toast.LENGTH_LONG).show();
            }


        }
        else
        {
            if(progressDialog.isShowing())
            {
                progressDialog.dismiss();
            }
            Toast.makeText(MyWareHouse.mywarehousecontext, "Total selected amount not matching with To-Dispatch amount!!!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void startDispatch(String dispatching_crop_name,int to_dispatch_amount,int total_selected_amount,int selected_count)
    {
//        fab_add_stock.setVisibility(View.GONE);
//        fab_dispatch_stock.setVisibility(View.GONE);


        MyWareHouse.isDispatchedProcessStarted = true;
        MyWareHouse.DispatchingCropName = dispatching_crop_name;
        MyWareHouse.totalDispatchingAmount = to_dispatch_amount;
        MyWareHouse.totalSelectedAmount = total_selected_amount;
        MyWareHouse.isSelectedCount = selected_count;

        notifyDataSetChanged();




        rl_fab.setVisibility(View.VISIBLE);
        ll_dispatch_finalizer.setVisibility(View.VISIBLE);

        tv_to_dispatch.setText(MyWareHouse.totalDispatchingAmount + "");
        tv_total_selected_amount.setText(MyWareHouse.totalSelectedAmount+"");

        Log.d("selected amount", MyWareHouse.totalSelectedAmount + "");
        Log.d("dispatching amount",MyWareHouse.totalDispatchingAmount+"");
        Log.d("count",MyWareHouse.isSelectedCount+"");

    }


    public static class StockViewHolder extends RecyclerView.ViewHolder
    {
        CardView cv;
        TextView stockname,cropname,amount;
        LinearLayout linearLayout;
        EditText et_dispatch_amount_selector;
        int position=-1;

        StockViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.stocklistcv);
            stockname = (TextView)itemView.findViewById(R.id.stock_list_name);
            cropname = (TextView)itemView.findViewById(R.id.crop_name);
            amount = (TextView)itemView.findViewById(R.id.amount);
            et_dispatch_amount_selector=(EditText)itemView.findViewById(R.id.et_dispatch_amount_selector);
            linearLayout = (LinearLayout)cv.getParent();

            cv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    show_stock_info(v, stockname.getText().toString());
                    Log.d("The pos is",position+"");
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

    public void showToast(final String toast)
    {
        ((Activity)MyWareHouse.mywarehousecontext).runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(MyWareHouse.mywarehousecontext, toast, Toast.LENGTH_LONG).show();
            }
        });
    }


}
