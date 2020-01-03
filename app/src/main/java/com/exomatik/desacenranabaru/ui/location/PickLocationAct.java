package com.exomatik.desacenranabaru.ui.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.exomatik.desacenranabaru.R;
import com.exomatik.desacenranabaru.ui.proker.TambahProkerFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class PickLocationAct extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
    public static String latitudeDestination, longitudeDestination;
    private ImageButton imgBack, btnFinish;
    private TextView textTujuan, textAlamat;
    private GoogleMap mMap;
    private Marker markerMe;
    private MarkerOptions place1;
    private LocationManager locationManager;
    private boolean alreadyMoved = true;
    private RelativeLayout rlTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pick_location);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        init();
        onClick();
        if (locationManager != null) {
            locationManager.removeUpdates(PickLocationAct.this);
            getLocation();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        latitudeDestination = null;
        longitudeDestination = null;
    }

    private void init() {
        imgBack = findViewById(R.id.imgBack);
        textAlamat = findViewById(R.id.textAlamat);
        textTujuan = findViewById(R.id.textTujuan);
        btnFinish = findViewById(R.id.btnFinish);
        rlTop = findViewById(R.id.rlTop);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(PickLocationAct.this);
    }


    private void CheckPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("Checking", "Permission");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        } else {
            getLocation();
        }
    }

    public void getLocation() {
        try {
            Log.e("Get", "Getting Location");
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, PickLocationAct.this);
            // GET CURRENT LOCATION
            FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(this);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mFusedLocation.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        // Do it all with location
                        if (mMap != null) {
                            LatLng destination = new LatLng(Float.parseFloat(Double.toString(location.getLatitude())),
                                    Float.parseFloat(Double.toString(location.getLongitude())));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destination, 13.0f));
                            alreadyMoved = false;
                            latitudeDestination = Double.toString(location.getLatitude());
                            longitudeDestination = Double.toString(location.getLongitude());

                            if (markerMe != null){
                                markerMe.remove();
                            }
                            place1 = new MarkerOptions().position(new LatLng(Float.parseFloat(latitudeDestination), Float.parseFloat(longitudeDestination))).title("Destination");
                            textAlamat.setText("Titik Lokasi : " + latitudeDestination + ", " + longitudeDestination);
                            markerMe = mMap.addMarker(place1);
                            textTujuan.setText("Lokasi Anda");
                        }

                    }
                }
            });
        } catch (SecurityException e) {
            Log.e("Error Security", e.getMessage().toString());
        }
    }

    private void onClick() {
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TambahProkerFragment.locationPoint = latitudeDestination + ";" + longitudeDestination;
                finish();
            }
        });

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

        if (latitudeDestination != null && longitudeDestination != null){
//            place1 = new MarkerOptions().position(new LatLng(Float.parseFloat(latitudeDestination), Float.parseFloat(longitudeDestination))).title("Destination");
//            markerMe = mMap.addMarker(place1);
            LatLng destination = new LatLng(Float.parseFloat(latitudeDestination),
                    Float.parseFloat(longitudeDestination));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destination, 13.0f));
            alreadyMoved = false;

            if (markerMe != null){
                markerMe.remove();
            }

            place1 = new MarkerOptions().position(new LatLng(Float.parseFloat(latitudeDestination), Float.parseFloat(longitudeDestination))).title("Destination");
            textAlamat.setText("Titik Lokasi : " + latitudeDestination + ", " + longitudeDestination);
            markerMe = mMap.addMarker(place1);
            textTujuan.setText("Lokasi Anda");
        }
        else {
            CheckPermission();
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                latitudeDestination = Double.toString(latLng.latitude);
                longitudeDestination = Double.toString(latLng.longitude);

                if (markerMe != null){
                    markerMe.remove();
                }

                place1 = new MarkerOptions().position(new LatLng(Float.parseFloat(latitudeDestination), Float.parseFloat(longitudeDestination))).title("Destination");
                textAlamat.setText("Titik Lokasi : " + latitudeDestination + ", " + longitudeDestination);
                markerMe = mMap.addMarker(place1);
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        if (alreadyMoved) {
            if (mMap != null) {
                Log.e("Map Change", "Not null");
                LatLng destination = new LatLng(Float.parseFloat(Double.toString(location.getLatitude())),
                        Float.parseFloat(Double.toString(location.getLongitude())));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destination, 13.0f));
                alreadyMoved = false;
            }
        }
        Log.e("Map Change", "Not ");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
