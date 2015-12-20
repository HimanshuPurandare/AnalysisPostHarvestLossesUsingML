package com.example.shreyas.newdemo;

/**
 * Created by shreyas on 12/20/15.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> farmdata = new ArrayList<String>();
        List<String> weatherdata = new ArrayList<String>();
        List<String> predictions = new ArrayList<String>();
        List<String> notifications = new ArrayList<String>();

        farmdata.add("hi");
        farmdata.add("hiii");


        weatherdata.add("hi");
        weatherdata.add("hiii");


        predictions.add("hi");
        predictions.add("hiii");


        notifications.add("hi");
        notifications.add("hiii");

        expandableListDetail.put("FARM DATA", farmdata);
        expandableListDetail.put("WEATHER DATA", weatherdata);
        expandableListDetail.put("PREDICTIONS", predictions);
        expandableListDetail.put("NOTIFICATIONS", notifications);
        return expandableListDetail;
    }
}
