package com.example.shreyas.newdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class AddWarehouse extends AppCompatActivity
{

    public static String wh_name;
    EditText et_name;
    Button btn_addwh;

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_warehouse);

        setupToolbar();

        et_name=(EditText)findViewById(R.id.warehousename);
        btn_addwh=(Button)findViewById(R.id.addwarehouse);
        btn_addwh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptAddWarehouse();


            }
        });



    }

    void setupToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Section");
        //getSupportActionBar().setSubtitle("Pune");
        toolbar.setTitleTextColor(getResources().getColor(R.color.Text_Icon));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.LightPrimaryColor));
    }


    private void attemptAddWarehouse()
    {
        wh_name=et_name.getText().toString();



        if(MainActivity.ConnectedToNetwork)
        {

            JSONObject j = new JSONObject();
            try {
                j.put("AddWarehouseUID",MainActivity.Global_Email_Id);
                j.put("AddWarehouseName", wh_name);

            } catch (JSONException e)
            {
                e.printStackTrace();
            }

            String url = MainActivity.ServerIP + "/addwarehouse/";
            JsonObjectRequest jsonRequest = new JsonObjectRequest
                    (Request.Method.POST, url, j, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // the response is already constructed as a JSONObject!
                            try {
                                response = response.getJSONObject("Android");
                                String signinresult = response.getString("Result");
                                if (signinresult.equals("Valid")) {
                                    Intent i = new Intent(AddWarehouse.this, MainActivity.class);


                                    startActivity(i);
                                    finish();
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
    public void onBackPressed() {
        Intent i = new Intent(AddWarehouse.this,MainActivity.class );
        startActivity(i);
        finish();
    }



}

