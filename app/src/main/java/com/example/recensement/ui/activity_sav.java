package com.example.recensement.ui;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.recensement.Adapters.SpinAdapter;
import com.example.recensement.Model.Client.Respond;
import com.example.recensement.Model.SAV.AfterSalesModel;
import com.example.recensement.Model.SAV.FetchSavProduct;
import com.example.recensement.R;
import com.example.recensement.service.Userclient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.PendingIntent.getActivity;

public class activity_sav extends AppCompatActivity {

    public static final String base_url = "http://154.70.200.106:9009/api/";

    Retrofit.Builder builder = new Retrofit.Builder().baseUrl(base_url).addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit=builder.build();
    Userclient dataInterface= retrofit.create(Userclient.class);

    private Spinner spinner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sav);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        spinner= findViewById(R.id.models);
        fetchModelsName();


    }



    private void fetchModelsName(){
        Call<ArrayList<FetchSavProduct>> call = dataInterface.FetchModelsData("bearer "+loadToken());
        final ProgressBar progressBar;
        //progressBar=findViewById(R.id.pBar);
        //progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<ArrayList<FetchSavProduct>>() {
            @Override
            public void onResponse(Call<ArrayList<FetchSavProduct>> call, Response<ArrayList<FetchSavProduct>> response) {
                //Toast.makeText(AddClient.this,response.raw().code()+"test",Toast.LENGTH_SHORT).show();
                ArrayList<FetchSavProduct> models= (ArrayList<FetchSavProduct>) response.body();
                showModelsInSpinner(models);
            }

            @Override
            public void onFailure(Call<ArrayList<FetchSavProduct>> call, Throwable t) {
                Toast.makeText(activity_sav.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                //progressBar.setVisibility(View.GONE);
            }
        });
    }

    public String loadToken(){
        SharedPreferences sharedPref = getSharedPreferences("Mypref",MODE_PRIVATE);
        return sharedPref.getString("token","");
    }

    private void showModelsInSpinner(ArrayList<FetchSavProduct> result){
        //String array to store all the book names
        final ArrayAdapter<FetchSavProduct> adapter =
                new SpinAdapter(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, result);
        //adapter.setAda
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                FetchSavProduct user = adapter.getItem(position);
                // Here you can do the action you want to...
                Toast.makeText(activity_sav.this, "ID: " + user.getId() + "\nName: " + user.getName(),
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });
    }

}
