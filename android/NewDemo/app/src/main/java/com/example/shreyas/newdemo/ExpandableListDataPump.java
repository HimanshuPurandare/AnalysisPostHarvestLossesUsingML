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

import org.json.JSONArray;
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

    static String cropname = "",farmarea="",sowingdatestart="",sowingdateend="",seedtype="",hwkitid="";
    public static HashMap<String, List<String>> getData()
    {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        farmdata = new ArrayList<String>();
        weatherdata = new ArrayList<String>();
        predictions = new ArrayList<String>();
        notifications = new ArrayList<String>();


        FetchfarmData();
        FetchNotificationData();
        FetchPredictionsData();
        FetchWeatherData();


        expandableListDetail.put("FARM DATA", farmdata);
        expandableListDetail.put("WEATHER DATA", weatherdata);
        expandableListDetail.put("PREDICTIONS", predictions);
        expandableListDetail.put("NOTIFICATIONS", notifications);
        return expandableListDetail;
    }

    private static void FetchWeatherData()
    {
        if (MainActivity.ConnectedToNetwork) {

            JSONObject j = new JSONObject();
            try {

                j.put("UserID",MainActivity.Global_Email_Id);
                j.put("FarmName",MyFarm.farmname);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String url = MainActivity.ServerIP + "/getweatherdata/";

            JsonObjectRequest jsonRequest = new JsonObjectRequest
                    (Request.Method.POST, url, j, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // the response is already constructed as a JSONObject!
                            try {
                                response = response.getJSONObject("Android");
                                //String signinresult = response.getString("Result");
                                //if (signinresult.equals("Valid")) {
                                JSONObject s = response.getJSONObject("current_weather");

                                String a = s.getString("Humidity");
                                String b = s.getString("Soil Moisture");
                                String c = s.getString("Temperature");

                                weatherdata.add("Temperature : "+c+"°C");
                                weatherdata.add("Humidity : "+a+"%");
                                weatherdata.add("Soil Moisture"+b+"%");

                                MyFarm.expandableListAdapter.notifyDataSetChanged();

                                //}
                                //else {



                                //}
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

    private static void FetchPredictionsData()
    {
        if (MainActivity.ConnectedToNetwork) {

            JSONObject j = new JSONObject();
            try {

                j.put("UserID",MainActivity.Global_Email_Id);
                j.put("FarmName",MyFarm.farmname);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            String url = MainActivity.ServerIP + "/getfarmpredictions/";

            JsonObjectRequest jsonRequest = new JsonObjectRequest
                    (Request.Method.POST, url, j, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // the response is already constructed as a JSONObject!
                            try {
                                response = response.getJSONObject("Android");
                                String signinresult = response.getString("Result");
                                if (signinresult.equals("Valid")) {

                                    String a = response.getString("HTP");
                                    predictions.add(a);
                                    Log.d("HT",a);
                                    predictions.add(response.getString("Disease"));


                                    MyFarm.expandableListAdapter.notifyDataSetChanged();

                                }
                                else {



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

    private static void FetchfarmData() {
        if (MainActivity.ConnectedToNetwork) {

            JSONObject j = new JSONObject();
            try {

                j.put("UserID",MainActivity.Global_Email_Id);
                j.put("FarmName",MyFarm.farmname);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            String url = MainActivity.ServerIP + "/getfarminfo/";

            JsonObjectRequest jsonRequest = new JsonObjectRequest
                    (Request.Method.POST, url, j, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // the response is already constructed as a JSONObject!
                            try {
                                response = response.getJSONObject("Android");
                                String signinresult = response.getString("Result");
                                if (signinresult.equals("Valid")) {

                                    cropname = response.getString("AddFarmCrop");
                                    farmarea= response.getString("AddFarmArea");
                                    sowingdatestart= response.getString("AddFarmStartDate");
                                    sowingdateend= response.getString("AddFarmEndDate");
                                    seedtype= response.getString("AddFarmCropType");
                                    hwkitid= response.getString("AddFarmHWID");

                                    farmdata.add("Crop name : " + cropname);
                                    farmdata.add("Seed Type : " + seedtype);
                                    farmdata.add("Farm area : " + farmarea);

                                    sowingdateend.replace("/", "-");
                                    sowingdatestart.replace("/","-");

                                    farmdata.add("Period of Sowing : " + sowingdatestart +" to "+sowingdateend);
                                    farmdata.add("Hardware ID : " + hwkitid);

                                    MyFarm.expandableListAdapter.notifyDataSetChanged();

                                }
                                else {



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


    private static void FetchNotificationData() {
        if (MainActivity.ConnectedToNetwork) {

            JSONObject j = new JSONObject();
            try {

                j.put("UserID",MainActivity.Global_Email_Id);
                j.put("Farmname",MyFarm.farmname);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            String url = MainActivity.ServerIP + "/getnotifications/";

            JsonObjectRequest jsonRequest = new JsonObjectRequest
                    (Request.Method.POST, url, j, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // the response is already constructed as a JSONObject!
                            try {
                                response = response.getJSONObject("Android");
                                {
                                    JSONArray j=new JSONArray();
                                    j=response.getJSONArray("list");
                                    JSONObject u=new JSONObject();
                                    for(int i=0;i<j.length();i++)
                                    {
                                        u=j.getJSONObject(i);
                                        notifications.add(u.getString("Message")+" :  "+u.getString("date"));
                                        Log.d("data",u.toString());
                                    }

                                    MyFarm.expandableListAdapter.notifyDataSetChanged();

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
