package com.example.recensement;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.recensement.ui.ListActivity;
import com.example.recensement.ui.LoginActivity;

import static android.support.v4.content.ContextCompat.getSystemService;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private LocationManager locationManager;
    private LocationListener locationListener;
    private EditText Name, Name2;
    private String loc=null;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbarActivate();


        Name = (EditText) findViewById(R.id.Name);
        Name2 = (EditText) findViewById(R.id.Name2);
        loc = new String();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ListFragment()).commit();
        }

        locationListener = new LocationListener() {


            @Override
            public void onLocationChanged(Location location) {
                loc = loc+"/n "+location.getLatitude() + "," + location.getLongitude();
                //Log.d("MyApp","hahowa"+locationListener.getLatitude());
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

        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 10);
                return;
            } else {

            }
        }

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 10:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    locationManager.requestLocationUpdates("gps",5000, 0, locationListener);
                return;
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private boolean validateString(String name) {
        if (name=="")
            return false;
        else
            return true;
    }

    public void toolbarActivate (){

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open_nav,R.string.close_nav);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    public void toolbarActivate (Toolbar toolbar){

        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open_nav,R.string.close_nav);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }


    public void onRegister(View view){
            String name = Name.getText().toString();
            String name2 = Name2.getText().toString();


        //locationManager.requestLocationUpdates("gps",5000, 0, locationListener);

        String locc = loc;
        GPSTracker gps = new GPSTracker(this);
        int status = 0;
        double lat=gps.getLatitude();
        double longi=gps.getLongitude();
        //d=d;
            // locc += loc.getLoc();
        if (isNetworkAvailable() & validateString(name) & validateString(name2)) {
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(name, name2, "" + lat + "," + longi);
        }
        else {

        }

        }

        public void rest(View view){

            Intent intent= new Intent(this,RestApiActivity.class);
            startActivity(intent);

        }

        public void listt(View view){

            Intent intent= new Intent(this, ListActivity.class);
            startActivity(intent);

        }
        public void loginAccess(View view){

            Intent intent= new Intent(this, LoginActivity.class);
            startActivity(intent);

        }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.add_client:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AddFragment()).commit();
                break;
            case R.id.log_out:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ListFragment()).commit();
                break;
        }
        return true;
    }
}
