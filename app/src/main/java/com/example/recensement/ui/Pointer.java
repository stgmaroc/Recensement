package com.example.recensement.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recensement.Adapters.ListViewClass;
import com.example.recensement.Adapters.PointerRecycleViewAdapter;
import com.example.recensement.GPSTracker;
import com.example.recensement.Model.Pointer.PointageListModel;
import com.example.recensement.Model.Pointer.PointerModel;
import com.example.recensement.Model.Pointer.pointerRespond;
import com.example.recensement.R;
import com.example.recensement.service.Userclient;
import com.example.recensement.toolBar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.Permission;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Pointer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static String LOG_TAG = "RecyclerViewActivity";

    private Button in;
    private Button out;
    private TextView nameTextView;

    private DrawerLayout drawer;

    private toolBar toolbar;
    private Toolbar toolBar;

    private SwipeRefreshLayout swipeContainer;

    ArrayList<PointageListModel> results = new ArrayList<PointageListModel>();

    public static final String base_url_list = "http://154.70.200.106:9009/api/";
    public static final String base_url_pointage_list = "http://192.168.1.90:8000/api/pointage/";

    Gson gson= new GsonBuilder().setLenient().create();
    Retrofit.Builder builder = new Retrofit.Builder().baseUrl(base_url_list).addConverterFactory(GsonConverterFactory.create(gson));
    Retrofit retrofit=builder.build();
    final Userclient client= retrofit.create(Userclient.class);

    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pointer);
        final TextView timeTextView=findViewById(R.id.time);
        final TextView dateTextView=findViewById(R.id.date);
        recyclerView= findViewById(R.id.pointer_list);
        Context context = Pointer.this;

        NavigationView navigationView = findViewById(R.id.nav_view);
        nameTextView=navigationView.getHeaderView(0).findViewById(R.id.username);
        navigationView.setNavigationItemSelectedListener(this);

        in=findViewById(R.id.in);
        out=findViewById(R.id.out);
        loadPointerPreferences();

        //Toolbar and drawer
        toolBar= findViewById(R.id.toolbar2);
        drawer= findViewById(R.id.drawer_layout2);

        toolbar =new toolBar(drawer,toolBar);
        setSupportActionBar(toolBar);
        toolbar.toolbarActivate(drawer,toolBar);
        //Navigation bar


        Thread t = new Thread(){
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                timeTextView.setText(DateUtils.formatDateTime(Pointer.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));
                                dateTextView.setText(DateUtils.formatDateTime(Pointer.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_DATE));
                            }
                        });
                    }
                }
                catch (InterruptedException e) {

                }
            }
        };
        t.start();

        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPointer("IN");
            }
        });

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendPointer("OUT");
            }
        });
        ShowRecyclerView();

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.refresh_list);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //results.deleteAllItem();
                //ShowRecyclerView();
                if(swipeContainer.isRefreshing()){

                    ShowRecyclerView();
                    swipeContainer.setRefreshing(false);
                    //handler.postDelayed(this, 1000);
                }else{
                    //swipeContainer.setRefreshing(false);
                    //mainActivity.forceUpdate();
                    //setLayout();
                }
            }
        });
    }

    public void ShowRecyclerView(){
        mAdapter= new PointerRecycleViewAdapter(getDataSet());
        ListViewClass recyclerViewClass = new ListViewClass(mAdapter,recyclerView,Pointer.this);
    }

    private ArrayList<PointageListModel> getDataSet() {

       final ProgressBar progressBar=findViewById(R.id.pBar);
       progressBar.setVisibility(View.VISIBLE);

       final TextView empty = findViewById(R.id.empty_list);
        //empty.setVisibility(View.VISIBLE);
        //String jj="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9sb2NhbGhvc3Q6ODAwNSIsImF1ZCI6Imh0dHA6XC9cL2xvY2FsaG9zdDo4MDA1IiwiaWF0IjoxMzU2OTk5NTI0LCJuYmYiOjEzNTcwMDAwMDAsImRhdGEiOnsiYWNjZXNzIjoiYWRtaW4iLCJ1c2VyIjoiYWRtaW4ifX0.DFnVt-DnSzjseFxfmm-oRRWfbyMr3hbDRdtwv9w8fKA";
        //Token jwt = new Token(readFile());
        //String token = "bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8xNTQuNzAuMjAwLjEwNjo5MDA5XC9hcGlcL2xvZ2luIiwiaWF0IjoxNTU5NTc2MDk4LCJuYmYiOjE1NTk1NzYwOTgsImp0aSI6InFYM3hST0xIek9VQjNUTHYiLCJzdWIiOjEyLCJwcnYiOiIyM2JkNWM4OTQ5ZjYwMGFkYjM5ZTcwMWM0MDA4NzJkYjdhNTk3NmY3In0.JxwP3GoIsap0rYENdXTmWxCfSish1250xGSkoV-RoL4";
        String token = "bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8xOTIuMTY4LjEuOTA6ODAwMFwvYXBpXC9sb2dpbiIsImlhdCI6MTU2MDQyMTE4MywibmJmIjoxNTYwNDIxMTgzLCJqdGkiOiJGZUhyNHFqaEtITnpRT29SIiwic3ViIjo5LCJwcnYiOiIyM2JkNWM4OTQ5ZjYwMGFkYjM5ZTcwMWM0MDA4NzJkYjdhNTk3NmY3In0.x0R1FRhLeYGxFeY31QAtuiCbhjyqKPUgJ6g2ACrUZ5s";
        //String editor=prefs.getString("token","");

        //String token=loadToken();
        Call<ArrayList<PointageListModel>> call = client.getPointageList("bearer "+loadToken(),"application/json");
        //Call<ArrayList<PointageListModel>> call = client.getPointageList(token,"application/json");

        call.enqueue(new Callback<ArrayList<PointageListModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PointageListModel>> call, Response<ArrayList<PointageListModel>> response) {
                ArrayList<PointageListModel> jsonResult2 = (ArrayList<PointageListModel>) response.body();
                if (jsonResult2.size()!= 0) {
                    showData(jsonResult2);
                    progressBar.setVisibility(View.GONE);
                    empty.setVisibility(View.GONE);
                } else {
                    empty.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<PointageListModel>> call, Throwable t) {
                Toast.makeText(Pointer.this,"Vérifiez votre connexion",Toast.LENGTH_SHORT).show();
                //empty.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
        return results;
    }

    private void showData(ArrayList<PointageListModel> jsonResult) {
        mAdapter= new PointerRecycleViewAdapter(jsonResult);
        recyclerView.setAdapter(mAdapter);
    }

    public void sendPointer(final String status){
        if (ActivityCompat.checkSelfPermission(Pointer.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                //demander la permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                //Log.e("testing", "Permission is revoked");
            }
        else if (ActivityCompat.checkSelfPermission(Pointer.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //call();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            //Log.e("testing", "Permission is revoked");
        }else {
            GPSTracker gps = new GPSTracker(Pointer.this);
            double lat = gps.getLatitude();
            double longi = gps.getLongitude();

            if (lat == 0.00 && longi == 0.00) {
                Toast.makeText(Pointer.this, "Merci d'activer le GPS ", Toast.LENGTH_LONG).show();

            } else {
                PointerModel point = new PointerModel(lat + "," + longi, status);
                String token = "bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8xOTIuMTY4LjEuOTA6ODAwMFwvYXBpXC9sb2dpbiIsImlhdCI6MTU2MDQyMTE4MywibmJmIjoxNTYwNDIxMTgzLCJqdGkiOiJGZUhyNHFqaEtITnpRT29SIiwic3ViIjo5LCJwcnYiOiIyM2JkNWM4OTQ5ZjYwMGFkYjM5ZTcwMWM0MDA4NzJkYjdhNTk3NmY3In0.x0R1FRhLeYGxFeY31QAtuiCbhjyqKPUgJ6g2ACrUZ5s";

                Call<pointerRespond> call = client.addPointer(point, "bearer " + loadToken(), "application/json");
                //Call<pointerRespond> call = client.addPointer(point,token,"application/json");

                final ProgressBar progressBar;
                progressBar = findViewById(R.id.pBar);
                progressBar.setVisibility(View.VISIBLE);

                call.enqueue(new Callback<pointerRespond>() {
                    @Override
                    public void onResponse(Call<pointerRespond> call, Response<pointerRespond> response) {
                        progressBar.setVisibility(View.GONE);

                        if (response.raw().code() == 201) {
                            if (status == "OUT") {
                                in.setEnabled(true);
                                out.setEnabled(false);
                            } else if (status == "IN") {
                                in.setEnabled(false);
                                out.setEnabled(true);
                            }

                            Toast.makeText(Pointer.this, "Pointage ajouter avec succés Merci!!!! ", Toast.LENGTH_LONG).show();
                            ShowRecyclerView();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Pointer.this, "Erreur " + response.raw().code() + " est survenu", Toast.LENGTH_SHORT).show();

                        }
                        savePointerPreferences();
                    }

                    @Override
                    public void onFailure(Call<pointerRespond> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(Pointer.this, "Connexion au serveur impossible", Toast.LENGTH_SHORT).show();
                        //progressBar.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public String loadToken(){
        SharedPreferences sharedPref = getSharedPreferences("Mypref",MODE_PRIVATE);
        return sharedPref.getString("token","");
    }

    public void savePointerPreferences(){
        SharedPreferences sharedPref = getSharedPreferences("Mypref",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("in",in.isEnabled());
        editor.putBoolean("out",out.isEnabled());
        editor.apply();
        //Toast.makeText(LoginActivity.this,"Token"+sharedPref.getString("token",""),Toast.LENGTH_LONG).show();
    }

    public void loadPointerPreferences(){
        SharedPreferences sharedPref = getSharedPreferences("Mypref",MODE_PRIVATE);
        in.setEnabled(sharedPref.getBoolean("in",true));
        out.setEnabled(sharedPref.getBoolean("out",true));
        nameTextView.setText(sharedPref.getString("name","User"));
        //Toast.makeText(LoginActivity.this,"Token"+sharedPref.getString("token",""),Toast.LENGTH_LONG).show();
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.list_client:
                Intent intent3= new Intent(this, ListActivity.class);
                drawer.closeDrawer(GravityCompat.START);
                startActivity(intent3);
                break;
            case R.id.add_client:
                Intent intent= new Intent(this, AddClient.class);
                drawer.closeDrawer(GravityCompat.START);
                startActivity(intent);
                break;
            case R.id.pointer:
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.add_sav:
                Intent intent4= new Intent(this, SAVActivity.class);
                drawer.closeDrawer(GravityCompat.START);
                startActivity(intent4);
                break;
            case R.id.add_visite:
                Intent intent5= new Intent(this, Visites.class);
                drawer.closeDrawer(GravityCompat.START);
                startActivity(intent5);
                break;
            case R.id.log_out:
                //File file = new File(context.getFilesDir(),"Token" );
                //file.delete();
                Intent intent2= new Intent(this, LoginActivity.class);
                drawer.closeDrawer(GravityCompat.START);
                startActivity(intent2);
                finish();
                break;
        }
        return true;
    }
}
