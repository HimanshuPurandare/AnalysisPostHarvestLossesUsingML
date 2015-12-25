package com.example.shreyas.newdemo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;


    public ExpandableListAdapter(Context context, List<String> expandableListTitle,
                                 HashMap<String, List<String>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition)
    {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent)
    {
        Log.d("Inside Childviw","Function");
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);

            if(listPosition==0)
            {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.list_item, null);
                TextView expandedListTextView = (TextView) convertView
                        .findViewById(R.id.expandedListItem);
                expandedListTextView.setText(expandedListText);
                Log.d("position", listPosition + " " + expandedListPosition);

            }
            else if(listPosition==1)
            {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.list_item, null);
                TextView expandedListTextView = (TextView) convertView
                        .findViewById(R.id.expandedListItem);
                expandedListTextView.setText(expandedListText);
                Log.d("position", listPosition + " " + expandedListPosition);
            }
            else if(listPosition==2 )
            {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.list_item, null);
                TextView expandedListTextView = (TextView) convertView
                        .findViewById(R.id.expandedListItem);
                expandedListTextView.setText(expandedListText);
                Log.d("position", listPosition + " " + expandedListPosition);
            }
            else if(listPosition==3 )
            {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.list_item, null);
                TextView expandedListTextView = (TextView) convertView
                        .findViewById(R.id.expandedListItem);
                expandedListTextView.setText(expandedListText);
                Log.d("position", listPosition + " " + expandedListPosition +" "+expandedListText);

            }
            else if(listPosition==4)
            {
                GraphView graph;
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.list_item1, null);

                graph = (GraphView) convertView.findViewById(R.id.graph);



                StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
                staticLabelsFormatter.setHorizontalLabels(ExpandableListDataPump.date);

                graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
                Log.d("graph object created","dfsa");


                Log.d("The arrays created","........");

                for(int date_cnt=0;date_cnt<5;date_cnt++)
                {
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                            new DataPoint(date_cnt,ExpandableListDataPump.Min[date_cnt]),
                            new DataPoint(date_cnt,ExpandableListDataPump.Max[date_cnt])
                    });


                    series.setThickness(10);
                                series.setOnDataPointTapListener(new OnDataPointTapListener() {
                @Override
                public void onTap(Series series, DataPointInterface dataPoint) {
                    Toast.makeText(context, "Series1: On Data Point clicked: " + dataPoint.getY(), Toast.LENGTH_SHORT).show();
                }
            });

                    graph.addSeries(series);



                }



                Log.d("position", listPosition + " " + expandedListPosition +" "+expandedListText);

            }
            else if(listPosition==5)
            {
                GraphView graph;
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.list_item1, null);

                graph = (GraphView) convertView.findViewById(R.id.graph);



                StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
                staticLabelsFormatter.setHorizontalLabels(ExpandableListDataPump.date);

                graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
                Log.d("graph object created", "dfsa");


                Log.d("The arrays created", "........");

                    LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{

                    });

                for(int date_cnt=0;date_cnt<5;date_cnt++)
                {
                    series.appendData(new DataPoint(date_cnt,ExpandableListDataPump.Hum[date_cnt]),false,7);


                }

                series.setThickness(6);
                series.setOnDataPointTapListener(new OnDataPointTapListener() {
                    @Override
                    public void onTap(Series series, DataPointInterface dataPoint) {
                        Toast.makeText(context, "Series1: On Data Point clicked: " + dataPoint.getY(), Toast.LENGTH_SHORT).show();
                    }
                });

                    graph.addSeries(series);







                Log.d("position", listPosition + " " + expandedListPosition +" "+expandedListText);

            }




        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition)
    {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }









}