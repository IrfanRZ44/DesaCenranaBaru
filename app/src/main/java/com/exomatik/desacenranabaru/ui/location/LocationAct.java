package com.exomatik.desacenranabaru.ui.location;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.exomatik.desacenranabaru.R;
import com.exomatik.desacenranabaru.utils.Constant;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

public class LocationAct extends AppCompatActivity implements OnMapReadyCallback {
    private double latitudeDestination1 = -4.9880839, longitudeDestination1 =  119.8073873;
    private double latitudeDestination2 = -5.0041588, longitudeDestination2 = 119.7955427;
    private double latitudeDestination3 = -5.0392143, longitudeDestination3 = 119.8163137;
    private double latitudeDestination4 = -5.0392143, longitudeDestination4 = 119.8163137;
    private ImageButton imgBack;
    private GoogleMap mMap;
    private Marker markerMe;
    private MarkerOptions place1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_location);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        init();
        onClick();
    }

    private void init() {
        imgBack = findViewById(R.id.imgBack);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(LocationAct.this);
    }

    private void onClick() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        setArea();
        LatLng destination = new LatLng(Float.parseFloat(Constant.latitude),
                Float.parseFloat(Constant.longitude));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destination, 16.0f));

        if (markerMe != null){
            markerMe.remove();
        }

        place1 = new MarkerOptions().position(new LatLng(Float.parseFloat(Constant.latitude), Float.parseFloat(Constant.longitude)))
                .title(Constant.appName);
        markerMe = mMap.addMarker(place1);
    }

    private void setArea(){
        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mMap.addPolygon(new PolygonOptions()
                .add(new LatLng(latitudeDestination1, longitudeDestination1)
                        , new LatLng(latitudeDestination2, longitudeDestination2)
                        , new LatLng(latitudeDestination3, longitudeDestination3)
                        , new LatLng(latitudeDestination4, longitudeDestination4)
                        , new LatLng(latitudeDestination1, longitudeDestination1))
                .strokeColor(Color.GREEN)
                .fillColor(Color.parseColor("#51000000")).strokeWidth(2));

    }
}
