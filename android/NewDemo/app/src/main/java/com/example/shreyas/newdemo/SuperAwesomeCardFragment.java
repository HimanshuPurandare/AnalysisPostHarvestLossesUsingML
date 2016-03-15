package com.example.shreyas.newdemo;


import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
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
import java.util.Iterator;
import java.util.List;

public class SuperAwesomeCardFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private int position;

    MyFarmAdapter fadap;
    MyWareHouseAdapter wadap;


    List<General_info> gen_info;
    List<General_info> news;
    List<Farm_info> farmlist;
    List<WareHouse_Info> warehouselist;



    public SuperAwesomeCardFragment()
    {
        farmlist = new ArrayList<Farm_info>();
        warehouselist = new ArrayList<WareHouse_Info>();

        fadap = new MyFarmAdapter(farmlist);
        wadap = new MyWareHouseAdapter(warehouselist);
    }

    private void initializeData()
    {

        Log.d("Inside data i", "Inside Data Initi");
        gen_info = new ArrayList<>();
        gen_info.add(new General_info(getString(R.string.infotitlestring1),getString(R.string.infostring1), R.drawable.d));
        gen_info.add(new General_info(getString(R.string.infotitlestring2),getString(R.string.infostring2), R.drawable.e));
        gen_info.add(new General_info(getString(R.string.infotitlestring3),getString(R.string.infostring3), R.drawable.f));

//        gen_info.add(new General_info("Onion storage structure", "Modern onion storage structures have been so planned to aerate the onions from all sides.Site selected for onion storage structure should be well drained and should be easily accusable to good road", R.drawable.g));
//        gen_info.add(new General_info("Wheat Farming", "Wheat is the main cereal crop in India. The total area under the crop is about 29.8 million hectares in the country.", R.drawable.h));
//        gen_info.add(new General_info("Seasons of rice growing in the Maharashtra", "In Maharashtra state, rice crop is predominantly cultivated under rainfed conditions, i.e. Kharif. In Marathwada region area under rice comprises 90,000 ha which is completely drill and rainfed cultivation.", R.drawable.i));

    }

    private void initializeNews()
    {
        Log.d("Inside news intion", "News initial");
        news = new ArrayList<>();
        news.add(new General_info(getString(R.string.newstitlestring1), getString(R.string.newsstring1), R.drawable.a));
        news.add(new General_info(getString(R.string.newstitlestring2),getString(R.string.newsstring2), R.drawable.b));
        news.add(new General_info(getString(R.string.newstitlestring3),getString(R.string.newsstring3), R.drawable.c));
    }
    public void setfarms()
    {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        final long load_time_start=System.currentTimeMillis();
        progressDialog.isIndeterminate();
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setMessage(getString(R.string.farm_loading_progressdialog_message));
        progressDialog.show();
        Thread temp_timer_thread=new Thread(new Runnable() {
            @Override
            public void run()
            {
                while(progressDialog.isShowing() && System.currentTimeMillis()-load_time_start<10000)
                {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (progressDialog.isShowing() && System.currentTimeMillis()-load_time_start>10000)
                {
                    progressDialog.dismiss();
                    showToast(getString(R.string.problem_in_loading_message));

                }
            }
        });

        temp_timer_thread.start();


        farmlist.clear();
        Log.d("Connected to network", MainActivity.ConnectedToNetwork + "");
        if(MainActivity.ConnectedToNetwork==true)
        {

            JSONObject j;
            j = new JSONObject();
            try {
                j.put("Email",MainActivity.Global_Email_Id);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            String url = MainActivity.ServerIP + "/getfarms/";


            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, j, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    // the response is already constructed as a JSONObject!
                    Log.d("Onresponse", "yes");
                    try {
                        response = response.getJSONObject("Android");
                        //String fname = response.getString("AddFarmName");

                        JSONArray a = new JSONArray();
                        int l;
                        try {
                            a = response.getJSONArray("farmlist");
                            Log.d("response array ", a.toString());
                            l = a.length();
                            Log.d("response array ", l + "");
                        }
                        catch (Exception e)
                        {
                            l = 0;
                        }
                        for(int i=0;i<l;i++)
                        {
                            JSONObject temp = a.getJSONObject(i);
                            Log.d("Min and Max ...", temp.toString());

                            //farmlist.add(new Farm_info(temp.getString("FarmName"),temp.getString("MaxTemperature")+"/"+temp.getString("MinTemperature"),temp.getString("MaxHumidity")+"/"+temp.getString("MinHumidity"),temp.getString("MaxSM")+"/"+temp.getString("MinSM"),temp.getString("URL")));
                            farmlist.add(new Farm_info(temp.getString("FarmName"),"18"+"/"+"29","39"+"/"+"52","0"+"/"+"495",temp.getString("URL")));
                        }


                        fadap.notifyDataSetChanged();
                        progressDialog.dismiss();

                        if(l==0)
                        {
                            Toast.makeText(getActivity(),"No farms added",Toast.LENGTH_LONG).show();
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
            Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_LONG).show();
        }
    }

    public void setwarehouses()
    {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        final long load_time_start=System.currentTimeMillis();
        progressDialog.isIndeterminate();
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setMessage(getString(R.string.warehouse_loading_progressdialog_message));
        progressDialog.show();

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
                    showToast(getString(R.string.problem_in_loading_message));
                }
            }
        });

        temp_timer_thread.start();

        Log.d("Dialog shown", "as loading warehouses");
        Log.d("start time:", load_time_start + "");



        warehouselist.clear();
        Log.d("Connected to network", MainActivity.ConnectedToNetwork + "");



        final StorageDBHandler db=new StorageDBHandler(this.getContext());
        int wh_cnt=db.getWarehousesCount();
        Log.d("The count is",wh_cnt+"");


        if(wh_cnt==0)
        {
            if(MainActivity.ConnectedToNetwork==true)
            {
                db.deleteAll();

                JSONObject j;
                j = new JSONObject();
                try {
                    j.put("Email",MainActivity.Global_Email_Id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String url = MainActivity.ServerIP + "/getwarehouses/";


                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, j, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        Log.d("Onresponse", "yes");
                        try {
                            response = response.getJSONObject("Android");
                            //String fname = response.getString("AddFarmName");

                            JSONArray a = new JSONArray();

                            a = response.getJSONArray("AddWareHouseName");

                            int l = a.length();

                            for(int i=0;i<l;i++)
                            {
                                warehouselist.add(new WareHouse_Info(a.getString(i)));
                                db.addWarehouse(a.getString(i));
                            }



                            Log.d("Used data","from internet");

                            wadap.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                            {

                                progressDialog.dismiss();
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
                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
            }

        }
        else
        {
            List<String> whlist=db.getwarehouses();
            for(String wh:whlist)
            {
                warehouselist.add(new WareHouse_Info(wh));
            }
            Log.d("Used data","from db");
            if(progressDialog.isShowing())
            {
                progressDialog.dismiss();
            }
        }
    }





    public static SuperAwesomeCardFragment newInstance(int position) {
        SuperAwesomeCardFragment f = new SuperAwesomeCardFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt(ARG_POSITION);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

//        if (position==1)
//        {
//            View rootView = inflater.inflate(R.layout.a, container, false);
//
//            RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv1);
//            rv.setHasFixedSize(true);
//            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
//            rv.setLayoutManager(llm);
//            initializeData();
//            RVAdapter adapter = new RVAdapter(persons);
//            adapter.notifyDataSetChanged();
//            rv.setAdapter(adapter);
//
//            //rootView.setId(i);
//            return rootView;
//        }
//        else
//        {
//            return null;
//        }
//        if(signedin==1)
//        {
//            i--;
//        }
        if(position==0)
        {
            if(MainActivity.signedin==0)
            {
                View rootView = inflater.inflate(R.layout.d, container, false);

                TextView tw = (TextView)rootView.findViewById(R.id.tw1);
                tw.setText(R.string.sign_in_msg_home);

                Button b1 = (Button)rootView.findViewById(R.id.sign_in_button);
                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getActivity(), SignIn.class);
                        startActivity(i);
                        getActivity().finish();
                    }
                });


                Button b2 = (Button)rootView.findViewById(R.id.sign_up_button);
                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getActivity(),SignUpActivity.class);
                        startActivity(i);
                        getActivity().finish();
                    }
                });

                return rootView;
            }
            else if(MainActivity.signedin==1)
            {

                if(MainActivity.GlobalUser_Role.equals("Farmer"))
                {

                    View rootView = inflater.inflate(R.layout.c, container, false);

                    FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view)
                        {
                            Log.d("Add","Farm");
                            AddFarm.location_set=false;
                            Intent i = new Intent(getActivity(),AddFarm.class );
                            startActivity(i);
                            getActivity().finish();
                        }
                    });

                    RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv3);
                    rv.setHasFixedSize(true);
                    LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                    rv.setLayoutManager(llm);

                    setfarms();

                    rv.setAdapter(fadap);
                    return rootView;
                }
                else if(MainActivity.GlobalUser_Role.equals("Go-Down Manager"))
                {
                    Log.d("Add", "Warehouse");
                    View rootView = inflater.inflate(R.layout.c, container, false);

                    FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("Add", "warehouse");
                            Intent i = new Intent(getActivity(), AddWarehouse.class);
                            startActivity(i);
                            getActivity().finish();
                        }
                    });

                    RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv3);
                    rv.setHasFixedSize(true);
                    LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                    rv.setLayoutManager(llm);


                    setwarehouses();

                    rv.setAdapter(wadap);
                    return rootView;
                }
                else if(MainActivity.GlobalUser_Role.equals("Both"))
                {
                    Log.d("Show","Dialog");
                    return null;
                }
                else
                {
                    Log.d("Ohhh","shittt");
                    return null;
                }

            }
            else
            {
                Log.d("Error","Error");
                return null;
            }


        }
        else if (position==1)
        {
            View rootView = inflater.inflate(R.layout.a, container, false);

            RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv1);
            rv.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            rv.setLayoutManager(llm);
            initializeData();
            RVAdapter adapter = new RVAdapter(gen_info);
            adapter.notifyDataSetChanged();
            rv.setAdapter(adapter);

            //rootView.setId(i);
            return rootView;
        }
        else if(position==2)
        {
            View rootView = inflater.inflate(R.layout.b, container, false);

            RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv2);
            rv.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            rv.setLayoutManager(llm);
            initializeNews();
            RVAdapter adapter = new RVAdapter(news);
            adapter.notifyDataSetChanged();
            rv.setAdapter(adapter);

            //rootView.setId(i);
            return rootView;
        }
        else
        {
            return null;
        }
    }


    public void showToast(final String toast)
    {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getActivity(), toast, Toast.LENGTH_LONG).show();
            }
        });
    }

}