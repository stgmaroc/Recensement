package com.example.recensement.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recensement.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class DetailsClient extends AppCompatActivity {

    private GoogleMap mMap;
    private TextView first_name;
    private TextView last_name;
    private TextView type;
    private TextView adresse;
    private TextView phone;
    private TextView location;
    private TextView magasin;
    private TextView city;
    ViewGroup viewGroup;
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_client);
        Button Loca = findViewById(R.id.loc);
        Loca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localiser();
            }
        });

        getSupportActionBar().setElevation(0);

        sharedPref = getSharedPreferences("Mypref",MODE_PRIVATE);

        //Toast.makeText(this,"Token"+sharedPref.getString("first_name","nok"),Toast.LENGTH_LONG).show();

        first_name=findViewById(R.id.first_name);
        last_name=findViewById(R.id.last_name);
        type=findViewById(R.id.type);
        adresse=findViewById(R.id.adresse);
        phone=findViewById(R.id.phone_number);
        magasin=findViewById(R.id.magasin);
        location=findViewById(R.id.Location);
        city=findViewById(R.id.city);

        String fname= sharedPref.getString("first_name","nok");
        first_name.setText(fname);
        String lname= sharedPref.getString("last_name","nok");
        last_name.setText(lname);
        String city_text= sharedPref.getString("city","nok");
        city.setText(city_text);
        String type_text= sharedPref.getString("type","nok");
        type.setText(type_text);
        String magazin= sharedPref.getString("magasin","nok");
        magasin.setText(magazin);
        String phone_number= sharedPref.getString("phone","nok");
        phone.setText(phone_number);
        String adresse_text= sharedPref.getString("adress","nok");
        adresse.setText(adresse_text);
        String localisation= sharedPref.getString("localisation","nok");
        location.setText(localisation);
        //String location_text= getIntent().getExtras().getString("location");
        //location.setText(location_text);
        }


   /* @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }*/

    public void localiser() {
        String[] data = sharedPref.getString("localisation","0.0,0.0").split(",");
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("long",data[0]);
        intent.putExtra("lat",data[1]);
        intent.putExtra("name",sharedPref.getString("first_name","nok")+" "+sharedPref.getString("last_name","nok"));
        startActivityForResult(intent,0);

    }
}
