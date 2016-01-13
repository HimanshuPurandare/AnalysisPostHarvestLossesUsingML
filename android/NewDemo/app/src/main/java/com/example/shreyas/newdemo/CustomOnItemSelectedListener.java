package com.example.shreyas.newdemo;

/**
 * Created by shreyas on 12/16/15.
 */
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class CustomOnItemSelectedListener implements OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> parent, View view, int pos,
                               long id) {


        String temp = parent.getItemAtPosition(pos).toString();

        if(temp.equals("Wheat"))
        {
            AddFarm.crop = "Wheat";
            AddFarm.wheatlist.clear();
            AddFarm.wheatlist.add("Ajanta");
            AddFarm.wheatlist.add("Arjun");
            AddFarm.wheatlist.add("Parabhani-51");
            AddFarm.wheatlist.add("Malvika");

            AddFarm.dataAdapter1.notifyDataSetChanged();
        }
        else if(temp.equals("Rice"))
        {
            AddFarm.crop = "Rice";
            AddFarm.wheatlist.clear();
            AddFarm.wheatlist.add("Ambemohar");
            AddFarm.wheatlist.add("tambdajog");
            AddFarm.wheatlist.add("krishnasal");
            AddFarm.wheatlist.add("champakali");

            AddFarm.dataAdapter1.notifyDataSetChanged();
        }
        else if(temp.equals("Onion"))
        {
            AddFarm.crop = "Onion";
            AddFarm.wheatlist.clear();
            AddFarm.wheatlist.add("Bheema-Red");
            AddFarm.wheatlist.add("Bheema-Shweta");
            AddFarm.wheatlist.add("Bheema-Super");
            AddFarm.dataAdapter1.notifyDataSetChanged();
        }
        else if(temp.equals("Wheat1"))
        {
            AddFarm.type_of_crop = "Wheat1";
        }
        else if(temp.equals("Wheat2"))
        {
            AddFarm.type_of_crop = "Wheat2";
        }
        else if(temp.equals("Wheat3"))
        {
            AddFarm.type_of_crop = "Wheat3";
        }
        else if(temp.equals("Wheat4"))
        {
            AddFarm.type_of_crop = "Wheat4";
        }
        else if(temp.equals("Rice1"))
        {
            AddFarm.type_of_crop = "Rice1";
        }
        else if(temp.equals("Rice2"))
        {
            AddFarm.type_of_crop = "Rice2";
        }
        else if(temp.equals("Onion1"))
        {
            AddFarm.type_of_crop = "Onion1";
        }
        else if(temp.equals("Onion2"))
        {
            AddFarm.type_of_crop = "Onion2";
        }
        else if(temp.equals("Onion3"))
        {
            AddFarm.type_of_crop = "Onion3";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
}