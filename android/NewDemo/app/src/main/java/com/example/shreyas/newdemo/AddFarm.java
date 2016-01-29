package com.example.shreyas.newdemo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.*;

public class AddFarm extends AppCompatActivity {

    private Toolbar toolbar;
    private Spinner spinner1;
    private EditText f_name,f_area,hd_id;
    public static  Spinner spinner2;

    private ImageButton ib,ib1;
    private Calendar cal,cal1;
    private int day,day1;
    private int month,month1;
    private int year,year1;
    private EditText et,et1;
    private ImageView im1;

    private TextView te;

    public static String farm_name;
    private int dateflag=0;
    public static String farm_area;
    public static String start_date,end_date;
    public static String hwid;
    public static String URL;

    public static boolean location_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_farm);

        setupToolbar();

        im1 = (ImageView)findViewById(R.id.location_display);
        im1.setImageResource(R.drawable.location);

        te = (TextView) findViewById(R.id.click_here);


        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                te.setText("");
                setlocation();
            }
        });

        if(location_set) {
            te.setText("");

            Intent intent = getIntent();
            //ArrayList<Double> t = intent.getDoubleArrayExtra("list");
            String s = intent.getStringExtra("list");
            Log.d("Locations",s);

            String[] values = s.split(",");

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
            new DownloadImageTask(im1).execute(url);
            //new DownloadImageTask(im1).execute("https://maps.googleapis.com/maps/api/staticmap?&size=600x600&maptype=roadmap&sensor=false&markers=color:blue%7Clabel:S%7C40.702147,-74.015794&markers=color:green%7Clabel:G%7C40.711614,-74.012318&markers=color:red%7Clabel:C%7C40.718217,-73.998284&path=weight:5%7Cfillcolor:orange%7Ccolor:red%7C40.702147,-74.015794%7C40.711614,-74.012318%7C40.718217,-73.998284%7C40.702147,-74.015794");
        }


        f_name = (EditText) findViewById(R.id.farm_name);
        f_area= (EditText) findViewById(R.id.area_farm);
        hd_id= (EditText) findViewById(R.id.hd_id);


        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);



        List<String> list = new ArrayList<String>();
        list.add(getString(R.string.WheatString));
        list.add(getString(R.string.RiceString));
        list.add(getString(R.string.OnionString));

        CustomOnItemSelectedListener.wheatlist = new ArrayList<String>();
        CustomOnItemSelectedListener.wheatlist.add(getString(R.string.AjantaString));
        CustomOnItemSelectedListener.wheatlist.add(getString(R.string.ArjunString));
        CustomOnItemSelectedListener.wheatlist.add(getString(R.string.ParabhaniString));
        CustomOnItemSelectedListener.wheatlist.add(getString(R.string.MalvikaString));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line,list);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_dropdown_item_1line);

        spinner1.setAdapter(dataAdapter);


        CustomOnItemSelectedListener.dataAdapter1 = new ArrayAdapter<String>
                (this, android.R.layout.simple_expandable_list_item_1,CustomOnItemSelectedListener.wheatlist);

        CustomOnItemSelectedListener.dataAdapter1.setDropDownViewResource
                (android.R.layout.simple_expandable_list_item_1);

        spinner2.setAdapter(CustomOnItemSelectedListener.dataAdapter1);

        addListenerOnSpinnerItemSelection();


        ib = (ImageButton) findViewById(R.id.imageButton1);
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        et = (EditText)findViewById(R.id.startdate);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(0);
                dateflag=0;
            }
        });


        ib1 = (ImageButton) findViewById(R.id.imageButton2);
        cal1 = Calendar.getInstance();
        day1 = cal1.get(Calendar.DAY_OF_MONTH);
        month1 = cal1.get(Calendar.MONTH);
        year1 = cal1.get(Calendar.YEAR);
        et1 = (EditText)findViewById(R.id.enddate);
        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(0);
                dateflag=1;
            }
        });



        Button addfarm = (Button) findViewById(R.id.add_farm);
        addfarm.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                Log.d("Clicked", "btn clicked");
                attemptAddFarm();
            }
        });

    }

    void setlocation()
    {


        {
            Intent i = new Intent(AddFarm.this,MyMapActivity.class);
            i.putExtra("ActivityName","AddFarm");
            startActivity(i);
            AddFarm.location_set=false;
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

    void setupToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Field");
        //getSupportActionBar().setSubtitle("Pune");
        toolbar.setTitleTextColor(getResources().getColor(R.color.Text_Icon));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.LightPrimaryColor));
    }



    public void addListenerOnSpinnerItemSelection(){

        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        spinner2.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    public void attemptAddFarm()
    {

        Log.d("Inside adfarm","inside");
        farm_name = f_name.getText().toString();
        farm_area = f_area.getText().toString();
        CustomOnItemSelectedListener.crop=spinner1.getSelectedItem().toString();
        CustomOnItemSelectedListener.type_of_crop=spinner2.getSelectedItem().toString();
        start_date=et.getText().toString();
        end_date=et1.getText().toString();
        hwid=hd_id.getText().toString();

        if(MainActivity.ConnectedToNetwork)
        {

            JSONObject j = new JSONObject();
            try {
                j.put("AddFarmUID",MainActivity.Global_Email_Id);
                j.put("AddFarmName", farm_name);
                j.put("AddFarmArea", farm_area);
                j.put("AddFarmCrop", CustomOnItemSelectedListener.crop);
                j.put("AddFarmCropType", CustomOnItemSelectedListener.type_of_crop);
                j.put("AddFarmStartDate", start_date);
                j.put("AddFarmEndDate", end_date);
                j.put("AddFarmHWID", hwid);
                j.put("AddFarmURL",URL);

            } catch (JSONException e)
            {
                e.printStackTrace();
            }

            String url = MainActivity.ServerIP + "/addfarm/";
            JsonObjectRequest jsonRequest = new JsonObjectRequest
                    (Request.Method.POST, url, j, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // the response is already constructed as a JSONObject!
                            try {
                                response = response.getJSONObject("Android");
                                String signinresult = response.getString("Result");
                                if (signinresult.equals("Valid")) {
                                    Intent i = new Intent(AddFarm.this, MainActivity.class);


                                    startActivity(i);
                                    AddFarm.location_set=false;
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
    @Deprecated
    protected Dialog onCreateDialog(int id)
    {
        return new DatePickerDialog(this, datePickerListener, year, month, day);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener()
    {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay)
        {
            if(dateflag==0)
            {
                et.setText(selectedDay + "-" + (selectedMonth + 1) + "-"
                        + selectedYear);

            }
            else
            {
                et1.setText(selectedDay + "-" + (selectedMonth + 1) + "-"
                        + selectedYear);
            }
        }
    };

    @Override
    public void onBackPressed() {
        Intent i = new Intent(AddFarm.this,MainActivity.class );
        startActivity(i);
        AddFarm.location_set=false;
        finish();
    }
}

