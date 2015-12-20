package com.example.shreyas.newdemo;

/**
 * Created by shreyas on 12/20/15.
 */

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump
{
    static List<String> farmdata;
    static List<String> weatherdata;
    static List<String> predictions;
    static List<String> notifications;

    static String cropname = "",farmarea="",sowingdate="",harvestingdate="",seedtype="",hwkitid="";
    public static HashMap<String, List<String>> getData()
    {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        farmdata = new ArrayList<String>();
        weatherdata = new ArrayList<String>();
        predictions = new ArrayList<String>();
        notifications = new ArrayList<String>();

        farmdata.add("Crop name : "+ cropname);
        farmdata.add("Farm area : "+farmarea);


        weatherdata.add("hi");
        weatherdata.add("hiii");

        weatherdata.add("hi");
        weatherdata.add("hiii");
        weatherdata.add("hi");
        weatherdata.add("hiii");
        weatherdata.add("hi");
        weatherdata.add("hiii");




        predictions.add("hi");


        notifications.add("hi");
        notifications.add("hiii");

        expandableListDetail.put("FARM DATA", farmdata);
        expandableListDetail.put("WEATHER DATA", weatherdata);
        expandableListDetail.put("PREDICTIONS", predictions);
        expandableListDetail.put("NOTIFICATIONS", notifications);
        return expandableListDetail;
    }

    private void FetchfarmData() {
        if (MainActivity.ConnectedToNetwork) {

            JSONObject j = new JSONObject();
            try {
                j.put("UserName", MainActivity.Global_User_Name);
                j.put("FarmName", MyFarm.farmname);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            String url = MainActivity.ServerIP + "/getupdatefarmdata/";

            JsonObjectRequest jsonRequest = new JsonObjectRequest
                    (Request.Method.POST, url, j, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // the response is already constructed as a JSONObject!
                            try {
                                response = response.getJSONObject("Android");
                                String signinresult = response.getString("Result");
                                if (signinresult.equals("Valid")) {

                                    cropname = response.getString("FarmName");
                                    farmarea = response.getString("FarmArea");
                                    sowingdate = response.getString("SowingDate");
                                    harvestingdate = response.getString("HarvestingDate");
                                    seedtype = response.getString("Seedtype");
                                    hwkitid = response.getString("HwKitID");

                                    MyFarm.expandableListAdapter.notifyDataSetChanged();

                                } else {


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
        } else {
            Log.d("Login activity check", MainActivity.ConnectedToNetwork + "");
        }


    }
}
