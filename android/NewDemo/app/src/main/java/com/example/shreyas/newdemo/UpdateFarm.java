package com.example.shreyas.newdemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateFarm extends AppCompatActivity {
    private String farmname, cropname, seedtype, farmarea, sowingdate, harvestingdate, hwkitid;

    Toolbar toolbar;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_farm);

        farmname = getIntent().getStringExtra("FarmName");
        Log.d("FarmName", farmname);

        setupToolbar("Update Farm Info");

        FetchfarmData();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void FetchfarmData() {
        if (MainActivity.ConnectedToNetwork) {

            JSONObject j = new JSONObject();
            try {
                j.put("UserName", MainActivity.Global_User_Name);
                j.put("FarmName", farmname);

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

                                } else {

                                    Toast.makeText(UpdateFarm.this, "Error in Fetching Data", Toast.LENGTH_LONG).show();

                                }
                            } catch (JSONException e) {
                                Toast.makeText(UpdateFarm.this, "Exception", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();

                            Toast.makeText(UpdateFarm.this, "Connection Error", Toast.LENGTH_LONG).show();
                        }
                    });
            MainActivity.getInstance().addToRequestQueue(jsonRequest);
        } else {
            Log.d("Login activity check", MainActivity.ConnectedToNetwork + "");
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }


    }

    void setupToolbar(String title) {
        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        toolbar.setTitleTextColor(getResources().getColor(R.color.Text_Icon));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.LightPrimaryColor));
    }
}

