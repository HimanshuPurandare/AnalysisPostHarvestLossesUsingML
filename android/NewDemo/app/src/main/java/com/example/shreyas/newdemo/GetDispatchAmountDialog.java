package com.example.shreyas.newdemo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by niranjan on 23/1/16.
 */
public class GetDispatchAmountDialog extends Dialog implements View.OnClickListener
{
    private Dialog d1;

    private EditText et_get_dispatch_amount_dialog;
    private Spinner dialog_crop_spinner;
    private Button btn_proceed,btn_cancel;
    private WHAdapter whAdapter;
    onDispatchStarted ondispatchstarted;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        setContentView(R.layout.get_dispatch_amount_dialog);
        Log.d("Content view", "set for dialog");
        this.setTitle(this.getContext().getString(R.string.title_get_dispatch_amount_dialog));

        et_get_dispatch_amount_dialog=(EditText)findViewById(R.id.dialog_accept_dispatch_amount);
        dialog_crop_spinner=(Spinner)findViewById(R.id.dialog_dispatch_crop_spinner);

        ondispatchstarted=(onDispatchStarted)whAdapter;



        List<String> list = new ArrayList<String>();
        list.add(this.getContext().getString(R.string.WheatString));
        list.add(this.getContext().getString(R.string.RiceString));
        list.add(this.getContext().getString(R.string.OnionString));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this.getContext(), android.R.layout.simple_dropdown_item_1line,list);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_dropdown_item_1line);

        dialog_crop_spinner.setAdapter(dataAdapter);


        btn_proceed=(Button)findViewById(R.id.btn_ok_dialog_dispatch);
        btn_cancel=(Button)findViewById(R.id.btn_cancel_dialog_dispatch);

        btn_proceed.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);



        d1=this;
        Log.d("End of on create", "----");

    }

    public GetDispatchAmountDialog(Context context,WHAdapter whAdapter)
    {
        super(context);
        this.whAdapter=whAdapter;




    }

    public interface onDispatchStarted
    {
        public void startDispatch(String cropname,int dispatchamount,int selectedamount,int selectedcount);
    }

    @Override
    public void onClick(View v)
    {



        Log.d("Inside", "onclick");
        if (v.getId() == R.id.btn_ok_dialog_dispatch)
        {
            try {
                final int to_dispatch_amount = Integer.parseInt(et_get_dispatch_amount_dialog.getText().toString());

                Log.d("Inside ok button", "clicked");
                if (MainActivity.ConnectedToNetwork) {

                    JSONObject j = new JSONObject();
                    try {
                        j.put("DispatcherUID", MainActivity.Global_Email_Id);
                        j.put("WareHouseName", MyWareHouse.warehousename);
                        j.put("DispatchCropName", dialog_crop_spinner.getSelectedItem().toString());
                        j.put("DispatchAmount", et_get_dispatch_amount_dialog.getText().toString() + "");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String url = MainActivity.ServerIP + "/getdispatchsequence/";
                    JsonObjectRequest jsonRequest = new JsonObjectRequest
                            (Request.Method.POST, url, j, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {

                                    // the response is already constructed as a JSONObject!
                                    try {
                                        JSONArray dispatch_sequence = response.getJSONArray("Android");
                                        Log.d("Received", dispatch_sequence.toString());

                                        int total_selected_amount = 0;

                                        for (int i = 0; i < dispatch_sequence.length(); ++i) {
                                            JSONObject temp = dispatch_sequence.getJSONObject(i);
                                            String temp_stockname = temp.getString("StockName");

                                            int temp_dispatch_amount = Integer.parseInt(temp.getString("DispatchAmount"));

                                            for (int j = 0; j < whAdapter.stocks.size(); ++j) {
                                                StockList temp_stocklist_item = whAdapter.stocks.get(j);
                                                Log.d("going to check " + temp_stockname
                                                        , temp_stocklist_item.getStockname());
                                                if (temp_stocklist_item.getStockname().equals(temp_stockname) == true) {
                                                    Log.d("Entered in", "If");

                                                    temp_stocklist_item.setIsSelected(true);
                                                    temp_stocklist_item.setSelected_amount(temp_dispatch_amount);
                                                    total_selected_amount += temp_dispatch_amount;

                                                    Log.d("Processed", temp_stocklist_item.getStockname());
                                                    break;
                                                }
                                            }

                                        }

                                        ondispatchstarted.startDispatch(dialog_crop_spinner.getSelectedItem().toString(),to_dispatch_amount,total_selected_amount,dispatch_sequence.length());

                                        d1.dismiss();
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

                } else {
                    Toast.makeText(this.getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                }

            }
            catch (NumberFormatException e)
            {
                et_get_dispatch_amount_dialog.setText("0");
            }
        } else if (v.getId() == R.id.btn_cancel_dialog_dispatch) {
            Log.d("Inside cancel button", "clicked");
            this.dismiss();
        }

    }

}
