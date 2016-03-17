package com.example.shreyas.newdemo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

/**
 * Created by niranjan on 16/3/16.
 */
public class ForecastPredictionDialog extends Dialog
{


    private float[] Min,Max,Hum;
    private String[] date;
    private GraphView graph_temp,graph_hum;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.forecast_prediction_layout);


        graph_temp = (GraphView) findViewById(R.id.graph_temp_forecast);


        StaticLabelsFormatter staticLabelsFormatter_temp = new StaticLabelsFormatter(graph_temp);
        staticLabelsFormatter_temp.setHorizontalLabels(date);

        graph_temp.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter_temp);
        Log.d("graph object created", "dfsa");


        Log.d("The arrays created", "........");

        for(int date_cnt=0;date_cnt<5;date_cnt++)
        {
            LineGraphSeries<DataPoint> series_temp = new LineGraphSeries<DataPoint>(new DataPoint[]{
                    new DataPoint(date_cnt,Min[date_cnt]),
                    new DataPoint(date_cnt,Max[date_cnt])
            });


            series_temp.setThickness(10);

            series_temp.setOnDataPointTapListener(new OnDataPointTapListener()
            {
                @Override
                public void onTap(Series series, DataPointInterface dataPoint) {
                   // Toast.makeText(getContext(), "Series1: On Data Point clicked: " + dataPoint.getY(), Toast.LENGTH_SHORT).show();
                }
            });

            graph_temp.addSeries(series_temp);

        }








        graph_hum = (GraphView) findViewById(R.id.graph_hum_forecast);



        StaticLabelsFormatter staticLabelsFormatter_hum = new StaticLabelsFormatter(graph_hum);
        staticLabelsFormatter_hum.setHorizontalLabels(date);

        graph_hum.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter_hum);
        Log.d("graph object created", "dfsa");


        Log.d("The arrays created", "........");

        LineGraphSeries<DataPoint> series_hum = new LineGraphSeries<DataPoint>(new DataPoint[]{

        });

        for(int date_cnt=0;date_cnt<5;date_cnt++)
        {
            series_hum.appendData(new DataPoint(date_cnt,Hum[date_cnt]),false,7);


        }

        series_hum.setThickness(6);

        series_hum.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
               // Toast.makeText(getContext(), "Series1: On Data Point clicked: " + dataPoint.getY(), Toast.LENGTH_SHORT).show();
            }
        });

        graph_hum.addSeries(series_hum);



    }




    ForecastPredictionDialog(Context context,float[] Min,float[] Max,float[] Hum,String[]date)
    {
        super(context);
        this.Min=Min;
        this.Max=Max;
        this.Hum=Hum;
        this.date=date;


    }

}