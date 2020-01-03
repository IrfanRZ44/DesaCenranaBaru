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
    private double latitudeDestination1 = -4.94648, longitudeDestination1 =  119.8186;
    private double latitudeDestination2 = -4.98704, longitudeDestination2 = 119.82094;
    private double latitudeDestination3 = -4.98964, longitudeDestination3 = 119.82343;
    private double latitudeDestination4 = -4.98481, longitudeDestination4 = 119.83591;
    private double latitudeDestination5 = -4.97707, longitudeDestination5 = 119.83445;
    private double latitudeDestination6 = -4.97226, longitudeDestination6 = 119.83498;
    private double latitudeDestination7 = -4.96641, longitudeDestination7 = 119.84052;
    private double latitudeDestination8 = -4.9599, longitudeDestination8 = 119.83522;
    private double latitudeDestination9 =  -4.94879, longitudeDestination9 = 119.83082;
    private double latitudeDestination10 = -4.93954, longitudeDestination10 = 119.8282;
    private double latitudeDestination11 = -4.93798, longitudeDestination11 = 119.82554;
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
                .title(Constant.kantorDesa);
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
                        , new LatLng(latitudeDestination5, longitudeDestination5)
                        , new LatLng(latitudeDestination6, longitudeDestination6)
                        , new LatLng(latitudeDestination7, longitudeDestination7)
                        , new LatLng(latitudeDestination8, longitudeDestination8)
                        , new LatLng(latitudeDestination9, longitudeDestination9)
                        , new LatLng(latitudeDestination10, longitudeDestination10)
                        , new LatLng(latitudeDestination11, longitudeDestination11)
                        , new LatLng(latitudeDestination1, longitudeDestination1))
                .strokeColor(Color.GREEN)
                .fillColor(Color.parseColor("#51000000")).strokeWidth(2));

    }
}
