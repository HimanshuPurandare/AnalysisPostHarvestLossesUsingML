package com.example.shreyas.newdemo;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.List;

public class SuperAwesomeCardFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private int position;

    MyFarmAdapter adap;

    List<General_info> gen_info;
    List<General_info> news;
    List<Farm_info> farmlist;


    public SuperAwesomeCardFragment()
    {
        farmlist = new ArrayList<Farm_info>();

        adap = new MyFarmAdapter(farmlist);
    }

    private void initializeData()
    {

        Log.d("Inside data i", "Inside Data Initi");
        gen_info = new ArrayList<>();
        gen_info.add(new General_info("Maharashtra Agriculture", "Agriculture is the mainstay of the state of Maharashtra. It is the main occupation of the people. Both food crops and cash crops are grown in the state. ", R.drawable.d));
        gen_info.add(new General_info("Main crops in Maharashtra", "The main food crops of Maharashtra are mangoes, grapes, bananas, oranges, wheat, rice, jowar, bajra, and pulses. Cash crops include groundnut, cotton, sugarcane, turmeric, and tobacco. ", R.drawable.e));
        gen_info.add(new General_info("Agro Climatic Zones in Maharashtra", "India has been divided into 15 resource development zones of which 14 fall in the main land and remaining one in the islands of Bay of Bengal and the Arabian sea.", R.drawable.f));
        gen_info.add(new General_info("Onion storage structure", "Modern onion storage structures have been so planned to aerate the onions from all sides.Site selected for onion storage structure should be well drained and should be easily accusable to good road", R.drawable.g));
        gen_info.add(new General_info("Wheat Farming", "Wheat is the main cereal crop in India. The total area under the crop is about 29.8 million hectares in the country.", R.drawable.h));
        gen_info.add(new General_info("Seasons of rice growing in the Maharashtra", "In Maharashtra state, rice crop is predominantly cultivated under rainfed conditions, i.e. Kharif. In Marathwada region area under rice comprises 90,000 ha which is completely drill and rainfed cultivation.", R.drawable.i));

    }

    private void initializeNews()
    {
        Log.d("Inside news intion", "News initial");
        news = new ArrayList<>();
        news.add(new General_info("Weather-Proof Indian Agriculture: President Mukherjee", "Indian agriculture is increasingly becoming a victim of unpredictable weather. Be it the Monsoon fury or dreading the drought, Indian agriculture is still bearing the brunt of inclement weather by way of declining farm produce and shrinking exports. ", R.drawable.a));
        news.add(new General_info("R Gopalakrishnan: Intelligent farming", "The urban excitement about start-ups, entrepreneurship and innovation is missing in farming. Try compiling a repertoire of bottom-of-the-pyramid innovation articles on farming. Agri-business correspondents write reasoned columns", R.drawable.b));
        news.add(new General_info("Agriculture Minister Emphasizes the need to reduce Post-Harvest Losses in Horticulture", "Union Agriculture Minister, Shri Sharad Pawar today emphasized the need to reduce post-harvest losses in horticultural sector. He said that 18-25% of horticultural produce is lost, as estimated by the Central Institute of Post-Harvest Engineering and Technology study.", R.drawable.c));
    }
    public void setfarms()
    {
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

                        a = response.getJSONArray("AddFarmName");

                        int l = a.length();

                        for(int i=0;i<l;i++)
                        {
                            farmlist.add(new Farm_info(a.getString(i)));
                        }


                        // farmlist.add(new Farm_info(fname));


                        adap.notifyDataSetChanged();



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
                tw.setText("Please SignIn to get this feature");

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

                View rootView = inflater.inflate(R.layout.c, container, false);

                FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        if(MainActivity.GlobalUser_Role.equals("Farmer"))
                        {
                            Log.d("Add","Farm");
                            Intent i = new Intent(getActivity(),AddFarm.class );
                            startActivity(i);
                            getActivity().finish();
                        }
                        else if(MainActivity.GlobalUser_Role.equals("Go-Down Manager"))
                        {
                            Log.d("Add","Warehouse");
                        }
                        else if(MainActivity.GlobalUser_Role.equals("Both"))
                        {
                            Log.d("Show","Dialog");
                        }
                        else
                        {
                            Log.d("Ohhh","shittt");
                        }
                    }
                });

                RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv3);
                rv.setHasFixedSize(true);
                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                rv.setLayoutManager(llm);

                setfarms();

                rv.setAdapter(adap);
                return rootView;
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

}