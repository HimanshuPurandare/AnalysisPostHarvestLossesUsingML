package com.example.shreyas.newdemo;

/**
 * Created by shreyas on 12/20/15.
 */

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump
{
    static List<String> farmdata;
    static List<String> weatherdata;
    static List<String> predictions;
    static List<String> notifications;
    static List<String>graphs;

    public static float[] Min,Max,Hum;
    public static String[] date;
    public static String lattitude="19.1336",longitude="72.9154";


    static String cropname = "",farmarea="",sowingdatestart="",sowingdateend="",seedtype="",hwkitid="";
    public static HashMap<String, List<String>> getData()
    {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        farmdata = new ArrayList<String>();
        weatherdata = new ArrayList<String>();
        predictions = new ArrayList<String>();
        notifications = new ArrayList<String>();
        graphs=new ArrayList<String>();
        graphs.add("Temp");
;

        FetchfarmData();
        FetchNotificationData();
        FetchPredictionsData();
        FetchWeatherData();
        FetchForecastData();

        expandableListDetail.put("Farm Data", farmdata);
        expandableListDetail.put("Current Farm Status" , weatherdata);
        expandableListDetail.put("PREDICTIONS", predictions);
        expandableListDetail.put("NOTIFICATIONS", notifications);
        expandableListDetail.put("Temperature Forecast", graphs);
        expandableListDetail.put("Humidity Forecast",graphs);

        return expandableListDetail;
    }

    private static void FetchForecastData()
    {

        Min=new float[5];
        Max=new float[5];
        Hum=new float[5];
        date=new String[5];

        for(int date_cnt=0;date_cnt<5;date_cnt++)
        {

            Calendar c = Calendar.getInstance();
            Log.d("Time.....", c.getTimeZone().toString());
            Date dateobj = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM");

            c.setTime(dateobj);
            c.add(Calendar.DATE, date_cnt - 2);
            Log.d("date is......", dateFormat.format(c.getTime()));
            Log.d("day is.......", dateFormat1.format(c.getTime()));

            String temp_date = dateFormat.format(c.getTime());
            String temp_day = (dateFormat1.format(c.getTime()));


            SetData(temp_date,temp_day,date_cnt);



        }



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

                                weatherdata.add(MainActivity.getInstance().getString(R.string.TemperatureString)+" : "+c+"°C");
                                weatherdata.add(MainActivity.getInstance().getString(R.string.HumidityString)+" : "+a+"%");
                                weatherdata.add(MainActivity.getInstance().getString(R.string.SoilMoistureString)+" : "+b+"%");

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

                                    farmdata.add(MainActivity.getInstance().getString(R.string.CropNameString) + " : " + cropname);
                                    farmdata.add(MainActivity.getInstance().getString(R.string.SeedTypeString) + " : " + seedtype);
                                    farmdata.add(MainActivity.getInstance().getString(R.string.FarmAreaString) + " : " + farmarea);

                                    sowingdateend.replace("/", "-");
                                    sowingdatestart.replace("/", "-");

                                    farmdata.add(MainActivity.getInstance().getString(R.string.PeriodofSowingString)+" : " + sowingdatestart + " to " + sowingdateend);
                                    farmdata.add(MainActivity.getInstance().getString(R.string.HardwareIDString)+" : " + hwkitid);

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

    private  static void SetData(String temp_date,String temp_day1,int date_cnt1)
    {

        final int date_cnt=date_cnt1;
        final String temp_day=temp_day1;

        String url = "https://api.forecast.io/forecast/effdaeb7474c03015ad3f83872d83696/"+lattitude+","+longitude+","+temp_date+"T11:59:59";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            jsonObject = jsonObject.getJSONObject("daily");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            jsonObject = new JSONObject(jsonArray.get(0).toString());



                            Min[date_cnt]= (Float.parseFloat(jsonObject.getString("temperatureMin"))-32)*5/9;
                            Max[date_cnt]= (Float.parseFloat(jsonObject.getString("temperatureMax"))-32)*5/9;
                            Hum[date_cnt]=Float.parseFloat(jsonObject.getString("humidity"));
                            date[date_cnt]=temp_day;





                            Log.d("Min.......",jsonObject.getString("temperatureMin"));
                            Log.d("Min......",Min[date_cnt]+"");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });
        MainActivity.getInstance().addToRequestQueue(stringRequest);

    }





}
