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

    public static String crop=MainActivity.getInstance().getString(R.string.WheatString);
    public static List<String> wheatlist;
    public static ArrayAdapter<String> dataAdapter1;
    public static String type_of_crop;


    public void onItemSelected(AdapterView<?> parent, View view, int pos,
                               long id) {


        String temp = parent.getItemAtPosition(pos).toString();

        if(temp.equals(view.getContext().getString(R.string.WheatString)))
        {
            crop = MainActivity.getInstance().getString(R.string.WheatString);
            wheatlist.clear();
            wheatlist.add(view.getContext().getString(R.string.AjantaString));
            wheatlist.add(view.getContext().getString(R.string.ArjunString));
            wheatlist.add(view.getContext().getString(R.string.ParabhaniString));
            wheatlist.add(view.getContext().getString(R.string.MalvikaString));

            dataAdapter1.notifyDataSetChanged();
        }
        else if(temp.equals(view.getContext().getString(R.string.RiceString)))
        {
            crop = view.getContext().getString(R.string.RiceString);
            wheatlist.clear();
            wheatlist.add(view.getContext().getString(R.string.AmbemoharString));
            wheatlist.add(view.getContext().getString(R.string.TambdajogString));
            wheatlist.add(view.getContext().getString(R.string.KrishnasalString));
            wheatlist.add(view.getContext().getString(R.string.ChampakaliString));

            dataAdapter1.notifyDataSetChanged();
        }
        else if(temp.equals(view.getContext().getString(R.string.OnionString)))
        {
            crop = view.getContext().getString(R.string.OnionString);
            wheatlist.clear();
            wheatlist.add(view.getContext().getString(R.string.BheemaredString));
            wheatlist.add(view.getContext().getString(R.string.BheemashwetaString));
            wheatlist.add(view.getContext().getString(R.string.BheemasuperString));
            dataAdapter1.notifyDataSetChanged();
        }
        else if(temp.equals(view.getContext().getString(R.string.AjantaString)))
        {
            type_of_crop = view.getContext().getString(R.string.AjantaString);
        }
        else if(temp.equals(view.getContext().getString(R.string.ArjunString)))
        {
            type_of_crop = view.getContext().getString(R.string.ArjunString);
        }
        else if(temp.equals(view.getContext().getString(R.string.ParabhaniString)))
        {
            type_of_crop = view.getContext().getString(R.string.ParabhaniString);
        }
        else if(temp.equals(view.getContext().getString(R.string.MalvikaString)))
        {
            type_of_crop = view.getContext().getString(R.string.MalvikaString);
        }
        else if(temp.equals(view.getContext().getString(R.string.AmbemoharString)))
        {
            type_of_crop = view.getContext().getString(R.string.AmbemoharString);
        }
        else if(temp.equals(view.getContext().getString(R.string.TambdajogString)))
        {
            type_of_crop = view.getContext().getString(R.string.TambdajogString);
        }
        else if(temp.equals(view.getContext().getString(R.string.TambdajogString)))
        {
            type_of_crop = view.getContext().getString(R.string.TambdajogString);
        }
        else if(temp.equals(view.getContext().getString(R.string.KrishnasalString)))
        {
            type_of_crop = view.getContext().getString(R.string.KrishnasalString);
        }
        else if(temp.equals(view.getContext().getString(R.string.ChampakaliString)))
        {
            type_of_crop = view.getContext().getString(R.string.ChampakaliString);
        }

        else if(temp.equals(view.getContext().getString(R.string.BheemaredString)))
        {
            type_of_crop = view.getContext().getString(R.string.BheemaredString);
        }
        else if(temp.equals(view.getContext().getString(R.string.BheemashwetaString)))
        {
            type_of_crop = view.getContext().getString(R.string.BheemashwetaString);
        }
        else if(temp.equals(view.getContext().getString(R.string.BheemasuperString)))
        {
            type_of_crop = view.getContext().getString(R.string.BheemasuperString);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
}