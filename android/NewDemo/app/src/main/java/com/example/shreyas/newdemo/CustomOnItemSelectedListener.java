package com.example.shreyas.newdemo;

/**
 * Created by shreyas on 12/16/15.
 */
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;

import java.util.List;

public class CustomOnItemSelectedListener implements OnItemSelectedListener {

    public static String crop="Wheat";
    public static List<String> wheatlist;
    public static ArrayAdapter<String> dataAdapter1;
    public static String type_of_crop;


    public void onItemSelected(AdapterView<?> parent, View view, int pos,
                               long id) {


        String temp = parent.getItemAtPosition(pos).toString();

        if(temp.equals("Wheat"))
        {
            crop = MainActivity.getInstance().getString(R.string.WheatString);
            wheatlist.clear();
            wheatlist.add("Ajanta");
            wheatlist.add("Arjun");
            wheatlist.add("Parabhani-51");
            wheatlist.add("Malvika");

            dataAdapter1.notifyDataSetChanged();
        }
        else if(temp.equals("Rice"))
        {
            crop = "Rice";
            wheatlist.clear();
            wheatlist.add("Ambemohar");
            wheatlist.add("tambdajog");
            wheatlist.add("krishnasal");
            wheatlist.add("champakali");

            dataAdapter1.notifyDataSetChanged();
        }
        else if(temp.equals("Onion"))
        {
            crop = "Onion";
            wheatlist.clear();
            wheatlist.add("Bheema-Red");
            wheatlist.add("Bheema-Shweta");
            wheatlist.add("Bheema-Super");
            dataAdapter1.notifyDataSetChanged();
        }
        else if(temp.equals("Ajanta"))
        {
            type_of_crop = "Ajanta";
        }
        else if(temp.equals("Arjun"))
        {
            type_of_crop = "Arjun";
        }
        else if(temp.equals("Parabhani-51"))
        {
            type_of_crop = "Parabhani-51";
        }
        else if(temp.equals("Malvika"))
        {
            type_of_crop = "Malvika";
        }
        else if(temp.equals("Ambemohar"))
        {
            type_of_crop = "Ambemohar";
        }
        else if(temp.equals("tambdajog"))
        {
            type_of_crop = "tambdajog";
        }
        else if(temp.equals("tambdajog"))
        {
            type_of_crop = "tambdajog";
        }
        else if(temp.equals("krishnasal"))
        {
            type_of_crop = "krishnasal";
        }
        else if(temp.equals("champakali"))
        {
            type_of_crop = "champakali";
        }

        else if(temp.equals("Bheema-Red"))
        {
            type_of_crop = "Bheema-Red";
        }
        else if(temp.equals("Bheema-Shweta"))
        {
            type_of_crop = "Bheema-Shweta";
        }
        else if(temp.equals("Bheema-Super"))
        {
            type_of_crop = "Bheema-Super";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
}