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
        else if(temp.equals("Ajanta"))
        {
            AddFarm.type_of_crop = "Ajanta";
        }
        else if(temp.equals("Arjun"))
        {
            AddFarm.type_of_crop = "Arjun";
        }
        else if(temp.equals("Parabhani-51"))
        {
            AddFarm.type_of_crop = "Parabhani-51";
        }
        else if(temp.equals("Malvika"))
        {
            AddFarm.type_of_crop = "Malvika";
        }
        else if(temp.equals("Ambemohar"))
        {
            AddFarm.type_of_crop = "Ambemohar";
        }
        else if(temp.equals("tambdajog"))
        {
            AddFarm.type_of_crop = "tambdajog";
        }
        else if(temp.equals("tambdajog"))
        {
            AddFarm.type_of_crop = "tambdajog";
        }
        else if(temp.equals("krishnasal"))
        {
            AddFarm.type_of_crop = "krishnasal";
        }
        else if(temp.equals("champakali"))
        {
            AddFarm.type_of_crop = "champakali";
        }

        else if(temp.equals("Bheema-Red"))
        {
            AddFarm.type_of_crop = "Bheema-Red";
        }
        else if(temp.equals("Bheema-Shweta"))
        {
            AddFarm.type_of_crop = "Bheema-Shweta";
        }
        else if(temp.equals("Bheema-Super"))
        {
            AddFarm.type_of_crop = "Bheema-Super";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
}