package com.example.shreyas.newdemo;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.List;

public class MyMapActivity extends AppCompatActivity {

    private GoogleMap map;
    static final LatLng MUMBAI = new LatLng(18.9750,  72.8258);

    List<LatLng> t;
    int FirstPointSet = 0;
    private LatLng firstpoint,lastpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_map);

        t = new ArrayList<LatLng>();

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();

        Marker hamburg = map.addMarker(new MarkerOptions().position(MUMBAI)
                .title("Mumbai").snippet("Im the best"));


        // Move the camera instantly to hamburg with a zoom of 15.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(MUMBAI, 15));

        map.setMyLocationEnabled(true);

        // Zoom in, animating the camera.
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                map.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("You are here")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                t.add(latLng);

                if (FirstPointSet == 0) {
                    FirstPointSet = 1;
                    firstpoint = latLng;
                } else {


                    Polygon p = map.addPolygon(new PolygonOptions()
                            .addAll(t)
                            .strokeColor(Color.RED)
                            .fillColor(Color.BLUE));

//            Polyline line1 = mMap.addPolyline(new PolylineOptions()
//                    .add(lastpoint, point)
//                    .width(5)
//                    .color(Color.RED));
                    lastpoint = latLng;
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.done);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFarm.location_set=true;
                Intent i = new Intent(MyMapActivity.this,AddFarm.class);
                i.putExtra("list",t.toString());
                startActivity(i);
                finish();
            }
        });



    }



}
