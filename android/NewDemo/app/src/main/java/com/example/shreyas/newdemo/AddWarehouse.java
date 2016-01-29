package com.example.shreyas.newdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;


public class AddWarehouse extends AppCompatActivity
{

    public static boolean location_set;

    public static String URL;

    public static String wh_name,wh_lat,wh_long;
    EditText et_name;
    Button btn_addwh;
    TextView te1;
    ImageView imw;

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_warehouse);

        setupToolbar();

        te1 = (TextView) findViewById(R.id.click_here_warehouse);

        imw = (ImageView)findViewById(R.id.warehouse_location_display);
        imw.setImageResource(R.drawable.location);


        imw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                te1.setText("");
                setlocation();
            }
        });


        et_name=(EditText)findViewById(R.id.warehousename);
        btn_addwh=(Button)findViewById(R.id.addwarehouse);
        btn_addwh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptAddWarehouse();


            }
        });


        Log.d("The location_set variable",""+location_set);

        if(location_set) {
            te1.setText("");

            Intent intent = getIntent();
            //ArrayList<Double> t = intent.getDoubleArrayExtra("list");
            String s = intent.getStringExtra("list");
                Log.d("Locations", s);

            String[] values = s.split(",");
            Log.d("The values stringlist",values.toString());



            String url = "https://maps.googleapis.com/maps/api/staticmap?&size=600x600&maptype=roadmap&sensor=false";
            url = url + "&markers=color:red%7Clabel:S%7C"+values[0].replaceAll("\\[lat/lng: \\(","")+","+values[1].replaceAll("\\)","");
            for(int i=2;i<values.length;i++)
            {
                url = url + "&markers=color:red%7Clabel:S%7C"+values[i].replaceAll(" lat/lng: \\(","")+","+values[++i].replaceAll("\\)","");
            }
            url = url.replaceAll("\\]","");
            url = url + "&path=weight:5%7Cfillcolor:orange%7Ccolor:red";
            url = url + "%7C" +values[0].replaceAll("\\[lat/lng: \\(","")+","+values[1].replaceAll("\\)","");
            for(int i=2;i<values.length;i++)
            {
                url = url + "%7C"+values[i].replaceAll(" lat/lng: \\(","")+","+values[++i].replaceAll("\\)","");
            }
            url = url.replaceAll("\\]","");
            url = url + "%7C" +values[0].replaceAll("\\[lat/lng: \\(","")+","+values[1].replaceAll("\\)","");
            URL = url;
            Log.d("URL",URL);
            new DownloadImageTask(imw).execute(url);
            //new DownloadImageTask(im1).execute("https://maps.googleapis.com/maps/api/staticmap?&size=600x600&maptype=roadmap&sensor=false&markers=color:blue%7Clabel:S%7C40.702147,-74.015794&markers=color:green%7Clabel:G%7C40.711614,-74.012318&markers=color:red%7Clabel:C%7C40.718217,-73.998284&path=weight:5%7Cfillcolor:orange%7Ccolor:red%7C40.702147,-74.015794%7C40.711614,-74.012318%7C40.718217,-73.998284%7C40.702147,-74.015794");
        }















    }

    void setupToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add WareHouse");
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
                j.put("AddWarehouseURL",URL);

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
                                    AddWarehouse.location_set=false;
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


    void setlocation()
    {


        {
            Intent i = new Intent(AddWarehouse.this,MyMapActivity.class);
            i.putExtra("ActivityName", "AddWarehouse");
            startActivity(i);
            AddWarehouse.location_set=false;
            finish();
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }



    @Override
    public void onBackPressed() {
        Intent i = new Intent(AddWarehouse.this,MainActivity.class );
        startActivity(i);
        AddWarehouse.location_set=false;
        finish();
    }



}

