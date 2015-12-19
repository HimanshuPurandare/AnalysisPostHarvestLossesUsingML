package com.example.shreyas.newdemo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class SuperAwesomeCardFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private int position;

    List<General_info> persons;

    private void initializeData()
    {

        Log.d("Inside data i", "Inside Data Initi");
        persons = new ArrayList<>();
        persons.add(new General_info("Maharashtra Agriculture", "Agriculture is the mainstay of the state of Maharashtra. It is the main occupation of the people. Both food crops and cash crops are grown in the state. ", R.drawable.d));
        persons.add(new General_info("Main crops in Maharashtra", "The main food crops of Maharashtra are mangoes, grapes, bananas, oranges, wheat, rice, jowar, bajra, and pulses. Cash crops include groundnut, cotton, sugarcane, turmeric, and tobacco. ", R.drawable.e));
        persons.add(new General_info("Agro Climatic Zones in Maharashtra", "India has been divided into 15 resource development zones of which 14 fall in the main land and remaining one in the islands of Bay of Bengal and the Arabian sea.", R.drawable.f));
        persons.add(new General_info("Onion storage structure", "Modern onion storage structures have been so planned to aerate the onions from all sides.Site selected for onion storage structure should be well drained and should be easily accusable to good road", R.drawable.g));
        persons.add(new General_info("Wheat Farming", "Wheat is the main cereal crop in India. The total area under the crop is about 29.8 million hectares in the country.", R.drawable.h));
        persons.add(new General_info("Seasons of rice growing in the Maharashtra", "In Maharashtra state, rice crop is predominantly cultivated under rainfed conditions, i.e. Kharif. In Marathwada region area under rice comprises 90,000 ha which is completely drill and rainfed cultivation.", R.drawable.i));

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

        if (position==1)
        {
            View rootView = inflater.inflate(R.layout.a, container, false);

            RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv1);
            rv.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            rv.setLayoutManager(llm);
            initializeData();
            RVAdapter adapter = new RVAdapter(persons);
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