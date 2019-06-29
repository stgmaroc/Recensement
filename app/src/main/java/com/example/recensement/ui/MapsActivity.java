package com.example.recensement.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.recensement.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    private float lat;
    private float longi;
    private String marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        String longitude= getIntent().getExtras().getString("long");
        String latitude= getIntent().getExtras().getString("lat");

        getSupportActionBar().setElevation(0);
        //String marker= getIntent().getExtras().getString("client");
        //Toast.makeText(MapsActivity.this,fname+"",Toast.LENGTH_LONG).show();
        try {
            longi = Float.parseFloat(longitude);
            lat = Float.parseFloat(latitude);
            marker= getIntent().getExtras().getString("name");
        }catch (NumberFormatException e){
            longi = 0;
            lat = 0;
            marker= "Point Invalide";
        }

        //Toast.makeText(MapsActivity.this,longi+" hada",Toast.LENGTH_LONG).show();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //Intent myIntent = getIntent();
        //SharedPreferences sharedPref = getSharedPreferences("Mypref",MODE_PRIVATE);
        //in.setEnabled(sharedPref.getBoolean("in",true));
        //out.setEnabled(sharedPref.getBoolean("out",true));
        //lat = sharedPref.getFloat("long",0);
        //longi = sharedPref.getFloat("lat",0);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(longi,lat);
        mMap.addMarker(new MarkerOptions().position(sydney).title(marker));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(longi, lat), 12.0f));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
